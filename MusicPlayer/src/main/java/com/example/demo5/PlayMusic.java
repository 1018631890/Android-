package com.example.demo5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class PlayMusic extends AppCompatActivity{

    private MediaPlayer mediaPlayer=null;
    private SeekBar seekBar;
    private boolean flag=true;
    private boolean isChange;
    private Thread thread;
    private ImageButton imageButton;
    private TextView begin;
    private TextView end;
    private TextView View1;
    private TextView View2;
    private int position;
    private int time=0;
    private ImageButton front;
    private ImageButton behind;
    private ImageButton sw;
    int type=1;                 //type==1为顺序播放,type==2为随机播放
    String[] p=new String[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        seekBar=(SeekBar)findViewById(R.id.seekbar);
        imageButton=(ImageButton)findViewById(R.id.play);
        begin=(TextView)findViewById(R.id.start);
        end=(TextView)findViewById(R.id.end);
        View1=(TextView)findViewById(R.id.View1);
        View2=(TextView)findViewById(R.id.View2);
        seekBar.setOnSeekBarChangeListener(new MySeekBar());
        front=(ImageButton)findViewById(R.id.front);
        behind=(ImageButton)findViewById(R.id.behind);
        sw=(ImageButton)findViewById(R.id.sw);

        final Intent intent=getIntent();
        if(intent.getIntExtra("position",-1)!=-1)
        {
            position=intent.getIntExtra("position",0);
            init(test.get_menuList().get(position));
        }

        imageButton.setOnClickListener(new View.OnClickListener()                        //开始暂停按钮点击事件
        {
                public void onClick(View v) {
                if(flag)
                {
                    imageButton.setImageDrawable(getResources().getDrawable(R.drawable.pause));
                    flag=false;
                    ImageView imageView=(ImageView)findViewById(R.id.round);
                    Animation operatingAnim = AnimationUtils.loadAnimation(PlayMusic.this, R.anim.roate_anim);
                    LinearInterpolator lin = new LinearInterpolator();
                    operatingAnim.setInterpolator(lin);
                    if(operatingAnim!=null)
                    {
                        imageView.startAnimation(operatingAnim);
                    }else{
                        imageView.setAnimation(operatingAnim);
                        imageView.startAnimation(operatingAnim);
                    }
                    Play(test.get_menuList().get(position).getFilename());
                    mediaPlayer.seekTo(time);
                    //thread.stop();
                    thread=new Thread(new SeekBarThread());
                    thread.start();
                }
                else if(!flag)
                {
                    if(mediaPlayer.isPlaying())
                    {
                        time=mediaPlayer.getCurrentPosition();
                        mediaPlayer.pause();
                    }
                    imageButton.setImageDrawable(getResources().getDrawable(R.drawable.start));
                    flag=true;
                    ImageView imageView=(ImageView)findViewById(R.id.round);
                    imageView.clearAnimation();
                }
            }
        });

        behind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag)                                      //切换为暂停
                {
                    imageButton.setImageDrawable(getResources().getDrawable(R.drawable.start));
                    mediaPlayer.pause();
                    //flag=true;
                    ImageView imageView=(ImageView)findViewById(R.id.round);
                    imageView.clearAnimation();
                }
                position = position + type;
                if(position>=test.get_menuList().size())                     //若播放到最后一个，返回第一个
                {
                    position=0;
                }
                time=0;
                init(test.get_menuList().get(position));
            }
        });

        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag)                                      //切换为暂停
                {
                    imageButton.setImageDrawable(getResources().getDrawable(R.drawable.start));
                    mediaPlayer.pause();
                    flag = true;
                    ImageView imageView = (ImageView) findViewById(R.id.round);
                    imageView.clearAnimation();
                }
                position=position-type;
                if(position<0)                                               //若列表前置无歌，播放最后一首
                {
                    position=test.get_menuList().size()-1;
                }
                time=0;
                init(test.get_menuList().get(position));
            }
        });

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==1)
                {
                    sw.setImageDrawable(getResources().getDrawable(R.drawable.radom));
                    int[] p1=new int[10];
                    for(int i=0;i<test.get_menuList().size();i++)
                    {   p1[i]=i;
                        p[i]=test.get_menuList().get(i).get_id();
                    }
                    for (int i =test.get_menuList().size()-1; i >= 0; i--)                         //费雪耶兹乱置算法
                    {
                        int rand = (new Random()).nextInt(i+1);
                        int temp=p1[i];
                        p1[i]=p1[rand];
                        p1[rand]=temp;
                    }
                    /*test.get_menuList().clear();
                    for(int i=0;i<test.get_menuList().size();i++)
                    {
                        Music music;
                        if(findById(addActivity.getAllList(),String.valueOf(p1[i]))!=null)
                        {
                            music=findById(addActivity.getAllList(),String.valueOf(p1[i]));
                            test.get_menuList().add(music);
                        }
                    }*/
                    type=2;
                }
                else if(type==2)
                {
                    sw.setImageDrawable(getResources().getDrawable(R.drawable.line));
                    /*test.get_menuList().clear();
                    for(int i=0;i<test.get_menuList().size();i++)
                    {
                        Music music;
                        if(findById(addActivity.getAllList(),p[i])!=null)
                        {
                            music=findById(addActivity.getAllList(),p[i]);
                            test.get_menuList().add(music);
                        }

                    }*/
                    type=1;
                }
            }
        });
}

    public static String timeParse(long duration)                            //ms转换为00:00格式的函数
    {
        String time = "" ;
        long minute = duration / 60000 ;
        long seconds = duration % 60000 ;
        long second = Math.round((float)seconds/1000) ;
        if( minute < 10 ){
            time += "0" ;
        }
        time += minute+":" ;
        if( second < 10 ){
            time += "0" ;
        }
        time += second ;
        return time ;
    }

    class SeekBarThread implements Runnable {

        @Override
        public void run() {
            //int i=0;
            while (!flag&&!isChange) {
                // 将SeekBar位置设置到当前播放位置
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                //seekBar.setProgress(i);
                try {
                    // 每100毫秒更新一次位置
                    Thread.sleep(100);
                    //播放进度

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //i+=100;
                String str=timeParse(mediaPlayer.getCurrentPosition());
                begin.setText(str);
            }
        }
    }

    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {

            }
        }

    }

    public void init(Music music)
    {
        end.setText(music.getTime());
        View1.setText(music.getName());
        View2.setText(music.getArtist());
    }

    public void Play(String name)                                        //name为文件名
    {
        try {
            if(mediaPlayer!=null)
            { mediaPlayer.reset();}
            AssetManager assetManager = getAssets();
            mediaPlayer=new MediaPlayer();
            String filename="music/"+name;
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(filename);
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            assetFileDescriptor.close();

            mediaPlayer.prepare();
            //mediaPlayer.prepareAsync();
            seekBar.setMax(mediaPlayer.getDuration());
            seekBar.setProgress(0);
            mediaPlayer.start();
            /*mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    // 装载完毕回调
                    mediaPlayer.start();
                }
            });*/
        }catch (IOException ex){
            Log.d("tag", "create failed:", ex);
        }catch (IllegalArgumentException ex) {
            Log.d("tag", "create failed:", ex);
        }catch (SecurityException ex) {
            Log.d("tag", "create failed:", ex);
        }
    }

    protected void onDestroy() {
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        super.onDestroy();
    }

    /*protected void onResume() {
        super.onResume();
        if (mediaPlayer!=null){
            if(!mediaPlayer.isPlaying())
                mediaPlayer.start();
        }
    }

    protected void onPause() {
        super.onPause();
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }*/

    public class MySeekBar implements SeekBar.OnSeekBarChangeListener {
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            isChange = true;
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            isChange = false;
            mediaPlayer.seekTo(seekBar.getProgress());
            //thread.stop();
            thread=new Thread(new SeekBarThread());
            thread.start();
        }
    }

    public Music findById(ArrayList<Music> list,String id){
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).equals(id))
            {
                return list.get(i);
            }
        }
        return null;
    }
}
