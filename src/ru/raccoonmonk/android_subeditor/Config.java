package ru.raccoonmonk.android_subeditor;

public class Config {
	public static boolean isChanged = false;
	public static final int CODE_REQ_OPEN_FILE = 1;
	public static final int CODE_REQ_GOTO_REC = 2;
	public static final int CODE_REQ_EDIT_REC = 3;
	public static final int CODE_REQ_SAVE_AS = 4;
	public static final int CODE_REQ_TIME_SHIFT = 5;
	
	public static final int CODE_SHOW_DLG_OPEN = 1;
	public static final int CODE_SHOW_DLG_EXIT = 2;
	
	public static final String SUFFIX_SRT = ".srt";
	public static final String DIVIDER_SUB_RIP = " --> ";
	
	public static final long MAGIC_3_HOURS = 7200000;
	public static final String TIME_FORMAT = "HH:mm:ss,SSS";
	
}
