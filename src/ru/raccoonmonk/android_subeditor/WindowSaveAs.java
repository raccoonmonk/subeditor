package ru.raccoonmonk.android_subeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class WindowSaveAs extends ListActivity {

	private File curPath = null;
	private ArrayList<File> al = null;
	EditText edt;
	Button btnUp = null;
	Button btnCancel = null;
	Button btnSave = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.window_save_as);
		initGUI();
		Intent intent = getIntent();
		String fileAbsPath = intent.getStringExtra("subFileAbs");
		File curFile = new File(fileAbsPath);
		curPath = curFile.getParentFile();
		edt = (EditText) findViewById(R.id.save_as_name);
		edt.setText(curFile.getName());
		showFiles();
		super.onCreate(savedInstanceState);
	}

	private void initGUI() {
		btnSave = (Button) findViewById(R.id.save_as_save_btn);
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				trySave();
			}
		});
		btnUp = (Button) findViewById(R.id.save_as_path_and_up);
		btnUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				up();
			}
		});
		btnCancel = (Button) findViewById(R.id.save_as_cancel_btn);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void trySave() {
		if (!curPath.canWrite()) {
			Toast.makeText(getApplicationContext(), R.string.cant_save,
					Toast.LENGTH_LONG).show();
			return;
		}
		if (!edt.getText().toString().equals("")) {
			Intent intent = getIntent();
			intent.putExtra("modAbsPath", curPath + "/"
					+ edt.getText().toString());
			setResult(RESULT_OK, intent);
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}
	private void up() {
		if (curPath.getParentFile() != null) {
			curPath = curPath.getParentFile();
			showFiles();
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if ((al.get(position).isDirectory())
				&& (al.get(position).list() != null)) {
			curPath = al.get(position);
			showFiles();
		}
		super.onListItemClick(l, v, position, id);
	}

	private void showFiles() {
		btnUp.setText(curPath.getAbsolutePath());
		al = sortFiles(getFiles(curPath));
		BrowserCastomArrayAdapter adapter = new BrowserCastomArrayAdapter(this,
				al);
		setListAdapter(adapter);
	}

	private ArrayList<File> sortFiles(ArrayList<File> al) {
		Comparator<File> compName = new Comparator<File>() {

			@Override
			public int compare(File lhs, File rhs) {
				return lhs.getName().toLowerCase()
						.compareTo(rhs.getName().toLowerCase());
			}
		};
		Comparator<File> compFold = new Comparator<File>() {

			@Override
			public int compare(File lhs, File rhs) {
				if ((lhs.isFile() && rhs.isFile())
						|| !(lhs.isFile() || rhs.isFile())) {
					return 0;
				} else {
					if (lhs.isFile()) {
						return 1;
					} else {
						return -1;
					}
				}
			}
		};
		Collections.sort(al, compName);
		Collections.sort(al, compFold);
		return al;
	}

	private ArrayList<File> getFiles(File path) {
		if (isStorageReadable()) {
			File[] fileList = path.listFiles();
			ArrayList<File> arrLem = new ArrayList<File>(fileList.length);
			for (File file : fileList) {
				arrLem.add(file);
			}
			return arrLem;
		}
		return null;
	}

	private boolean isStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)
				|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}

		return false;
	}
}
