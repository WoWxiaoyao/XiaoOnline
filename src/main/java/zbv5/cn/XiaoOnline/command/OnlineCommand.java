package zbv5.cn.XiaoOnline.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginIdentifiableCommand;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.plugin.Plugin;
import zbv5.cn.XiaoOnline.Main;
import zbv5.cn.XiaoOnline.lang.Lang;
import zbv5.cn.XiaoOnline.util.*;
import zbv5.cn.XiaoOnline.windows.OnlineWindow;

import java.util.ArrayList;
import java.util.List;


public class OnlineCommand  extends Command implements PluginIdentifiableCommand
{
    private final Main plugin;
    public OnlineCommand(Main plugin)
    {
        super("XiaoOnline", "XiaoOnline 插件主指令.", "/XiaoOnline <help>", new String[]{"xoe"});
        this.setPermission("XiaoOnline.command");
        this.getCommandParameters().clear();
        this.addCommandParameters("default", new CommandParameter[]{
                new CommandParameter("命令", false, new String[]{"help","reload"})
        });
        this.plugin = plugin;
    }

    public Plugin getPlugin()
    {
        return this.plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args)
    {
        if (!this.plugin.isEnabled() || !this.testPermission(sender))
        {
            return false;
        }
        if ((args.length == 0) || (args[0].equalsIgnoreCase("help")) || (args[0].equalsIgnoreCase("?")))
        {
            if((sender instanceof Player) && (args.length == 0))
            {
                if(!hasPermission(sender,"XiaoOnline.open"))
                {
                    PrintUtil.PrintCommandSender(sender, Lang.NoPermission);
                    return false;
                }
                Player p = (Player)sender;
                p.showFormWindow(OnlineWindow.OnlineUI(p));
            } else {
                Help(sender,label);
            }
            return false;
        }
        if(args[0].equalsIgnoreCase("info"))
        {
            if(hasPermission(sender,"XiaoOnline.info"))
            {
                if((args.length == 1) || (args.length == 2))
                {
                    if(args.length == 1)
                    {
                        if(sender instanceof Player)
                        {
                            Player p = (Player)sender;
                            Info(sender,p);
                        } else {
                            PrintUtil.PrintCommandSender(sender,Lang.NeedPlayer);
                        }
                    } else {
                        Player p = Main.getInstance().getServer().getPlayer(args[1]);
                        if((p != null) && (p.isOnline()))
                        {
                            Info(sender,p);
                        } else {
                            PrintUtil.PrintCommandSender(sender,Lang.NullPlayer.replace("{player}",args[1]));
                        }
                    }
                } else {
                    Correct(sender,"/"+label+" info <玩家>");
                }
            } else {
                PrintUtil.PrintCommandSender(sender, Lang.NoPermission);
            }
            return true;
        }
        if(args[0].equalsIgnoreCase("reload"))
        {
            if(hasPermission(sender,"XiaoOnline.reload"))
            {
                PluginUtil.reloadLoadPlugin(sender);
            } else {
                PrintUtil.PrintCommandSender(sender, Lang.NoPermission);
            }
            return true;
        }
        PrintUtil.PrintCommandSender(sender,Lang.NullCommand);
        return false;
    }

    private static void Help(CommandSender sender,String label)
    {
        if(sender != null)
        {
            PrintUtil.PrintCommandSender(sender,"&6=== [&bXiaoOnline&6] &6===");
            PrintUtil.PrintCommandSender(sender,"&6/"+label+" help&f[?] &7- &b查看帮助");
            PrintUtil.PrintCommandSender(sender,"&6/"+label+" &7- &b打开页面");
            PrintUtil.PrintCommandSender(sender,"&6/"+label+" info <玩家> &7- &b查询玩家今日信息");
            PrintUtil.PrintCommandSender(sender,"&6/"+label+"&c reload &7- &d重载插件配置文件");
        }
    }

    private static void Correct(CommandSender sender,String s)
    {
        if(sender != null)
        {
            PrintUtil.PrintCommandSender(sender,"{prefix}&c正确用法:&a"+s);
        }
    }

    private static boolean hasPermission(CommandSender sender,String Permission)
    {
        return (sender.isOp()) || (sender.hasPermission("XiaoOnline.admin")) || (sender.hasPermission(Permission));
    }

    private static void Info(CommandSender sender,Player p)
    {
        if(sender != null)
        {
            PrintUtil.PrintCommandSender(sender,"&6=== [&bXiaoOnline&6] &6===");
            PrintUtil.PrintCommandSender(sender,"&e玩家:&a"+p.getName());
            PrintUtil.PrintCommandSender(sender,"&6本次在线:&3"+DateUtil.getFormat(DataUtil.getThisTime(p.getName())));
            PrintUtil.PrintCommandSender(sender,"&6今日总在线:&3"+DateUtil.getFormat(DataUtil.getTodayTime(p.getName())));
            List<String> list = new ArrayList<String>(DataUtil.getRewards(p.getName()));
            if(list.isEmpty())
            {
                PrintUtil.PrintCommandSender(sender,"&c今日未领取任何奖励");
            } else {
                PrintUtil.PrintCommandSender(sender,"&d今日已领取奖励:");
                for(String r:list)
                {
                    PrintUtil.PrintCommandSender(sender,"&e> &b"+r+":"+ RewardUtil.getShowName(r));
                }
            }
        }
    }
}
