package com.project.caloriehealthty7;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.caloriehealthty7.Model.Profile;

public class Cal2Activity extends AppCompatActivity {

    TextView high, width, age, bmi;

    ImageView sex;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    float result;

    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal2);

        high = findViewById(R.id.high);
        width = findViewById(R.id.width);
        age = findViewById(R.id.age);
        bmi = findViewById(R.id.bmi);

        sex = findViewById(R.id.sex);

        edit = findViewById(R.id.edit);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Profile").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile profile = dataSnapshot.getValue(Profile.class);

                high.setText(profile.getHigh());
                width.setText(profile.getWidth());
                age.setText(profile.getAge());

                if (profile.getSex() == "หญิง") {
                    sex.setImageResource(R.drawable.female);
                } else {
                    sex.setImageResource(R.drawable.male);
                }



                bmi.setText("BMI: "+ calculate(Integer.parseInt(profile.getHigh()), Integer.parseInt(profile.getWidth())));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cal2Activity.this, Cal3Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private float calculate(float high, float width) {
        float x = width;
        float y = high/100;
        result = x / (y * y);
        return result;
    }
}
