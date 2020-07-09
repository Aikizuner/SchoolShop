package com.example.schoolshop;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class MyService extends Service {
    private MediaPlayer player;
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player=new MediaPlayer();
        player=MediaPlayer.create(getApplicationContext(),R.raw.asphyxia);  //设置音乐源文件
        player.start(); //开始播放
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicControl();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player==null)return;
        if (player.isPlaying()) player.stop();  //停止
        player.release();       //释放内存
        player=null;
    }
    class MusicControl extends Binder{
        public void play(){
            player.reset();
            player=MediaPlayer.create(getApplicationContext(),R.raw.asphyxia);
            player.start();
        }
//        //判断是否在播放
//        public boolean isPlaying(){
//            return player.isPlaying();
//        }
        //播放或暂停歌曲
        public void checkPlay() {
            if (player.isPlaying()) {
                player.pause();
            } else {
                player.start();
            }
        }
    }
}
