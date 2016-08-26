package com.wugian.mvpmodule.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wugian.mvpmodule.R;
import com.wugian.mvpmodule.presenter.UserLoginPresenter;
import com.wugian.mvpmodule.utils.DownloadUtil;
import com.wugian.mvpmodule.dowload.DownloadProgressListener;
import com.wugian.mvpmodule.dowload.FileDownloader;

import java.io.File;

public class UserLoginActivity extends Activity implements IUserLoginView{
    private EditText userName;
    private EditText pwd;
    private ProgressBar progressBar;
    private DownloadUtil downloadUtil;
    private UserLoginPresenter userLoginPresenter = new UserLoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_user_login);
        userName = (EditText) findViewById(R.id.user_name);
        pwd = (EditText) findViewById(R.id.user_pwd);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        Button login = (Button) findViewById(R.id.login);
        Button clear = (Button) findViewById(R.id.clear);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("lovely", "╔════════════════════════════════════════════");
                Log.d("lovely", "╟download start");
                Log.d("lovely", "╚════════════════════════════════════════════");
                File savDir = Environment.getExternalStorageDirectory();
                download("http://abv.cn/music/光辉岁月.mp3", savDir);
//                userLoginPresenter.login();
//                String urlString = "http://bbra.cn/Uploadfiles/imgs/20110303/fengjin/013.jpg";
//                String urlString = "http://update.xgimi.com/xgimi_video/st.mp4";
//                String urlString = "https://github-cloud.s3.amazonaws.com/releases/23216272/854b8bc8-6ad8-11e6-9613-1d62a1581fc4.exe?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAISTNZFOVBIJMK3TQ%2F20160826%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20160826T073342Z&X-Amz-Expires=300&X-Amz-Signature=f5ed2422f6d56c6506d514fc1842c81d3a5ac614599b21dd218d4a579fc4a8b2&X-Amz-SignedHeaders=host&actor_id=3983510&response-content-disposition=attachment%3B%20filename%3DGit-2.9.3.2-64-bit.exe&response-content-type=application%2Foctet-stream";
//                String urlString = "http://4k.znds.com/20140314/8kznds7.jpg";
//                final String localPath = Environment.getExternalStorageDirectory()
//                        .getAbsolutePath();
//                downloadUtil = new DownloadUtil(2, localPath, "aaa.mp4", urlString,
//                        UserLoginActivity.this);
//                downloadUtil.setOnDownloadListener(new DownloadUtil.OnDownloadListener() {

//                    @Override
//                    public void downloadStart(int fileSize) {
//                        Log.d("lovely", "╔════════════════════════════════════════════");
//                        Log.d("lovely", "╟download start");
//                        Log.d("lovely", "╚════════════════════════════════════════════");
//                    }

//                    @Override
//                    public void downloadProgress(int downloadedSize) {
//                        Log.d("lovely", "╔════════════════════════════════════════════");
//                        Log.d("lovely", "╟current download size:  " + downloadedSize);
//                        Log.d("lovely", "╚════════════════════════════════════════════");
//                    }

//                    @Override
//                    public void downloadEnd() {
//                        Log.d("lovely", "╔════════════════════════════════════════════");
//                        Log.d("lovely", "╟download end");
//                        Log.d("lovely", "╚════════════════════════════════════════════");
//                    }
//                });
//                downloadUtil.start();

            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLoginPresenter.clear();
            }
        });
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public String getUserName() {
        return userName.getText().toString().trim();
    }

    @Override
    public String getUserPwd() {
        return pwd.getText().toString().trim();
    }

    @Override
    public void clearName() {
        userName.setText("");
    }

    @Override
    public void clearPWD() {
        pwd.setText("");
    }

    @Override
    public void successJump() {
        Toast.makeText(this,"登录成功,正在跳转",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failureShow() {
        Toast.makeText(this,"登录失败",Toast.LENGTH_SHORT).show();
    }


    private DownloadTask task;

    private void exit() {
        if (task != null)
            task.exit();
    }
    private static final int PROCESSING = 1;
    private static final int FAILURE = -1;
    private void download(String path, File savDir) {
        task = new DownloadTask(path, savDir);
        new Thread(task).start();
    }

    private final class DownloadTask implements Runnable {
        private String path;
        private File saveDir;
        private FileDownloader loader;

        public DownloadTask(String path, File saveDir) {
            this.path = path;
            this.saveDir = saveDir;
        }

        /**
         * �˳�����
         */
        public void exit() {
            if (loader != null)
                loader.exit();
        }

        DownloadProgressListener downloadProgressListener = new DownloadProgressListener() {
            @Override
            public void onDownloadSize(int size) {
                Message msg = new Message();
                msg.what = PROCESSING;
                msg.getData().putInt("size", size);
                handler.sendMessage(msg);
            }
        };

        public void run() {
            try {
                // ʵ����һ���ļ�������
                loader = new FileDownloader(getApplicationContext(), path,
                        saveDir, 3);
                loader.download(downloadProgressListener);
            } catch (Exception e) {
                e.printStackTrace();
                handler.sendMessage(handler.obtainMessage(FAILURE)); // ����һ������Ϣ����
            }
        }
    }

    private Handler handler = new UIHandler();

    private final class UIHandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROCESSING:
                    int size = msg.getData().getInt("size");
//                    float num = (float) progressBar.getProgress()
//                            / (float) progressBar.getMax();
//                    int result = (int) (num * 100);
                    Log.d("lovely", "╔════════════════════════════════════════════");
                    Log.d("lovely", "╟download:  " + size);
                    Log.d("lovely", "╚════════════════════════════════════════════");
                    break;
                case FAILURE:
                    Log.d("lovely", "╔════════════════════════════════════════════");
                    Log.d("lovely", "╟download failure");
                    Log.d("lovely", "╚════════════════════════════════════════════");
                    break;
            }
        }
    }
}
