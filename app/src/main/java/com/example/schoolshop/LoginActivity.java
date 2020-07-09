package com.example.schoolshop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolshop.database.UserDBHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin,btnRegister;
    EditText et_username,et_pwd;
    UserDBHelper ud=new UserDBHelper(this,"shopping",null,1);
    private String nameinfo,pwdinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar=this.getSupportActionBar();
        actionBar.hide();   //隐藏掉登录页的actionbar

        btnLogin=findViewById(R.id.btn_login);
        btnRegister=findViewById(R.id.btn_register);
        et_username=findViewById(R.id.et_username);
        et_pwd=findViewById(R.id.et_pwd);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        //获取sharedpreferences对象，取出userinfo中的用户名和密码
        SharedPreferences sp=getSharedPreferences("userinfo",MODE_PRIVATE);
        nameinfo=sp.getString("name","");
        pwdinfo=sp.getString("pwd","");
        if (pwdinfo!=""){
            if (ud.findUser(nameinfo,pwdinfo)){     //判断用户信息是否发生了变化
                Toast.makeText(this, "欢迎回来", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }
        }
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_login:
                if (check()){
                    String username=et_username.getText().toString();
                    String userpwd=et_pwd.getText().toString();
                    if (ud.findUser(username,userpwd)){
                        //SharedPreferences存储用户的用户名和密码
                        SharedPreferences sp=getSharedPreferences("userinfo",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putString("name",username);
                        editor.putString("pwd",userpwd);
                        editor.commit();
                        Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }
                    else
                        Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        }

    }
    //判断是否输入了用户名和密码
    public boolean check(){
        String username=et_username.getText().toString();
        String userpwd=et_pwd.getText().toString();
        if (username.trim().equals("")){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userpwd.trim().equals("")){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp=getSharedPreferences("userinfo",MODE_PRIVATE);
        nameinfo=sp.getString("name","");
        et_username.setText(nameinfo);
    }
}
