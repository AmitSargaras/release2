package com.integrosys.cms.batch.common.item;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: 15-Jun-2007 Time: 18:46:44 To
 * change this template use File | Settings | File Templates.
 */
public class OBRecordKey {
	String fileType;

	String keyName;

	int startPos = ICMSConstant.INT_INVALID_VALUE;

	int endPos = ICMSConstant.INT_INVALID_VALUE;

	int columnNo = ICMSConstant.INT_INVALID_VALUE;

	public OBRecordKey(String fileType, String keyName, int startPos, int endPos) {
		this.setFileType(fileType);
		this.setKeyName(keyName);
		this.setStartPos(startPos);
		this.setEndPos(endPos);
	}

	public OBRecordKey(String fileType, String keyName, int columnNo) {
		this.setFileType(fileType);
		this.setKeyName(keyName);
		this.setColumnNo(columnNo);
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public int getStartPos() {
		return startPos;
	}

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	public int getEndPos() {
		return endPos;
	}

	public void setEndPos(int endPos) {
		this.endPos = endPos;
	}

	public int getColumnNo() {
		return columnNo;
	}

	public void setColumnNo(int columnNo) {
		this.columnNo = columnNo;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}
