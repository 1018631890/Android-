package com.example.demo4;

import android.provider.BaseColumns;

public class Words {
    private int id;
    private String word;
    private String meaning;
    private String example;
    public Words(String word,String meaning,String example)
    {
        this.word=word;
        this.meaning=meaning;
        this.example=example;
    }

    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getExample() {
        return example;
    }

    public int getId() {
        return id;
    }

   public void setId(int id)
   {
       this.id=id;
   }

    public static abstract class Word implements BaseColumns {
        public static final String TABLE_NAME="words";
        public static final String COLUMN_NAME_WORD="word";//列：单词
        public static final String COLUMN_NAME_MEANING="meaning";//列：单词含义
        public static final String COLUMN_NAME_EXAMPLE="example";//单词示例
    }

}
