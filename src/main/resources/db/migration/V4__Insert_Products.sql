INSERT INTO PRODUCTS (des_name, des_description, vlr_price)
SELECT
    'Product ' || i AS des_name,
    CASE
        WHEN i % 2 = 0 THEN 'Description for product ' || i
        ELSE NULL
    END AS des_description,
    ROUND((RANDOM() * 990 + 10)::numeric, 2) AS vlr_price
FROM generate_series(1, 1000) AS t(i);