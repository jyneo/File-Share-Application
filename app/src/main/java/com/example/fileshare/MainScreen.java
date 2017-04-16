package com.example.fileshare;

import com.custom.fileshare.MyTitleBar;

import com.function.fileshare.Capture;
import com.function.fileshare.Clipping;
import com.function.fileshare.Synthesis;
import com.function.fileshare.WiFiDirectActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MainScreen extends Activity {
	private MyTitleBar tbTitle;
	private MyGridView gridView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		initView();
		
		//设置事件监听
	    gridView.setOnItemClickListener(new OnItemClickListener(){
	    	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
	    		if(id==0){
	    			Intent intent = new Intent(MainScreen.this, WiFiDirectActivity.class);
	    			startActivity(intent);
	    		}
	    		if(id==1){
	    			Intent intent = new Intent(MainScreen.this, Capture.class);
	    			startActivity(intent);
	    		}
	    		if(id==2){
	    			Intent intent = new Intent(MainScreen.this, Clipping.class);
	    			startActivity(intent);
	    		}
	    		if(id==3){
	    			Intent intent = new Intent(MainScreen.this, Synthesis.class);
	    			startActivity(intent);
	    		}
	    		if(id==4){
	    			Intent intent = new Intent(MainScreen.this, Setting.class);
	    			startActivity(intent);
	    		}
	        }
	    });
	}	
	
	private void initView(){
		gridView = (MyGridView) findViewById(R.id.gridView);
		gridView.setAdapter(new MyGridAdapter(this));
		
		//初始化控件
        tbTitle = (MyTitleBar) findViewById(R.id.my_TitleBar);
        //先来设置一个背景色，当然你也可以用默认的背景色
        tbTitle.setBackColor("#7067E2");
        
        //设置中间的标题
        tbTitle.setTitleText("FileShare局域网文件共享系统");
        tbTitle.setTitleTextSize(20);
	}
		
}
