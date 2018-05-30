package acheiaqui.com.acheiaqui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ProfileShopActivity extends AppCompatActivity
{

    private static final String[] nameShop = {"Catatau", "Baraca da Tia", "Hamgurgao do Carlos"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_shop);


        ListView profileShopList = (ListView) findViewById(R.id.profile_shop_listView);
        //porque eu preciso passar de um contexto para criar uma lista?
       // ArrayAdapter<String> nameShopAdapter = ;
        profileShopList.setAdapter(new ArrayAdapter<String>(this, R.layout.shop_name_listitem, (List<String>) profileShopList));


    }
}
