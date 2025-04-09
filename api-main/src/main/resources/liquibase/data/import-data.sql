-- Dumping data for table db_cake_shop.tbl_group: ~2 rows (approximately)
INSERT INTO `tbl_group` (`id`, `description`, `is_system_role`, `kind`, `name`, `created_at`, `updated_at`)
VALUES ('beaaf975-0b71-11f0-93e5-0242ac110002', 'Administrator group', b'1', 1, 'Admin', '2025-03-28 01:12:40',
        '2025-03-28 01:12:40'),
       ('f5ca64e7-0b71-11f0-93e5-0242ac110002', 'Customer group', b'1', 2, 'Customer', '2025-03-28 01:14:13',
        '2025-03-28 01:14:13');

-- Dumping data for table db_cake_shop.tbl_account: ~0 rows (approximately)
INSERT INTO `tbl_account` (`id`, `username`, `password`, `is_active`, `email`, `avatar_path`, `group_id`, `created_at`,
                           `updated_at`)
VALUES ('cc6b725f-0b7f-11f0-93e5-0242ac110002', 'admin', '$2a$10$6Rdxpba/3kSoI8ofL7qbuOsHFGoeBXIGyzjySVxXeuLU4MlgCSJd6',
        b'1', 'admin@gmail.com', '', 'beaaf975-0b71-11f0-93e5-0242ac110002', '2025-03-28 02:53:16',
        '2025-03-28 02:53:16');

-- Dumping data for table db_cake_shop.tbl_permission: ~9 rows (approximately)
INSERT INTO `tbl_permission` (`id`, `action`, `code`, `name`, `description`, `created_at`, `updated_at`)
VALUES ('07d78809-0b80-11f0-93e5-0242ac110002', '/api/v1/category/update', 'CAT_UPD', 'Update category',
        'Update category', '2025-03-28 02:54:56', '2025-03-28 02:54:56'),
       ('33410c77-0d79-11f0-88b5-0242ac110002', '/api/v1/nation/create', 'NAT_CRE', 'Create nation', 'Create nation',
        '2025-03-30 15:11:05', '2025-03-30 15:11:05'),
       ('340bb59f-0b80-11f0-93e5-0242ac110002', '/api/v1/category/delete', 'CAT_DEL', 'Delete category',
        'Delete category', '2025-03-28 02:56:10', '2025-03-28 02:56:10'),
       ('3ba7c703-0c53-11f0-83d8-0242ac110002', '/api/v1/tag/delete', 'TAG_DEL', 'Delete tag', 'Delete tag',
        '2025-03-29 04:06:47', '2025-03-29 04:06:47'),
       ('3e00cba9-0c7d-11f0-83d8-0242ac110002', '/api/v1/product/create', 'PRO_CRE', 'Create product', 'Create product',
        '2025-03-29 09:07:29', '2025-03-29 09:07:29'),
       ('4898be2d-0c7d-11f0-83d8-0242ac110002', '/api/v1/product/update', 'PRO_UPD', 'Update product', 'Update product',
        '2025-03-29 09:07:47', '2025-03-29 09:07:47'),
       ('59d13e87-0d79-11f0-88b5-0242ac110002', '/api/v1/nation/update', 'NAT_UPD', 'Update nation', 'Update nation',
        '2025-03-30 15:12:09', '2025-03-30 15:12:09'),
       ('6b45edd9-0c7d-11f0-83d8-0242ac110002', '/api/v1/product/delete', 'PRO_DEL', 'Delete product', 'Delete product',
        '2025-03-29 09:08:45', '2025-03-29 09:08:45'),
       ('6cf0f02c-0d79-11f0-88b5-0242ac110002', '/api/v1/nation/delete', 'NAT_DEL', 'Delete nation', 'Delete nation',
        '2025-03-30 15:12:41', '2025-03-30 15:12:41'),
       ('8d273797-0c52-11f0-83d8-0242ac110002', '/api/v1/tag/create', 'TAG_CRE', 'Create tag', 'Create tag',
        '2025-03-29 04:01:54', '2025-03-29 04:01:54'),
       ('bbe39956-0c52-11f0-83d8-0242ac110002', '/api/v1/tag/update', 'TAG_UPD', 'Update tag', 'Update tag',
        '2025-03-29 04:03:12', '2025-03-29 04:03:12'),
       ('dc5e454f-0b7f-11f0-93e5-0242ac110002', '/api/v1/category/create', 'CAT_CRE', 'Create category',
        'Create category', '2025-03-28 02:53:43', '2025-03-28 02:53:43');

