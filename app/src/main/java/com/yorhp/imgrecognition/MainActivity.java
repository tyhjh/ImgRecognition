package com.yorhp.imgrecognition;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;

import com.yorhp.imgrecognition.util.FileUitl;
import com.yorhp.library.rgb.CrossAntForestImpl;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * @author Tyhj
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CrossAntForestImpl cross = new CrossAntForestImpl();

        findViewById(R.id.tvTest).setOnClickListener(v -> {
            new Thread(() -> {
                long startTime = System.currentTimeMillis();
                cross.setSimilarity(0.5F);
                cross.setAccuracy(0.9F);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;

                BitmapFactory.Options options2 = new BitmapFactory.Options();
                options2.inScaled = false;

                Bitmap template = BitmapFactory.decodeResource(getResources(), R.drawable.transparent, options);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_test, options2).copy(Bitmap.Config.ARGB_8888, true);
                List<Rect> rects=cross.matching(bitmap, template);
                for (Rect rect : rects) {
                    FileUitl.drawRect(bitmap, rect, Color.parseColor("#88ffff00"));
                }
                FileUitl.bitmapToPath(bitmap, "/sdcard/Atest.png");
                Log.i(TAG, "所用{时间为：" + (System.currentTimeMillis() - startTime) + " 匹配个数为：" + rects.size());
            }).start();
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (lacksPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    /**
     * 判断是否缺少权限
     *
     * @param permission
     * @return
     */
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED;
    }

}
