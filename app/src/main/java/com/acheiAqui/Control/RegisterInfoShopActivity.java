package com.acheiAqui.Control;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

import acheiaqui.com.acheiAqui.R;

public class RegisterInfoShopActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private ImageView ivPicture;
    private Button btnAcessCamera;
    Bitmap bitmap;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    private static final int CAMERA_REQUEST_CODE = 1;

    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shop);

        btnAcessCamera = (Button) findViewById(R.id.btn_upload_image_shop);
        ivPicture = (ImageView) findViewById(R.id.image_shop);

        //Pega a referencia do Storage do firebase
        storageReference = FirebaseStorage.getInstance().getReference();

        //Acessa a camera do smartphone ao clicar no botão
        btnAcessCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){

            //Pega a informação (imagem) que vem da camera e faz um tratamento dela
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) data.getExtras().get("data");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] dataBAOS = baos.toByteArray();

            //carrega a imagem na ImageView da activity
            ivPicture.setImageBitmap(bitmap);

          /*  //nome da imagem que vai ser inserida no banco, uso de data para evitar sobrescrita
            StorageReference imagesRef = storageReference.child("ShopsPicture").child("picture" + new Date().getTime());

            //upload a imagem
            UploadTask uploadTask = imagesRef.putBytes(dataBAOS);

            //se ocorreu com sucesso exibe uma mensagem
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(RegisterInfoShopActivity.this,
                            "Carregamento termminou ...", Toast.LENGTH_LONG).show();
                }
            });*/
        }

    }

    public void nextPageButton(View view)
    {

        EditText edName = (EditText) findViewById(R.id.name_shop);
        EditText edInfo = (EditText) findViewById(R.id.shop_info);
        EditText edReferencePoint = (EditText) findViewById(R.id.reference_point);

        String name = edName.getText().toString();
        String info = edInfo.getText().toString();
        String referencePoint = edReferencePoint.getText().toString();


        if(name.length()!=0 && info.length()!=0 && referencePoint.length()!=0){

            Intent intent = new Intent(RegisterInfoShopActivity.this, RegisterLocationActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("info", info);
            intent.putExtra("referencedPoint", referencePoint);
            intent.putExtra("picture", baos.toByteArray());

            startActivity(intent);
        } else {
            Toast.makeText(this, "É preciso preencher todos os campos", Toast.LENGTH_SHORT).show();
        }

    }
}
