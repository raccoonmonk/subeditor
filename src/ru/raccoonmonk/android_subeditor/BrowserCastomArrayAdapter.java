package ru.raccoonmonk.android_subeditor;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BrowserCastomArrayAdapter extends ArrayAdapter<File> {
	private Context context;
	private ArrayList<File> lems;

	public BrowserCastomArrayAdapter(Context context, ArrayList<File> lems) {
		super(context, R.id.label, lems);
		this.context = context;
		this.lems = lems;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.rec_browse, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		textView.setText(lems.get(position).getName());
		if (lems.get(position).isFile()) {
			String fileName = lems.get(position).getName();
			if (fileName.toLowerCase().endsWith(Config.SUFFIX_SRT)) {
				imageView.setImageResource(R.drawable.file_sub_srt);
			} else {
				imageView.setImageResource(R.drawable.file_unknown);
			}
		} else {
			imageView.setImageResource(R.drawable.folder);
		}

		return rowView;

	}

}
