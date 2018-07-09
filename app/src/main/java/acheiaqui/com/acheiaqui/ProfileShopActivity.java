package acheiaqui.com.acheiaqui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileShopActivity extends AppCompatActivity {

    private TextView nameShopText;
    private TextView infoShopText;
    private TextView referencePointText;
    private TextView addressShopText;
    private FirebaseAuth auth;
    private Shop shop = new Shop();
    private DatabaseReference firebaseDatabase;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_shop);

        nameShopText = findViewById(R.id.profile_name_shop);
        infoShopText = findViewById(R.id.profile_info_shop);
        referencePointText = findViewById(R.id.profile_reference_point);
        addressShopText = findViewById(R.id.profile_address_shop);

        auth = FirebaseAuth.getInstance();

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
        };
    }
}
