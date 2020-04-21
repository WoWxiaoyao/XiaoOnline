package zbv5.cn.XiaoOnline.lang;

import zbv5.cn.XiaoOnline.util.FileUtil;
import zbv5.cn.XiaoOnline.util.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class Lang
{
    public static String Prefix = "&6[&bXiaoOnline&6]";
    public static String NoPermission = "{prefix}&c你没有权限这样做";
    public static String NullPlayer = "{prefix}&c玩家&3{player}&c不在线";
    public static String SuccessReload = "{prefix}&a重载完成!";
    public static String FailReload = "{prefix}&c重载失败!请前往控制台查看报错.";
    public static String NullCommand = "{prefix}&c未知指令.";
    public static String NullReward = "{prefix}&c服务器设定奖励异常,请联系管理处理.";
    public static String NeedPlayer = "{prefix}&&c只有玩家才能执行该操作.";

    public static List<String> Show = new ArrayList<String>();
    public static List<String> ShowNull = new ArrayList<String>();
    public static String Status_Already = "&7(&4已领取&7)";
    public static String Status_Can = "&7(&6可领取&7)";
    public static String Status_TimeInsufficient = "&7(&3时间不足&7)";

    public static String Reward_Already = "{prefix}&c你已经领取过该奖励了,请明天再来吧.";
    public static String Reward_TimeInsufficient = "{prefix}&c今日在线时间不足,请稍后再来.";

    public static String NewDay = "{prefix}&a新的一天到来了，数据已重置.";

    public static void LoadLang()
    {
        try
        {
            Prefix = FileUtil.lang.getString("Prefix");
            NoPermission = FileUtil.lang.getString("NoPermission");
            NullPlayer = FileUtil.lang.getString("NullPlayer");
            SuccessReload = FileUtil.lang.getString("SuccessReload");
            FailReload = FileUtil.lang.getString("FailReload");
            NullCommand = FileUtil.lang.getString("NullCommand");
            NullReward = FileUtil.lang.getString("NullReward");
            NeedPlayer = FileUtil.lang.getString("NeedPlayer");

            Show = FileUtil.lang.getStringList("Show");
            ShowNull = FileUtil.lang.getStringList("ShowNull");

            Status_Already = FileUtil.lang.getString("Status_Already");
            Status_Can = FileUtil.lang.getString("Status_Can");
            Status_TimeInsufficient = FileUtil.lang.getString("Status_TimeInsufficient");
            Reward_Already = FileUtil.lang.getString("Reward_Already");
            Reward_TimeInsufficient = FileUtil.lang.getString("Reward_TimeInsufficient");
            NewDay = FileUtil.lang.getString("NewDay");
            PrintUtil.PrintConsole("&a&l√ &a语言文件加载完成.");
        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("&3lang.yml &c读取出现问题!");
            e.printStackTrace();
        }
    }
}
