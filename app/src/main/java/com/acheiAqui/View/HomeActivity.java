package com.acheiAqui.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.acheiAqui.Model.Shop;
import com.acheiAqui.Control.RegisterInfoShopActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import acheiaqui.com.acheiAqui.R;

public class HomeActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private Marker currentLocationMaker;
    private LatLng currentLocationLatLong;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        Intent intent = getIntent();
        intent.getExtras();
        if(intent.hasExtra("success")){
            Toast.makeText(this, intent.getStringExtra("success"), Toast.LENGTH_SHORT).show();
        }
        mapFragment.getMapAsync(this);
        startGettingLocations();
        getMarkers();
    }

    //funcao que carrega o mapa quando o aplicativo e aberto
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        /*
        MarkerOptions markerShop = new MarkerOptions();
        markerShop.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_shop));
        MarkerOptions markerAtualLocation = new MarkerOptions();
        markerAtualLocation.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_shop_round));*/

    }

    //funcao que pega a localizacao atual do cliente, caso este permita que sua localizacao seja utilizada,
    // plota um marcador no mapa e coloca foco do mapa nessa atual localizacao
    public void onLocationChanged(Location location) {

        //quando a localizacao atual do usuario mudar, ele retira o marcador anterior, caso tenha existido, e cria outro
        if (currentLocationMaker != null) {
            currentLocationMaker.remove();
        }
        //adiciona o marcador com a localizacao atual do usuario
        currentLocationLatLong = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocationLatLong);
        markerOptions.title("Localização atual");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        currentLocationMaker = mMap.addMarker(markerOptions);
        currentLocationMaker.setDraggable(true); //permite que o marcador possa ser trocado de lugar

        //quando a localizacao atual do usuario muda, o foco do mapa muda para o ponto atual do usuario e aumenta
        //o zoom do mapa, mostrando mais detalhes do mesmo
        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(17).target(currentLocationLatLong).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //Toast.makeText(this, "Localização atualizada", Toast.LENGTH_SHORT).show();
        getMarkers();
    }


    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    //caso a versao do celular seja maior que a marshmallow, o aplicativo pergunta se pode usar a localizacao
    //do usuario, caso contrario, ele ja tem essa informacao a partir das confifuracoes do arquivo do manifest
    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    //funcao que verifica se o GPS do usuario esta ativado, e caso nao esteja, perguna ao mesmo se deseja ativa-lo
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS desativado!");
        alertDialog.setMessage("Ativar GPS?");
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    //funcao que pega a localizaao atual do usuario, via GPS ou via Internet
    private void startGettingLocations() {

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean isGPS = false;
        if (lm != null) {
            isGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        boolean isNetwork = false;
        if (lm != null) {
            isNetwork = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
        boolean canGetLocation = true;
        int ALL_PERMISSIONS_RESULT = 101;
        long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;// Distance in meters
        long MIN_TIME_BW_UPDATES = 1000 * 10;// Time in milliseconds

        ArrayList<String> permissions = new ArrayList<>();
        ArrayList<String> permissionsToRequest;

        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);

        //Verifica se o GPS ou Internet do usuario esta ligada, caso nao esteja, pergunta se o mesmo deseja ativa-la
        if (!isGPS && !isNetwork) {
            showSettingsAlert();
        } else {

            //verifica permissoes de uso do GPS para versoes anteriores ao marshmallow
            //para versoes anteriores ao marshmallow, a localizacao ja pode ser adquirida a partir da
            //configucao do arquivo do manifest
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    canGetLocation = false;
                }
            }
        }


        //verfica se a permissao para utilizar a localizacao foi concedida
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show();
            return;
        }

        //atualiza a localizacao do usuario, a partir do GPS ou Intenet
        if (canGetLocation) {
            if (isGPS) lm.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            else if (isNetwork) {
                lm.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            }
        } else {
            Toast.makeText(this, "Não é possível obter a localização", Toast.LENGTH_SHORT).show();
        }
    }

    private void getMarkers(){
        mDatabase.child("shop").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        if (dataSnapshot.getValue() != null) {
                            getAllLocations((Map<String,Object>) dataSnapshot.getValue());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    private void getAllLocations(Map<String, Object> locations) {


        for (Map.Entry<String, Object> entry : locations.entrySet()) {
            Shop shop = new Shop();
            Map singleLocation = (Map) entry.getValue();
            shop.setName((String) singleLocation.get("name"));
            shop.setInfo((String) singleLocation.get("info"));
            shop.setId((String) singleLocation.get("id"));
            shop.setReference((String) singleLocation.get("reference"));
            shop.setFood((String) singleLocation.get("food"));
            shop.setLatitude((Double) singleLocation.get("latitude"));
            shop.setLongitude((Double) singleLocation.get("longitude"));
            LatLng latLng = new LatLng(shop.getLatitude(), shop.getLongitude());
            addGreenMarker(shop, latLng).setTag(shop);
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Shop actualShop = (Shop) marker.getTag();
                    Intent intent = new Intent(HomeActivity.this, ProfileShopActivity.class);
                    intent.putExtra("name", actualShop.getName());
                    intent.putExtra("info", actualShop.getInfo());
                    intent.putExtra("id", actualShop.getId());
                    intent.putExtra("reference", actualShop.getReference());
                    intent.putExtra("food", actualShop.getFood());
                    startActivity(intent);
                }
            });


        }


    }

    private Marker addGreenMarker(final Shop shop, LatLng latLng) {


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(shop.getName());
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

        Marker shopMarker = mMap.addMarker(markerOptions);

        return shopMarker;
    }


    //funcoes padroes da classe LocationListerner. Nao foi necessario sobrescreve-las para manipular o mapa
    //e a localizacao atual do usuario
    @Override
    public void onStatusChanged(String provider,int status, Bundle extras){

    }

    @Override
    public void onProviderEnabled(String provider){

    }

    @Override
    public void onProviderDisabled(String provider){

    }

    public void registerNewShop(View ciew){

        Intent intent = new Intent(this, RegisterInfoShopActivity.class);
        startActivity(intent);
    }


}

