package ru.raccoonmonk.android_subeditor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import static ru.raccoonmonk.android_subeditor.RecEditFuncs.*;

public class WindowEditRec extends Activity implements OnClickListener,
		OnFocusChangeListener {
	int curPos = -10;
	String recText = "";
	EditText etText = null;

	EditText edt_hh = null;
	EditText edt_mm = null;
	EditText edt_ss = null;
	EditText edt_ms = null;
	EditText edt_hh_end = null;
	EditText edt_mm_end = null;
	EditText edt_ss_end = null;
	EditText edt_ms_end = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.window_edit_rec);
		Intent intent = getIntent();
		String recNum = intent.getStringExtra("recNum");
		String recTime = intent.getStringExtra("recTime");
		recText = intent.getStringExtra("recText");
		curPos = intent.getIntExtra("curRec", -10);
		TextView tvNum = (TextView) findViewById(R.id.edit_tvnum);
		TextView tvTime = (TextView) findViewById(R.id.edit_tvtime);
		etText = (EditText) findViewById(R.id.edit_ettext);
		tvNum.setText(recNum);
		tvNum.setTextColor(getResources().getColor(R.color.font_color));
		tvTime.setText(recTime);
		etText.setText(recText);
		etText.selectAll();
		Button btnCancel = (Button) findViewById(R.id.edit_btn_cancel);
		Button btnApply = (Button) findViewById(R.id.edit_btn_apply);
		Button btnNext = (Button) findViewById(R.id.edit_btn_next);
		btnCancel.setOnClickListener(this);
		btnApply.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		edt_hh = (EditText) findViewById(R.id.edt_hh);
		edt_mm = (EditText) findViewById(R.id.edt_mm);
		edt_ss = (EditText) findViewById(R.id.edt_ss);
		edt_ms = (EditText) findViewById(R.id.edt_ms);
		edt_hh.setText(getHHSubRip(getStartTimeSubRip(recTime)));
		edt_mm.setText(getMMSubRip(getStartTimeSubRip(recTime)));
		edt_ss.setText(getSSSubRip(getStartTimeSubRip(recTime)));
		edt_ms.setText(getMSSubRip(getStartTimeSubRip(recTime)));
		edt_hh.setOnFocusChangeListener(this);
		edt_mm.setOnFocusChangeListener(this);
		edt_ss.setOnFocusChangeListener(this);
		edt_ms.setOnFocusChangeListener(this);
		edt_hh_end = (EditText) findViewById(R.id.edt_hh_end);
		edt_mm_end = (EditText) findViewById(R.id.edt_mm_end);
		edt_ss_end = (EditText) findViewById(R.id.edt_ss_end);
		edt_ms_end = (EditText) findViewById(R.id.edt_ms_end);
		edt_hh_end.setText(getHHSubRip(getEndTimeSubRip(recTime)));
		edt_mm_end.setText(getMMSubRip(getEndTimeSubRip(recTime)));
		edt_ss_end.setText(getSSSubRip(getEndTimeSubRip(recTime)));
		edt_ms_end.setText(getMSSubRip(getEndTimeSubRip(recTime)));
		edt_hh_end.setOnFocusChangeListener(this);
		edt_mm_end.setOnFocusChangeListener(this);
		edt_ss_end.setOnFocusChangeListener(this);
		edt_ms_end.setOnFocusChangeListener(this);

		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edit_btn_cancel:
			finish();
			break;

		case R.id.edit_btn_apply:
			CloseOk(true);
			break;
		case R.id.edit_btn_next:
			CloseOk(false);
			break;
		default:
			break;
		}
	}

	private void CloseOk(boolean done) {
		Intent intent = getIntent();
		intent.putExtra("recModText", etText.getText().toString());
		intent.putExtra(
				"recModTime",
				normTime(edt_hh.getText().toString(), 2) + ":"
						+ normTime(edt_mm.getText().toString(), 2) + ":"
						+ normTime(edt_ss.getText().toString(), 2) + ","
						+ normTime(edt_ms.getText().toString(), 3)
						+ Config.DIVIDER_SUB_RIP
						+ normTime(edt_hh_end.getText().toString(), 2) + ":"
						+ normTime(edt_mm_end.getText().toString(), 2) + ":"
						+ normTime(edt_ss_end.getText().toString(), 2) + ","
						+ normTime(edt_ms_end.getText().toString(), 3));
		intent.putExtra("recModPos", curPos);
		intent.putExtra("recModDone", done);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			EditText edt = (EditText) v;
			edt.selectAll();
		}

	}

}
