package com.example.fileshare;

import com.custom.fileshare.MyTitleBar;
import com.custom.fileshare.MyTitleBar.OnClickListenerL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserInfo extends Activity {
    private MyTitleBar tbTitle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
    	
    	/*ActionBar actionBar = getActionBar();
    	actionBar.setDisplayHomeAsUpEnabled(true);
    	actionBar.setHomeAsUpIndicator(R.drawable.menu_back);*/
        initView();
    }
	
	/*@Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        return true;  
    }  
      
    //监听返回按钮操作事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
        case android.R.id.home:
        	Intent intent = new Intent(UserInfo.this, Setting.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void initView() {

        //初始化控件
        tbTitle = (MyTitleBar) findViewById(R.id.my_TitleBar);
        //先来设置一个背景色，当然你也可以用默认的背景色
        tbTitle.setBackColor("#7067E2");

        //设置左侧文字显示文字，也可设置背景图
        tbTitle.setLeftText("返回");
        //设置左侧控件点击事件
        tbTitle.setOnClickListenerL(new OnClickListenerL() {
            public void onClick(View v) {
                Intent intent = new Intent(UserInfo.this, Setting.class);
                startActivity(intent);
            }
        });

        //设置中间的标题
        tbTitle.setTitleText("个人信息");
        tbTitle.setTitleTextSize(20);

        //由于我们将右侧的控件默认为隐藏，在此显示出来
        /*tbTitle.setRightVisible(true);*/
        //设置右侧的控件的背景图，这里找了两张搜索的图片
        /*tbTitle.setRightBackImage(R.drawable.title_search);
        tbTitle.setRightBackImageSeleted(R.drawable.title_search_selected);
        tbTitle.setOnClickLisenerR(new OnClickListenerR() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyTitleBarActivity.this, "你点击了搜索", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
