package hrbeu.courseDesign.yxd.infrastructure.AOP;

import hrbeu.courseDesign.yxd.domain.shiro.entity.User;
import hrbeu.courseDesign.yxd.infrastructure.annotation.LogRecord;
import hrbeu.courseDesign.yxd.infrastructure.utils.LogUserBehavior;
import hrbeu.courseDesign.yxd.infrastructure.utils.ShiroUtils;
import org.apache.logging.log4j.ThreadContext;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class LogRecordAOP {
    //动态解析
    private SpelExpressionParser spelParser = new SpelExpressionParser();

    final Logger logger = LoggerFactory.getLogger("UserBehaviorLogger");
    ThreadLocal<Long> beginTime = new ThreadLocal<>();
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    @Pointcut("@annotation(logRecord)")
    public void serviceStatistics(LogRecord logRecord) {
    }

    @Before("serviceStatistics(logRecord)")
    public void doBefore(JoinPoint joinPoint, LogRecord logRecord) {
        // 记录请求到达时间
        beginTime.set(System.currentTimeMillis());
        String time=formatter.format(calendar.getTime());
//        ThreadContext.put("userId", logRecord.id());
        ThreadContext.put("time",time);
//        ThreadContext.put("usrAction",logRecord.note());
    }

    @After("serviceStatistics(logRecord)")
    public void doAfter(JoinPoint joinPoint,LogRecord logRecord) throws Exception{

        User user = (User) SecurityUtils.getSubject().getPrincipal();

        //获取方法的参数名和参数值
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        List<String> paramNameList = Arrays.asList(methodSignature.getParameterNames());
        List<Object> paramList = Arrays.asList(joinPoint.getArgs());

        //将方法的参数名和参数值一一对应的放入上下文中
        EvaluationContext ctx = new StandardEvaluationContext();
        for (int i = 0; i < paramNameList.size(); i++) {
            ctx.setVariable(paramNameList.get(i), paramList.get(i));
        }

        // 解析SpEL表达式获取结果
        int b = 0;
        switch (logRecord.type()){
            case "createA":
                b =(int) spelParser.parseExpression(logRecord.body()).getValue(ctx);
                break;
        }


        ThreadContext.put("userId", String.valueOf(user.getId()));
        ThreadContext.put("usrAction","b的值为 "+b);

        long timeConsuming=System.currentTimeMillis() - beginTime.get();
        ThreadContext.put("timeConsuming",String.valueOf(timeConsuming));
        logger.info("");
    }

}