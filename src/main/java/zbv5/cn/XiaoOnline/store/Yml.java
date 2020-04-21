package zbv5.cn.XiaoOnline.store;

import com.google.common.base.Joiner;
import zbv5.cn.XiaoOnline.util.DataUtil;
import zbv5.cn.XiaoOnline.util.DateUtil;
import zbv5.cn.XiaoOnline.util.FileUtil;
import zbv5.cn.XiaoOnline.util.PrintUtil;

import java.util.ArrayList;
import java.util.List;


public class Yml
{
    public static void getPlayerData(String PlayerName)
    {
        if(FileUtil.data.getString(PlayerName+".date") == null)
        {
            build(PlayerName);
        }
        int data = 0;
        if(DateUtil.getDate("yyyy-MM-dd").equals(FileUtil.data.getString(PlayerName+".date")))
        {
            data = FileUtil.data.getInt(PlayerName+".data");
        } else {
            build(PlayerName);
        }
        DataUtil.put(PlayerName,data,FileUtil.data.getStringList(PlayerName+".rewards"));
    }
    public static void writePlayerData(String PlayerName, int data, List<String> rewards)
    {
        try
        {
            FileUtil.data.set(PlayerName+".data", data);
            FileUtil.data.set(PlayerName+".rewards", rewards);
            FileUtil.data.save();
        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("{prefix}&3Yml玩家数据&c写入出现问题!");
            e.printStackTrace();
        }
    }
    public static String getOfflinePlayerData(String PlayerName)
    {
        if(!FileUtil.data.getSections().containsKey(PlayerName))
        {
            return "null";
        } else {
            if(DateUtil.getDate("yyyy-MM-dd").equals(FileUtil.data.getString(PlayerName+".date")))
            {
                if(FileUtil.data.getStringList(PlayerName+".rewards").isEmpty())
                {
                    return String.valueOf(FileUtil.data.getInt(PlayerName+".data"));
                } else {
                    return FileUtil.data.getInt(PlayerName+".data")+"/"+Joiner.on(",").join(FileUtil.data.getStringList(PlayerName+".rewards"));
                }
            } else {
                return "none";
            }
        }
    }

    private static void build(String PlayerName)
    {
        try
        {
            FileUtil.data.set(PlayerName+".date", DateUtil.getDate("yyyy-MM-dd"));
            FileUtil.data.set(PlayerName+".data", 0);
            FileUtil.data.set(PlayerName+".rewards", new ArrayList<String>());
            FileUtil.data.save();
        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("{prefix}&3Yml玩家数据&c写入出现问题!");
            e.printStackTrace();
        }
    }

}
