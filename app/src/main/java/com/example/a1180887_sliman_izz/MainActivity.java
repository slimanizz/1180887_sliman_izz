package com.example.a1180887_sliman_izz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ImageView backgroundImageView;
    private ImageView nightImageView;
    private int[] backgroundImages = {R.drawable.day, R.drawable.night};
    private int count = 0;
    private float maxFAD = 1.0F, minFAD = 0.7F; // fading Range
    private Handler cloudAnimationHandler1 = new Handler();
    private Handler cloudAnimationHandler2 = new Handler();
    private Handler switchHandler = new Handler();
    private final int switchDelay = 10000; // 10 seconds
    private final int cloudAnimationDelay1 = 8000, cloudAnimationDelay2 = 12000;

    private int iterationCount = 0;
    private final int maxIterations = 2; // Set the maximum number of iterations here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageMoon = findViewById(R.id.moon);
        final ImageView imageSun = findViewById(R.id.sun);
        backgroundImageView = findViewById(R.id.backgroundImageView);

        imageMoon.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.moon));
        imageSun.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.sun));
        startCloudAnimation(); // for the first cloud
        startCloudAnimation2();// for the second cloud

        // Switch background image after 10 seconds and back to day photo after another 10 seconds
        // Switch background image after 10 seconds and back to day photo after another 10 seconds
        switchHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switchBackgroundImage();
                switchHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switchBackgroundImage();
                        // Continue the switching process if needed
                    }
                }, switchDelay);
            }
        }, switchDelay);
    }

    private void startCloudAnimation() {
        final ImageView imageCloud1 = findViewById(R.id.cloud1);
        imageCloud1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.cloud));
        cloudAnimationHandler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                startCloudAnimation();
            }
        }, cloudAnimationDelay1); // 8 seconds
    }

    private void startCloudAnimation2() {
        final ImageView imageCloud2 = findViewById(R.id.cloud2);
        imageCloud2.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.cloud2));
        cloudAnimationHandler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                startCloudAnimation2();
            }
        }, cloudAnimationDelay2);// 12 seconds
    }

    private void switchBackgroundImage() {
        count = (count + 1) % backgroundImages.length;

        // Switching between day and night images
        if (count == 0) {
            backgroundImageView.setImageResource(backgroundImages[count]);
            nightImageView.setVisibility(View.GONE);
            backgroundImageView.setVisibility(View.VISIBLE);
        } else if (count == 1) {
            nightImageView.setImageResource(backgroundImages[count]);
            backgroundImageView.setVisibility(View.GONE);
            nightImageView.setVisibility(View.VISIBLE);
        }
    }
}