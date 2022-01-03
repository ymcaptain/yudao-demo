-- mall 菜单SQL
INSERT INTO `sys_menu`(`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`,
                                       `component`, `status`, `creator`, `create_time`, `updater`, `update_time`,
                                       `deleted`, `tenant_id`)
VALUES (1185, '商品管理', '', 1, 12, 0, '/mall', 'shopping', NULL, 0, '1', '2022-01-02 17:12:50', '1',
        '2022-01-02 17:12:50', b'0', 0);

-- mall 商品模块SQL
INSERT INTO `sys_menu`(`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`,
                                       `component`, `status`, `creator`, `create_time`, `updater`, `update_time`,
                                       `deleted`, `tenant_id`)
VALUES (1186, '商品规格键管理', '', 2, 0, 1185, 'product-attr-key', '', 'mall/product/attr/index', 0, '',
        '2022-01-02 17:13:21', '1', '2022-01-02 17:14:56', b'0', 0);

-- mall 商品模块 attr 权限SQL
INSERT INTO `sys_menu`(`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`,
                                       `component`, `status`, `creator`, `create_time`, `updater`, `update_time`,
                                       `deleted`, `tenant_id`)
VALUES (1187, '商品规格键查询', 'mall:product-attr-key:query', 3, 1, 1186, '', '', '', 0, '', '2022-01-02 17:13:21', '',
        '2022-01-02 17:13:21', b'0', 0);
INSERT INTO `sys_menu`(`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`,
                                       `component`, `status`, `creator`, `create_time`, `updater`, `update_time`,
                                       `deleted`, `tenant_id`)
VALUES (1188, '商品规格键创建', 'mall:product-attr-key:create', 3, 2, 1186, '', '', '', 0, '', '2022-01-02 17:13:21', '',
        '2022-01-02 17:13:21', b'0', 0);
INSERT INTO `sys_menu`(`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`,
                                       `component`, `status`, `creator`, `create_time`, `updater`, `update_time`,
                                       `deleted`, `tenant_id`)
VALUES (1189, '商品规格键更新', 'mall:product-attr-key:update', 3, 3, 1186, '', '', '', 0, '', '2022-01-02 17:13:21', '',
        '2022-01-02 17:13:21', b'0', 0);
INSERT INTO `sys_menu`(`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`,
                                       `component`, `status`, `creator`, `create_time`, `updater`, `update_time`,
                                       `deleted`, `tenant_id`)
VALUES (1190, '商品规格键删除', 'mall:product-attr-key:delete', 3, 4, 1186, '', '', '', 0, '', '2022-01-02 17:13:21', '',
        '2022-01-02 17:13:21', b'0', 0);
INSERT INTO `sys_menu`(`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`,
                                       `component`, `status`, `creator`, `create_time`, `updater`, `update_time`,
                                       `deleted`, `tenant_id`)
VALUES (1191, '商品规格键导出', 'mall:product-attr-key:export', 3, 5, 1186, '', '', '', 0, '', '2022-01-02 17:13:21', '',
        '2022-01-02 17:13:21', b'0', 0);
