
package acheiaqui.com.acheiaqui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class RegisterFoodListActivity extends AppCompatActivity
{
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private EditText foods;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        foods = (EditText) findViewById(R.id.registerfoods_shop);

        //Pega a referencia do Storage do firebase
        storageReference = FirebaseStorage.getInstance().getReference();

    }

    public void registerShop(View view){
        String nameShop = getIntent().getStringExtra("name");
        String infoShop = getIntent().getStringExtra("info");
        String referencePointShop = getIntent().getStringExtra("referencedPoint");

        Bundle doubleDatas = getIntent().getExtras();
        double latitudeShop =  doubleDatas.getDouble("latitude");
        double longitudeShop =  doubleDatas.getDouble("longitude");

        if(foods.toString().length()!=0) {

            DatabaseReference dbReference = database.getReference("shop");

            Shop newShop = new Shop();
            newShop.setName(nameShop);
            newShop.setInfo(infoShop);
            newShop.setReference(referencePointShop);
            newShop.setFoods(foods.getText().toString());
            newShop.setLatitude(latitudeShop);
            newShop.setLongitude(longitudeShop);
            newShop.setId(dbReference.push().getKey());

            //insere lojinha no banco de dados
            dbReference.child(newShop.getId()).setValue(newShop);

            //insere a imagem no storage, cujo nome é o id da lojinha
            StorageReference imagesRef = storageReference.child("pictures").child(newShop.getId());

            //upload a imagem
            UploadTask uploadTask = imagesRef.putBytes(getIntent().getByteArrayExtra("picture"));

            Intent intent = new Intent(RegisterFoodListActivity.this, HomeActivity.class);
            intent.putExtra("success", "Ponto de venda cadastrado com sucesso!");
            startActivity(intent);

        }else {
            Toast.makeText(this, "É preciso informar as comidas oferecidas.", Toast.LENGTH_SHORT).show();
        }
    }
}
