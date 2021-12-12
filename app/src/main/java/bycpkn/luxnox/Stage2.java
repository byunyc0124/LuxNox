package bycpkn.luxnox;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class Stage2 extends AppCompatActivity {
    ImageView left;
    ImageView straight;
    ImageView right;
    ImageView backgroundImg;
    ImageView setting;
    ImageView camera;

    GridView itemList;

    /*
        스테이지2 배경 플래그
        0 : stage2_1
        1 : stage2_1right
        2 : stage2_2
        3 : stage2_2_1
        4 : stage2_2_2
        5 : stage2_3
        6 : stage2_4
        7 : stage2_5
        8 : stage2_6
    */
    int flag = 0;

    private long backKeyPressedTime = 0; // 뒤로가기 버튼 누른 시간
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage2);

        Intent intent = new Intent(Stage2.this, LoadingActivity.class);
        startActivity(intent);

        left = findViewById(R.id.arrow_left);
        straight = findViewById(R.id.arrow_straight);
        right = findViewById(R.id.arrow_right);
        backgroundImg = findViewById(R.id.imgbg);
        setting = findViewById(R.id.setting);
        itemList = findViewById(R.id.grid_img);
        camera = findViewById(R.id.camera);

        // 이미지 그리드뷰
        final GridView itemList = (GridView) findViewById(R.id.grid_img);
        MyGridAdapter gridAdapter = new MyGridAdapter(this);
        itemList.setAdapter(gridAdapter);

        checkDangerousPermission();
        // 스크린샷
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Stage2.this, "You just Captured a Screenshot", Toast.LENGTH_SHORT).show();
                takeScreenshot();
            }
        });

        // 방향 버튼 제어
        // 왼쪽
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 1) {
                    backgroundImg.setImageResource(R.drawable.stage2_2);
                    flag = 2;
                }
                else if (flag == 2) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 3) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 4) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
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
                else if (flag == 8) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // 직진
        straight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 1) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 2) {
                    backgroundImg.setImageResource(R.drawable.stage2_2_1);
                    flag = 3;
                }
                else if (flag == 3) {
                    backgroundImg.setImageResource(R.drawable.stage2_2_2);
                    flag = 4;
                }
                else if (flag == 4) {
                    backgroundImg.setImageResource(R.drawable.stage2_3);
                    flag = 5;
                }
                else if (flag == 5) {
                    backgroundImg.setImageResource(R.drawable.stage2_4);
                    flag = 6;
                }
                else if (flag == 6) {
                    backgroundImg.setImageResource(R.drawable.stage2_5);
                    flag = 7;
                }
                else if (flag == 7) {
                    backgroundImg.setImageResource(R.drawable.stage2_6);
                    flag = 8;
                }
                else if (flag == 8) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // 오른쪽
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0) {
                    backgroundImg.setImageResource(R.drawable.stage2_1right);
                    flag = 1;
                }
                else if (flag == 1) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 2) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 3) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 4) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
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
                else if (flag == 8) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // 세팅 버튼
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Stage2.this, SettingActivity.class);
                startActivity(intent);
            }
        });

    }

    // 스크린샷
    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/DCIM/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    // 권한 요청
    private void checkDangerousPermission() {
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE, // 외부 파일 쓰기
                Manifest.permission.READ_EXTERNAL_STORAGE // 외부 파일 읽기
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for(int i=0; i< permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if(permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함", Toast.LENGTH_LONG).show();
            }
            else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1) {
            for(int i = 0; i< permissions.length; i++) {
                if(grantResults[i] ==PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + "권한이 승인됨", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, permissions[i] + "권한이 승인되지 않음", Toast.LENGTH_LONG).show();
                }
            }
        }
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
