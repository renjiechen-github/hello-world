package pccom.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("hisTableUtil")
public class HisTableUtil {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	@Resource(name="dbHelper")
	public DBHelperSpring db;// 数据操作
	
	private String[] xxNames = {"from","delete","where","update","from","set"}; 
	 
	public boolean checkExcits( String[] xxNames,String str){
		if(xxNames != null && xxNames.length != 0){
			 for(int i=0;i<xxNames.length;i++){
				 if(xxNames[i].equals(str)){
					 return true;
				 }
			 }
		}
		return false;
	}
	
	/**
	 * 插入历史表
	 * @param sql
	 * @throws Exception
	 */
	public void insertHisTable(String sql) throws Exception{
            
//            
//		StringTokenizer strtoken = new StringTokenizer(sql);
//		List<String> list = new ArrayList<>();
//		while(strtoken.hasMoreElements()){
//			String str = strtoken.nextToken();
//			String strx = str.toLowerCase();
//			if(checkExcits(xxNames, strx.trim())){
//				list.add(strx);
//			}else{
//				list.add(str);
//			}
//		}
//		if(!list.isEmpty()){
//			String tableName = "";
//			StringBuffer wheresql = new StringBuffer();
//			String bianliang = "";
//			String type = "";//0删除1修改
//			if(list.get(0).equals("delete")){
//				int intFrom = list.indexOf("from");
//				
//				//获取更新的表名
//				tableName = list.get(intFrom+1);
//				if(tableName.endsWith("_his_")){
//					logger.debug("退出插入历史表");
//					return ;
//				}
//				 
//				//检查where与表名对应后面是否存在变量名称
//				int intWhere = list.indexOf("where");
//				type = "0";
//				//核实是否存在对表名进行设置变量
//				if(intFrom+2 != intWhere){
//					bianliang = list.get(intFrom+2);
//				}
//				
//				CCJSqlParserManager parser = new CCJSqlParserManager();
//				Statement stmt = parser.parse(new StringReader(sql));
//				
//				if(stmt instanceof Delete){
//					Delete deleteStatement = (Delete)stmt;
//                                        
//					wheresql.append(" where ").append(deleteStatement.getWhere());
//				}
//				
//			}else if(list.get(0).equals("update")){
//				type = "1";
//				tableName = list.get(1);
//				if(tableName.endsWith("_his_")){
//					logger.debug("退出插入历史表");
//					return ;
//				}
//				//获取变量名称
//				int setCnt = list.indexOf("set");
//				
//				//核实是否存在对表名进行设置变量
//				if(setCnt != 2){
//					bianliang = list.get(2);
//				}
//				CCJSqlParserManager parser = new CCJSqlParserManager();
//				Statement stmt = parser.parse(new StringReader(sql));
//				if(stmt instanceof Update){
//					Update updateStatement = (Update)stmt;
//					TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
//					wheresql.append(" where ").append(updateStatement.getWhere());
//				}else{
//					return;
//				}
//			}else{
//				return;
//			}
//			
//			Object oper_ip_obj = MDC.get("oper_ip");
//			String oper_ip = "";
//			Object oper_id_obj = MDC.get("oper_id");
//			String oper_id = "";
//			Object oper_remark_obj = MDC.get("oper_content");
//			String oper_content = "";
//			if(oper_ip_obj != null){
//				oper_ip = String.valueOf(oper_ip_obj);
//			}
//			if(oper_id_obj != null){
//				oper_id = String.valueOf(oper_id_obj);
//			}
//			if(oper_remark_obj != null){
//				oper_content = String.valueOf(oper_remark_obj);
//			}
//			logger.debug("执行插入历史表操作："+tableName+" wheresql:"+wheresql+":oper_ip:"+oper_ip+":oper_id:"+oper_id);
//			insertTable(tableName, wheresql.toString(), bianliang,oper_content, oper_ip, oper_id,type);
//                        //执行完毕后进行清除操作
//                        MDC.remove("oper_id");
//                        MDC.remove("oper_content");
//		}
	}
	
