package com.integrosys.cms.app.pincodemapping.trx;

import com.integrosys.cms.app.pincodemapping.bus.IPincodeMapping;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface IPincodeMappingTrxValue extends ICMSTrxValue{

	
	public IPincodeMapping getPincodeMapping();

	public IPincodeMapping getStagingPincodeMapping();

	public void setPincodeMapping(IPincodeMapping value);

	public void setStagingPincodeMapping(IPincodeMapping value);
}
