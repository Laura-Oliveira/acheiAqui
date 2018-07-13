package acheiaqui.com.acheiaqui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.Date;

public class RegisterFoodListActivity extends AppCompatActivity
{
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private TextView food,food1, food2,food3,food4,food5,food6;
    private CheckBox checkFood;
    private StorageReference storageReference;
    private Button buttonRegister;
    private TextView[] foodsList = {food, food1, food2, food3, food4, food5, food6};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        food = findViewById(R.id.check_comida);
        food1 = findViewById(R.id.check_comida1);
        food2 = findViewById(R.id.check_comida2);
        food3 = findViewById(R.id.check_comida3);
        food4 = findViewById(R.id.check_comida4);
        food5 = findViewById(R.id.check_comida5);
        food6 = findViewById(R.id.check_comida6);
        checkFood = findViewById(R.id.check_box_comida);
        buttonRegister = findViewById(R.id.btn_register);

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

        //boolean checked = view.isPressed();
        //boolean btnNextPressed = buttonRegister.isPressed();
        //boolean checkedTrue = true;

        //vetor de string para armazenar os nomes das comidas
        String foodList [] = {food.getText().toString(), food1.getText().toString(),
                food2.getText().toString(), food3.getText().toString(), food4.getText().toString(),
                food5.getText().toString(), food6.getText().toString()};

        String shopFoods = "";
        //verifica se o valor de cada posição do vetor está vazio ou preenchido, se estiver preenchido
        //adiciona à String
        for(int count = 0; count < foodList.length; count++){
            if(foodList[count] != ""){
                shopFoods += (foodList[count] +" ");
            }
        }

        if(shopFoods!="" || shopFoods!= " ") {

            DatabaseReference dbReference = database.getReference("shop");

            Shop newShop = new Shop();
            newShop.setName(nameShop);
            newShop.setInfo(infoShop);
            newShop.setReference(referencePointShop);
            newShop.setFood(shopFoods);
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