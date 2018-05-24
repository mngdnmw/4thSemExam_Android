package mafioso.so.so.android.GUI.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import mafioso.so.so.android.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView mafiaImg = findViewById(R.id.imageView_mafia);
        ImageView pandaImg = findViewById(R.id.imageView_panda);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_anim);

        mafiaImg.startAnimation(animation);
        pandaImg.startAnimation(animation);

        Thread timer = new Thread() {

            @Override
            public void run() {

            try {
                sleep(4000);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                super.run();
            } catch(
            InterruptedException e)

            {
                e.printStackTrace();
            }

        }

        };
        timer.start();
    }
}
