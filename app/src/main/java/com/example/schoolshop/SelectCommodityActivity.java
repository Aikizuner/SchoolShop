package com.example.schoolshop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.schoolshop.adapter.FindCommodityAdapter;
import com.example.schoolshop.database.CommodityDBHelper;
import com.example.schoolshop.bean.CommodityItems;
import com.example.schoolshop.bean.TemporaryItems;

import java.util.List;

public class SelectCommodityActivity extends AppCompatActivity {
    ListView listView;
    private List<CommodityItems> list;
    CommodityDBHelper cd=new CommodityDBHelper(this,"shopping",null,1);
    TemporaryItems temporaryItems=new TemporaryItems();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_commodity);
        Intent intent=getIntent();  //接受Intent
        ActionBar actionBar=this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(intent.getStringExtra("type"));
        listView=findViewById(R.id.listview);

        list=cd.findLearningCommodity(intent.getStringExtra("type"));   //获取值并作为参数传入方法中
        if (list.size()!=0){
            FindCommodityAdapter adapter=new FindCommodityAdapter(this,list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CommodityItems commodityItems=list.get(position);
                    Intent intent=new Intent(SelectCommodityActivity.this,XiangqingActivity.class);
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
        else{
            Toast.makeText(this, "暂无商品信息", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
