package zbv5.cn.XiaoOnline.windows;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.ConfigSection;
import zbv5.cn.XiaoOnline.lang.Lang;
import zbv5.cn.XiaoOnline.util.*;

import java.util.List;

public class OnlineWindow
{
    public static FormWindowSimple OnlineUI(Player p)
    {
        FormWindowSimple ui;
        if(RewardUtil.rewards.isEmpty())
        {
            ui = new FormWindowSimple(PrintUtil.cc("&8&5&7&4&l在线奖励"),PrintUtil.cc(buildString(Lang.ShowNull,p)));
        } else {
            ui = new FormWindowSimple(PrintUtil.cc("&8&5&7&9&4&l在线奖励"),PrintUtil.cc(buildString(Lang.Show,p)));
            for(String name:RewardUtil.rewards.keySet())
            {
                ConfigSection cs = FileUtil.reward.getSection(name);
                String showName = cs.getString("Name");
                if((showName != null) && (!showName.equals("")))
                {
                    ui.addButton(new ElementButton(PrintUtil.cc(showName+" "+getShowStatus(p.getName(),showName,cs.getInt("Second")))));
                }
            }

        }
        ui.addButton(new ElementButton(PrintUtil.cc("&4&l关闭")));
        return ui;
    }

    public static String buildString(List<String> list,Player p)
    {
        if(list.isEmpty())
        {
            return "null";
        } else {
            StringBuilder sb = new StringBuilder();
            int size = 1;
            for (String s :list)
            {
                if(size == list.size())
                {
                    sb.append(s);
                } else {
                    sb.append(s).append("\n");
                }
                size ++;
            }
            return sb.toString().replace("{player}",p.getName()).replace("{time}", DateUtil.getFormat(DataUtil.getTodayTime(p.getName())));
        }
    }


    public static String getShowStatus(String PlayerName,String name,int need)
    {
        String rn = RewardUtil.getName(name);
        if(rn != null)
        {
            if(DataUtil.getTodayTime(PlayerName) >= need)
            {
                if(DataUtil.getStatus(PlayerName,rn).equalsIgnoreCase("Already"))
                {
                    return Lang.Status_Already;
                } else {
                    return Lang.Status_Can;
                }
            } else {
                return Lang.Status_TimeInsufficient;
            }
        }
        return "null";
    }
}
