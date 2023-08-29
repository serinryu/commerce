INSERT INTO members (auth_id, auth_pw, role, name, phone, city, street)
VALUES
    ('testuser1', 'password1', 'USER', 'John Doe', '123-456-7890', 'Test City 1', 'Test Street 1'),
    ('testuser2', 'password2', 'USER', 'Jane Smith', '987-654-3210', 'Test City 2', 'Test Street 2'),
    ('adminuser', 'adminpass', 'ADMIN', 'Admin User', '555-555-5555', 'Admin City', 'Admin Street');

INSERT INTO delivery (city, street, status)
VALUES
    ('Delivery City 1', 'Delivery Street 1', 'READY_STATUS'),
    ('Delivery City 2', 'Delivery Street 2', 'SHIPPING_STATUS'),
    ('Delivery City 3', 'Delivery Street 3', 'COMPLETE_STATUS');

INSERT INTO categories (id, category_name)
VALUES
    (1, 'Electronics'),
    (2, 'Clothing'),
    (3, 'Books'),
    (4, 'Furniture'),
    (5, 'Beauty');

INSERT INTO items (id, image_path, name, price, stock_quantity, category_id, dtype)
VALUES
    (1, 'sample1.jpg', 'Sample Item 1', 1000, 10, 3, 'Book'),
    (2, 'sample2.jpg', 'Sample Item 2', 1500, 15, 4, 'Album'),
    (3, 'sample3.jpg', 'Sample Item 3', 2000, 20, 4, 'Album'),
    (4, 'sample4.jpg', 'Sample Item 4', 800, 5, 4, 'Album'),
    (5, 'sample5.jpg', 'Sample Item 5', 2500, 8, 4, 'Album'),
    (6, 'sample6.jpg', 'Sample Item 6', 1800, 12, 1, 'Movie');

INSERT INTO orders (id, total_amount, status, removed, removed_at, member_id, delivery_id)
VALUES
    (1, 35, 'ORDERED_STATUS', FALSE, NULL, 1, 1),
    (2, 50, 'ORDERED_STATUS', FALSE, NULL, 2, 2),
    (3, 12, 'ORDERED_STATUS', FALSE, NULL, 3, 3);

INSERT INTO cart (cart_id, member_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3);

INSERT INTO cart_line (cart_id, item_id, order_count, map_key)
VALUES
    (1, 1, 2, 1),
    (1, 2, 3, 2),
    (2, 3, 1, 3),
    (3, 4, 4, 4),
    (3, 5, 2, 5);



