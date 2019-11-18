package com.example.text1;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;


public class ActivityOne extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_one);
       Button button=(Button)this.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Toast toast=Toast.makeText(ActivityOne.this,"你单击了cancel按钮", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        final TextView t1=(TextView)this.findViewById(R.id.textView);
        Button btn_ok=(Button)this.findViewById(R.id.button1);
         btn_ok.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                t1.setText("你是弱智么");
             }
         });

    }
}
