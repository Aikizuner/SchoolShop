package com.example.schoolshop.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.schoolshop.bean.CommodityItems;
import com.example.schoolshop.R;

import java.util.List;

public class FindCommodityAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<CommodityItems> list1;

    public FindCommodityAdapter(Context context,List<CommodityItems> list){
         layoutInflater=LayoutInflater.from(context);
         this.list1=list;

    }
    @Override
    public int getCount() { //获取item总数
        if (list1==null){
            return 0;       //如果为空返回0，反之返回list集合的长度
        }
        else{
            return list1.size();
        }

    }
    @Override
    public Object getItem(int i) {
        return list1.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //获得指定位置要显示的View
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewholder viewholder;
        if (view==null){
            view=layoutInflater.inflate(R.layout.commodity_item,null);
            viewholder=new Viewholder(view);    //view进行对应
            view.setTag(viewholder);
        }
        else{
            viewholder=(Viewholder) view.getTag();
        }
        CommodityItems commodityItems= (CommodityItems) getItem(i);
        viewholder.name.setText(commodityItems.getName());  //获取商品名并填充
        viewholder.price.setText(commodityItems.getPrice()+"元");  //获取商品价格并填充
        byte[] picture1 = commodityItems.getPicture();  //获得byte数组
        Bitmap img = BitmapFactory.decodeByteArray(picture1,0,picture1.length);  //转成bitmap格式以进行显示
        viewholder.picture.setImageBitmap(img);
        return view;
    }
    class Viewholder{
        ImageView picture;
        TextView name,price;
        public Viewholder(View view){
            //获取当前view对象下的控件
            picture=view.findViewById(R.id.iv_img);
            name=(TextView)view.findViewById(R.id.tv_name);
            price=(TextView)view.findViewById(R.id.tv_price);
        }
    }
}
