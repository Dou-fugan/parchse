package hrbeu.courseDesign.yxd.infrastructure.utils;/*
@date 2021/6/28 - 2:13 下午
*/


import java.text.SimpleDateFormat;
import java.util.Date;


public class getID {

    public static String returnID() {
        //todo 自定义id代码编写位置
        String id = getId();
        String ran = randomNumber();
        return id + ran;
    }

    public static String getId() {

        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd-HH-mm-ss-");
        return ft.format(dNow);
    }

    /**
     * 生成自增的一个数字字符串
     *
     */
    public static String randomNumber() {
        int ran = (int)(Math.random()*9000)+1000;
        return String.valueOf(ran);
    }
}