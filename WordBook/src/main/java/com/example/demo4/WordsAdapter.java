package com.example.demo4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class WordsAdapter extends ArrayAdapter<Words> {
    private int resourceId;

    public WordsAdapter(@NonNull Context context, int resource, @NonNull List<Words> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }


    public View getView(int position, View convertView, ViewGroup parent)
    {
        Words words=getItem(position);
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }else{
            view=convertView;
        }
        //TextView id=(TextView)view.findViewById(R.id.words_id);
        TextView word =(TextView)view.findViewById(R.id.word);
        TextView words_meaning=(TextView)view.findViewById(R.id.words_meaning);
        TextView words_example=(TextView)view.findViewById(R.id.words_example);
        //id.setText(words.getId());
        word.setText(words.getWord());
        words_meaning.setText(words.getMeaning());
        words_example.setText(words.getExample());
        return view;
    }
}
