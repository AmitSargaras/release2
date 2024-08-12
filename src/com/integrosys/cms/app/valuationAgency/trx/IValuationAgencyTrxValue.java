package com.integrosys.cms.app.valuationAgency.trx;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;

/**
 * @author rajib.aich
 * 
 */

public interface IValuationAgencyTrxValue extends ICMSTrxValue {

	public IValuationAgency getValuationAgency();

	public IValuationAgency getStagingValuationAgency();

	public void setValuationAgency(IValuationAgency valuationAgency);

	public void setStagingValuationAgency(
			IValuationAgency stagingValuationAgency);
	
	   public void setStagingFileMapperID(IFileMapperId value);
	   
	   public IFileMapperId getFileMapperID();
	   
	   public IFileMapperId getStagingFileMapperID();
	   
	   public void setFileMapperID(IFileMapperId value);
	    
	
	public ICity getStagingCity();
}
