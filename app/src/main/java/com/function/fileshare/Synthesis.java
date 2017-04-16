package com.function.fileshare;

import com.custom.fileshare.MyTitleBar;
import com.custom.fileshare.MyTitleBar.OnClickListenerL;
import com.custom.fileshare.MyTitleBar.OnClickListenerR;
import com.example.fileshare.MainScreen;
import com.example.fileshare.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Synthesis extends Activity {
    private MyTitleBar tbTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.synthesis);

        initView();
    }

    private void initView() {
        //初始化控件
        tbTitle = (MyTitleBar) findViewById(R.id.my_TitleBar);
        //先来设置一个背景色，当然你也可以用默认的背景色
        tbTitle.setBackColor("#7067E2");
        //设置左侧文字显示文字，也可设置背景图
        tbTitle.setLeftText("返回");
        //设置左侧控件点击事件
        tbTitle.setOnClickListenerL(new OnClickListenerL() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Synthesis.this, MainScreen.class);
                startActivity(intent);
            }
        });
        //设置中间的标题
        tbTitle.setTitleText("图像合成");
        tbTitle.setTitleTextSize(20);

        //由于我们将右侧的控件默认为隐藏，在此显示出来
        tbTitle.setRightVisible(true);
        //设置右侧显示文字
        tbTitle.setRightText("导入图片");
        tbTitle.setOnClickListenerR(new OnClickListenerR() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Synthesis.this, "你点击了导入图片", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
