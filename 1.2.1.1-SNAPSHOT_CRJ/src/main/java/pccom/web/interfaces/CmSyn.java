package pccom.web.interfaces;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import pccom.common.util.DownloadImage;
import pccom.common.util.SHA1;
import pccom.common.util.StringHelper;
import pccom.web.server.BaseService;

/**
 * 春眠房源接口
 *
 * @author admin
 *
 */
@Service("cmSyn") 
public class CmSyn extends BaseService {

//	public final static String APPID = "CMIDqm3h3A4TDq8TyyoD8t6EBaxqSHYn1qDS";
//	public final static String KEY = "jzzwFBW5XS6XPBRPW9GXYll30lgqJK6u";
    public final static String APPID = "CMIDHWOglW2Kgym2wj5D6P5TL176bcPXiXmT";
    public final static String KEY = "AdpocQrvAacjXJze8vQGiRXDxIzTycnS";

    /**
     * 房源同步url 测试环境
     */
//	public final static String SYN_INFO_URL = "http://api.chunmiantest.fadongxi.com/api/v1/cmhImport";
    /**
     * 房源同步url 真实环境
     */
    public final static String SYN_INFO_URL = "http://partner.chunmian.qufenqi.com/api/v1/cmhImport";

    /**
     * 进行初始化同步操作
     */
    public void initSyn() {
        logger.debug("开始同步春雷房源----------------------------------------------------------------------");
        String sql = "select b.id outHouseId, "
                + " a.`images` picUrlList,"
                + " a.rank_type+1 rentType,"
                + " (CASE when SUBSTRING(b.spec,1,1) = '' then 0 else SUBSTRING(b.spec,1,1) end) bedRoomNum,"
                + " (CASE when SUBSTRING(b.spec,3,1) = '' then 0 else SUBSTRING(b.spec,3,1) end) livingRoomNum,"
                + " (CASE when SUBSTRING(b.spec,5,1) = '' then 0 else SUBSTRING(b.spec,5,1) end) toiletNum,"
                + " a.rank_area rentRoomArea,"
                + " '33'  bedRoomType,"
                + " a.rank_name roomName ,"
                + " a.house_id roomCode,"
                + " '70' faceToType,"
                + " (CASE WHEN b.floor IS NULL OR b.floor = '' THEN -1 ELSE b.floor end) totalFloor,"
                + " (CASE WHEN b.floor IS NULL OR b.floor = '' THEN -1 ELSE b.floor end) houseFloor,"
                + " '11,16' featureTag,"
                + " '71,72,73,74,80,81,82,90,94,107,112' detailPoint,"
                + " '96,97' servicePoint,"
                + " a.fee monthRent,"
                + " 1 shortRent,"
                + " '江苏省' province,"
                + " '南京' cityName,"
                + " e.area_name countyName,"
                + " (SELECT f.`area_name` FROM yc_area_tab f WHERE f.id = c.`trading`) areaName,"
                + " c.group_name districtName,"
                + " c.address street,"
                + " b.address address,"
                + " a.rank_desc houseDesc,"
                + //" '1' houseDesc,"+
                " SUBSTRING_INDEX(b.coordinate,',',1) xCoord,"
                + " REPLACE(b.coordinate,CONCAT(SUBSTRING_INDEX(b.coordinate,',',1),','),'') yCoord,"
                + " g.mobile agentPhone,"
                + " g.mobile orderPhone,"
                + " g.name agentName,"
                + " 3 supplyHeating,"
                + " 30 greenRatio,"
                + " (SELECT SUBSTRING(t.rank_spec,1,1) from yc_houserank_tab t WHERE t.house_id = b.id AND t.rank_type = 0) actualRoomNum"
                + " from yc_houserank_tab as a,yc_house_tab as b,yc_group_tab as c,yc_area_tab e,yc_account_tab g, cf_team_member_area tma"
                + " where c.`areaid` = e.id "
                + "   and tma.user_id = g.id "
                + "   and tma.area_id = c.id "
                + "   and a.isdelete = 1 	"
                + "   and b.id = a.house_id "
                + "   and c.id=b.group_id 	"
                + "   and b.`publish` = 2 "
                + "   AND a.`rank_status` = 3"
                + "   and a.fee>='500' ";
        List<Map<String, Object>> list = db.queryForList(sql);
        logger.debug("获取需要同步的数据信息：" + sql);
        //进行拆解图片信息
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String[] split = str.get(map, "picUrlList").split("\\|");
            List<Map<String, Object>> picUrlList = new ArrayList<Map<String, Object>>();
            for (int j = 0; j < split.length; j++) {
                if (split[j].length() > 5) {
                    if (!picUrlList.isEmpty()) {
                        continue;
                    }
                    Map<String, Object> picUrlMap = new HashMap<String, Object>();
                    picUrlMap.put("detailNum", j + "");
                    picUrlMap.put("picDesc", "其他");
//					picUrlMap.put("picUrl",SystemConfig.getValue("FTP_HTTP")+"/"+SystemConfig.getValue("DATA_PATH") + "img"+split[j].replace("\\", "/"));
                    picUrlMap.put("picUrl", "http://file.room1000.com:9003/data/img" + split[j].replace("\\", "/"));
                    picUrlList.add(picUrlMap);
                }
            }
            Map<String, Object> params = new HashMap<>();
            params.put("appId", CmSyn.APPID);
            params.put("actualRoomNum", getValue(map, "actualRoomNum"));
            params.put("address", getValue(map, "address"));
            params.put("agentName", getValue(map, "agentName"));
            params.put("agentPhone", getValue(map, "agentPhone"));
            params.put("areaName", getValue(map, "areaName"));
            params.put("bedRoomNum", getValue(map, "bedRoomNum"));
            params.put("bedRoomType", getValue(map, "bedRoomType"));
            params.put("cityName", getValue(map, "cityName"));
            params.put("countyName", getValue(map, "countyName"));
            params.put("detailPoint", getValue(map, "detailPoint"));
            params.put("districtName", getValue(map, "districtName"));
            params.put("faceToType", getValue(map, "faceToType"));
            params.put("featureTag", getValue(map, "featureTag"));
            params.put("greenRatio", getValue(map, "greenRatio"));
            params.put("houseDesc", getValue(map, "houseDesc"));
            params.put("houseFloor", getValue(map, "houseFloor"));
            params.put("livingRoomNum", getValue(map, "livingRoomNum"));
            params.put("monthRent", getValue(map, "monthRent"));
            params.put("orderPhone", getValue(map, "orderPhone"));
            params.put("outHouseId", getValue(map, "outHouseId"));
            params.put("province", getValue(map, "province"));
            params.put("rentRoomArea", getValue(map, "rentRoomArea"));
            params.put("rentType", getValue(map, "rentType"));
            params.put("roomCode", getValue(map, "roomCode"));
            params.put("roomName", getValue(map, "roomName"));
            params.put("servicePoint", getValue(map, "servicePoint"));
            params.put("shortRent", getValue(map, "shortRent"));
            params.put("street", getValue(map, "street"));
            params.put("supplyHeating", getValue(map, "supplyHeating"));
            params.put("toiletNum", getValue(map, "toiletNum"));
            params.put("totalFloor", getValue(map, "totalFloor"));
            params.put("xCoord", getValue(map, "xCoord"));
            params.put("yCoord", getValue(map, "yCoord"));

            String result = sendData(params, picUrlList);
            String sys_state = "1";
            String sys_id = getValue(map, "outHouseId");
            String sys_result = "";
            if ("".equals(result)) {
                sys_state = "0";
                logger.error("同步春眠房源失败：" + params + "-----" + picUrlList);
            } else {
                JSONObject json = JSONObject.fromObject(result);
                if ("0".equals(json.getString("code"))) {
                    sys_state = "1";
                    logger.debug("同步房源成功" + params + "-----" + picUrlList);
                } else {
                    sys_state = "0";
                    sys_result = convertUnicode(result);
                    logger.error("同步春眠房源失败：" + convertUnicode(result) + "-------------" + params + "-----" + picUrlList);
                }
            }
            sql = "insert into yc_clsyn_house(syn_time,sys_state,sys_id,sys_result)values(now(),?,?,?)";
            db.update(sql, new Object[]{sys_state, sys_id, sys_result});
        }
        logger.debug("结束同步春雷房源----------------------------------------------------------------------");
    }

    public void testDownload() {

        String sql = "select b.id outHouseId, "
                + " a.`images` picUrlList,"
                + " a.rank_type+1 rentType,"
                + " (CASE when SUBSTRING(b.spec,1,1) = '' then 0 else SUBSTRING(b.spec,1,1) end) bedRoomNum,"
                + " (CASE when SUBSTRING(b.spec,3,1) = '' then 0 else SUBSTRING(b.spec,3,1) end) livingRoomNum,"
                + " (CASE when SUBSTRING(b.spec,5,1) = '' then 0 else SUBSTRING(b.spec,5,1) end) toiletNum,"
                + " a.rank_area rentRoomArea,"
                + " '33'  bedRoomType,"
                + " a.rank_name roomName ,"
                + " a.house_id roomCode,"
                + " '70' faceToType,"
                + " (CASE WHEN b.floor IS NULL OR b.floor = '' THEN -1 ELSE b.floor end) totalFloor,"
                + " (CASE WHEN b.floor IS NULL OR b.floor = '' THEN -1 ELSE b.floor end) houseFloor,"
                + " '11,16' featureTag,"
                + " '71,72,73,74,80,81,82,90,94,107,112' detailPoint,"
                + " '96,97' servicePoint,"
                + " a.fee monthRent,"
                + " 1 shortRent,"
                + " '江苏省' province,"
                + " '南京' cityName,"
                + " e.area_name countyName,"
                + " (SELECT f.`area_name` FROM yc_area_tab f WHERE f.id = c.`trading`) areaName,"
                + " c.group_name districtName,"
                + " c.address street,"
                + " b.address address,"
                + " a.rank_desc houseDesc,"
                + //" '1' houseDesc,"+
                " SUBSTRING_INDEX(b.coordinate,',',1) xCoord,"
                + " REPLACE(b.coordinate,CONCAT(SUBSTRING_INDEX(b.coordinate,',',1),','),'') yCoord,"
                + " g.mobile agentPhone,"
                + " g.mobile orderPhone,"
                + " g.name agentName,"
                + " 3 supplyHeating,"
                + " 30 greenRatio,"
                + " (SELECT SUBSTRING(t.rank_spec,1,1) from yc_houserank_tab t WHERE t.house_id = b.id AND t.rank_type = 0) actualRoomNum"
                + " from yc_houserank_tab as a,yc_house_tab as b,yc_group_tab as c,yc_area_tab e,yc_account_tab g, cf_team_member_area tma"
                + " where c.`areaid` = e.id "
                + "   AND tma.area_id = c.id"
                + "   AND tma.user_id = g.id"
                + "   and a.isdelete = 1 	"
                + "   and b.id = a.house_id "
                + "   and c.id=b.group_id 	"
                + "   and b.`publish` = 2 "
                + "   AND a.`rank_status` = 3"
                + "   and a.fee>='500' ";
        List<Map<String, Object>> list = db.queryForList(sql);
        logger.debug("获取需要同步的数据信息：" + sql);
        //进行拆解图片信息
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String[] split = str.get(map, "picUrlList").split("\\|");
            List<Map<String, Object>> picUrlList = new ArrayList<Map<String, Object>>();
            final CountDownLatch latch = new CountDownLatch(split.length);
            for (int j = 0; j < split.length; j++) {
                if (split[j].length() > 5) {
                    final String path = "http://file.room1000.com:9003/data/img" + split[j].replace("\\", "/");
                    final String fileName = path.substring(path.lastIndexOf("/") + 1);
                    System.out.println("下载地址：" + path);
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            //进行下载操作
                            try {
                                DownloadImage.download(path, fileName, "C:\\Users\\admin\\Desktop\\08\\");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            latch.countDown();
                        }
                    }).start();

                }
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 出现错误进行补跑
     *
     * @return
     */
    public void errorSyn() {
        logger.debug("开始同步春雷房源----------------------------------------------------------------------");
        String sql = "select b.id outHouseId, "
                + " a.`images` picUrlList,"
                + " a.rank_type+1 rentType,"
                + " (CASE when SUBSTRING(b.spec,1,1) = '' then 0 else SUBSTRING(b.spec,1,1) end) bedRoomNum,"
                + " (CASE when SUBSTRING(b.spec,3,1) = '' then 0 else SUBSTRING(b.spec,3,1) end) livingRoomNum,"
                + " (CASE when SUBSTRING(b.spec,5,1) = '' then 0 else SUBSTRING(b.spec,5,1) end) toiletNum,"
                + " a.rank_area rentRoomArea,"
                + " '33'  bedRoomType,"
                + " a.rank_name roomName ,"
                + " a.house_id roomCode,"
                + " '70' faceToType,"
                + " (CASE WHEN b.floor IS NULL OR b.floor = '' THEN -1 ELSE b.floor end) totalFloor,"
                + " (CASE WHEN b.floor IS NULL OR b.floor = '' THEN -1 ELSE b.floor end) houseFloor,"
                + " '11,16' featureTag,"
                + " '71,72,73,74,80,81,82,90,94,107,112' detailPoint,"
                + " '96,97' servicePoint,"
                + " a.fee monthRent,"
                + " 1 shortRent,"
                + " '江苏省' province,"
                + " '南京' cityName,"
                + " e.area_name countyName,"
                + " (SELECT f.`area_name` FROM yc_area_tab f WHERE f.id = c.`trading`) areaName,"
                + " c.group_name districtName,"
                + " c.address street,"
                + " b.address address,"
                + " a.rank_desc houseDesc,"
                + //" '1' houseDesc,"+
                " SUBSTRING_INDEX(b.coordinate,',',1) xCoord,"
                + " REPLACE(b.coordinate,CONCAT(SUBSTRING_INDEX(b.coordinate,',',1),','),'') yCoord,"
                + " g.mobile agentPhone,"
                + " g.mobile orderPhone,"
                + " g.name agentName,"
                + " 3 supplyHeating,"
                + " 30 greenRatio,"
                + " (SELECT SUBSTRING(t.rank_spec,1,1) from yc_houserank_tab t WHERE t.house_id = b.id AND t.rank_type = 0) actualRoomNum"
                + " from yc_houserank_tab as a,yc_house_tab as b,yc_group_tab as c,yc_area_tab e,yc_account_tab g, cf_team_member_area tma"
                + " where exists(select 1 from yc_clsyn_house f where f.sys_id = a.id and f.sys_state = 0)"
                + " and c.`areaid` = e.id "
                + "   AND tma.area_id = c.id"
                + "   AND tma.user_id = g.id"
                + "   and a.isdelete = 1 	"
                + "   and b.id = a.house_id "
                + "   and c.id=b.group_id 	"
                + "   and b.`publish` = 2 "
                + "   AND a.`rank_status` = 3"
                + "   and a.fee>='500' ";
        List<Map<String, Object>> list = db.queryForList(sql);
        logger.debug("获取需要同步的数据信息：" + sql);
        //进行拆解图片信息
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String[] split = str.get(map, "picUrlList").split("\\|");
            List<Map<String, Object>> picUrlList = new ArrayList<Map<String, Object>>();
            for (int j = 0; j < split.length; j++) {
                if (split[j].length() > 5) {
                    Map<String, Object> picUrlMap = new HashMap<String, Object>();
                    picUrlMap.put("detailNum", j + "");
                    picUrlMap.put("picDesc", "其他");
//					picUrlMap.put("picUrl",SystemConfig.getValue("FTP_HTTP")+"/"+SystemConfig.getValue("DATA_PATH") + "img"+split[j].replace("\\", "/"));
                    picUrlMap.put("picUrl", "http://file.room1000.com:9003/data/img" + split[j].replace("\\", "/"));
                    picUrlList.add(picUrlMap);
                }
            }
            Map<String, Object> params = new HashMap<>();
            params.put("appId", CmSyn.APPID);
            params.put("actualRoomNum", getValue(map, "actualRoomNum"));
            params.put("address", getValue(map, "address"));
            params.put("agentName", getValue(map, "agentName"));
            params.put("agentPhone", getValue(map, "agentPhone"));
            params.put("areaName", getValue(map, "areaName"));
            params.put("bedRoomNum", getValue(map, "bedRoomNum"));
            params.put("bedRoomType", getValue(map, "bedRoomType"));
            params.put("cityName", getValue(map, "cityName"));
            params.put("countyName", getValue(map, "countyName"));
            params.put("detailPoint", getValue(map, "detailPoint"));
            params.put("districtName", getValue(map, "districtName"));
            params.put("faceToType", getValue(map, "faceToType"));
            params.put("featureTag", getValue(map, "featureTag"));
            params.put("greenRatio", getValue(map, "greenRatio"));
            params.put("houseDesc", getValue(map, "houseDesc"));
            params.put("houseFloor", getValue(map, "houseFloor"));
            params.put("livingRoomNum", getValue(map, "livingRoomNum"));
            params.put("monthRent", getValue(map, "monthRent"));
            params.put("orderPhone", getValue(map, "orderPhone"));
            params.put("outHouseId", getValue(map, "outHouseId"));
            params.put("province", getValue(map, "province"));
            params.put("rentRoomArea", getValue(map, "rentRoomArea"));
            params.put("rentType", getValue(map, "rentType"));
            params.put("roomCode", getValue(map, "roomCode"));
            params.put("roomName", getValue(map, "roomName"));
            params.put("servicePoint", getValue(map, "servicePoint"));
            params.put("shortRent", getValue(map, "shortRent"));
            params.put("street", getValue(map, "street"));
            params.put("supplyHeating", getValue(map, "supplyHeating"));
            params.put("toiletNum", getValue(map, "toiletNum"));
            params.put("totalFloor", getValue(map, "totalFloor"));
            params.put("xCoord", getValue(map, "xCoord"));
            params.put("yCoord", getValue(map, "yCoord"));

            String result = sendData(params, picUrlList);
            String sys_state = "1";
            String sys_id = getValue(map, "outHouseId");
            String sys_result = "";
            if ("".equals(result)) {
                sys_state = "0";
                logger.error("同步春眠房源失败：" + params + "-----" + picUrlList);
            } else {
                JSONObject json = JSONObject.fromObject(result);
                if ("0".equals(json.getString("code"))) {
                    sys_state = "1";
                    logger.debug("同步房源成功" + params + "-----" + picUrlList);
                } else {
                    sys_state = "0";
                    sys_result = convertUnicode(result);
                    logger.error("同步春眠房源失败：" + convertUnicode(result) + "-------------" + params + "-----" + picUrlList);
                }
            }
            db.update("delete a from yc_clsyn_house a where a.sys_id = ?", new Object[]{sys_id});
            sql = "insert into yc_clsyn_house(syn_time,sys_state,sys_id,sys_result)values(now(),?,?,?)";
            db.update(sql, new Object[]{sys_state, sys_id, sys_result});
        }
        logger.debug("结束同步春雷房源----------------------------------------------------------------------");
    }

    public String getValue(Map<String, Object> map, String key) {
        return StringHelper.get(map, key).trim();
    }

    /**
     * 发送数据信息
     *
     * @param params
     * @param picUrlList
     * @return
     */
    private String sendData(Map<String, Object> params, List<Map<String, Object>> picUrlList) {
        try {
            SHA1 sha1 = new SHA1();
            params.put("picUrlList", JSONArray.fromObject(picUrlList).toString());
            Map<String, String> resultMap = sha1.sortMap(params, true);
            logger.debug("需要转换的参数信息:" + resultMap);
            String resultString = sha1.castMapToString(resultMap) + KEY;
            logger.debug("需要转换的参数信息:" + resultString);
            String sign = sha1.Digest(resultString);
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("ak", sign);
            paramsMap.put("data", JSONObject.fromObject(params).toString());
            logger.debug(CmSyn.SYN_INFO_URL + "---------" + paramsMap);
            String result = CmSyn.send(CmSyn.SYN_INFO_URL, paramsMap);
            logger.debug(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("appId", CmSyn.APPID);
        params.put("actualRoomNum", 4);
        params.put("address", "滨江瑞景3栋1单元1701");
        params.put("agentName", "张修果");
        params.put("agentPhone", "18805157589");
        params.put("areaName", "福建路");
        params.put("bedRoomNum", "3");
        params.put("bedRoomType", "33");
        params.put("cityName", "南京");
        params.put("countyName", "鼓楼区");
        params.put("detailPoint", "71,72,73,74,80,81,82,90,94,107,112");
        params.put("districtName", "滨江瑞景家园");
        params.put("faceToType", "70");
        params.put("featureTag", "11,16");
        params.put("greenRatio", "30");
        params.put("houseDesc", "1");
        params.put("houseFloor", "17");
        params.put("livingRoomNum", "2");
        params.put("monthRent", "1200");
        params.put("orderPhone", "18805157589");
        params.put("outHouseId", "266");
        params.put("province", "江苏省");
        params.put("rentRoomArea", "15");
        params.put("rentType", "2.0");
        params.put("roomCode", "266");
        params.put("roomName", "");
        params.put("servicePoint", "96,97");
        params.put("shortRent", "1");
        params.put("street", "滨江瑞景家园");
        params.put("supplyHeating", "3");
        params.put("toiletNum", "1");
        params.put("totalFloor", "17");
        params.put("xCoord", "118.735135");
        params.put("yCoord", "32.049856");
        try {
            List<Map<String, Object>> picUrlList = new ArrayList<Map<String, Object>>();
            Map<String, Object> picUrlMap = new HashMap<String, Object>();
            picUrlMap.put("detailNum", "1");
            picUrlMap.put("picDesc", "其他");
            picUrlMap.put("picUrl", "img\\/266/pub\\1450231689029_0.jpg");
            picUrlList.add(picUrlMap);
//			System.out.println(sendData(params, picUrlList));

//			
//			
//			SHA1 sha1 = new SHA1();
//			params.put("picUrlList", JSONArray.fromObject(picUrlList).toString());
//			Map<String, String> resultMap = sha1.sortMap(params, true);
//			System.out.println("需要转换的参数信息:"+resultMap);
//			String resultString = sha1.castMapToString(resultMap)+ KEY;
//			System.out.println("需要转换的参数信息:"+resultString);
//			String sign = sha1.Digest(resultString);
//			Map<String,Object> paramsMap = new HashMap<>();
//			paramsMap.put("ak",sign);
//			paramsMap.put("data",JSONObject.fromObject(params).toString());
//			System.out.println(CmSyn.SYN_INFO_URL+"---------"+paramsMap);
//			String result = CmSyn.send(CmSyn.SYN_INFO_URL, paramsMap);
//			System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String send(String url, Map<String, Object> paramsMap) {
        HttpPost httppost = new HttpPost(url);
        //建立HttpPost对象
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //建立一个NameValuePair数组，用于存储欲传送的参数
        if (!paramsMap.isEmpty()) {
            Set<String> set = paramsMap.keySet();
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String name = it.next();
                params.add(new BasicNameValuePair(name, StringHelper.get(paramsMap, name)));
            }
        }
        //添加参数
        try {
            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            //设置编码
            HttpResponse response = new DefaultHttpClient().execute(httppost);
            //发送Post,并返回一个HttpResponse对象
            //如果状态码为200,就是正常返回
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity());
            } else {
                return "";
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static String sendLoginRequest(String urlPath, String content) throws IOException {
//		String urlPath = new String("http://localhost:8080/Test1/HelloWorld"); 
        //String urlPath = new String("http://localhost:8080/Test1/HelloWorld?name=丁丁".getBytes("UTF-8"));
        //建立连接
        URL url = new URL(urlPath);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        //设置参数
        httpConn.setDoOutput(true);   //需要输出
        httpConn.setDoInput(true);   //需要输入
        httpConn.setUseCaches(false);  //不允许缓存
        httpConn.setRequestMethod("POST");   //设置POST方式连接
        //设置请求属性
        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
        httpConn.setRequestProperty("Charset", "UTF-8");
        //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
        httpConn.connect();
        //建立输入流，向指向的URL传入参数
        DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
        dos.writeBytes(content);
        dos.flush();
        dos.close();
        //获得响应状态
        int resultCode = httpConn.getResponseCode();
        if (HttpURLConnection.HTTP_OK == resultCode) {
            StringBuffer sb = new StringBuffer();
            String readLine = new String();
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine).append("\n");
            }
            responseReader.close();
            return sb.toString();
        }
        return "";
    }

    /**
     * 执行同步操作
     *
     */
    public void syn() {
    }

    public static String convertUnicode(String ori) {
        char aChar;
        int len = ori.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = ori.charAt(x++);
            if (aChar == '\\') {
                aChar = ori.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = ori.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }

        }
        return outBuffer.toString();
    }

}
