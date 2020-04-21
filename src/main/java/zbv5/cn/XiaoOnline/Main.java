package zbv5.cn.XiaoOnline;

import cn.nukkit.plugin.PluginBase;
import zbv5.cn.XiaoOnline.util.PluginUtil;

public class Main extends PluginBase
{
    private static Main instance;

    public static Main getInstance()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        instance = this;
        PluginUtil.LoadPlugin();
    }

    @Override
    public void onDisable()
    {
        PluginUtil.DisablePlugin();
    }
}