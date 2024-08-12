

package com.integrosys.cms.app.creditApproval.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBCreditApprovalTrxValue extends OBCMSTrxValue implements ICreditApprovalTrxValue {


	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBCreditApprovalTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Default constructor.
	 */
	public OBCreditApprovalTrxValue() {
	}

	private ICreditApproval actualEntry;

	private ICreditApproval stagingEntry;
	
	//Add By Govind S For File Upload Update on 16-jun-2011
    IFileMapperId FileMapperID;
    IFileMapperId stagingFileMapperID;
    //End: Add By Govind S For File Upload Update on 16-jun-2011

	/**
	 * @return the fileMapperID
	 */
	public IFileMapperId getFileMapperID() {
		return FileMapperID;
	}

	/**
	 * @param fileMapperID the fileMapperID to set
	 */
	public void setFileMapperID(IFileMapperId fileMapperID) {
		FileMapperID = fileMapperID;
	}

	/**
	 * @return the stagingFileMapperID
	 */
	public IFileMapperId getStagingFileMapperID() {
		return stagingFileMapperID;
	}

	/**
	 * @param stagingFileMapperID the stagingFileMapperID to set
	 */
	public void setStagingFileMapperID(IFileMapperId stagingFileMapperID) {
		this.stagingFileMapperID = stagingFileMapperID;
	}

	public ICreditApproval getCreditApproval() {
		return actualEntry;
	}

	public ICreditApproval getStagingCreditApproval() {
		return stagingEntry;
	}

	public void setCreditApproval(ICreditApproval actualEntry) {
		
		this.actualEntry = actualEntry;
	}

	public void setStagingCreditApproval(ICreditApproval stagingEntry) {
		
		this.stagingEntry = stagingEntry;
	}

}
