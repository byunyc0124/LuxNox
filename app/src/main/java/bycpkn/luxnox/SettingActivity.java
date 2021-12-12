package bycpkn.luxnox;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {
    ArrayList<SettingData> settingDataList;
    ListView settinglist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        this.InitializeSettingData();

        settinglist = findViewById(R.id.list_setting);
        final ListAdapter myAdapter = new ListAdapter(this, settingDataList);

        settinglist.setAdapter(myAdapter);
        settinglist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(),
                        myAdapter.getItem(position).getSettingName(),
                        Toast.LENGTH_LONG).show();
                }
            });
        }

    public void InitializeSettingData() {
        settingDataList = new ArrayList<SettingData>();
        settingDataList.add(new SettingData("윤용익 교수님"));
        settingDataList.add(new SettingData("모프 수업"));
        settingDataList.add(new SettingData("재밌어요! 짱!!"));
        settingDataList.add(new SettingData("만든 이"));
        settingDataList.add(new SettingData("경나 & 영채"));
    }
}

