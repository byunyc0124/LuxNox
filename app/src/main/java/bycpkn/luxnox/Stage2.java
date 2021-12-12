package bycpkn.luxnox;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

// android.speech
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

public class Stage2 extends AppCompatActivity {
    ImageView left;
    ImageView straight;
    ImageView right;
    ImageView backgroundImg;
    ImageView setting;
    ImageView camera;

    ImageView redIV, greenIV, blueIV, colorsIV;
    ImageView evIV;

    GridView itemList;
    MyGridAdapter gridAdapter;

    TextView txtInMsg; // 음성 인식 출력 텍스트뷰
    SpeechRecognizer mRecognizer;

    //음성 출력용
    TextToSpeech tts;
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
        itemList = findViewById(R.id.grid_img);
        gridAdapter = new MyGridAdapter(this);
        itemList.setAdapter(gridAdapter);

        //음성인식
        Intent stt_intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        stt_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        stt_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");   // 텍스트로 변환시킬 언어 설정
        txtInMsg = findViewById(R.id.txtInMsg);
        evIV = findViewById(R.id.st2_evbtn);

        // 음성출력 생성, 리스너 초기화
        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=android.speech.tts.TextToSpeech.ERROR){
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });

        // 엘레베이터 버튼 클릭 시 음성 인식 이벤트 발생
        evIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecognizer=SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
                mRecognizer.setRecognitionListener(listener);
                mRecognizer.startListening(stt_intent);
            }
        });

        // 권한 체크
        checkDangerousPermission();

        // 스크린샷
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Stage2.this, "You just Captured a Screenshot", Toast.LENGTH_SHORT).show();
                takeScreenshot();
            }
        });

        redIV = findViewById(R.id.st2_red);
        greenIV = findViewById(R.id.st2_green);
        blueIV = findViewById(R.id.st2_blue);
        colorsIV = findViewById(R.id.st2_colors);


        redIV.setVisibility(View.VISIBLE);
        redIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSt2ClueDialog(0);
                gridAdapter.changeImage(0, R.drawable.st2_red);
                redIV.setVisibility(View.INVISIBLE);
            }
        });
        greenIV.setVisibility(View.VISIBLE);
        greenIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSt2ClueDialog(1);
                gridAdapter.changeImage(1, R.drawable.st2_green);
                greenIV.setVisibility(View.INVISIBLE);
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
                    backgroundImg.setImageResource(R.drawable.stage2_1);
                    flag = 0;
                }
                else if (flag == 3) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 4) {
                    Toast.makeText(getApplicationContext(),"이동할 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (flag == 5) {
                    txtInMsg.setVisibility(View.INVISIBLE);
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
                flagToSt2Clue();
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
                    FuncVoiceOut("엘레베이터 버튼을 눌러야 문이 열립니다."); // 정답 음성 출력
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
                    txtInMsg.setVisibility(View.INVISIBLE);
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
                flagToSt2Clue();
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
                    backgroundImg.setImageResource(R.drawable.stage2_1right);
                    flag = 1;
                }
                else if (flag == 3) {
                    backgroundImg.setImageResource(R.drawable.stage2_1right);
                    flag = 1;
                }
                else if (flag == 4) {
                    backgroundImg.setImageResource(R.drawable.stage2_1right);
                    flag = 1;
                }
                else if (flag == 5) {
                    txtInMsg.setVisibility(View.INVISIBLE);
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
                flagToSt2Clue();
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

    // 음성 인식 객체 생성
    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(getApplicationContext(),"음성인식을 시작합니다.",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {}

        @Override
        public void onRmsChanged(float rmsdB) {}

        @Override
        public void onBufferReceived(byte[] buffer) {}

        @Override
        public void onEndOfSpeech() {}

        @Override
        public void onError(int error) {
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }

            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다. : " + message,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어준다.
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            for(int i = 0; i < matches.size() ; i++){
                txtInMsg.setText(matches.get(i));
                FuncVoiceOrderCheck(matches.get(i));
            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {}

        @Override
        public void onEvent(int eventType, Bundle params) {}
    };


    //입력된 음성 메세지 확인 후 동작 처리
    private void FuncVoiceOrderCheck(String VoiceMsg){
        if(VoiceMsg.length()<1)
            return;
        VoiceMsg=VoiceMsg.replace(" ","");//공백제거

        if(VoiceMsg.indexOf("요이땅")>-1) {
            FuncVoiceOut("정답입니다. 문이 열립니다."); // 정답 음성 출력
            evIV.setVisibility(View.INVISIBLE);

            //final AnimationDrawable drawable = (AnimationDrawable) backgroundImg.getBackground();
            backgroundImg.setImageResource(R.drawable.openev); // 배경 이미지 전환 -> 프레임 애니메이션으로 교체하기
            //drawable.start();
            flag = 5;
        }
        else {
            FuncVoiceOut("정답이 아닙니다. 버튼을 눌러 다시 대답해주세요"); // 오답 음성 출력
        }
    }

    //음성 메세지 출력 함수
    private void FuncVoiceOut(String OutMsg){
        if(OutMsg.length()<1)
            return;
        tts.setPitch(1.0f);//목소리 톤1.0
        tts.setSpeechRate(1.0f);//목소리 속도
        tts.speak(OutMsg,TextToSpeech.QUEUE_FLUSH,null);
        //어플이 종료할때는 완전히 제거
    }

    /*
        스테이지2 배경 플래그
        0 : stage2_1 -> red, green
        1 : stage2_1right -> blue, colors
        2 : stage2_2
        3 : stage2_2_1
        4 : stage2_2_2
        5 : stage2_3
        6 : stage2_4
        7 : stage2_5
        8 : stage2_6
    */
    private void flagToSt2Clue(){
        if (flag == 0){
            redIV.setVisibility(View.VISIBLE);
            redIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSt2ClueDialog(0);
                    gridAdapter.changeImage(0, R.drawable.st2_red);
                    redIV.setVisibility(View.INVISIBLE);
                }
            });
            greenIV.setVisibility(View.VISIBLE);
            greenIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSt2ClueDialog(1);
                    gridAdapter.changeImage(1, R.drawable.st2_green);
                    greenIV.setVisibility(View.INVISIBLE);
                }
            });
            blueIV.setVisibility(View.INVISIBLE);
            colorsIV.setVisibility(View.INVISIBLE);
            evIV.setVisibility(View.INVISIBLE);
            txtInMsg.setVisibility(View.INVISIBLE);
        }
        else if(flag == 1){
            blueIV.setVisibility(View.VISIBLE);
            blueIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSt2ClueDialog(2);
                    gridAdapter.changeImage(2, R.drawable.st2_blue);
                    blueIV.setVisibility(View.INVISIBLE);
                }
            });
            colorsIV.setVisibility(View.VISIBLE);
            colorsIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSt2ClueDialog(3);
                    gridAdapter.changeImage(3, R.drawable.st2_colors);
                    colorsIV.setVisibility(View.INVISIBLE);
                }
            });
            redIV.setVisibility(View.INVISIBLE);
            greenIV.setVisibility(View.INVISIBLE);
            evIV.setVisibility(View.INVISIBLE);
            txtInMsg.setVisibility(View.INVISIBLE);
        }

        else if(flag == 2) {
            evIV.setVisibility(View.VISIBLE);
            txtInMsg.setVisibility(View.VISIBLE);

            redIV.setVisibility(View.INVISIBLE);
            greenIV.setVisibility(View.INVISIBLE);
            blueIV.setVisibility(View.INVISIBLE);
            colorsIV.setVisibility(View.INVISIBLE);
        }
        else {
            evIV.setVisibility(View.INVISIBLE);
            txtInMsg.setVisibility(View.INVISIBLE);
            redIV.setVisibility(View.INVISIBLE);
            greenIV.setVisibility(View.INVISIBLE);
            blueIV.setVisibility(View.INVISIBLE);
            colorsIV.setVisibility(View.INVISIBLE);
        }
    }

    private void showSt2ClueDialog(int i){
        Dialog dialog = new Dialog(Stage2.this);
        dialog.setContentView(R.layout.st2dialog);
        ImageView posterIV = (ImageView) dialog.findViewById(R.id.imageViewForSt2Poster);

        if (i==0) {
            posterIV.setImageResource(R.drawable.st2_red);
            dialog.show();
        }
        else if(i==1){
            posterIV.setImageResource(R.drawable.st2_green);
            dialog.show();
        }
        else if(i==2){
            posterIV.setImageResource(R.drawable.st2_blue);
            dialog.show();
        }
        else if(i==3){
            posterIV.setImageResource(R.drawable.st2_colors);
            dialog.show();
        }

    }

    // 권한 요청
    private void checkDangerousPermission() {
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE, // 외부 파일 쓰기
                Manifest.permission.READ_EXTERNAL_STORAGE, // 외부 파일 읽기
                Manifest.permission.RECORD_AUDIO, // 음성 인식
                Manifest.permission.INTERNET, // 인터넷
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