
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBLocalObject;

public interface EBSecurityCoverageLocal extends EJBLocalObject {
	
	public long getId();

	public ISecurityCoverage getValue();

	public void setValue(ISecurityCoverage securityCoverage);

}