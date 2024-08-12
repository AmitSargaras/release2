package com.integrosys.cms.host.eai.support;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIHeader;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.Message;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class EAIHeaderHelper implements IEAIHeaderConstant {

	public static final String MESSAGE_CONFIG_PREFIX_1 = "integrosys.eai.eaihelper.";

	public static final String MESSAGE_CONFIG_PREFIX_2 = ".eaiheader.";

	public static final String EAI_DATE_FORMAT = "yyyyMMdd";

	public static final String EAI_TIME_FORMAT = "hhmmSSSS";

	public static EAIHeader prepareHeader(String messageType, String messageId, Map headerMap) throws Exception {
		if (null == messageId) {
			throw new Exception("Message Id is null!");
		}
		if (null == messageType) {
			throw new Exception("Message Type is null!");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(EAI_DATE_FORMAT);
		String date = dateFormat.format(new Date());
		EAIHeader eaiHeader = new EAIHeader();
		String fieldPrefix = MESSAGE_CONFIG_PREFIX_1 + messageType.toLowerCase() + "." + messageId
				+ MESSAGE_CONFIG_PREFIX_2;

		eaiHeader.setMessageId(PropertyManager.getValue(fieldPrefix + EAIHDR_MESSAGE_NUMBER, ""));

		eaiHeader.setSource(getHeaderKeyValue(fieldPrefix, EAIHDR_SOURCE, headerMap));

		if (null == headerMap.get(IEAIHeaderConstant.EAIHDR_MESSAGE_REF_NO)) {

			String sequence = new SequenceManager().getSeqNum(ICMSConstant.SEQUENCE_EAI_HEADER_REF_NO, false);
			sequence = new EAIReferenceNumberFormatter().formatSeq(sequence);
			eaiHeader.setMessageRefNum(PropertyManager.getValue(EAIHDR_MESSAGE_REF_NO_SERVER_KEY) + date + sequence);
		}
		else {
			// Reply back with the reference number
			eaiHeader.setMessageRefNum((String) headerMap.get(IEAIHeaderConstant.EAIHDR_MESSAGE_REF_NO));
		}

		return eaiHeader;
	}

	/**
	 * Helper method
	 */
	public static String getHeaderValue(String msg, String tag) {
		String invalid = Long.toString(ICMSConstant.LONG_INVALID_VALUE);
		String value = "";
		try {
			// AT: For simplified empty tag .
			if (msg.indexOf("<" + tag + "/>") > -1) {
				return "";
			}

			String newTag = "<" + tag.toUpperCase() + ">";
			int start = msg.toUpperCase().indexOf(newTag);
			start = start + newTag.length();

			String temp = msg.substring(start, msg.length());
			int end = temp.toUpperCase().indexOf(tag.toUpperCase());
			end = end - 2; // move back 1 for </ symbol

			value = temp.substring(0, end);
		}
		catch (Exception e) {
			value = invalid;
		}
		return value;
	}

	/**
	 * Gets value from preconfigured properties for the message, if not found,
	 * get from headerMap
	 * @param fieldPrefix
	 * @param key
	 * @param headerMap
	 * @return
	 */
	private static String getHeaderKeyValue(String fieldPrefix, String key, Map headerMap) {
		String value = "";
		if (headerMap != null) {
			value = (String) headerMap.get(key);
			if ((value == null) || "".equals(value)) {
				value = PropertyManager.getValue(fieldPrefix + key, "");
			}
		}
		return value;
	}

	public static String getSystemSourceId(EAIMessage msg) {
		return msg.getMsgHeader().getSource();
	}

	public static String getSystemSourceId(Message msg) {
		return msg.getMsgHeader().getSource();
	}

}
