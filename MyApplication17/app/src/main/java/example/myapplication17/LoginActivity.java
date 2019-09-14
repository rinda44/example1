package example.myapplication17;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class LoginActivity extends AppCompatActivity
{
    Handler msgHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // 从配置里加载
        SharedPreferences sharePref = getPreferences(Context.MODE_PRIVATE);
        String username = sharePref.getString("username", "");
        String password = sharePref.getString("password", "");
        if(username.length()>0 && password.length()>0)
        {
            // 自动填写用户名和密码
            ((EditText)findViewById(R.id.id_loginusername)).setText(username);
            ((EditText)findViewById(R.id.id_loginpassword)).setText(password);
            // 延时1500毫秒后自动登录
            autoLogin();
        }
    }
    public void goLogin(View view)
    {
        // 取得用户界面输入
        String username = ((EditText)findViewById(R.id.id_loginusername)).getText().toString().trim();
        String password = ((EditText)findViewById(R.id.id_loginpassword)).getText().toString().trim();

        File file = new File(getExternalFilesDir(""), "users.txt");
        UserManager um = new UserManager(file);
        try
        {
            um.load();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        User u = um.find(username); //从用户列表里查找用户
        // 比较密码是否区配
        if (!u.password.equals(password))
        {
            Toast.makeText(this, "输入的密码错误", Toast.LENGTH_SHORT).show();

        } else
        {
            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
            // 登录成功, 把用户信息放在全局对象里
            // 以便在各个Activity里都可以访问当前用户信息
            Global.i.username = username;

            // 保存当前用户信息，以便下一次开机启动时加载
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("username", username);
            editor.putString("password", password);
            editor.commit();

            // 进入主界面
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();   // 注意: 关闭本界面
        }


    }
    public void goRegister(View view)
    {
        Intent intent = new Intent(this, UserRegisterActivity.class);
        startActivity(intent);
    }
    private void autoLogin()
    {
        msgHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                goLogin(null);
            }
        },1500);
    }
}
