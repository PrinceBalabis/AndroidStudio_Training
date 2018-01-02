package se.www.malmo2night;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import gps.CompareCoordinates;
import gps.FetchLocation;

public class Start_Utestallen_Popup_Locations extends DialogFragment {
    private EditText message;

    @SuppressLint("WrongViewCast")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_utestallen_popup_locations, container);
        message = (EditText) view.findViewById(R.id.welcome_text);
        getDialog().setTitle("Hitta närmaste pub");

        Button findButton = (Button) view.findViewById(R.id.utestalle_popupFind);
        findButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //				String uri = String.format(Locale.ENGLISH, "geo:%f,%f", fetchgps.getLatitude(), fetchgps.getLongitude());
                //				String uri = String.format(Locale.ENGLISH, "geo:%f,%f",-44.096002,-71.279297 (" + name + "));
                //				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=37.423156,-122.084917 (" + "hello" + ")"));

                launchGoogleMaps();
                dismiss();
            }
        });

        Button abortButton = (Button) view.findViewById(R.id.utestalle_popupDismiss);
        abortButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }


    private void launchGoogleMaps() {
        double[] utestalleCoordinateListLat = new double[MainActivity.getDataList().getUtestallenLatitud().length];
        double[] utestalleCoordinateListLng = new double[MainActivity.getDataList().getUtestallenLongitud().length];

        for (int i = 0; i < MainActivity.getDataList().getUtestallenLatitud().length; i++) {
            utestalleCoordinateListLat[i] = Double.parseDouble(MainActivity.getDataList().getUtestallenLatitud()[i]);
            utestalleCoordinateListLng[i] = Double.parseDouble(MainActivity.getDataList().getUtestallenLongitud()[i]);
        }

        CompareCoordinates closestUtestalle = new CompareCoordinates(utestalleCoordinateListLat, utestalleCoordinateListLng);
        double[] currentCoordinates = getCurrentCoordinates();
        double[] closestUtestalleCoordinates = closestUtestalle.findClosest(currentCoordinates[0], currentCoordinates[1]);
        String UtestalleNamn = MainActivity.getDataList().getUtestallenNamnLista()[closestUtestalle.getClosestID()];
        String uri = "geo:0,0?q=" + closestUtestalleCoordinates[0] + "," + closestUtestalleCoordinates[1] + " (" + MainActivity.getDataList().getUtestallenTypLista()[closestUtestalle.getClosestID()] + ": " + UtestalleNamn + ")";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    private double[] getCurrentCoordinates() {
        FetchLocation fetchgps = new FetchLocation(getActivity());
        double[] coordinates = new double[2];
        coordinates[0] = fetchgps.getLatitude();
        coordinates[1] = fetchgps.getLongitude();
        return coordinates;
    }
}
