package zbv5.cn.XiaoOnline.util;

import cn.nukkit.utils.Config;
import zbv5.cn.XiaoOnline.Main;
import zbv5.cn.XiaoOnline.lang.Lang;

import java.io.File;

public class FileUtil
{
    public static Config lang;
    public static Config config;
    public static Config data;
    public static Config reward;

    public static void LoadFile()
    {
        try
        {
            File Config_Yml = new File(Main.getInstance().getDataFolder(), "config.yml");
            if (!Config_Yml.exists())
            {
                Main.getInstance().saveResource("config.yml", false);
            }
            config = new Config(new File(Main.getInstance().getDataFolder() + "/config.yml"));

            File Lang_Yml = new File(Main.getInstance().getDataFolder(), "lang.yml");
            if (!Lang_Yml.exists())
            {
                Main.getInstance().saveResource("lang.yml", false);
            }
            lang = new Config(new File(Main.getInstance().getDataFolder() + "/lang.yml"));

            Lang.LoadLang();

            File Data_Yml = new File(Main.getInstance().getDataFolder(), "data.yml");
            if (!Data_Yml.exists())
            {
                Main.getInstance().saveResource("data.yml", false);
            }
            data = new Config(new File(Main.getInstance().getDataFolder() + "/data.yml"));

            File Reward_Yml = new File(Main.getInstance().getDataFolder(), "reward.yml");
            if (!Reward_Yml.exists())
            {
                Main.getInstance().saveResource("reward.yml", false);
            }
            reward = new Config(new File(Main.getInstance().getDataFolder() + "/reward.yml"));

            PrintUtil.PrintConsole("&a&l√ &a配置文件加载完成.");
        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("&c&l× &4加载配置文件出现问题,请检查服务器.");
            e.printStackTrace();
        }
    }
}
