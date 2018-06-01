package com.example.awsintegrationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
/*
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;*/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();*/

       new Thread(new Runnable() {
           @Override
           public void run() {
               KMSExample kms = new KMSExample("f93e7516-d166-4f3b-ad58-f3c3d9815377");
               System.out.println("Encrypted message is:");
               byte[] enc = kms.encrypt("hello");
               System.out.println(new String(enc));
               System.out.println("Decrypted message is:");
               System.out.println(kms.decrypt(enc));

               KMSExample kms1 = new KMSExample("d59783fb-9b94-4322-9faf-7df12e51c6c0");
               System.out.println("Encrypted message is1:");
               byte[] enc1 = kms1.encrypt("hello");
               System.out.println(new String(enc1));
               System.out.println("Decrypted message is1:");
               System.out.println(kms.decrypt(enc1));
           }
       }).start();



    }
}
