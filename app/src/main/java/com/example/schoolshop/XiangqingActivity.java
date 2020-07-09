package com.example.schoolshop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.schoolshop.bean.TemporaryItems;

public class XiangqingActivity extends AppCompatActivity {

//    private Integer id;
//    private String name;
//    private String price;
//    private String type;
//    private String describe;
//    private String phone;
//    private byte[] picture;
//    private int uid;
    ImageView iv;
    TextView tvName,tvPrice,tvPhone,tvDescribe;
    Button btnCall;
    TemporaryItems temporaryItems=new TemporaryItems();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiangqing);
        ActionBar actionBar=this.getSupportActionBar(); //设置actionbar的标题并显示返回的按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("商品详情");
//        Intent intent=getIntent();
//        if (intent!=null){
//            id=intent.getIntExtra("id",1);
//            name=intent.getStringExtra("name");
//            price=intent.getStringExtra("price");
//            type=intent.getStringExtra("type");
//            describe=intent.getStringExtra("describe");
//            phone=intent.getStringExtra("phone");
//            picture=intent.getByteArrayExtra("picture");
//            uid=intent.getIntExtra("uid",1);
//
//        }
        iv=findViewById(R.id.iv_img);
        tvName=findViewById(R.id.tv_name);
        tvPrice=findViewById(R.id.tv_price);
        tvPhone=findViewById(R.id.tv_phone);
        tvDescribe=findViewById(R.id.tv_describe);
        btnCall=findViewById(R.id.btn_call);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvPhone.getText().toString()!=""&&tvPhone.getText().toString()!=null){
                    Intent intent=new Intent(Intent.ACTION_DIAL);       //调用电话
                    Uri data=Uri.parse("tel:"+tvPhone.getText().toString());
                    intent.setData(data);
                    startActivity(intent);
                }
            }
        });

        byte[] picture = temporaryItems.getPicture();
        Bitmap img = BitmapFactory.decodeByteArray(picture,0,picture.length); //将byte数组转成bitmap格式
        iv.setImageBitmap(img);
        tvName.setText(temporaryItems.getName());
        tvPrice.setText(temporaryItems.getPrice());
        tvPhone.setText(temporaryItems.getPhone());
        tvDescribe.setText(temporaryItems.getDescribe());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
