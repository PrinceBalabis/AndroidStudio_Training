package se.www.malmo2night;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import tcp.ServerConnection;

/**
 * Fragment som representarar.
 */
public class UtestalleDetaljer_Information extends Fragment {

    private String lat;
    private String lng;
    private String typ;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.utestalledetaljer_information,
                container, false);
        DownloadUtestalleDetaljer getDetaljerFromServer = new DownloadUtestalleDetaljer();
        getDetaljerFromServer.execute();

        Button findButton = (Button) view.findViewById(R.id.utestalle_hittahit);
        findButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                launchGoogleMaps();
            }
        });

        return view;
    }

    private class DownloadUtestalleDetaljer extends AsyncTask<Void, Void, Void> {
        ServerConnection serverConnection;
        boolean serverConnected;
        String[] utestalleDetaljer;

        protected Void doInBackground(Void... params) {
            serverConnection = new ServerConnection("utestalle_detaljer_" + UtestalleDetaljerActivity.getUtestalleNamn());
            serverConnected = serverConnection.isConnected();
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
                utestalleDetaljer = serverConnection.getAnswer();
            }

            ImageView utestalle_logo = (ImageView) getView().findViewById(
                    R.id.utestalle_logo);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(baos);
                oos.writeObject(MainActivity.getDataList().getUtestalleLogoLista()[0]);
                InputStream is = new ByteArrayInputStream(baos.toByteArray());

                utestalle_logo.setImageBitmap(BitmapFactory.decodeStream(is));
                oos.flush();
                oos.close();

            } catch (IOException e) {
            }

            TextView textView_oppettider = (TextView) getView().findViewById(
                    R.id.utestalle_oppettider);
            textView_oppettider.setText(utestalleDetaljer[5]);

            TextView textView_address = (TextView) getView().findViewById(
                    R.id.utestalle_address);
            textView_address.setText(textView_address.getText()
                    + utestalleDetaljer[1]);
            TextView textView_aldersgrans = (TextView) getView().findViewById(
                    R.id.utestalle_aldersgrans);
            textView_aldersgrans.setText(textView_aldersgrans.getText()
                    + utestalleDetaljer[2] + " år");

            TextView textView_intrade = (TextView) getView().findViewById(
                    R.id.utestalle_intrade);
            textView_intrade.setText(textView_intrade.getText()
                    + utestalleDetaljer[3] + " kr");

            TextView textView_telefonnummer = (TextView) getView()
                    .findViewById(R.id.utestalle_telefonnummer);
            textView_telefonnummer.setText(textView_telefonnummer.getText()
                    + utestalleDetaljer[4]);

            typ = utestalleDetaljer[7];
            lat = utestalleDetaljer[8];
            lng = utestalleDetaljer[9];

            TextView utestalle_hittahitknapp = (TextView) getView()
                    .findViewById(R.id.utestalle_hittahit);
            utestalle_hittahitknapp.setEnabled(true);

        }
    }

    private void launchGoogleMaps() {
        String uri = "geo:0,0?q=" + lat + "," + lng + " (" + typ + ": "
                + UtestalleDetaljerActivity.getUtestalleNamn() + ")";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }
}