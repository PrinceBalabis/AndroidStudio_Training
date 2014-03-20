package se.www.malmo2night;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import tcp.ConnectionDetector;
import tcp.DataHandler;
import tcp.ServerConnection;

public class Start_LoadingScreen extends Activity {
    DownloadDataTask download;
    Context context = this;
    static DataHandler datahandler;
    ProgressBar progressBar;
    boolean connectedToServer = false;
    boolean runningPeridiodicServerCheck = false;
    PeriodicServerConnectionCheck psc;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_loadingscreen);
        progressBar = (ProgressBar) findViewById(R.id.loadingscreen_progressbar);
        progressBar.setMax(100);
        initiateServerConnection();
    }

    private void initiateServerConnection() {
        if (isNetworkOnline() == true) {
            download = new DownloadDataTask();
            download.execute();
        } else {
            System.out.println("Client DO NOT HAVE Internetconnection, updating GUI");
            showNoNetworkError();
        }
    }

    private class DownloadDataTask extends AsyncTask<Void, Integer, Void> {
        private String[] utestallenNamnLista;
        private String[] utestallenAddressLista;
        private String[] utestallenAldersgransLista;
        private String[] utestallenTypLista;
        private String[] utestalleLatitud;
        private String[] utestalleLongitud;
        private String[] systembolagetNamnLista;
        private String[] systembolagetAddressLista;
        private String[] systembolagetTelefonLista;
        private String[] systembolagetOppettiderLankLista;
        private String[] systembolagetLatitud;
        private String[] systembolagetLongitud;
        private String[] taxiNamnLista;
        private String[] taxiTelefonLista;
        private String[] taxiHemsidaLista;
        private String[] drinkreceptNamnLista;
        private String[] drinkreceptInstruktionLista;
        private String[] drinkreceptKategorierLista;
        private String[] olspelNamnLista;
        private String[] olspelInstruktionLista;
        private String[] olspelAntalspelareLista;
        private String[] olspelKravLista;
        private Object[] utestalleLogoLista;

        protected Void doInBackground(Void... params) {
            System.out.println("Startar första uppkopplingen mot servern...");
            ServerConnection getUtestallenNamn = new ServerConnection("utestallen_namn_lista");
            connectedToServer = getUtestallenNamn.isConnected();
            if (connectedToServer == false) {
                System.out.println("ERROR! Kunde inte koppla sig till servern!");
                if (runningPeridiodicServerCheck != true) {
                    System.out.println("Visar ServersAreDownError meddelande!");
                    showServersAreDownError();
                }
                System.out.println("ERROR! Avbryter nerladdning av data!");
                download.cancel(true);
            }

            if (download.isCancelled() == false) {
                System.out.println("Server är online!");
                if (runningPeridiodicServerCheck == true) {
                    System.out.println("Dödar periodisk serveruppkoppling");
                    psc.killPeriodicServerConnectionCheck();
                }
                runningPeridiodicServerCheck = false;
                if (runningPeridiodicServerCheck == true) {
                    runningPeridiodicServerCheck = false;
                    reloadprogressbar();
                }
                publishProgress(1);
                System.out.println("Laddar ner data från servern...");
                publishProgress(4);
                ServerConnection getUtestallenAddress = new ServerConnection("utestallen_address_lista");
                publishProgress(8);
                ServerConnection getUtestallenaAldergrans = new ServerConnection("utestallen_aldersgrans_lista");
                publishProgress(12);
                ServerConnection getUtestalleTyp = new ServerConnection("utestallen_typ_lista");
                publishProgress(16);
                ServerConnection getUtestalleLatitud = new ServerConnection(
                        "utestallen_koordinatLat_lista");
                publishProgress(20);
                ServerConnection getUtestalleLongitud = new ServerConnection(
                        "utestallen_koordinatLng_lista");
                publishProgress(24);
                publishProgress(33);
                ServerConnection getSystembolagetNamn = new ServerConnection(
                        "systembolaget_namn_lista");
                publishProgress(28);
                ServerConnection getSystembolagetAddresser = new ServerConnection(
                        "systembolaget_address_lista");
                publishProgress(32);
                ServerConnection getSystembolagetTelefonnummer = new ServerConnection(
                        "systembolaget_nummer_lista");
                publishProgress(36);
                ServerConnection getSystembolagetoppettiderlank = new ServerConnection(
                        "systembolaget_opettiderlank_lista");
                publishProgress(40);
                ServerConnection getSystembolagetLatitud = new ServerConnection(
                        "systembolaget_koordinatLat_lista");
                publishProgress(44);
                ServerConnection getSystembolagetLongitud = new ServerConnection(
                        "systembolaget_koordinatLng_lista");
                publishProgress(48);
                ServerConnection getTaxiNamn = new ServerConnection(
                        "taxi_namn_lista");
                publishProgress(52);
                ServerConnection getTaxiNummer = new ServerConnection(
                        "taxi_nummer_lista");
                publishProgress(56);
                ServerConnection getTaxiHemsida = new ServerConnection(
                        "taxi_hemsida_lista");
                publishProgress(60);
                publishProgress(66);
                ServerConnection getDrinkreceptNamn = new ServerConnection(
                        "drinkrecept_namn_lista");
                publishProgress(64);
                ServerConnection getDrinkreceptInstruktion = new ServerConnection(
                        "drinkrecept_instruktion_lista");
                publishProgress(68);
                ServerConnection getDrinkreceptKategorier = new ServerConnection(
                        "drinkrecept_kategorier_lista");
                publishProgress(72);
                ServerConnection getOlspelNamn = new ServerConnection(
                        "olspel_namn_lista");
                publishProgress(76);
                ServerConnection getOlspelInstruktioner = new ServerConnection(
                        "olspel_instruktioner_lista");
                publishProgress(80);
                ServerConnection getOlspelAntalspelare = new ServerConnection(
                        "olspel_antalspelare_lista");
                publishProgress(84);
                ServerConnection getOlspelKrav = new ServerConnection(
                        "olspel_krav_lista");
                ServerConnection getUtestalleLogo = new ServerConnection("image_");
                publishProgress(88);

                utestallenNamnLista = getUtestallenNamn.getAnswer();
                utestallenAddressLista = getUtestallenAddress.getAnswer();
                utestallenAldersgransLista = getUtestallenaAldergrans.getAnswer();
                utestallenTypLista = getUtestalleTyp.getAnswer();
                utestalleLatitud = getUtestalleLatitud.getAnswer();
                utestalleLongitud = getUtestalleLongitud.getAnswer();
                systembolagetNamnLista = getSystembolagetNamn.getAnswer();
                systembolagetAddressLista = getSystembolagetAddresser.getAnswer();
                systembolagetTelefonLista = getSystembolagetTelefonnummer.getAnswer();
                systembolagetOppettiderLankLista = getSystembolagetoppettiderlank.getAnswer();
                systembolagetLatitud = getSystembolagetLatitud.getAnswer();
                systembolagetLongitud = getSystembolagetLongitud.getAnswer();
                taxiNamnLista = getTaxiNamn.getAnswer();
                taxiTelefonLista = getTaxiNummer.getAnswer();
                taxiHemsidaLista = getTaxiHemsida.getAnswer();
                drinkreceptNamnLista = getDrinkreceptNamn.getAnswer();
                drinkreceptInstruktionLista = getDrinkreceptInstruktion.getAnswer();
                drinkreceptKategorierLista = getDrinkreceptKategorier.getAnswer();
                olspelNamnLista = getOlspelNamn.getAnswer();
                olspelInstruktionLista = getOlspelInstruktioner.getAnswer();
                olspelAntalspelareLista = getOlspelAntalspelare.getAnswer();
                olspelKravLista = getOlspelKrav.getAnswer();
                utestalleLogoLista = getUtestalleLogo.getImageAnswer();
                publishProgress(92);

                datahandler = new DataHandler();
                datahandler.setUtestallenNamnLista(utestallenNamnLista);
                datahandler.setUtestallenAddressLista(utestallenAddressLista);
                datahandler.setUtestallenAldergransLista(utestallenAldersgransLista);
                datahandler.setUtestallenTypLista(utestallenTypLista);
                datahandler.setUtestallenLatitud(utestalleLatitud);
                datahandler.setUtestallenLongitud(utestalleLongitud);
                datahandler.setSystembolagetNamnLista(systembolagetNamnLista);
                datahandler.setSystembolagetAddressLista(systembolagetAddressLista);
                datahandler.setSystembolageTelefonLista(systembolagetTelefonLista);
                datahandler.setSystembolagetOppettiderLankLista(systembolagetOppettiderLankLista);
                datahandler.setSystembolagetLatitud(systembolagetLatitud);
                datahandler.setSystembolagetLongitud(systembolagetLongitud);
                datahandler.setTaxiNamnLista(taxiNamnLista);
                datahandler.setTaxiTelefonLista(taxiTelefonLista);
                datahandler.setTaxiHemsidaLista(taxiHemsidaLista);
                datahandler.setDrinkreceptNamnLista(drinkreceptNamnLista);
                datahandler.setDrinkreceptInstruktionLista(drinkreceptInstruktionLista);
                datahandler.setDrinkreceptKategoriLista(drinkreceptKategorierLista);
                datahandler.setOlspelNamnLista(olspelNamnLista);
                datahandler.setOlspelInstruktionLista(olspelInstruktionLista);
                datahandler.setOlspelAntalSpelareLista(olspelAntalspelareLista);
                datahandler.setOlspelKravLista(olspelKravLista);
                datahandler.setUtestalleLogoLista(utestalleLogoLista);
                publishProgress(95);
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            if (runningPeridiodicServerCheck != true && download.isCancelled() == false) {
                progressBar.setProgress(progress[0]);
            }
        }

        protected void onPostExecute(Void arg0) {
            super.onPostExecute(arg0);
            if (download.isCancelled() == false) {
                Intent intent = new Intent(context, MainActivity.class);
                publishProgress(100);
                startActivity(intent);
                finish();
            }
        }
    }


    private boolean isNetworkOnline() {
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        Boolean isInternetPresent = cd.isConnectingToInternet(); // true or false
        return isInternetPresent;
    }

    private void showServersAreDownError() {
        TextView errorText = (TextView) findViewById(R.id.loadingscreen_errortext);
        errorText.setText("Ursäkta, servrarna är för tillfället nere!");
//		ImageView sadSmiley = (ImageView) findViewById(R.id.loadingscreen_sadsmiley);	
//		sadSmiley.setImageResource(R.drawable.ic_sadsmiley);
        progressBar.setVisibility(View.INVISIBLE);
        psc = new PeriodicServerConnectionCheck();
    }

    private void reloadprogressbar() {
        TextView errorText = (TextView) findViewById(R.id.loadingscreen_errortext);
        errorText.setText("");
        ImageView sadSmiley = (ImageView) findViewById(R.id.loadingscreen_sadsmiley);
        sadSmiley.setVisibility(0);
        progressBar.setVisibility(View.VISIBLE);
    }

    private class PeriodicServerConnectionCheck extends Thread {
        private volatile Thread periodicServerCheckThred;

        public PeriodicServerConnectionCheck() {
            start();
            System.out.println("Startar periodisk serveruppkoppling - Testar uppkoppling till servern med ett intervall av 10 sekunder");
        }

        public void start() {
            periodicServerCheckThred = new Thread(this);
            periodicServerCheckThred.start();
        }

        public void killPeriodicServerConnectionCheck() {
            periodicServerCheckThred = null;
        }

        public void run() {
            Thread thisThread = Thread.currentThread();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            runningPeridiodicServerCheck = true;
            while (periodicServerCheckThred == thisThread) {
                System.out.println("Kör periodisk uppkoppling mot servern...");
                initiateServerConnection();
                try {
                    Thread.sleep(11000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    private void showNoNetworkError() {
        TextView errorText = (TextView) findViewById(R.id.loadingscreen_errortext);
        errorText.setText("Se till att internetuppkoppling och GPS för att kunna använda appen");
        progressBar.setVisibility(View.INVISIBLE);
//		ImageView sadSmiley = (ImageView) findViewById(R.id.loadingscreen_sadsmiley);	
//		sadSmiley.setImageResource(R.drawable.ic_sadsmiley);
//		psc = new PeriodicServerConnectionCheck();

    }


    protected void onPause() {
        super.onPause();
        if (connectedToServer == false) {
            finish();
        }
    }


    public static DataHandler getDataHandler() {
        return datahandler;
    }
}
