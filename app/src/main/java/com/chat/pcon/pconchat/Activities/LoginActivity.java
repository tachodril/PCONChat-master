package com.chat.pcon.pconchat.Activities;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.chat.pcon.pconchat.Models.UserInfo;
import com.chat.pcon.pconchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.regex.Pattern;
public class LoginActivity extends AppCompatActivity {
    //widgets
    //EditText email, password;
    Button cancel, submit;
    TextView register;
    TextInputLayout email_wrapper, pass_wrapper;
    TextInputEditText email, password;
    //variables
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    private static final String TAG = "LoginActivity";
    private boolean isFirstTime = true;
    ProgressDialog dialog;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    protected void onStart() {
        super.onStart();
        setDialog();
        //TODO 10: autologin
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        onSubmit();
        onCancel();
        onRegister();
    }

    void init() {
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        cancel = findViewById(R.id.login_cancel);
        submit = findViewById(R.id.login_submit);
        register = findViewById(R.id.login_register);
        email_wrapper = findViewById(R.id.login_email_wrapper);
        pass_wrapper = findViewById(R.id.login_password_wrapper);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }

    void setDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("...Signing you in");
        dialog.setCancelable(false);
    }

    //TODO 7: add updateUI func
    //TODO 8: dismiss dialog
    //TODO 9: start chat activity
    //TODO 11: store user info at shared preferences
    private void updateUI(FirebaseUser user, boolean isfirsttime) {
        if (user != null) {
            if (isfirsttime == true) {
                mFirestore.collection("user").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot dataSnapshot = task.getResult();
                            UserInfo info = dataSnapshot.toObject(UserInfo.class);
                            SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("name", info.name);
                            editor.putString("email", info.email);
                            editor.putString("color", info.color);
                            editor.putString("uid", info.uid);
                            editor.commit();
                            dialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                        }
                    }
                });
            } else {
                dialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }

    void onSubmit() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 5: firebase authentication
                //TODO 6: dialog show and dismiss
                //TODO: check input errors
                if (checkInputErrors()) {
                    dialog.show();
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user, true);
                            } else {
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Signing in Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    void onCancel() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: remove text and focus
                removeTextnFocus();
            }
        });
    }

    void onRegister() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: remove text and focus
                removeTextnFocus();
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void removeTextnFocus() {
        email.setText("");
        password.setText("");
        email.clearFocus();
        password.clearFocus();

        email_wrapper.setErrorEnabled(false);

        pass_wrapper.setErrorEnabled(false);
    }

    boolean checkInputErrors() {
        String mail = email.getText().toString();
        String pass = password.getText().toString();

        if (!pattern.matcher(mail).matches()) {
            email_wrapper.setError("Not a valid Email");
            return false;
        }
        email_wrapper.setErrorEnabled(false);
        if (pass.length() <= 5) {
            pass_wrapper.setError("Not a valid Password");
            return false;
        }
        pass_wrapper.setErrorEnabled(false);
        return true;
    }
}

