package se.www.malmo2night;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import gps.DistanceCalculator;
import gps.FetchLocation;
import se.www.malmo2night.R.drawable;

public class Start_Utestallen_Lista extends ListFragment {
    public MyListAdapter myListAdapter;
    private double[] utestalleCoordinateListLat;
    private double[] utestalleCoordinateListLng;
    private double[] currentCoordinates;
    private int distance;
    private int[] listIds;
    private String[] utestalleNamnList;
    private String[] utestalleAddressList;

    public class MyListAdapter extends ArrayAdapter<String> {
        Context myContext;
        private String[] objects;

        public MyListAdapter(Context context, int textViewResourceId,
                             String[] objects) {
            super(context, textViewResourceId, objects);
            myContext = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) myContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.listfragment_row_utestallen,
                    parent, false);

            TextView namn = (TextView) row.findViewById(R.id.utestalle_namn);
            namn.setText(utestalleNamnList[position]);

            TextView address = (TextView) row
                    .findViewById(R.id.utestalle_address);
            address.setText(utestalleAddressList[position]);

            ImageView icon = (ImageView) row.findViewById(R.id.utestalle_ikon);

            icon.setImageResource(drawable.ic_launcher);

            TextView gradering = (TextView) row
                    .findViewById(R.id.utestalle_gradering);
            gradering.setText("gradering \nkommer snart!");

            distance = (int) DistanceCalculator.distFrom(currentCoordinates[0],
                    currentCoordinates[1],
                    utestalleCoordinateListLat[position],
                    utestalleCoordinateListLng[position]);

            TextView avstand = (TextView) row
                    .findViewById(R.id.utestalle_avstand);

            TextView avstandTitel = (TextView) row
                    .findViewById(R.id.utestalle_avståndsText);

            if (distance == -1) {
                avstand.setText("");
                avstandTitel.setText("");
            } else {
                avstand.setText((" " + distance) + "m");
            }

            return row;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        utestalleCoordinateListLat = new double[MainActivity.getDataList()
                .getUtestallenLatitud().length];
        utestalleCoordinateListLng = new double[MainActivity.getDataList()
                .getUtestallenLongitud().length];

        for (int i = 0; i < MainActivity.getDataList().getUtestallenLatitud().length; i++) {
            utestalleCoordinateListLat[i] = Double.parseDouble(MainActivity
                    .getDataList().getUtestallenLatitud()[i]);
            utestalleCoordinateListLng[i] = Double.parseDouble(MainActivity
                    .getDataList().getUtestallenLongitud()[i]);
        }

        currentCoordinates = getCurrentCoordinates();

