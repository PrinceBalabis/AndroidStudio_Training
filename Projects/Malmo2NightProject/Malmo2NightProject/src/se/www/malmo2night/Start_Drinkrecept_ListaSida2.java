package se.www.malmo2night;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Start_Drinkrecept_ListaSida2 extends ListActivity {
    String[] drinkreceptNamn = new String[0];
    MyListAdapter myListAdapter;
    ArrayList<Integer> drinkReceptId = new ArrayList<Integer>();
    int page = 1;

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
        drinkreceptNamn = getIntent().getStringArrayExtra("DrinkreceptNamn");
        myListAdapter = new MyListAdapter(this, R.layout.listfragment_row_drinkrecept, drinkreceptNamn);
        setListAdapter(myListAdapter);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.start_drinkrecept_lista, container, false);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        String ValdDrinkreceptNamn = getListView().getItemAtPosition(position).toString();
        Intent intent = new Intent(v.getContext(), DrinkreceptDetaljerActivity.class);
        intent.putExtra("DrinkreceptNamn", ValdDrinkreceptNamn);
        startActivity(intent);
    }
}

