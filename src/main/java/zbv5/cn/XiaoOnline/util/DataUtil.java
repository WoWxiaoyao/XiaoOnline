package zbv5.cn.XiaoOnline.util;

import com.google.common.base.Joiner;
import zbv5.cn.XiaoOnline.store.Mysql;
import zbv5.cn.XiaoOnline.store.Yml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataUtil
{

    public static boolean useSql = false;

    public static HashMap<String, String> date = new HashMap<String, String>();
    public static HashMap<String, Integer> data = new HashMap<String, Integer>();
    public static HashMap<String, Long> players = new HashMap<String, Long>();
    public static HashMap<String, List<String>> rewards = new HashMap<String, List<String>>();

    public static void load(String PlayerName)
    {
        if(useSql)
        {
            Mysql.getPlayerData(PlayerName);
        } else {
            Yml.getPlayerData(PlayerName);
        }
    }
    public static String loadOffline(String PlayerName)
    {
        if(useSql)
        {
            return Mysql.getOfflinePlayerData(PlayerName);
        } else {
            return Yml.getOfflinePlayerData(PlayerName);
        }
    }

    public static void write(String PlayerName)
    {
        if(useSql)
        {
            Mysql.writePlayerData(PlayerName,getTodayTime(PlayerName),getRewards(PlayerName));
        } else {
            Yml.writePlayerData(PlayerName,getTodayTime(PlayerName),getRewards(PlayerName));
        }
    }

    public static void close(String PlayerName)
    {
        write(PlayerName);
        remove(PlayerName);
    }

    public static void remove(String PlayerName)
    {
        data.remove(PlayerName);
        date.remove(PlayerName);
        players.remove(PlayerName);
        rewards.remove(PlayerName);
    }

    public static void refresh(String PlayerName)
    {
        close(PlayerName);
        load(PlayerName);
    }

    public static void put(String PlayerName,int Data,List<String> Reward)
    {
        data.put(PlayerName,Data);
        date.put(PlayerName,DateUtil.getDate("yyyy-MM-dd"));
        players.put(PlayerName,System.currentTimeMillis());
        rewards.put(PlayerName,Reward);
    }

    public static int getData(String PlayerName)
    {
        if(data.containsKey(PlayerName)){
            return data.get(PlayerName);
        } else {
            return 0;
        }
    }

    public static String getDate(String PlayerName)
    {
        if(date.containsKey(PlayerName)){
            return date.get(PlayerName);
        } else {
            return null;
        }
    }

    public static int getThisTime(String PlayerName)
    {
        if(players.containsKey(PlayerName))
        {
            return (int)((System.currentTimeMillis() - players.get(PlayerName)) / 1000L);
        } else {
            return 0;
        }
    }

    public static int getTodayTime(String PlayerName)
    {
        int s = 0;
        if(data.containsKey(PlayerName))
        {
            s = getData(PlayerName) + getThisTime(PlayerName);
        } else {
            s = getThisTime(PlayerName);
        }
        return s;
    }

    public static List<String> getRewards(String PlayerName)
    {
        if(rewards.containsKey(PlayerName))
        {
            return rewards.get(PlayerName);
        } else {
            return new ArrayList<String>();
        }
    }

    public static void setRewards(String PlayerName,List<String> NewRewards)
    {
        if((NewRewards == null) || (NewRewards.isEmpty()) || (NewRewards == rewards.get(PlayerName))) return;
        rewards.put(PlayerName,NewRewards);
        write(PlayerName);
    }

    public static String getStatus(String PlayerName,String name)
    {
        if(getRewards(PlayerName).contains(name))
        {
            return "Already";
        } else {
            return "can";
        }
    }
}
