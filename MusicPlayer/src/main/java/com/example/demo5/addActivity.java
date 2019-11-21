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

public class addActivity extends AppCompatActivity {

    private Music music=new Music();
    static private ArrayList<Music> allList=new ArrayList<>();                                //全部歌曲列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        if(allList.size()<4)
        {
            init();
        }
        MusicAdapter adapter=new MusicAdapter(addActivity.this,R.layout.list_unit,allList);
        final ListView listView=(ListView)findViewById(R.id.list_add);
        listView.setAdapter(adapter);
        listView.setOnCreateContextMenuListener(this);                //注册contextmenu
    }

    public void init()                               //初始化
    {
        Music m1=new Music("1","02:24","骆益集","5.26MB","离情殇","离情殇.mp3");
        Music m2=new Music("2","01:36","Falcom Sound Team J.D.K.","3.83MB","雨天的真相","雨天的真相.mp3");
        Music m3=new Music("3","05:20","Two Steps From Hell","12.2MB","victory","victory.mp3");
        Music m4=new Music("4","02:35","Marcin Przybyłowicz","2.36MB","Kaer Morhen","Kaer Morhen.mp3");
        allList.add(m1);
        allList.add(m2);
        allList.add(m3);
        allList.add(m4);

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,v,menuInfo);
        getMenuInflater().inflate(R.menu.contentmenu, menu);
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info=null;
        switch (item.getItemId()){
            case R.id.join:
                info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                int i=info.position;
                Music m=allList.get(i);
                join(m);
                break;
        }
        return true;
    }

    public void join(Music music)
    {
        test.get_menuList().add(music);
        Intent intent=new Intent(addActivity.this,test.class);
        startActivity(intent);
    }




    static public ArrayList<Music> getAllList()
    {
        return allList;
    }






}
