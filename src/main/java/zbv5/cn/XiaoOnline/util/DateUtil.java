package zbv5.cn.XiaoOnline.util;

import cn.nukkit.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
    public static String getDate(String s)
    {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(s);
        return dateFormat.format(date);
    }

    public static String getFormat(int s)
    {
        return s % (24 * 60 * 60) / (60 * 60) + "小时"+s % (24 * 60 * 60) % (60 * 60) / 60 + "分钟"+s % (24 * 60 * 60) % (60 * 60) % 60 + "秒";
    }
}