-- Dumping data for table db_cake_shop.tbl_permission_group: ~9 rows (approximately)
INSERT INTO `tbl_permission_group` (`group_id`, `permission_id`)
VALUES ('beaaf975-0b71-11f0-93e5-0242ac110002', '340bb59f-0b80-11f0-93e5-0242ac110002'),
       ('beaaf975-0b71-11f0-93e5-0242ac110002', '07d78809-0b80-11f0-93e5-0242ac110002'),
       ('beaaf975-0b71-11f0-93e5-0242ac110002', 'dc5e454f-0b7f-11f0-93e5-0242ac110002'),
       ('beaaf975-0b71-11f0-93e5-0242ac110002', '8d273797-0c52-11f0-83d8-0242ac110002'),
       ('beaaf975-0b71-11f0-93e5-0242ac110002', 'bbe39956-0c52-11f0-83d8-0242ac110002'),
       ('beaaf975-0b71-11f0-93e5-0242ac110002', '3ba7c703-0c53-11f0-83d8-0242ac110002'),
       ('beaaf975-0b71-11f0-93e5-0242ac110002', '3e00cba9-0c7d-11f0-83d8-0242ac110002'),
       ('beaaf975-0b71-11f0-93e5-0242ac110002', '4898be2d-0c7d-11f0-83d8-0242ac110002'),
       ('beaaf975-0b71-11f0-93e5-0242ac110002', '6b45edd9-0c7d-11f0-83d8-0242ac110002'),
       ('beaaf975-0b71-11f0-93e5-0242ac110002', '33410c77-0d79-11f0-88b5-0242ac110002'),
       ('beaaf975-0b71-11f0-93e5-0242ac110002', '59d13e87-0d79-11f0-88b5-0242ac110002'),
       ('beaaf975-0b71-11f0-93e5-0242ac110002', '6cf0f02c-0d79-11f0-88b5-0242ac110002');

