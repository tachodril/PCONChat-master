package com.chat.pcon.pconchat.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.chat.pcon.pconchat.R;
import com.chat.pcon.pconchat.Models.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {
    String uid;
    TextView name;
    TextView email;
    FirebaseFirestore firestore;
    ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        setDialog();
       // dialog.show();
        getDetails();
    }
    void init(){
        name = findViewById(R.id.profile_name);
        email = findViewById(R.id.profile_email);
        uid = getIntent().getStringExtra("uid");
        firestore = FirebaseFirestore.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    void setDialog(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("...Loading");
        dialog.setCancelable(false);
    }
    void getDetails(){

        firestore.collection("user").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    UserInfo inf0 = task.getResult().toObject(UserInfo.class);
                    name.setText(inf0.name);
                    email.setText(inf0.email);
                    dialog.dismiss();
                }else{
                    Toast.makeText(ProfileActivity.this,"Unable to load data",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
