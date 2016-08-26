package com.wugian.mvpmodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.wugian.mvpmodule.utils.OkHttpClientManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String asString = null;
        OkHttpClientManager.getAsyn("http://api.qnbar.com/Api/Agent/GetInfo?imei=LETEST",
                new OkHttpClientManager.ResultCallback<String>() {

                    @Override
                    public void onError(Request request, Exception e) {
                        Log.d("lovely", "╔════════════════════════════════════════════");
                        Log.d("lovely", "╟  " + e.getLocalizedMessage());
                        Log.d("lovely", "╚════════════════════════════════════════════");
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.d("lovely", "╔════════════════════════════════════════════");
                        Log.d("lovely", "╟ rr " + response);
                        Log.d("lovely", "╚════════════════════════════════════════════");
                    }
                });
        Log.d("lovely", "╔════════════════════════════════════════════");
        Log.d("lovely", "╟ ss " + asString);
        Log.d("lovely", "╚════════════════════════════════════════════");
        String url = "http://qnbar-test.oss-cn-hangzhou.aliyuncs.com/fbms/upload/default_img/57aa89d281dbf.jpg";
//        OkHttpClientManager.downloadAsyn(url, "/mnt/usb/sda1/", new OkHttpClientManager.ResultCallback<String>() {
        OkHttpClientManager.downloadAsyn(url, "/sdcard/", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.d("lovely", "╔════════════════════════════════════════════");
                Log.d("lovely", "╟  download failure" + e.getMessage());
                Log.d("lovely", "╚════════════════════════════════════════════");
            }

            @Override
            public void onResponse(String response) {
                Log.d("lovely", "╔════════════════════════════════════════════");
                Log.d("lovely", "╟  download success" + response);
                Log.d("lovely", "╚════════════════════════════════════════════");
            }
        });

        ImageView imageView = (ImageView)this.findViewById(R.id.image);
        Glide.with(this).load("/mnt/sdcard/poster.qnj").into(imageView);
    }
}
