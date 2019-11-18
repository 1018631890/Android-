package com.example.text1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Calculator extends AppCompatActivity implements View.OnClickListener {

    private Button[] buttons = new Button[31];
    private int[] ids = new int[]{R.id.bte1,R.id.bte2,R.id.bte3,R.id.bte4,R.id.bte5,R.id.bte6,R.id.bte7,
            R.id.bte8,R.id.bte9,R.id.bte10,R.id.bte11,R.id.bte12,R.id.bte13,R.id.bte14,R.id.bte15,R.id.bte16,
            R.id.bte17,R.id.bte18,R.id.bte19,R.id.bte20,R.id.bte21,R.id.bte22,R.id.bte23,R.id.bte24,
            R.id.bte25,R.id.bte26,R.id.bte27,R.id.bte29,R.id.bte28,R.id.bte30,R.id.bte31};

    private int[] ids1 = new int[]{R.id.bte1,R.id.bte2,R.id.bte3,R.id.bte4,R.id.bte5,R.id.bte6,R.id.bte7,
            R.id.bte8,R.id.bte9,R.id.bte10,R.id.bte11,R.id.bte12,R.id.bte13,R.id.bte14,R.id.bte15,R.id.bte16,
            R.id.bte17,R.id.bte18,R.id.bte19,R.id.bte20,R.id.bte21,R.id.bte22,R.id.bte23,R.id.bte24,
            R.id.bte25,R.id.bte26,R.id.bte27,R.id.bte29,R.id.bte28,R.id.bte30,R.id.bte31
    };

    private TextView textView;
    private String expression = "0";
    private boolean end = false;
    private int countOperate=2;
    protected static final int Menu_Help = Menu.FIRST;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE)

        {
            Log.i("info", "landscape"); // 横屏
            setContentView(R.layout.calculator_land);

        }
        else if(this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
        {

            Log.i("info", "portrait"); // 竖屏
            setContentView(R.layout.calculator_port);

        }
        for(int i=0;i<ids.length;i++){
            buttons[i] = (Button)findViewById(ids[i]);
            buttons[i].setOnClickListener(this);

        }
        textView = (TextView)findViewById(R.id.View);
        }
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.add(0,Menu_Help,0,"help");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        //return super.onOptionsItemSelected(item);
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case Menu_Help:
                Toast.makeText(Calculator.this,"连计算器都不会用，你脑子瓦特了",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
    //点击事件，强制排错
    public void onClick(View view) {
        int id = view.getId();
        Button button = (Button)view.findViewById(id);
        String current = button.getText().toString();
        if(end){ //如果上一次算式已经结束，则先清零
            expression = "0";
            end = false;
        }
        if(current.equals("AC"))
        { //如果为CE则清零
            expression = "0";
            countOperate=0;
        }
       else if(current.equals("+/-"))
        {
            int i=0;                                                      //判断数字后是否有符号
            for(;i<expression.length()-1;i++)
            {
                char temp=expression.charAt(i);
                if(temp=='0'||temp=='1'||temp=='2'||temp=='3'||temp=='4'||temp=='5'||temp=='6'||temp=='7'||temp=='8'||temp=='9')
                {
                    temp=expression.charAt(i+1);
                    if(temp=='+'||temp=='-'||temp=='*'||temp=='/')
                        return;                                           //若有则不执行
                    //temp=expression.charAt(i-1);
                }
            }
            double val= Double.parseDouble(expression);
            val=-val;
            expression= String.valueOf(val);
        }
       else if(current.equals("L→"))
        {
            int i=0;                                                      //判断数字后是否有符号
            for(;i<expression.length()-1;i++)
            {
                char temp=expression.charAt(i);
                if(temp=='0'||temp=='1'||temp=='2'||temp=='3'||temp=='4'||temp=='5'||temp=='6'||temp=='7'||temp=='8'||temp=='9')
                {
                    temp=expression.charAt(i+1);
                    if(temp=='+'||temp=='-'||temp=='*'||temp=='/')
                        return;                                           //若有则不执行
                    //temp=expression.charAt(i-1);
                }
            }
            double val= Double.parseDouble(expression);
            val=val/10;
            expression= String.valueOf(val);
        }
       else if(current.equals("L←"))
        {
            int i=0;                                                      //判断数字后是否有符号
            for(;i<expression.length()-1;i++)
            {
                char temp=expression.charAt(i);
                if(temp=='0'||temp=='1'||temp=='2'||temp=='3'||temp=='4'||temp=='5'||temp=='6'||temp=='7'||temp=='8'||temp=='9')
                {
                    temp=expression.charAt(i+1);
                    if(temp=='+'||temp=='-'||temp=='*'||temp=='/')
                        return;                                           //若有则不执行
                    //temp=expression.charAt(i-1);
                }
            }
            double val= Double.parseDouble(expression);
            val=val*10;
            expression= String.valueOf(val);
        }
        else if(current.equals("V→"))
        {
            int i=0;                                                      //判断数字后是否有符号
            for(;i<expression.length()-1;i++)
            {
                char temp=expression.charAt(i);
                if(temp=='0'||temp=='1'||temp=='2'||temp=='3'||temp=='4'||temp=='5'||temp=='6'||temp=='7'||temp=='8'||temp=='9')
                {
                    temp=expression.charAt(i+1);
                    if(temp=='+'||temp=='-'||temp=='*'||temp=='/')
                        return;                                           //若有则不执行
                    //temp=expression.charAt(i-1);
                }
            }
            double val= Double.parseDouble(expression);
            val=val/1000;
            expression= String.valueOf(val);
        }
        else if(current.equals("V←"))
        {
            int i=0;                                                      //判断数字后是否有符号
            for(;i<expression.length()-1;i++)
            {
                char temp=expression.charAt(i);
                if(temp=='0'||temp=='1'||temp=='2'||temp=='3'||temp=='4'||temp=='5'||temp=='6'||temp=='7'||temp=='8'||temp=='9')
                {
                    temp=expression.charAt(i+1);
                    if(temp=='+'||temp=='-'||temp=='*'||temp=='/')
                        return;                                           //若有则不执行
                    //temp=expression.charAt(i-1);
                }
            }
            double val= Double.parseDouble(expression);
            val=val*1000;
            expression= String.valueOf(val);
        }
       else if(current.equals("#2"))
        {
            /*for(int i=0;i<expression.length();i++)                        //若数字非01不可为二进制
            {
                char tmp=expression.charAt(i);
                if(tmp!='0'||tmp!='1')
                    return;
            }*/

            Integer it= Integer.valueOf(expression);
            expression=Integer.toBinaryString(it);
        }
        else if(current.equals("#8"))
        {
            Integer it= Integer.valueOf(expression);
            expression=Integer.toOctalString(it);
        }
        else if(current.equals("#10"))
        {

        }
        else if(current.equals("#16"))
        {
            Integer it= Integer.valueOf(expression);
            expression=Integer.toHexString(it);
        }
       else if(current.equals("sin"))
        {
            int i=0;                                                      //判断数字后是否有符号
            for(;i<expression.length()-1;i++)
            {
                char temp=expression.charAt(i);
                if(temp=='0'||temp=='1'||temp=='2'||temp=='3'||temp=='4'||temp=='5'||temp=='6'||temp=='7'||temp=='8'||temp=='9')
                {
                    temp=expression.charAt(i+1);
                    if(temp=='+'||temp=='-'||temp=='*'||temp=='/')
                        return;
                    //temp=expression.charAt(i-1);
                }
            }
            double val= Double.parseDouble(expression);
            val=Math.sin(val);
            expression= String.valueOf(val);
        }
        else if(current.equals("cos"))
        {
            int i=0;
            for(;i<expression.length()-1;i++)
            {
                char temp=expression.charAt(i);
                if(temp=='0'||temp=='1'||temp=='2'||temp=='3'||temp=='4'||temp=='5'||temp=='6'||temp=='7'||temp=='8'||temp=='9')
                {
                    temp=expression.charAt(i+1);
                    if(temp=='+'||temp=='-'||temp=='*'||temp=='/')
                        return;
                    //temp=expression.charAt(i-1);
                }
            }
            double val= Double.parseDouble(expression);
            val=Math.cos(val);
            expression= String.valueOf(val);
        }
        else if(current.equals("Back"))
        { //如果点击退格
            if(expression.length()>1){ //算式长度大于1
                expression = expression.substring(0,expression.length()-1);//退一格
                int i = expression.length()-1;
                char tmp = expression.charAt(i); //获得最后一个字符
                char tmpFront = tmp;
                for(;i>=0;i--){ //向前搜索最近的 +-*/和.，并退出
                    tmpFront = expression.charAt(i);
                    if(tmpFront=='.'||tmpFront=='+'||tmpFront=='-'||tmpFront=='*'||tmpFront=='/'){
                        break;
                    }
                }
                //    Toast.makeText(this, "tmp = "+tmp, Toast.LENGTH_SHORT).show();
                if(tmp>='0'&&tmp<='9'){ //最后一个字符为数字，则识别数赋值为0
                    countOperate=0;
                }else if(tmp==tmpFront&&tmpFront!='.') countOperate=2; //如果为+-*/，赋值为2
                else if(tmpFront=='.') countOperate=1; //如果前面有小数点赋值为1
            }else if(expression.length()==1){
                expression = "";
            }
        }
        else if(current.equals(".")){
            if(expression.equals("")||countOperate==2){
                expression+="0"+current;
                countOperate = 1;  //小数点按过之后赋值为1
            }
            if(countOperate==0){
                expression+=".";
                countOperate = 1;
            }
        }
        else if(current.equals("+")||current.equals("-")||current.equals("*")||current.equals("/")){
            if(countOperate==0){
                expression+=current;
                countOperate = 2;  //  +-*/按过之后赋值为2
            }
        }
        else if(current.equals("=")){ //按下=时，计算结果并显示
            double result = (double) Math.round(count(expression)*1000000000)/1000000000;
            expression = "";
            expression+=result;
            end = true; //此次计算结束
        }
        else{//此处是当退格出现2+0时，用current的值替代0
            if(expression.length()>=1){
                char tmp1 = expression.charAt(expression.length()-1);
                if(tmp1=='0'&&expression.length()==1){
                    expression = expression.substring(0,expression.length()-1);
                }
                else if(tmp1=='0'&&expression.length()>1){
                    char tmp2 = expression.charAt(expression.length()-2);
                    if(tmp2=='+'||tmp2=='-'||tmp2=='*'||tmp2=='/'){
                        expression = expression.substring(0,expression.length()-1);
                    }
                }
            }
            expression+=current;
            if(countOperate==2||countOperate==1) countOperate=0;
        }
        //    Toast.makeText(this, "countOperate:"+countOperate, Toast.LENGTH_SHORT).show();
        textView.setText(expression); //显示出来
    }
    //计算逻辑，求expression表达式的值
    private double count( String str){                                      //括号部分只能实现单括号
        double result=0;
        double result1=0;
        double tNum=1,lowNum=0.1,num=0;
        char tmp=0;
        int operate = 1; //识别+-*/，为+时为正数，为-时为负数，为×时为-2/2,为/时为3/-3;
        boolean point = false;
        boolean com = false;
        for(int i=0;i<str.length();i++)
        {
            tmp = str.charAt(i);
            if(tmp=='(') {
                char tmp1=0;
                for(int j=str.length()-1;j>i;j--)
                {
                    tmp1=str.charAt(j);
                    if(tmp1==')')
                    {
                        String tmp3="",tmp2="";
                        for(int p=i+1;p<j;p++)
                        {
                            tmp3 += String.valueOf(str.charAt(p));
                        }
                        for(int k=j+1;k<str.length();k++)
                            tmp2 += String.valueOf(str.charAt(k));
                        tmp2 = count(tmp3)+tmp2;
                        result1=count(tmp2);
                        com = true;
                    }
                }
                com=true;
            }
        }
        if(com==true)
        {
            return result1;
        }
        for(int i=0;i<str.length();i++){ //遍历表达式
            tmp = str.charAt(i);
            if(tmp=='.'){ //因为可能出现小数，此处进行判断是否有小数出现
                point = true;
                lowNum = 0.1;
            }
            else if(tmp=='+'||tmp=='-'){
                if(operate!=3&&operate!=-3){ //此处判断通用，适用于+-*
                    tNum *= num;
                }
                else{ //计算/
                    tNum /= num;
                }
                //    Toast.makeText(this, "tNum = "+tNum, Toast.LENGTH_SHORT).show();
                if(operate<0){ //累加入最终的结果
                    result -= tNum;
                }else{
                    result += tNum;
                }
                operate = tmp=='+'?1:-1;
                num = 0;
                tNum = 1;
                point = false;
            }else if(tmp=='*'){
                if(operate!=3&&operate!=-3){
                    tNum *= num;
                }else{
                    tNum /= num;
                }
                operate = operate<0?-2:2;
                point = false;
                num = 0;
            }else if(tmp=='/'){
                if(operate!=3&&operate!=-3){
                    tNum *= num;
                }else{
                    tNum /= num;
                }
                operate = operate<0?-3:3;
                point = false;
                num = 0;
            }

            else{
                //读取expression中的每个数字，doube型
                if(!point){
                    num = num*10+tmp-'0';
                }else{
                    num += (tmp-'0')*lowNum;
                    lowNum*=0.1;
                }
            }
        }
        //循环遍历结束，计算最后一个运算符后面的数
        if(operate!=3&&operate!=-3){
            tNum *= num;
        }else{
            tNum /= num;
        }
        //    Toast.makeText(this, "tNum = "+tNum, Toast.LENGTH_SHORT).show();
        if(operate<0){
            result -= tNum;
        }
        else{
            result += tNum;
        }
        //返回最后的结果
        return result;

    }
}
