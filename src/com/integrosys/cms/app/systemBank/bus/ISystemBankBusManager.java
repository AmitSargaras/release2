package com.integrosys.cms.app.systemBank.bus;
/**
@author $Author: Abhijit R $
*/
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public interface ISystemBankBusManager {
	
	


	   ISystemBank getSystemBankById(long id) throws SystemBankException,TrxParameterException,TransactionException;
	   List getAllSystemBank();
	   ISystemBank updateSystemBank(ISystemBank item) throws SystemBankException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	   ISystemBank updateToWorkingCopy(ISystemBank workingCopy, ISystemBank imageCopy) throws SystemBankException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	   ISystemBank createSystemBank(ISystemBank systemBank)throws SystemBankException;
}
