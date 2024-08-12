package com.integrosys.cms.app.systemBank.bus;

/**
 @author $Author: Abhijit R $
 */
import java.io.Serializable;
import java.util.List;

public interface ISystemBankDao {

	static final String ACTUAL_SYSTEM_BANK_NAME = "actualOBSystemBank";

	static final String STAGE_SYSTEM_BANK_NAME = "stageOBSystemBank";

	ISystemBank getSystemBank(String entityName, Serializable key)
			throws SystemBankException;

	ISystemBank updateSystemBank(String entityName, ISystemBank item)
			throws SystemBankException;

	ISystemBank createSystemBank(String entityName, ISystemBank systemBank)
			throws SystemBankException;
	List getAllSystemBank () throws SystemBankException;
	
	List listSystemBank(long branchCode)throws SystemBankException;
	
	public ISystemBank getSystemBankById(long id) throws SystemBankException ;
	
	public ISystemBank getSystemBankByCode(String id) throws SystemBankException ;
	
}
