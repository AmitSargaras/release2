package com.integrosys.cms.host.eai.support;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * <p>
 * A very simple utility to convert from string represented date to a date
 * object, or vice-versa.
 * <p>
 * TODO: this should be an abstract class, provide static methods for doing the
 * conversion
 * @author marvin
 * @author Chong Jun Yong
 */
public class MessageDate {
	private static final String DATE_FORMAT = "yyyyMMdd";

	private static final String DATETIME_FORMAT = "yyyyMMddHHmmss";

	private static final String DATETIME_LONG_FORMAT = "dd-MMM-yyyy HH:mm:ss";

	/** Singleton instance of MessageDate object. */
	private static final MessageDate instance = new MessageDate();

	private MessageDate() {
	}

	public static MessageDate getInstance() {
		return instance;
	}

	public Date getDate(String dateString) {
		Date result = null;

		if ((dateString == null) || dateString.trim().equals("")) {
			return null;
		}

		try {
			result = DateUtils.parseDate(dateString,
					new String[] { DATE_FORMAT, DATETIME_FORMAT, DATETIME_LONG_FORMAT });
		}
		catch (ParseException ex) {
			DefaultLogger.error(this, "failed to parse date value [" + dateString + "] using available format ["
					+ ArrayUtils.toString(new String[] { DATE_FORMAT, DATETIME_FORMAT, DATETIME_LONG_FORMAT })
					+ "], return 'null'", ex);
			return result;
		}

		return result;
	}

	public String getString(Date dateObj) {
		return dateObj != null ? DateFormatUtils.format(dateObj, DATE_FORMAT) : null;
	}
}
