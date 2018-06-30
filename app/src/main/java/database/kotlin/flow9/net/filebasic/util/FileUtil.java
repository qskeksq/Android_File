package database.kotlin.flow9.net.filebasic.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.FileObserver;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import database.kotlin.flow9.net.filebasic.R;

/**
 * Basic Process
 *
 * 1. path
 *
 * 2. stream
 *
 * 3. (buffer)
 *
 * 4. read or write
 *
 * 5. close
 *
 */
public class FileUtil {

    /**
     * test internal directory
     */
    public static void internalDir(Context context) {
        try {
            // getDataDirectory
            Log.d("E.getDataDirectory.path", Environment.getDataDirectory().getPath());
            Log.d("E.getDataDirectory.abs", Environment.getDataDirectory().getAbsolutePath());
            Log.d("E.getDataDirectory.cnn", Environment.getDataDirectory().getCanonicalPath());

            // getRootDirectory
            Log.d("E.getRootDirectory.path", Environment.getRootDirectory().getPath());
            Log.d("E.getRootDirectory.abs", Environment.getRootDirectory().getAbsolutePath());
            Log.d("E.getRootDirectory.cnn", Environment.getRootDirectory().getCanonicalPath());

            // getDir
            Log.d("getDir.path", context.getDir(null, 0).getPath());
            Log.d("getDir.abs", context.getDir(null, 0).getAbsolutePath());
            Log.d("getDir.cnn", context.getDir(null, 0).getCanonicalPath());

            // getFilesDir
            Log.d("getFilesDir.path", context.getFilesDir().getPath());
            Log.d("getFilesDir.abs", context.getFilesDir().getAbsolutePath());
            Log.d("getFilesDir.cnn", context.getFilesDir().getCanonicalPath());

            // getCacheDir
            Log.d("getCacheDir.path", context.getCacheDir().getPath());
            Log.d("getCacheDir.abs", context.getCacheDir().getAbsolutePath());
            Log.d("getCacheDir.cnn", context.getCacheDir().getCanonicalPath());

        } catch (Exception e) {

        } finally {

        }
    }

    /**
     * text external directory
     */
    public static void externalDir(Context context) {
        try {
            // getExternalStorageDir
            Log.d("E.getExStorageDir.pth",
                    Environment.getExternalStorageDirectory().getPath());
            Log.d("E.getExStorageDir.abs",
                    Environment.getExternalStorageDirectory().getAbsolutePath());
            Log.d("E.getExStorageDir.cnn",
                    Environment.getExternalStorageDirectory().getCanonicalPath());

            // getExternalFilesDir
            Log.d("getExternalFilesDir.pth",
                    context.getExternalFilesDir(null).getPath());
            Log.d("getExternalFilesDir.abs",
                    context.getExternalFilesDir(null).getAbsolutePath());
            Log.d("getExternalFilesDir.cnn",
                    context.getExternalFilesDir(null).getCanonicalPath());

            // getExternalCacheDir
            Log.d("getExternalCacheDir.pth",
                    context.getExternalCacheDir().getPath());
            Log.d("getExternalCacheDir.abs",
                    context.getExternalCacheDir().getAbsolutePath());
            Log.d("getExternalCacheDir.cnn",
                    context.getExternalCacheDir().getCanonicalPath());

            // getDownloadCacheDir
            Log.d("E.getDownldCacheDir.pth",
                    Environment.getDownloadCacheDirectory().getPath());
            Log.d("E.getDownldCacheDir.abs",
                    Environment.getDownloadCacheDirectory().getAbsolutePath());
            Log.d("E.getDownldCacheDir.cnn",
                    Environment.getDownloadCacheDirectory().getCanonicalPath());

            // getExternalPublicDir
            Log.d("E.ExtStrPublicDir.pth",
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                            .getPath());
            Log.d("E.ExtStrPublicDir.pth",
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                            .getAbsolutePath());
            Log.d("E.ExtStrPublicDir.pth",
                    Environment.
                            getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                            .getCanonicalPath());
        } catch (Exception e) {

        } finally {

        }
    }

