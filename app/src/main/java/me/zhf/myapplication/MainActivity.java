package me.zhf.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends Activity {


    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(listener);


    }

    Button.OnClickListener listener = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            button.setText("点了一下");
            Log.d("zf",getSdCardPath());
            //createDirOnSDCard("zzzfff");
            //String url="http://mimg.127.net/logo/163logo.gif";
            //Bitmap bitmap=getHttpBitmap(url);
//            if(bitmap!=null) {
//                savePicture(bitmap);
//            }else
//            {
//                Log.d("zf","啥也没有");
//            }

            UpdateTextTask updateTextTask = new UpdateTextTask(view.getContext());
            updateTextTask.execute();
        }
    };

    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
    public static String getSdCardPath() {
        boolean exist = isSdCardExist();
        String sdPath = "";
        if (exist) {
            sdPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        } else {
            sdPath = "不适用";
        }
        return sdPath;

    }

    public File createDirOnSDCard(String dir)
    {
        String  sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        File dirFile = new File(sdCardRoot + File.separator + dir +File.separator);
        Log.v("createDirOnSDCard", sdCardRoot + File.separator + dir +File.separator);
        dirFile.mkdirs();
        return dirFile;
    }

    public Bitmap getHttpBitmap(String url)
    {
        Bitmap bitmap = null;
        try
        {
            URL pictureUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) pictureUrl.openConnection();
            InputStream in = con.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (Exception e)
        {
            e.printStackTrace();
            Log.e("zf" ,e.getMessage());
        }
        return bitmap;
    }

    public void savePicture(Bitmap bitmap)
    {
        String  sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        String pictureName = sdCardRoot+"/zzzfff/" + "car"+".gif";
        File file = new File(pictureName);
        FileOutputStream out;
        try
        {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    class UpdateTextTask extends AsyncTask<Void,Integer,Integer> {
        private Context context;
        UpdateTextTask(Context context) {
            this.context = context;
        }
        /**
         * 运行在UI线程中，在调用doInBackground()之前执行
         */
        @Override
        protected void onPreExecute() {
            Toast.makeText(context,"开始执行",Toast.LENGTH_SHORT).show();
        }
        /**
         * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
         */
        @Override
        protected Integer doInBackground(Void... params) {
//            int i=0;
//            while(i<10){
//                i++;
//                publishProgress(i);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                }
//            }
            String url="http://mimg.127.net/logo/163logo.gif";
            Bitmap bitmap=  getHttpBitmap(url);
            if(bitmap!=null) {
                savePicture(bitmap);
            }else
            {
                Log.d("zf","啥也没有");
            }
            return null;

        }



        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(Integer integer) {
            Toast.makeText(context,"执行完毕",Toast.LENGTH_SHORT).show();
        }

        /**
         * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
         */
        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }
}
