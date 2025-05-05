-- Replace the id here with the first user id you want to have ownership of the orders.
SET @userId1 = 1;
-- Replace the id here with the second user id you want to have ownership of the orders.
SET @userId2 = 2;

DELETE FROM web_order where id >= 1;
DELETE FROM product where id >= 1;

INSERT INTO product (name, description, price) VALUES ('ProductEx #1', 'ProductEx one description', 9.99);
INSERT INTO product (name, description, price) VALUES ('ProductEx #2', 'ProductEx two description', 49.99);

SET @product1 = 0;
SET @product2 = 0;

SELECT @product1 := id FROM product WHERE name = 'ProductEx #1';
SELECT @product2 := id FROM product WHERE name = 'ProductEx #2';

INSERT INTO web_order (user_id) VALUES (@userId1);
INSERT INTO web_order (user_id) VALUES (@userId2);

SET @order1 = 0;
SET @order2 = 0;

SELECT @order1 := id FROM web_order WHERE user_id = @userId1 ORDER BY id DESC LIMIT 1;
SELECT @order1 := id FROM web_order WHERE user_id = @userId2 ORDER BY id DESC LIMIT 1;