package com.integrosys.cms.app.cersaiMapping.trx;

import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface ICersaiMappingTrxValue extends ICMSTrxValue{ 

	public ICersaiMapping getCersaiMapping();

	public ICersaiMapping getStagingCersaiMapping();

	public void setCersaiMapping(ICersaiMapping value);

	public void setStagingCersaiMapping(ICersaiMapping value);
}
