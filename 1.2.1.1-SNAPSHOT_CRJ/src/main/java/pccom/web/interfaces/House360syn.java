package pccom.web.interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.common.util.SignUtil;
import pccom.common.util.StringHelper;
import pccom.web.beans.SystemConfig;
import pccom.web.server.BaseService;

/**
 * 360同步接口
 *
 * @author admin
 *
 */
@Controller
public class House360syn extends BaseService {

    private String token = "ycqwjhouse";

    public void writeJsonData(Object data, HttpServletResponse response) {
        try {
            response.setContentType("text/plain;charset=" + "UTF-8");
            response.setCharacterEncoding("UTF-8");
            if (data instanceof Map) {
                response.getWriter().print(JSONObject.fromObject(data));
            } else if (data instanceof List) {
                JSONArray array = JSONArray.fromObject(((List) data).toArray());
                response.getWriter().print(array);
            } else {
                response.getWriter().print(JSONObject.fromObject(data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前时间
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "interfaces/365house/getData.do")
    public void getData(HttpServletRequest request, HttpServletResponse response) {
        //

        Map<String, Object> map = getReturnMap(1);
        map.put("data", System.currentTimeMillis());
        writeJsonData(map, response);
    }

    /**
     * 获取房源信息
     *
     * @author 雷杨
     * @param response
     */
    @RequestMapping(value = "interfaces/365house/gethouse.do")
    public void gethouse(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("获取房源信息");

        //获取传递数据拼接信息
        Map<String, String> params = new HashMap<String, String>();
        if (null != request) {
            Set<String> paramsKey = request.getParameterMap().keySet();
            for (String key : paramsKey) {
                params.put(key, request.getParameter(key));
            }
        }
        params.remove("sign");
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        String str = content.toString() + "&key=" + token;

        String size = req.getAjaxValue(request, "size");//页码
        String start = req.getAjaxValue(request, "start");//由第几条数据开始
        String date = req.getAjaxValue(request, "date");//时间
        String timestamp = req.getAjaxValue(request, "timestamp");//时间戳
        String sign = req.getAjaxValue(request, "sign");//编码

        if ("".equals(size)) {
            writeJsonData(getReturnMap(-12), response);
            return;
        }

        try {
            if (Integer.valueOf(size) > 1000) {
                writeJsonData(getReturnMap(-15), response);
                return;
            }
        } catch (Exception e) {
            writeJsonData(getReturnMap(-16), response);
            return;
        }

        if ("".equals(start)) {
            writeJsonData(getReturnMap(-13), response);
            return;
        }

        if ("".equals(timestamp)) {
            writeJsonData(getReturnMap(-14), response);
            return;
        }

        try {
            //检查timestamp是否过期  超过10分钟无效
            if ((System.currentTimeMillis() - Long.valueOf(timestamp)) / 1000 / 60 > 2) {//timestamp时间过期
                writeJsonData(getReturnMap(-11), response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            writeJsonData(getReturnMap(-17), response);
            return;
        }

        if (!SignUtil.md5(str).equals(sign)) {//签名失败
            writeJsonData(getReturnMap(-10), response);
            return;
        }

        String sql = "select a.`id`,"
                + "  a.`house_id`,"
                + "  a.`istop`,"
                + "  a.`title`,"
                + "  b.house_name,"
                + "  DATE_FORMAT(a.`createtime`,'%Y-%m-%d') createtime,"
                + "  a.`rank_type`,"
                + "  a.`fee`,"
                + "  a.`rank_desc`,"
                + "  a.`allocation_id_a`,"
                + "  a.`images`,"
                + "  a.`rank_area`,"
                + "  a.`rank_code`,"
                + "  b.house_code,"
                + "  b.coordinate,"
                + "  b.address,"
                + "  b.floor,"
                + "  b.spec,"
                + "	b.area,"
                + "  b.group_id,"
                + "  c.group_name,"
                + "  c.areaid,e.area_name "
                + "  from yc_houserank_tab as a,yc_house_tab as b,yc_group_tab as c,yc_area_tab e "
                + " where c.`areaid` = e.id "
                + "  and a.isdelete = 1 "
                + "  and b.id = a.house_id"
                + "  and c.id=b.group_id "
                + "  and b.`publish` = 2 "
                + "  AND a.`rank_status` = 3 and b.house_status in(5,6,9) ";
        List<Object> params1 = new ArrayList<>();
        if (!"".equals(date)) {
            sql += " and date_format(a.publish_time,'%Y-%m-%d') >= ?";
            params1.add(date);
        }
        sql += "  order by a.istop desc, a.rank_status asc,a.createtime desc limit ? ,?";
        params1.add(Integer.valueOf(start));
        params1.add(Integer.valueOf(size));
        logger.debug("获取房源信息:" + sql);
        //进行图片转换
        List<Map> list = db.queryForList(sql, params1);
        for (int i = 0; i < list.size(); i++) {
            Map map = list.get(i);
            String spec = StringHelper.get(map, "spec");
            map.put("spec", spec.replaceFirst("\\|", "室").replaceFirst("\\|", "厅") + "卫");
            String images = StringHelper.get(map, "images");
            logger.debug("images:" + images.length());
            if (images.length() > 4) {
                String[] imagess = images.split("\\|");
                List listImg = new ArrayList();
                for (String img : imagess) {
                    listImg.add(SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH") + img.replace("\\", "/"));
                }
                logger.debug(listImg + "");
                map.put("images", listImg);
            } else {
                map.put("images", new ArrayList());
            }
        }
        Map<String, Object> map = getReturnMap(1);
        map.put("data", list);
        writeJsonData(map, response);
    }

    /**
     * 获取全量失效房源信息
     *
     * @author 雷杨
     * @param response
     */
    @RequestMapping(value = "interfaces/365house/getLostHouse.do")
    public void getLostHouse(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("获取房源信息");

        //获取传递数据拼接信息
        Map<String, String> params = new HashMap<String, String>();
        if (null != request) {
            Set<String> paramsKey = request.getParameterMap().keySet();
            for (String key : paramsKey) {
                params.put(key, request.getParameter(key));
            }
        }
        params.remove("sign");
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        String str = content.toString() + "&key=" + token;

        String size = req.getAjaxValue(request, "size");//页码
        String start = req.getAjaxValue(request, "start");//由第几条数据开始
        String timestamp = req.getAjaxValue(request, "timestamp");//时间戳
        String sign = req.getAjaxValue(request, "sign");//编码

        if ("".equals(size)) {
            writeJsonData(getReturnMap(-12), response);
            return;
        }

        try {
            if (Integer.valueOf(size) > 1000) {
                writeJsonData(getReturnMap(-15), response);
                return;
            }
        } catch (Exception e) {
            writeJsonData(getReturnMap(-16), response);
            return;
        }

        if ("".equals(start)) {
            writeJsonData(getReturnMap(-13), response);
            return;
        }

        if ("".equals(timestamp)) {
            writeJsonData(getReturnMap(-14), response);
            return;
        }

        try {
            //检查timestamp是否过期  超过10分钟无效
            if ((System.currentTimeMillis() - Long.valueOf(timestamp)) / 1000 / 60 > 2) {//timestamp时间过期
                writeJsonData(getReturnMap(-11), response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            writeJsonData(getReturnMap(-17), response);
            return;
        }

        if (!SignUtil.md5(str).equals(sign)) {//签名失败
            writeJsonData(getReturnMap(-10), response);
            return;
        }

        String sql = "select a.`id`, "
                + "  a.`rank_code`,"
                + "  b.house_code,"
                + "   a.rank_type,(CASE WHEN a.`rank_status` = 5 THEN '已出租' else '无效' end) state "
                + "  from yc_houserank_tab as a,yc_house_tab as b,yc_group_tab as c,yc_area_tab e "
                + " where c.`areaid` = e.id "
                + "  and a.isdelete = 1 "
                + "  and b.id = a.house_id"
                + "  and c.id=b.group_id "
                + "  and b.`publish` = 2 "
                + "  AND (a.`rank_status` in(4,5,6)  OR b.house_status not in(5,6,9)) ";
        Map<String, Object> map = getReturnMap(1);
        map.put("data", db.queryForList(sql));
        writeJsonData(map, response);
    }

    public static void main(String[] args) {
        String url = "http://localhost/interfaces/365house/gethouse.do";

        Map<String, Object> map = new HashMap<>();
        map.put("size", 200);
        map.put("start", 0);
        map.put("timestamp", System.currentTimeMillis());
        map.put("date", "2017-06-14");

        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = String.valueOf(map.get(key));
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        String str = content.toString() + "&key=ycqwjhouse";
        System.out.println(str);
        String sign = SignUtil.md5(str);
        map.put("sign", sign);

        System.out.println(url + "?" + content.toString() + "&sign=" + sign);
    }

}
