package ru.raccoonmonk.android_subeditor;

import static ru.raccoonmonk.android_subeditor.Config.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WindowMain extends Activity implements OnClickListener {
	private Button btnOpenFile = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.window_main);
		btnOpenFile = (Button) findViewById(R.id.btn_open_file);
		btnOpenFile.setOnClickListener(this);
		openEditor();
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_open_file:
			Intent intent = new Intent(WindowMain.this, WindowBrowseOpen.class);
			startActivityForResult(intent, CODE_REQ_OPEN_FILE);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case CODE_REQ_OPEN_FILE:
			openEditor();
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void openEditor() {
		if (GlobalData.getFileSub() != null)
			startActivity(new Intent(WindowMain.this, WindowEditor.class));
	}
}
