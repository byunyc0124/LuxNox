package bycpkn.luxnox;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class Stage1 extends AppCompatActivity {
    ImageView left;
    ImageView straight;
    ImageView right;
    ImageView backgroundImg;
    ImageView setting;
    ImageView camera;
    FrameLayout frame;

    ImageView book1IV, book2IV, book3IV, book4IV, book5IV;
    ImageView stationIV, cofferIV, keyIV;

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
    private static final String CAPTURE_PATH = "/CAPTURE_TEST"; // 캡쳐 위치

    private long backKeyPressedTime = 0; // 뒤로가기 버튼 누른 시간
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage1);

        Intent intent = new Intent(Stage1.this, LoadingActivity.class);
        startActivity(intent);

        left = findViewById(R.id.arrow_left);
        straight = findViewById(R.id.arrow_straight);
        right = findViewById(R.id.arrow_right);
        backgroundImg = findViewById(R.id.imgbg);
        setting = findViewById(R.id.setting);
        camera = findViewById(R.id.camera);
        frame = findViewById(R.id.fragment_container);

        book1IV = findViewById(R.id.st1_book1);
        book2IV = findViewById(R.id.st1_book2);
        book3IV = findViewById(R.id.st1_book3);
        book4IV = findViewById(R.id.st1_book4);
        book5IV = findViewById(R.id.st1_book5);
        stationIV = findViewById(R.id.st1_station);
        cofferIV = findViewById(R.id.st1_coffer);
        keyIV = findViewById(R.id.st1_key);

        // 이미지 그리드뷰
        final GridView itemList = (GridView) findViewById(R.id.grid_img);
        MyGridAdapter gridAdapter = new MyGridAdapter(this);
        itemList.setAdapter(gridAdapter);

        // 스크린샷
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String folder = "Test_Directory"; // 폴더 이름
                try {
                    // 현재 날짜로 파일을 저장하기
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                    // 년월일시분초
                    Date currentTime_1 = new Date();
                    String dateString = formatter.format(currentTime_1);
                    File sdCardPath = Environment.getExternalStorageDirectory();
                    File dirs = new File(Environment.getExternalStorageDirectory(), folder);

                    if (!dirs.exists()) { // 원하는 경로에 폴더가 있는지 확인
                        dirs.mkdirs(); // Test 폴더 생성
                        Log.d("CAMERA_TEST", "Directory Created");
                    }
                    frame.buildDrawingCache();
                    Bitmap captureView = frame.getDrawingCache();
                    FileOutputStream fos;
                    String save;

                    try {
                        save = sdCardPath.getPath() + "/" + folder + "/" + dateString + ".jpg";
                        // 저장 경로
                        fos = new FileOutputStream(save);
                        captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos); // 캡쳐
                        // 미디어 스캐너를 통해 모든 미디어 리스트를 갱신시킨다.
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                                Uri.parse("file://" + Environment.getExternalStorageDirectory())));

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), dateString + ".jpg 저장",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("Screen", "" + e.toString());
                }
            }
        });

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
                    backgroundImg.setImageResource(R.drawable.stage1_3);
                    flag = 4;
                }
                else if (flag == 4) {
                    backgroundImg.setImageResource(R.drawable.stage1_3left);
                    flag = 5;
                }
                else if (flag == 5) {
                    backgroundImg.setImageResource(R.drawable.stage1_1);
                    flag = 0;
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
                    flag = 2;
                }
                else if (flag == 1) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 2) {
                    backgroundImg.setImageResource(R.drawable.stage1_3);
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
                    backgroundImg.setImageResource(R.drawable.stage1_3left);
                    flag = 5;
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

        // 세팅 버튼
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Stage1.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }
}
