package bycpkn.luxnox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class EpilogueActivity extends AppCompatActivity {

    ImageView backgroundEpilIV;
    ImageView conversationEpilIV;
    TextView conversationEpilTV;

    Animation fadeInAnim, fadeOutAnim;

    int[] backgroundImage = { R.drawable.pro_fade, R.drawable.epil_window,
            R.drawable.epil_desk }; // 눈 깜빡이는 거 나중에 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epilogue);

        Intent intent = new Intent(EpilogueActivity.this, SplashActivity.class);
        startActivity(intent);

        backgroundEpilIV = findViewById(R.id.epil_imageView1);    // 변경될 백그라운드 이미지
        conversationEpilIV = findViewById(R.id.epil_imageView2); // 터치할 수 있는 대화창 이미지
        conversationEpilTV = findViewById(R.id.epil_textView2);  // 변경될 대화창 텍스트

        fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fadein);
        fadeOutAnim = AnimationUtils.loadAnimation(this, R.anim.fadeout);

        backgroundEpilIV.setImageResource(R.drawable.pro_fade);

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
                case 3: // case 4로 눈 깜빡이는 거 나중에 추가
                    Intent intent = new Intent(EpilogueActivity.this, MainActivity.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    finish();
                    break;
            }
        }
    }
}