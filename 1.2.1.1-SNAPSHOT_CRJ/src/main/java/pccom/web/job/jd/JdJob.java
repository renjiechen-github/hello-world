package pccom.web.job.jd;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import pccom.common.util.DBHelperSpring;
import pccom.common.util.StringHelper;

/**
 * 金蝶任务管理
 *
 * @author admin
 *
 */
@Service("jdJob")
public class JdJob {

    public final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    @Resource(name = "dbHelper")
    private DBHelperSpring db;

    /**
     * 最大10个线程同时执行
     */
    private int maxRunThread = 10;

    public void execute() {
        logger.debug("开始同步金蝶信息1----------------------------------");
//		String sql = "SELECT a.id,a.resource_id,a.notiy_type FROM yc_notiy_info a WHERE a.notiy_state not in (2,4)";
        String sql = "SELECT a.id,a.resource_id,a.notiy_type FROM yc_notiy_info a WHERE a.notiy_state =6";
        List<Map<String, Object>> list = db.queryForList(sql);
        //logger.debug("list:"+list);
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String id = StringHelper.get(map, "id");
            JdTaskJob jdTaskJob = new JdTaskJob(id + "", null);
            jdTaskJob.run();
        }
        logger.debug("结束同步金蝶信息----------------------------------");
    }

    public void executeSyn() {
        //Constants.is_test = false;
        logger.debug("开始同步金蝶信息1----------------------------------");
        String sql = "SELECT a.id,a.resource_id,a.notiy_type FROM yc_notiy_info a WHERE a.notiy_state not in (2,4,6) ";
        List<Map<String, Object>> list = db.queryForList(sql);
        CountDownLatch latch = null;
        int maxRun = 0;
        if (list.size() < maxRunThread) {
            maxRun = list.size();
        } else {
            maxRun = maxRunThread;
        }
        latch = new CountDownLatch(maxRun);
//		logger.debug("list:"+list); 
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String id = StringHelper.get(map, "id");
            JdTaskJob jdTaskJob = new JdTaskJob(id + "", latch);
            //加入线程执行
            JdTask.addPool(jdTaskJob);
            if ((i + 1) % maxRun == 0 || i + 1 == list.size()) {//达到最大条数或者是最终的一次就进行等待执行完毕后再执行
                try {
                    logger.debug("等待当前金蝶线程执行完毕,当前执行线程数量：" + latch.getCount());
                    latch.await();
                    if (i + 1 != list.size()) {//没到最后一条就重新初始化计数
                        if (list.size() - (i + 1) >= maxRunThread) {
                            latch = new CountDownLatch(maxRunThread);
                        } else {
                            latch = new CountDownLatch(list.size() - (i + 1));
                        }
                    }
                } catch (Exception e) {
                    logger.error("金蝶线程执行错误：", e);
                }
            }
        }
        logger.debug("结束同步金蝶信息----------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("????????1?????");
        System.out.println(JdJob.class.getResource("ycapi_client.properties"));
        System.out.println("????????2?????");
    }

}
