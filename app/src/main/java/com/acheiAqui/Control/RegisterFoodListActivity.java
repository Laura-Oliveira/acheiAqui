package com.acheiAqui.Control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.acheiAqui.Model.Shop;
import com.acheiAqui.View.HomeActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import acheiaqui.com.acheiAqui.R;

public class RegisterFoodListActivity extends AppCompatActivity
{
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private TextView food,food1, food2,food3,food4,food5,food6;
    private CheckBox checkFood, checkFood1, checkFood2, checkFood3, checkFood4, checkFood5, checkFood6;
    private StorageReference storageReference;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        food =  (TextView) findViewById(R.id.check_comida);
        food1 = (TextView) findViewById(R.id.check_comida1);
        food2 = (TextView) findViewById(R.id.check_comida2);
        food3 = (TextView) findViewById(R.id.check_comida3);
        food4 = (TextView) findViewById(R.id.check_comida4);
        food5 = (TextView) findViewById(R.id.check_comida5);
        food6 = (TextView) findViewById(R.id.check_comida6);
        checkFood = (CheckBox) findViewById(R.id.check_box_comida);
        checkFood1 = (CheckBox) findViewById(R.id.check_box_comida1);
        checkFood2 = (CheckBox) findViewById(R.id.check_box_comida2);
        checkFood3 = (CheckBox) findViewById(R.id.check_box_comida3);
        checkFood4 = (CheckBox) findViewById(R.id.check_box_comida4);
        checkFood5 = (CheckBox) findViewById(R.id.check_box_comida5);
        checkFood6 = (CheckBox) findViewById(R.id.check_box_comida6);

        buttonRegister = findViewById(R.id.btn_register_shop);


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


        String shopFoods = "";
        if(checkFood.isChecked()){
            shopFoods += (food.getText().toString()+"\n");
        }
        if(checkFood1.isChecked()){
            shopFoods += (food1.getText().toString()+"\n");
        }
        if(checkFood2.isChecked()){
            shopFoods += (food2.getText().toString()+"\n");
        }
        if(checkFood3.isChecked()){
            shopFoods += (food3.getText().toString()+"\n");
        }
        if(checkFood4.isChecked()){
            shopFoods += (food4.getText().toString()+"\n");
        }
        if(checkFood5.isChecked()){
            shopFoods += (food5.getText().toString()+"\n");
        }
        if(checkFood6.isChecked()){
            shopFoods += (food6.getText().toString());
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