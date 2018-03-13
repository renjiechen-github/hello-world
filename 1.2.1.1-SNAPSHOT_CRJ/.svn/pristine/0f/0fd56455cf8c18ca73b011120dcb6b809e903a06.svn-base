drop table if exists owner_cancel_lease_order_type;

/*==============================================================*/
/* Table: owner_cancel_lease_order_type                         */
/*==============================================================*/
create table owner_cancel_lease_order_type
(
   type                 char(1) not null comment '类型',
   type_name            varchar(30) not null comment '类型名',
   type_name_en         varchar(30) comment '类型名 英文',
   comments             varchar(255) comment '备注',
   primary key (type)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

insert into owner_cancel_lease_order_type(type, type_name, type_name_en, comments) values ('A', '正常退租', 'normal', '正常退租');
insert into owner_cancel_lease_order_type(type, type_name, type_name_en, comments) values ('B', '违约退租', 'breakContract', '违约退租');



drop table if exists owner_cancel_lease_order;

/*==============================================================*/
/* Table: owner_cancel_lease_order                              */
/*==============================================================*/
create table owner_cancel_lease_order
(
   id                   int(9) not null auto_increment comment '主键',
   code                 varchar(30) not null comment '编码',
   work_order_id        int(9) not null comment '工单主键',
   take_house_order_id  int(9) not null comment '收房合约主键',
   staff_id             int(9) comment '处理员工主键',
   created_date         datetime not null comment '创建时间',
   appointment_date     datetime not null comment '退租时间',
   node_name            varchar(30) comment '当前环节名称',
   actual_dealer_id     int(9) comment '当前环节实际处理责任人',
   assigned_dealer_id   int(9) comment '当前环节待处理责任人，当前环节的指定人，未必一定由此人处理，也可能由角色中的其他人处理',
   assigned_dealer_role_id int(9) comment '当前环节待处理角色，默认值为-1，也就是没有角色来处理',
   type                 char(1) comment '退租类型',
   attr_catg_id         int(9) comment '属性目录主键',
   state                char(1) comment '状态',
   state_date           datetime not null comment '状态变更时间',
   update_person_id     int(9) comment '修改人主键',
   update_date          datetime comment '修改时间',
   comments             varchar(255) comment '备注',
   primary key (id),
   unique key UQ_code (code)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

alter table owner_cancel_lease_order add constraint FK_owner_cancel_lease_order_attr_catg_id foreign key (attr_catg_id)
      references attr_catg (id) on delete restrict on update restrict;

alter table owner_cancel_lease_order add constraint FK_owner_cancel_lease_order_state foreign key (state)
      references sub_order_state (state) on delete restrict on update restrict;

alter table owner_cancel_lease_order add constraint FK_owner_cancel_lease_order_type foreign key (type)
      references owner_cancel_lease_order_type (type) on delete restrict on update restrict;

alter table owner_cancel_lease_order add constraint FK_owner_cancel_lease_order_work_order_id foreign key (work_order_id)
      references work_order (id) on delete restrict on update restrict;



drop table if exists owner_cancel_lease_order_value;

/*==============================================================*/
/* Table: owner_cancel_lease_order_value                        */
/*==============================================================*/
create table owner_cancel_lease_order_value
(
   id                   int(9) not null auto_increment comment '主键',
   sub_order_id         int(9) not null comment '子订单（业主退租订单）',
   attr_id              int(9) not null comment '属性主键',
   attr_path            varchar(255) comment '由于属性目录下还可以继续配置目录，就导致可能父目录和子目录同时拥有相同的属性。所以使用路径来进行区别',
   text_input           varchar(4000) comment '界面输入项',
   attr_value_id        int(9) comment '属性可选项主键',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

alter table owner_cancel_lease_order_value add constraint FK_owner_cancel_lease_order_value_sub_order_id foreign key (sub_order_id)
      references owner_cancel_lease_order (id) on delete restrict on update restrict;
      

drop table if exists owner_cancel_lease_order_his;

/*==============================================================*/
/* Table: owner_cancel_lease_order_his                          */
/*==============================================================*/
create table owner_cancel_lease_order_his
(
   id                   int(9) not null comment '主键',
   priority             int(3) not null comment '优先级',
   code                 varchar(30) not null comment '编码',
   work_order_id        int(9) not null comment '工单主键',
   take_house_order_id  int(9) not null comment '收房合约主键',
   staff_id             int(9) comment '处理员工主键',
   created_date         datetime not null comment '创建时间',
   appointment_date     datetime not null comment '退租时间',
   node_name            varchar(30) comment '当前环节名称',
   actual_dealer_id     int(9) comment '当前环节实际处理责任人',
   assigned_dealer_id   int(9) comment '当前环节待处理责任人，当前环节的指定人，未必一定由此人处理，也可能由角色中的其他人处理',
   assigned_dealer_role_id int(9) comment '当前环节待处理角色，默认值为-1，也就是没有角色来处理',
   type                 char(1) comment '退租类型',
   attr_catg_id         int(9) comment '属性目录主键',
   state                char(1) comment '状态',
   state_date           datetime not null comment '状态变更时间',
   update_person_id     int(9) comment '修改人主键',
   update_date          datetime comment '修改时间',
   comments             varchar(255) comment '备注',
   primary key (priority, id)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

/* 插入业主退租属性目录 */
insert into attr_catg(name, code, type, state, state_date) values ('业主退租属性目录', 'OCL_ORDER_PROCESS', 'A', 'A', '2017-05-08 16:20:39');
insert into attr_catg_children (catg_id, child_catg_id, child_attr_id, priority) values ((select id from attr_catg where code = 'OCL_ORDER_PROCESS'), (select id from attr_catg where code = 'HOUSE_RELEASE'), null, 10);
insert into attr_catg_children (catg_id, child_catg_id, child_attr_id, priority) values ((select id from attr_catg where code = 'OCL_ORDER_PROCESS'), (select id from attr_catg where code = 'TAKE_ORDER'), null, 20);
insert into attr_catg_children (catg_id, child_catg_id, child_attr_id, priority) values ((select id from attr_catg where code = 'OCL_ORDER_PROCESS'), (select id from attr_catg where code = 'BUTLER_GET_HOME'), null, 30);
insert into attr_catg_children (catg_id, child_catg_id, child_attr_id, priority) values ((select id from attr_catg where code = 'OCL_ORDER_PROCESS'), (select id from attr_catg where code = 'RENTAL_ACCOUNT'), null, 40);
insert into attr_catg_children (catg_id, child_catg_id, child_attr_id, priority) values ((select id from attr_catg where code = 'OCL_ORDER_PROCESS'), (select id from attr_catg where code = 'MARKETING_EXECUTIVE_AUDIT'), null, 50);
insert into attr_catg_children (catg_id, child_catg_id, child_attr_id, priority) values ((select id from attr_catg where code = 'OCL_ORDER_PROCESS'), (select id from attr_catg where code = 'FINANCE_AUDIT'), null, 60);
insert into attr (name, code, mandatory, unit_id, type, state, state_date, comments) values ('业主退房原因', 'OCL_REASON', 'Y', null, 'F', 'A', '2017-05-10 20:20:00', null);
insert into attr_catg_children (catg_id, child_catg_id, child_attr_id, priority) values ((select id from attr_catg where code = 'BUTLER_GET_HOME'), null, (select id from attr where code = 'OCL_REASON'), 11);
/* 业主退租原因 */
insert into attr_value (attr_id, value, value_mask, priority) values ((select id from attr where code = 'OCL_REASON'), 'REASON_A', '房屋置换', 10);
insert into attr_value (attr_id, value, value_mask, priority) values ((select id from attr where code = 'OCL_REASON'), 'REASON_A', '物品损失', 20);
insert into attr_value (attr_id, value, value_mask, priority) values ((select id from attr where code = 'OCL_REASON'), 'REASON_A', '价格原因', 30);
insert into attr_value (attr_id, value, value_mask, priority) values ((select id from attr where code = 'OCL_REASON'), 'REASON_A', '我司服务不满意', 40);
insert into attr_value (attr_id, value, value_mask, priority) values ((select id from attr where code = 'OCL_REASON'), 'REASON_A', '到期退房', 50);
insert into attr_value (attr_id, value, value_mask, priority) values ((select id from attr where code = 'OCL_REASON'), 'REASON_A', '其他原因', 60);
/* 插入订单类型 */
insert into work_order_type (type, type_name, type_short_name, type_name_en, comments) values ('k', '业主退租订单', '业主退', 'ownercancelleaseorder', '业主退租订单');



