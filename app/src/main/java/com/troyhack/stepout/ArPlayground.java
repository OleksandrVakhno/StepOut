package com.troyhack.stepout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import android.content.Context;


class Model {
    public String uri;
    public String modelName;
    public String author;

    public Model(String uri, String name, String author){
        this.uri = uri;
        this.modelName = name;
        this.author = author;
    }
}

public class ArPlayground extends AppCompatActivity  {

    private ArFragment arFragment;

    Model fox = new Model("ArcticFox_Posed.sfb", "Arctic Fox", "anonymous");
    Model runningMan = new Model("model.sfb","Dash", "Sugamo");
    Model truck = new Model("truck.sfb","Truck", "Sugamo");
    Model sisters = new Model("sisters.sfb","Sisters", "Sugamo");
    Model peanut = new Model("peanut.sfb","Peanuts-kun", "Sugamo");

    //array of models
    private Model[] models = {fox, truck, sisters, peanut};

    private Button prevBtn;
    private Button nextBtn;
    private TextView name;
    private Button backBtn;
    private ImageButton captureBtn;
    private ImageView imageView;


    private Integer i = 0;

    private Uri file;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;

    private String currentPhotoPath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        imageView = (ImageView) findViewById(R.id.imageview);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_playground);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

        //when user taps on screen
        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {

            Anchor anchor = hitResult.createAnchor();

            ModelRenderable.builder()
                    .setSource(this, Uri.parse(models[i].uri))
                    .build()
                    .thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable))
                    .exceptionally(throwable -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(throwable.getMessage())
                                .show();
                        return null;
                    });


            Toast.makeText(this, models[i].modelName + " by " + models[i].author, Toast.LENGTH_SHORT).show();

        }));

        buttonListener();

    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable){
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }

    private void buttonListener(){
        prevBtn = (Button) findViewById(R.id.prevModel);
        nextBtn = (Button) findViewById(R.id.nextModel);
        name = (TextView) findViewById(R.id.about);
        backBtn = (Button) findViewById(R.id.back);
        //captureBtn = findViewById(R.id.capture);


        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i--;

                //go to last model in array
                if(i<0){
                    i = models.length-1;
                }

                name.setText(models[i].modelName);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;

                //if at the end, go to first model in array
                if(i>=models.length){
                    i = 0;
                }

                name.setText(models[i].modelName);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(ArPlayground.this, MainActivity.class);
                startActivity(home);
            }
        });
/*
        captureBtn.setOnClickListener (new View.OnClickListener() {

            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent

                //file = Uri.fromFile(getOutputMediaFile());

                try {
                    file = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileprovider", createImageFile());
                }
                catch(IOException ex){
                    //Error
                }


                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, file);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                startActivityForResult(takePictureIntent, 100);


            }


        });*/
    }


/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(file);
            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",  // suffix
                storageDir     // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
*/



}

