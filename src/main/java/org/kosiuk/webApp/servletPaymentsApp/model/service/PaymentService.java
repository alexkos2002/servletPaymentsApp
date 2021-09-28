package org.kosiuk.webApp.servletPaymentsApp.model.service;

import org.apache.log4j.Logger;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.PaymentConfirmationDto;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.PaymentDetailsDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.*;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.*;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.*;

import org.kosiuk.webApp.servletPaymentsApp.controller.dto.PaymentSendingDto;

import java.util.*;

import static java.lang.Math.round;

public class PaymentService {

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final ResourceBundle rb = ResourceBundle.getBundle("application");
    private final ResourceBundle rbDB = ResourceBundle.getBundle("/db/database");
    private final double comissionPercantage = Double.parseDouble(rb.getString("comissionPercentage"));
    private final int halfPageSize = Integer.parseInt(rbDB.getString("payment.page.halfSize"));
    public static final Logger logger = Logger.getLogger(PaymentService.class);

    public synchronized Optional<PaymentConfirmationDto> prepareToCardPayment(int senderMoneyAccId, long receiverCardNum,
                                                                              String payedSumString, String assignment, ResourceBundle messagesRb)
            throws NoRequisitesByNumberException, ToOwnRequisitePaymentException, BlockedAccountException,
            NotEnoughMoneyOnAccountException {

        String[] sumIntDec = payedSumString.split("\\.");
        long payedSumInt = Long.parseLong(sumIntDec[0]);
        int payedSumDec = Integer.parseInt(sumIntDec[1]);

        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            CreditCardDao creditCardDao = daoFactory.createCreditCardDao(connection);
            Optional<CreditCard> receiverCardOptional = creditCardDao.findCreditCardByNumber(receiverCardNum);
            if (receiverCardOptional.isEmpty()) {
                throw new NoRequisitesByNumberException();
            }
            CreditCard receiverCreditCard = receiverCardOptional.get();
            int receiverMoneyAccountId = receiverCreditCard.getMoneyAccountId();

            MoneyAccountDao moneyAccountDao = daoFactory.createMoneyAccountDao(connection);
            MoneyAccount receiverMoneyAccount = moneyAccountDao.findById(receiverMoneyAccountId).get();
            if (receiverMoneyAccount.getId() == senderMoneyAccId) {
                throw new ToOwnRequisitePaymentException();
            }
            MoneyAccount senderMoneyAccount = moneyAccountDao.findById(senderMoneyAccId).get();
            if (!(senderMoneyAccount.getActive() == MoneyAccountActStatus.ACTIVE)) {
                throw new BlockedAccountException(messagesRb.getString("verification.payment.sender.blocked"));
            }
            if (!(receiverMoneyAccount.getActive() == MoneyAccountActStatus.ACTIVE)) {
                throw new BlockedAccountException(messagesRb.getString("verification.payment.receiver.blocked"));
            }

            long payedSum = payedSumInt * 100 + payedSumDec;
            long paymentComission = round((payedSum) * comissionPercantage);
            int paymentComissionDec = (int)(paymentComission % 100);
            long paymentComissionInt = (paymentComission - paymentComissionDec) / 100;
            long totalInt = payedSum + paymentComission;
            int totalDec = (int)(totalInt % 100);
            totalInt = (totalInt - totalDec) / 100;

            if (senderMoneyAccount.getCurSumAvailableInt() < totalInt ||
                    (senderMoneyAccount.getCurSumAvailableInt() == totalInt && senderMoneyAccount.getCurSumAvailableDec() < totalDec)) {
                NotEnoughMoneyOnAccountException exception = new NotEnoughMoneyOnAccountException();
                exception.setPayedSumString(payedSumInt + "." + payedSumDec);
                exception.setPaymentComissionString(paymentComissionInt + "." + paymentComissionDec);
                throw exception;
            }

            long curPaymentNum = 0;
            try {
                PaymentDao paymentDao = daoFactory.createPaymentDao(connection);
                AdditionalPropertiesDao addPropDao = daoFactory.createAdditionalPropertiesDao(connection);
                curPaymentNum = addPropDao.getCurPaymentNum();
                addPropDao.incCurPaymentNum();

                Payment payment = new Payment(curPaymentNum, totalInt, totalDec, paymentComissionInt,
                        paymentComissionDec, assignment, PaymentStatus.PREPARED, senderMoneyAccId);

                paymentDao.create(payment);

                long curSumAvailableInt = moneyAccountDao.selectCurSumAvailableInt(senderMoneyAccId) * 100 - totalInt * 100 +
                        moneyAccountDao.selectCurSumAvailableDec(senderMoneyAccId) - totalDec;
                int curSumAvailableDec = (int)(curSumAvailableInt % 100);
                curSumAvailableInt = (curSumAvailableInt - curSumAvailableDec) / 100;

                moneyAccountDao.updateCurSumAvailable(senderMoneyAccId, curSumAvailableInt, curSumAvailableDec);

            } catch (DaoException e) {
                logger.warn(e.getMessage());
                connection.rollback();
            }

            connection.commit();

            return Optional.of(new PaymentConfirmationDto(senderMoneyAccId, receiverMoneyAccountId, receiverCardNum,
                    receiverMoneyAccount.getName(), curPaymentNum, assignment, payedSumInt, payedSumDec,
                    totalInt, totalDec, paymentComissionInt, paymentComissionDec));


        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Optional.empty();
        }

    }

    public synchronized Optional<PaymentConfirmationDto> prepareToMoneyAccountPayment(int senderMoneyAccId, long receiverMoneyAccNum,
                                                                                      String payedSumString, String assignment, ResourceBundle messagesRb)
            throws NoRequisitesByNumberException, ToOwnRequisitePaymentException, BlockedAccountException,
            NotEnoughMoneyOnAccountException {

        String[] sumIntDec = payedSumString.split("\\.");
        long payedSumInt = Long.parseLong(sumIntDec[0]);
        int payedSumDec = Integer.parseInt(sumIntDec[1]);

        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            MoneyAccountDao moneyAccountDao = daoFactory.createMoneyAccountDao(connection);
            PaymentDao paymentDao = daoFactory.createPaymentDao(connection);
            Optional<MoneyAccount> receiverMoneyAccOptional = moneyAccountDao.findByNumber(receiverMoneyAccNum);
            if (receiverMoneyAccOptional.isEmpty()) {
                throw new NoRequisitesByNumberException();
            }
            MoneyAccount receiverMoneyAccount = receiverMoneyAccOptional.get();

            int receiverMoneyAccId = receiverMoneyAccount.getId();
            if (receiverMoneyAccId == senderMoneyAccId) {
                throw new ToOwnRequisitePaymentException();
            }

            MoneyAccount senderMoneyAccount = moneyAccountDao.findById(senderMoneyAccId).get();
            if (!(senderMoneyAccount.getActive() == MoneyAccountActStatus.ACTIVE)) {
                throw new BlockedAccountException(rb.getString("verification.payment.sender.blocked"));
            }
            if (!(receiverMoneyAccount.getActive() == MoneyAccountActStatus.ACTIVE)) {
                throw new BlockedAccountException(rb.getString("verification.payment.receiver.blocked"));
            }

            long payedSum = payedSumInt * 100 + payedSumDec;
            long paymentComission = round((payedSum) * comissionPercantage);
            int paymentComissionDec = (int)(paymentComission % 100);
            long paymentComissionInt = (paymentComission - paymentComissionDec) / 100;
            long totalInt = payedSum + paymentComission;
            int totalDec = (int)(totalInt % 100);
            totalInt = (totalInt - totalDec) / 100;

            if (senderMoneyAccount.getCurSumAvailableInt() < totalInt ||
                    (senderMoneyAccount.getCurSumAvailableInt() == totalInt && senderMoneyAccount.getCurSumAvailableDec() < totalDec)) {
                NotEnoughMoneyOnAccountException exception = new NotEnoughMoneyOnAccountException();
                exception.setPayedSumString(payedSumInt + "." + payedSumDec);
                exception.setPaymentComissionString(paymentComissionInt + "." + paymentComissionDec);
                throw exception;
            }

            long curPaymentNum = 0;
            try {
                AdditionalPropertiesDao addPropDao = daoFactory.createAdditionalPropertiesDao(connection);
                curPaymentNum = addPropDao.getCurPaymentNum();
                addPropDao.incCurPaymentNum();

                Payment payment = new Payment(curPaymentNum, totalInt, totalDec, paymentComissionInt,
                        paymentComissionDec, assignment, PaymentStatus.PREPARED, senderMoneyAccId);

                paymentDao.create(payment);

                long curSumAvailableInt = moneyAccountDao.selectCurSumAvailableInt(senderMoneyAccId) * 100 - totalInt * 100 +
                        moneyAccountDao.selectCurSumAvailableDec(senderMoneyAccId) - totalDec;
                int curSumAvailableDec = (int)(curSumAvailableInt % 100);
                curSumAvailableInt = (curSumAvailableInt - curSumAvailableDec) / 100;

                moneyAccountDao.updateCurSumAvailable(senderMoneyAccId, curSumAvailableInt, curSumAvailableDec);

            } catch (DaoException e) {
                logger.warn(e.getMessage());
                connection.rollback();
            }

            connection.commit();

            return Optional.of(new PaymentConfirmationDto(senderMoneyAccId, receiverMoneyAccId, receiverMoneyAccNum,
                    receiverMoneyAccount.getName(), curPaymentNum, assignment, payedSumInt, payedSumDec,
                    totalInt, totalDec, paymentComissionInt, paymentComissionDec));

        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Optional.empty();
        }

    }

    public void cancelPayment(int senderMoneyAccId, long paymentNumber, String totalString) {
        String[] totalIntDec = totalString.split("\\.");
        long totalInt = Long.parseLong(totalIntDec[0]);
        int totalDec = Integer.parseInt(totalIntDec[1]);

        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            PaymentDao paymentDao = daoFactory.createPaymentDao(connection);
            MoneyAccountDao moneyAccountDao = daoFactory.createMoneyAccountDao(connection);
            try {
                long curSumAvailableInt = moneyAccountDao.selectCurSumAvailableInt(senderMoneyAccId) * 100 + totalInt * 100 +
                        moneyAccountDao.selectCurSumAvailableDec(senderMoneyAccId) + totalDec;
                int curSumAvailableDec = (int)(curSumAvailableInt % 100);
                curSumAvailableInt = (curSumAvailableInt - curSumAvailableDec) / 100;
                moneyAccountDao.updateCurSumAvailable(senderMoneyAccId, curSumAvailableInt, curSumAvailableDec);
                paymentDao.deleteByNumber(paymentNumber);
                connection.commit();
            } catch (DaoException e) {
                logger.warn(e.getMessage());
                connection.rollback();
            }
        }
    }

    public void sendPayment(PaymentSendingDto paymentSendingDto) {
        String[] payedSumIntDec = paymentSendingDto.getPayedSumString().split("\\.");
        long payedSumInt = Long.parseLong(payedSumIntDec[0]);
        int payedSumDec = Integer.parseInt(payedSumIntDec[1]);

        String[] totalIntDec = paymentSendingDto.getTotalString().split("\\.");
        long totalInt = Long.parseLong(totalIntDec[0]);
        int totalDec = Integer.parseInt(totalIntDec[1]);

        int senderMoneyAccId = paymentSendingDto.getSenderMoneyAccId();
        int receiverMoneyAccId = paymentSendingDto.getReceiverMoneyAccId();
        long paymentNumber = paymentSendingDto.getPaymentNumber();

        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            PaymentDao paymentDao = daoFactory.createPaymentDao(connection);
            CreditCardDao creditCardDao = daoFactory.createCreditCardDao(connection);
            MoneyAccountDao moneyAccountDao = daoFactory.createMoneyAccountDao(connection);
            TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
            try {

                paymentDao.updateStatus(paymentNumber, PaymentStatus.SENT);

                int senderCreditCardId = creditCardDao.findByMoneyAccountId(senderMoneyAccId).get().getId();

                long senderSumInt = moneyAccountDao.selectSumInt(senderMoneyAccId) * 100 - totalInt * 100 +
                        moneyAccountDao.selectSumDec(senderMoneyAccId) - totalDec;
                int senderSumDec = (int)(senderSumInt % 100);
                senderSumInt = (senderSumInt - senderSumDec) / 100;

                moneyAccountDao.updateSum(senderMoneyAccId, senderSumInt, senderSumDec);
                moneyAccountDao.updateCurSumAvailable(senderMoneyAccId, senderSumInt, senderSumDec);
                creditCardDao.updateAvailableSum(senderCreditCardId, senderSumInt, senderSumDec);

                int receiverCreditCardId = creditCardDao.findByMoneyAccountId(receiverMoneyAccId).get().getId();

                long recSumInt = moneyAccountDao.selectSumInt(receiverMoneyAccId) * 100 + payedSumInt * 100 +
                        moneyAccountDao.selectSumDec(receiverMoneyAccId) + payedSumDec;
                int recSumDec = (int)(recSumInt % 100);
                recSumInt = (recSumInt - recSumDec) / 100;

                moneyAccountDao.updateCurSumAvailable(receiverMoneyAccId, recSumInt, recSumDec);
                moneyAccountDao.updateSum(receiverMoneyAccId, recSumInt, recSumDec);
                creditCardDao.updateAvailableSum(receiverCreditCardId, recSumInt, recSumDec);

                Transaction transaction = new Transaction(payedSumInt, payedSumDec, receiverMoneyAccId, paymentNumber, senderMoneyAccId);
                transactionDao.create(transaction);
                connection.commit();
            } catch (DaoException e) {
                logger.warn(e.getMessage());
                connection.rollback();
            }
        } catch (Exception e){
            logger.warn(e.getMessage());
        }
    }

    public List<Payment> getAllPaymentsPage(int pageNum, String sortParameter) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            PaymentDao paymentDao = daoFactory.createPaymentDao(connection);
            return paymentDao.findAllSortedPageable(pageNum, sortParameter);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Payment> getAllSortedPageableReceivedOnMoneyAccount(int receiverMoneyAccountId, int pageNumber, String sortParameter) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
            PaymentDao paymentDao = daoFactory.createPaymentDao(connection);
            List<Transaction> transactions = transactionDao.findAllByReceiverMoneyAccountId(receiverMoneyAccountId);
            List<Payment> receivedPayments = new ArrayList<>();
            transactions.stream().forEach(transaction -> {
                try {
                    receivedPayments.add(paymentDao.findByNumber(transaction.getPaymentNumber()).get());
                } catch (DaoException e) {
                    logger.warn(e.getMessage());
                    e.printStackTrace();
                }
            });

            int totalReceivedItems = receivedPayments.size();

            int totalReceivedPages;
            if ((totalReceivedItems % halfPageSize) != 0) {
                totalReceivedPages = totalReceivedItems / halfPageSize + 1;
            } else {
                totalReceivedPages = totalReceivedItems / halfPageSize;
            }

            int lastElNumber;
            if (totalReceivedItems == 0) {
                lastElNumber = 0;
            } else if ((totalReceivedItems % halfPageSize) != 0 && pageNumber == totalReceivedPages - 1) {
                lastElNumber = (halfPageSize * pageNumber) + totalReceivedItems % halfPageSize;
            } else {
                lastElNumber = halfPageSize * (pageNumber + 1);
            }
            if (sortParameter.equals(rbDB.getString("field.payment.number"))) {
                receivedPayments.sort(Comparator.comparingLong(Payment::getNumber));
            } else if (sortParameter.equals(rbDB.getString("sortParam.payment.time.asc"))) {
                receivedPayments.sort(Comparator.comparing(Payment::getTimeString));
            } else if (sortParameter.equals(rbDB.getString("sortParam.payment.time.desc"))) {
                receivedPayments.sort((paymentOne, paymentTwo) -> {return paymentTwo.getTimeString().compareTo(paymentOne.getTimeString());});
            }
            return receivedPayments.subList(halfPageSize * pageNumber, lastElNumber);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Payment> getAllSortedPageableSentFromMoneyAccount(int senderMoneyAccountId, int pageNumber, String sortParameter) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            PaymentDao paymentDao = daoFactory.createPaymentDao(connection);
            return paymentDao.findAllBySenderMonAccIdSortedPageable(senderMoneyAccountId, pageNumber, sortParameter);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Collections.emptyList();
        }
    }

    public Optional<PaymentDetailsDto> getPaymentDetails(long paymentNumber) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            PaymentDao paymentDao = daoFactory.createPaymentDao(connection);
            return paymentDao.findPaymentDetails(paymentNumber);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Optional.empty();
        }
    }

    public long getNumberOfRecords() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            PaymentDao paymentDao = daoFactory.createPaymentDao(connection);
            return paymentDao.getNumberOfRecords();
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return 0;
        }
    }

    public long getNumberOfSentFromMonAccRecords(int moneyAccountId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            PaymentDao paymentDao = daoFactory.createPaymentDao(connection);
            return paymentDao.getNumberOfRecordsBySenderMonAccId(moneyAccountId);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return 0;
        }
    }



}
