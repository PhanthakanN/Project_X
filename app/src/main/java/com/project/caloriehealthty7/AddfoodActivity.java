package com.project.caloriehealthty7;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.caloriehealthty7.Adapter.MenuAdapter;
import com.project.caloriehealthty7.Model.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AddfoodActivity extends AppCompatActivity {

    ImageView add;

    RecyclerView recadd;
    MenuAdapter menuAdapter;
    List<Menu> mMenu;

    FirebaseUser fuser;
    DatabaseReference reference;

    EditText edittxt1;

    FirebaseAuth mAuth;
    FirebaseUser curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfood);

        add = findViewById(R.id.add);

        mAuth = FirebaseAuth.getInstance();
        curUser = mAuth.getCurrentUser();

        edittxt1 = findViewById(R.id.edittxt1);
        edittxt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    searchMenu(charSequence.toString());
                } else {
                    scoreList();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddfoodActivity.this, AddmaduActivity.class));
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        recadd  = findViewById(R.id.recadd);
        recadd.setHasFixedSize(true);
        recadd.setLayoutManager(linearLayoutManager);

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Menus").child(fuser.getUid());


        scoreList();
    }

    private void scoreList() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMenu = new ArrayList<>();
                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {
                    Menu menu = postsnap.getValue(Menu.class);
                    if(menu.getId().equals(fuser.getUid()))
                        mMenu.add(menu);
                }

                Collections.sort(mMenu, new Comparator<Menu>() {
                    @Override
                    public int compare(Menu obj1, Menu obj2) {
                        StringBuffer buffer1 = new StringBuffer(obj1.getDate().replace("/", ""));
                        StringBuffer buffer2 = new StringBuffer(obj2.getDate().replace("/", ""));
                        if (Integer.parseInt(buffer1.toString()) < Integer.parseInt(buffer2.toString())) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });

                menuAdapter = new MenuAdapter(getApplicationContext(), mMenu);
                recadd.setAdapter(menuAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchMenu(final String s) {

        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = reference.orderByChild("foodname")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMenu.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Menu menu = snapshot.getValue(Menu.class);

                    assert fuser != null;
                    assert menu != null;
                    if (menu.getFoodname().contains(s)){
                        mMenu.add(menu);
                    }
                }

                menuAdapter = new MenuAdapter(getApplicationContext(), mMenu);
                recadd.setAdapter(menuAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
