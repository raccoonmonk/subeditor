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
import android.widget.ListView;

public class WindowBrowseOpen extends ListActivity {

	private File curPath = Environment.getExternalStorageDirectory();
	private ArrayList<File> al = null;
	Button btnCancel = null;
	Button pathUp = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.window_file_browser_open);
		initGUI();
		showFiles();
		super.onCreate(savedInstanceState);
	}
	private void initGUI() {
		pathUp = (Button) findViewById(R.id.path_and_up);
		pathUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				up();
			};
		});
		btnCancel = (Button)findViewById(R.id.window_browse_cancel_btn);
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			};
		});
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if ((al.get(position).isDirectory())
				&& (al.get(position).list() != null)) {
			curPath = al.get(position);
			showFiles();
		}else{
			if(al.get(position).getName().toLowerCase().endsWith(".srt")){
				
				Intent intent = getIntent();
				GlobalData.setFileSub(al.get(position));
				setResult(RESULT_OK, intent);
				finish();
			}
		}

		super.onListItemClick(l, v, position, id);
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

	private void showFiles() {
		pathUp.setText(curPath.getAbsolutePath());
		al = sortFiles(getFiles(curPath));
		BrowserCastomArrayAdapter adapter = new BrowserCastomArrayAdapter(this, al);
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
