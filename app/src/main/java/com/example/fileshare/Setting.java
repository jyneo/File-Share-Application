package com.example.fileshare;

import java.util.ArrayList;
import java.util.HashMap;

import com.custom.fileshare.MyTitleBar;
import com.custom.fileshare.MyTitleBar.OnClickListenerL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

public class Setting extends Activity {
	private MyTitleBar tbTitle;
	private CornerListView cornerListView1 = null;
	private CornerListView cornerListView2 = null;

	ArrayList<HashMap<String, String>> map_list1 = null;
	ArrayList<HashMap<String, String>> map_list2 = null;

	/** Called when the activity is first created. */
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		initView();
    	
    	/*ActionBar actionBar = getActionBar();
    	actionBar.setDisplayHomeAsUpEnabled(true);
    	actionBar.setHomeAsUpIndicator(R.drawable.menu_back);*/

		cornerListView1 = (CornerListView)findViewById(R.id.list1);
		cornerListView2 = (CornerListView)findViewById(R.id.list2);

		getDataSource1();
		getDataSource2();

		SimpleAdapter adapter1 = new SimpleAdapter(getApplicationContext(), map_list1, R.layout.list_item,
				new String[] { "item" }, new int[] { R.id.list_item_text });
		cornerListView1.setAdapter(adapter1);
		cornerListView1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				if (position == 0) {
					Intent intent = new Intent(Setting.this, UserInfo.class);
					startActivity(intent);
				}else if(position == 1){
					System.out.println("1");
				}
			}
		});

		SimpleAdapter adapter2 = new SimpleAdapter(getApplicationContext(), map_list2, R.layout.list_item,
				new String[]{ "item" }, new int[]{ R.id.list_item_text });
		cornerListView2.setAdapter(adapter2);
		cornerListView2.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				if (position == 0) {
					System.out.println("2");
				}else if(position == 1){
					System.out.println("3");
				}else if(position == 2){
					System.out.println("4");
				}else if(position == 3){
					System.out.println("5");
				}
			}
		});

		Button button_logout = (Button)findViewById(R.id.logout);//获取按钮资源
		button_logout.setOnClickListener(new Button.OnClickListener(){//创建监听
			public void onClick(View v) {
				Intent intent = new Intent(Setting.this, Login.class);
				startActivity(intent);
			}
		});
	}

	public ArrayList<HashMap<String, String>> getDataSource1() {

		map_list1 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();

		map1.put("item", "个人信息");
		map2.put("item", "修改密码");

		map_list1.add(map1);
		map_list1.add(map2);

		return map_list1;
	}

	public ArrayList<HashMap<String, String>> getDataSource2() {

		map_list2 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		HashMap<String, String> map3 = new HashMap<String, String>();
		HashMap<String, String> map4 = new HashMap<String, String>();

		map1.put("item", "分享软件");
		map2.put("item", "清除缓存");
		map3.put("item", "意见反馈");
		map4.put("item", "联系我们");

		map_list2.add(map1);
		map_list2.add(map2);
		map_list2.add(map3);
		map_list2.add(map4);

		return map_list2;
	}

    /*private List<Map<String, Object>>  getDataSource2() {
    	map_list2 = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("text", "");
                map.put("img", R.drawable.logo);
        map_list2.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "");
                map.put("img", R.drawable.logo);
        map_list2.add(map);

        return map_list2;
    }*/

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    //监听返回按钮操作事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
        case android.R.id.home:
        	Intent intent = new Intent(Setting.this, MainScreen.class);
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
				Intent intent = new Intent(Setting.this, MainScreen.class);
				startActivity(intent);
			}
		});

		//设置中间的标题
		tbTitle.setTitleText("用户设置");
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
