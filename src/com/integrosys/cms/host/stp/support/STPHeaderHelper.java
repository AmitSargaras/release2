package com.integrosys.cms.host.stp.support;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.support.EAIReferenceNumberFormatter;
import com.integrosys.cms.host.stp.STPHeader;
import com.integrosys.cms.host.stp.STPMessage;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.trade.TradeMessage;
import com.integrosys.cms.ui.common.UIUtil;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class STPHeaderHelper implements ISTPHeaderConstant {

/*	public static final String MESSAGE_CONFIG_PREFIX_1 = "integrosys.stp.stphelper.";

	public static final String MESSAGE_CONFIG_PREFIX_2 = ".stpheader.";*/

	public static final String STP_DATE_FORMAT = "yyyyMMdd";

	public static final String STP_TIME_FORMAT = "hhmmSSSS";

	public static STPHeader prepareHeader(String messageType, String messageId, Map headerMap)throws Exception {
		if (null == messageId) {
			throw new Exception("Message Id is null!");
		}
		if (null == messageType) {
			throw new Exception("Message Type is null!");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(STP_DATE_FORMAT);
		String date = dateFormat.format(new Date());
		STPHeader stpHeader = new STPHeader();

        String fieldPrefix = "";

		stpHeader.setMessageId(getHeaderKeyValue(fieldPrefix,STPHDR_MESSAGE_NUMBER, headerMap));
		stpHeader.setSource(getHeaderKeyValue(fieldPrefix, STPHDR_SOURCE, headerMap));
        stpHeader.setMessageType(getHeaderKeyValue(fieldPrefix, MESSAGE_TYPE, headerMap));
        stpHeader.setPublishDate(getHeaderKeyValue(fieldPrefix, PUBLISH_DATE, headerMap));
        stpHeader.setPublishType(getHeaderKeyValue(fieldPrefix, PUBLISH_TYPE, headerMap));

		if (null == headerMap.get(ISTPHeaderConstant.STPHDR_MESSAGE_REF_NO)) {
            try{
			String sequence = new SequenceManager().getSeqNum(ICMSConstant.SEQUENCE_EAI_HEADER_REF_NO, false);
			sequence = new EAIReferenceNumberFormatter().formatSeq(sequence);
			stpHeader.setMessageRefNum(PropertyManager.getValue(STPHDR_MESSAGE_REF_NO_SERVER_KEY) + date + sequence);
            }catch(Exception e){
                throw new Exception("Fail to construct header!");
            }
		}

		return stpHeader;
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

	public static String getSystemSourceId(STPMessage msg) {
		return msg.getMsgHeader().getSource();
	}

	public static String getSystemSourceId(TradeMessage msg) {
		return msg.getMsgHeader().getSource();
	}

}