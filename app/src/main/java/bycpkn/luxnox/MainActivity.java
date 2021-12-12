package bycpkn.luxnox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button startBtn, continueBtn;
    MediaPlayer mediaPlayer;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int myInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = findViewById(R.id.startButton);
        continueBtn = findViewById(R.id.continueButton);

        mediaPlayer = MediaPlayer.create(this, R.raw.bgm); // 스플래시 화면이 끝남과 동시에 시작
        mediaPlayer.setLooping(true); // 앱이 종료될 때까지 계속 재생
        mediaPlayer.start();

        preferences = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = preferences.edit();

        // 저장해둔 값 불러오기
        myInt = preferences.getInt("LuxNox", 0);

        if(myInt > 0){
            continueBtn.setEnabled(true);
            continueBtn.setTextColor(getResources().getColor(R.color.norang));
        }

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myInt > 0){
                    showDialog();
                }
                else{
                    Intent intent = new Intent(MainActivity.this, PrologueActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myInt==1){ // 프롤로그 끝, st1으로
                    Intent intent = new Intent(MainActivity.this, Stage1.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
                else if(myInt==2){ // st2로
                    Intent intent = new Intent(MainActivity.this, Stage2.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
                else if(myInt==3){ // st3으로
                    Intent intent = new Intent(MainActivity.this, Stage3.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
                else if(myInt==4){ // 에필로그로
                    Intent intent = new Intent(MainActivity.this, EpilogueActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
            }
        });
    }

    void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setMessage("지금까지 플레이한 내역이 초기화됩니다.")
                .setPositiveButton("네, 괜찮습니다.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putInt("LuxNox", 0);
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this, PrologueActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}