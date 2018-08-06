package org.horoyoii.horoyochat.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.horoyoii.horoyochat.R;
import org.horoyoii.horoyochat.app.HoroyoChatApp;
import org.horoyoii.horoyochat.util.AuthenticationUtil;
import org.horoyoii.horoyochat.util.FirebaseUtil;
import org.horoyoii.horoyochat.util.StorageUtil;

import java.io.File;

/*
* Created by Horoyoii 2018.07.20
 */

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "checkcheck";
    public static final int LOGIN_TO_REGI = 1002;
    Button btnLogin, btnRegister;
    EditText etEmail, etPassWord;
    ProgressBar progressBar;
    ImageView imageView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.hide();
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.login_button);
        etEmail = (EditText) findViewById(R.id.login_editText_id);
        etPassWord = (EditText) findViewById(R.id.login_editText_pw);
        btnRegister = (Button)findViewById(R.id.login_button_register);
        progressBar = (ProgressBar)findViewById(R.id.login_progressBar);
        imageView = (ImageView)findViewById(R.id.imageView2);

        RequestOptions requestOptions = new RequestOptions();
        DrawableImageViewTarget target = new DrawableImageViewTarget(imageView);
        Glide.with(this).setDefaultRequestOptions(requestOptions.override(300,300)).load(R.raw.rain).into(target);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, LOGIN_TO_REGI);

            }
        });


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", user.getEmail());
                    editor.apply();

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn(etEmail.getText().toString(), etPassWord.getText().toString());
            }
        });





    }
    //=================================================================================================

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == LOGIN_TO_REGI){
            if(resultCode == RESULT_OK){
                etEmail.setText(data.getStringExtra("email"));
            }else{
                // 다른 곳으로부터 정보를 받을 경우
            }
        }


    }


    public void SignIn(String email, String password){
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.",0).show();
            return;
        }

        showProgressDialog();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        hideProgressDialog();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        //==========================================================================
                        // 로그인 성공 지점
                        //==========================================================================
                        else{
                            FirebaseUtil.init();
                            StorageUtil.init();

                            String savePath = HoroyoChatApp.getInstance().getCacheDir().toString()+"/save_profile_image";
                            File dir = new File(savePath);
                            if(!dir.exists()){
                                dir.mkdirs();
                            }

                            AuthenticationUtil.init(mAuth.getCurrentUser());

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }


                    }
                });
    }


    public  void showProgressDialog(){
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgressDialog(){
        progressBar.setVisibility(View.GONE);
    }


}
