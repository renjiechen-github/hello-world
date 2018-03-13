-- 创建订单查询时，对于步骤时长的超限定义
insert into yc_systemparameter_tab (`key`, name, value, `type`, rule, `desc`) values ('ORDER_OVERLENGTH_LIMIT','订单超长时间界限节点（小时）','48','0',null,'只能配置大于0的正整数');

-- 投诉订单创建，增加图片
alter table complaint_order add image_url varchar(4000) NULL COMMENT '图片地址' ;

-- 其他订单创建，增加图片
alter table other_order add image_url varchar(4000) NULL COMMENT '图片地址';

-- 评论增加图片
alter table order_commentary add image_url varchar(4000) NULL COMMENT '图片地址';

insert into yc_role_new_tab (role_id, role_name, state, role_decs) values('36', '团队负责人', 1, '团队负责人');

-- 团队管理角色
INSERT INTO cf_role (id, name, role_desc, create_id, modify_id, modify_date, team_id) VALUES(48, '团队管理', '团队管理', 545454, 545480, '2017-08-17 10:28:39.000', 1);

-- 创建数据权限表
DROP TABLE IF EXISTS `yc_data_permission`;
CREATE TABLE `yc_data_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NOT NULL COMMENT '角色主键',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `creater_id` int(11) NOT NULL COMMENT '创建人id',
  `update_date` datetime DEFAULT NULL COMMENT '最后一次修改时间，第一次为创建时间',
  `type_id` int(11) NOT NULL COMMENT '权限类型id（1：订单；2：合约）',
  `sub_type_id` varchar(2) NOT NULL COMMENT '子类型id。订单：（A：退租；B：保洁；C：投诉；D：看房；E：入住问题；F：其他；G：业主维修；H：维修；I：例行保洁；J：经纪人签约；K：业务退租；L：支付）。合约：（A：业主；B：租客）',
  `sub_type_name` varchar(50) DEFAULT NULL COMMENT '子订单中文名称',
  `query_permission` int(2) DEFAULT NULL COMMENT '查询权限（1：可查询；0：不可查询）',
  `add_permission` int(2) DEFAULT NULL COMMENT '新增权限（1：可查询；0：不可查询）',
  `update_permission` int(2) DEFAULT NULL COMMENT '修改权限（1：可查询；0：不可查询）',
  `assign_permission` int(2) DEFAULT NULL COMMENT '指派权限（1：可查询；0：不可查询）',
  `close_permission` int(2) DEFAULT NULL COMMENT '关闭权限（1：可查询；0：不可查询）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='数据权限表'