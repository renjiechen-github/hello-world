drop table if exists repair_order_value_his;

/*==============================================================*/
/* Table: repair_order_value_his                                */
/*==============================================================*/
create table repair_order_value_his
(
   id                   int(9) not null comment '主键',
   priority             int(3) not null comment '优先级',
   sub_order_id         int(9) not null comment '维修订单主键',
   attr_id              int(9) not null comment '属性主键',
   attr_path            varchar(255) comment '由于属性目录下还可以继续配置目录，就导致可能父目录和子目录同时拥有相同的属性。所以使用路径来进行区别',
   text_input           varchar(4000) comment '界面输入项',
   attr_value_id        int(9) comment '属性可选项主键',
   primary key (id, priority)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

drop table if exists cancel_lease_order_value_his;

/*==============================================================*/
/* Table: cancel_lease_order_value_his                          */
/*==============================================================*/
create table cancel_lease_order_value_his
(
   id                   int(9) not null comment '主键',
   priority             int(3) not null comment '优先级',
   sub_order_id         int(9) not null comment '退租单主键',
   attr_id              int(9) not null comment '属性主键',
   attr_path            varchar(255) comment '由于属性目录下还可以继续配置目录，就导致可能父目录和子目录同时拥有相同的属性。所以使用路径来进行区别',
   text_input           varchar(4000) comment '界面输入项',
   attr_value_id        int(9) comment '属性可选项主键',
   primary key (id, priority)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

drop table if exists house_looking_order_value_his;

/*==============================================================*/
/* Table: house_looking_order_value_his                         */
/*==============================================================*/
create table house_looking_order_value_his
(
   id                   int(9) not null comment '主键',
   priority             int(3) not null comment '优先级',
   sub_order_id         int(9) not null comment '看房单主键',
   attr_id              int(9) not null comment '属性主键',
   attr_path            varchar(255) comment '由于属性目录下还可以继续配置目录，就导致可能父目录和子目录同时拥有相同的属性。所以使用路径来进行区别',
   text_input           varchar(4000) comment '界面输入项',
   attr_value_id        int(9) comment '属性可选项主键',
   primary key (id, priority)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

drop table if exists complaint_order_value_his;

/*==============================================================*/
/* Table: complaint_order_value_his                             */
/*==============================================================*/
create table complaint_order_value_his
(
   id                   int(9) not null comment '主键',
   priority             int(3) not null comment '优先级',
   sub_order_id         int(9) not null comment '投诉订单主键',
   attr_id              int(9) not null comment '属性主键',
   attr_path            varchar(255) comment '由于属性目录下还可以继续配置目录，就导致可能父目录和子目录同时拥有相同的属性。所以使用路径来进行区别',
   text_input           varchar(4000) comment '界面输入项',
   attr_value_id        int(9) comment '属性可选项主键',
   primary key (priority, id)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

drop table if exists other_order_value_his;

/*==============================================================*/
/* Table: other_order_value_his                                 */
/*==============================================================*/
create table other_order_value_his
(
   id                   int(9) not null comment '主键',
   priority             int(3) not null comment '优先级',
   sub_order_id         int(9) not null comment '其他订单主键',
   attr_id              int(9) not null comment '属性主键',
   attr_path            varchar(255) comment '由于属性目录下还可以继续配置目录，就导致可能父目录和子目录同时拥有相同的属性。所以使用路径来进行区别',
   text_input           varchar(4000) comment '界面输入项',
   attr_value_id        int(9) comment '属性可选项主键',
   primary key (id, priority)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

drop table if exists living_problem_order_value_his;

/*==============================================================*/
/* Table: living_problem_order_value_his                        */
/*==============================================================*/
create table living_problem_order_value_his
(
   id                   int(9) not null comment '主键',
   priority             int(3) not null comment '优先级',
   sub_order_id         int(9) not null comment '入住问题订单主键',
   attr_id              int(9) not null comment '属性主键',
   attr_path            varchar(255) comment '由于属性目录下还可以继续配置目录，就导致可能父目录和子目录同时拥有相同的属性。所以使用路径来进行区别',
   text_input           varchar(4000) comment '界面输入项',
   attr_value_id        int(9) comment '属性可选项主键',
   primary key (id, priority)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

drop table if exists cleaning_order_value_his;

/*==============================================================*/
/* Table: cleaning_order_value_his                              */
/*==============================================================*/
create table cleaning_order_value_his
(
   id                   int(9) not null comment '主键',
   priority             int(3) not null comment '优先级',
   sub_order_id         int(9) not null comment '退租单主键',
   attr_id              int(9) not null comment '属性主键',
   attr_path            varchar(255) comment '由于属性目录下还可以继续配置目录，就导致可能父目录和子目录同时拥有相同的属性。所以使用路径来进行区别',
   text_input           varchar(4000) comment '界面输入项',
   attr_value_id        int(9) comment '属性可选项主键',
   primary key (priority, id)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

drop table if exists routine_cleaning_order_value_his;

/*==============================================================*/
/* Table: routine_cleaning_order_value_his                      */
/*==============================================================*/
create table routine_cleaning_order_value_his
(
   id                   int(9) not null comment '主键',
   priority             int(3) not null comment '优先级',
   sub_order_id         int(9) not null comment '例行保洁订单主键',
   attr_id              int(9) not null comment '属性主键',
   attr_path            varchar(255) comment '由于属性目录下还可以继续配置目录，就导致可能父目录和子目录同时拥有相同的属性。所以使用路径来进行区别',
   text_input           varchar(4000) comment '界面输入项',
   attr_value_id        int(9) comment '属性可选项主键',
   primary key (id, priority)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

drop table if exists owner_cancel_lease_order_value_his;

/*==============================================================*/
/* Table: owner_cancel_lease_order_value_his                    */
/*==============================================================*/
create table owner_cancel_lease_order_value_his
(
   id                   int(9) not null comment '主键',
   priority             int(3) not null comment '优先级',
   sub_order_id         int(9) not null comment '子订单（业主退租订单）',
   attr_id              int(9) not null comment '属性主键',
   attr_path            varchar(255) comment '由于属性目录下还可以继续配置目录，就导致可能父目录和子目录同时拥有相同的属性。所以使用路径来进行区别',
   text_input           varchar(4000) comment '界面输入项',
   attr_value_id        int(9) comment '属性可选项主键',
   primary key (priority, id)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

drop table if exists owner_repair_order_value_his;

/*==============================================================*/
/* Table: owner_repair_order_value_his                          */
/*==============================================================*/
create table owner_repair_order_value_his
(
   id                   int(9) not null comment '主键',
   priority             int(3) not null comment '优先级',
   sub_order_id         int(9) not null comment '业主维修订单主键',
   attr_id              int(9) not null comment '属性主键',
   attr_path            varchar(255) comment '由于属性目录下还可以继续配置目录，就导致可能父目录和子目录同时拥有相同的属性。所以使用路径来进行区别',
   text_input           varchar(4000) comment '界面输入项',
   attr_value_id        int(9) comment '属性可选项主键',
   primary key (id, priority)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;
