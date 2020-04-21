package zbv5.cn.XiaoOnline.util;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import zbv5.cn.XiaoOnline.Main;
import zbv5.cn.XiaoOnline.lang.Lang;

public class PrintUtil
{
    public static void PrintConsole(String s)
    {
        Main.getInstance().getServer().getConsoleSender().sendMessage(cc(s));
    }

    public static void PrintPlayer(Player p, String s)
    {
        p.sendMessage(cc(s));
    }
    public static void PrintBroadcast(String s)
    {
        Main.getInstance().getServer().broadcastMessage(cc(s));
    }

    public static String cc(String s)
    {
        s = s.replace("{prefix}", Lang.Prefix);
        s = TextFormat.colorize('&', s);
        return s;
    }

    public static void PrintCommandSender(CommandSender sender, String s)
    {
        if(!s.equalsIgnoreCase("none"))
        {
            sender.sendMessage(cc(s));
        }
    }


}
