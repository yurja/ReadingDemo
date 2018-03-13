package com.example.mm.reading;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mm.reading.adapter.EssayAdapter;
import com.example.mm.reading.bean.EssayInfo;
import com.example.mm.reading.dao.EsyDao;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class CollectActivity extends AppCompatActivity {

    private List<EssayInfo> essayInfos;
    private EssayAdapter adapter;
    private ListView title_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        title_listview = (ListView) findViewById(R.id.listview);

        EsyDao ED = new EsyDao();

        query(ED);//查询数据，并设置适配器
        /*
        * 设置LitsView的item点击事件
        */
        title_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EssayInfo essayInfo =essayInfos.get(position);
                Intent intent = new Intent(CollectActivity.this,EssayActivity.class);
                intent.putExtra(EssayActivity.TITLE,essayInfo.getTitle());
                intent.putExtra(EssayActivity.AUTHOR,essayInfo.getAuthor());
                intent.putExtra(EssayActivity.CONTENT,essayInfo.getContent());
                startActivity(intent);
            }
        });
    }


    /*
    * 查询数据，设置适配器
    */
    private void query(EsyDao ED) {
        try {
            Dao<EssayInfo,Integer> esyDao =ED.getEsyDAO(CollectActivity.this);
            essayInfos = esyDao.queryForAll();
            adapter = new EssayAdapter(CollectActivity.this,essayInfos);
            title_listview.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
