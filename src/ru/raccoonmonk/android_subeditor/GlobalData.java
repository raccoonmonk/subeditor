package ru.raccoonmonk.android_subeditor;

import java.io.File;

public class GlobalData {
	private static File fileSub = null;
	public static File getFileSub() {
		return fileSub;
	}
	public static void setFileSub(File fileSub) {
		GlobalData.fileSub = fileSub;
	}
	public static void freeNotIntent(){
		GlobalData.fileSub = null;
	}
}