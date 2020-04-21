package zbv5.cn.XiaoOnline.util;

import cn.nukkit.Player;
import zbv5.cn.XiaoOnline.Main;
import zbv5.cn.XiaoOnline.lang.Lang;

public class TaskUtil
{
    //用于每日刷新
    public static void run()
    {
        Main.getInstance().getServer().getScheduler().scheduleRepeatingTask(Main.getInstance(), new Runnable()
        {
            public void run()
            {
                if(DateUtil.getDate("HH:mm:ss").equals("00:00:00"))
                {
                    if(Main.getInstance().getServer().getOnlinePlayers().isEmpty())
                    {
                        //没玩家在线 刷新了个寂寞~
                    }  else {
                        for(Player p:Main.getInstance().getServer().getOnlinePlayers().values())
                        {
                            DataUtil.refresh(p.getName());
                        }
                        PrintUtil.PrintConsole(Lang.NewDay);
                    }
                }
            }
        }, 20, true);
    }

    //用于自动保存
    public static void save(int s)
    {
        Main.getInstance().getServer().getScheduler().scheduleRepeatingTask(Main.getInstance(), new Runnable()
        {
            public void run()
            {
                if(!Main.getInstance().getServer().getOnlinePlayers().isEmpty())
                {
                    for(Player p:Main.getInstance().getServer().getOnlinePlayers().values())
                    {
                        DataUtil.refresh(p.getName());
                    }
                }
            }
        }, s*20, true);
    }
}
