package com.example.mm.reading;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import com.example.mm.reading.bean.EssayInfo;
import com.example.mm.reading.dao.EsyDao;
import com.j256.ormlite.dao.Dao;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView title_txt;
    private TextView author_txt;
    private TextView content_txt;
    private Button nextPage;
    private Button abovePage;
    private Button collect;
    private Integer i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();//初始化界面

        /*
        * 收藏按钮点击事件
        */
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                try {
                    EsyDao ED = new EsyDao();
                    Dao<EssayInfo,Integer> esyDao =ED.getEsyDAO(MainActivity.this);
                    EssayInfo essayInfo = new EssayInfo(title_txt.getText().toString(),author_txt.getText().toString(),
                            content_txt.getText().toString());
                    List<EssayInfo> essayInfos =  esyDao.queryForAll();
                    if(essayInfos.contains(essayInfo) == false){ //判断文章是否被收藏过
                        esyDao.create(essayInfo);//将文章添加到数据库中
                        Toast.makeText(MainActivity.this,"收藏文章成功！",Toast.LENGTH_SHORT).show();
                        Log.d("111","收藏");
                    } else{
                        Toast.makeText(MainActivity.this,"已经收藏过了",Toast.LENGTH_SHORT).show();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        /*
        * 上一页按钮点击事件
        */
        abovePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String U;
                if(i == 1){
                    i=30;
                    U="http://www.imooc.com/api/teacher?type=3&cid="+i;
                }else{
                    i--;
                    U="http://www.imooc.com/api/teacher?type=3&cid="+i;
                }
                new EssayAsyncTask().execute(U);
            }
        });


        /*
        * 下上一页按钮点击事件
        */
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                String U="http://www.imooc.com/api/teacher?type=3&cid="+i;
                if(i == 30){
                    i=0;
                }
                new EssayAsyncTask().execute(U);
            }
        });

    }

    /*
     * 创建菜单
     */
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return  true;
    }


    /*
     * 菜单点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.my_collect:
                Intent intent = new Intent(MainActivity.this,CollectActivity.class);
                startActivity(intent);
        }
        return true;
    }


    /*
    * 初始化界面
    */
    private void initView() {
        ImageView imageView = (ImageView) findViewById(R.id.image);
        Picasso.with(this).load("http://img.mukewang.com//546418750001a81906000338-590-330.jpg").
                resize(1600,1000).centerCrop().into(imageView);
        title_txt = (TextView) findViewById(R.id.title);
        author_txt = (TextView) findViewById(R.id.author);
        content_txt = (TextView) findViewById(R.id.content);
        String url = "http://www.imooc.com/api/teacher?type=3&cid=1";
        new EssayAsyncTask().execute(url);
        nextPage = (Button) findViewById(R.id.nextPage);
        abovePage = (Button) findViewById(R.id.abovePage);
        collect = (Button) findViewById(R.id.collect);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    /*
    * 从网络中获取数据
    */
    public  class EssayAsyncTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            return request(params[0]);
        }

        private String request(String urlString) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(30000);
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                String result = null;
                if(responseCode == HttpURLConnection.HTTP_OK){
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    result = stringBuilder.toString();
                } else {
                }
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            title_txt.setText(values[0]);
            author_txt.setText(values[1]);
            content_txt.setText("\u3000\u3000"+values[2]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject object = new JSONObject(result);
                JSONObject dataObject = object.getJSONObject("data");
                String title = dataObject.getString("title");
                String author =dataObject.getString("author");
                String content=dataObject.getString("content");
                publishProgress(new String[]{title,author,content});
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
