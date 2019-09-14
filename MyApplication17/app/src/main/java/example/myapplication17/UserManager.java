package example.myapplication17;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/9/13 0013.
 */

public class UserManager
{
    // 创建一个List来缓存User信息
    List<User> userList = new ArrayList<>();
    // 数据保存到这个文件里
    File file;


    public UserManager(File file)
    {
        this.file = file;
    }
    // 保存到文件
    public void save() throws  Exception
    {
        FileOutputStream fstream = new FileOutputStream(file);

        //遍历数组
        for(User u : userList)
        {
            String line = u.username + "," + u.password + "\n";
            fstream.write(line.getBytes("UTF-8"));
        }
        fstream.close();
    }
    // 从文件加载
    public void load() throws Exception
    {
        InputStreamReader m = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader reader = new BufferedReader(m);   //缓冲流
        userList.clear();   //清空数组
        while(true)
        {
            String line = reader.readLine();
            if(line == null) break;;
            String[] cols = line.split(",");
            if(cols.length<2) continue;

            User u = new User();
            u.username = cols[0].trim();
            u.password = cols[1].trim();
            userList.add(u);
        }
        reader.close();
    }
    // 注册一个用户
    public  void add(User u)
    {
        userList.add(u);

    }
    // 按名称查找
    public User find(String name)
    {
      for(User u :userList)
      {
         if(u.username.equals(name))
         {
             return u;
         }
      }
      return null;

    }

}
