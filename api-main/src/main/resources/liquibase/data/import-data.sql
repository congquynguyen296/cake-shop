-- Dumping data for table db_cake_shop.tbl_group: ~2 rows (approximately)
INSERT INTO `tbl_group` (`id`, `description`, `is_system_role`, `kind`, `name`, `created_at`, `updated_at`) VALUES
    ('beaaf975-0b71-11f0-93e5-0242ac110002', 'Administrator group', b'1', 1, 'Admin', '2025-03-28 01:12:40', '2025-03-28 01:12:40'),
    ('f5ca64e7-0b71-11f0-93e5-0242ac110002', 'Customer group', b'1', 2, 'Customer', '2025-03-28 01:14:13', '2025-03-28 01:14:13');

-- Dumping data for table db_cake_shop.tbl_account: ~0 rows (approximately)
INSERT INTO `tbl_account` (`id`, `username`, `password`, `is_active`, `email`, `avatar_path`, `group_id`, `created_at`, `updated_at`) VALUES
    ('cc6b725f-0b7f-11f0-93e5-0242ac110002', 'admin', '$2a$10$6Rdxpba/3kSoI8ofL7qbuOsHFGoeBXIGyzjySVxXeuLU4MlgCSJd6', b'1', 'admin@gmail.com', '', 'beaaf975-0b71-11f0-93e5-0242ac110002', '2025-03-28 02:53:16', '2025-03-28 02:53:16');

-- Dumping data for table db_cake_shop.tbl_permission: ~9 rows (approximately)
INSERT INTO `tbl_permission` (`id`, `action`, `code`, `name`, `description`, `created_at`, `updated_at`) VALUES
     ('07d78809-0b80-11f0-93e5-0242ac110002', '/api/v1/category/update', 'CAT_UPD', 'Update category', 'Update category', '2025-03-28 02:54:56', '2025-03-28 02:54:56'),
     ('33410c77-0d79-11f0-88b5-0242ac110002', '/api/v1/nation/create', 'NAT_CRE', 'Create nation', 'Create nation', '2025-03-30 15:11:05', '2025-03-30 15:11:05'),
     ('340bb59f-0b80-11f0-93e5-0242ac110002', '/api/v1/category/delete', 'CAT_DEL', 'Delete category', 'Delete category', '2025-03-28 02:56:10', '2025-03-28 02:56:10'),
     ('3ba7c703-0c53-11f0-83d8-0242ac110002', '/api/v1/tag/delete', 'TAG_DEL', 'Delete tag', 'Delete tag', '2025-03-29 04:06:47', '2025-03-29 04:06:47'),
     ('3e00cba9-0c7d-11f0-83d8-0242ac110002', '/api/v1/product/create', 'PRO_CRE', 'Create product', 'Create product', '2025-03-29 09:07:29', '2025-03-29 09:07:29'),
     ('4898be2d-0c7d-11f0-83d8-0242ac110002', '/api/v1/product/update', 'PRO_UPD', 'Update product', 'Update product', '2025-03-29 09:07:47', '2025-03-29 09:07:47'),
     ('59d13e87-0d79-11f0-88b5-0242ac110002', '/api/v1/nation/update', 'NAT_UPD', 'Update nation', 'Update nation', '2025-03-30 15:12:09', '2025-03-30 15:12:09'),
     ('6b45edd9-0c7d-11f0-83d8-0242ac110002', '/api/v1/product/delete', 'PRO_DEL', 'Delete product', 'Delete product', '2025-03-29 09:08:45', '2025-03-29 09:08:45'),
     ('6cf0f02c-0d79-11f0-88b5-0242ac110002', '/api/v1/nation/delete', 'NAT_DEL', 'Delete nation', 'Delete nation', '2025-03-30 15:12:41', '2025-03-30 15:12:41'),
     ('8d273797-0c52-11f0-83d8-0242ac110002', '/api/v1/tag/create', 'TAG_CRE', 'Create tag', 'Create tag', '2025-03-29 04:01:54', '2025-03-29 04:01:54'),
     ('bbe39956-0c52-11f0-83d8-0242ac110002', '/api/v1/tag/update', 'TAG_UPD', 'Update tag', 'Update tag', '2025-03-29 04:03:12', '2025-03-29 04:03:12'),
     ('dc5e454f-0b7f-11f0-93e5-0242ac110002', '/api/v1/category/create', 'CAT_CRE', 'Create category', 'Create category', '2025-03-28 02:53:43', '2025-03-28 02:53:43');

-- Dumping data for table db_cake_shop.tbl_permission_group: ~9 rows (approximately)
INSERT INTO `tbl_permission_group` (`group_id`, `permission_id`) VALUES
     ('beaaf975-0b71-11f0-93e5-0242ac110002', '340bb59f-0b80-11f0-93e5-0242ac110002'),
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
INSERT INTO `tbl_category` (`id`, `code`, `name`, `description`, `image`, `created_at`, `updated_at`) VALUES
    ('7751d611-3b80-4cce-b8f6-1b3130d8c992', 'WEDDING_CAKE', 'Bánh Cưới', 'Những chiếc bánh sinh nhật ngọt ngào với nhiều hương vị hấp dẫn.', 'https://th.bing.com/th/id/OIP.J96rcY0GJUH7bg6UpMcmNQHaHH?w=200&h=193&c=7&r=0&o=5&dpr=1.1&pid=1.7', '2025-03-29 10:54:11', '2025-03-29 10:54:11');

-- Dumping data for table db_cake_shop.tbl_product: ~1 rows (approximately)
INSERT INTO `tbl_product` (`id`, `name`, `description`, `price`, `quantity`, `status`, `created_at`, `updated_at`, `category_id`) VALUES
    ('c850eb1f-4b4e-4ca8-9c22-0b65240c0428', 'Bánh Cưới', 'Những chiếc bánh sinh nhật ngọt ngào với nhiều hương vị hấp dẫn.', 100000, 100, 1, '2025-03-29 19:10:23.259000', '2025-03-29 19:10:23.259000', '7751d611-3b80-4cce-b8f6-1b3130d8c992');

-- Dumping data for table db_cake_shop.tbl_product_images: ~1 rows (approximately)
INSERT INTO `tbl_product_images` (`product_id`, `image_url`) VALUES
    ('c850eb1f-4b4e-4ca8-9c22-0b65240c0428', 'https://th.bing.com/th/id/OIP.J96rcY0GJUH7bg6UpMcmNQHaHH?w=200&h=193&c=7&r=0&o=5&dpr=1.1&pid=1.7');

-- Dumping data for table db_cake_shop.tbl_tag: ~1 rows (approximately)
INSERT INTO `tbl_tag` (`id`, `code`, `name`, `created_at`, `updated_at`) VALUES
    ('8f1f608f-be88-467e-9a7a-12e6285ab72a', 'CHOCOLATE', 'Socola', '2025-03-29 11:07:29.341000', '2025-03-29 11:07:29.341000');

-- Dumping data for table db_cake_shop.tbl_product_tag: ~1 rows (approximately)
INSERT INTO `tbl_product_tag` (`product_id`, `tag_id`) VALUES
    ('c850eb1f-4b4e-4ca8-9c22-0b65240c0428', '8f1f608f-be88-467e-9a7a-12e6285ab72a');