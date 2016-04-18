package me.zhf.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTaskTestActivity extends Activity {

    private Button button;
    private ProgressBar progressBar;
    private String API = "http://ip.taobao.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_test);
        button = (Button) this.findViewById(R.id.button);
        progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("aaaad");
                //DownImageTask asyncTask = new DownImageTask();
                //asyncTask.execute("http://www.baidu.com/img/baidu_jgylogo3.gif");


//                RestAdapter restAdapter = new RestAdapter.Builder()
//                        .setEndpoint(API).build();
//                ipapi ip = restAdapter.create(ipapi.class);
//                ip.getIpInfo("222.222.222.222", new Callback<ipmodel>() {
//                    @Override
//                    public void success(ipmodel ipmodel, Response response) {
//                        Log.d("zf", "code:" + String.valueOf(ipmodel.getCode()));
//                        Log.d("zf", "Area:" + ipmodel.getData().getArea());
//                        Log.d("zf", "City:" + ipmodel.getData().getCity());
//                        Log.d("zf", "Country:" + ipmodel.getData().getCountry());
//                        Log.d("zf", "ISP:" + ipmodel.getData().getIsp());
//                    }
//
//                    @Override
//                    public void failure(RetrofitError retrofitError) {
//                        Log.d("zf", retrofitError.getMessage());
//                    }
//                });


            }
        });
    }

    class DownImageTask extends AsyncTask<String, Integer, Bitmap> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(100);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream inputStream = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                for (int i = 1; i <= 10; i++) {
                    publishProgress(i * 10);
                    Thread.sleep(200);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            Log.d("zf", values[0].toString());
        }
    }
}
