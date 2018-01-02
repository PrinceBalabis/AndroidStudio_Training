package se.www.malmo2night;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tcp.ServerConnection;


public class UtestalleDetaljer_Kommentarer extends ListFragment {
    private MyListAdapter myListAdapter;
    private String[] utestalleKommentarerTitel = {"Inga kommentarer"};
    private String[] utestalleKommentarer = {" "};
    private EditText kommentar;
    private EditText kommentarTitel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.utestalledetaljer_kommentar, container, false);

        DownloadUtestalleKommentarer getKommentarerFromServer = new DownloadUtestalleKommentarer();
        getKommentarerFromServer.execute();

        Button skrivKommentar = (Button) view.findViewById(R.id.utestalle_kommentar_skriv);
        skrivKommentar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                Popup_kommentar popup = new Popup_kommentar();
                popup.show(fm, "popupdialog");
            }
        });

        return view;

    }

    public class MyListAdapter extends ArrayAdapter<String> {
        Context myContext;

        public MyListAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
            myContext = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.listfragment_row_utestallekommentar, parent, false);

            TextView kommentar = (TextView) row.findViewById(R.id.utestalle_kommentar);
            kommentar.setText(utestalleKommentarer[position]);

            TextView kommentartitel = (TextView) row.findViewById(R.id.utestalle_titelkommentar);
            kommentartitel.setText(utestalleKommentarerTitel[position]);


            return row;
        }
    }


    private class DownloadUtestalleKommentarer extends AsyncTask<Void, Void, Void> {
        ServerConnection sCKommentarer;
        ServerConnection sCTitelKommentarer;
        boolean serverConnected;

        protected Void doInBackground(Void... params) {
            String utestalleNamn = UtestalleDetaljerActivity.getUtestalleNamn();
            sCKommentarer = new ServerConnection("rk_" + utestalleNamn);
            sCTitelKommentarer = new ServerConnection("rkt_" + utestalleNamn);
            serverConnected = sCKommentarer.isConnected();
            if (serverConnected == false) {
                Intent intent = new Intent(getActivity(), Start_LoadingScreen.class);
                startActivity(intent);
                getActivity().finish();
            }
            return null;
        }

        protected void onPostExecute(Void arg0) {
            super.onPostExecute(arg0);
            if (serverConnected) {
                if (sCKommentarer.getAnswer() != null) {
                    utestalleKommentarer = sCKommentarer.getAnswer();
                    utestalleKommentarerTitel = sCTitelKommentarer.getAnswer();
                }
                myListAdapter = new MyListAdapter(getActivity(), R.layout.listfragment_row_utestallekommentar, utestalleKommentarer);
                setListAdapter(myListAdapter);
                myListAdapter.notifyDataSetChanged();
            }

        }
    }

    private class SendUtestalleKommentarer extends AsyncTask<Void, Void, Void> {
        ServerConnection sendComment;

        protected Void doInBackground(Void... params) {
            //			ServerConnection writeComment = new ServerConnection("wk_Namn~asdfasdf~ffdsgfsdg");
            String sendCommand = "wk_" + UtestalleDetaljerActivity.getUtestalleNamn() + "~" + kommentarTitel.getText().toString() + "~" + kommentar.getText().toString();
            System.out.println(sendCommand);
            sendComment = new ServerConnection(sendCommand);
            return null;
        }

        protected void onPostExecute(Void arg0) {
            if (sendComment.getAnswer()[0].equals("true")) {
                Toast.makeText(getActivity(), "Kommentar skriven", 3).show();
            } else {
                Toast.makeText(getActivity(), "ERROR!", 3).show();
            }
        }
    }


    @SuppressLint("ValidFragment")
    public class Popup_kommentar extends DialogFragment {

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.utestalledetaljer_popup_kommentar, container, false);

            getDialog().setTitle("Skriv kommentar");
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            // HÄNDELSE NÄR MAN TRYCKER ENTER
            kommentar = (EditText) view.findViewById(R.id.utestalle_kommentar_text);
            kommentar.setOnKeyListener(new OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN)
                            && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        new SendUtestalleKommentarer().execute();
                        new DownloadUtestalleKommentarer().execute();
                        dismiss();
                        return true;
                    }
                    return false;
                }
            });

            // HÄNDELSE NÄR MAN TRYCKER ENTER
            kommentarTitel = (EditText) view.findViewById(R.id.utestalle_kommentar_titel);
            kommentarTitel.setOnKeyListener(new OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN)
                            && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        new SendUtestalleKommentarer().execute();
                        new DownloadUtestalleKommentarer().execute();
                        dismiss();
                        return true;
                    }
                    return false;
                }
            });
            // Ställer in avbrytknappen
            Button cancelButton = (Button) view.findViewById(R.id.utestalle_kommentar_avbryt);
            cancelButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

            // Ställer in skicka knappen
            Button skickaKommentar = (Button) view.findViewById(R.id.utestalle_kommentar_skicka);
            skickaKommentar.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    System.out.println("KLICKADE");
                    new SendUtestalleKommentarer().execute();
                    new DownloadUtestalleKommentarer().execute();

                    dismiss();
                }
            });

            kommentar.requestFocus();
            return view;
        }

    }

}