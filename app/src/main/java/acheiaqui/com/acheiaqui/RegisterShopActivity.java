package acheiaqui.com.acheiaqui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterShopActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shop);

    }

    public void onClickButton(View view)
    {

        EditText edName = (EditText) findViewById(R.id.name_shop);
        EditText edPlace = (EditText) findViewById(R.id.shop_address);
        EditText edInfo = (EditText) findViewById(R.id.shop_info);
        EditText edReference = (EditText) findViewById(R.id.reference_point);

        String name = edName.getText().toString();
        String info = edInfo.getText().toString();
        String place = edPlace.getText().toString();
        String reference = edReference.getText().toString();

        Shop newShop = new Shop(name, info, place, reference);

        DatabaseReference dbReference = database.getReference("shop");

        dbReference.push().setValue(newShop);

        Intent intent = new Intent(RegisterShopActivity.this,ConfirmShopActivity.class);
        startActivity(intent);
    }
}