    /**
     * read internal storage file by FilInputStream
     */
    public static void readInternal1(Context context, String FILE_NAME) {
        String text = "";
        // TODO 만약 경로를 써주지 않고 FILE_NAME 만 있을 경우에도 찾아갈 수 있는지 확인해보자. 파일 클래스에서 자동으로 내부 파일 디렉토리를 찾아갈 것 같긴 하다
        File file = new File(context.getFilesDir(), FILE_NAME);
        try {
            // FileNotFoundException
            if (!file.exists()) {
                Log.e("readInternal1", "File Not Found!!");
                return;
            }
            // Open Stream
            FileInputStream fis = new FileInputStream(FILE_NAME);
            // Reader
            InputStreamReader isr = new InputStreamReader(fis);
            // Buffer
            BufferedReader br = new BufferedReader(isr);
            // line
            String line = "";
            while ((line = br.readLine()) != null) {
                text += line;
                text += "\r\n";
            }
            fis.close();
            isr.close();
            br.close();
        } catch (IOException e) {
            Log.e("readInternal1", e.toString());
        }
        Log.e("readInternal1", text);
    }

    /**
     * read internal storage file by openFileInput
     */
    public static void readInternal2(Context context, String FILE_NAME) {
        String text = "";
        File file = new File(context.getFilesDir(), FILE_NAME);
        try {
            // FileNotFoundException
            if (!file.exists()) {
                Log.e("readInternal2", "File Not Found!!");
                return;
            }
            // Open Stream
            FileInputStream fis = context.openFileInput(FILE_NAME);
            // Reader
            InputStreamReader isr = new InputStreamReader(fis);
            // Buffer
            BufferedReader br = new BufferedReader(isr);
            // line
            String line = "";
            while ((line = br.readLine()) != null) {
                text += line;
                text += "\r\n";
            }
            fis.close();
            isr.close();
            br.close();
        } catch (IOException e) {
            Log.e("readInternal2", e.toString());
        }
        Log.e("readInternal2", text);
    }

    /**
     * read internal storage file by FileReader
     */
    public static void readInternal3(Context context, String FILE_NAME) {
        String text = "";
        File file = new File(context.getFilesDir(), FILE_NAME);
        try {
            // FileNotFoundException
            if (!file.exists()) {
                Log.e("readInternal3", "File Not Found!!");
                return;
            }
            // Reader
            FileReader fr = new FileReader(file);
            // Buffer
            BufferedReader br = new BufferedReader(fr);
            // line
            String line = "";
            while ((line = br.readLine()) != null) {
                text += line;
                text += "\r\n";
            }
            fr.close();
            br.close();
        } catch (IOException e) {
            Log.e("readInternal3", e.toString());
        }
        Log.e("readInternal3", text);
    }

    /**
     * read internal storage file
     */
    public static void readInternal4(Context context, String FILE_NAME) {
        byte[] data = null;
        File file = new File(context.getFilesDir(), FILE_NAME);
        try {
            // FileNotFoundException
            if (!file.exists()) {
                Log.e("readInternal5", "File Not Found!!");
                return;
            }
            // Reader
            FileInputStream fis = new FileInputStream(file);
            // data
            data = new byte[fis.available()];
            while (fis.read(data) != -1)
            fis.close();
        } catch (IOException e) {
            Log.e("readInternal4", e.toString());
        }
        Log.e("readInternal4", new String(data));
    }

    /**
     * read internal storage file
     */
    public static void readInternal5(Context context, String FILE_NAME) {
        byte[] data = null;
        File file = new File(context.getFilesDir(), FILE_NAME);
        try {
            // FileNotFoundException
            if (!file.exists()) {
                Log.e("readInternal5", "File Not Found!!");
                return;
            }
            // Stream
            InputStream is = context.getAssets().open("test.txt");
            // data
            data = new byte[is.available()];
            while (is.read(data) != -1)
            is.close();
        } catch (IOException e) {

        }
        Log.e("readInternal5", new String(data));
    }

    /**
     * write at internal storage by FileOutputStream
     */
    public static void writeInternal1(String FILE_NAME, String content) {
        // TODO 파일이 없을 경우 이렇게 하면 자동으로 생성해 주는지 확인해보자. FileNotFoundException 뜨는지 확인해 볼 것
        // TODO 파일을 생성할 때 경로를 적어주지 않고 이름만 적어줘도 되는지 확인해보자
        try {
            // Open Stream
            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            // writer
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            // Buffer
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(content);
            bw.flush();
            bw.close();
        } catch (IOException e) {

        }
    }

