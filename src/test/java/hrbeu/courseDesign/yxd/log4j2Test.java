package hrbeu.courseDesign.yxd;

import org.apache.logging.log4j.ThreadContext;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

public class log4j2Test {
    @Test
    public void log4j2Run(){

        final Logger logger = LoggerFactory.getLogger(this.getClass());
        int id=22;
        String time=new Timestamp(System.currentTimeMillis()).toString();

        ThreadContext.put("user_id", String.valueOf(id));
        ThreadContext.put("time",time);
        ThreadContext.put("action","测试动作记录");
        logger.debug("日志已经写入数据库，用户编号为:" + id+ ",上传时间为："+ time+"动作为："+"测试动作记录");
        logger.info("日志已经写入数据库，用户编号为:" + id+ ",上传时间为："+ time+"动作为："+"测试动作记录");
        logger.error("日志已经写入数据库，用户编号为:" + id+ ",上传时间为："+ time+"动作为："+"测试动作记录");
    }
}
