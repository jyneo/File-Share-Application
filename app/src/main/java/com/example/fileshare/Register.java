package com.example.fileshare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Register extends Activity {
	/*private MyTitleBar tbTitle;*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		/*initView();*/

		Button button_sure = (Button)findViewById(R.id.register_button_sure);//获取按钮资源
		button_sure.setOnClickListener(new Button.OnClickListener(){//创建监听
            public void onClick(View v) {
                //************功能完善：可以将用户名带入到登录界面，输入密码进行登录************
                Intent intent = new Intent(Register.this, MainScreen.class);    
                startActivity(intent);    
            }
        });

		Button button_cancel = (Button)findViewById(R.id.register_button_cancel);//获取按钮资源
		button_cancel.setOnClickListener(new Button.OnClickListener(){//创建监听

            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);    
            }
        });
	}
	
	/*private void initView() {
		
        //初始化控件
        tbTitle = (MyTitleBar) findViewById(R.id.my_titlebar);
        //先来设置一个背景色，当然你也可以用默认的背景色
        tbTitle.setBackColor("#7067E2");
        
        //设置左侧文字显示文字，也可设置背景图
        tbTitle.setLeftText("返回");
        //设置左侧控件点击事件
        tbTitle.setOnClickListenerL(new OnClickListenerL() {
            public void onClick(View v) {
            	Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
        
        //设置中间的标题
        tbTitle.setTitleText("用户注册");
        tbTitle.setTitleTextSize(20);
    }*/
}
