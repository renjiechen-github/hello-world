/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pccom.common.reloadSpring;

import java.io.IOException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pccom.common.util.Constants;
import pccom.common.util.SpringHelper;

/**
 *
 * @author eliay 23 48
 */
@Service
public class ReloadSpring {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SpringHelper springHelper;

    public static boolean isStartReloadSpring = false;

    /**
     * 等待用户输入
     *
     * @throws IOException
     */
    public void reload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (isStartReloadSpring) {
                    return;
                }
                if (Constants.is_test) {
                    while (true) {
                        Scanner input = new Scanner(System.in);
                        System.out.println("********************************************************************");
                        System.out.println("*********************开启输入‘1’回车即可重新加载spring配置文件****************");
                        System.out.println("******#在spring对应的文件中添加方法后建议重启配置文件，否则不生效*************");
                        System.out.println("******请到官网：https://github.com/spring-projects/spring-loaded 下载springloaded-1.2.5.RELEASE.jar  自行百度如何使用 ，如不使用此，那么此重启对于添加方法无效*************");
                        System.out.println("**************************真实环境此项设置自动无效********************");
                        System.out.println("*********************如出现一直加载的情况请直接重启服务********************");
                        System.out.println("********************************************************************");
                        try {
                            String str = input.next();
                            if ("1".equals(str)) {
                                logger.info("********************************************************************");
                                logger.info("*************************开始重新加载spring配置文件******************");
                                logger.info("********************************************************************");
                                springHelper.reload();
                            } else {
                                logger.warn("********************************************************************");
                                logger.warn("*********************输入错误，输入‘1’即可重新加载spring配置文件***********");
                                logger.warn("********************************************************************");
                            }
                            Thread.sleep(100);
                        } catch (Exception e) {
                            logger.error("刷新springbean出现错误，建议重启服务：", e);
                        }
                    }
                }
            }
        }).start();
    }

}
