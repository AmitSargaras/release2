package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBSecurityCoverageLocalHome extends EJBLocalHome {
	
	public EBSecurityCoverageLocal create(ISecurityCoverage securityCoverage) throws CreateException;

	public EBSecurityCoverageLocal findByPrimaryKey(Long id) throws FinderException;
}