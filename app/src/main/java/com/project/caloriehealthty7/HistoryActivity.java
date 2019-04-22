package com.project.caloriehealthty7;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.caloriehealthty7.Adapter.DateAdapter;
import com.project.caloriehealthty7.Adapter.MenuAdapter;
import com.project.caloriehealthty7.Model.Menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recdate;
    DateAdapter dateAdapter;
    List<Menu> mDate;

    Intent intent;

    String getdate;

    TextView date, sumall;

    FirebaseUser fuser;
    DatabaseReference reference;

    EditText edittxt1;

    FirebaseAuth mAuth;
    FirebaseUser curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mAuth = FirebaseAuth.getInstance();
        curUser = mAuth.getCurrentUser();

        sumall = findViewById(R.id.sumall);

        edittxt1 = findViewById(R.id.edittxt1);
        edittxt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    searchTime(charSequence.toString());
                } else {
                    scoreList();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        date = findViewById(R.id.date);

        intent = getIntent();
        getdate = intent.getStringExtra("date");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        recdate  = findViewById(R.id.recdate);
        recdate.setHasFixedSize(true);
        recdate.setLayoutManager(linearLayoutManager);

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Menus").child(fuser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Menu menu = snapshot.getValue(Menu.class);
                    if(menu.getId().equals(fuser.getUid()))
                        if(menu.getDate().equals(getdate))
                            sum += menu.getCal();
                    }
                sumall.setText("แคลรวม: " + sum);
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Menus").child(fuser.getUid());

        date.setText(getdate);

        scoreList();
    }

    private void scoreList() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDate = new ArrayList<>();
                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {
                    final Menu menu = postsnap.getValue(Menu.class);
                    if(menu.getId().equals(fuser.getUid()))
                        if(menu.getDate().equals(getdate))
                            mDate.add(menu);

                }

                dateAdapter = new DateAdapter(getApplicationContext(), mDate);
                recdate.setAdapter(dateAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchTime(final String s) {

        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = reference.orderByChild("time")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDate.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Menu menu = snapshot.getValue(Menu.class);

                    assert fuser != null;
                    assert menu != null;
                    if (menu.getTime().contains(s)){
                        mDate.add(menu);
                    }
                }

                dateAdapter = new DateAdapter(getApplicationContext(), mDate);
                recdate.setAdapter(dateAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
