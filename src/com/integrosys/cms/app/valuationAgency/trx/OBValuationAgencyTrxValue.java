package com.integrosys.cms.app.valuationAgency.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;

/**
 * @author rajib.aich Checker approve Operation to approve update made by maker
 */
public class OBValuationAgencyTrxValue extends OBCMSTrxValue implements
		IValuationAgencyTrxValue {
	public OBValuationAgencyTrxValue() {
	}
	
	private ICity stagingCity;

	IValuationAgency valuationAgency;
	IValuationAgency stagingValuationAgency;
	
	IFileMapperId FileMapperID;
    IFileMapperId stagingFileMapperID;
    

	public IValuationAgency getValuationAgency() {
		return valuationAgency;
	}

	public void setValuationAgency(IValuationAgency valuationAgency) {
		this.valuationAgency = valuationAgency;
	}

	public IValuationAgency getStagingValuationAgency() {
		return stagingValuationAgency;
	}

	public void setStagingValuationAgency(
			IValuationAgency stagingValuationAgency) {
		this.stagingValuationAgency = stagingValuationAgency;
	}

	public OBValuationAgencyTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	public ICity getStagingCity() {
		return stagingCity;
	}

	public void setStagingCity(ICity stagingCity) {
		this.stagingCity = stagingCity;
	}

	public IFileMapperId getFileMapperID() {
		return FileMapperID;
	}

	public void setFileMapperID(IFileMapperId fileMapperID) {
		FileMapperID = fileMapperID;
	}

	public IFileMapperId getStagingFileMapperID() {
		return stagingFileMapperID;
	}

	public void setStagingFileMapperID(IFileMapperId stagingFileMapperID) {
		this.stagingFileMapperID = stagingFileMapperID;
	}
  
	
	
}
