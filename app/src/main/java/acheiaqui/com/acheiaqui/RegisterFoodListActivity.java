package acheiaqui.com.acheiaqui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class RegisterFoodListActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        final ListView foodItemList = (ListView) findViewById(R.id.foodItem_listView);

        foodItemList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                Toast.makeText(parent.getContext(), "Comida selecionada!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
