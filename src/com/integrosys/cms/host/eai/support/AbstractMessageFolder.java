package com.integrosys.cms.host.eai.support;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * <p>
 * Abstract implementation of {@link IMessageFolder}, provide the storage of the
 * message object backed by the message id generated internally.
 * 
 * <p>
 * Each message will be assigned an Id internally, in the format of
 * yyyyMMdd000000000. <b>Example, 20080825000000998</b>. So that message can be
 * found providing the correct msg id.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class AbstractMessageFolder implements IMessageFolder {

	private int msgIdInternalId = 0;

	protected Map msgIdHolderMap = Collections.synchronizedMap(new LinkedHashMap());

	/** date format to be used to format the date which will be part of msg id */
	protected static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";

	/** number of padding of characters (0) */
	protected static final int DEFAULT_ZERO_PADDING_NUM = 9;

	/** default padding character */
	protected static final String DEFAULT_PADDING_CHARACTER = "0";

	public Object popMessage() {
		return null;
	}

	public abstract Object popMessageByMsgId(String msgId);

	public abstract String putMessage(Object message);

	/**
	 * Retrieve the next message id for the message object passed in.
	 * 
	 * @return the next message id used to key the message object
	 */
	protected String getNextMessageId() {
		String msgId = String.valueOf(++msgIdInternalId);

		String todayDate = DateFormatUtils.format(new Date(), DEFAULT_DATE_FORMAT);
		String paddedMsgId = StringUtils.repeat(DEFAULT_PADDING_CHARACTER, DEFAULT_ZERO_PADDING_NUM - msgId.length());

		StringBuffer msgIdBuffer = new StringBuffer();
		msgIdBuffer.append(todayDate);
		msgIdBuffer.append(paddedMsgId);
		msgIdBuffer.append(msgId);

		return msgIdBuffer.toString();
	}
}
