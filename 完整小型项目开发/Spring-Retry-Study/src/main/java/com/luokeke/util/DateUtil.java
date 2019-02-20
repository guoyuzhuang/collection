package com.luokeke.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static final SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getTime() {
		return s.format(new Date());
	}
}
