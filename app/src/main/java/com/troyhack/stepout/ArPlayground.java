package com.troyhack.stepout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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

public class ArPlayground extends AppCompatActivity {

    private ArFragment arFragment;

    Model fox = new Model("ArcticFox_Posed.sfb", "Arctic Fox", "anonymous");
    Model runningMan = new Model("model.sfb","Dash", "Sugamo");
    Model truck = new Model("truck.sfb","Truck", "Sugamo");
    Model sisters = new Model("sisters.sfb","Sisters", "Sugamo");
    Model peanut = new Model("peanut.sfb","Peanuts-kun", "Sugamo");

    //array of models
    private Model[] models = {fox, runningMan, truck, sisters, peanut};

    private Button prevBtn;
    private Button nextBtn;
    private TextView name;

    private Integer i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


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
    }

}

