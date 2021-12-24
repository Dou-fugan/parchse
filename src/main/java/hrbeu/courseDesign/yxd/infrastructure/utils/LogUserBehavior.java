package hrbeu.courseDesign.yxd.infrastructure.utils;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogUserBehavior {
    public static void logUserBehavior(int userId,String action){
        Calendar calendar = Calendar.getInstance();
        final Logger logger = LoggerFactory.getLogger("UserBehaviorAppender");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String id=String.valueOf(userId);
        String time=formatter.format(calendar.getTime());
        ThreadContext.put("userId", id);
        ThreadContext.put("time",time);
        ThreadContext.put("usrAction",action);
        logger.info("日志已经写入数据库，用户编号为:" + id+ ",上传时间为："+ time+"动作为："+action);
    }
}