-- Dumping data for table db_cake_shop.tbl_category: ~1 rows (approximately)
INSERT INTO `tbl_category` (`id`, `code`, `name`, `description`, `image`, `created_at`, `updated_at`)
VALUES ('7751d611-3b80-4cce-b8f6-1b3130d8c992', 'WEDDING_CAKE', 'Bánh Cưới',
        'Bánh cưới là biểu tượng ngọt ngào cho ngày trọng đại, được thiết kế tinh tế với nhiều tầng bánh và phong cách trang trí sang trọng. Mỗi chiếc bánh cưới không chỉ là món tráng miệng mà còn là điểm nhấn ấn tượng trong buổi lễ, thể hiện tình yêu, sự gắn kết và hạnh phúc của cặp đôi. Với nhiều kiểu dáng, màu sắc và hương vị khác nhau, bánh cưới mang đến sự hoàn hảo và lãng mạn cho khoảnh khắc thiêng liêng nhất.',
        'https://res.cloudinary.com/dcxgx3ott/image/upload/v1744210471/wedding-3_dlteib.webp',
        '2025-03-29 10:54:11', '2025-03-29 10:54:11'),
       ('bbee11f7-4420-4bbe-9150-c8790027e580', 'BIRTH_CAKE', 'Bánh Sinh Nhật',
        'Những chiếc bánh sinh nhật xinh đẹp, được trang trí tỉ mỉ và sáng tạo, là món quà ý nghĩa để gửi gắm yêu thương trong ngày đặc biệt. Với nhiều kiểu dáng, kích thước và chủ đề đa dạng, bánh sinh nhật không chỉ ngon miệng mà còn mang đến niềm vui và sự bất ngờ cho người nhận. Hãy chọn một chiếc bánh thật ấn tượng để lưu giữ những khoảnh khắc đáng nhớ cùng người thân và bạn bè.',
        'https://res.cloudinary.com/dcxgx3ott/image/upload/v1744209366/Best-Birthday-Cake-with-milk-chocolate-buttercream-SQUARE_x5yuqo.webp',
        '2025-04-04 12:15:28', '2025-04-04 12:15:28'),
       ('d187988e-5d2e-47e2-8ca2-be23e6de000f', 'SPONGE_CAKE', 'Bánh bông lan',
        'Bánh bông lan là một loại bánh mềm, xốp và nhẹ, thường được làm từ bột mì, trứng, đường và bơ. Với kết cấu bông xốp và vị ngọt nhẹ nhàng, bánh bông lan là món tráng miệng quen thuộc, có thể dùng kèm với trà, cà phê hoặc kết hợp với các loại kem, trái cây để tăng thêm hương vị. Có nhiều biến thể của bánh bông lan, từ bánh bông lan truyền thống, bánh bông lan cuộn đến bánh bông lan phô mai, mang lại sự phong phú và đa dạng cho người thưởng thức.',
        'https://res.cloudinary.com/dcxgx3ott/image/upload/v1744211244/sponge-2_fptwjd.webp',
        '2025-04-04 12:15:28', '2025-04-04 12:15:28'),
       ('e121da67-bd67-4d7c-ab82-4b84e785651e', 'CUP_CAKE', 'Cupcake',
        'Cupcake là những chiếc bánh nhỏ xinh, mềm mịn với lớp kem trang trí bắt mắt và hương vị phong phú. Chúng là lựa chọn hoàn hảo cho các buổi tiệc, quà tặng hay đơn giản là để thưởng thức vào những khoảnh khắc ngọt ngào trong ngày. Với nhiều kiểu dáng và nhân bánh đa dạng, cupcake luôn mang lại sự thú vị và bất ngờ cho người thưởng thức.',
        'https://res.cloudinary.com/dcxgx3ott/image/upload/v1744208115/product-10_q4cevx.webp',
        '2025-04-04 12:15:28', '2025-04-04 12:15:28');

-- Dumping data for table db_cake_shop.tbl_discount: ~1,003 rows (approximately)
INSERT INTO `tbl_discount` (`id`, `code`, `discount_percentage`, `end_date`, `start_date`, `created_at`, `updated_at`)
VALUES ('8bcfca3e-1556-11f0-8ac6-0242ac110002', 'DISCOUNT_5', 5, '2026-04-09 15:23:10.000000',
        '2025-04-09 15:23:10.000000', '2025-04-09 15:23:10.000000', '2025-04-09 15:23:10.000000'),
       ('8bcfd297-1556-11f0-8ac6-0242ac110002', 'DISCOUNT_10', 10, '2026-04-09 15:23:10.000000',
        '2025-04-09 15:23:10.000000', '2025-04-09 15:23:10.000000', '2025-04-09 15:23:10.000000'),
       ('8bcfd355-1556-11f0-8ac6-0242ac110002', 'DISCOUNT_20', 20, '2026-04-09 15:23:10.000000',
        '2025-04-09 15:23:10.000000', '2025-04-09 15:23:10.000000', '2025-04-09 15:23:10.000000');

-- Dumping data for table db_cake_shop.tbl_tag: ~1 rows (approximately)
INSERT INTO `tbl_tag` (`id`, `code`, `name`, `created_at`, `updated_at`)
VALUES ('375fd18f-6ee3-4325-8d1a-757974531f7e', 'COFFEE', 'Coffee', '2025-04-04 12:16:00.657000',
        '2025-04-04 12:16:00.657000'),
       ('4464e134-7974-4b7f-8d4d-41fb14eee46a', 'VANILLA', 'Vani', '2025-04-04 12:16:00.652000',
        '2025-04-04 12:16:00.652000'),
       ('5c79934c-0fc6-4e0c-9928-21d8b1ae56d7', 'CHEESE', 'Phô mai', '2025-04-04 12:16:00.651000',
        '2025-04-04 12:16:00.651000'),
       ('606ef0e9-5dd5-4165-b499-b64577f8f324', 'FRUIT', 'Trái cây', '2025-04-04 12:16:00.647000',
        '2025-04-04 12:16:00.647000'),
       ('8f1f608f-be88-467e-9a7a-12e6285ab72a', 'CHOCOLATE', 'Socola', '2025-03-29 11:07:29.341000',
        '2025-03-29 11:07:29.341000'),
       ('bd7cdc57-ec6b-4f89-b407-665539ac97a6', 'MATCHA', 'Matcha', '2025-04-04 12:16:00.658000',
        '2025-04-04 12:16:00.658000'),
       ('e5c796ad-0893-4dd5-a29b-23d49a3b3647', 'STRAWBERRY', 'Dâu', '2025-04-04 12:16:00.653000',
        '2025-04-04 12:16:00.653000');

