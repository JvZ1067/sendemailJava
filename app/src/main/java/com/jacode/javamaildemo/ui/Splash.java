package com.jacode.javamaildemo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;

import com.jacode.javamaildemo.MainActivity;
import com.jacode.javamaildemo.databinding.ActivitySplashBinding;
import com.jacode.javamaildemo.model.ConfigurationModel;
import com.jacode.javamaildemo.provider.ConfigurationProvider;

public class Splash extends AppCompatActivity {

    /**
     * link view with ViewBinding
     */
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.motionLayout.addTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                if (checkConfiguration()){
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Splash.this, ConfigurationHost.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });
    }

    private Boolean checkConfiguration(){
        ConfigurationModel model= ConfigurationProvider.ReadConfiguration(this);
        return model.getPort()!=0;

    }
}