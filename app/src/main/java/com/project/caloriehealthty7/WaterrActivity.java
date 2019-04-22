package com.project.caloriehealthty7;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.john.waveview.WaveView;

public class WaterrActivity extends AppCompatActivity {

    private WaveView waveView;

    Button btn_1, btn_0;

    int count = 0;
    int save_count = 0;
    int count_nub = 0;

    Runnable run;

    TextView nub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterr);

        waveView = (WaveView) findViewById(R.id.wave_view);

        btn_1 = findViewById(R.id.btn_1);
        btn_0 = findViewById(R.id.btn_0);

        nub = findViewById(R.id.nub);

        waveView.setProgress(0);

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                run = new Runnable() {
                    @Override
                    public void run() {
                        if (count < save_count+12) {
                            count += 1;
                            waveView.setProgress(count);
                            handler.postDelayed(run, 100);
                        }

                    }
                };
                handler.postDelayed(run, 100);
                save_count = count;
                count = save_count;
                count_nub += 1;
                nub.setText(Integer.toString(count_nub) + "/8");
            }
        });

        btn_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count >= 12) {
                    final Handler handler = new Handler();
                    run = new Runnable() {
                        @Override
                        public void run() {
                            if (count > save_count - 12) {
                                count -= 1;
                                waveView.setProgress(count);
                                handler.postDelayed(run, 100);
                            }

                        }
                    };
                    handler.postDelayed(run, 100);
                    save_count = count;
                    count = save_count;
                }
                if (count_nub >= 1) {
                    count_nub -= 1;
                    nub.setText(Integer.toString(count_nub) + "/8");
                }
            }
        });
    }
}
