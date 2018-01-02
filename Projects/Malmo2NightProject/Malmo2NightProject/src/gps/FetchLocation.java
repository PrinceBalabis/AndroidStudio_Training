package gps;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


public class FetchLocation implements LocationListener {
    private LocationManager locationManager;
    private String provider;
    static private double lat;
    static private double lng;

    public FetchLocation(Context context) {
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            System.out.println("Kan ej finna position");
        }
    }


    //	/* Request updates at startup */
    //	@Override
    //	protected void onResume() {
    //		super.onResume();
    //		locationManager.requestLocationUpdates(provider, 400, 1, this);
    //	}
    //
    //	/* Remove the locationlistener updates when Activity is paused */
    //	@Override
    //	protected void onPause() {
    //		super.onPause();
    //		locationManager.removeUpdates(this);
    //	}

    public void onLocationChanged(Location location) {
        lat = (double) (location.getLatitude());
        lng = (double) (location.getLongitude());
        System.out.println("************Latitud=" + lat);
        System.out.println("************Latitud=" + lng);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        System.out.println("Enabled new provider " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {

        System.out.println("Disabled provider " + provider);
    }

    public static double getLongitude() {
        return lng;
    }

    public static double getLatitude() {
        return lat;
    }

    private void calculateDistanceToUtestalle() {
        /*
		 * Kontrollera om det finns något sätt att jämföra i
		 * databasen istället för i Android. 
		 * 
		 * 
		 */
    }
}
