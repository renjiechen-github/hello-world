/* 投诉订单value表字段修改 */
alter table complaint_order_value change complaint_order_id sub_order_id int(9);
alter table complaint_order_value drop foreign key FK_complaint_order_value_complaint_order_id;
alter table complaint_order_value drop index FK_complaint_order_value_complaint_order_id;
alter table complaint_order_value add constraint FK_complaint_order_value_sub_order_id foreign key (sub_order_id) references complaint_order (id) on delete restrict on update restrict;

/* 保洁订单value表字段修改 */
alter table cleaning_order_value change cleaning_order_id sub_order_id int(9);
alter table cleaning_order_value drop foreign key FK_cleaning_order_value_cleaning_order_id;
alter table cleaning_order_value drop index FK_cleaning_order_value_cleaning_order_id;
alter table cleaning_order_value add constraint FK_cleaning_order_value_sub_order_id foreign key (sub_order_id) references cleaning_order (id) on delete restrict on update restrict;

/* 看房订单value表字段修改 */
alter table house_looking_order_value change house_looking_order_id sub_order_id int(9);
alter table house_looking_order_value drop foreign key FK_house_looking_order_value_house_looking_order_id;
alter table house_looking_order_value drop index FK_house_looking_order_value_house_looking_order_id;
alter table house_looking_order_value add constraint FK_house_looking_order_value_sub_order_id foreign key (sub_order_id) references house_looking_order (id) on delete restrict on update restrict;

/* 入住问题订单value表字段修改 */
alter table living_problem_order_value change living_problem_order_id sub_order_id int(9);
alter table living_problem_order_value drop foreign key FK_living_problem_order_value_living_problem_order_id;
alter table living_problem_order_value drop index FK_living_problem_order_value_living_problem_order_id;
alter table living_problem_order_value add constraint FK_living_problem_order_value_sub_order_id foreign key (sub_order_id) references living_problem_order (id) on delete restrict on update restrict;

/* 其他订单value表字段修改 */
alter table other_order_value change other_order_id sub_order_id int(9);
alter table other_order_value drop foreign key FK_other_order_value_other_order_id;
alter table other_order_value drop index FK_other_order_value_other_order_id;
alter table other_order_value add constraint FK_other_order_value_sub_order_id foreign key (sub_order_id) references other_order (id) on delete restrict on update restrict;

/* 业主维修订单value表字段修改 */
alter table owner_repair_order_value change owner_repair_order_id sub_order_id int(9);
alter table owner_repair_order_value drop foreign key FK_owner_repair_order_value_owner_repair_order_id;
alter table owner_repair_order_value drop index FK_owner_repair_order_value_owner_repair_order_id;
alter table owner_repair_order_value add constraint FK_owner_repair_order_value_sub_order_id foreign key (sub_order_id) references owner_repair_order (id) on delete restrict on update restrict;

/* 维修订单value表字段修改 */
alter table repair_order_value change repair_order_id sub_order_id int(9);
alter table repair_order_value drop foreign key FK_repair_order_value_repair_order_id;
alter table repair_order_value drop index FK_repair_order_value_repair_order_id;
alter table repair_order_value add constraint FK_repair_order_value_sub_order_id foreign key (sub_order_id) references repair_order (id) on delete restrict on update restrict;

/* 例行保洁订单value表字段修改 */
alter table routine_cleaning_order_value change routine_cleaning_order_id sub_order_id int(9);
alter table routine_cleaning_order_value drop foreign key FK_routine_cleaning_order_value_routine_cleaning_order_id;
alter table routine_cleaning_order_value drop index FK_routine_cleaning_order_value_routine_cleaning_order_id;
alter table routine_cleaning_order_value add constraint FK_routine_cleaning_order_value_sub_order_id foreign key (sub_order_id) references routine_cleaning_order (id) on delete restrict on update restrict;

/* 退租订单value表字段修改 */
alter table cancel_lease_order_value change cancel_lease_order_id sub_order_id int(9);
alter table cancel_lease_order_value drop foreign key FK_cancel_lease_order_value_cancel_lease_order_id;
alter table cancel_lease_order_value drop index FK_cancel_lease_order_value_cancel_lease_order_id;
alter table cancel_lease_order_value add constraint FK_cancel_lease_value_sub_order_id foreign key (sub_order_id) references cancel_lease_order (id) on delete restrict on update restrict;