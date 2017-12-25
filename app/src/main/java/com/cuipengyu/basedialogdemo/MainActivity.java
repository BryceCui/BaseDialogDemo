package com.cuipengyu.basedialogdemo;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    BaseDialog builder;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn_create);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new BaseDialog.Builder(MainActivity.this).setTitle("这是标题").setMessage("这是内容").setPositiveTextView("这是确定按钮").setNegativeTextView("这是取消按钮").create();
                builder.setNegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "点击了取消", Toast.LENGTH_SHORT).show();
                        builder.dismiss();
                        builder.cancel();
                    }
                });
                builder.setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "点击了确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }
}
