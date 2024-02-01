DROP DATABASE IF EXISTS food;
CREATE DATABASE food;
USE food;
-- Rest of your SQL statements


CREATE TABLE customer (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        customer_name VARCHAR(255),
                        customer_email_address VARCHAR(255)
);


CREATE TABLE restaurant (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            email VARCHAR(255)
);

CREATE TABLE menu_item (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           description VARCHAR(255),
                           image_link VARCHAR(255),
                           restaurant_id BIGINT,
                           price DECIMAL(10,2)
);

CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        customer_id BIGINT,
                        restaurant_id BIGINT,
                        order_date DATE,
                        FOREIGN KEY (customer_id) REFERENCES customer(id),
                        FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);
CREATE TABLE order_menu_items (
                             order_id BIGINT,
                             menu_item_id BIGINT,
                             quantity BIGINT,
                             PRIMARY KEY (order_id, menu_item_id),
                             FOREIGN KEY (order_id) REFERENCES orders(id),
                             FOREIGN KEY (menu_item_id) REFERENCES menu_item(id)
);
DELIMITER //
CREATE PROCEDURE InsertOrder(
    IN p_customer_id BIGINT,
    IN p_restaurant_id BIGINT,
    IN p_order_date DATE,
    IN p_items_json JSON
)
BEGIN
    DECLARE v_order_id INT;

    -- Insert into orders table
    INSERT INTO orders (customer_id, restaurant_id, order_date)
    VALUES (p_customer_id, p_restaurant_id, p_order_date);

    -- Get the last inserted order_id
    SET v_order_id = LAST_INSERT_ID();

    -- Process JSON array and insert into order_items table
    INSERT INTO order_menu_items (order_id, menu_item_id, quantity)
    SELECT v_order_id,
           items.menu_item_id,
           items.quantity
    FROM JSON_TABLE(p_items_json, '$[*]' COLUMNS (
        menu_item_id BIGINT PATH '$.menu_item_id',
        quantity BIGINT PATH '$.quantity'
        )) AS items;
END //
DELIMITER ;



INSERT INTO customer (customer_name, customer_email_address)
VALUES
    ('John Doe', 'John@example.com'),
    ('Ez P.', 'EZ.p@example.com'),
    ('Cat Will', 'cat@example.com'),
    ('miguel santos', 'miguel@example.com'),
    ('pearl g.', 'pearl@example.com');

-- Restaurants
INSERT INTO restaurant (name, email)
VALUES
    ('Cafe Delight', 'cafedelight@example.com'),
    ('Sushi Sensation', 'sushisensation@example.com'),
    ('Gourmet Grill', 'gourmetgrill@example.com'),
    ('Taco Ring', 'tacoring@example.com'),
    ('Bistro Bliss', 'bistrobliss@example.com');

-- Menu items for Restaurant 1
INSERT INTO menu_item (name, description, image_link, restaurant_id, price)
VALUES
    ('Burger', 'Delicious beef burger', 'https://example.com/burger.jpg', 1, 10.99),
    ('Pizza', 'Tasty pepperoni pizza', 'https://example.com/pizza.jpg', 1, 12.49),
    ('Pasta', 'Creamy Alfredo pasta', 'https://example.com/pasta.jpg', 1, 9.99),
    ('Salad', 'Fresh garden salad', 'https://example.com/salad.jpg', 1, 7.99),
    ('Soda', 'Refreshing cola', 'https://example.com/soda.jpg', 1, 2.49);

-- Menu items for Restaurant 2
INSERT INTO menu_item (name, description, image_link, restaurant_id, price)
VALUES
    ('Sushi', 'Assorted sushi platter', 'https://example.com/sushi.jpg', 2, 18.99),
    ('Tempura', 'Crispy tempura shrimp', 'https://example.com/tempura.jpg', 2, 14.99),
    ('Teriyaki Chicken', 'Grilled chicken with teriyaki sauce', 'https://example.com/teriyaki.jpg', 2, 15.99),
    ('Miso Soup', 'Traditional Japanese miso soup', 'https://example.com/miso.jpg', 2, 4.99),
    ('Green Tea Ice Cream', 'Matcha-flavored ice cream', 'https://example.com/greentea.jpg', 2, 6.99);

-- Menu items for Restaurant 3
INSERT INTO menu_item (name, description, image_link, restaurant_id, price)
VALUES
    ('Steak', 'Juicy ribeye steak', 'https://example.com/steak.jpg', 3, 22.99),
    ('Seafood Platter', 'Fresh seafood medley', 'https://example.com/seafood.jpg', 3, 26.99),
    ('Grilled Vegetables', 'Assorted grilled veggies', 'https://example.com/vegetables.jpg', 3, 9.99),
    ('Garlic Bread', 'Warm garlic breadsticks', 'https://example.com/garlicbread.jpg', 3, 5.49),
    ('Red Wine', 'Fine red wine', 'https://example.com/redwine.jpg', 3, 14.99);

-- Menu items for Restaurant 4
INSERT INTO menu_item (name, description, image_link, restaurant_id, price)
VALUES
    ('Taco', 'Spicy beef taco', 'https://example.com/taco.jpg', 4, 8.99),
    ('Enchiladas', 'Cheesy enchiladas', 'https://example.com/enchiladas.jpg', 4, 10.99),
    ('Burrito', 'Bean and cheese burrito', 'https://example.com/burrito.jpg', 4, 9.49),
    ('Guacamole', 'Freshly made guacamole', 'https://example.com/guacamole.jpg', 4, 4.99),
    ('Margarita', 'Classic margarita', 'https://example.com/margarita.jpg', 4, 7.99);

-- Menu items for Restaurant 5
INSERT INTO menu_item (name, description, image_link, restaurant_id, price)
VALUES
    ('Fried Chicken', 'Crispy fried chicken', 'https://example.com/friedchicken.jpg', 5, 11.99),
    ('Biscuits', 'Flaky biscuits with honey', 'https://example.com/biscuits.jpg', 5, 4.99),
    ('Macaroni and Cheese', 'Creamy mac and cheese', 'https://example.com/macandcheese.jpg', 5, 8.99),
    ('Coleslaw', 'Homemade coleslaw', 'https://example.com/coleslaw.jpg', 5, 3.99),
    ('Sweet Tea', 'Southern-style sweet tea', 'https://example.com/sweettea.jpg', 5, 2.99);

CALL InsertOrder(1, 1, '2024-01-10', '[{"menu_item_id": 1, "quantity": 2}, {"menu_item_id": 2, "quantity": 1}]');

DELIMITER //

CREATE PROCEDURE GetOrderDetails(IN p_order_id BIGINT)
BEGIN
    SELECT
        o.id AS order_id,
        r.name,
        r.id,
        o.order_date,
        c.customer_name,
        c.customer_email_address,
        JSON_ARRAYAGG(
                JSON_OBJECT(
                        'id', om.menu_item_id,
                        'name', mi.name,
                        'description', mi.description,
                        'price', mi.price,
                        'quantity', om.quantity

                )
        ) AS menu_items
    FROM
        orders o
            JOIN
        order_menu_items om ON o.id = om.order_id
            JOIN
        menu_item mi ON om.menu_item_id = mi.id
            JOIN
        customer c on o.customer_id = c.id
            JOIN
        restaurant r on r.id = o.restaurant_id
    WHERE
        o.id = p_order_id
    Group BY
        order_id, o.customer_id, o.restaurant_id, o.order_date;

END //

DELIMITER ;

CALL GetOrderDetails(1);

