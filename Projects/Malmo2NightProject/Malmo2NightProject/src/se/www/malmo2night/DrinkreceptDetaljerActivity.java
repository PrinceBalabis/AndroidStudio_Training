package se.www.malmo2night;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class DrinkreceptDetaljerActivity extends Activity {
    private static String namn;
    private static String antalSpelare;
    private static String kategorier;
    private static String instruktioner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Den laddar");
        setContentView(R.layout.activity_drinkrecept_detaljer);
        System.out.println("Den fixar XML");
        namn = getIntent().getStringExtra("DrinkreceptNamn").toString();
        System.out.println("NAMN=" + namn);
        DownloadDrinkreceptInfo dol = new DownloadDrinkreceptInfo();
        dol.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.olspel_detaljer, menu);
        return false;
    }

    private class DownloadDrinkreceptInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            String[] antalElement = new String[MainActivity.getDataList().getDrinkreceptNamnLista().length];
            int position = 0;
            for (int i = 0; i < MainActivity.getDataList().getDrinkreceptNamnLista().length; i++) {
                if (MainActivity.getDataList().getDrinkreceptNamnLista()[i].equals(namn)) {
                    position = i;
                }
            }

            kategorier = MainActivity.getDataList().getDrinkreceptKategoriLista()[position];
            instruktioner = MainActivity.getDataList().getDrinkreceptInstruktionLista()[position];
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            setTitle(namn);

            TextView kategori = (TextView) findViewById(R.id.drinkrecept_kategorier);
            TextView ingredienserView = (TextView) findViewById(R.id.drinkrecept_ingredienser);
            kategori.setText(kategorier);
            ingredienserView.setText(instruktioner);
            super.onPostExecute(result);
        }

    }
}
