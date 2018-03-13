package pccom.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchSql {
	private List sqlList = new ArrayList();

	public void addBatch(String sql, Object[] objects) {
		Map map = new HashMap();
		map.put("sql", sql);
		map.put("objects", objects);
		sqlList.add(map);
	}

	public void addBatch(String sql) {
		Map map = new HashMap();
		map.put("sql", sql);
		map.put("objects", new Object[] {});
		sqlList.add(map);
	}

	public List getSqlList() {
		return sqlList;
	}
}
