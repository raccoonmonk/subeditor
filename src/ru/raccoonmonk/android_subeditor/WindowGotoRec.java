package ru.raccoonmonk.android_subeditor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class WindowGotoRec extends Activity implements OnClickListener {
	private int curNum = 0;
	private int maxNum = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.window_goto_rec);
		Intent intent = getIntent();
		
		maxNum = intent.getIntExtra("maxRec", 0);
		curNum = intent.getIntExtra("curRec", 0);
		final TextView tvPos = (TextView) findViewById(R.id.tvPos);
		tvPos.setText(curNum+"/"+maxNum);
		SeekBar sb = (SeekBar) findViewById(R.id.sbPos);
		sb.setMax(maxNum);
		sb.setProgress(curNum);
		Button btnCancel = (Button)findViewById(R.id.goto_btn_cancel);
		Button btnGo = (Button)findViewById(R.id.goto_btn_go);
		btnCancel.setOnClickListener(this);
		btnGo.setOnClickListener(this);
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				curNum = progress;
				tvPos.setText(curNum+"/"+maxNum);
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
		});
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.goto_btn_cancel:
			finish();
			break;
		case R.id.goto_btn_go:
			Intent intent = getIntent();
			if (curNum < 1)curNum = 1;
			intent.putExtra("recNum", curNum - 1);
			setResult(RESULT_OK, intent);
			finish();
			break;	

		default:
			break;
		}
		
	}
}
