package bycpkn.luxnox;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

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

    SensorManager sensorManager;
    Sensor accelerometer;
    ShakeDetector shakeDetector;
    GridView itemList;
    MyGridAdapter gridAdapter;

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

    int bookTouchCnt = 0;

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

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                if(bookTouchCnt==1){
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(500);
                    stationIV.setVisibility(View.VISIBLE);
                    stationIV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {showSt1ClueDialog(6);  }
                    });
                }
            }
        });

        // 이미지 그리드뷰
        itemList = findViewById(R.id.grid_img);
        gridAdapter = new MyGridAdapter(this);

        itemList.setAdapter(gridAdapter);
        //itemList.getId()
        // gridAdapter.notifyDataSetChanged();



        checkDangerousPermission();
        // 스크린샷
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Stage1.this, "You just Captured a Screenshot", Toast.LENGTH_SHORT).show();
                takeScreenshot();
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
                flagToSt1Clue();
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
                flagToSt1Clue();
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
                flagToSt1Clue();
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
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        sensorManager.registerListener(shakeDetector, accelerometer,    SensorManager.SENSOR_DELAY_UI);
    }
    // background 상황에서도 흔들림을 감지하고 적용할 필요는 없다
    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        sensorManager.unregisterListener(shakeDetector);
        super.onPause();
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
        스테이지1 배경 플래그
        0 : stage1_1
        1 : stage1_1left -> coffer, key
        2 : stage1_2
        3 : stage1_2left
        4 : stage1_3
        5 : stage1_3left -> books, station
        6 : stage1_4
        7 : stage1_5
     */
    private void flagToSt1Clue(){
        if (flag == 1){
            cofferIV.setVisibility(View.VISIBLE);
            cofferIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { showSt1ClueDialog(0); }
            });
            book1IV.setVisibility(View.INVISIBLE);
            book2IV.setVisibility(View.INVISIBLE);
            book3IV.setVisibility(View.INVISIBLE);
            book4IV.setVisibility(View.INVISIBLE);
            book5IV.setVisibility(View.INVISIBLE);
        }
        else if(flag == 5){
            book1IV.setVisibility(View.VISIBLE);
            book1IV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSt1ClueDialog(1);
                    //gridAdapter.changeImage(0, R.drawable.st1_book1);
                }
            });
            book2IV.setVisibility(View.VISIBLE);
            book2IV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSt1ClueDialog(2);
                    //gridAdapter.changeImage(1, R.drawable.st1_book2);
                }
            });
            book3IV.setVisibility(View.VISIBLE);
            book3IV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSt1ClueDialog(3);
                    //gridAdapter.changeImage(2, R.drawable.st1_book3);
                }
            });
            book4IV.setVisibility(View.VISIBLE);
            book4IV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSt1ClueDialog(4);
                    //gridAdapter.changeImage(3, R.drawable.st1_book3);
                }
            });
            book5IV.setVisibility(View.VISIBLE);
            book5IV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSt1ClueDialog(5);
                    //gridAdapter.changeImage(4, R.drawable.st1_book4);
                }
            });
            cofferIV.setVisibility(View.INVISIBLE);
            //stationIV.setVisibility(View.VISIBLE);
        }
        else {
            cofferIV.setVisibility(View.INVISIBLE);
            book1IV.setVisibility(View.INVISIBLE);
            book2IV.setVisibility(View.INVISIBLE);
            book3IV.setVisibility(View.INVISIBLE);
            book4IV.setVisibility(View.INVISIBLE);
            book5IV.setVisibility(View.INVISIBLE);
        }
    }
    private void showSt1ClueDialog(int i){
        Dialog dialog = new Dialog(Stage1.this);
        dialog.setContentView(R.layout.st1dialog);
        ImageView posterIV = (ImageView) dialog.findViewById(R.id.imageViewForPoster);
        ImageView openIV = (ImageView) dialog.findViewById(R.id.cofferOpenBtn);
        ImageView keyIV = (ImageView) dialog.findViewById(R.id.cofferKey);
        TextView firstTV = (TextView) dialog.findViewById(R.id.cofferFirstBtn);
        TextView secondTV = (TextView) dialog.findViewById(R.id.cofferSecondBtn);
        TextView thirdTV = (TextView) dialog.findViewById(R.id.cofferThirdBtn);

        if (i==0){
            posterIV.setImageResource(R.drawable.st1_coffer);
            dialog.show();
            openIV.setOnClickListener(new View.OnClickListener() { // cofferIV 이미지 바꾸기 위한 이미지뷰
                @Override
                public void onClick(View v) {
                    posterIV.setImageResource(R.drawable.st1_cofferpw);
                    firstTV.setVisibility(View.VISIBLE);
                    firstTV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String string = firstTV.getText().toString();
                            int i = Integer.parseInt(string);
                            firstTV.setText(String.valueOf(++i));
                            if(i==10) firstTV.setText("0");
                        }
                    });
                    secondTV.setVisibility(View.VISIBLE);
                    secondTV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String string = secondTV.getText().toString();
                            int i = Integer.parseInt(string);
                            secondTV.setText(String.valueOf(++i));
                            if(i==10) secondTV.setText("0");
                        }
                    });
                    thirdTV.setVisibility(View.VISIBLE);
                    thirdTV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String string = thirdTV.getText().toString();
                            int i = Integer.parseInt(string);
                            thirdTV.setText(String.valueOf(++i));
                            if(i==10) thirdTV.setText("0");
                        }
                    });
                    String fString = firstTV.getText().toString();
                    int i1 = Integer.parseInt(fString);
                    String sString = secondTV.getText().toString();
                    int i2 = Integer.parseInt(sString);
                    String tString = thirdTV.getText().toString();
                    int i3 = Integer.parseInt(tString);
                    if(i1==2 && i2==1 && i3==4){
                        posterIV.setImageResource(R.drawable.st1_cofferopen);
                        keyIV.setVisibility(View.VISIBLE);
                        keyIV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 단서 획득 후 문 따고 나갈 수 있게
                            }
                        });
                        firstTV.setVisibility(View.INVISIBLE);
                        secondTV.setVisibility(View.INVISIBLE);
                        thirdTV.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
        else if(i==1){
            posterIV.setImageResource(R.drawable.st1_book1);
            dialog.show();
        }
        else if(i==2){
            posterIV.setImageResource(R.drawable.st1_book2);
            dialog.show();
        }
        else if(i==3){
            posterIV.setImageResource(R.drawable.st1_book3);
            dialog.show();
            bookTouchCnt = 1;
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    bookTouchCnt = 0;
                }
            });
        }
        else if(i==4){
            posterIV.setImageResource(R.drawable.st1_book4);
            dialog.show();
        }
        else if(i==5){
            posterIV.setImageResource(R.drawable.st1_book5);
            dialog.show();
        }
        else if(i==6){
            posterIV.setImageResource(R.drawable.st1_station);
            dialog.show();
        }
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