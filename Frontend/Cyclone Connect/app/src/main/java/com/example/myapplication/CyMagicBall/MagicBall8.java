package com.example.myapplication.CyMagicBall;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.databinding.Magicball8Binding;
import java.util.Random;

/** @noinspection ALL*/
public class MagicBall8 extends AppCompatActivity {
    private Magicball8Binding binding;
    private final Handler handler = new Handler();
    private int progressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Magicball8Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final int[] images = {R.drawable.ball2, R.drawable.ball3, R.drawable.ball4, R.drawable.ball5};
        binding.askButton.setOnClickListener(view -> {
            progressStatus = 0;
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.askButton.setEnabled(false);

            new Thread(() -> {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    handler.post(() -> binding.progressBar.setProgress(progressStatus));
                    try {
                        Thread.sleep(10); // Sleep for 20 milliseconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                handler.post(() -> {
                    Random random = new Random();
                    int index = random.nextInt(images.length);
                    binding.imageEightBall.setImageResource(images[index]);
                    binding.progressBar.setVisibility(View.GONE);
                    binding.askButton.setEnabled(true);
                });
            }).start();
        });

        binding.backButton.setOnClickListener(view -> finish());
    }
}
