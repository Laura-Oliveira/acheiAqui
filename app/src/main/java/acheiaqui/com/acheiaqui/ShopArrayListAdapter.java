package acheiaqui.com.acheiaqui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ShopArrayListAdapter extends ArrayAdapter <Shop>
{
    private Shop[] shops;

    public ShopArrayListAdapter(Context context, int shop_name_listitem, int resource, Shop[] shops)
    {
        super(context, resource, shops);
        this.shops = shops;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View listitem = inflater.inflate(R.layout.shop_name_listitem, null, true);

        TextView shopName = (TextView) listitem.findViewById(R.id.shop_name);
        TextView shopInfo = (TextView) listitem.findViewById(R.id.shop_info);

        shopName.setText(shops[position].getName());
        shopInfo.setText(shops[position].getInfo());

        return listitem;
    }
}