	private void insertTable(String tableName, String wheresql,String bianliang, String oper_content,String ip,String oper_id,String operType){
		String sql = "select a.COLUMN_NAME,a.DATA_TYPE,a.CHARACTER_MAXIMUM_LENGTH,a.NUMERIC_PRECISION,a.NUMERIC_SCALE from information_schema.COLUMNS a where UPPER(TABLE_NAME) = UPPER(?) and table_schema = database()";
		List<Map<String, Object>> list = db.queryForList(sql,new Object[]{tableName});
		List<String> params = new ArrayList<String>();
		String tableName_his = tableName + "_his_";
		String columns = "";
		for (int i = 0; i < list.size(); i++)
		{
			if (i == list.size() - 1)
			{
				columns += StringHelper.get((Map) list.get(i), "COLUMN_NAME");
			}
			else
			{
				columns += StringHelper.get((Map) list.get(i), "COLUMN_NAME") + ",";
			}
		}
		sql = "select a.COLUMN_NAME,a.DATA_TYPE,a.CHARACTER_MAXIMUM_LENGTH,a.NUMERIC_PRECISION,a.NUMERIC_SCALE from information_schema.COLUMNS a where UPPER(TABLE_NAME) = UPPER(?) and table_schema = database()";
		List<Map<String, Object>> his = db.queryForList(sql,new Object[]{tableName_his});
		int exc = his.size();
		if (exc == 0)
		{// 不存在进行新建表
			sql = "create table " + tableName_his +" as select '" + oper_id + "' his_oper_id ,now() his_oper_date,  '" + ip + "' ip,"+"'"+operType+"' oper_type," + "'" + oper_content + "' oper_content," + columns + " from "
			      + tableName +  " "+bianliang+" " + wheresql;
			if (params != null)
			{
				db.update(sql, params.toArray());
			}
			else
			{
				db.update(sql);
			}
			sql = "alter table " + tableName_his + " modify his_oper_id varchar(255)";
			db.update(sql);
			sql = "alter table " + tableName_his + " modify oper_content varchar(255)";
			db.update(sql);
			sql = "alter table " + tableName_his + " modify ip varchar(255)";
			db.update(sql);
			
			//改变表存储引擎
			sql = "ALTER TABLE " + tableName_his +" ENGINE=ARCHIVE";
			db.update(sql);
		}
		else
		{// 存在进行插入表数据信息
			// 检查历史表中的字段信息与需要插入历史表的字段信息的字段内容是否是一致，如果不一致进行调整
			List<Map<String, Object>> add_list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list.size(); i++)
			{
				Map<String, Object> map = list.get(i);
				String column = StringHelper.get(map, "COLUMN_NAME");
				if (!this.checkHisCol(his, column))
				{
					add_list.add(map);
				}
			}
			for (int i = 0; i < add_list.size(); i++)
			{
				Map<String, Object> map = add_list.get(i);
				String column = StringHelper.get(map, "COLUMN_NAME");
				String data_type = StringHelper.get(map, "DATA_TYPE");
				String char_data_length = StringHelper.get(map, "CHARACTER_MAXIMUM_LENGTH");
				String numeric_precision = StringHelper.get(map, "NUMERIC_PRECISION");
				String numeric_scale = StringHelper.get(map, "NUMERIC_SCALE");
				if ("varchar".equals(data_type))
				{
					column += " varchar(" + char_data_length + ")";
				}
				else if("decimal".equals(data_type))
				{
					column += " decimal(" + numeric_precision + "," + numeric_scale + ")";
				}else{
					column += " " + data_type;
				}
				//alter table MyClass add passtest int(4) default '0';
				sql = "alter table " + tableName_his + " add " + column;
				db.update(sql);
			}
			List<String> param = new ArrayList<String>();
			param.add(oper_id);
			param.add(ip);
			param.add(operType);
			param.add(oper_content);
			if (params != null)
			{
				param.addAll(params);
			}
			sql = "insert into " + tableName_his + "(his_oper_id,his_oper_date,ip,oper_type,oper_content," + columns
			      + ") select ?,now(),?,?,?," + columns + " from " + tableName + " "+bianliang+" " + wheresql;
			logger.debug("新增数据sql:" + StringHelper.getSql(sql, param.toArray()));
			db.update(sql, param.toArray());
		}
	}
	
	/**
	 * 检查字段在list中是否存在
	 * @description
	 * @author 雷杨 Feb 25, 2014
	 * @param his
	 * @param column
	 * @return
	 */
	private boolean checkHisCol (List<Map<String, Object>> his, String column)
	{
		for (int j = 0; j < his.size(); j++)
		{
			String column_his = StringHelper.get((Map) his.get(j), "COLUMN_NAME");
			if (column.equals(column_his))
			{
				return true;
			}
		}
		return false;
	}
	
}
