package com.integrosys.cms.host.eai.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class FileLogger {

	public static final String LOG_BASE_DIR = PropertyManager.getValue("eai.log.base.dir");

	static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");

	static final String FILE_SEPARATOR = File.separator;

	static final String fileExtension = ".xml";

	static final String THIS_CLASS = "com.integrosys.cms.host.mq.log.FileLogger";

	// change to 99 .
	static final int MAX_FILENAME = 99;

	private final static String CRLF = "\r\n";

	public FileLogger() {

		File file = new File(LOG_BASE_DIR);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	private static String prepareFolders() {
		String dateFormat = formatDate.format(new Date(System.currentTimeMillis()));
		File messageFolder = new File(LOG_BASE_DIR + FILE_SEPARATOR + dateFormat);
		if (!messageFolder.exists()) {
			messageFolder.mkdirs();
		}
		return messageFolder.toString();
	}

	public static void logMessage(String messageReferenceNumber, String content) {
		try {

			String messageFolder = prepareFolders();

			File messageFile = new File(messageFolder + FILE_SEPARATOR + messageReferenceNumber + "_0" + fileExtension);

			// Always Create as new file
			for (int i = 0; (i < MAX_FILENAME) && (messageFile.exists()); i++) {
				messageFile = new File(messageFolder + FILE_SEPARATOR + messageReferenceNumber + "_" + i
						+ fileExtension);
			}

			// if (!messageFile.exists())
			// {
			boolean createNewFile = messageFile.createNewFile();
			  if(createNewFile==false) {
			System.out.println("Error while creating new file:"+messageFile.getPath());	
		      }
			// }

			FileOutputStream fos = new FileOutputStream(messageFile.toString(), true);
			fos.write(CRLF.getBytes());
			fos.write(content.getBytes());

		}
		catch (Exception e) {
			DefaultLogger.debug(THIS_CLASS, "Error in logging EAI message - " + e.getMessage());
		}

	}

	public static void logException(String messageReferenceNumber, Exception exception) {
		try {

			String messageFolder = prepareFolders();

			File messageFile = new File(messageFolder + FILE_SEPARATOR + messageReferenceNumber + "_0" + ".err");

			// Always Create as new file
			for (int i = 0; (i < MAX_FILENAME) && (messageFile.exists()); i++) {
				messageFile = new File(messageFolder + FILE_SEPARATOR + messageReferenceNumber + "_" + i + ".err");
			}

			// if (!messageFile.exists())
			// {
			boolean createNewFile = messageFile.createNewFile();
			  if(createNewFile==false) {
			System.out.println("Error while creating new file:"+messageFile.getPath());	
		      }
			// }

			PrintStream ps = new PrintStream(new FileOutputStream(messageFile.toString(), true));
			// FileOutputStream fos = new FileOutputStream(messageFile);
			ps.print(CRLF + "Exception:");
			ps.print(CRLF);
			exception.printStackTrace(ps);

		}
		catch (Exception e) {
			DefaultLogger.debug(THIS_CLASS, "Error in logging EAI message - " + e.getMessage());
		}

	}

}