    /**
     * write at internal storage by openFileOutput
     */
    public static void writeInternal2(Context context, String FILE_NAME, String content) {
        try {
            // Open Stream
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            // writer
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            // Buffer
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(content);
            bw.flush();
            bw.close();
        } catch (IOException e) {

        }
    }

    /**
     * write at internal storage by FileWriter
     */
    public static void writeInternal3(Context context, String FILE_NAME, String content) {
        FileWriter fileWriter;
        File file = new File(context.getFilesDir(), FILE_NAME);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            // Open Stream
            fileWriter = new FileWriter(file, true);
            // Buffer
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {

        }
    }


    /**
     * check external storage status
     */
    private static boolean isMediaMounted() {
        String state = Environment.getExternalStorageState();
        Log.e("isMediaMounted", state);
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * read image(bitmap) file external storage
     */
    public static Bitmap readBitmapExternal(Context context, String FILE_NAME) {
        if (!isMediaMounted()) {
            return null;
        }
        File file = new File(context.getExternalCacheDir(), FILE_NAME);
        Bitmap bitmap = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
        } catch (IOException e) {

        }
        return bitmap;
    }

    /**
     * write image(bitmap) at external storage
     */
    public static void writeBitmapExternal(Context context, String FILE_NAME, Bitmap bitmap) {
        if (!isMediaMounted()) {
            return;
        }
        File file = new File(context.getExternalCacheDir(), FILE_NAME);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {

        }
    }


    /**
     * write file at external storage by FileWriter with separate directory
     */
    public static void writeExternal(Context context, String DIR_NAME, String FILE_NAME, String content) {
        if (!isMediaMounted()) {
            return;
        }
        FileWriter fileWriter;
        // 외부 저장 공간 root 하위에 넘겨준 DIR_NAME으로 디렉토리 생성
        String dirPath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath() + "/" + DIR_NAME;
        try {
            // 폴더가 없을시 폴더 생성
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            // 파일이 없을시 파일 생성
            File file = new File(dirPath + "/"+ FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }
            // open stream
            fileWriter = new FileWriter(FILE_NAME);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {

        }
    }

    /**
     * write file at external storage by FileWriter with separate directory
     */
    public static void writePublicExternal(Context context, String FILE_NAME, String content) {
        FileWriter fileWriter;
        // 외부 저장 공간 root 하위에 넘겨준 DIR_NAME으로 디렉토리 생성
        String dirPath = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .getAbsolutePath();
        try {
            // 폴더가 없을시 폴더 생성
            File dir = new File(dirPath, FILE_NAME);
            if (!dir.exists()) {
                dir.mkdir();
            }
            // 파일이 없을시 파일 생성
            File file = new File(dirPath + FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }
            // open stream
            fileWriter = new FileWriter(FILE_NAME);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {

        }
    }

    /**
     * read file from other package, designed to share outside
     */
    public static void readExternalPackageFile(Context context, String FILE_NAME) {
        byte[] data = null;
        try {
            Context packageContext = context.createPackageContext(
                    "database.kotlin.flow9.net.filebasic",
                    Context.CONTEXT_IGNORE_SECURITY);
            FileInputStream fis = packageContext.openFileInput(FILE_NAME);
            data = new byte[fis.available()];
            while (fis.read(data) != -1)
            fis.close();
        } catch (PackageManager.NameNotFoundException e) {

        } catch (IOException e) {

        }
        Log.e("readExternalPackageFile", new String(data));
    }

    /**
     * FileObserver
     */
    public static class ScreenCaptureObserver extends FileObserver {

        public ScreenCaptureObserver(String path) {
            super(path);
        }

        @Override
        public void onEvent(int event, @Nullable String path) {
            switch (event) {
                case FileObserver.CREATE:
                    Log.d("FileObserver.CREATE", "create : " + path);
                    break;
                case FileObserver.CLOSE_WRITE:
                    Log.d("FileObserver.END", "end : " + path);
                    break;
            }
        }
    }

}