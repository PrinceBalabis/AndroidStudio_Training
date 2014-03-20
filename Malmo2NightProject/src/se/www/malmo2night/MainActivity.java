package se.www.malmo2night;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

import tcp.DataHandler;

import static se.www.malmo2night.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static se.www.malmo2night.CommonUtilities.EXTRA_MESSAGE;
import static se.www.malmo2night.CommonUtilities.SENDER_ID;

public class MainActivity extends FragmentActivity {
    private static DataHandler dh;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    // Asyntask
    AsyncTask<Void, Void, Void> mRegisterTask;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Malmo 2 Night");
        dh = Start_LoadingScreen.getDataHandler();


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);

        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(this);

        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                DISPLAY_MESSAGE_ACTION));

        // Get GCM registration id
        final String regId = GCMRegistrar.getRegistrationId(this);
        // Check if regid already presents
        if (regId.equals("")) {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(this, SENDER_ID);
            System.out.println("registrerar nu");
        } else {
            // Device is already registered on GCM
            if (GCMRegistrar.isRegisteredOnServer(this)) {
                System.out.println("hoppar över registrering");
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                final Context context = this;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        // Register on our server
                        // On server creates a new user
                        ServerUtilities.register(context, "TESTNAME", "abdulhamit.sen@gmail.com	", regId);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };
                mRegisterTask.execute(null, null, null);
            }
        }

    }

    /**
     * Receiving push messages
     */
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());

            /**
             * Take appropriate action on this message
             * depending upon your app requirement
             * For now i am just displaying it on the screen
             * */

            // Releasing wake lock
            WakeLocker.release();
        }
    };

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(this);
        } catch (Exception e) {
            Log.e("UnRegister Receiver Error", "> " + e.getMessage());
        }
        super.onDestroy();
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Fragment start = new Start_Start();
                    return start;
                case 1:
                    Fragment utestallen = new Start_Utestallen_Lista();
                    return utestallen;
                case 2:
                    Fragment systembolaget = new Start_Systembolaget_Lista();
                    return systembolaget;
                case 3:
                    Fragment taxi = new Start_Taxi_Lista();
                    return taxi;
                case 4:
                    Fragment drinkrecept = new Start_Drinkrecept_Lista();
                    return drinkrecept;
                case 5:
                    Fragment olspel = new Start_Olspel_Lista();
                    return olspel;
            }
            return null;
        }

        public int getCount() {
            return 6;
        }

        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_start).toUpperCase();
                case 1:
                    return getString(R.string.title_utestallen).toUpperCase();
                case 2:
                    return getString(R.string.title_systembolaget).toUpperCase();
                case 3:
                    return getString(R.string.title_taxi).toUpperCase();
                case 4:
                    return getString(R.string.title_drinkrecept).toUpperCase();
                case 5:
                    return getString(R.string.title_olspel).toUpperCase();
            }
            return null;
        }
    }

    public static DataHandler getDataList() {
        return dh;
    }
}