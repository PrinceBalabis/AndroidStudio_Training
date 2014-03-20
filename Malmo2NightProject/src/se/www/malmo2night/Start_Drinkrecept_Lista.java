package se.www.malmo2night;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Random;

public class Start_Drinkrecept_Lista extends ListFragment {
    String[] drinkreceptNamn = new String[0];
    MyListAdapter myListAdapter;
    ArrayList<Integer> drinkReceptId = new ArrayList<Integer>();

    public class MyListAdapter extends ArrayAdapter<String> {
        Context myContext;

        public MyListAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
            myContext = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.listfragment_row_drinkrecept, parent, false);

            TextView titel = (TextView) row.findViewById(R.id.titel_drinkrecept_kategori);
            titel.setText(drinkreceptNamn[position]);

            //			TextView antalRecept = (TextView) row.findViewById(R.id.text_drinkrecept_antalrecept);
            //			antalRecept.setText(MainActivity.getLists().getDrinkreceptKategoriLista()[position]);
            return row;
        }

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initializeKategorier();
        myListAdapter = new MyListAdapter(getActivity(), R.layout.listfragment_row_drinkrecept, drinkreceptNamn);
        setListAdapter(myListAdapter);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.start_drinkrecept_lista, container, false);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.start_drinkrecept_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_drinkrecept_random:
                Random random = new Random();
                int visibleChildCount = (getListView().getLastVisiblePosition() - getListView().getFirstVisiblePosition()) + 1;
                getListView().performItemClick(getView(), random.nextInt(visibleChildCount), getId());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        drinkreceptNamn = new String[drinkReceptId.size()];
        for (int i = 0; i < drinkReceptId.size(); i++) {
            drinkreceptNamn[i] = MainActivity.getDataList().getDrinkreceptNamnLista()[drinkReceptId.get(i)];
        }
        //			myListAdapter = new MyListAdapter(getActivity(),R.layout.listfragment_row_drinkrecept, drinkreceptNamn);
        //			setListAdapter(myListAdapter);
        //			myListAdapter.notifyDataSetChanged();
        Intent i = new Intent(getActivity(), Start_Drinkrecept_ListaSida2.class);
        i.putExtra("DrinkreceptNamn", drinkreceptNamn);
        startActivity(i);

    }

    private void initializeKategorier() {
        if (drinkreceptNamn.length == 0) {
            String[] ofiltreradDrinkreceptKategorier = MainActivity.getDataList().getDrinkreceptKategoriLista();
            ArrayList<String> countArray = new ArrayList<String>();

            boolean finns;
            for (int i = 0; i < ofiltreradDrinkreceptKategorier.length; i++) {
                finns = false;
                for (int j = 0; j < countArray.size(); j++) {
                    if (ofiltreradDrinkreceptKategorier[i].equals(countArray.get(j))) {
                        finns = true;
                    }
                }
                if (finns == false) {
                    countArray.add(ofiltreradDrinkreceptKategorier[i]);
                    drinkReceptId.add(i);
                }
            }
            drinkreceptNamn = new String[countArray.size()];
            for (int i = 0; i < countArray.size(); i++) {
                drinkreceptNamn[i] = countArray.get(i);
            }
        }

    }
}
