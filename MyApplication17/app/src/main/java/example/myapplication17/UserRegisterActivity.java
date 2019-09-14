package example.myapplication17;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class UserRegisterActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
    }
    //
    public void doRegister(View view)
    {
        // 取得用户的输入
        String username = ((EditText)findViewById(R.id.id_yonghumin)).getText().toString();
        String password = ((EditText)findViewById(R.id.id_mima)).getText().toString();
        String verify = ((EditText)findViewById(R.id.id_mima2)).getText().toString();

        //检查两次输入的密码是否一致
        if(!password.equals(verify))
        {
            Toast.makeText(this, "两次输入的密码不同", Toast.LENGTH_SHORT).show();
        }

        // 保存用户信息
        File file = new File(getExternalFilesDir(""), "users.txt");
        UserManager um = new UserManager(file);
        try
        {
            um.load();// 从users.txt中读取数据
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        // 检查该用户名是否已经存在
        if(um.find(username) != null)
        {
            Toast.makeText(this, "该用户名已被注册", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // 添加用户
            um.add(new User(username, password));
            try
            {
                um.save();//保存到文件
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
