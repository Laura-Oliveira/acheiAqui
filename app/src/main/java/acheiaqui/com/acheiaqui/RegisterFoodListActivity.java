
package acheiaqui.com.acheiaqui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class RegisterFoodListActivity extends AppCompatActivity
{
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private EditText foods;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        foods = (EditText) findViewById(R.id.registerfoods_shop);

    }

    public void registerShop(View view){
        String nameShop = getIntent().getStringExtra("name");
        String infoShop = getIntent().getStringExtra("info");
        String referencePointShop = getIntent().getStringExtra("referencedPoint");

        Bundle doubleDatas = getIntent().getExtras();
        double latitudeShop =  doubleDatas.getDouble("latitude");
        double longitudeShop =  doubleDatas.getDouble("longitude");

        if(foods.toString().length()!=0) {

            Shop newShop = new Shop();
            newShop.setName(nameShop);
            newShop.setInfo(infoShop);
            newShop.setReference(referencePointShop);
            newShop.setFoods(foods.getText().toString());
            newShop.setLatitude(latitudeShop);
            newShop.setLongitude(longitudeShop);

            //mDatabase.child("shop").child(String.valueOf(new Date().getTime())).setValue(newShop);
            DatabaseReference dbReference = database.getReference("shop");
            dbReference.push().setValue(newShop);

            Intent intent = new Intent(RegisterFoodListActivity.this, HomeActivity.class);
            startActivity(intent);

        }else {
            Toast.makeText(this, "É preciso informar as comidas oferecidas.", Toast.LENGTH_SHORT).show();
        }
    }
}
