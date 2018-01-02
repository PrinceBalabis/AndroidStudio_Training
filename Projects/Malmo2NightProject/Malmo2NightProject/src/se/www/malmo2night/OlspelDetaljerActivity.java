package se.www.malmo2night;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class OlspelDetaljerActivity extends Activity {
    private static String namn;
    private static String antalSpelare;
    private static String regler;
    private static String instruktioner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olspel_detaljer);
        namn = getIntent().getStringExtra("passon").toString();
        System.out.println("NAMN=" + namn);
        DownloadOlspelInfo dol = new DownloadOlspelInfo();
        dol.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.olspel_detaljer, menu);
        return false;
    }

    private class DownloadOlspelInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            String[] antalElement = new String[MainActivity.getDataList().getOlspelNamnLista().length];
            int position = 0;
            for (int i = 0; i < MainActivity.getDataList().getOlspelNamnLista().length; i++) {
                if (MainActivity.getDataList().getOlspelNamnLista()[i].equals(namn)) {
                    position = i;
                }
            }

            regler = MainActivity.getDataList().getOlspelKravLista()[position];
            instruktioner = MainActivity.getDataList().getOlspelInstruktionLista()[position];
            antalSpelare = MainActivity.getDataList().getOlspelAntalSpelareLista()[position];
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            setTitle(namn);

            TextView antalSpelareView = (TextView) findViewById(R.id.olspel_antal);
            TextView reglerView = (TextView) findViewById(R.id.olspel_regler);
            TextView instruktionerView = (TextView) findViewById(R.id.olspel_instruktioner);
            antalSpelareView.setText(antalSpelare);
            reglerView.setText(regler);
            instruktionerView.setText(instruktioner);
            super.onPostExecute(result);
        }

    }
}
