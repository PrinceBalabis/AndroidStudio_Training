package se.www.malmo2night;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Start_Systembolaget_Popup extends DialogFragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_systembolag_popup,
                container, false);
        getDialog().setTitle(getArguments().getString("Namn"));
        final String oppettiderLank = getArguments()
                .getString("OppettiderLank");
        // Ställer in så att en oppettider öppnas i en webbläsare
        Button oppettiderButton = (Button) view
                .findViewById(R.id.systembolaget_button_oppettider);
        oppettiderButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse(oppettiderLank));
                startActivity(browserIntent);
                dismiss();
            }
        });
        Button findButton = (Button) view
                .findViewById(R.id.systembolaget_hittanarmaste);
        findButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                launchGoogleMaps();
                dismiss();
            }
        });

        return view;
    }

    private void launchGoogleMaps() {
        String uri = "geo:0,0?q=" + getArguments().getString("Latitud") + "," + getArguments().getString("Longitud") + " (Systembolag: " + getArguments().getString("Namn") + ")";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }
}
