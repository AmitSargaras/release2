package com.integrosys.cms.batch.common.item;

import java.util.ArrayList;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.batch.common.filereader.CSVTokenizer;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: 15-Jun-2007 Time: 16:56:40 To
 * change this template use File | Settings | File Templates.
 */
public class OBFileBody {

	private OBRecordKey obKey;

	private long totalHashNumber = 0;

	private long hashTotal = 0;

	private long totalRecord = 0;

	HashTotalHelper hHelper = new HashTotalHelper();

	ArrayList keyList = new ArrayList();

	public OBFileBody(OBRecordKey key) {
		setObKey(key);
	}

	public OBRecordKey getObKey() {
		return obKey;
	}

	public void setObKey(OBRecordKey obKey) {
		this.obKey = obKey;
	}

	public ArrayList getKeyList() {
		return keyList;
	}

	public void setKeyList(ArrayList keyList) {
		this.keyList = keyList;
	}

	public long getTotalHashNumber() {
		return totalHashNumber;
	}

	public void setTotalHashNumber(long totalHashNumber) {
		this.totalHashNumber = totalHashNumber;
	}

	public long getHashTotal() {
		return hHelper.getHashTotal(this.getTotalHashNumber());
	}

	public void setHashTotal(long hashTotal) {
		this.hashTotal = hashTotal;
	}

	public long getTotalRecord() {
		return keyList.size();
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}

	public void addLine(String line) {
		String key = "";
		if ((obKey != null) && "txt".equalsIgnoreCase(obKey.getFileType())) {
			key = line.substring(obKey.getStartPos(), obKey.getEndPos()).trim();
		}

		if ((obKey != null) && "csv".equalsIgnoreCase(obKey.getFileType())) {
			int i = 0;
			CSVTokenizer st = new CSVTokenizer(line, ",");
			String token = null;
			while (st.hasMoreTokens()) {
				i = i + 1;
				token = st.nextToken();
				if (i == obKey.getColumnNo()) {
					key = token.trim();
					break;
				}
			}
		}
		keyList.add(key);
		long lineHashNumber = hHelper.getHashNumber(key);
		this.setTotalHashNumber(this.getTotalHashNumber() + lineHashNumber);

	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
