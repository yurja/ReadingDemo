package com.example.mm.reading.dao;

import android.content.Context;

import com.example.mm.reading.bean.EssayInfo;
import com.example.mm.reading.db.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by 李颖佳 on 2018/3/12.
 */

public class EsyDao {

    public Dao getEsyDAO(Context context) throws SQLException{
        return  DatabaseHelper.getInstance(context).getDao(EssayInfo.class);
    }
}
