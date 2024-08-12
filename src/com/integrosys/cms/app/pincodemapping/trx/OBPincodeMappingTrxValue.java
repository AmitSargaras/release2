package com.integrosys.cms.app.pincodemapping.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMapping;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBPincodeMappingTrxValue extends OBCMSTrxValue implements
IPincodeMappingTrxValue{

	public OBPincodeMappingTrxValue() {
	}
	
	public OBPincodeMappingTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}
	
	IPincodeMapping pincodeMapping;
	IPincodeMapping stagingPincodeMapping;
	
	public IPincodeMapping getPincodeMapping() {
		return pincodeMapping;
	}
	public void setPincodeMapping(IPincodeMapping pincodeMapping) {
		this.pincodeMapping = pincodeMapping;
	}
	public IPincodeMapping getStagingPincodeMapping() {
		return stagingPincodeMapping;
	}
	public void setStagingPincodeMapping(IPincodeMapping stagingPincodeMapping) {
		this.stagingPincodeMapping = stagingPincodeMapping;
	}

	
}