-- Dumping data for table db_cake_shop.tbl_product: ~6 rows (approximately)
INSERT INTO `tbl_product` (`id`, `name`, `description`, `price`, `quantity`, `status`, `created_at`, `updated_at`, `category_id`, `discount_id`) VALUES
     ('7aa275b2-7c0d-4b59-a9c6-1eaa1ba16a07', 'Bông Lan Cacao Đậm Vị', 'Bông Lan Cacao đậm vị thơm béo.', 450000, 20, 1, '2025-04-09 23:02:41.002000', '2025-04-09 23:02:41.002000', 'd187988e-5d2e-47e2-8ca2-be23e6de000f', '8bcfd355-1556-11f0-8ac6-0242ac110002'),
     ('7c008281-a5e3-453d-9091-97224f849216', 'Hương Tình Yêu', 'Bánh cưới \'Hương Tình Yêu\' mang đến sự ngọt ngào và lãng mạn, với lớp kem mềm mịn cùng thiết kế hoa trang nhã, lý tưởng cho ngày trọng đại.', 525000, 10, 1, '2025-04-09 23:02:40.986000', '2025-04-09 23:02:40.986000', '7751d611-3b80-4cce-b8f6-1b3130d8c992', '8bcfd297-1556-11f0-8ac6-0242ac110002'),
     ('a68b26cd-e6b0-426b-a070-abf4c98182a2', 'Kem Mây Bồng Bềnh', 'Cupcake nhỏ gọn, dễ thương, vị ngọt dịu.', 15000, 40, 1, '2025-04-09 23:02:40.999000', '2025-04-09 23:02:40.999000', 'e121da67-bd67-4d7c-ab82-4b84e785651e', '8bcfca3e-1556-11f0-8ac6-0242ac110002'),
     ('affb5df4-e943-4540-b9e4-1b74bf1646c0', 'Cupcake vani truyền thống', 'Cupcake vani truyền thống, mềm mịn và ngọt nhẹ.', 15000, 25, 1, '2025-04-09 23:02:41.001000', '2025-04-09 23:02:41.001000', 'e121da67-bd67-4d7c-ab82-4b84e785651e', '8bcfca3e-1556-11f0-8ac6-0242ac110002'),
     ('b434f56a-bb32-4d3f-8ab7-c8ad09969d7b', 'Bông Lan Kem Dâu Hồng', 'Bông Lan Kem Dâu Hồng đậm vị thơm béo.', 550000, 20, 1, '2025-04-09 23:02:41.003000', '2025-04-09 23:02:41.003000', 'd187988e-5d2e-47e2-8ca2-be23e6de000f', '8bcfd297-1556-11f0-8ac6-0242ac110002'),
     ('d73bbb9e-e4f9-44e7-a826-cc0a5118b8cf', 'Socola Ấm Áp', 'Cupcake chocolate nhỏ gọn, dễ thương, vị ngọt dịu.', 15000, 50, 1, '2025-04-09 23:02:40.997000', '2025-04-09 23:02:40.997000', 'e121da67-bd67-4d7c-ab82-4b84e785651e', '8bcfca3e-1556-11f0-8ac6-0242ac110002');

