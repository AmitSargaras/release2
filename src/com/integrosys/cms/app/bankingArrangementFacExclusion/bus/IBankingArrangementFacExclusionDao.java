package com.integrosys.cms.app.bankingArrangementFacExclusion.bus;

import java.io.Serializable;
import java.util.List;

public interface IBankingArrangementFacExclusionDao {

	static final String ACTUAL_NAME = "actualBankingArrangementFacExclusion";
	static final String STAGE_NAME = "stageBankingArrangementFacExclusion";
	
	IBankingArrangementFacExclusion get(String entityName, Serializable key) throws BankingArrangementFacExclusionException;
	IBankingArrangementFacExclusion update(String entityName, IBankingArrangementFacExclusion item) throws BankingArrangementFacExclusionException;
	IBankingArrangementFacExclusion create(String entityName, IBankingArrangementFacExclusion item)	throws BankingArrangementFacExclusionException;
	IBankingArrangementFacExclusion delete(String entityName, IBankingArrangementFacExclusion item) throws BankingArrangementFacExclusionException;
	public List getList();
}
