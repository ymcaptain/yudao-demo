-- 菜单 SQL
INSERT INTO `system_menu`(
    `name`, `permission`, `menu_type`, `sort`, `parent_id`,
    `path`, `icon`, `component`, `status`
)
VALUES (
    '数据大屏管理', '', 2, 0, 1245,
    'screen', '', 'design/screen/index', 0
);

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
INSERT INTO `system_menu`(
    `name`, `permission`, `menu_type`, `sort`, `parent_id`,
    `path`, `icon`, `component`, `status`
)
VALUES (
    '数据大屏查询', 'design:screen:query', 3, 1, @parentId,
    '', '', '', 0
);
INSERT INTO `system_menu`(
    `name`, `permission`, `menu_type`, `sort`, `parent_id`,
    `path`, `icon`, `component`, `status`
)
VALUES (
    '数据大屏创建', 'design:screen:create', 3, 2, @parentId,
    '', '', '', 0
);
INSERT INTO `system_menu`(
    `name`, `permission`, `menu_type`, `sort`, `parent_id`,
    `path`, `icon`, `component`, `status`
)
VALUES (
    '数据大屏更新', 'design:screen:update', 3, 3, @parentId,
    '', '', '', 0
);
INSERT INTO `system_menu`(
    `name`, `permission`, `menu_type`, `sort`, `parent_id`,
    `path`, `icon`, `component`, `status`
)
VALUES (
    '数据大屏删除', 'design:screen:delete', 3, 4, @parentId,
    '', '', '', 0
);
INSERT INTO `system_menu`(
    `name`, `permission`, `menu_type`, `sort`, `parent_id`,
    `path`, `icon`, `component`, `status`
)
VALUES (
    '数据大屏导出', 'design:screen:export', 3, 5, @parentId,
    '', '', '', 0
);
