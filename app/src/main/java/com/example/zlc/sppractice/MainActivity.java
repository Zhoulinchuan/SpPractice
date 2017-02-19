package com.example.zlc.sppractice;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_password;
    private CheckBox cb_check;
    private SharedPreferences sp;
    private SharedPreferences spcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("config",0);
        spcheck = getSharedPreferences("flag",0);

        et_name = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_userpassword);
        cb_check = (CheckBox) findViewById(R.id.cb_ischeck);
        TextView tv_useable_size = (TextView) findViewById(R.id.tv_useable_size);
        TextView tv_total_size = (TextView) findViewById(R.id.tv_total_size);



        //获取总空间和可用大小
        File file = Environment.getExternalStorageDirectory();
        Long useableSpace = file.getUsableSpace();
        Long totalSpace = file.getTotalSpace();

        String formatUseableSpace = android.text.format.Formatter.formatFileSize(this,useableSpace);
        String formatTotalSpace = android.text.format.Formatter.formatFileSize(this,totalSpace);

        tv_useable_size.setText("可用空间："+formatUseableSpace);
        tv_total_size.setText("总空间："+formatTotalSpace);

        //读取数据
        String name = sp.getString("name","");
        String pwd = sp.getString("pwd","");
        et_name.setText(name);
        et_password.setText(pwd);

        Boolean check = spcheck.getBoolean("check",false);
        cb_check.setChecked(check);

    }


    public void Login(View v){
        String name = et_name.getText().toString().trim();
        String pwd = et_password.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)){

            Toast.makeText(MainActivity.this, "用户名或密码为空", 1).show();
            return;
        }else{

            System.out.println("登录");

            if (cb_check.isChecked()){

                SharedPreferences.Editor edit = sp.edit();
                edit.putString("name",name);
                edit.putString("pwd",pwd);
                edit.commit();

                SharedPreferences.Editor edit1 = spcheck.edit();
                edit1.putBoolean("check",cb_check.isChecked());
                edit1.commit();

                Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_LONG).show();

                }else {

                Toast.makeText(MainActivity.this, "请勾记住用户名和密码", 1).show();
                return;
            }



        }
    }
}
