package com.example.mm.reading.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.mm.reading.bean.EssayInfo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MM on 2018/3/12.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


    public static final String DB_NAME = "reading.db";

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    private static DatabaseHelper mHelper = null;

    /**
     * 单例模式 获得对象
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getInstance(Context context){
        if(mHelper == null){
            mHelper = new DatabaseHelper(context);
        }
        return mHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource,EssayInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource,EssayInfo.class,true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Map<String,Dao> daos = new HashMap<String,Dao>();
    /**
     * 获得DAO对象
     */
    public synchronized Dao getDao(Class clazz) throws SQLException{
        Dao dao = null;
        String name = clazz.getSimpleName();
        if(daos.containsKey(name)){
            dao = daos.get(name);
        }
        if(dao == null){
            dao = super.getDao(clazz);
            daos.put(name,dao);
        }
        return dao;
    }

    public void close(){
        super.close();
        for(String key:daos.keySet()){
            Dao dao =  daos.get(key);
            dao = null;
        }
    }
}
