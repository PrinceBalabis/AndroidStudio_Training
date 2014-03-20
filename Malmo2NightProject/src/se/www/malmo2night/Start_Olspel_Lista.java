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

import java.util.Random;

public class Start_Olspel_Lista extends ListFragment {
    public class MyListAdapter extends ArrayAdapter<String> {
        Context myContext;

        public MyListAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
            myContext = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.listfragment_row_olspel, parent, false);

            TextView titel = (TextView) row.findViewById(R.id.titel_spel);
            titel.setText(MainActivity.getDataList().getOlspelNamnLista()[position]);

            TextView krav = (TextView) row.findViewById(R.id.krav_spel);
            krav.setText(MainActivity.getDataList().getOlspelKravLista()[position]);

            TextView antalSpelare = (TextView) row.findViewById(R.id.antalSpelare_spel);
            antalSpelare.setText("Antal spelare: " + MainActivity.getDataList().getOlspelAntalSpelareLista()[position]);

            return row;
        }

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        MyListAdapter myListAdapter = new MyListAdapter(getActivity(), R.layout.listfragment_row_olspel, MainActivity.getDataList().getOlspelNamnLista());
        setListAdapter(myListAdapter);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.start_olspel_lista, container, false);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.start_olspel_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_olspel_random:
                Random random = new Random();
                int visibleChildCount = (getListView().getLastVisiblePosition() - getListView().getFirstVisiblePosition()) + 1;
                getListView().performItemClick(getView(), random.nextInt(visibleChildCount), getId());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        String olspel = getListView().getItemAtPosition(position).toString(); // Hämtar namnet på spelet som klickades
        Intent intent = new Intent(v.getContext(), OlspelDetaljerActivity.class);
        intent.putExtra("passon", olspel); // Skickar namnet på ölspelet till activityn
        startActivity(intent); // Startar activityn
    }
}
