package acheiaqui.com.acheiaqui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

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

        ImageView imageShop = (ImageView) findViewById(R.id.profile_image_shop);
        TextView nameShopText = (TextView) findViewById(R.id.profile_name_shop);
        TextView infoShopText = (TextView) findViewById(R.id.profile_info_shop);
        TextView referencePointText = (TextView) findViewById(R.id.profile_reference_point);
        TextView addressShopText = findViewById(R.id.profile_address_shop);

        Toast.makeText(this, intent.getStringExtra("name"), Toast.LENGTH_SHORT).show();
        String name = intent.getStringExtra("name");
        String info = intent.getStringExtra("info");
        String id = intent.getStringExtra("id");
        nameShopText.setText(name);
        infoShopText.setText(info);
        referencePointText.setText(id);

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference().child("pictures").child(id);

        Glide.with(this /* context */)
                .load(storageReference)
                .into(imageShop);


       /* auth = FirebaseAuth.getInstance();

        final DatabaseReference referenceDatabase = FirebaseDatabase.getInstance().getReference("acheiaqui");
        DatabaseReference shopReference = referenceDatabase.child("shop").child("ID");
        final DatabaseReference nameShopFirebase = referenceDatabase.child("name");
        DatabaseReference infoShopFirebase = shopReference.child("info");
        DatabaseReference pointReferenceShopFirebase = shopReference.child("reference");
        DatabaseReference addressShopFirebase = shopReference.child("place");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameShopText.setText(String.valueOf(dataSnapshot.getValue(Shop.class)));
                infoShopText.setText(String.valueOf(dataSnapshot.getValue(String.class)));
                referencePointText.setText(String.valueOf(dataSnapshot.getValue(String.class)));
                addressShopText.setText(String.valueOf(dataSnapshot.getValue(String.class)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };*/
    }
}
