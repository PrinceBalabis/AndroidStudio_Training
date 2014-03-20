package se.www.malmo2night;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Start_Taxi_Popup extends DialogFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_taxi_popup, container, false);
        getDialog().setTitle(getArguments().getString("Namn"));
        final String hemsida = getArguments().getString("Hemsida");
        final String telefonnummer = getArguments().getString("Telefonnummer");
        Button oppettiderButton = (Button) view.findViewById(R.id.taxi_hemsida);
        oppettiderButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(hemsida));
                startActivity(browserIntent);
                dismiss();
            }
        });

        Button ringKnapp = (Button) view.findViewById(R.id.taxi_ring);
        ringKnapp.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + telefonnummer));
                startActivity(i);
                dismiss();
            }
        });


        Button kopiera = (Button) view.findViewById(R.id.taxi_kopiera_telefonnummer);
        kopiera.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ClipData clip = ClipData.newPlainText("label", telefonnummer);
                ClipboardManager clipBoardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                clipBoardManager.setPrimaryClip(clip);
                Toast toast = Toast.makeText(getView().getContext(), "Taxibolagets telefonnummer har lagts in på urklipp", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                dismiss();
            }
        });
        return view;
    }
}
