package acheiaqui.com.acheiaqui;

import android.content.pm.PackageManager;
import android.location.Location;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;
  //  private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
/*
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();;
*/

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng recife = new LatLng(-8.0538900, -34.8811100);
        mMap.addMarker(new MarkerOptions().position(recife).title("Recife"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(recife));

    }
/*
   @Override
   protected void onStop()
   {
        super.onStop();
        pararConexaoComGoogleApi();
   }

   public void pararConexaoComGoogleApi()
   {
       if(googleApiClient.isConnected())
       {
           googleApiClient.disconnect();
       }
   }


   public void onConnected(Bundle bundle)
   {
       LocationData lastLocation = new LocationData();
       final double latitude = lastLocation.getLatitude();
       final double longitude = lastLocation.getLongitude();
   }

   public void onConnectionSuspended(int suspend)
   {

   }

   public void onConnectionFailed(ConnectionResult connectionResult)
   {
       pararConexaoComGoogleApi();
   }
   */
}
