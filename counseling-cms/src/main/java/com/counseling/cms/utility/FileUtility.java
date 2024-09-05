package com.counseling.cms.utility;

import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtility {

	public static String createFileName(String extension) {
		Date now = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD");
		String today = simpleDateFormat.format(now);
		String randomNumber = "";
		for(int a = 0 ; a < 6 ; a++) {
			randomNumber += String.valueOf(Math.round(Math.random()*10));			
		}
		String newFileName = today + randomNumber + "." + extension;
		return newFileName;
	}
}
