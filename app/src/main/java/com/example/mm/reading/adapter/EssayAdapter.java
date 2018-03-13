package com.example.mm.reading.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mm.reading.R;
import com.example.mm.reading.bean.EssayInfo;

import java.util.List;

/**
 * Created by 李颖佳 on 2018/3/12.
 */

public class EssayAdapter extends BaseAdapter {

    private Context mcontext;
    private List<EssayInfo> essayInfoList;

    public EssayAdapter(Context mcontext, List<EssayInfo> essayInfoList) {
        this.mcontext = mcontext;
        this.essayInfoList = essayInfoList;
    }

    @Override


    public int getCount() {
        return essayInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return essayInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        convertView = layoutInflater.inflate(R.layout.list_item, null, true);
        EssayInfo essayInfo = (EssayInfo) getItem(position);
        TextView textView = (TextView) convertView.findViewById(R.id.Essaytitle);
        textView.setText(essayInfo.getTitle());
        return convertView;
    }
}
