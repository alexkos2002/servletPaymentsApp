insert into account(id, username, email, password, active, has_order_on_check, has_blocked_account) values(1, 'Oleksii', 'liashko2002@gmail.com', '20032002', true, false, false);
insert into role(id, roles) values(1, 'USER');
insert into role(id, roles) values(2, 'ADMIN');
insert into account_has_role(account_id, role_id) values(1, 2);
insert into additional_properties (cur_master_card_num, cur_visa_card_num, cur_money_account_num, cur_payment_num) values(0, 0, 0, 0);