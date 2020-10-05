package com.example.paymentgateway;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    Checkout checkout;
    String apikey;
    EditText n,p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        n = findViewById(R.id.cname);
        p = findViewById(R.id.cprice);

        findViewById(R.id.b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPayment();

            }
        });
    }

    private void startPayment() {

        apikey = "rzp_test_GPelazI5eZTA7n";

        checkout = new Checkout();
        checkout.setKeyID(apikey);

        String name = n.getText().toString();
        String amount =  String.valueOf(Integer.parseInt(p.getText().toString())*100);

        try {
            JSONObject options = new JSONObject();
            options.put("name", name);
            options.put("currency", "INR");
            options.put("amount", amount);//pass amount in currency subunits
            checkout.open(MainActivity.this, options);
        } catch(Exception e) {

        }


    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this,"Payment Success",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,"Payment Failure!",Toast.LENGTH_SHORT).show();

    }
}