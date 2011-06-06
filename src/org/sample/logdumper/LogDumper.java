package org.sample.logdumper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class LogDumper extends Activity {
	/** Called when the activity is first created. */
	Process mLogcatProc = null;
	BufferedReader reader = null;
	ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		try {
			mLogcatProc = Runtime.getRuntime().exec(
					new String[] { "logcat", "-d" });
			reader = new BufferedReader(new InputStreamReader(
					mLogcatProc.getInputStream()));
			String line;
			final StringBuilder log = new StringBuilder();
			String sepatator = System.getProperty("line.separator");

			while ((line = reader.readLine()) != null) {
				log.append(line);
				log.append(sepatator);
			}
			String outs[] = log.toString().split(sepatator);
			Log.d("debug", log.toString());

			ListAdapter la = (ListAdapter) new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, outs);

			lv = (ListView) findViewById(R.id.listview);
			lv.setAdapter(la);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {

				}
		}

	}
}