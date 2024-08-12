package com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail;

import javax.ejb.EJBLocalObject;

public interface EBLineDetailLocal extends EJBLocalObject {
	
	public long getLineDetailID();

	public ILineDetail getValue();

	public void setValue(ILineDetail value);


}
