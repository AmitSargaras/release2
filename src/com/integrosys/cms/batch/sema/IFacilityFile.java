package com.integrosys.cms.batch.sema;

import java.math.BigDecimal;
import java.util.Date;

public interface IFacilityFile extends java.io.Serializable {

	public String getRecordType();

	public void setRecordType(String recordType);

	public String getNewIC();

	public void setNewIC(String newIC);

	public String getOldIC();

	public void setOldIC(String oldIC);

	public String getOthers();

	public void setOthers(String others);

	public Date getStatusDateNPL();

	public void setStatusDateNPL(Date statusDateNPL);

	public String getOutstandingSign();

	public void setOutstandingSign(String outstandingSign);

	public BigDecimal getOutstandingBalance();

	public void setOutstandingBalance(BigDecimal outstandingBalance);

	public String getUserCode3();

	public void setUserCode3(String userCode3);

	public String getStatusNPL();

	public void setStatusNPL(String statusNPL);

    public String getCustomerStatus();

    public void setCustomerStatus(String customerStatus);

    public long getTempID();

	public void setTempID(long tempID);

}