-- Dumping data for table db_cake_shop.tbl_product_images: ~11 rows (approximately)
INSERT INTO `tbl_product_images` (`product_id`, `image_url`) VALUES
     ('7aa275b2-7c0d-4b59-a9c6-1eaa1ba16a07', 'https://res.cloudinary.com/dcxgx3ott/image/upload/v1744211374/sponge-3_qsse7y.png'),
     ('7c008281-a5e3-453d-9091-97224f849216', 'https://res.cloudinary.com/dcxgx3ott/image/upload/v1744210471/wedding-3_dlteib.webp'),
     ('7c008281-a5e3-453d-9091-97224f849216', 'https://res.cloudinary.com/dcxgx3ott/image/upload/v1744210338/wedding-1_robivv.webp'),
     ('7c008281-a5e3-453d-9091-97224f849216', 'https://res.cloudinary.com/dcxgx3ott/image/upload/v1744210509/wedding-2_mljufv.webp'),
     ('a68b26cd-e6b0-426b-a070-abf4c98182a2', 'https://res.cloudinary.com/dcxgx3ott/image/upload/v1744213475/cupcake-3_doaecs.webp'),
     ('a68b26cd-e6b0-426b-a070-abf4c98182a2', 'https://res.cloudinary.com/dcxgx3ott/image/upload/v1744213474/cupcake-2_vgtxpx.webp'),
     ('affb5df4-e943-4540-b9e4-1b74bf1646c0', 'https://res.cloudinary.com/dcxgx3ott/image/upload/v1744214185/cupcake-5_jrvzfq.webp'),
     ('b434f56a-bb32-4d3f-8ab7-c8ad09969d7b', 'https://res.cloudinary.com/dcxgx3ott/image/upload/v1744211374/sponge-3_qsse7y.png'),
     ('d73bbb9e-e4f9-44e7-a826-cc0a5118b8cf', 'https://res.cloudinary.com/dcxgx3ott/image/upload/v1744208115/product-10_q4cevx.webp'),
     ('d73bbb9e-e4f9-44e7-a826-cc0a5118b8cf', 'https://res.cloudinary.com/dcxgx3ott/image/upload/v1744213474/cupcake-4_whytdy.webp');

-- Dumping data for table db_cake_shop.tbl_product_tag: ~8 rows (approximately)
INSERT INTO `tbl_product_tag` (`product_id`, `tag_id`) VALUES
   ('7aa275b2-7c0d-4b59-a9c6-1eaa1ba16a07', '375fd18f-6ee3-4325-8d1a-757974531f7e'),
   ('7c008281-a5e3-453d-9091-97224f849216', '4464e134-7974-4b7f-8d4d-41fb14eee46a'),
   ('affb5df4-e943-4540-b9e4-1b74bf1646c0', '4464e134-7974-4b7f-8d4d-41fb14eee46a'),
   ('7aa275b2-7c0d-4b59-a9c6-1eaa1ba16a07', '5c79934c-0fc6-4e0c-9928-21d8b1ae56d7'),
   ('7c008281-a5e3-453d-9091-97224f849216', '5c79934c-0fc6-4e0c-9928-21d8b1ae56d7'),
   ('a68b26cd-e6b0-426b-a070-abf4c98182a2', '5c79934c-0fc6-4e0c-9928-21d8b1ae56d7'),
   ('b434f56a-bb32-4d3f-8ab7-c8ad09969d7b', '5c79934c-0fc6-4e0c-9928-21d8b1ae56d7'),
   ('a68b26cd-e6b0-426b-a070-abf4c98182a2', '606ef0e9-5dd5-4165-b499-b64577f8f324'),
   ('b434f56a-bb32-4d3f-8ab7-c8ad09969d7b', '606ef0e9-5dd5-4165-b499-b64577f8f324'),
   ('7aa275b2-7c0d-4b59-a9c6-1eaa1ba16a07', '8f1f608f-be88-467e-9a7a-12e6285ab72a'),
   ('a68b26cd-e6b0-426b-a070-abf4c98182a2', '8f1f608f-be88-467e-9a7a-12e6285ab72a'),
   ('d73bbb9e-e4f9-44e7-a826-cc0a5118b8cf', '8f1f608f-be88-467e-9a7a-12e6285ab72a'),
   ('b434f56a-bb32-4d3f-8ab7-c8ad09969d7b', 'e5c796ad-0893-4dd5-a29b-23d49a3b3647');