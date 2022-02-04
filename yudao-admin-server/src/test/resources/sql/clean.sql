-- inf 开头的 DB
DELETE FROM "inf_config";
DELETE FROM "inf_file";
DELETE FROM "inf_job";
DELETE FROM "inf_job_log";
DELETE FROM "inf_api_access_log";
DELETE FROM "inf_api_error_log";

-- sys 开头的 DB
DELETE FROM "sys_dept";
DELETE FROM "sys_dict_data";
DELETE FROM "sys_role";
DELETE FROM "sys_role_menu";
DELETE FROM "sys_menu";
DELETE FROM "sys_user_role";
DELETE FROM "sys_dict_type";
DELETE FROM "sys_user_session";
DELETE FROM "sys_post";
DELETE FROM "sys_login_log";
DELETE FROM "sys_operate_log";
DELETE FROM "sys_user";
DELETE FROM "sys_sms_channel";
DELETE FROM "sys_sms_template";
DELETE FROM "sys_sms_log";
DELETE FROM "sys_error_code";
DELETE FROM "sys_social_user";
DELETE FROM "sys_tenant";

-- pay 开头的 DB
DELETE FROM pay_merchant;
DELETE FROM pay_app;
DELETE FROM pay_channel;
DELETE FROM pay_order;
DELETE FROM pay_refund;


-- mall 模块
DELETE FROM mall_product_attr_key;
DELETE FROM mall_product_attr_value;
DELETE FROM mall_product_brand;
DELETE FROM  mall_product_category;

-- bpm 开头的 DB
DELETE FROM "bpm_form";
DELETE FROM "bpm_user_group";
