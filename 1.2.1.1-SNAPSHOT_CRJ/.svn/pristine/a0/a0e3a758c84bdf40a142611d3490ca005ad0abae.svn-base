package com.ycdc.util.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对分页的基本数据进行一个简单的封装
 */
public class Page<T> {
 
	private int pageNo = 1;//页码，默认是第一页
    private int pageSize = 15;//每页显示的记录数，默认是15
    private int totalRecord;//总记录数
    private int totalPage;//总页数
    private List<T> results;//对应的当前页记录
    private boolean isExp = false;//是否是导出
    private Map<String, Object> params = new HashMap<String, Object>();//其他的参数我们把它分装成一个Map对象
	private String iSortCol="";
    private String bSortable="";
    private String mDataProp = "";
    private String sSortDir_0 = "";
    private String iDisplayLength = "";
    private String iDisplayStart="";
    private String isCxSeach= "";
    private String sizelength="";
    
    public String getSizelength() {
		return sizelength;
	}

	public void setSizelength(String sizelength) {
		this.sizelength = sizelength;
	}

	public String getIsCxSeach() {
		return isCxSeach;
	}

	public void setIsCxSeach(String isCxSeach) {
		this.isCxSeach = isCxSeach;
	}

	public String getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(String iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public String getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(String iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
 
    public String getsSortDir_0() {
		return sSortDir_0;
	}

	public void setsSortDir_0(String sSortDir_0) {
		this.sSortDir_0 = sSortDir_0;
	}

	public String getmDataProp() {
		return mDataProp;
	}

	public void setmDataProp(String mDataProp) {
		this.mDataProp = mDataProp;
	}

	public String getbSortable() {
		return bSortable;
	}

	public void setbSortable(String bSortable) {
		this.bSortable = bSortable;
	}

	public String getiSortCol() {
		return iSortCol;
	}

	public void setiSortCol(String iSortCol) {
		this.iSortCol = iSortCol;
	}

	public boolean isExp() {
		return isExp;
	}

	public void setExp(boolean isExp) {
		this.isExp = isExp;
	}
    
    public int getPageNo() {
       return pageNo;
    }
 
    public void setPageNo(int pageNo) {
       this.pageNo = pageNo;
    }
 
    public int getPageSize() {
       return pageSize;
    }
 
    public void setPageSize(int pageSize) {
       this.pageSize = pageSize;
    }
 
    public int getTotalRecord() {
       return totalRecord;
    }
 
    public void setTotalRecord(int totalRecord) {
       this.totalRecord = totalRecord;
       //在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
       int totalPage = totalRecord%pageSize==0 ? totalRecord/pageSize : totalRecord/pageSize + 1;
       this.setTotalPage(totalPage);
    }
 
    public int getTotalPage() {
       return totalPage;
    }
 
    public void setTotalPage(int totalPage) {
       this.totalPage = totalPage;
    }
 
    public List<T> getResults() {
       return results;
    }
 
    public void setResults(List<T> results) {
       this.results = results;
    }
   
    public Map<String, Object> getParams() {
       return params;
    }
   
    public void setParams(Map<String, Object> params) {
       this.params = params;
    }
 
    @Override
    public String toString() {
       StringBuilder builder = new StringBuilder();
       builder.append("Page [pageNo=").append(pageNo).append(", pageSize=")
              .append(pageSize).append(", results=").append(results).append(
                     ", totalPage=").append(totalPage).append(", totalRecord=").append(totalRecord).append(", sizelength=").append(sizelength).append("]");
       return builder.toString();
    }
 
}