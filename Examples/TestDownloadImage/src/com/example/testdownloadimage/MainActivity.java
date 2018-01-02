package com.example.testdownloadimage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView tv;
	EditText etip;
	Button btn;
	Bitmap bitmapimage;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv=(TextView)findViewById(R.id.textView1);
		btn=(Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.out.println("test");
				new DownloadDataTask().execute();	
			}

		});
	}


	private class DownloadDataTask extends AsyncTask<Void, Integer, Void> {
		boolean downloaded = false;
		ServerConnection sc;
		protected Void doInBackground(Void... params) {
			System.out.println("running");
			sc = new ServerConnection("image_Babel");
			if(sc.isConnected){
				downloaded = true;
			} else{
				System.out.println("fail1");
			}
			return null;
		}

		protected void onPostExecute(Void arg0) {
			super.onPostExecute(arg0);
			if(downloaded){
				ImageView icon = (ImageView) findViewById(R.id.imageView1);
				icon.setImageBitmap(sc.getImageAnswer());
				icon.invalidate();
			} else{
				System.out.println("fail2");
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
