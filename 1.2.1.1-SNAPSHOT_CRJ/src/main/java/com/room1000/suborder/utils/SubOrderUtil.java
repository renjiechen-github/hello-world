package com.room1000.suborder.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;

import com.alibaba.fastjson.JSONObject;
import com.room1000.core.activiti.IProcessDeploy;
import com.room1000.core.activiti.IProcessTask;
import com.room1000.core.activiti.impl.ProcessDeployImpl;
import com.room1000.core.activiti.impl.ProcessTaskImpl;
import com.room1000.core.exception.BaseAppException;
import com.room1000.core.user.dto.StaffDto;
import com.room1000.core.user.dto.UserDto;
import com.room1000.core.user.service.IUserService;
import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderValueDto;
import com.room1000.suborder.cancelleaseorder.service.ICancelLeaseOrderService;
import com.room1000.suborder.cleaningorder.dto.CleaningOrderDto;
import com.room1000.suborder.cleaningorder.service.ICleaningOrderService;
import com.room1000.suborder.repairorder.dto.RepairOrderDto;
import com.room1000.suborder.repairorder.service.IRepairOrderService;
import com.room1000.workorder.define.WorkOrderTypeDef;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;
import com.ycqwj.ycqwjApi.request.PlushRequest;
import com.ycqwj.ycqwjApi.request.WeiXinRequest;
import com.ycqwj.ycqwjApi.request.bean.plush.AppointPlushBean;
import com.ycqwj.ycqwjApi.request.bean.plush.ServerAssessBean;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import pccom.common.util.Constants;

import pccom.common.util.FtpUtil;
import pccom.common.util.SpringHelper;
import pccom.web.beans.Login;
import pccom.web.beans.SystemConfig;
import pccom.web.beans.User;
import pccom.web.interfaces.Financial;
import pccom.web.listener.SessionManage;

