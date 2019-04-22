package com.project.caloriehealthty7;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.project.caloriehealthty7.Model.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class AddmaduActivity extends AppCompatActivity {

    EditText edittxt2, edittxt3;

    ImageView caser, ok;

    DatabaseReference ref;
    FirebaseAuth mAuth;
    FirebaseUser curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmadu);

        edittxt2 = findViewById(R.id.edittxt2);
        edittxt3 = findViewById(R.id.edittxt3);

        mAuth = FirebaseAuth.getInstance();
        curUser = mAuth.getCurrentUser();

        caser = findViewById(R.id.caser);
        ok = findViewById(R.id.ok);

        ref = FirebaseDatabase.getInstance().getReference("Menus").child(curUser.getUid()).push();

        caser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edittxt2.getText().toString().equals("") && !edittxt3.getText().toString().equals("")) {
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String timezoneID = TimeZone.getDefault().getID();
                            TimeZone timeZone = TimeZone.getTimeZone(timezoneID);
                            Calendar c = Calendar.getInstance(timeZone);

                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
                            String formattedDate = df.format(c.getTime());
                            String formattedTime = tf.format(c.getTime());

                            Menu menu = new Menu(edittxt2.getText().toString(), curUser.getUid(), Integer.parseInt(edittxt3.getText().toString()), formattedDate, formattedTime);
                            addMenu(menu);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    private void addMenu(Menu menu) {

        // get post unique ID and upadte post key
        String key = ref.getKey();
        menu.setPostkey(key);


        // add post data to firebase database

        ref.setValue(menu).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
            }
        });
    }
}
