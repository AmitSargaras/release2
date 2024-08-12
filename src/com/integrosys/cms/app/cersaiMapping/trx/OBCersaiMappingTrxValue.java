package com.integrosys.cms.app.cersaiMapping.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBCersaiMappingTrxValue  extends OBCMSTrxValue implements
ICersaiMappingTrxValue {

	
	public OBCersaiMappingTrxValue() {
		// TODO Auto-generated constructor stub
	}
	
	ICersaiMapping cersaiMapping;
	ICersaiMapping stagingCersaiMapping;
	
	public OBCersaiMappingTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}
	
	public ICersaiMapping getCersaiMapping() {
		return cersaiMapping;
	}

	public void setCersaiMapping(ICersaiMapping cersaiMapping) {
		this.cersaiMapping = cersaiMapping;
	}

	public ICersaiMapping getStagingCersaiMapping() {
		return stagingCersaiMapping;
	}

	public void setStagingCersaiMapping(ICersaiMapping stagingCersaiMapping) {
		this.stagingCersaiMapping = stagingCersaiMapping;
	}

}
