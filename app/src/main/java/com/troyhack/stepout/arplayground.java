package com.troyhack.stepout;


import android.content.Context;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Build;

import android.app.Activity;
import android.app.ActivityManager;
import android.widget.Toast;

import android.opengl.GLES31;




public class arplayground extends AppCompatActivity {

    final private static double MIN_OPENGL_VERSION = 3.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arplayground);


        boolean state = checkIsSupportedDeviceOrFinish(this);

        if(state){

        }
    }



    /*
    This method checks whether your device can support Sceneform SDK or not.
    The SDK requires Android API level 27 or newer and OpenGL ES version 3.0 or newer.
    If a device does not support these two, the Scene would not be rendered and your application will show a blank screen.
     */
    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e("TAG", "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }

        String openGlVersionString = ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e("TAG", "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }

        return true;
    }
}
