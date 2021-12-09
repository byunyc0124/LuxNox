package bycpkn.luxnox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<SettingData> settingData;

    public ListAdapter(Context context, ArrayList<SettingData> data) {
        mContext = context;
        settingData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return settingData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public SettingData getItem(int position) {
        return settingData.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.list_setting, null);
        TextView tv = (TextView)view.findViewById(R.id.setting_name);
        tv.setText(settingData.get(position).getSettingName());
        return view;
    }
}
