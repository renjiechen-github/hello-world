drop table if exists pay_order_type;

/*==============================================================*/
/* Table: pay_order_type                                        */
/*==============================================================*/
create table pay_order_type
(
   type                 char(1) not null comment '类型',
   type_name            varchar(30) not null comment '类型名',
   type_name_en         varchar(30) comment '类型名 英文',
   comments             varchar(255) comment '备注',
   primary key (type)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

insert into pay_order_type(type, type_name, type_name_en, comments) values ('A', '经纪人推荐支付订单', 'RecommendPayOrder', '经纪人推荐支付订单');

drop table if exists pay_order;

/*==============================================================*/
/* Table: pay_order                                             */
/*==============================================================*/
create table pay_order
(
   id                   int(9) not null auto_increment comment '主键',
   code                 varchar(30) not null comment '编码',
   work_order_id        int(9) not null comment '工单主键',
   type                 char(1) not null comment '支付订单类型',
   pay_ref_id           int(9) not null comment '关联支付信息表主键',
   created_date         datetime not null comment '创建时间',
   staff_id             int(9) comment '处理员工主键',
   appointment_date     datetime comment '预约时间',
   actual_dealer_id     int(9) comment '当前环节实际处理责任人',
   assigned_dealer_id   int(9) comment '当前环节指定责任人',
   assigned_dealer_role_id int(9) comment '当前环节指定处理角色',
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

alter table pay_order add constraint FK_pay_order_attr_catg_id foreign key (attr_catg_id)
      references attr_catg (id) on delete restrict on update restrict;

alter table pay_order add constraint FK_pay_order_state foreign key (state)
      references sub_order_state (state) on delete restrict on update restrict;

alter table pay_order add constraint FK_pay_order_type foreign key (type)
      references pay_order_type (type) on delete restrict on update restrict;

alter table pay_order add constraint FK_pay_order_work_order_id foreign key (work_order_id)
      references work_order (id) on delete restrict on update restrict;


drop table if exists pay_order_value;

/*==============================================================*/
/* Table: pay_order_value                                       */
/*==============================================================*/
create table pay_order_value
(
   id                   int(9) not null auto_increment comment '主键',
   sub_order_id         int(9) not null comment '支付订单主键',
   attr_id              int(9) not null comment '属性主键',
   attr_path            varchar(255) comment '由于属性目录下还可以继续配置目录，就导致可能父目录和子目录同时拥有相同的属性。所以使用路径来进行区别',
   text_input           varchar(4000) comment '界面输入项',
   attr_value_id        int(9) comment '属性可选项主键',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

alter table pay_order_value add constraint FK_pay_order_value_sub_order_id foreign key (sub_order_id)
      references pay_order (id) on delete restrict on update restrict;


drop table if exists pay_order_value_his;

/*==============================================================*/
/* Table: pay_order_value_his                                   */
/*==============================================================*/
create table pay_order_value_his
(
   id                   int(9) not null comment '主键',
   priority             int(3) not null comment '优先级',
   sub_order_id         int(9) not null comment '支付订单主键',
   attr_id              int(9) not null comment '属性主键',
   attr_path            varchar(255) comment '由于属性目录下还可以继续配置目录，就导致可能父目录和子目录同时拥有相同的属性。所以使用路径来进行区别',
   text_input           varchar(4000) comment '界面输入项',
   attr_value_id        int(9) comment '属性可选项主键',
   primary key (priority, id)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;


drop table if exists pay_order_his;

/*==============================================================*/
/* Table: pay_order_his                                         */
/*==============================================================*/
create table pay_order_his
(
   id                   int(9) not null comment '主键',
   priority             int(3) not null comment '优先级',
   code                 varchar(30) not null comment '编码',
   work_order_id        int(9) not null comment '工单主键',
   type                 char(1) not null comment '支付订单类型',
   pay_ref_id           int(9) not null comment '关联支付信息表主键',
   created_date         datetime not null comment '创建时间',
   staff_id             int(9) comment '处理员工主键',
   appointment_date     datetime comment '预约时间',
   actual_dealer_id     int(9) comment '当前环节实际处理责任人',
   assigned_dealer_id   int(9) comment '当前环节指定责任人',
   assigned_dealer_role_id int(9) comment '当前环节指定处理角色',
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

insert into work_order_type (type, type_name, type_short_name, type_name_en, comments) values ('L', '支付订单', '付', 'PayOrder', '支付订单');
insert into attr_catg (name, code, type, state, state_date) values ('支付订单属性目录', 'PAY_ORDER_PROCESS', 'A', 'A', '2017-05-23 11:00:00');
INSERT INTO attr_catg (name, code, type, state, state_date) VALUES ('运营审核', 'OPERATOR_AUDIT', 'A', 'A', '2017-06-02 11:00:00');
INSERT INTO attr (name, code, mandatory, unit_id, type, state, state_date, comments) VALUES ('收款人', 'PAYEE', 'N', NULL, 'A', 'A', '2017-06-02 11:00:00', NULL);
INSERT INTO attr_catg_children (catg_id, child_catg_id, child_attr_id, priority) VALUES ((select id from attr_catg where code = 'PAY_ORDER_PROCESS'), (select id from attr_catg where code = 'ORDER_INPUT'), NULL, 1);
INSERT INTO attr_catg_children (catg_id, child_catg_id, child_attr_id, priority) VALUES ((select id from attr_catg where code = 'PAY_ORDER_PROCESS'), (select id from attr_catg where code = 'OPERATOR_AUDIT'), NULL, 2);

INSERT INTO attr_catg_children (catg_id, child_catg_id, child_attr_id, priority) VALUES ((select id from attr_catg where code = 'ORDER_INPUT'), NULL, (select id from attr where code = 'BANK_CARD_NBR'), 1);
INSERT INTO attr_catg_children (catg_id, child_catg_id, child_attr_id, priority) VALUES ((select id from attr_catg where code = 'ORDER_INPUT'), NULL, (select id from attr where code = 'BANK_ADDRESS'), 2);
INSERT INTO attr_catg_children (catg_id, child_catg_id, child_attr_id, priority) VALUES ((select id from attr_catg where code = 'ORDER_INPUT'), NULL, (select id from attr where code = 'PAYEE'), 3);
INSERT INTO attr_catg_children (catg_id, child_catg_id, child_attr_id, priority) VALUES ((select id from attr_catg where code = 'ORDER_INPUT'), NULL, (select id from attr where code = 'COMMENTS'), 4);

INSERT INTO attr_catg_children (catg_id, child_catg_id, child_attr_id, priority) VALUES ((select id from attr_catg where code = 'OPERATOR_AUDIT'), NULL, (select id from attr where code = 'COMMENTS'), 1);
INSERT INTO attr_catg_children (catg_id, child_catg_id, child_attr_id, priority) VALUES ((select id from attr_catg where code = 'OPERATOR_AUDIT'), NULL, (select id from attr where code = 'PASSED'), 2);
/**
 * 运营角色
 * */
INSERT INTO yc_role_new_tab (role_id, role_name, state, role_decs) VALUES ('34', '运营', 1, NULL);
/**
 * 订单状态-订单审核
 */
INSERT INTO sub_order_state (state, state_name, state_name_en, hidden, comments) VALUES ('W', '订单审核', 'OrderAudit', 'N', '订单审核');
