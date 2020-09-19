package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneAuth extends AppCompatActivity {
    EditText number,code;
    FirebaseAuth auth;
    //To get otp , to do auto verify
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    //To resend otp
    PhoneAuthProvider.ForceResendingToken token;
    //To hold verification id
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        number = findViewById(R.id.number);
        code = findViewById(R.id.otp);
        auth = FirebaseAuth.getInstance();

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                //to verify id
                id = s;
                //token to resend
                token = forceResendingToken;
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                //to auto verify
                signPhoneAuth(phoneAuthCredential);

            }

            @Override

            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(PhoneAuth.this,"Failed to Verify",Toast.LENGTH_SHORT).show();

            }
        };


    }

    private void signPhoneAuth(PhoneAuthCredential phoneAuthCredential) {
        auth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()){
                  startActivity(new Intent(PhoneAuth.this,HomeActivity.class));
                  finish();
              }
              else {
                  Toast.makeText(PhoneAuth.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
              }
            };
        });
    }

    public void send(View view) {
        String num = number.getText().toString();
        if (num.isEmpty()){
            number.setError("Cant be empty");
        }
        else {
            PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+num,60 , TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,callbacks);
        }


    }

    public void verify(View view) {
        String otp = code.getText().toString();
        if (otp.isEmpty()){
            code.setError("Cant be empty");
        }
        else {

        //to authenticate manually
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id,otp);
        signPhoneAuth(credential);
        }

    }

    public void resend(View view) {
        String num = number.getText().toString();
        if (num.isEmpty()){
            Toast.makeText(this,"Cant be Empty",Toast.LENGTH_SHORT).show();
        }
        else {
            PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+num,60,TimeUnit.SECONDS,TaskExecutors.MAIN_THREAD,callbacks,token);
            Toast.makeText(this,"OTP sent",Toast.LENGTH_SHORT).show();
        }
    }
}