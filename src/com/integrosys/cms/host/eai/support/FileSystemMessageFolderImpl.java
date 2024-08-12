package com.integrosys.cms.host.eai.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.integrosys.cms.host.eai.FileSystemAccessException;

/**
 * Implementation of {@link IMessageFolder} using file storage to keep the XML
 * content from the EAI interface.
 * 
 * @author Chong Jun Yong
 * 
 */
public class FileSystemMessageFolderImpl extends AbstractMessageFolder {

	private static final String UNDERSCORE_SEPARATOR = "_";

	private static final String XML_EXTENSION = ".xml";

	private String messageFolder;

	private String messageFileDateFormat;

	public void setMessageFileDateFormat(String messageFileDateFormat) {
		this.messageFileDateFormat = messageFileDateFormat;
	}

	public String getMessageFileDateFormat() {
		return messageFileDateFormat;
	}

	public void setMessageFolder(String messageFolder) {
		this.messageFolder = messageFolder;
	}

	public String getMessageFolder() {
		return messageFolder;
	}

	public Object popMessage() {
		throw new IllegalStateException("pop up last message is not supported by file storage message folder, "
				+ "please use 'popMessageByMsgId' instead.");
	}

	public Object popMessageByMsgId(String msgId) {
		String filename = (String) msgIdHolderMap.get(msgId);

		StringBuffer msgBuf = new StringBuffer();

		try {
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String line = bufReader.readLine();
			while (line != null) {
				msgBuf.append(line);
				line = bufReader.readLine();
			}
		}
		catch (FileNotFoundException e) {
			throw new FileSystemAccessException("failed to find the file name [" + getMessageFolder() + File.separator
					+ filename + "]", e);
		}
		catch (IOException e) {
			throw new FileSystemAccessException("failed to read the file name [" + getMessageFolder() + File.separator
					+ filename + "], is it readable ?", e);
		}

		msgIdHolderMap.remove(msgId);

		MessageHolder messageHolder = new MessageHolder(msgBuf.toString(), msgId, filename);
		return messageHolder;
	}

	public String putMessage(Object message) {
		String xmlMessage = (String) message;

		String msgId = getNextMessageId();

		String xmlMsgType = EAIHeaderHelper.getHeaderValue(xmlMessage, IEAIHeaderConstant.MESSAGE_TYPE);
		String xmlMsgId = EAIHeaderHelper.getHeaderValue(xmlMessage, IEAIHeaderConstant.MESSAGE_ID);
		String xmlMsgRefNum = EAIHeaderHelper.getHeaderValue(xmlMessage, IEAIHeaderConstant.MESSAGE_REF_NUM);

		Date todayDate = new Date();

		StringBuffer filenameBuf = new StringBuffer();
		filenameBuf.append(xmlMsgType).append(UNDERSCORE_SEPARATOR);
		filenameBuf.append(xmlMsgId).append(UNDERSCORE_SEPARATOR);
		filenameBuf.append(xmlMsgRefNum).append(UNDERSCORE_SEPARATOR);
		filenameBuf.append(DateFormatUtils.format(todayDate, getMessageFileDateFormat()));
		filenameBuf.append(XML_EXTENSION);

		String filename = filenameBuf.toString();

		try {
			File messageFolder = new File(getMessageFolder() + File.separator
					+ DateFormatUtils.format(todayDate, "yyyyMMdd"));
			if (!messageFolder.exists()) {
				messageFolder.mkdir();
			}

			String filepath = messageFolder.getPath() + File.separator + filename;

			FileOutputStream fos = new FileOutputStream(filepath);
			PrintStream ps = new PrintStream(fos);
			ps.print(xmlMessage);
			fos.close();

			msgIdHolderMap.put(msgId, filepath);
		}
		catch (FileNotFoundException e) {
			throw new FileSystemAccessException("failed to find the file name [" + getMessageFolder() + File.separator
					+ filename + "] to be printed to, is the folder created ?", e);
		}
		catch (IOException e) {
			throw new FileSystemAccessException("failed to close the stream for file [" + getMessageFolder()
					+ File.separator + filename + "].", e);
		}

		return msgId;
	}
}
