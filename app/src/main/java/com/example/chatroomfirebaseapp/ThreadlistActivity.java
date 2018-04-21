package com.example.chatroomfirebaseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThreadlistActivity extends AppCompatActivity {

    private int THREAD_ID_COUNTER = 0;

    private DatabaseReference mDatabase;
    private DatabaseReference mThreadReference;
    private FirebaseAuth mAuth;

    private Users current_user;
    private List<Threads> mThreadsList = new ArrayList<>();

    private ImageButton logout,addNewThreadButton;
    private TextView name;
    private EditText addNewThreadText;
    private ListView threadsListView;
    private String TAG = "threadlist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threadlist);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mThreadReference = mDatabase.child("threads");
        mAuth = FirebaseAuth.getInstance();

        logout = findViewById(R.id.imageButtonLogout);
        addNewThreadButton = findViewById(R.id.imageButtonAddNewThread);
        addNewThreadText = findViewById(R.id.editTextAddNewThread);
        name = findViewById(R.id.textViewFullName);
        threadsListView = findViewById(R.id.listViewThreads);

        if(getIntent().hasExtra("USER")) {
            current_user = (Users) getIntent().getExtras().getSerializable("USER");
            name.setText(current_user.getFname() + " " + current_user.getLname());
        }


        mThreadReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("ctr", "1 ");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int size = (int)snapshot.getChildrenCount();
                    if (size == 0) { return; }
                    String  [] params = new String[size];
                    Log.d(TAG, "onDataChange: " + size);
                    int i = 0;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        //Log.d(TAG, "onDataChange: "+ snapshot1.toString());
                        params[i] = snapshot1.getValue(String.class);
                        i++;
                    }
                    Threads threads = new Threads(params[0],params[1],params[2],params[3],params[4],params[5]);
                    Log.d(TAG, threads.toString());
                    mThreadsList.add(threads);
                }
                THREAD_ID_COUNTER = mThreadsList.size();
                Log.d(TAG, "ThreadListSize = " + mThreadsList.size() + " Thread_id_ctr = "+ THREAD_ID_COUNTER);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(ThreadlistActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addNewThreadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addThread = addNewThreadText.getText().toString();
                if(addThread.equals("")) {
                    Toast.makeText(ThreadlistActivity.this,"Enter Thread Name!",Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d("ctr", "2 ");
                THREAD_ID_COUNTER = mThreadsList.size();
                Log.d(TAG, "onClick: ThreadCOUNTEr " + THREAD_ID_COUNTER + " " + mThreadsList.size());


                Threads threads = new Threads("3/2/12",String.valueOf(THREAD_ID_COUNTER),
                        addThread, current_user.fname, current_user.userID, current_user.lname);
                mThreadReference.child(String.valueOf(THREAD_ID_COUNTER)).setValue(threads);
                //threadsListView.setAdapter(adapter);
                Log.d(TAG, "onClick: getThread size Notify");
            }
        });


    }
}
