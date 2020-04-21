package zbv5.cn.XiaoOnline.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;
import org.json.JSONObject;
import zbv5.cn.XiaoOnline.lang.Lang;
import zbv5.cn.XiaoOnline.util.DataUtil;
import zbv5.cn.XiaoOnline.util.PrintUtil;
import zbv5.cn.XiaoOnline.util.RewardUtil;


public class PlayerListener implements Listener
{

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        DataUtil.load(e.getPlayer().getName());
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        DataUtil.close(e.getPlayer().getName());
    }

    @EventHandler
    public void onClickWindow(PlayerFormRespondedEvent e)
    {
        if (e.getPlayer() == null) return;
        if (e.getResponse() == null) return;

        FormWindow gui = e.getWindow();

        JSONObject json = new JSONObject(e.getWindow().getJSONData());

        Player p = e.getPlayer();
        String title = json.getString("title");
        if (gui instanceof FormWindowSimple)
        {
            String ButtonName = ((FormResponseSimple)e.getResponse()).getClickedButton().getText();
            if(title.equals(PrintUtil.cc("&8&5&7&9&4&l在线奖励")))
            {
                if(ButtonName.endsWith(PrintUtil.cc(Lang.Status_Already))) PrintUtil.PrintCommandSender(p,Lang.Reward_Already);
                if(ButtonName.endsWith(PrintUtil.cc(Lang.Status_TimeInsufficient))) PrintUtil.PrintCommandSender(p,Lang.Reward_TimeInsufficient);

                //执行奖励
                if(ButtonName.endsWith(PrintUtil.cc(Lang.Status_Can)))
                {
                    String rns = ButtonName.replace(PrintUtil.cc(" "+Lang.Status_Can),"");
                    String rn = RewardUtil.getNameL(rns);
                    if(rn != null)
                    {
                        RewardUtil.send(p,rn);
                    } else {
                        PrintUtil.PrintConsole(rns);
                    }
                }
            }
        }
    }
}
