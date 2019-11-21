package com.example.demo5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class test extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    public static ArrayList<Music> menuList=new ArrayList<>();                                  //当前菜单列表
    private ArrayList<Music> allList=new ArrayList<>();                                   //全部歌曲列表


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //Firstmenu();
        init();
        //Play("Kaer Morhen.mp3");
        final ListView listView=(ListView)findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> adapterView, View view, int position,long id) {
                Intent intent=new Intent(test.this,PlayMusic.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

    }

    public void Play(String name)                                        //name为文件名
    {
        try {
            //mediaPlayer.reset();
            AssetManager assetManager = getAssets();
            mediaPlayer=new MediaPlayer();
            String filename="music/"+name;
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(filename);
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            assetFileDescriptor.close();


            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    // 装载完毕回调
                    mediaPlayer.start();
                }
            });
        }catch (IOException ex){
            Log.d("tag", "create failed:", ex);
        }catch (IllegalArgumentException ex) {
            Log.d("tag", "create failed:", ex);
        }catch (SecurityException ex) {
            Log.d("tag", "create failed:", ex);
        }
    }

    public void Firstmenu()                         //初始菜单
    {
        menuList.add(new Music("01","02:24","骆益集","5.26MB","离情殇","离情殇.mp3"));
        menuList.add(new Music("02","01:36","Falcom Sound Team J.D.K.","3.83MB","雨天的真相","雨天的真相.mp3"));
    }

    public void init()                               //初始化
    {

        MusicAdapter adapter=new MusicAdapter(test.this,R.layout.list_unit,menuList);
        final ListView listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnCreateContextMenuListener(this);                //注册contextmenu
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add:
                Intent intent=new Intent(test.this,addActivity.class);                        //跳转到另一个界面添加
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static ArrayList<Music> get_menuList()
    {
        return menuList;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,v,menuInfo);
        getMenuInflater().inflate(R.menu.menu_del, menu);
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info=null;
        switch (item.getItemId()){
            case R.id.del:
                info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                int i=info.position;
                menuList.remove(i);
                init();
                break;
        }
        return true;
    }




















}
