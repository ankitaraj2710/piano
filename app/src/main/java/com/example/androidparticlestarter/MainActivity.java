package com.example.androidparticlestarter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudSDK;
import io.particle.android.sdk.cloud.ParticleDevice;
import io.particle.android.sdk.cloud.ParticleEvent;
import io.particle.android.sdk.cloud.ParticleEventHandler;
import io.particle.android.sdk.cloud.exceptions.ParticleCloudException;
import io.particle.android.sdk.utils.Async;

public class MainActivity extends AppCompatActivity {
    // MARK: Debug info
    private final String TAG="";

    // MARK: Particle Account Info
    private final String PARTICLE_USERNAME = "ankitrajkaur@gmail.com";
    private final String PARTICLE_PASSWORD = "ankita2710";

    // MARK: Particle device-specific info
    private final String DEVICE_ID = "280042001247363333343437";

    // MARK: Particle Publish / Subscribe variables
    private long subscriptionId;

    // MARK: Particle device
    private ParticleDevice mDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize your connection to the Particle API
        ParticleCloudSDK.init(this.getApplicationContext());

        // 2. Setup your device variable
        getDeviceFromCloud();

    }
    public void PressA(View view){
        playPiano("a");
    }
    public void PressB(View view){
        playPiano("b");
    }

 public void playPiano(String str){
     Async.executeAsync(ParticleCloudSDK.getCloud(), new Async.ApiWork<ParticleCloud, Object>() {

         @Override
         public Object callApi(@NonNull ParticleCloud particleCloud) throws ParticleCloudException, IOException {
             particleCloud.logIn(PARTICLE_USERNAME, PARTICLE_PASSWORD);
             mDevice = particleCloud.getDevice(DEVICE_ID);
             List<String> parameters = new ArrayList<>();
             parameters.add(str);
             try {
                 mDevice.callFunction("playSound",parameters);
             } catch (ParticleDevice.FunctionDoesNotExistException e) {
                 e.printStackTrace();
             }
             return -1;
         }

         @Override
         public void onSuccess(Object o) {
             Log.d(TAG, "Successfully got device from Cloud");
         }

         @Override
         public void onFailure(ParticleCloudException exception) {
             Log.d(TAG, exception.getBestMessage());
         }
     });
 }

    /**
     * Custom function to connect to the Particle Cloud and get the device
     */
    public void getDeviceFromCloud() {
        // This function runs in the background
        // It tries to connect to the Particle Cloud and get your device

    }


}
