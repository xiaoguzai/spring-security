# spring-security
## 创建数据库的语句
```sql
-- sgsecurity.sys_menu definition
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '菜单名',
  `path` varchar(200) DEFAULT NULL COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '是否删除（0未删除 1已删除）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单表';

INSERT INTO sgsecurity.sys_menu (menu_name,`path`,component,visible,status,perms,icon,create_by,create_time,update_by,update_time,del_flag,remark) VALUES
	 ('部门管理','dept','system/dept/index','0','0','system:dept:list','#',NULL,NULL,NULL,NULL,0,NULL),
	 ('测试','test','system/test/index','0','0','system:test:list','#',NULL,NULL,NULL,NULL,0,NULL);

-- sgsecurity.sys_role definition
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `role_key` varchar(100) DEFAULT NULL COMMENT '角色权限字符串',
  `status` char(1) DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `del_flag` int DEFAULT '0' COMMENT 'del_flag',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

INSERT INTO sgsecurity.sys_role (name,role_key,status,del_flag,create_by,create_time,update_by,update_time,remark) VALUES
	 ('CEO','ceo','0',0,NULL,NULL,NULL,NULL,NULL),
	 ('Coder','coder','0',0,NULL,NULL,NULL,NULL,NULL),
	 (NULL,NULL,'0',0,NULL,NULL,NULL,NULL,NULL);

-- sgsecurity.sys_role_menu definition
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `menu_id` bigint NOT NULL DEFAULT '0' COMMENT '菜单id',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO sgsecurity.sys_role_menu (menu_id) VALUES
	 (1),
	 (2),
	 (2);

-- sgsecurity.sys_user definition

CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `nick_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '昵称',
  `password` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `phonenumber` varchar(32) DEFAULT NULL COMMENT '手机号',
  `sex` char(1) DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
  `user_type` char(1) NOT NULL DEFAULT '1' COMMENT '用户类型（0管理员，1普通用户）',
  `create_by` bigint DEFAULT NULL COMMENT '创建人的用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

INSERT INTO sgsecurity.sys_user (user_name,nick_name,password,status,email,phonenumber,sex,avatar,user_type,create_by,create_time,update_by,update_time,del_flag) VALUES	 ('sg','sg','$2a$10$hEtDd0gl.twQBoqhg3bS6.rt55H2msPGCrclF02uono0pHzvzZjma','0',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,0),
('sg2','sg','$2a$10$qQfrFt8kpln4ETRxSN.Rgu/N1VPGzV/cjWry3JVwNWxs6V.KY9Y8O','0',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,0);

-- sgsecurity.sys_user_before definition
CREATE TABLE `sys_user_before` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `nick_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '昵称',
  `password` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `phonenumber` varchar(32) DEFAULT NULL COMMENT '手机号',
  `sex` char(1) DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
  `user_type` char(1) NOT NULL DEFAULT '1' COMMENT '用户类型（0管理员，1普通用户）',
  `create_by` bigint DEFAULT NULL COMMENT '创建人的用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111112 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

INSERT INTO sgsecurity.sys_user_before (user_name,nick_name,password,status,email,phonenumber,sex,avatar,user_type,create_by,create_time,update_by,update_time,del_flag) VALUES
('xiaomazai','xiaomazai','$2a$10$VDFx9Khqpo4FAkx/NZLL3uZO0PcBZekL3AU5JtzJuuxbn2emZUCUK','0',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,0);

-- sgsecurity.sys_user_role definition
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `role_id` bigint NOT NULL DEFAULT '0' COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO sgsecurity.sys_user_role (role_id) VALUES
	 (1),
	 (2),
	 (2);
```
## 各个接口图片
### 接口1.http://localhost:8888/user/login
![image](https://github.com/xiaoguzai/spring-security/assets/31726901/fc1ae5c1-7d2e-4a86-9b3c-264fd6fb7e77)
![image](https://github.com/xiaoguzai/spring-security/assets/31726901/f9a93633-4a64-445b-b64b-a00756199eac)
### 接口2.http://localhost:8888/hello 注意接口1/user/login得到的接口放在hello接口的headers中
![image](https://github.com/xiaoguzai/spring-security/assets/31726901/d36e3319-c4ff-407a-a3f8-ffb123ddad9c)
![image](https://github.com/xiaoguzai/spring-security/assets/31726901/724ed6c8-6930-4c6d-861e-700ab08291ef)
### 接口3.http://localhost:8888/testCors 注意接口1/user/login得到的接口放在testCors接口的headers中
![image](https://github.com/xiaoguzai/spring-security/assets/31726901/a037dd6c-db16-48c3-b726-3d4ce5d3fb8e)
![image](https://github.com/xiaoguzai/spring-security/assets/31726901/f7112bc2-9ea3-480f-81ab-f9c40c6fa3b8)
### 接口4.http://localhost:8888/user/logout 注意接口1/user/login得到的接口放在/user/logout接口的headers中
![image](https://github.com/xiaoguzai/spring-security/assets/31726901/971e05ec-33bd-4c78-801a-49e64305caa8)
![image](https://github.com/xiaoguzai/spring-security/assets/31726901/43819273-5050-47a0-b738-fdde2ccb068d)


## 注意数据库的表以及端口号根据程序进行响应的调整！！！
