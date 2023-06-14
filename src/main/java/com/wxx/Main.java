package com.wxx;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.wxx.uitl.DateUtil;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: wangxinxin-hj
 * @date: 2022/2/13 18:33
 */
public class Main {

    public static void main(String[] args) {
//        Date date = DateUtil.getDateFromStrFull("2021-02-01 10:00:00");
//        calcRecentLogin(1L, 2, date);

//        int good = calc("good");
//        System.out.println(good);
//
//        System.out.println("===============");
//        int test = calc("test");
//        System.out.println(test);


//        province();

//        String yearWeek = getYearWeek(new Date());
//        System.out.println(yearWeek);

//        int i = 229861929 % 3;
//        System.out.println(i);

//        String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").format(new Date());
//        System.out.println(str);



    }
    public static String getYearWeek(Date date) {
        //跨年时周需要特殊处理
        DateTime weekDateTime = new DateTime(date);
        if (weekDateTime.getMonthOfYear() >= 11 && weekDateTime.getWeekOfWeekyear() <= 1) {
            return (weekDateTime.getYear() + 1) + "01";
        }else if(weekDateTime.getMonthOfYear() == 1 && weekDateTime.getWeekOfWeekyear() > 50) {
            return (weekDateTime.getYear() - 1) + "" + weekDateTime.getWeekOfWeekyear();
        }else{
            return weekDateTime.toString("yyyyww");
        }
    }

    public static BiMap<String, String> hotProvince = HashBiMap.create();

    static {
        hotProvince.put("北京", "beijing");
        hotProvince.put("上海", "shanghai");
        hotProvince.put("天津", "tianjin");
        hotProvince.put("重庆", "chongqin");
        hotProvince.put("江苏", "jiangsu");
        hotProvince.put("浙江", "zhejiang");
        hotProvince.put("山东", "shandong");
        hotProvince.put("广东", "guangdong");
    }

    ;
    public static BiMap<String, String> otherProvince = HashBiMap.create();

    static {
        otherProvince.put("河北", "hebei");
        otherProvince.put("贵州", "guizhou");
        otherProvince.put("山西", "shanxi");
        otherProvince.put("辽宁", "liaoning");
        otherProvince.put("吉林", "jilin");
        otherProvince.put("黑龙江", "heilongjiang");
        otherProvince.put("福建", "fujian");
        otherProvince.put("江西", "jiangxi");
        otherProvince.put("河南", "henan");
        otherProvince.put("湖北", "hubei");
        otherProvince.put("湖南", "hunan");
        otherProvince.put("四川", "sichuan");
        otherProvince.put("云南", "yunnan");
        otherProvince.put("陕西", "shanxi2");
        otherProvince.put("安徽", "anhui");
        otherProvince.put("广西", "guangxi");
        otherProvince.put("新疆", "xinjiang");
        otherProvince.put("其他", "qita");
    }

    ;

    public static BiMap<String, String> biProvinceList = HashBiMap.create();

    static {
        biProvinceList.putAll(hotProvince);
        biProvinceList.putAll(otherProvince);
    }

    private static void province() {
        Set<String> provinceList = biProvinceList.values();
        for (String province : provinceList) {
            System.out.println(province);
        }
    }

    private static int calc(String passwd) {
        int sum = 0;
        for (int i = 1; i <= passwd.length(); i++) {
            int cur = sumCurrent(passwd, i);
            System.out.println(cur);
            sum += cur;
        }
        return sum;
    }
    private static int sumCurrent(String passwd, int size) {
        int sum = 0;
        for (int i = 0; i <= passwd.length() - size; i++) {
            Set<Character> cs = new HashSet<>();
            for (int k = i; k < size+i; k++) {
                cs.add(passwd.charAt(k));
            }
            sum += cs.size();
        }
        return sum;
    }




    public static boolean calcRecentLogin(Long uid, int skipLoginDay, Date startDate) {
        //处理跨月问题
        int dayInMonth = Integer.parseInt(DateUtil.getFormatDate(startDate, "dd"));
        int lastMonthDay = skipLoginDay - dayInMonth + 1;
        if (dayInMonth != 1 && lastMonthDay > 0) {
            String monthFirstDate = DateUtil.getFormatDate(DateUtil.getMonthFirstDay(startDate), "yyyy-MM-dd") + " 00:00:00";
            String notLoginEndDay = DateUtil.getFormatDate(startDate, "yyyy-MM-dd") + " 00:00:00";
            return isNotLoginRecently(uid, monthFirstDate, notLoginEndDay) && calcRecentLogin(uid, lastMonthDay, DateUtil.getMonthFirstDay(startDate));
        }

        String notLoginStartDay = DateUtil.getFormatDate(DateUtil.addSecond(startDate, -86400L * skipLoginDay), "yyyy-MM-dd") + " 00:00:00";
        String notLoginEndDay = DateUtil.getFormatDate(startDate, "yyyy-MM-dd") + " 00:00:00";
        String shouldLoginDay = DateUtil.getFormatDate(DateUtil.addSecond(startDate, -86400L * (skipLoginDay + 1)), "yyyy-MM-dd") + " 00:00:00";
        return isNotLoginRecently(uid, notLoginStartDay, notLoginEndDay) && !isNotLoginRecently(uid, shouldLoginDay, notLoginStartDay);
    }

    //判断时间段内是否未登录
    private static boolean isNotLoginRecently(Long uid, String startDay, String endDay) {
//        ClientResWrapper<JSONArray> loginLog = pepperClient.getLoginLog(uid, startDay, endDay, 1);
//        if (loginLog != null && loginLog.getData() != null && !loginLog.getData().isEmpty()) {
//            log.info("uid={} has login recent! ", uid);
//            return false;
//        }
//        return true;
        return true;
    }
}
