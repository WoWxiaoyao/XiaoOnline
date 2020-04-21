package zbv5.cn.XiaoOnline.util;

import cn.nukkit.Player;
import cn.nukkit.utils.ConfigSection;
import zbv5.cn.XiaoOnline.Main;
import zbv5.cn.XiaoOnline.lang.Lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class RewardUtil
{
    public static HashMap<String, ConfigSection> rewards = new HashMap<String, ConfigSection>();
    public static HashMap<String, String> showNames = new HashMap<String, String>();

    public static void send(Player p,String name)
    {
        if((rewards.containsKey(name)) && (p.isOnline()))
        {
            ConfigSection cs = rewards.get(name);
            if(cs != null)
            {
                if(DataUtil.getTodayTime(p.getName()) >= cs.getInt("Second"))
                {
                    List<String> list = cs.getStringList("Reward");
                    if(!list.isEmpty())
                    {
                        List<String> data = DataUtil.getRewards(p.getName());
                        if(!data.contains(name))
                        {
                            List<String> New = new ArrayList<String>(data);
                            New.add(name);
                            DataUtil.setRewards(p.getName(),New);
                            Run(p,list);
                        }
                    } else {
                        PrintUtil.PrintCommandSender(p, Lang.NullReward);
                    }
                } else {
                    PrintUtil.PrintCommandSender(p, Lang.Reward_TimeInsufficient);
                }
            } else {
                PrintUtil.PrintCommandSender(p, Lang.NullReward);
            }
        }
    }

    public static void Run(Player p, List<String> RunList)
    {
        for(String s:RunList)
        {
            s = s.replace("{player}",p.getName());
            if(s.startsWith("[message]"))
            {
                s=s.replace("[message]", "");
                PrintUtil.PrintPlayer(p,s);
            }
            if(s.startsWith("[bc]"))
            {
                s=s.replace("[bc]", "");
                PrintUtil.PrintBroadcast(s);
            }
            if(s.startsWith("[console]"))
            {
                s=s.replace("[console]", "");
                Main.getInstance().getServer().dispatchCommand(Main.getInstance().getServer().getConsoleSender(),s);
            }
            if(s.startsWith("[player]"))
            {
                s=s.replace("[player]", "");
                Main.getInstance().getServer().dispatchCommand(p, s);
            }
            if(s.startsWith("[title]"))
            {
                s=s.replace("[title]", "");
                try
                {
                    String[] ss = s.split(",");
                    if(ss.length == 1)
                    {
                        p.sendTitle(s);
                    }
                    if(ss.length == 2)
                    {
                        p.sendTitle(ss[0],ss[1]);
                    }
                    if(ss.length == 5)
                    {
                        p.sendTitle(ss[0],ss[1],Integer.parseInt(ss[2]),Integer.parseInt(ss[3]),Integer.parseInt(ss[4]));
                    }
                }
                catch (Exception e)
                {
                    PrintUtil.PrintConsole("{prefix}&3Run出现问题 &c执行Title出现问题");
                    e.printStackTrace();
                }
            }
            if(s.startsWith("[ActionBar]"))
            {
                s=s.replace("[ActionBar]", "");
                try
                {
                    String[] ss = s.split(",");
                    if(ss.length == 1)
                    {
                        p.sendActionBar(s);
                    }
                    if(ss.length == 4)
                    {
                        p.sendActionBar(ss[0],Integer.parseInt(ss[1]),Integer.parseInt(ss[2]),Integer.parseInt(ss[3]));
                    }
                }
                catch (Exception e)
                {
                    PrintUtil.PrintConsole("{prefix}&3Run出现问题 &c执行ActionBar出现问题");
                    e.printStackTrace();
                }
            }
            if(s.startsWith("[op]"))
            {
                boolean op = p.isOp();
                p.setOp(true);
                try
                {
                    s=s.replace("[op]", "");
                    Main.getInstance().getServer().dispatchCommand(p, s);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                try
                {
                    p.setOp(op);
                }
                catch (Exception e)
                {
                    PrintUtil.PrintConsole("{prefix}&3Run出现问题 &c执行OP指令操作出现问题");
                    e.printStackTrace();
                    p.setOp(false);
                }
            }
            if(s.startsWith("[chat]"))
            {
                s=s.replace("[chat]", "");
                p.chat(s);
            }
        }
    }

    public static void load()
    {
        try {
            //reload清除缓存
            if(!rewards.isEmpty()) rewards.clear();
            if(!showNames.isEmpty()) showNames.clear();

            Set<String> keys = FileUtil.reward.getKeys(false);
            for (String name:keys)
            {
                ConfigSection c = FileUtil.reward.getSection(name);
                if((c != null) && (c.getBoolean("Enable")))
                {
                    rewards.put(name,c);
                    showNames.put(name,c.getString("Name"));
                    PrintUtil.PrintConsole("&e> &a加载 &6"+name);
                }
            }
            PrintUtil.PrintConsole("&e> &b共加载 &a"+rewards.size() +"种 &6奖励");
        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("&c&l× &4加载奖励配置文件出现问题,请检查服务器.");
            e.printStackTrace();
        }
    }

    public static String getName(String ShowName)
    {
        if(!showNames.isEmpty())
        {
            for(String name:showNames.keySet())
            {
                if(showNames.get(name).equals(ShowName)) return name;
            }
        }
        return null;
    }

    public static String getNameL(String ShowName)
    {
        if(!showNames.isEmpty())
        {
            for(String name:showNames.keySet())
            {
                if(PrintUtil.cc(showNames.get(name)).equals(ShowName)) return name;
            }
        }
        return null;
    }

    public static String getShowName(String name)
    {
        if(showNames.containsKey(name))
        {
            ConfigSection cs = rewards.get(name);
            return cs.getString("Name");
        }
        return null;
    }
}
