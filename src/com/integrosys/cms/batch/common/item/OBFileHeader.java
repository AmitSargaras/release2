package com.integrosys.cms.batch.common.item;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.batch.common.filereader.CSVTokenizer;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: 15-Jun-2007 Time: 16:19:37 To
 * change this template use File | Settings | File Templates.
 */
public class OBFileHeader {
	private String line;

	private String messageId;

	private Date date;

	private String Source;

	private long totalRecords;

	private String messageStaus;

	public OBFileHeader(String firstLine, String type) {
		this.setLine(firstLine);
		if ("txt".equalsIgnoreCase(type)) {
			setFixLengthHeader();
		}
		if ("csv".equalsIgnoreCase(type)) {
			setCsvHeader();
		}
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getMessageStaus() {
		return messageStaus;
	}

	public void setMessageStaus(String messageStaus) {
		this.messageStaus = messageStaus;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	private void setFixLengthHeader() {

		if (line.substring(0, 5) != null) {
			this.setMessageId(line.substring(0, 5).trim());
		}
		if (line.substring(5, 13) != null) {
			this.setDate(DateUtil.convertDate(getDateString(line.substring(5, 13).trim())));
		}
		if (line.substring(13, 23) != null) {
			this.setSource(line.substring(13, 23).trim());
		}
		if (line.substring(23, 55) != null) {
			this.setTotalRecords(Long.parseLong(line.substring(23, 55).trim()));
		}
		if (line.substring(55, 57) != null) {
			this.setMessageStaus(line.substring(55, 57).trim());
		}
	}

	private void setCsvHeader() {
		int i = 1;
		CSVTokenizer st = new CSVTokenizer(line, ",");
		String token = null;
		while (st.hasMoreTokens()) {

			token = st.nextToken();
			if ((i == 1) && (token != null)) {
				this.setMessageId(token.trim());
			}
			if ((i == 2) && (token != null)) {
				this.setDate(DateUtil.convertDate(getDateString(token.trim())));
			}
			if ((i == 3) && (token != null)) {
				this.setSource(token.trim());
			}
			if ((i == 4) && (token != null)) {
				this.setTotalRecords(Long.parseLong(token.trim()));
			}
			if ((i == 5) && (token != null)) {
				this.setMessageStaus(token.trim());
			}

			i++;
		}
	}

	private String getDateString(String dtString) {
		if (("").equals(dtString) || (dtString.length() != 8)) {
			return "";
		}
		String formatDtStr = dtString.substring(6) + "-" + dtString.substring(4, 6) + "-" + dtString.substring(0, 4);
		return formatDtStr;
	}
}
