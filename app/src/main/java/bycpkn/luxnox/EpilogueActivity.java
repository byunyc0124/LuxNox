package bycpkn.luxnox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class EpilogueActivity extends AppCompatActivity {

    ImageView backgroundEpilIV;
    ImageView conversationEpilIV;
    TextView conversationEpilTV;
    TextView conversationIdTV;
    ImageView eyesIV;
    TextView continueTV;

    Animation fadeInAnim, growAnim;

    int[] backgroundImage = { R.drawable.pro_fade, R.drawable.epil_window,
            R.drawable.epil_desk }; // 눈 깜빡이는 거 나중에 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epilogue);

        Intent intent = new Intent(EpilogueActivity.this, SplashActivity.class);
        startActivity(intent);

        backgroundEpilIV = findViewById(R.id.epil_imageView1);    // 변경될 백그라운드 이미지
        conversationEpilIV = findViewById(R.id.epil_imageView2);  // 터치할 수 있는 대화창 이미지
        conversationEpilTV = findViewById(R.id.epil_textView2);   // 변경될 대화창 텍스트
        conversationIdTV = findViewById(R.id.epil_textView1);     // 교수님 텍스트
        eyesIV = findViewById(R.id.epil_imageView3);              // 깜빡이는 눈
        continueTV = findViewById(R.id.epil_textView3);           // to be continued

        fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fadein);
        growAnim = AnimationUtils.loadAnimation(this, R.anim.grow);

        backgroundEpilIV.setImageResource(R.drawable.pro_fade);

        eyesIV.setVisibility(View.INVISIBLE);
        continueTV.setVisibility(View.INVISIBLE);

        conversationEpilIV.setOnClickListener(new ChangeEpilBackground());
    }

    class ChangeEpilBackground implements View.OnClickListener{
        int i = 0;
        @Override
        public void onClick(View v) {
            i++;
            switch (i){
                case 1:
                    backgroundEpilIV.startAnimation(fadeInAnim);
                    backgroundEpilIV.setImageResource(backgroundImage[i]);
                    conversationEpilTV.setText("앗! 벌써 아침이잖아? 내가 방금 봤던 건 다 꿈이였나?");
                    break;
                case 2:
                    backgroundEpilIV.setImageResource(backgroundImage[i]);
                    conversationEpilTV.setText("몸이 찌뿌둥하니 악몽을 꾼 것 같네..");
                    break;
                case 3:
                    eyesIV.setVisibility(View.VISIBLE);
                    Glide.with(EpilogueActivity.this).load(R.drawable.eyes).into(eyesIV);
                    conversationEpilIV.setVisibility(View.INVISIBLE);
                    conversationEpilTV.setVisibility(View.INVISIBLE);
                    conversationIdTV.setVisibility(View.INVISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            backgroundEpilIV.startAnimation(growAnim);
                            continueTV.startAnimation(fadeInAnim);
                            continueTV.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(EpilogueActivity.this, MainActivity.class);
                                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                    finish();
                                }
                            }, 2500);
                        }
                    }, 3000);
                    break;
            }
        }
    }
}