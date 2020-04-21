package zbv5.cn.XiaoOnline.store;

import com.google.common.base.Joiner;
import zbv5.cn.XiaoOnline.Main;
import zbv5.cn.XiaoOnline.util.DataUtil;
import zbv5.cn.XiaoOnline.util.DateUtil;
import zbv5.cn.XiaoOnline.util.FileUtil;
import zbv5.cn.XiaoOnline.util.PrintUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mysql
{
    static final String Driver = "com.mysql.jdbc.Driver";
    static final String Url = FileUtil.config.getString("Mysql.Url");
    static final String User = FileUtil.config.getString("Mysql.User");
    static final String Pass = FileUtil.config.getString("Mysql.PassWord");

    public static boolean createTable()
    {
        boolean s = true;
        try {
            Class.forName(Driver);
        }catch (ClassNotFoundException e){
            PrintUtil.PrintConsole("{prefix}&3Mysql &c数据库驱动异常!");
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Url,User,Pass);
            Statement st = conn.createStatement();
            String table = FileUtil.config.getString("Mysql.Table");
            st.execute("CREATE TABLE IF NOT EXISTS `"+table+"` (`name` VARCHAR(40) PRIMARY KEY, `data`  VARCHAR(40), `date`  VARCHAR(40), `rewards`  VARCHAR(255) );");
            PrintUtil.PrintConsole("&3Mysql&7（table:"+table+") &a连接成功!");
        } catch (SQLException e)
        {
            PrintUtil.PrintConsole("&3Mysql &c数据库创表出现问题!");
            e.printStackTrace();
            s = false;
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
        }
        return s;
    }

    public static void getPlayerData(String PlayerName)
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Url,User,Pass);
            Statement st = conn.createStatement();
            String table = FileUtil.config.getString("Mysql.Table");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM "+table+" WHERE name=?");
            String sql = "select * from "+table+" where name='" + PlayerName + "' ";
            ResultSet rs = ps.executeQuery(sql);
            while (!rs.next())
            {
                st.execute("INSERT INTO "+table+" VALUES ('" + PlayerName + "', '0', '"+ DateUtil.getDate("yyyy/MM/dd") +"', 'null_none')");
                rs.close();
                st.close();
                conn.close();
                sendInfo(PlayerName,0,"null_none");
                return;
            }
            if(rs.getString("date").equals(DateUtil.getDate("yyyy/MM/dd")))
            {
                sendInfo(PlayerName,rs.getInt("data"),rs.getString("date"));
            } else {
                st.executeUpdate("UPDATE "+table+" set name= '" + PlayerName + "' , data= '0', date='" + DateUtil.getDate("yyyy/MM/dd") + "' , rewards='null_none'  WHERE name='" + PlayerName + "'");
                sendInfo(PlayerName,0,"null_none");
            }
            rs.close();
            st.close();
            conn.close();
        }
        catch (SQLException e)
        {
            PrintUtil.PrintConsole("{prefix}&3玩家数据 &c查询出现问题!");
            e.printStackTrace();
        }
    }

    public static void writePlayerData(String PlayerName, int data, List<String> rewards)
    {
        String reward = getRewardString(rewards);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Url,User,Pass);
            Statement st = conn.createStatement();
            String table = FileUtil.config.getString("Mysql.Table");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM "+table+" WHERE name=?");
            String sql = "select * from "+table+" where name='" +PlayerName + "' ";
            ResultSet rs = ps.executeQuery(sql);
            while (!rs.next())
            {
                st.execute("INSERT INTO "+table+" VALUES ('" + PlayerName + "', '"+data+"', '"+ DateUtil.getDate("yyyy/MM/dd") +"', '"+reward+"')");
                rs.close();
                st.close();
                conn.close();
                return;
            }
            st.executeUpdate("UPDATE "+table+" set name= '" + PlayerName + "' , data= '"+data+"', date='" + DateUtil.getDate("yyyy/MM/dd") + "' , rewards='"+reward+"'  WHERE name='" + PlayerName + "'");
            rs.close();
            st.close();
            conn.close();
        }
        catch (SQLException e)
        {
            PrintUtil.PrintConsole("{prefix}&3玩家数据 &c写入出现问题!");
            e.printStackTrace();
        }
    }

    public static String getOfflinePlayerData(String PlayerName)
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Url,User,Pass);
            Statement st = conn.createStatement();
            String table = FileUtil.config.getString("Mysql.Table");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM "+table+" WHERE name=?");
            String sql = "select * from "+table+" where name='" +PlayerName + "' ";
            ResultSet rs = ps.executeQuery(sql);
            while (!rs.next())
            {
                rs.close();
                st.close();
                conn.close();
                return "null";
            }
            if(rs.getString("date").equals(DateUtil.getDate("yyyy/MM/dd")))
            {
                if(rs.getString("rewards").equalsIgnoreCase("null_none"))
                {
                    String date = String.valueOf(rs.getInt("data"));
                    rs.close();
                    st.close();
                    conn.close();
                    return date;
                } else {
                    String date = String.valueOf(rs.getInt("data"));
                    String rewards = rs.getString("rewards");
                    rs.close();
                    st.close();
                    conn.close();
                    return date +"/"+rewards;
                }
            } else {
                rs.close();
                st.close();
                conn.close();
                return "none";
            }
        }
        catch (SQLException e)
        {
            PrintUtil.PrintConsole("{prefix}&3玩家数据 &c写入出现问题!");
            e.printStackTrace();
        }
        return "null";
    }

    private static void sendInfo(String PlayerName,int data, String rewards)
    {
        DataUtil.put(PlayerName,data,getRewardList(rewards));
    }

    private static List<String> getRewardList(String rewards)
    {
        List<String> list = new ArrayList<String>();
        if(rewards.equalsIgnoreCase("null_none")) return list;
        if(rewards.contains(","))
        {
            list = Arrays.asList(rewards.split(","));
        } else {
            list.add(rewards);
        }
        return list;
    }

    private static String getRewardString(List<String> list)
    {
        if(list.isEmpty())
        {
            return "null_none";
        } else {
            return Joiner.on(",").join(list);
        }
    }
}
