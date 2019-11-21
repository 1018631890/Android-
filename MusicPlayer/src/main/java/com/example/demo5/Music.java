package com.example.demo5;

import java.util.ArrayList;

public class Music {
    private String id;           //id
    private String time;          //时长
    private String artist;        //作者
    private String size;          //大小
    private String name;          //名字
    private String filename;      //文件名

    static private ArrayList<Music> List; //全部歌曲列表

    public Music(){}

    public Music(String id,String time,String artist,String size,String name,String filename)
    {
        this.id=id;
        this.artist=artist;
        this.filename=filename;
        this.size=size;
        this.name=name;
        this.time=time;
    }

    public ArrayList<Music> getList()
    {
        Music m1=new Music("1","02:24","骆益集","5.26MB","离情殇","离情殇.mp3");
        Music m2=new Music("2","01:36","Falcom Sound Team J.D.K.","3.83MB","雨天的真相","雨天的真相.mp3");
        Music m3=new Music("3","05:20","Two Steps From Hell","12.2MB","victory","victory.mp3");
        Music m4=new Music("4","02:35","Marcin Przybyłowicz","2.36MB","Kaer Morhen","Kaer Morhen.mp3");
        List.add(m1);
        List.add(m2);
        List.add(m3);
        List.add(m4);
        return List;
    }

    public String get_id() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public String getFilename() {
        return filename;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getTime() {
        return time;
    }

    public void set_id(String id) {
        this.id = id;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Music findById(String id){
        for(int i=0;i<List.size();i++)
        {
            if(List.get(i).equals(id))
            {
                return List.get(i);
            }
        }
        return null;
    }
}
