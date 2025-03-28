-- Dumping data for table db_cake_shop.tbl_group: ~2 rows (approximately)
INSERT INTO `tbl_group` (`id`, `description`, `is_system_role`, `kind`, `name`, `created_at`, `updated_at`) VALUES
    ('beaaf975-0b71-11f0-93e5-0242ac110002', 'Administrator group', b'1', 1, 'Admin', '2025-03-28 01:12:40', '2025-03-28 01:12:40'),
    ('f5ca64e7-0b71-11f0-93e5-0242ac110002', 'Customer group', b'1', 2, 'Customer', '2025-03-28 01:14:13', '2025-03-28 01:14:13');

-- Dumping data for table db_cake_shop.tbl_account: ~0 rows (approximately)
INSERT INTO `tbl_account` (`id`, `username`, `password`, `is_active`, `email`, `avatar_path`, `group_id`, `created_at`, `updated_at`) VALUES
    ('cc6b725f-0b7f-11f0-93e5-0242ac110002', 'admin', '$2a$10$6Rdxpba/3kSoI8ofL7qbuOsHFGoeBXIGyzjySVxXeuLU4MlgCSJd6', b'1', 'admin@gmail.com', '', 'beaaf975-0b71-11f0-93e5-0242ac110002', '2025-03-28 02:53:16', '2025-03-28 02:53:16');

-- Dumping data for table db_cake_shop.tbl_permission: ~0 rows (approximately)
INSERT INTO `tbl_permission` (`id`, `action`, `code`, `name`, `description`, `created_at`, `updated_at`) VALUES
     ('07d78809-0b80-11f0-93e5-0242ac110002', '/api/v1/category/update', 'CAT_UPD', 'Update category', 'Update category', '2025-03-28 02:54:56', '2025-03-28 02:54:56'),
     ('340bb59f-0b80-11f0-93e5-0242ac110002', '/api/v1/category/delete', 'CAT_DEL', 'Delete category', 'Delete category', '2025-03-28 02:56:10', '2025-03-28 02:56:10'),
     ('dc5e454f-0b7f-11f0-93e5-0242ac110002', '/api/v1/category/create', 'CAT_CRE', 'Create category', 'Create category', '2025-03-28 02:53:43', '2025-03-28 02:53:43');

-- Dumping data for table db_cake_shop.tbl_permission_group: ~0 rows (approximately)
INSERT INTO `tbl_permission_group` (`group_id`, `permission_id`) VALUES
     ('beaaf975-0b71-11f0-93e5-0242ac110002', '340bb59f-0b80-11f0-93e5-0242ac110002'),
     ('beaaf975-0b71-11f0-93e5-0242ac110002', '07d78809-0b80-11f0-93e5-0242ac110002'),
     ('beaaf975-0b71-11f0-93e5-0242ac110002', 'dc5e454f-0b7f-11f0-93e5-0242ac110002');