package bycpkn.luxnox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class PrologueActivity extends AppCompatActivity {

    ImageView backgroundIV;
    ImageView conversationIV;
    TextView conversationTV;

    Animation fadeInAnim;

    int[] backgroundImage = { R.drawable.pro_day, R.drawable.pro_window_day,
    R.drawable.pro_fade, R.drawable.pro_night, R.drawable.pro_window_night};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prologue);

        Intent intent = new Intent(PrologueActivity.this, SplashActivity.class);
        startActivity(intent);

        backgroundIV = findViewById(R.id.pro_imageView1);    // 변경될 백그라운드 이미지
        conversationIV = findViewById(R.id.pro_imageView2); // 터치할 수 있는 대화창 이미지
        conversationTV = findViewById(R.id.pro_textView2);  // 변경될 대화창 텍스트

        fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fadein);

        backgroundIV.setImageResource(R.drawable.pro_day);

        conversationIV.setOnClickListener(new ChangeBackground());
    }

    class ChangeBackground implements View.OnClickListener{
        int i = 0;

        @Override
        public void onClick(View v) {
            i++;
            switch (i){
                case 1:
                    backgroundIV.setImageResource(backgroundImage[i]);
                    conversationTV.setText("곧 해가 지겠네. 얼른 하고 집에 가야겠어.");
                    break;
                case 2:
                    backgroundIV.setImageResource(backgroundImage[i]);
                    conversationTV.setText(". . .");
                    break;
                case 3:
                    backgroundIV.startAnimation(fadeInAnim);
                    backgroundIV.setImageResource(backgroundImage[i]);
                    conversationTV.setText("어? 뭐지? 잠들었나..");
                    break;
                case 4:
                    backgroundIV.setImageResource(backgroundImage[i]);
                    conversationTV.setText("벌써 깜깜해졌잖아. 집에 가자.");
                    break;
                case 5:
                    Intent intent = new Intent(PrologueActivity.this, Stage1.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    finish();
                    break;
            }
        }
    }
}