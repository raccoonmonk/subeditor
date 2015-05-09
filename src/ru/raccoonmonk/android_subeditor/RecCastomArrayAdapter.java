package ru.raccoonmonk.android_subeditor;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

	public class RecCastomArrayAdapter extends ArrayAdapter<SubRecord> {
		private Context context;
		private ArrayList<SubRecord> lems;
		public RecCastomArrayAdapter(Context context,
				ArrayList<SubRecord> lems) {
			super(context, R.id.text, lems);
			this.context = context;
			this.lems = lems;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View rowView = inflater.inflate(R.layout.rec_sub, parent, false);
	        TextView tvNum = (TextView) rowView.findViewById(R.id.num);
	        TextView tvTime = (TextView) rowView.findViewById(R.id.time);
	        TextView tvText = (TextView) rowView.findViewById(R.id.text);
	        tvNum.setText(lems.get(position).num);
	        tvTime.setText(lems.get(position).time);
	        tvText.setText(lems.get(position).text);

	        return rowView;

		}

		
		
	}
