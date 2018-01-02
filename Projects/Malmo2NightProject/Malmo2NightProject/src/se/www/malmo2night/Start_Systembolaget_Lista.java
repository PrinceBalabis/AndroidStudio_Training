package se.www.malmo2night;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import gps.DistanceCalculator;
import gps.FetchLocation;

public class Start_Systembolaget_Lista extends ListFragment {
    private double[] systembolagetCoordinateListLat;
    private double[] systembolagetCoordinateListLng;
    private double[] currentCoordinates;
    private int distance;

    public class MyListAdapter extends ArrayAdapter<String> {
        Context myContext;

        public MyListAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
            myContext = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.listfragment_row_systembolag, parent, false);
            TextView titel_avstand = (TextView) row.findViewById(R.id.title_distance_systembolag);
            TextView titel = (TextView) row.findViewById(R.id.titel_systembolag);
            titel.setText(getSystembolagNamn()[position]);

            TextView adress = (TextView) row.findViewById(R.id.adress_systembolag);
            adress.setText(getSystembolagAdress()[position]);

            TextView telefon = (TextView) row.findViewById(R.id.telefon_systembolag);
            telefon.setText(getSystembolagTelefon()[position]);

            distance = (int) DistanceCalculator.distFrom(currentCoordinates[0], currentCoordinates[1], systembolagetCoordinateListLat[position], systembolagetCoordinateListLng[position]);
            TextView avstand = (TextView) row.findViewById(R.id.distance_systembolag);

            if (distance == -1) {
                titel_avstand.setText("");
                avstand.setText("");
            } else if (distance <= 1000)
                avstand.setText((" " + distance) + "m");
            else
                avstand.setText((" " + distance / 1000) + "km");

            return row;
        }

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        /*
		 * ListAdapter myListAdapter = new ArrayAdapter<String>( getActivity(),
		 * android.R.layout.simple_list_item_1, month);
		 * setListAdapter(myListAdapter);
		 */

        systembolagetCoordinateListLat = new double[MainActivity.getDataList().getSystembolagetLatitud().length];
        systembolagetCoordinateListLng = new double[MainActivity.getDataList().getSystembolagetLongitud().length];

        for (int i = 0; i < MainActivity.getDataList().getSystembolagetLatitud().length; i++) {
            systembolagetCoordinateListLat[i] = Double.parseDouble(MainActivity.getDataList().getSystembolagetLatitud()[i]);
            systembolagetCoordinateListLng[i] = Double.parseDouble(MainActivity.getDataList().getSystembolagetLongitud()[i]);
        }

        currentCoordinates = getCurrentCoordinates();


        MyListAdapter myListAdapter = new MyListAdapter(getActivity(), R.layout.listfragment_row_systembolag, getSystembolagNamn());
        setListAdapter(myListAdapter);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.start_systembolag_lista, container, false);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.start_systembolaget_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_systembolaget_locations:
                System.out.println("egjhifdgigsdfg");
                FragmentManager fm1 = getFragmentManager();
                Start_Systembolag_Popup_Locations popup3 = new Start_Systembolag_Popup_Locations();
                popup3.show(fm1, "popupdialog");
                return true;
            default:
                // Not one of ours. Perform default menu processing
                return super.onOptionsItemSelected(item);
        }

    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        FragmentManager fm = getFragmentManager();
        Start_Systembolaget_Popup popup = new Start_Systembolaget_Popup();
        Bundle bundle = new Bundle(4);
        bundle.putString("Namn", MainActivity.getDataList().getSystembolagetNamnLista()[position]);
        bundle.putString("OppettiderLank", MainActivity.getDataList().getSystembolagetOppettiderLankLista()[position]);
        bundle.putString("Latitud", MainActivity.getDataList().getSystembolagetLatitud()[position]);
        bundle.putString("Longitud", MainActivity.getDataList().getSystembolagetLongitud()[position]);
        popup.setArguments(bundle);
        popup.show(fm, "popupdialog");
    }

    private String[] getSystembolagNamn() {
        String[] n = MainActivity.getDataList().getSystembolagetNamnLista();
        return n;
    }

    private String[] getSystembolagAdress() {
        String[] n = MainActivity.getDataList().getSystembolagetAddressLista();
        return n;
    }

    private String[] getSystembolagTelefon() {
        String[] n = MainActivity.getDataList().getSystembolageTelefonLista();
        return n;
    }

    private double[] getCurrentCoordinates() {
        FetchLocation fetchgps = new FetchLocation(getActivity());
        double[] coordinates = new double[2];
        coordinates[0] = fetchgps.getLatitude();
        coordinates[1] = fetchgps.getLongitude();
        return coordinates;
    }
}
