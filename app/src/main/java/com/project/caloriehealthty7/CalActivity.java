package com.project.caloriehealthty7;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.caloriehealthty7.Model.User;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class CalActivity extends AppCompatActivity {

    CircleImageView profile_image;

    TextView username_profile;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    RadioGroup check1;

    String sex = null;

    ImageView enter;

    EditText high, width, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);

        profile_image = findViewById(R.id.profile_image);
        username_profile = findViewById(R.id.username_profile);

        check1 = findViewById(R.id.check1);
        enter = findViewById(R.id.enter);

        high = findViewById(R.id.high);
        width = findViewById(R.id.width);
        age = findViewById(R.id.age);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        check1.setOnCheckedChangeListener ( new RadioGroup.OnCheckedChangeListener () {
            public void onCheckedChanged ( RadioGroup group, int checkedId ) {
                RadioButton checkedRadio = findViewById(checkedId);

                String checkedLabel = checkedRadio.getText().toString();

                if (checkedLabel == "1") {
                    sex = "หญิง";
                } else {
                    sex = "ชาย";
                }
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sex != null && !high.getText().toString().equals("") && !width.getText().toString().equals("") && !age.getText().toString().equals(""))
                {
                    reference = FirebaseDatabase.getInstance().getReference("Profile").child(firebaseUser.getUid());

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("high", high.getText().toString());
                            hashMap.put("width", width.getText().toString());
                            hashMap.put("age", age.getText().toString());
                            hashMap.put("sex", sex);

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

                    Intent intent = new Intent(CalActivity.this, Cal2Activity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username_profile.setText(user.getUsername());
                Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
