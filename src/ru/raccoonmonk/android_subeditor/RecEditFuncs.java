package ru.raccoonmonk.android_subeditor;

import android.view.Menu;

public class RecEditFuncs {
	public static String getStartTimeSubRip(String time){
		//String ret = "00:00:00,000";
		int sidePos = time.indexOf(Config.DIVIDER_SUB_RIP, 0);
		//if (sidePos > 0)ret = time.substring(0, sidePos);
		return (sidePos > 0) ? time.substring(0, sidePos) : "00:00:00,000";
		//return ret;
	}
	public static String getEndTimeSubRip(String time){
		String ret = "00:00:00,000";
		int sidePos = time.indexOf(Config.DIVIDER_SUB_RIP, 0) + Config.DIVIDER_SUB_RIP.length();
		if (sidePos > 0)ret = time.substring(sidePos, time.length());
		return ret;
	}
	public static String getHHSubRip(String time){
		String ret = "00";
		time = time.substring(0, 2);
		if(time != null)ret = time;
		return ret;
	}
	public static String getMMSubRip(String time){
		String ret = "00";
		time = time.substring(3, 5);
		if(time != null)ret = time;
		return ret;
	}
	public static String getSSSubRip(String time){
		String ret = "00";
		time = time.substring(6, 8);
		if(time != null)ret = time;
		return ret;
	}public static String getMSSubRip(String time){
		String ret = "000";
		time = time.substring(9);
		if(time != null)ret = time;
		return ret;
	}
	public static void setIsChanged(boolean phrase, Menu menu){
		Config.isChanged = phrase;
		menu.findItem(R.id.sub_save).setEnabled(phrase);
	}
	public static String normTime(String str, int length){
		if(str.length() > length)str = str.substring(0, length);
		while(str.length()<length){
			str = "0" + str;
		}
		return str;
	}
}
