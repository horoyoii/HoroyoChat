package org.horoyoii.horoyochat;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "HoroyoChat";

    private FirebaseAuth mAuth;

    Button btn_request, btn_cancel;
    EditText etMail, etPassWord, etPassWord2;
    TextView strong1, strong2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_request = (Button)findViewById(R.id.regi_button_request);
        btn_cancel = (Button)findViewById(R.id.regi_button_cancel);
        etMail = (EditText)findViewById(R.id.regi_editText_id);
        etPassWord = (EditText)findViewById(R.id.regi_editText_pw);
        etPassWord2 = (EditText)findViewById(R.id.regi_editText_pw2);
        mAuth = FirebaseAuth.getInstance();
        strong1 = (TextView)findViewById(R.id.regi_strong1);
        strong2 = (TextView)findViewById(R.id.regi_strong2);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(etMail.getText().toString(), etPassWord.getText().toString(), etPassWord2.getText().toString());
            }
        });



        etPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    strong1.setText("...");
                }
                else if(charSequence.length() <= 4){
                    strong1.setText("약함");
                    strong1.setTextColor(Color.parseColor("#00ff00"));
                }else if(charSequence.length() <=6){
                    strong1.setText("중간");
                    strong1.setTextColor(Color.parseColor("#ff1493"));

                }else if(charSequence.length() >6){
                    strong1.setText("강함");
                    strong1.setTextColor(Color.parseColor("#800000"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etPassWord2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    strong2.setText("...");
                }
                else if(charSequence.length() <= 4){
                    strong2.setText("약함");
                    strong2.setTextColor(Color.parseColor("#00ff00"));
                }else if(charSequence.length() <=6){
                    strong2.setText("중간");
                    strong2.setTextColor(Color.parseColor("#ff1493"));

                }else if(charSequence.length() >6){
                    strong2.setText("강함");
                    strong2.setTextColor(Color.parseColor("#800000"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




    }
    // onCreate
    //==============================================================================================

    private void createAccount(String email, String password, String password2) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm(email, password, password2)) {
            return;
        }

        //showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    public boolean validateForm(String email, String pw, String pw2){
        if(TextUtils.isEmpty(email)){ // 이메일이 입력되지 않은 경우
            Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.",0).show();
            return false;
        }


        if(!(pw.equals(pw2))){
            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.",0).show();
            return false;
        }

        if(pw.length() <6){
            Toast.makeText(getApplicationContext(), "비밀번호를 6자 이상 입력해주세요",0).show();
            return false;
        }




        return true;
    }


    public void updateUI(FirebaseUser user){
        if(user != null){
            Intent intent =new Intent();
            intent.putExtra("email",user.getEmail());
            setResult(RESULT_OK, intent);
            finish();
        }
    }




}
