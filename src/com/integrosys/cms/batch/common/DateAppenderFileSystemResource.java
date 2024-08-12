package com.integrosys.cms.batch.common;

import org.springframework.core.io.FileSystemResource;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateAppenderFileSystemResource extends FileSystemResource{
	private static final String FILE_TYPE_SEPERATOR = ".";
	
	public DateAppenderFileSystemResource(String path, String dateFormat) {
        super(generateFilePath(path, dateFormat));
	}
	
	private static String generateFilePath(String path, String dateFormat) {
		int seperatorIndex = -1;
		if (path != null)
			seperatorIndex = path.indexOf(FILE_TYPE_SEPERATOR);
		
		String fileType = "", fileName = "";		
		if (seperatorIndex != -1) {
			fileType = path.substring(seperatorIndex+1);
			fileName = path.substring(0, seperatorIndex);
		}
		
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String strDateFormat = sdf.format(cal.getTime());		
        
        return fileName+strDateFormat+FILE_TYPE_SEPERATOR+fileType;		
	}

}
