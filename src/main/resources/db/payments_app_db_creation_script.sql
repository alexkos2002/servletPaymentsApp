-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema servlet_payments_app_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema servlet_payments_app_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `servlet_payments_app_db` DEFAULT CHARACTER SET utf8 ;
USE `servlet_payments_app_db` ;

-- -----------------------------------------------------
-- Table `servlet_payments_app_db`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servlet_payments_app_db`.`account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(30) NOT NULL,
  `email` VARCHAR(30) NOT NULL,
  `password` VARCHAR(16) NOT NULL,
  `active` TINYINT NOT NULL,
  `has_order_on_check` TINYINT NOT NULL,
  `has_blocked_account` TINYINT NOT NULL,
  PRIMARY KEY (`id`), 
  CONSTRAINT `username_unique` UNIQUE(`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servlet_payments_app_db`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servlet_payments_app_db`.`role` (
  `roles` ENUM('USER', 'ADMIN') NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servlet_payments_app_db`.`account_has_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servlet_payments_app_db`.`account_has_role` (
  `account_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`account_id`, `role_id`),
  INDEX `fk_account_has_role_role1_idx` (`role_id` ASC) VISIBLE,
  INDEX `fk_account_has_role_account_idx` (`account_id` ASC) VISIBLE,
  CONSTRAINT `fk_account_has_role_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `servlet_payments_app_db`.`account` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_account_has_role_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `servlet_payments_app_db`.`role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servlet_payments_app_db`.`money_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servlet_payments_app_db`.`money_account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `number` BIGINT(12) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `active` ENUM('ACTIVE', 'BLOCKED', 'UNLOCK_REQUESTED') NOT NULL,
  `sum_int` BIGINT NOT NULL,
  `sum_dec` INT NOT NULL,
  `cur_sum_available_int` BIGINT NOT NULL,
  `cur_sum_available_dec`INT NOT NULL,
  PRIMARY KEY (`id`), 
  CONSTRAINT `number_unique` UNIQUE(`number`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servlet_payments_app_db`.`credit_card`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servlet_payments_app_db`.`credit_card` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `number` BIGINT(16) NOT NULL,
  `cvv` INT(3) NOT NULL,
  `expire_date` DATE NOT NULL,
  `payment_system` ENUM('VISA', 'MASTERCARD') NOT NULL,
  `available_sum_int` BIGINT NOT NULL,
  `available_sum_dec` INT NOT NULL,
  `account_id` INT NOT NULL,
  `money_account_id` INT NOT NULL,
  PRIMARY KEY (`id`, `account_id`, `money_account_id`),
  INDEX `fk_credit_card_account1_idx` (`account_id` ASC) VISIBLE,
  INDEX `fk_credit_card_money_account1_idx` (`money_account_id` ASC) VISIBLE,
  CONSTRAINT `fk_credit_card_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `servlet_payments_app_db`.`account` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_credit_card_money_account1`
    FOREIGN KEY (`money_account_id`)
    REFERENCES `servlet_payments_app_db`.`money_account` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `number_unique` UNIQUE(`number`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servlet_payments_app_db`.`credit_card_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servlet_payments_app_db`.`credit_card_order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `desired_payment_system` ENUM('VISA', 'MASTERCARD') NOT NULL,
  `message` VARCHAR(200) NULL,
  `order_status` ENUM('ON_CHECK', 'CONFIRMED', 'REJECTED') NOT NULL,
  `account_id` INT NOT NULL,
  PRIMARY KEY (`id`, `account_id`),
  INDEX `fk_credit_card_order_account1_idx` (`account_id` ASC) VISIBLE,
  CONSTRAINT `fk_credit_card_order_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `servlet_payments_app_db`.`account` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `servlet_payments_app_db`.`payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servlet_payments_app_db`.`payment` (
  `number` INT NOT NULL,
  `payed_sum_int` BIGINT NOT NULL,
  `payed_sum_dec` INT NOT NULL,
  `comission_int` BIGINT NOT NULL,
  `comission_dec` INT NOT NULL,
  `assignment` VARCHAR(45) NOT NULL,
  `time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP,
  `status` ENUM('PREPARED', 'SENT') NOT NULL,
  `sender_money_account_id` INT NOT NULL,
  PRIMARY KEY (`number`, `sender_money_account_id`),
  INDEX `fk_payment_money_account1_idx` (`sender_money_account_id` ASC) VISIBLE,
  CONSTRAINT `fk_payment_money_account1`
    FOREIGN KEY (`sender_money_account_id`)
    REFERENCES `servlet_payments_app_db`.`money_account` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servlet_payments_app_db`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servlet_payments_app_db`.`transaction` (
  `moved_sum_int` BIGINT NOT NULL,
  `moved_sum_dec` INT NOT NULL,
  `receiver_money_account_id` INT NOT NULL,
  `payment_number` BIGINT NOT NULL,
  `sender_money_account_id` INT NOT NULL,
  PRIMARY KEY (`receiver_money_account_id`, `payment_number`, `sender_money_account_id`),
  INDEX `fk_transaction_money_account1_idx` (`receiver_money_account_id` ASC) VISIBLE,
  INDEX `fk_transaction_payment1_idx` (`payment_number` ASC, `sender_money_account_id` ASC) VISIBLE,
  CONSTRAINT `fk_transaction_money_account1`
    FOREIGN KEY (`receiver_money_account_id`)
    REFERENCES `servlet_payments_app_db`.`money_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transaction_payment1`
    FOREIGN KEY (`payment_number` , `sender_money_account_id`)
    REFERENCES `servlet_payments_app_db`.`payment` (`number` , `sender_money_account_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `servlet_payments_app_db`.`additional_properties`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servlet_payments_app_db`.`additional_properties` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`cur_master_card_num` INT NOT NULL,
    `cur_visa_card_num` INT NOT NULL,
    `cur_money_account_num` BIGINT NOT NULL,
    `cur_payment_num` BIGINT NOT NULL,
    PRIMARY KEY (`id`)
)
ENGINE = InnoDB;
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
