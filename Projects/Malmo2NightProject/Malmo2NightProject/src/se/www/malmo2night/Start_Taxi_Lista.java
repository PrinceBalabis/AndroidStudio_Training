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

import java.util.Random;

public class Start_Taxi_Lista extends ListFragment {
    public class MyListAdapter extends ArrayAdapter<String> {
        Context myContext;

        public MyListAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
            myContext = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.listfragment_row_taxi, parent, false);

            TextView titel = (TextView) row.findViewById(R.id.titel_taxi);
            titel.setText(getTaxiNamn()[position]);

            TextView telefon = (TextView) row.findViewById(R.id.telefon_taxi);
            telefon.setText("Telefon: " + getTaxiTelefon()[position]);

            TextView gradering = (TextView) row.findViewById(R.id.gradering_taxi);
            gradering.setText("gilla/ogilla funktion kommer snart!");


            return row;
        }

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        MyListAdapter myListAdapter = new MyListAdapter(getActivity(), R.layout.listfragment_row_taxi, getTaxiNamn());
        setListAdapter(myListAdapter);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.start_taxi_lista, container, false);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.start_taxi_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_taxi_random:
                Random random = new Random();
                int visibleChildCount = (getListView().getLastVisiblePosition() - getListView().getFirstVisiblePosition()) + 1;
                getListView().performItemClick(getView(), random.nextInt(visibleChildCount), getId());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void onListItemClick(ListView l, View v, int position, long id) {
        FragmentManager fm = getFragmentManager();
        Start_Taxi_Popup popup = new Start_Taxi_Popup();
        Bundle bundle = new Bundle(3);
        bundle.putString("Namn", MainActivity.getDataList().getTaxiNamnLista()[position]);
        bundle.putString("Hemsida", MainActivity.getDataList().getTaxiHemsidaLista()[position]);
        bundle.putString("Telefonnummer", MainActivity.getDataList().getTaxiTelefonLista()[position]);
        popup.setArguments(bundle);
        popup.show(fm, "popupdialog");
    }

    private String[] getTaxiNamn() {
        String[] n = MainActivity.getDataList().getTaxiNamnLista();
        return n;
    }

    private String[] getTaxiTelefon() {
        String[] n = MainActivity.getDataList().getTaxiTelefonLista();
        return n;
    }

}
