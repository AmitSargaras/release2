package com.integrosys.cms.app.excLineforstpsrm.bus;

import java.io.Serializable;
import java.util.List;

public interface IExcLineForSTPSRMDao {

	static final String ACTUAL_NAME = "actualExcLineForSTPSRM";
	static final String STAGE_NAME = "stageExcLineForSTPSRM";
	
	IExcLineForSTPSRM get(String entityName, Serializable key) throws ExcLineForSTPSRMException;
	IExcLineForSTPSRM update(String entityName, IExcLineForSTPSRM item) throws ExcLineForSTPSRMException;
	IExcLineForSTPSRM create(String entityName, IExcLineForSTPSRM item)	throws ExcLineForSTPSRMException;
	IExcLineForSTPSRM delete(String entityName, IExcLineForSTPSRM item) throws ExcLineForSTPSRMException;
	public List getList();
}
