package pccom.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;

/**
 * 从网络获取图片到本地 
 * @author 雷杨
 * @date 2014-10-09
 */
public class GetImage {
	
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(GetImage.class);
	
    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        String url = "http://10.32.171.89/jump/Jump?url=https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQFE8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xLzhFaWdDOGpsX2hxRGRwOE5qR1FCAAIENdssVQMEAAAAAA%3D%3D&method=GET&params=download";
        //		byte[] btImg = getImageFromNetByUrl(url);
        //		if (null != btImg && btImg.length > 0)
        //		{
        //			String fileName = "百度.jpg";
        //			writeImageToDisk(btImg, fileName);
        //		}
        //		else
        //		{
        //			logger.debug("没有从该连接获得内容");
        //		}
        GetImage.getUrlImg(url, "d:\\百度11.jpg");
    }

    /**
     * 获取链接图片
     * @author 雷杨 2014-10-09
     * @param imgUrl
     * @param savePath
     */
    public static void getUrlImg(String imgUrl, String savePath) {
        byte[] btImg = getImageFromNetByUrl(imgUrl);
        if(null != btImg && btImg.length > 0) {
            writeImageToDisk(btImg, savePath);
        } else {
            logger.debug("没有从该连接获得内容");
        }
    }

    /**
     * 将图片写入到磁盘
     * @author 雷杨 2014-10-09
     * @param img
     * @param filePath
     */
    public static void writeImageToDisk(byte[] img, String filePath) {
        try {
            File file = new File(filePath);
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(img);
            fops.flush();
            fops.close();
            logger.debug("图片已经写入到" + filePath);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据地址获得数据的字节流
     * @author 雷杨 2014-10-09
     * @param strUrl
     * @return
     */
    public static byte[] getImageFromNetByUrl(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
            return btImg;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从输入流中获取数据
     * @author 雷杨 2014-10-09
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
