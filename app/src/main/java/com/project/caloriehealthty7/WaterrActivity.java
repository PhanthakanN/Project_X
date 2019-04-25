package com.project.caloriehealthty7;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.john.waveview.WaveView;
import com.project.caloriehealthty7.Model.Water;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

public class WaterrActivity extends AppCompatActivity {

    private WaveView waveView;

    Button btn_1, btn_0;

    int count = 0;
    int save_count = 0;
    int count_nub = 0;

    Runnable run;

    TextView nub;

    boolean clickadd = false;
    boolean addWater = false;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterr);

        waveView = (WaveView) findViewById(R.id.wave_view);

        btn_1 = findViewById(R.id.btn_1);
        btn_0 = findViewById(R.id.btn_0);

        nub = findViewById(R.id.nub);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Water").child(firebaseUser.getUid());

        waveView.setProgress(count);

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Water water = dataSnapshot.getValue(Water.class);

                if (dataSnapshot.exists()) {
                    if (!addWater) {
                        count = Integer.parseInt(water.getWater());
                        waveView.setProgress(count);
                        count_nub = count / 12;
                        nub.setText(Integer.toString(count_nub) + "/8");
                        addWater = true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clickadd) {
                    final Handler handler = new Handler();
                    run = new Runnable() {
                        @Override
                        public void run() {
                            if (count < save_count + 12) {
                                count += 1;
                                waveView.setProgress(count);
                                handler.postDelayed(run, 100);
                                clickadd = true;
                            } else {
                                if (count_nub < 8) {
                                    count_nub += 1;
                                    nub.setText(Integer.toString(count_nub) + "/8");
                                }
                                clickadd = false;
                            }


                        }
                    };
                    handler.postDelayed(run, 100);
                    save_count = count;
                    count = save_count;
                }

            }
        });

        btn_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clickadd) {
                    final Handler handler = new Handler();
                    run = new Runnable() {
                        @Override
                        public void run() {
                            if (count > save_count - 12) {
                                count -= 1;
                                waveView.setProgress(count);
                                handler.postDelayed(run, 100);
                                clickadd = true;
                            } else {
                                if (count_nub >= 1) {
                                    count_nub -= 1;
                                    nub.setText(Integer.toString(count_nub) + "/8");
                                }
                                clickadd = false;
                            }

                        }
                    };
                    handler.postDelayed(run, 100);
                    save_count = count;
                    count = save_count;
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reference = FirebaseDatabase.getInstance().getReference("Water").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String timezoneID = TimeZone.getDefault().getID();
                TimeZone timeZone = TimeZone.getTimeZone(timezoneID);
                Calendar c = Calendar.getInstance(timeZone);

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = df.format(c.getTime());

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("time", formattedDate);
                hashMap.put("water", ""+count);

                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
