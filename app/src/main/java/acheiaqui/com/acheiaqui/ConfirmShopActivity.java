package acheiaqui.com.acheiaqui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ConfirmShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_shop);

    }

    public void onClickButton(View view)
    {
        Intent intent = new Intent(this, HomeActivity2.class);
        startActivity(intent);
    }
}