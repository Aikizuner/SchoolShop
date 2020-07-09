package com.example.schoolshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.schoolshop.database.CommodityDBHelper;
import com.example.schoolshop.database.UserDBHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddCommodityActivity extends AppCompatActivity {
    UserDBHelper ud;
    CommodityDBHelper cd=new CommodityDBHelper(this,"shopping",null,1);
    ImageButton ibUpload;
    EditText etName,etPrice,etPhone,etDescribe;
    Spinner spinner;
    Button btnAdd;
    protected static String cname,cprice,cphone,ctype,cdescribe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_commodity);
        ibUpload=findViewById(R.id.ib_upload);
        etName=findViewById(R.id.et_name);
        etPrice=findViewById(R.id.et_price);
        etPhone=findViewById(R.id.et_phone);
        spinner=findViewById(R.id.spinner);
        etDescribe=findViewById(R.id.et_describe);
        btnAdd=findViewById(R.id.btn_add);
        ActionBar actionBar=this.getSupportActionBar(); //显示出左上角的返回按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("发布商品");
        //为Spinner填充内容并显示
        List<String> list=new ArrayList<String>();
        list.add("运动用品");
        list.add("生活用品");
        list.add("学习用品");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(AddCommodityActivity.this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        ibUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()){
                    try{
                        BitmapDrawable bd=(BitmapDrawable)ibUpload.getDrawable();   //把图片转成bitmap格式
                        Bitmap bitmap=bd.getBitmap();
                        ByteArrayOutputStream os=new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG,100,os);  //图片进行压缩
                        byte[] bytes=os.toByteArray();  //把输出流转成二进制数组
                        cd.insertCommodity(cname,cprice,ctype,cdescribe,cphone,bytes,ud.uid);   //添加商品信息到数据表
                        Toast.makeText(AddCommodityActivity.this, "发布商品成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddCommodityActivity.this,MainActivity.class));
                        finish();
                    }
                    catch (Exception e){
                        Toast.makeText(AddCommodityActivity.this, "你还没有上传图片！！！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    //回调方法中进行判断图片的选择
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1){
            if (data!=null){
                Uri uri=data.getData();
                ibUpload.setImageURI(uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //判断输入是否合规范
    public boolean check(){
        cname=etName.getText().toString().trim();
        cprice=etPrice.getText().toString().trim();
        cphone=etPhone.getText().toString().trim();
        ctype=spinner.getSelectedItem().toString();
        cdescribe=etDescribe.getText().toString().trim();
        if (cname.trim().equals("")){
            Toast.makeText(this,"商品名称不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (cprice.trim().equals("")){
            Toast.makeText(this,"商品价格不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (cphone.trim().equals("")){
            Toast.makeText(this,"联系电话不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (cdescribe.trim().equals("")){
            Toast.makeText(this,"商品描述不能为空",Toast.LENGTH_SHORT).show();
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
