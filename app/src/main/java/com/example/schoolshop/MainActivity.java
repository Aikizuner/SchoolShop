package com.example.schoolshop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.schoolshop.adapter.FindCommodityAdapter;
import com.example.schoolshop.database.CommodityDBHelper;
import com.example.schoolshop.bean.CommodityItems;
import com.example.schoolshop.bean.TemporaryItems;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CommodityDBHelper cd=new CommodityDBHelper(this,"shopping",null,1);
    Button btnPersonal,btnGoods;
    ImageButton ibStudy,ibLive,ibSport;
    private ListView listView;
    private List<CommodityItems> list;
    TemporaryItems temporaryItems=new TemporaryItems();
    public static MainActivity instance=null;
    FindCommodityAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);

        ActionBar actionBar=this.getSupportActionBar();
        actionBar.setTitle("校园二手交易市场");
        //actionBar.hide();

        btnPersonal=findViewById(R.id.btn_personal);
        btnGoods=findViewById(R.id.btn_goods);
        listView=findViewById(R.id.lv_goods);
        ibStudy=findViewById(R.id.ib_learning);
        ibLive=findViewById(R.id.ib_living);
        ibSport=findViewById(R.id.ib_sporting);
        btnPersonal.setOnClickListener(this);
        btnGoods.setOnClickListener(this);
        ibStudy.setOnClickListener(this);
        ibLive.setOnClickListener(this);
        ibSport.setOnClickListener(this);
//        if(cd.findCommodity()){
//            Toast.makeText(this, "有内容", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Toast.makeText(this, "无内容", Toast.LENGTH_SHORT).show();
//        }
        //获取数据，并为listview设置适配器
        list=cd.findAllCommodity();
        adapter=new FindCommodityAdapter(this,list);
        listView.setAdapter(adapter);
        //设置listview每一项的点击事件
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommodityItems commodityItems=list.get(position);   //获取当前位置的commodityItems对象
                Intent intent=new Intent(MainActivity.this,XiangqingActivity.class);

                //bundle和intent的方式传递数据大小上限为1MB左右，所以无法直接传过去
//                Bundle bundle=new Bundle();
//                bundle.putByteArray("picture",commodityItems.getPicture());
//                byte[] bytes=commodityItems.getPicture();
//                intent.putExtra("id",commodityItems.getId());
//                intent.putExtra("name",commodityItems.getName());
//                intent.putExtra("price",commodityItems.getPrice());
//                intent.putExtra("type",commodityItems.getType());
//                intent.putExtra("describe",commodityItems.getDescribe());
//                intent.putExtra("phone",commodityItems.getPhone());
//                intent.putExtra("picture",commodityItems.getPicture());
//                intent.putExtra("uid",commodityItems.getUid());
//                intent.putExtras(bundle);

                temporaryItems.setId(commodityItems.getId());
                temporaryItems.setName(commodityItems.getName());
                temporaryItems.setPrice(commodityItems.getPrice());
                temporaryItems.setType(commodityItems.getType());
                temporaryItems.setDescribe(commodityItems.getDescribe());
                temporaryItems.setPhone(commodityItems.getPhone());
                temporaryItems.setPicture(commodityItems.getPicture());
                temporaryItems.setUid(commodityItems.getUid());
                startActivity(intent);
            }
        });


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //点击个人中心按钮时
            case R.id.btn_personal:
                Intent intent=new Intent(MainActivity.this,PersonalActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_goods:
                startActivity(new Intent(MainActivity.this,AddCommodityActivity.class));
                break;
            case R.id.ib_learning:
                Intent intent1=new Intent(MainActivity.this,SelectCommodityActivity.class);
                intent1.putExtra("type","学习用品");    //把学习用品作为参数传递过去
                startActivity(intent1);
                break;
            case R.id.ib_living:
                Intent intent2=new Intent(MainActivity.this,SelectCommodityActivity.class);
                intent2.putExtra("type","生活用品");    //把生活用品作为参数传递过去
                startActivity(intent2);
                break;
            case R.id.ib_sporting:
                Intent intent3=new Intent(MainActivity.this,SelectCommodityActivity.class);
                intent3.putExtra("type","运动用品");    //把运动用品作为参数传递过去
                startActivity(intent3);
                break;
        }
    }

    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            list=cd.findAllCommodity();
            adapter=new FindCommodityAdapter(MainActivity.this,list);
            listView.setAdapter(adapter);
//            finish();
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(); //意图过滤器
        intentFilter.addAction("刷新");     //指定action
        registerReceiver(broadcastReceiver, intentFilter);  //动态注册广播接收者

    }
    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(broadcastReceiver);
    }

}
