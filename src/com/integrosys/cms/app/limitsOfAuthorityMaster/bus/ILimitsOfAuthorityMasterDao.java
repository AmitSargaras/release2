package com.integrosys.cms.app.limitsOfAuthorityMaster.bus;

import java.io.Serializable;
import java.util.List;

public interface ILimitsOfAuthorityMasterDao {

	static final String ACTUAL_NAME = "actualLimitsOfAuthorityMaster";
	static final String STAGE_NAME = "stageLimitsOfAuthorityMaster";
	
	ILimitsOfAuthorityMaster get(String entityName, Serializable key) throws LimitsOfAuthorityMasterException;
	ILimitsOfAuthorityMaster update(String entityName, ILimitsOfAuthorityMaster item) throws LimitsOfAuthorityMasterException;
	ILimitsOfAuthorityMaster create(String entityName, ILimitsOfAuthorityMaster item)	throws LimitsOfAuthorityMasterException;
	ILimitsOfAuthorityMaster delete(String entityName, ILimitsOfAuthorityMaster item) throws LimitsOfAuthorityMasterException;
	public List getList();
}
