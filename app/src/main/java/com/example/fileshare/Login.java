package com.example.fileshare;

import com.custom.fileshare.MyTitleBar;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	private MyTitleBar tbTitle;
	private MyDatabaseHelper dbHelper;
	private SharedPreferences.Editor editor;
	private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login); // 加载资源文件

		editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putString("userName", null);                      
        editor.commit();
		
		initView(); // 初始化窗口、按钮

		// 获取数据库操作对象
		dbHelper = new MyDatabaseHelper(this, "UserData.db", null, 1);
		dbHelper.getWritableDatabase();

		// 获取用户名、密码输入框资源
		final EditText loginName = (EditText) findViewById(R.id.userEditText);
		loginName.setOnFocusChangeListener(Login.onFocusAutoClearHintListener);
		final EditText password = (EditText) findViewById(R.id.passwordEditText);
		password.setOnFocusChangeListener(Login.onFocusAutoClearHintListener);
		
		Button button_login = (Button)findViewById(R.id.Login);// 获取登录按钮资源
		button_login.setOnClickListener(new OnClickListener(){// 创建监听
		@Override
		public void onClick(View v) {
			if(validation(loginName) && validation(password)){
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				cursor=db.rawQuery("select * from User where username=?", new String[] {loginName.getText().toString() });
				if (cursor.moveToFirst()) {
						if(cursor.getString(cursor.getColumnIndex("password")).equals(password.getText().toString())){
							Toast.makeText(Login.this, "登录成功",Toast.LENGTH_SHORT).show();
							cursor.close();
							Intent intent = new Intent(Login.this, MainScreen.class);
							editor=getSharedPreferences("data", MODE_PRIVATE).edit();
							editor.putString("userName", loginName.getText().toString()); 					    		
							editor.commit();
							startActivity(intent);
						}else{
							Toast.makeText(Login.this, "密码错误",Toast.LENGTH_SHORT).show();
							cursor.close();
						}
				}else{
					Toast.makeText(Login.this, "用户名不存在，请先注册",Toast.LENGTH_SHORT).show();
				}								
			}else{
				Toast.makeText(Login.this, "输入内容不符合规则",Toast.LENGTH_SHORT).show();
			}
		}
	});
		
		Button button_register = (Button)findViewById(R.id.Register);// 获取按钮资源
		button_register.setOnClickListener(new OnClickListener(){// 创建监听
            public void onClick(View v) {  
    			if(validation(loginName) && validation(password)){
    				SQLiteDatabase db = dbHelper.getWritableDatabase();
    				cursor=db.rawQuery("select * from User where username=?", new String[] {loginName.getText().toString() });
    				if (cursor.moveToFirst()){
    					Toast.makeText(Login.this, "用户名已存在，请直接登录",
    						Toast.LENGTH_SHORT).show();
    				}else{
    					ContentValues values = new ContentValues();			
    					values.put("username", loginName.getText().toString());
    					values.put("password", password.getText().toString());
    					db.insert("User", null, values); // 插入第一条数据
    					Toast.makeText(Login.this, "注册成功", Toast.LENGTH_SHORT).show();
    				}
    				cursor.close();
    			}else{
    				Toast.makeText(Login.this, "输入内容不符合规则",Toast.LENGTH_SHORT).show();
    			}
    		}
        });
		
		Button button_forget = (Button) findViewById(R.id.forget);
		button_forget.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				cursor=db.rawQuery("select * from User where username=?", new String[] {loginName.getText().toString() });
				if (cursor.moveToFirst()){Toast.makeText(Login.this, "密码为"+cursor.getString(cursor.getColumnIndex("password")),
						Toast.LENGTH_SHORT).show();
				}
				else{Toast.makeText(Login.this, "用户名不存在，请先注册",
						Toast.LENGTH_SHORT).show();}
				cursor.close();
			}
		});
	}

    // 用户名和密码hint的显示与隐藏
	public static OnFocusChangeListener onFocusAutoClearHintListener = new OnFocusChangeListener() {
        // TODO: 17/3/8 改善： 
        @Override
		public void onFocusChange(View v, boolean hasFocus) {
			EditText textView = (EditText) v;
			String hint;
			if (hasFocus) {
				hint = textView.getHint().toString();
				textView.setTag(hint);
				textView.setHint("");
			} else {
				hint = textView.getTag().toString();
				textView.setHint(hint);
			}
		}
	};
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
        // TODO: 17/3/8 Auto-generated method stub 
        return super.onOptionsItemSelected(item);
	}

    // 对输入内容进行规则验证
    public boolean validation(EditText inputContent) {
        // TODO: 17/3/8 修改成正则表达式进行验证
		String name;
		name = inputContent.getText().toString();
		if(name.length() == 0){
			return false;
		}else{
			for(int i = 0; i < name.length(); i++){
				int j = name.charAt(i);
				if((j<48) || (j>57 && j<65) || (j>90 && j<97) || (j>122)){
					return false;
				}
			}
		return true;
		}
	}
	
	private void initView() {		
        //初始化控件
        tbTitle = (MyTitleBar) findViewById(R.id.my_TitleBar);
        //先来设置一个背景色，当然你也可以用默认的背景色
        tbTitle.setBackColor("#7067E2");       
        //设置中间的标题
        tbTitle.setTitleText("FileShare局域网文件共享系统");
        tbTitle.setTitleTextSize(20);               
    }
}
