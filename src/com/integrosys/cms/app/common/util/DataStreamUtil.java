package com.integrosys.cms.app.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/common/util/DataStreamUtil.java,v 1.1 2005/11/12 14:48:13 wltan Exp $
 */

/**
 * Description: Utility class for converting streams into byte arrays
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/11/12 14:48:13 $ Tag: $Name: $
 */
public class DataStreamUtil {
	/**
	 * converts an InputStream to a byte array
	 * @param is
	 * @return a byte array
	 */
	public static byte[] convertInputStreamToByteArray(InputStream is) {

		DefaultLogger.info(DataStreamUtil.class.getName(), "Entering method convertInputStreamToByteArray");

		if (is == null) {
			return null;
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		BufferedInputStream bis = new BufferedInputStream(is);

		try {
			int ch;
			while ((ch = bis.read()) != -1) {
				os.write(ch);
			}
			return os.toByteArray();

		}
		catch (IOException IOExp) {

			DefaultLogger.error(DataStreamUtil.class.getName(), "convertInputStreamToByteArray"
					+ "Error converting InputStream to ByteArray", IOExp);
			return null;
		}
		finally {
			DefaultLogger.info(DataStreamUtil.class.getName(), "Exiting method convertInputStreamToByteArray");
			try {
				os.close();
				bis.close();
			}
			catch (IOException IOExp) {
				DefaultLogger.error(DataStreamUtil.class.getName(), "In finally of convertInputStreamToByteArray, "
						+ "error closing streams" + IOExp);
			}
		}
	}

	/**
	 * converts an InputStream to a char array
	 * 
	 * @param is
	 * @return
	 */
	public static char[] convertInputStreamToCharArray(InputStream is) {

		DefaultLogger.info(DataStreamUtil.class.getName(), "Entering method convertInputStreamToCharArray");

		if (is == null) {
			return null;
		}

		CharArrayWriter writer = new CharArrayWriter();
		BufferedInputStream bis = new BufferedInputStream(is);

		try {
			int ch;
			while ((ch = bis.read()) != -1) {
				writer.write(ch);
			}
			return writer.toCharArray();

		}
		catch (IOException IOExp) {
			DefaultLogger.error(DataStreamUtil.class.getName(), "convertInputStreamToCharArray"
					+ "Error converting InputStream to CharArray", IOExp);
			return null;
		}
		finally {
			DefaultLogger.info(DataStreamUtil.class.getName(), "Exiting method convertInputStreamToCharArray");
			try {
				writer.close();
				bis.close();
			}
			catch (IOException IOExp) {
				DefaultLogger.error(DataStreamUtil.class.getName(), "In finally of convertInputStreamToCharArray, "
						+ "error closing streams" + IOExp);
			}
		}
	}

}
