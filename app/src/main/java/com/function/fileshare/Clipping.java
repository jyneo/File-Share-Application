package com.function.fileshare;

import java.io.File;

import com.custom.fileshare.MyTitleBar;
import com.custom.fileshare.MyTitleBar.OnClickListenerL;
import com.example.fileshare.MainScreen;
import com.example.fileshare.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

@SuppressLint({ "InflateParams", "ClickableViewAccessibility" })
public class Clipping extends Activity {
    private MyTitleBar tbTitle;
    private Context context = null;
    private PopupWindow popupWindow;

    private static final String IMAGE_FILE_NAME = "image.jpg";
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    ImageView default_select_photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.clipping);
        initView();

        default_select_photo = (ImageView) findViewById(R.id.default_select_photo);
        default_select_photo.setOnClickListener(popClick);
    }

    OnClickListener popClick = new OnClickListener(){
        public void onClick(View v) {

            initPopupWindow();
        }
    };

    //添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
    class popupDismissListener implements PopupWindow.OnDismissListener{
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    @SuppressWarnings("deprecation")
    protected void initPopupWindow(){
        View popupWindowView = getLayoutInflater().inflate(R.layout.pop, null);
        popupWindow = new PopupWindow(popupWindowView, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popupWindow.setBackgroundDrawable(dw);
        //宽度
        //popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
        //高度
        //popupWindow.setHeight(LayoutParams.FILL_PARENT);
        popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.clipping, null), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

        //设置背景半透明
        backgroundAlpha(0.5f);
        //关闭事件
        popupWindow.setOnDismissListener(new popupDismissListener());

        popupWindowView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if( popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow=null;
                }
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });

        Button takePhotoBtn = (Button) popupWindowView.findViewById(R.id.picture_selector_take_photo_btn);
        Button pickPictureBtn = (Button) popupWindowView.findViewById(R.id.picture_selector_pick_picture_btn);
        Button cancelBtn = (Button) popupWindowView.findViewById(R.id.picture_selector_cancel_btn);

        //设置拍照按钮监听
        takePhotoBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFromCapture = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可以用，可用进行存储
                String state = Environment
                        .getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    File path = Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    File file = new File(path, IMAGE_FILE_NAME);
                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                }
                startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
                Toast.makeText(context, "拍照", Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });

        //设置从相册选择按钮监听
        pickPictureBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFromGallery = new Intent();
                intentFromGallery.setType("image/*"); // 设置文件类型
                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
                Toast.makeText(context, "从相册选择", Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });

        //设置取消按钮监听
        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "取消", Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 结果码不等于取消时候
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE :
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE :
                    // 判断存储卡是否可以用，可用进行存储
                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        File tempFile = new File(path, IMAGE_FILE_NAME);
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case RESULT_REQUEST_CODE : // 图片缩放完成后
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(this.getResources(), photo);
            default_select_photo.setImageDrawable(drawable);
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
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
                Intent intent = new Intent(Clipping.this, MainScreen.class);
                startActivity(intent);
            }
        });
        //设置中间的标题
        tbTitle.setTitleText("图像裁剪");
        tbTitle.setTitleTextSize(20);
    }
}
