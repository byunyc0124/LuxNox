package bycpkn.luxnox;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class Stage3 extends AppCompatActivity {
    ImageView left;
    ImageView straight;
    ImageView right;
    ImageView backgroundImg;
    ImageView setting;
    ImageView camera;

    ImageView videoPaperIV;

    GridView itemList;

    /*
        스테이지3 배경 플래그
        0 : stage3_1
        1 : stage3_1s
        2 : stage3_1right
        3 : stage3_1rightright
        4 : stage3_2
        5 : stage3_3
    */
    int flag = 0;
    private long backKeyPressedTime = 0; // 뒤로가기 버튼 누른 시간

    int videoPlayCnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage3);

        Intent intent = new Intent(Stage3.this, LoadingActivity.class);
        startActivity(intent);

        left = findViewById(R.id.arrow_left);
        straight = findViewById(R.id.arrow_straight);
        right = findViewById(R.id.arrow_right);
        backgroundImg = findViewById(R.id.imgbg);
        setting = findViewById(R.id.setting);
        itemList = findViewById(R.id.grid_img);
        camera = findViewById(R.id.camera);

        videoPaperIV = findViewById(R.id.st3_videopaper);

        // 이미지 그리드뷰
        final GridView itemList = (GridView) findViewById(R.id.grid_img);
        MyGridAdapter gridAdapter = new MyGridAdapter(this);
        itemList.setAdapter(gridAdapter);

        checkDangerousPermission();
        // 스크린샷
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Stage3.this, "You just Captured a Screenshot", Toast.LENGTH_SHORT).show();
                takeScreenshot();
                if(videoPlayCnt == 1){
                    // 단서 st3_vendingmachine 획득
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(500);
                }
            }
        });

        // 방향 버튼 제어
        // 왼쪽
        Toast.makeText(Stage3.this, "현재 플래그 : " + flag, Toast.LENGTH_SHORT).show();
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0) {
                    backgroundImg.setImageResource(R.drawable.stage3_2);
                    flag = 4;
                }
                else if (flag == 1) {
                    backgroundImg.setImageResource(R.drawable.stage3_1);
                    flag = 0;
                }
                else if (flag == 2) {
                    backgroundImg.setImageResource(R.drawable.stage3_1);
                    flag = 0;
                }
                else if (flag == 3) {
                    backgroundImg.setImageResource(R.drawable.stage3_1right);
                    flag = 2;
                }
                else if (flag == 4) {
                    backgroundImg.setImageResource(R.drawable.stage3_1);
                    flag = 0;
                }
                else if (flag == 5) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                flagToSt3Clue();
            }
        });

        // 직진
        straight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0) {
                    backgroundImg.setImageResource(R.drawable.stage3_1_s);
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
                    backgroundImg.setImageResource(R.drawable.stage3_3);
                    flag = 5;
                }
                else if (flag == 5) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                flagToSt3Clue();
            }
        });

        // 오른쪽
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0) {
                    backgroundImg.setImageResource(R.drawable.stage3_1right);
                    flag = 2;
                }
                else if (flag == 1) {
                    backgroundImg.setImageResource(R.drawable.stage3_1);
                    flag = 0;
                }
                else if (flag == 2) {
                    backgroundImg.setImageResource(R.drawable.stage3_1rightright);
                    flag = 3;
                }
                else if (flag == 3) {
                    backgroundImg.setImageResource(R.drawable.stage3_1);
                    flag = 0;
                }
                else if (flag == 4) {
                    backgroundImg.setImageResource(R.drawable.stage3_1);
                    flag = 0;
                }
                else if (flag == 5) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                flagToSt3Clue();
            }
        });

        // 세팅 버튼
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Stage3.this, SettingActivity.class);
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

    /*
        스테이지3 배경 플래그
        0 : stage3_1
        1 : stage3_1s -> videopaper
        2 : stage3_1right -> vending button click
        3 : stage3_1rightright
        4 : stage3_2
        5 : stage3_3
    */
    private void flagToSt3Clue(){
        if (flag == 1){
            videoPaperIV.setVisibility(View.VISIBLE);
            videoPaperIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { showSt3ClueDialog(); }
            });
        }
        else {
            videoPaperIV.setVisibility(View.INVISIBLE);
        }
    }

    private void showSt3ClueDialog(){
        Dialog dialog = new Dialog(Stage3.this);
        dialog.setContentView(R.layout.st3dialog);
        ImageView posterIV = (ImageView) dialog.findViewById(R.id.imageViewForSt3Poster);
        VideoView videoView = (VideoView) dialog.findViewById(R.id.st3_clueVV);
        posterIV.setImageResource(R.drawable.st3_videopaper);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/clue");
        videoView.setVideoURI(uri);
        videoView.setMediaController(new MediaController(Stage3.this));
        dialog.show();
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                videoPlayCnt = 1;
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                videoPlayCnt = 0;
            }
        });

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
