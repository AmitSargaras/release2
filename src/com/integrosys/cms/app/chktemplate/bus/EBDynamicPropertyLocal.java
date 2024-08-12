package com.integrosys.cms.app.chktemplate.bus;

import javax.ejb.EJBLocalObject;

public interface EBDynamicPropertyLocal extends EJBLocalObject {

	//public Long getCMPPropertyID();

	//public Long getCMPPropertySetupID();

	public long getReferenceID();

    public void setStatus(String status);

    public IDynamicProperty getValue();

	public void setValue(IDynamicProperty dynProp);


}
