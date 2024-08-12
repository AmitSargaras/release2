package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGC;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface IInsuranceGCTrxValue extends ICMSTrxValue{
	
		  public IInsuranceGC getInsuranceGC();
		
		  public IInsuranceGC getStagingInsuranceGC();
			
			public IFileMapperId getStagingFileMapperID();
		    
		    public IFileMapperId getFileMapperID();
		    
		    public void setInsuranceGC(IInsuranceGC value);
		
		    public void setStagingInsuranceGC(IInsuranceGC value);
		    
		    public void setStagingFileMapperID(IFileMapperId value);
		    
		    public void setFileMapperID(IFileMapperId value);


}
