package com.integrosys.cms.batch.common.item;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.batch.common.filereader.CSVTokenizer;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: 15-Jun-2007 Time: 16:19:48 To
 * change this template use File | Settings | File Templates.
 */
public class OBFileFooter {
	String line;

	private Date date;

	private String source;

	private long totalRecords;

	private long totalHash;

	public OBFileFooter(String line, String type) {
		this.setLine(line);
		if ("txt".equalsIgnoreCase(type)) {
			setFixLengthFooter();
		}
		if ("csv".equalsIgnoreCase(type)) {
			setCsvFooter();
		}
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public long getTotalHash() {
		return totalHash;
	}

	public void setTotalHash(long totalHash) {
		this.totalHash = totalHash;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	private void setFixLengthFooter() {

		if (line.substring(5, 13) != null) {
			this.setDate(DateUtil.convertDate(getDateString(line.substring(5, 13).trim())));
		}
		if (line.substring(13, 23) != null) {
			this.setSource(line.substring(13, 23).trim());
		}
		if (line.substring(23, 55) != null) {
			this.setTotalRecords(Long.parseLong(line.substring(23, 55).trim()));
		}
		if (line.substring(55, 87) != null) {
			this.setTotalHash(Long.parseLong(line.substring(55, 87).trim()));
		}
	}

	private void setCsvFooter() {

		int i = 1;
		CSVTokenizer st = new CSVTokenizer(line, ",");
		String token = null;
		while (st.hasMoreTokens()) {

			token = st.nextToken();
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
				this.setTotalHash(Long.parseLong(token.trim()));
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
