package database.kotlin.flow9.net.filebasic.util;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * call init at onCreate() method
 *
 * call TextLog.o() to log
 *
 * call TextLog.ViewLog() to list log
 *
 */
public class TextLog {

    private static Context mContext;
    private static final int LOG_FILE = 1;
    private static final int LOG_SYSTEM = 2;
    private static int mWhere = LOG_FILE | LOG_SYSTEM;
    // log file SD card path, if absent internal storage path
    private static String mPath = "";
    private static String mTag = "textLog";
    public static boolean mAppendTime = false;
    public static float mViewTextSize = 6.0f;
    public static int mMaxFileSize = 100;
    public static boolean mReverseReport = false;
    private static long mStartTime;
    private static long mLastTime;

    /**
     * if sdcard is mounted, path is sdcard
     */
    static {
        boolean hasSD = Environment
                .getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSD) {
           String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
           mPath = SDPath + "/" + "andlog.txt";
        }
    }

    /**
     * control file size and set first log
     */
    public static void init(Context context) {
        mContext = context;

        // remove from start if log exceed over limit
        if (mMaxFileSize != 0 && (mWhere & LOG_FILE) != 0) {
            File file = new File(mPath);
            // if file exceed max file size
            if (file.length() > mMaxFileSize * 1024) {
                String log = "";
                try {
                    FileInputStream fis = new FileInputStream(mPath);
                    int avail = fis.available();
                    byte[] data = new byte[avail];
                    while (fis.read(data) != -1);
                    fis.close();
                    log = new String(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // remove 90% from start
                log = log.substring(log.length() * 9/10);

                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(log.getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // set first log
        TextLog.o("------start time : " + getNowTime());
    }

    /**
     * reset log file
     */
    public static void reset() {
        if ((mWhere & LOG_FILE) != 0) {
            File file = new File(mPath);
            file.delete();
        }
        TextLog.o("------reset time : " + getNowTime());
    }

    /**
     * calculate current time
     */
    private static String getNowTime() {
        Calendar calendar = Calendar.getInstance();
        String time = String.format(Locale.KOREA, "%d-%d %d:%d:%d",
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
        return time;
    }

    /**
     * write
     */
    public static void o(String text, Object...args) {
        // 릴리즈에서 로그 기록문을 제거했으면 바로 리턴한다
        if(mWhere == 0) {
            return;
        }

        // 예외의 getMessage 가 null 을 리턴하는 경우가 있어 널 점검 필요하다
        if (text == null) {
            return;
        }

        if(args.length != 0) {
            text = String.format(text, args);
        }

        if (mAppendTime) {
            Calendar calendar = Calendar.getInstance();
            String Time = String.format(Locale.KOREA, "%d:%d:%d02.%04d = ",
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    calendar.get(Calendar.SECOND),
                    calendar.get(Calendar.MILLISECOND));
            text = Time + text;
        }

        if ((mWhere & LOG_FILE) != 0 && mPath.length() != 0) {
            File file = new File(mPath);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                if (fos != null) {
                    fos.write(text.getBytes());
                    fos.write("\n".getBytes());
                }
            } catch (IOException e) {

            } finally {
                try {
                    if(fos != null) fos.close();
                } catch (IOException e) {

                }
            }
        }

        if ((mWhere & LOG_SYSTEM) != 0) {
            Log.d(mTag, text);
        }
    }

    public static void lapstart(String text) {
        mStartTime = System.currentTimeMillis();
        mLastTime = mStartTime;
        TextLog.o("St=0000,gap=0000 "+text);
    }

    public static void lap(String text) {
        long now = System.currentTimeMillis();
        String sText = String.format("St=%4d,gap%4d "+text,
                now - mStartTime, now - mLastTime);
        mLastTime = now;
        TextLog.o(sText);
    }

    public static void ViewLog() {
        String path;
        int ch;

        StringBuilder Result = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(mPath));
            if (in != null) {
                for (;;) {
                    ch = in.read();
                    if (ch == -1) break;
                    Result.append((char)ch);
                }
            }
        } catch (IOException e) {
            Result.append("log file not found");
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {

            }
        }

        String sResult = Result.toString();
        if (mReverseReport) {
            String[] lines = sResult.split("\n");
            Result.delete(0, Result.length());
            for (int i = lines.length -1; i >= 0 ; i--) {
                Result.append(lines[i]);
                Result.append("\n");
            }
            sResult = Result.toString();
        }

        ScrollView scroll = new ScrollView(mContext);
        TextView text = new TextView(mContext);
        text.setTextSize(TypedValue.COMPLEX_UNIT_PT, mViewTextSize);
        text.setTextColor(Color.WHITE);
        text.setText("length = " + sResult.length() + "\n" + sResult);
        scroll.addView(text);

        new AlertDialog.Builder(mContext)
                .setTitle("Log")
                .setView(scroll)
                .setPositiveButton("OK", null)
                .show();
    }

    public static void addMenu(Menu menu) {
        menu.add(0,101092+1,0,"ViewLog");
        menu.add(0,101092+2,0,"ResetLog");
    }

    public static boolean execMenu(MenuItem item) {
        switch (item.getItemId()) {
            case 101092+1:
                ViewLog();
                return true;
            case 10109+2:
                reset();
                return true;
        }
        return false;
    }

}

class lg {
    public static void o(String text, Object...args) {
        TextLog.o(text, args);
    }
}