/**
 *
 * Description: 子订单工具类
 *
 * Created on 2017年3月3日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public final class SubOrderUtil {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(SubOrderUtil.class);

    /**
     * SubOrderUtil
     */
    private SubOrderUtil() {

    }

    /**
     *
     * Description: 生成code
     *
     * @author jinyanan
     *
     * @return String
     */
    public static String generatorSubOrderCode() {
        logger.info("generatorSubOrderCode start");
        // 由于DateUtil.getDBDateTime()不带时分秒，所以还是使用new Date()
        long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        return String.valueOf(nowLong);
    }

    /**
     *
     * Description: 编译流程(需要代码中编译生成流程实例，需要引用src下applicationContext-jdbc.xml文件)
     *
     * @author jinyanan
     *
     * @param processName 流程名称
     * @param processPath 流程图目录
     *
     */
    public static void deployProcess(String processName, String processPath) {
        logger.info("deployProcess start");
        logger.debug("processName: " + processName);
        logger.debug("processPath: " + processPath);

        IProcessDeploy deploy = new ProcessDeployImpl();
        deploy.deployFlow(processName, processPath);
    }

    /**
     *
     * Description: 根据workOrderId回单操作
     *
     * @author jinyanan
     *
     * @param workOrderId workOrderId
     */
    public static void dispatcherOrder(Long workOrderId) {
        logger.info("dispatcherOrder start");
        logger.debug("workOrderId: " + workOrderId);
        IProcessTask task = new ProcessTaskImpl();
        task.dispatcherOrder(workOrderId);
    }

    /**
     *
     * Description: 根据workOrderCode回单操作
     *
     * @author jinyanan
     *
     * @param code code
     */
    public static void dispatcherOrder(String code) {
        logger.info("dispatcherOrder start");
        logger.debug("code: " + code);
        IProcessTask task = new ProcessTaskImpl();
        IWorkOrderService workOrderService = (IWorkOrderService) SpringHelper.getBean("WorkOrderService");
        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
        if (workOrderDto != null) {
            task.dispatcherOrder(workOrderDto.getId());
        }
    }

    /**
     *
     * Description: 创建历史
     *
     * @author jinyanan
     *
     * @param <T> <T>
     * @param from from
     * @param hisClazz hisClazz
     * @param priority priority
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T createHisDto(Object from, Class<?> hisClazz, Long priority) {
        List<String> excludesList = new ArrayList<>();
        excludesList.add("priority");
        Method[] fromMethods = from.getClass().getDeclaredMethods();
        Method[] toMethods = hisClazz.getDeclaredMethods();

        Method fromMethod = null;
        Method toMethod = null;
        String fromMethodName = null;
        String toMethodName = null;
        T to = null;
        try {
            to = (T) hisClazz.newInstance();
            for (int i = 0; i < fromMethods.length; i++) {
                fromMethod = fromMethods[i];
                fromMethodName = fromMethod.getName();
                if (!fromMethodName.contains("get")) {
                    continue;
                }
                // 排除列表检测
                if (excludesList != null && excludesList.contains(fromMethodName.substring(3).toLowerCase())) {
                    continue;
                }
                toMethodName = "set" + fromMethodName.substring(3);
                toMethod = findMethodByName(toMethods, toMethodName);
                if (toMethod == null) {
                    continue;
                }
                Object value = fromMethod.invoke(from);
                if (value == null) {
                    continue;
                }
                toMethod.invoke(to, value);

            }

            toMethod = findMethodByName(toMethods, "setPriority");

            toMethod.invoke(to, priority);

        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | InstantiationException e) {
            logger.error("createHisDto error");
        }

        return to;
    }

    /**
     *
     * Description: 创建历史dto
     *
     * @author jinyanan
     *
     * @param from from
     * @param to to
     * @param priority priority
     */
    public static void createdHisDto(Object from, Object to, Long priority) {
        logger.info("createdHisDto start");
        logger.debug("from: " + from);
        logger.debug("to: " + to);
        logger.debug("priority: " + priority);
        List<String> excludesList = new ArrayList<>();
        excludesList.add("priority");
        Method[] fromMethods = from.getClass().getDeclaredMethods();
        Method[] toMethods = to.getClass().getDeclaredMethods();

        Method fromMethod = null;
        Method toMethod = null;
        String fromMethodName = null;
        String toMethodName = null;

        for (int i = 0; i < fromMethods.length; i++) {
            fromMethod = fromMethods[i];
            fromMethodName = fromMethod.getName();
            if (!fromMethodName.contains("get")) {
                continue;
            }
            // 排除列表检测
            if (excludesList != null && excludesList.contains(fromMethodName.substring(3).toLowerCase())) {
                continue;
            }
            toMethodName = "set" + fromMethodName.substring(3);
            toMethod = findMethodByName(toMethods, toMethodName);
            if (toMethod == null) {
                continue;
            }
            Object value;
            try {
                value = fromMethod.invoke(from);
                if (value == null) {
                    continue;
                }
                toMethod.invoke(to, value);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                logger.error("createHisDto error");
            }

        }

        toMethod = findMethodByName(toMethods, "setPriority");
        try {
            toMethod.invoke(to, priority);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.error("createHisDto error");
        }
    }

    /**
     * 从方法数组中获取指定名称的方法
     *
     * @param methods methods
     * @param name name
     * @return Method
     */
    private static Method findMethodByName(Method[] methods, String name) {
        for (Method method : methods) {
            if (name.equals(method.getName())) {
                return method;
            }
        }
        return null;
    }

    /**
     * 文件ftp图片访问前缀
     */
    private static final String FTP_HTTP_PATH = "FTP_HTTP";

    /**
     * 临时文件存放路径
     */
    private static final String LOCAL_TMP_PATH = "upload/tmp/";

    /**
     * 如 /data/order
     */
    private static final String FTP_ORDER_HTTP_PATH = "FTP_ORDER_HTTP_PATH";

    /**
     * 如 /order/
     */
    private static final String FTP_ORDER_PATH = "FTP_ORDER_PATH";

    /**
     * 竖线 "|"
     */
    private static final String VERTICAL_LINE = "|";

    /**
     * 逗号 ","
     */
    private static final String COMMA = ",";

    /**
     * 句号 "."
     */
    private static final String PERIOD = ".";

    /**
     * 斜线 "/"
     */
    private static final String VIRGULE = "/";

    /**
     * 减号 "-"
     */
    private static final String MINUS = "-";

    /**
     *
     * Description: 获取图片的绝对路径
     *
     * @author jinyanan
     *
     * @param images 数据库中存的路径
     * @return 图片url路径，以逗号分隔
     */
    public static String getImagePath(String images) {
        logger.info("getImagePath start");
        logger.debug("images: " + images);
        String newPath = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(images)) {
            for (String path : images.split("\\|")) {
                String modifiedPath = StringUtils.EMPTY;
                if (path.contains("/wx/")) {
                    modifiedPath = SystemConfig.getValue(FTP_HTTP_PATH) + SystemConfig.getValue("WX_FTP_IMG_HTTP_PATH") + path;
                } else {
                    modifiedPath = SystemConfig.getValue(FTP_HTTP_PATH) + SystemConfig.getValue(FTP_ORDER_HTTP_PATH) + path;
                }
                newPath += modifiedPath + ",";
            }
            return newPath.substring(0, newPath.length() - 1);
        }
        return StringUtils.EMPTY;
    }

    /**
     *
     * Description: 上传图片
     *
     * @author jinyanan
     *
     * @param images 临时文件图片地址
     * @param workOrderId 工单主键
     * @param realPath 工程tomcat部署路径
     * @return String ftp上传图片后生成的新url
     */
    public static String uploadImage(String images, Long workOrderId, String realPath) {
        logger.info("uploadImage start");
        logger.debug("images: " + images);
        logger.debug("workOrderId: " + workOrderId);
        logger.debug("realPath: " + realPath);

        String newPath = StringUtils.EMPTY;

        FtpUtil ftp = null;
        try {
            ftp = new FtpUtil();
            if (StringUtils.isNotEmpty(images)) {
                String[] pathArray = images.split(COMMA);
                for (String path : pathArray) {

                    // 文件后缀
                    String imageSuffix = path.substring(path.lastIndexOf(PERIOD));

                    // 如果文件已存在
                    if (path
                            .contains(SystemConfig.getValue(FTP_HTTP_PATH) + SystemConfig.getValue(FTP_ORDER_HTTP_PATH))) {
                        newPath += VIRGULE + path.replace(LOCAL_TMP_PATH, StringUtils.EMPTY).replace(
                                SystemConfig.getValue(FTP_HTTP_PATH) + SystemConfig.getValue(FTP_ORDER_HTTP_PATH),
                                StringUtils.EMPTY) + VERTICAL_LINE;
                        continue;
                    } else if (path
                            .contains(SystemConfig.getValue(FTP_HTTP_PATH) + SystemConfig.getValue("WX_FTP_IMG_PATH"))) {
                        newPath += VIRGULE + path.replace(
                                SystemConfig.getValue(FTP_HTTP_PATH) + SystemConfig.getValue("WX_FTP_IMG_PATH"),
                                StringUtils.EMPTY) + VERTICAL_LINE;
                        continue;
                    }

                    String tmpPath = workOrderId + UUID.randomUUID().toString().replaceAll(MINUS, StringUtils.EMPTY)
                            + imageSuffix;
                    newPath += VIRGULE + workOrderId + VIRGULE + tmpPath + VERTICAL_LINE;
                    boolean success = ftp.uploadFile(realPath + path, tmpPath,
                            SystemConfig.getValue(FTP_ORDER_PATH) + workOrderId + VIRGULE);
                    if (!success) {
                        return null;
                    }
                }
            }
        } catch (Exception e) {
            return null;
        } finally {
            // 关闭流
            if (ftp != null) {
                ftp.closeServer();
            }
        }
        return newPath;
    }

    /**
     *
     * Description: 发送推送
     *
     * @author jinyanan
     *
     * @param workOrder workOrder
     * @param staffId 如果该值为空，则根据workOrder中的手机号查询客户进行发送推送，如果不为空，则给员工发送推送
     * @param model 模块，例如（工单系统-退租订单）
     */
    public static void sendMessage(WorkOrderDto workOrder, Long staffId, String model) {
        logger.info("SubOrderUtil sendMessage start");
        logger.info("workOrder: " + workOrder);
        logger.info("staffId: " + staffId);
        logger.info("model: " + model);

        final String message = "您有一份新的服务订单需要处理，请注意查收";
        logger.info("发送推送消息到用户手机号");
        String id = "";
        String extrasparam = "";
        IUserService userService = (IUserService) SpringHelper.getBean("UserService");
        if (null == staffId) {
            UserDto userDto = new UserDto();
            userDto.setMobile(workOrder.getUserPhone());
            List<UserDto> userDtoList = userService.qryUserByCond(userDto);
            if (userDtoList != null && userDtoList.size() > 0) {
                id = String.valueOf(userDtoList.get(0).getId());
            } else {
                // 如果根据手机号没有查到，则不发送推送
                logger.info("如果根据手机号没有查到，则不发送推送");
                return;
            }
        } else {
            StaffDto staffDto = new StaffDto();
            staffDto.setId(staffId);
            List<StaffDto> staffDtoList = userService.qryStaffByCond(staffDto);
            if (staffDtoList != null && staffDtoList.size() > 0) {
                id = String.valueOf(staffDtoList.get(0).getId());
            } else {
                // 如果根据staffId没有查到，则不发送推送
                logger.info("如果根据staffId没有查到，则不发送推送");
                return;
            }
        }
        Map<String, String> param = new HashMap<>();
        param.put("messag_type", "1");
        param.put("item_type", getItemType(workOrder.getType()));
        param.put("item_title", model);
        // param.put("item_add", value);
        param.put("item_id", workOrder.getId().toString());
        param.put("item_code", workOrder.getCode());
        param.put("item_time", DateUtil.date2String(workOrder.getCreatedDate(), DateUtil.DATETIME_FORMAT_1));
        param.put("comments", workOrder.getSubComments());
        extrasparam = JSONObject.toJSONString(param);
        logger.info("发送推送消息到用户手机号" + id+"---"+staffId+"---"+workOrder.getId());
        if (!"".equals(id)) {
            if (staffId == null) {
                ServerAssessBean serverAssessBean = new ServerAssessBean();
                serverAssessBean.setKey(Constants.YCQWJ_API);
                serverAssessBean.setContent(message);
                serverAssessBean.setId(id); 
                serverAssessBean.setResource_id(workOrder.getId().toString());
                serverAssessBean.setExreas_param(extrasparam);
                try {
                    PlushRequest.serverAssess(serverAssessBean);
                    // 微信的评价推送
                    WeiXinRequest.sendServerCompletedSuccId(workOrder.getId().toString(), id, Constants.YCQWJ_API, "您好！您的服务已经完成", model+"-"+workOrder.getName(), DateUtil.date2String(workOrder.getCreatedDate(), DateUtil.DATE_FORMAT_1), "为了给您提供更优质的服务，请对我们的服务做出评价。");
                } catch (IOException ex) {
                    logger.error("消息发送失败：" + extrasparam + "--" + id, ex);
                } catch (NoSuchAlgorithmException ex) {
                    logger.error("消息发送失败：" + extrasparam + "--" + id, ex);
                }
                
                
            } else {
                AppointPlushBean appointPlushBean = new AppointPlushBean();
                appointPlushBean.setKey(Constants.YCQWJ_API);
                appointPlushBean.setContent(message);
                appointPlushBean.setId(id);
                appointPlushBean.setExreas_param(extrasparam);
                try {
                    PlushRequest.appointPlush(appointPlushBean);
                } catch (IOException ex) {
                    logger.error("消息发送失败：" + extrasparam + "--" + id, ex);
                } catch (NoSuchAlgorithmException ex) {
                    logger.error("消息发送失败：" + extrasparam + "--" + id, ex);
                }
            }
        }
    }

    /**
     *
     * Description: 根据订单类型，返回C端对应的类型
     *
     * @author jinyanan
     *
     * @param workOrderType workOrderType
     * @return String
     */
    private static String getItemType(String workOrderType) {
        switch (workOrderType) {
            case WorkOrderTypeDef.HOUSE_LOOKING_ORDER:
                return "0";
            case WorkOrderTypeDef.REPAIR_ORDER:
                return "1";
            case WorkOrderTypeDef.CLEANING_ORDER:
                return "2";
            case WorkOrderTypeDef.COMPLAINT_ORDER:
                return "3";
            case WorkOrderTypeDef.LIVING_PROBLEM_ORDER:
                return "4";
            case WorkOrderTypeDef.CANCEL_LEASE_ORDER:
                return "5";
            case WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER:
            		return "5";
            default:
                return "";
        }
    }
    
    /**
     *
     * Description: 插入财务系统
     *
     * @author jinyanan
     *
     * @param workOrderDto 工单
     * @param agreementId 合约主键(可为出租合约，也可为收房合约)
     * @param actualDealerId 操作人
     */
    public static void apply2Pay(WorkOrderDto workOrderDto, Long agreementId, Long actualDealerId) {
        logger.debug(
                "SubOrderUtil.apply2Pay start ---------------------------------------------------------------------------");
        Financial financial = null;
        try {
            financial = (Financial) SpringHelper.getBean("financial");
        } catch (BeansException e) {
            return;
        }
        // 退租单
        if (WorkOrderTypeDef.CANCEL_LEASE_ORDER.equals(workOrderDto.getType())) {
            ICancelLeaseOrderService cancelLeaseService = (ICancelLeaseOrderService) SpringHelper
                    .getBean("CancelLeaseOrderService");
            CancelLeaseOrderValueDto cancelLeaseOrderValueDto = new CancelLeaseOrderValueDto();
            cancelLeaseOrderValueDto.setSubOrderId(workOrderDto.getRefId());
            cancelLeaseOrderValueDto.setAttrPath("CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.TOTAL_MONEY");
            cancelLeaseOrderValueDto = cancelLeaseService.selectByAttrPath(cancelLeaseOrderValueDto);

            updateWorkOrderMoney(workOrderDto,
                    new Float(Float.valueOf(cancelLeaseOrderValueDto.getTextInput()) * 100).longValue(), 0L);
        } // 维修单
        else if (WorkOrderTypeDef.REPAIR_ORDER.equals(workOrderDto.getType())) {
            IRepairOrderService repairOrderService = (IRepairOrderService) SpringHelper.getBean("RepairOrderService");
            RepairOrderDto repairOrderDto = repairOrderService.getOrderDetailById(workOrderDto.getRefId(), true);

            // 计算需要支付的金额，并换算金额进制单位
            Long needPaidMoney = repairOrderDto.getPayableMoney() - repairOrderDto.getPaidMoney();
            Float money = needPaidMoney.floatValue() / 100;
            Map<String, String> params = new HashMap<>();
            params.put("agreement_id", String.valueOf(agreementId));
            params.put("cost", String.valueOf(money));
            params.put("operid", String.valueOf(actualDealerId));
            params.put("order_id", String.valueOf(workOrderDto.getId()));
            financial.orderRent(params);

            updateWorkOrderMoney(workOrderDto, repairOrderDto.getPayableMoney(), repairOrderDto.getPaidMoney());
        } // 保洁单
        else if (WorkOrderTypeDef.CLEANING_ORDER.equals(workOrderDto.getType())) {
            ICleaningOrderService cleaningOrderService = (ICleaningOrderService) SpringHelper
                    .getBean("CleaningOrderService");
            CleaningOrderDto cleaningOrderDto = cleaningOrderService.getOrderDetailById(workOrderDto.getRefId(), true);

            logger.debug("开始支付9+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            // 计算需要支付的金额，并换算金额进制单位
            Float money = cleaningOrderDto.getCleaningMoney().floatValue() / 100;
            Map<String, String> params = new HashMap<>();
            params.put("agreement_id", String.valueOf(agreementId));
            params.put("cost", String.valueOf(money));
            params.put("operid", String.valueOf(actualDealerId));
            params.put("order_id", String.valueOf(workOrderDto.getId()));
            financial.orderRent(params);

            updateWorkOrderMoney(workOrderDto, cleaningOrderDto.getCleaningMoney(), 0L);
        } else if (WorkOrderTypeDef.OWNER_CANCEL_LEASE_ORDER.equals(workOrderDto.getType())) {
            // 业主退租申请支付
            int result = financial.orderTakeHousePayNew(workOrderDto, actualDealerId.intValue());
            if (result == -1) {
                throw new BaseAppException("未查询到合约信息");
            } else if (result == -2) {
                throw new BaseAppException("未查询到房间信息");
            } else if (result == 0) {
                throw new BaseAppException("操作失败");
            } else if (result > 1) {
                logger.info("申请支付成功");
            } else {
                throw new BaseAppException("操作失败");
            }
        }
        logger.debug(
                "SubOrderUtil.apply2Pay end ---------------------------------------------------------------------------");
    }

    /**
     *
     * Description: 修改workOrder中跟金额相关的参数
     *
     * @author jinyanan
     *
     * @param workOrderDto workOrderDto
     * @param payableMoney payableMoney
     * @param paidMoney paidMoney
     */
    private static void updateWorkOrderMoney(WorkOrderDto workOrderDto, Long payableMoney, Long paidMoney) {
        IWorkOrderService workOrderService = (IWorkOrderService) SpringHelper.getBean("WorkOrderService");
        workOrderDto.setPayableMoney(payableMoney.toString());
        workOrderDto.setPaidMoney(paidMoney.toString());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

    }

    /**
     *
     * Description: 根据userName在session中查询登录信息
     *
     * @author jinyanan
     *
     * @param userName userName
     * @return User
     */
    public static User getUserByUserName(String userName) {
        logger.info("getUserByUserName start");
        logger.debug("userName: " + userName);
        Object o = SessionManage.getSession(userName);
        if (null == o) {
            o = SessionManage.getSession("account" + userName);
        }
        if (null == o) {
            return null;
        }
        Login login = (Login) o;
        return login.getUser();
    }

}
