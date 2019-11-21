package com.example.demo5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class MusicAdapter extends ArrayAdapter<Music> {

    private int resourceId;

    public MusicAdapter(@NonNull Context context, int resource, @NonNull List<Music> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }


    public View getView(int position, View convertView, ViewGroup parent)
    {
        Music music=getItem(position);
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }else{
            view=convertView;
        }
        TextView name=(TextView)view.findViewById(R.id.name);
        TextView information=(TextView)view.findViewById(R.id.information);
        name.setText(music.getName());
        information.setText("作者："+music.getArtist()+"   时长："+music.getTime()+"   大小："+music.getSize());
        return view;
    }

}
