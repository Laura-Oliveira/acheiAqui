package com.acheiAqui.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.acheiAqui.Model.Shop;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import acheiaqui.com.acheiAqui.R;

public class ProfileShopActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Shop shop = new Shop();
    private DatabaseReference firebaseDatabase;
    private ValueEventListener valueEventListener;
    private Bitmap my_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_shop);


        Intent intent = getIntent();

        TextView nameShopText = (TextView) findViewById(R.id.profile_name_shop);
        TextView infoShopText = (TextView) findViewById(R.id.profile_info_shop);
        TextView referencePointText = (TextView) findViewById(R.id.profile_reference_point);
        TextView food = (TextView) findViewById(R.id.profile_foods_shop);

        String name = intent.getStringExtra("name");
        String info = intent.getStringExtra("info");
        String id = intent.getStringExtra("id"); //para pegar o id e exibir a imagem
        String referencePoint = intent.getStringExtra("reference");
        String foods = intent.getStringExtra("food");

        nameShopText.setText(name);
        infoShopText.setText(info);
        referencePointText.setText(referencePoint);
        food.setText(foods);

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://acheiaqui-3a20f.appspot.com/").child("pictures").child(id);

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                ImageView imageShop = (ImageView) findViewById(R.id.profile_image_shop);
                Picasso.get().load(uri).into(imageShop);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}
