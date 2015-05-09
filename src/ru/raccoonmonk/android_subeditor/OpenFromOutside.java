package ru.raccoonmonk.android_subeditor;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class OpenFromOutside extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		GlobalData.setFileSub(new File(getIntent().getData().getPath()));
		startActivity(new Intent(this, WindowMain.class));
		finish();
		super.onCreate(savedInstanceState);
	}
}
