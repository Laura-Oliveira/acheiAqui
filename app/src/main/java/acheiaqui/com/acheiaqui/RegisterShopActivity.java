package acheiaqui.com.acheiaqui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegisterShopActivity extends AppCompatActivity
{
   // Button btnRegister = (Button) findViewById(R.id.btn_register);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shop);
    }

  //  Button btnRegister = (Button) findViewById(R.id.btn_register);

    public void onClickButton(View view)
    {
        Intent intent = new Intent(RegisterShopActivity.this,ConfirmShopActivity.class);
        startActivity(intent);
    }
}