        utestalleNamnList = MainActivity.getDataList().getUtestallenNamnLista();
        utestalleAddressList = MainActivity.getDataList()
                .getUtestallenAddressLista();
        myListAdapter = new MyListAdapter(getActivity(),
                R.layout.listfragment_row_utestallen, utestalleNamnList);
        setListAdapter(myListAdapter);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.start_utestallen_lista, container,
                false);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.start_utestallen_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                FragmentManager fm = getFragmentManager();
                Start_Utestallen_Popup_Sokning popup = new Start_Utestallen_Popup_Sokning();
                popup.show(fm, "popupdialog");
                return true;
            case R.id.action_filter:
                FragmentManager fm2 = getFragmentManager();
                Start_Utestallen_Popup_Filter popup2 = new Start_Utestallen_Popup_Filter();
                popup2.show(fm2, "popupdialog");
                return true;
            case R.id.action_utestallen_locations:
                FragmentManager fm3 = getFragmentManager();
                Start_Utestallen_Popup_Locations popup3 = new Start_Utestallen_Popup_Locations();
                popup3.show(fm3, "popupdialog");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        String utestalleDetaljer = getListView().getItemAtPosition(position)
                .toString();
        Intent intent = new Intent(v.getContext(),
                UtestalleDetaljerActivity.class);
        System.out.println(utestalleDetaljer);
        intent.putExtra("UtestalleNamn", utestalleDetaljer);
        startActivity(intent);
    }

    @SuppressLint("ValidFragment")
    public class Start_Utestallen_Popup_Sokning extends DialogFragment {
        EditText searchText;

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(
                    R.layout.start_utestallen_popup_sokning, container, false);

            getDialog().setTitle("Sök uteställe");
            getDialog().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            searchText = (EditText) view.findViewById(R.id.popup_soktext);
            searchText.setOnKeyListener(new OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN)
                            && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        giveSearchTest();
                        dismiss();

                        return true;
                    }
                    return false;
                }
            });

            Button cancelButton = (Button) view.findViewById(R.id.popup_avbryt);
            cancelButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

            Button searchButton = (Button) view.findViewById(R.id.popup_sok);
            searchButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    giveSearchTest();

                }
            });

            searchText.requestFocus();
            return view;
        }

        private void giveSearchTest() {
            listIds = MainActivity.getDataList().searchForUtestalle(
                    searchText.getText().toString());
            String[] utestalleNamnList;
            String[] utestalleAddressList;
            if (listIds[0] == -1) {
                utestalleNamnList = new String[1];
                utestalleNamnList[0] = "Utestället du sökte efter kunde inte hittas!";
                utestalleAddressList = new String[1];
                utestalleAddressList[0] = "";
            } else {
                utestalleNamnList = new String[listIds.length];
                System.out.println("UTESTALLENNAMNLIST=" + utestalleNamnList);
                utestalleAddressList = new String[listIds.length];
                System.out.println("UTESTALLENADRESSLIST=" + utestalleAddressList);
                for (int i = 0; i < listIds.length; i++) {
                    utestalleNamnList[i] = MainActivity.getDataList()
                            .getUtestallenNamnLista()[listIds[i]];
                    System.out.println("UTESTALLENNAMNLIST=" + utestalleNamnList[i]);
                    utestalleAddressList[i] = MainActivity.getDataList()
                            .getUtestallenAddressLista()[listIds[i]];
                    System.out.println("UTESTALLENADRESSLIST=" + utestalleAddressList[i]);
                }
            }
            // myListAdapter = new
            // MyListAdapter(getActivity(),R.layout.listfragment_row_drinkrecept,
            // utestalleNamnList);
            // setListAdapter(myListAdapter);
            // myListAdapter.notifyDataSetChanged();
            Intent i = new Intent(getActivity(),
                    Start_Utestallen_SokningActivity.class);
            i.putExtra("UtestalleNamnSearch", searchText.getText().toString());
            i.putExtra("SearchResultsNamn", utestalleNamnList);
            i.putExtra("SearchResultsAddress", utestalleAddressList);
            startActivity(i);
            dismiss();
        }
    }

    @SuppressLint("ValidFragment")
    public class Start_Utestallen_Popup_Filter extends DialogFragment {
        Spinner typSpinner;
        CheckBox typCheckBox;
        EditText minimumalder;
        CheckBox minimumalderCheckbox;

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(
                    R.layout.start_utestallen_popup_filter, container);

            getDialog().setTitle("Filtrera uteställen");

            typSpinner = (Spinner) view
                    .findViewById(R.id.utestalle_spinner_typ);
            typSpinner.setEnabled(false);

            typCheckBox = (CheckBox) view.findViewById(R.id.utestalle_typ);
            typCheckBox
                    .setOnCheckedChangeListener(new OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            if (typCheckBox.isChecked()) {
                                typSpinner.setEnabled(true);
                            } else {
                                typSpinner.setEnabled(false);

                            }
                        }
                    });

            minimumalder = (EditText) view.findViewById(R.id.filter_alder);
            minimumalder.setEnabled(false);

            minimumalderCheckbox = (CheckBox) view
                    .findViewById(R.id.utestalle_minimumalder);
            minimumalderCheckbox
                    .setOnCheckedChangeListener(new OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            if (minimumalderCheckbox.isChecked()) {
                                minimumalder.setEnabled(true);
                            } else {
                                minimumalder.setEnabled(false);
                                minimumalder.setText("");
                            }
                        }
                    });

            minimumalder.setOnKeyListener(new OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN)
                            && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        executeFiltering();
                        dismiss();
                        return true;
                    }
                    return false;
                }
            });

            Button cancelButton = (Button) view
                    .findViewById(R.id.utestallen_filter_avbryt);
            cancelButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

            Button searchButton = (Button) view
                    .findViewById(R.id.utestallen_filter_ok);
            searchButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    executeFiltering();
                    dismiss();
                }
            });
            return view;
        }

        private void executeFiltering() {
            String typ = "-1";
            int alder = -1;
            if (typSpinner.isEnabled()) {
                typ = typSpinner.getSelectedItem().toString();
            }
            if (minimumalderCheckbox.isEnabled()
                    && minimumalder.getText().length() > 0) {
                alder = Integer.parseInt(minimumalder.getText().toString());
            }
            if (!typ.equals("-1") || alder != -1) {
                utestalleNamnList = MainActivity.getDataList()
                        .filterUtestallen(typ, alder, getSortMethod());
                myListAdapter = new MyListAdapter(getActivity(),
                        R.layout.listfragment_row_drinkrecept,
                        utestalleNamnList);
                setListAdapter(myListAdapter);
                myListAdapter.notifyDataSetChanged();
            }
        }

        public int getSortMethod() {
            RadioButton sortByName = (RadioButton) getView().findViewById(
                    R.id.utestallen_sortBy_name);
            RadioButton sortByAge = (RadioButton) getView().findViewById(
                    R.id.utestallen_sortBy_age);
            RadioButton sortByDistance = (RadioButton) getView().findViewById(
                    R.id.utestallen_sortBy_distance);
            RadioButton sortByGrade = (RadioButton) getView().findViewById(
                    R.id.utestallen_sortBy_grade);

            if (sortByName.isChecked()) {
                return 0;
            } else if (sortByAge.isChecked()) {
                return 1;
            } else if (sortByDistance.isChecked()) {
                return 2;
            } else if (sortByGrade.isChecked()) {
                return 3;
            } else {
                return -1;
            }
        }

    }

    private void executeOrder66() {
        System.out.println("Wipe them out, all of them...");
    }

    private double[] getCurrentCoordinates() {
        FetchLocation fetchgps = new FetchLocation(getActivity());
        double[] coordinates = new double[2];
        coordinates[0] = fetchgps.getLatitude();
        coordinates[1] = fetchgps.getLongitude();
        return coordinates;
    }
}
