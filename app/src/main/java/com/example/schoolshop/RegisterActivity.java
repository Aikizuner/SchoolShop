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

public class RegisterActivity extends AppCompatActivity {
    Button btn_register;
    EditText et_username,et_pwd,et_ensure;
    UserDBHelper ud = new UserDBHelper(RegisterActivity.this, "shopping", null, 1);
    private String username,userpwd,userensure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar=this.getSupportActionBar(); //显示出左上角的返回按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("注册");

        et_username=findViewById(R.id.et_username);
        et_pwd=findViewById(R.id.et_pwd);
        et_ensure=findViewById(R.id.et_repwd);
        btn_register=findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()) {
                    ud.getWritableDatabase();
                    ud.insertUser(et_username.getText().toString().trim(), et_pwd.getText().toString().trim());

                    SharedPreferences sp=getSharedPreferences("userinfo",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("name",username);
                    editor.commit();
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                    //intent.putExtra("username",et_username.getText().toString().trim());
                    //startActivity(intent);
                    finish();
                }
            }
        });
    }
    //判断三个输入框是否都输入内容且输入的密码和确认密码相同
    public boolean check(){
        username=et_username.getText().toString();
        userpwd=et_pwd.getText().toString();
        userensure=et_ensure.getText().toString();
        if (username.trim().equals("")){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userpwd.trim().equals("")){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userensure.trim().equals("")){
            Toast.makeText(this,"确认密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!userpwd.trim().equals(userensure.trim())){
            Toast.makeText(this,"两次密码输入不一致,请重新输入",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ud.findThisUser(username.trim())){
            Toast.makeText(this,"当前用户名已经被注册了！",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
