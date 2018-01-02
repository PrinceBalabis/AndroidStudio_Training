package se.www.malmo2night;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


@SuppressLint("WrongViewCast")
public class Start_Start_PopupDialog extends DialogFragment {
//	private EditText message;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popupdialog, container);
//		message = (EditText)view.findViewById(R.id.welcome_text);
        getDialog().setTitle("Erbjudande!");
        return view;
    }
}
