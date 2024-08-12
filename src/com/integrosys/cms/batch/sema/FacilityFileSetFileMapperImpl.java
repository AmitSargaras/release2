package com.integrosys.cms.batch.sema;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.file.mapping.FieldSet;

import com.integrosys.cms.batch.common.mapping.AbstractFieldSetMapper;

public class FacilityFileSetFileMapperImpl extends AbstractFieldSetMapper {

	private int outstandingBalanceDecimal;

	/**
	 * to set the decimal of outstanding balance, so that decimal point can be
	 * placed correctly before parse it to decimal osbject
	 * 
	 * @param outstandingBalanceDecimal
	 */
	public void setOutstandingBalanceDecimal(int outstandingBalanceDecimal) {
		this.outstandingBalanceDecimal = outstandingBalanceDecimal;
	}

	public int getOutstandingBalanceDecimal() {
		return outstandingBalanceDecimal;
	}

	public Object doMapLine(FieldSet fs) {
		IFacilityFile semaFileItem = new OBFacilityFile();
		semaFileItem.setRecordType(fs.readString(0));
		semaFileItem.setNewIC(fs.readString(1));
		semaFileItem.setOldIC(fs.readString(2));
		semaFileItem.setOthers(fs.readString(3));
		if (StringUtils.isNotBlank(fs.readString(4))) {
			semaFileItem.setStatusDateNPL(fs.readDate(4, getDateFormat()));
		}
		semaFileItem.setOutstandingSign(fs.readString(5));

		StringBuffer outstandingBalanceRawStringBuffer = new StringBuffer(fs.readString(6));
		if (getOutstandingBalanceDecimal() > 0) {
			outstandingBalanceRawStringBuffer.insert(fs.readString(6).length() - getOutstandingBalanceDecimal(), '.');
		}
		BigDecimal outstandingBalance = new BigDecimal(outstandingBalanceRawStringBuffer.toString());

		semaFileItem.setOutstandingBalance(outstandingBalance);
        semaFileItem.setCustomerStatus(fs.readString(7));
        semaFileItem.setUserCode3(fs.readString(8));
		semaFileItem.setStatusNPL(fs.readString(9));

		return semaFileItem;
	}
}
