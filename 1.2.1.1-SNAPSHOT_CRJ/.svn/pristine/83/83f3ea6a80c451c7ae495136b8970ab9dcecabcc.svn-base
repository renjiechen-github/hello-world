alter table order_commentary drop foreign key FK_order_commentary_type;
delete a from order_commentary a, work_order b 
where a.work_order_id = b.id and a.type = 'B' and b.type = 'D';
delete from order_commentary_type where type = 'B' and order_type = 'D';

delete a from order_commentary a, work_order b 
where a.work_order_id = b.id and a.type = 'D' and b.type != 'D';
delete from order_commentary_type where type = 'D' and order_type != 'D';

update `order_commentary_type` set `type`='A', `order_type`='A', `root_flag`='Y', `type_name`='总体评价', `type_name_en`='GeneralEvaluation', `comments`='总体评价', `priority`='30' where `order_type`='A' and `type`='A';
update `order_commentary_type` set `type`='A', `order_type`='B', `root_flag`='Y', `type_name`='总体评价', `type_name_en`='GeneralEvaluation', `comments`='总体评价', `priority`='30' where `order_type`='B' and `type`='A';
update `order_commentary_type` set `type`='A', `order_type`='C', `root_flag`='Y', `type_name`='总体评价', `type_name_en`='GeneralEvaluation', `comments`='总体评价', `priority`='30' where `order_type`='C' and `type`='A';
update `order_commentary_type` set `type`='A', `order_type`='D', `root_flag`='Y', `type_name`='总体评价', `type_name_en`='GeneralEvaluation', `comments`='总体评价', `priority`='30' where `order_type`='D' and `type`='A';
update `order_commentary_type` set `type`='A', `order_type`='E', `root_flag`='Y', `type_name`='总体评价', `type_name_en`='GeneralEvaluation', `comments`='总体评价', `priority`='30' where `order_type`='E' and `type`='A';
update `order_commentary_type` set `type`='A', `order_type`='F', `root_flag`='Y', `type_name`='总体评价', `type_name_en`='GeneralEvaluation', `comments`='总体评价', `priority`='30' where `order_type`='F' and `type`='A';
update `order_commentary_type` set `type`='A', `order_type`='G', `root_flag`='Y', `type_name`='总体评价', `type_name_en`='GeneralEvaluation', `comments`='总体评价', `priority`='30' where `order_type`='G' and `type`='A';
update `order_commentary_type` set `type`='A', `order_type`='H', `root_flag`='Y', `type_name`='总体评价', `type_name_en`='GeneralEvaluation', `comments`='总体评价', `priority`='30' where `order_type`='H' and `type`='A';
update `order_commentary_type` set `type`='A', `order_type`='I', `root_flag`='Y', `type_name`='总体评价', `type_name_en`='GeneralEvaluation', `comments`='总体评价', `priority`='30' where `order_type`='I' and `type`='A';
update `order_commentary_type` set `type`='B', `order_type`='A', `root_flag`='N', `type_name`='服务效率', `type_name_en`='ServiceEfficiency', `comments`='服务效率', `priority`='10' where `order_type`='A' and `type`='B';
update `order_commentary_type` set `type`='B', `order_type`='B', `root_flag`='N', `type_name`='服务效率', `type_name_en`='ServiceEfficiency', `comments`='服务效率', `priority`='10' where `order_type`='B' and `type`='B';
update `order_commentary_type` set `type`='B', `order_type`='C', `root_flag`='N', `type_name`='服务效率', `type_name_en`='ServiceEfficiency', `comments`='服务效率', `priority`='10' where `order_type`='C' and `type`='B';
update `order_commentary_type` set `type`='B', `order_type`='E', `root_flag`='N', `type_name`='服务效率', `type_name_en`='ServiceEfficiency', `comments`='服务效率', `priority`='10' where `order_type`='E' and `type`='B';
update `order_commentary_type` set `type`='B', `order_type`='F', `root_flag`='N', `type_name`='服务效率', `type_name_en`='ServiceEfficiency', `comments`='服务效率', `priority`='10' where `order_type`='F' and `type`='B';
update `order_commentary_type` set `type`='B', `order_type`='G', `root_flag`='N', `type_name`='服务效率', `type_name_en`='ServiceEfficiency', `comments`='服务效率', `priority`='10' where `order_type`='G' and `type`='B';
update `order_commentary_type` set `type`='B', `order_type`='H', `root_flag`='N', `type_name`='服务效率', `type_name_en`='ServiceEfficiency', `comments`='服务效率', `priority`='10' where `order_type`='H' and `type`='B';
update `order_commentary_type` set `type`='B', `order_type`='I', `root_flag`='N', `type_name`='服务效率', `type_name_en`='ServiceEfficiency', `comments`='服务效率', `priority`='10' where `order_type`='I' and `type`='B';
update `order_commentary_type` set `type`='C', `order_type`='A', `root_flag`='N', `type_name`='服务态度', `type_name_en`='ServiceAttitude', `comments`='服务态度', `priority`='20' where `order_type`='A' and `type`='C';
update `order_commentary_type` set `type`='C', `order_type`='B', `root_flag`='N', `type_name`='服务态度', `type_name_en`='ServiceAttitude', `comments`='服务态度', `priority`='20' where `order_type`='B' and `type`='C';
update `order_commentary_type` set `type`='C', `order_type`='C', `root_flag`='N', `type_name`='服务态度', `type_name_en`='ServiceAttitude', `comments`='服务态度', `priority`='20' where `order_type`='C' and `type`='C';
update `order_commentary_type` set `type`='C', `order_type`='D', `root_flag`='N', `type_name`='服务态度', `type_name_en`='ServiceAttitude', `comments`='服务态度', `priority`='20' where `order_type`='D' and `type`='C';
update `order_commentary_type` set `type`='C', `order_type`='E', `root_flag`='N', `type_name`='服务态度', `type_name_en`='ServiceAttitude', `comments`='服务态度', `priority`='20' where `order_type`='E' and `type`='C';
update `order_commentary_type` set `type`='C', `order_type`='F', `root_flag`='N', `type_name`='服务态度', `type_name_en`='ServiceAttitude', `comments`='服务态度', `priority`='20' where `order_type`='F' and `type`='C';
update `order_commentary_type` set `type`='C', `order_type`='G', `root_flag`='N', `type_name`='服务态度', `type_name_en`='ServiceAttitude', `comments`='服务态度', `priority`='20' where `order_type`='G' and `type`='C';
update `order_commentary_type` set `type`='C', `order_type`='H', `root_flag`='N', `type_name`='服务态度', `type_name_en`='ServiceAttitude', `comments`='服务态度', `priority`='20' where `order_type`='H' and `type`='C';
update `order_commentary_type` set `type`='C', `order_type`='I', `root_flag`='N', `type_name`='服务态度', `type_name_en`='ServiceAttitude', `comments`='服务态度', `priority`='20' where `order_type`='I' and `type`='C';
update `order_commentary_type` set `type`='D', `order_type`='D', `root_flag`='N', `type_name`='产品质量', `type_name_en`='ProductQuality', `comments`='产品质量', `priority`='20' where `order_type`='D' and `type`='D';