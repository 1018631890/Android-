package com.example.demo4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    WordsDBHelper mDbHelper;
    private List<Words> wordsList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建SQLiteOpenHelper对象，注意第一次运行时，此时数据库并没有被创建
        mDbHelper = new WordsDBHelper(this);
        EditText editText=(EditText)findViewById(R.id.find);
        initWords();
        //edittext查找（未完成）
        /*editText.addTextChangedListener(
                new TextWatcher() {
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    public void afterTextChanged(Editable s) {

                    }
                });*/
        //横屏点击在右边显示详细信息(只能显示第一个单词信息）
        //赋值地方有错误
        if(this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
        {
            Log.i("info", "landscape"); // 横屏
            final ListView listView=(ListView)findViewById(R.id.list_view);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> adapterView, View view, int position,long id) {
                    Words words=wordsList.get(position);
                    TextView w=(TextView)findViewById(R.id.value_word);
                    TextView m=(TextView)findViewById(R.id.value_meaning);
                    TextView e=(TextView)findViewById(R.id.value_example);
                    /*String value_word=((TextView)listView.findViewById(R.id.word)).getText().toString();
                    String value_meaning=((TextView)listView.findViewById(R.id.word)).getText().toString();
                    String value_example=((TextView)listView.findViewById(R.id.word)).getText().toString();*/
                    w.setText(words.getWord());
                    m.setText(words.getMeaning());
                    e.setText(words.getExample());

                }
            });
        }
       /* listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent,View view,int position,long id)
            {
                Words words=wordsList.get(position);
                Toast.makeText(MainActivity.this,words.getWord(),Toast.LENGTH_SHORT).show();
                view.showContextMenu();
                return true;
            }
        });*/
    }



    protected void onDestroy()                 //关闭数据库
    {
        super.onDestroy();
        mDbHelper.close();   }


    private void initWords()                       //初始构造words
    {
        wordsList.clear();                       //清空list中所有元素
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.query("words", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            //int id=cursor.getInt(cursor.getColumnIndex("_id"));
            String word=cursor.getString(cursor.getColumnIndex("word"));
            String meaning=cursor.getString(cursor.getColumnIndex("meaning"));
            String example=cursor.getString(cursor.getColumnIndex("example"));
            Words temp=new Words(word,meaning,example);
            //temp.setId(id);
            wordsList.add(temp);
        }
        WordsAdapter adapter=new WordsAdapter(MainActivity.this,R.layout.word_unit,wordsList);
        final ListView listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnCreateContextMenuListener(this);                //注册contextmenu
            /*Words apple=new Words("Apple","苹果","This is a apple");
            Words what=new Words("What","什么","What's up");
            wordsList.add(apple);
            wordsList.add(what);*/

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_insert:
                //新增单词
                InsertDialog();
                return true;
            case R.id.action_search:
                //查找单词
                SearchDialog();
                return true;
            case R.id.action_help:
                //帮助
                help();
                return true;
        }
                return super.onOptionsItemSelected(item);
    }

    private void help() {
        new AlertDialog.Builder(this).setTitle("帮助").setMessage("有问题建议询问制作者")
                .setNegativeButton("确定",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialogInterface, int i) {}
        }).create().show();
    }


    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,v,menuInfo);
        getMenuInflater().inflate(R.menu.contentmenu, menu);
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        TextView textId=null;
        TextView textWord=null;
        TextView textMeaning=null;
        TextView textSample=null;
        AdapterView.AdapterContextMenuInfo info=null;
        View itemView=null;
        switch (item.getItemId()){
            case R.id.action_delete:
                //删除单词
                info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                itemView=info.targetView;
                textWord =(TextView)itemView.findViewById(R.id.word);
                if(textWord!=null){
                    String word=textWord.getText().toString();
                    DeleteDialog(word);
                }
                break;
            case R.id.action_edit:
                //修改单词
                info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                itemView=info.targetView;
                //textId =(TextView)itemView.findViewById(R.id.words_id);
                textWord =(TextView)itemView.findViewById(R.id.word);
                textMeaning =(TextView)itemView.findViewById(R.id.words_meaning);
                textSample =(TextView)itemView.findViewById(R.id.words_example);
                if( textWord!=null && textMeaning!=null && textSample!=null){
                    //String strId=textId.getText().toString();
                    String strWord=textWord.getText().toString();
                    String strMeaning=textMeaning.getText().toString();
                    String strSample=textSample.getText().toString();
                    EditDialog(strWord, strMeaning, strSample);
                }
                break;

        }
        return true;
    }

    private void SearchDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.search, null);
        new AlertDialog.Builder(this)
                .setTitle("新增单词")//标题
                .setView(tableLayout)//设置视图
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String txtSearchWord = ((EditText) tableLayout.findViewById(R.id.key)).getText().toString();
                        Search(txtSearchWord);
                    }}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) { }
        }).create().show();
    }

    private void EditDialog(final String word, String meaning, String example) //编辑操作
    {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.insert, null);
        ((EditText)tableLayout.findViewById(R.id.key)).setText(word);
        ((EditText)tableLayout.findViewById(R.id.txtMeaning)).setText(meaning);
        ((EditText)tableLayout.findViewById(R.id.txtExample)).setText(example);
        new AlertDialog.Builder(this)
                .setTitle("修改单词")//标题
                .setView(tableLayout)//设置视图
        //确定按钮及其动作
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strNewWord = ((EditText) tableLayout.findViewById(R.id.key)).getText().toString();
                        String strNewMeaning = ((EditText) tableLayout.findViewById(R.id.txtMeaning)).getText().toString();
                        String strNewExample = ((EditText) tableLayout.findViewById(R.id.txtExample)).getText().toString();
                        Edit(strNewWord,strNewMeaning,strNewExample,word);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {}

        }).create().show();
    }



    private void DeleteDialog(final String word)  //删除操作
    {
        new AlertDialog.Builder(this).setTitle("删除单词").setMessage("是否真的删除单词?")
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Delete(word);
                    }
                }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {}
        }).create().show();
    }

    private void InsertDialog()  //增加操作
    {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.insert, null);
        new AlertDialog.Builder(this)
                .setTitle("新增单词")             //标题
                .setView(tableLayout)           //设置视图
                //确定按钮及其动作
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strWord=((EditText)tableLayout.findViewById(R.id.key)).getText().toString();
                        String strMeaning=((EditText)tableLayout.findViewById(R.id.txtMeaning)).getText().toString();
                        String strSample=((EditText)tableLayout.findViewById(R.id.txtExample)).getText().toString();
                        Insert(strWord, strMeaning, strSample);
                    }})
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {}
                })
                .create()
                .show();
    }

    public void Insert(String word,String meaning,String example)    //数据库添加函数
    {
        String sql="insert into  words(word,meaning,example) values(?,?,?)";
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(sql,new String[]{word,meaning,example});
        wordsList.add(new Words(word,meaning,example));
    }

    public void Delete(String word)                  //数据库删除操作
    {
        String sql="delete from words where word='"+word+"'";//数据库删除语句
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.execSQL(sql);
        initWords();
    }

    public void Edit(String word,String meaning,String example,String word1)         //数据库修改操作
    {
        String sql="update words set word=?,meaning=?,example=? where word=?";//数据库修改语句
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(sql,new String[]{word,meaning,example,word1});
        initWords();
    }

    public void Search(String key)
    {
        if(key.equals("")==true)
        {
            initWords();
        }
        else if(key.equals("")==false)
        {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            String sql="select * from words where word like ? order by word desc";
            Cursor c=db.rawQuery(sql,new String[]{"%"+key+"%"});
            wordsList.clear();
            while (c.moveToNext())
            {
                String word=c.getString(c.getColumnIndex("word"));
                String meaning=c.getString(c.getColumnIndex("meaning"));
                String example=c.getString(c.getColumnIndex("example"));
                Words temp=new Words(word,meaning,example);
                wordsList.add(temp);
            }
            WordsAdapter adapter=new WordsAdapter(MainActivity.this,R.layout.word_unit,wordsList);
            ListView listView=(ListView)findViewById(R.id.list_view);
            listView.setAdapter(adapter);
            listView.setOnCreateContextMenuListener(this);                //注册contextmenu
        }
    }

    public void refresh() {

        onCreate(null);

    }

    /*class GameThread implements Runnable {
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                // 使用postInvalidate可以直接在线程中更新界面
                mGameView.postInvalidate();
            }
        }
    }*/



}

