package acheiaqui.com.acheiaqui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity
{

    private static final Shop[] nameShop = {
            new Shop("Catatau", "O mestre dos salgados"),
            new Shop("Hamburgao do Roberto Carlos", "O Rei dos hamburguers"),
            new Shop("Barraquinha da Tia", "A tia que sempre tem aquele guaraná do Amazonas")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        ListView searchShopList = (ListView) findViewById(R.id.search_shop_listView);
        //porque eu preciso passar de um contexto para criar uma lista?
        searchShopList.setAdapter(new ShopArrayListAdapter(this, R.layout.shop_name_listitem, R.id.shop_name, nameShop));

        searchShopList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                Toast.makeText(parent.getContext(), "Estabelecimento selecionado: " + nameShop[position].getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
