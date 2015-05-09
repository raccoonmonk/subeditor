package ru.raccoonmonk.android_subeditor;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;
import static ru.raccoonmonk.android_subeditor.Config.*;

public class WindowEditor extends ListActivity{
	private File subFile = null;
	private ArrayList<SubRecord> arrLines = null;
	private int curRec = 0;
	Menu globMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.window_editor);
		subFile = GlobalData.getFileSub();
		arrLines = IOFiles.readFileSubRip(subFile);
		loadGUI();
		registerForContextMenu(getListView());
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		getListView().setSelection(position);
		curRec = position + 1;
		Intent intent1 = new Intent(WindowEditor.this, WindowEditRec.class);
		intent1.putExtra("recNum", arrLines.get(position).num);
		intent1.putExtra("recTime", arrLines.get(position).time);
		intent1.putExtra("recText", arrLines.get(position).text);
		intent1.putExtra("curRec", position);
		startActivityForResult(intent1, CODE_REQ_EDIT_REC);
		super.onListItemClick(l, v, position, id);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_rec_menu, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		RecEditFuncs.setIsChanged(true, globMenu);
		curRec = info.position;
		getListView().setSelection(curRec);
		int i = 1;
		switch (item.getItemId()) {
		case R.id.del_rec:
			arrLines.remove(info.position);
			for (SubRecord sub : arrLines) {
				sub.num = String.valueOf(i);
				i++;
			}
			if (curRec > arrLines.size())
				curRec = arrLines.size();
			loadGUI();
			break;
		case R.id.add_above_rec:
			String sideStartTime = RecEditFuncs.getStartTimeSubRip(arrLines
					.get(info.position).time);
			arrLines.add(info.position, new SubRecord("", sideStartTime
					+ Config.DIVIDER_SUB_RIP + sideStartTime, ""));
			for (SubRecord sub : arrLines) {
				sub.num = String.valueOf(i);
				i++;
			}
			loadGUI();
			break;
		case R.id.add_below_rec:
			String sideEndTime = RecEditFuncs.getEndTimeSubRip(arrLines
					.get(info.position).time);
			arrLines.add(info.position + 1, new SubRecord("", sideEndTime
					+ Config.DIVIDER_SUB_RIP + sideEndTime, ""));
			for (SubRecord sub : arrLines) {
				sub.num = String.valueOf(i);
				i++;
			}
			loadGUI();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onBackPressed() {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.editor_window, menu);
		globMenu = menu;
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.setGroupEnabled(R.id.group2, subFile != null);
		RecEditFuncs.setIsChanged(Config.isChanged, menu);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.sub_close:
			showSaveChangesDialog(CODE_SHOW_DLG_EXIT);
			break;
		case R.id.goto_rec:
			Intent intent2 = new Intent(this, WindowGotoRec.class);
			intent2.putExtra("maxRec", arrLines.size());
			intent2.putExtra("curRec", curRec);
			startActivityForResult(intent2, CODE_REQ_GOTO_REC);
			break;
		case R.id.sub_save:
			IOFiles.writeFileSubRip(subFile, arrLines);
			RecEditFuncs.setIsChanged(false, globMenu);
			break;
		case R.id.sub_save_as:
			Intent intent3 = new Intent(this, WindowSaveAs.class);
			intent3.putExtra("subFileAbs", subFile.getAbsolutePath());
			startActivityForResult(intent3, CODE_REQ_SAVE_AS);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showSaveChangesDialog(final int code) {
		if (!Config.isChanged) {
			switch (code) {
			case CODE_SHOW_DLG_EXIT:
				GlobalData.freeNotIntent();
				finish();
				break;
			}
			return;
		}
		;
		AlertDialog.Builder ad = new AlertDialog.Builder(WindowEditor.this);
		ad.setTitle(R.string.dialog_title_save_or_not);
		ad.setMessage(R.string.dialog_message_save_or_not);
		ad.setPositiveButton(R.string.save, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				IOFiles.writeFileSubRip(subFile, arrLines);
				RecEditFuncs.setIsChanged(false, globMenu);
				showSaveChangesDialog(code);
			}
		});
		ad.setNegativeButton(R.string.dont_save, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				RecEditFuncs.setIsChanged(false, globMenu);
				showSaveChangesDialog(code);
			}
		});
		ad.setNeutralButton(R.string.cancel, null);
		ad.setCancelable(true);
		ad.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case CODE_REQ_GOTO_REC:
			curRec = data.getIntExtra("recNum", 0);
			getListView().setSelection(curRec);
			curRec++;
			break;
		case CODE_REQ_EDIT_REC:
			RecEditFuncs.setIsChanged(true, globMenu);
			boolean done = data.getBooleanExtra("recModDone", true);
			int modRec = data.getIntExtra("recModPos", -10);
			arrLines.get(modRec).text = data.getStringExtra("recModText");
			arrLines.get(modRec).time = data.getStringExtra("recModTime");
			loadGUI();
			if (!done && curRec < arrLines.size()) {
				// doubled!
				getListView().setSelection(curRec);
				Intent intent = new Intent(this, WindowEditRec.class);
				intent.putExtra("recNum", arrLines.get(curRec).num);
				intent.putExtra("recTime", arrLines.get(curRec).time);
				intent.putExtra("recText", arrLines.get(curRec).text);
				intent.putExtra("curRec", curRec);
				curRec++;
				startActivityForResult(intent, CODE_REQ_EDIT_REC);
			} else
				getListView().setSelection(curRec - 1);
			break;
		case CODE_REQ_SAVE_AS:
			subFile = new File(data.getStringExtra("modAbsPath"));
			IOFiles.writeFileSubRip(subFile, arrLines);
			RecEditFuncs.setIsChanged(false, globMenu);
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void loadGUI() {
		setTitle(subFile.getName());
		ListView lv = getListView();
		RecCastomArrayAdapter adapter = new RecCastomArrayAdapter(this,
				arrLines);
		lv.setAdapter(adapter);
		getListView().setSelection(curRec);
	}
}
