package database.kotlin.flow9.net.filebasic;

import android.os.Environment;
import android.os.FileObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

import database.kotlin.flow9.net.filebasic.util.FileUtil;
import database.kotlin.flow9.net.filebasic.util.TextLog;

public class MainActivity extends AppCompatActivity {

    private FileObserver screenshotObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File screenShotFile =
                new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), "Screenshots");
        if(!screenShotFile.exists()) {
            screenShotFile.mkdir();
        }
        screenshotObserver = new FileUtil.ScreenCaptureObserver(screenShotFile.getAbsolutePath());
        findViewById(R.id.startObserving).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenshotObserver.startWatching();
            }
        });
        findViewById(R.id.stopObserving).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenshotObserver.stopWatching();
            }
        });
    }

    private void initTextLog() {
        TextLog.init(this);
        TextLog.mAppendTime = true;
        TextLog.mReverseReport = true;
    }
}
