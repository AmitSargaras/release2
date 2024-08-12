package com.integrosys.cms.app.systemBank.proxy;

import java.util.List;

import org.springframework.stereotype.Service;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBank.trx.ISystemBankTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
/**
 * @author $Author: Abhijit R $
 */
@Service
public interface ISystemBankProxyManager {

	public ISystemBankTrxValue makerCloseRejectedSystemBank(ITrxContext anITrxContext, ISystemBankTrxValue anISystemBankTrxValue) throws SystemBankException,TrxParameterException,TransactionException;
	public List getAllActual() throws SystemBankException,TrxParameterException,TransactionException;
	public ISystemBank getSystemBankById(long id) throws SystemBankException,TrxParameterException,TransactionException ;

	public ISystemBank updateSystemBank(ISystemBank systemBank) throws SystemBankException,TrxParameterException,TransactionException,ConcurrentUpdateException;

	public ISystemBankTrxValue makerUpdateSystemBank(ITrxContext anITrxContext, ISystemBankTrxValue anICCSystemBankTrxValue, ISystemBank anICCSystemBank)
	throws SystemBankException,TrxParameterException,TransactionException;
	public ISystemBankTrxValue makerEditRejectedSystemBank(ITrxContext anITrxContext, ISystemBankTrxValue anISystemBankTrxValue, ISystemBank anSystemBank) throws SystemBankException,TrxParameterException,TransactionException;
	public ISystemBankTrxValue getSystemBankTrxValue(long aSystemBankId) throws SystemBankException,TrxParameterException,TransactionException;

	public ISystemBankTrxValue getSystemBankByTrxID(String aTrxID) throws SystemBankException,TransactionException,CommandProcessingException;
	public ISystemBankTrxValue checkerApproveSystemBank(ITrxContext anITrxContext, ISystemBankTrxValue anISystemBankTrxValue) throws SystemBankException,TrxParameterException,TransactionException;
	public ISystemBankTrxValue checkerRejectSystemBank(ITrxContext anITrxContext, ISystemBankTrxValue anISystemBankTrxValue) throws SystemBankException,TrxParameterException,TransactionException;
	public ISystemBankTrxValue makerUpdateSaveUpdateSystemBank(ITrxContext anITrxContext, ISystemBankTrxValue anICCSystemBankTrxValue, ISystemBank anICCSystemBank)
	throws SystemBankException,TrxParameterException,TransactionException;
	public ISystemBankTrxValue makerCloseDraftSystemBank(ITrxContext anITrxContext, ISystemBankTrxValue anISystemBankTrxValue) throws SystemBankException,TrxParameterException,TransactionException;
}
