package com.example.schoolshop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnGuanli,btnClose,btnAbout,btnLeave;
    TextView tvName;
    private MyServiceConnection conn;
    private MyService.MusicControl musicControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        ActionBar actionBar=this.getSupportActionBar(); //显示出左上角的返回按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("个人中心");

        btnLeave=findViewById(R.id.btn_leave);
        btnGuanli=findViewById(R.id.btn_guanli);
        btnAbout=findViewById(R.id.btn_about);
        btnClose=findViewById(R.id.btn_close);
        tvName=findViewById(R.id.tv_name);
        btnLeave.setOnClickListener(this);
        btnGuanli.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnClose.setOnClickListener(this);

        Intent intent=new Intent(PersonalActivity.this,MyService.class);
        conn=new MyServiceConnection();
        bindService(intent,conn,BIND_AUTO_CREATE);  //绑定服务

        SharedPreferences sp=getSharedPreferences("userinfo",MODE_PRIVATE);
        tvName.setText(sp.getString("name",""));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.btn_return:
//                finish();
//                break;
            case R.id.btn_leave:
                //调用对话框
                    AlertDialog.Builder builder=new AlertDialog.Builder(PersonalActivity.this)
                        .setMessage("确认注销吗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //删掉userinfo文件中的key为pwd的值
                                SharedPreferences sp=getSharedPreferences("userinfo",MODE_PRIVATE);
                                SharedPreferences.Editor editor=sp.edit();
                                editor.remove("pwd");
                                editor.commit();
                                //sendBroadcast(new Intent("关闭窗口"));
                                //结束当前页和主页
                                Intent intent=new Intent(PersonalActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                                MainActivity.instance.finish(); //结束主页的activity
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();
                break;
            case R.id.btn_guanli:
                startActivity(new Intent(PersonalActivity.this,MyCommodityActivity.class));
                break;
            case R.id.btn_about:
                startActivity(new Intent(PersonalActivity.this,AboutActivity.class));
                break;
                //停止或继续播放
            case R.id.btn_close:
                musicControl.checkPlay();
                break;
        }
    }
    //设置navigation up按钮的监听：复写onSupportNavigateUp()，也就是左上角的返回按键
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    //实现ServiceConnection接口
    class MyServiceConnection implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicControl=(MyService.MusicControl) service;      //获取到这个服务对象
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
