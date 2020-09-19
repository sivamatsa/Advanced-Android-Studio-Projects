package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth auth;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv = findViewById(R.id.tv);
        auth = FirebaseAuth.getInstance();
        //Text displayed Email or Phone with Welcome
        tv.setText(" Welcome \n" + auth.getCurrentUser().getEmail() + "\n" + auth.getCurrentUser().getPhoneNumber() + "\n" +" \uD83D\uDC4B ");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.exit:
                auth.signOut();
                startActivity(new Intent(this,MainActivity.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}