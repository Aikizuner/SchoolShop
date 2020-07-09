package com.example.schoolshop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.schoolshop.adapter.FindCommodityAdapter;
import com.example.schoolshop.database.CommodityDBHelper;
import com.example.schoolshop.database.UserDBHelper;
import com.example.schoolshop.bean.CommodityItems;

import java.util.List;

public class MyCommodityActivity extends AppCompatActivity {
    ListView listView;
    private List<CommodityItems> list;
    CommodityDBHelper cd=new CommodityDBHelper(this,"shopping",null,1);
    UserDBHelper ud;
    FindCommodityAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_commodity);
        ActionBar actionBar=this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);  //显示左上角的小箭头
        actionBar.setTitle("我的发布");

        listView=findViewById(R.id.listview);
        int ui=ud.uid;
        String uid=String.valueOf(ui);
        list=cd.findMyCommodity(uid);   //获取值并作为参数传入方法中
        if (list.size()!=0){
            adapter=new FindCommodityAdapter(this,list);
            listView.setAdapter(adapter);
        }
        else{
            Toast.makeText(this, "你还没有发布过商品", Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MyCommodityActivity.this)
                        .setMessage("是否确认删除")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CommodityItems commodityItems=list.get(position);
                                if (cd.deleteCommodity(String.valueOf(commodityItems.getId()))){
                                    list.remove(position);      //移除该位置的list集合
                                    adapter.notifyDataSetChanged();     //刷新数据
                                    Toast.makeText(MyCommodityActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                    sendBroadcast(new Intent("刷新"));
                                }
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
                return true;
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
