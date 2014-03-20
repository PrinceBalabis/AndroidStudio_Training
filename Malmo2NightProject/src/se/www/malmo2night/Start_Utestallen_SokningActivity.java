package se.www.malmo2night;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import se.www.malmo2night.R.drawable;

public class Start_Utestallen_SokningActivity extends ListActivity {
    MyListAdapter myListAdapter;
    String[] utestalleNamnList;
    String[] utestalleAddressList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setTitle("Uteställesökning för: " + i.getStringExtra("UtestalleNamnSearch"));
        utestalleNamnList = i.getStringArrayExtra("SearchResultsNamn");
        utestalleAddressList = i.getStringArrayExtra("SearchResultsAddress");
        myListAdapter = new MyListAdapter(this, R.layout.listfragment_row_utestallen, utestalleNamnList);
        setListAdapter(myListAdapter);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.start_utestallen_lista, container, false);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        String utestalleDetaljer = getListView().getItemAtPosition(position).toString();
        Intent intent = new Intent(v.getContext(), UtestalleDetaljerActivity.class);
        intent.putExtra("UtestalleNamn", utestalleDetaljer);
        startActivity(intent);
    }

    public class MyListAdapter extends ArrayAdapter<String> {
        Context myContext;
        private String[] objects;

        public MyListAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
            myContext = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.listfragment_row_utestallen, parent, false);

            TextView namn = (TextView) row.findViewById(R.id.utestalle_namn);
            namn.setText(utestalleNamnList[position]);

            TextView address = (TextView) row.findViewById(R.id.utestalle_address);
            address.setText(utestalleAddressList[position]);

            ImageView icon = (ImageView) row.findViewById(R.id.utestalle_ikon);

            icon.setImageResource(drawable.ic_launcher);

            TextView gradering = (TextView) row.findViewById(R.id.utestalle_gradering);
            gradering.setText("gradering \nkommer snart!");

            return row;
        }
    }

}
