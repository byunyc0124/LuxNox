package bycpkn.luxnox;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Stage1 extends AppCompatActivity {
    ImageView left;
    ImageView straight;
    ImageView right;
    ImageView backgroundImg;
    /*
        스테이지1 배경 플래그
        0 : stage1_1
        1 : stage1_1left
        2 : stage1_2
        3 : stage1_2left
        4 : stage1_3
        5 : stage1_3left
        6 : stage1_4
        7 : stage1_5
     */
    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage1);

        left = findViewById(R.id.arrow_left);
        straight = findViewById(R.id.arrow_straight);
        right = findViewById(R.id.arrow_right);
        backgroundImg = findViewById(R.id.imgbg);

        // 방향 버튼 제어
        // 왼쪽
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0) {
                    backgroundImg.setImageResource(R.drawable.stage1_1left);
                    flag = 1;
                }
                else if (flag == 1) {
                    backgroundImg.setImageResource(R.drawable.stage1_3);
                    flag = 4;
                }
                else if (flag == 2) {
                    backgroundImg.setImageResource(R.drawable.stage1_2left);
                    flag = 3;
                }
                else if (flag == 3) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 4) {
                    backgroundImg.setImageResource(R.drawable.stage1_3left);
                    flag = 5;
                }
                else if (flag == 5) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 6) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 7) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 직진
        straight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0) {
                    backgroundImg.setImageResource(R.drawable.stage1_2);
                    straight.setImageResource(R.drawable.arrow_straight_down);
                    flag = 2;
                }
                else if (flag == 1) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 2) {
                    backgroundImg.setImageResource(R.drawable.stage1_3);
                    straight.setImageResource(R.drawable.arrow_straight);
                    flag = 4;
                }
                else if (flag == 3) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 4) {
                    backgroundImg.setImageResource(R.drawable.stage1_4);
                    flag = 6;
                }
                else if (flag == 5) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 6) {
                    backgroundImg.setImageResource(R.drawable.stage1_5);
                    flag = 7;
                }
                else if (flag == 7) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 오른쪽
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 1) {
                    backgroundImg.setImageResource(R.drawable.stage1_1);
                    flag = 0;
                }
                else if (flag == 2) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 3) {
                    backgroundImg.setImageResource(R.drawable.stage1_2);
                    flag = 2;
                }
                else if (flag == 4) {
                    backgroundImg.setImageResource(R.drawable.stage1_1left);
                    flag = 1;
                }
                else if (flag == 5) {
                    backgroundImg.setImageResource(R.drawable.stage1_3);
                    flag = 4;
                }
                else if (flag == 6) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 7) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
