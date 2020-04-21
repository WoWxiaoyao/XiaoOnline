package zbv5.cn.XiaoOnline.util;

import cn.nukkit.command.CommandSender;
import zbv5.cn.XiaoOnline.Main;
import zbv5.cn.XiaoOnline.command.OnlineCommand;
import zbv5.cn.XiaoOnline.lang.Lang;
import zbv5.cn.XiaoOnline.listener.PlayerListener;

public class PluginUtil
{
    public static void LoadPlugin()
    {
        PrintUtil.PrintConsole("&e======== &bXiaoOnline &e> &d开始加载 &e========");
        FileUtil.LoadFile();
        RewardUtil.load();
        Main.getInstance().getServer().getCommandMap().register("XiaoOnline", new OnlineCommand(Main.getInstance()));
        Main.getInstance().getServer().getPluginManager().registerEvents(new PlayerListener(), Main.getInstance());
        TaskUtil.run();
        PrintUtil.PrintConsole("&e======== &bXiaoOnline &e> &a加载成功 &e========");
    }

    public static void DisablePlugin()
    {
        PrintUtil.PrintConsole("&e======== &bXiaoOnline &e> &d开始卸载 &e========");
        PrintUtil.PrintConsole("&e> 感谢您的使用,期待下次运行~");
        PrintUtil.PrintConsole("&e======== &bXiaoOnline &e> &c卸载完成 &e========");
    }

    public static void reloadLoadPlugin(CommandSender sender)
    {
        try
        {
            FileUtil.LoadFile();
            RewardUtil.load();
            PrintUtil.PrintCommandSender(sender, Lang.SuccessReload);
        }
        catch (Exception e)
        {
            PrintUtil.PrintCommandSender(sender, Lang.FailReload);
            e.printStackTrace();
        }
    }
}