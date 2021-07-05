INSERT INTO users (user_id, username, password_hash)
VALUES (1001,'Bart Simpson','THIS REPRESNETS A PASSWROD HASH'),
       (1002,'Lisa Simpson','THIS REPRESNETS A PASSWROD HASH'),
       (1003,'Maggie Simpson','THIS REPRESNETS A PASSWROD HASH'),
       (1004,'Homer Simpson','THIS REPRESNETS A PASSWROD HASH'),
       (1005,'Marge Simpson','THIS REPRESNETS A PASSWROD HASH'),
       (1006,'Brian Griffin','THIS REPRESNETS A PASSWROD HASH'),
       (1007,'Stewie Griffin','THIS REPRESNETS A PASSWROD HASH'),
       (1008,'Meg Griffin','THIS REPRESNETS A PASSWROD HASH'),
       (1009,'Chris Griffin','THIS REPRESNETS A PASSWROD HASH');

INSERT INTO accounts (account_id, user_id, balance)
VALUES (2001, 1001, 100.00),
        (2002, 1002, 200.00),
        (2003, 1003, 300.00),
        (2004, 1004, 400.00),
        (2005, 1005, 500.00),
        (2006, 1006, 600.00),
        (2007, 1007, 700.00),
        (2008, 1008, 800.00),
        (2009, 1009, 900.00);

INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount)
VALUES (3001, 2, 2, 2001, 2002, 75.00),
       (3002, 2, 2, 2002, 2001, 97.38),
       (3003, 2, 2, 2003, 2004, 22.25),
       (3004, 2, 2, 2006, 2007, 198.32);



