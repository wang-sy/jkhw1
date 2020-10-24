DROP DATABASE IF EXISTS CQU_SSO_SYSTEM;

CREATE DATABASE CQU_SSO_SYSTEM;

USE CQU_SSO_SYSTEM;

-- encoding = utf8

-- ----------------------------
-- 1、小组表
-- ----------------------------
DROP TABLE IF EXISTS user_table;
CREATE TABLE user_table (
    user_phone_number       VARCHAR(13)                                 COMMENT '用户手机号',
    user_password           VARCHAR(30)                                 COMMENT '用户密码',
    user_nick_name          VARCHAR(255)    DEFAULT ''                  COMMENT '用户的昵称',
    CONSTRAINT pk_user_phone_number PRIMARY KEY (user_phone_number)
) COMMENT = '用户账号密码及其基本信息表';


-- 插入一些用户

INSERT INTO user_table (user_phone_number, user_password, user_nick_name) VALUES ("13969003119", "wangsy1990085", "王赛宇");
INSERT INTO user_table (user_phone_number, user_password, user_nick_name) VALUES ("13969003120", "123456", "李峻宇");
INSERT INTO user_table (user_phone_number, user_password, user_nick_name) VALUES ("13969003121", "123456", "方植滨");


-- 2、令牌表
-- ----------------------------
DROP TABLE IF EXISTS token_table;
CREATE TABLE token_table (
    user_phone_number       VARCHAR(13)                                 COMMENT '用户手机号',
    app_id                  VARCHAR(30)                                 COMMENT '登录平台id',
    user_token              VARCHAR(255)                                COMMENT '分配的token',
    CONSTRAINT pk_user_token PRIMARY KEY (user_token),
    CONSTRAINT fk_user_phone_number FOREIGN KEY (user_phone_number) REFERENCES user_table(user_phone_number) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT = '用户令牌表';