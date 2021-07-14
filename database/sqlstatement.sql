UPDATE accounts SET balance = balance + 20
WHERE account_id = 2001

INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)
VALUES (2, 2, (SELECT account_id FROM accounts WHERE user_id = 1001), (SELECT account_id FROM accounts WHERE user_id = 1002), 55)

select * FROM accounts

select * FROM transfers;
select * FROM accounts;
SELECT account_id FROM accounts WHERE user_id = 1001

