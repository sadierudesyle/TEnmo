SELECT * FROM users

INSERT INTO transfers VALUES (DEFAULT, 2, 2, 2004, 2003, 100.00)

SELECT transfer_id, transfer_type_desc, transfer_status_desc,
q.username as recipient, amount, u.username as sender  FROM transfers AS t
JOIN accounts b on account_to = b.account_id
JOIN users q ON b.user_id = q.user_id
JOIN accounts a on account_from = a.account_id
JOIN users u ON a.user_id = u.user_id
JOIN transfer_statuses ts ON ts.transfer_status_id = t.transfer_status_id
JOIN transfer_types tt ON tt.transfer_type_id = t.transfer_type_id
WHERE t.transfer_id = 3003;

SELECT a.user_id, u.username FROM accounts a
JOIN  users u ON a.user_id = u.user_id
WHERE u.user_id <> 1001
ORDER BY u.username;