package com.integrosys.cms.app.valuationAgency.proxy;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue;

/**
 *
 * 
 * @author $Author:  Rajib Aich $<br>
 * @version $Revision: 1.6 $
 * 
 */

public interface IValuationAgencyProxyManager {
	public List getAllActual() ;
	public List getFilteredActual(String code,String name) ;
	public IValuationAgencyTrxValue makerCreateValuationAgency(ITrxContext anITrxContext, IValuationAgency anICCvaluationAgency)throws ValuationAgencyException,TrxParameterException,TransactionException;
	public IValuationAgencyTrxValue getValuationAgencyByTrxID(String aTrxID) throws ValuationAgencyException,TransactionException,CommandProcessingException;
	public IValuationAgencyTrxValue checkerApproveValuationAgency(ITrxContext anITrxContext, IValuationAgencyTrxValue anIValuationAgencyTrxValue) throws ValuationAgencyException,TrxParameterException,TransactionException;
	public IValuationAgencyTrxValue checkerRejectValuationAgency(ITrxContext anITrxContext, IValuationAgencyTrxValue anIValuationAgencyTrxValue) throws ValuationAgencyException,TrxParameterException,TransactionException;
	public IValuationAgencyTrxValue makerCloseDraftValuationAgency(ITrxContext anITrxContext, IValuationAgencyTrxValue anIValuationAgencyTrxValue) throws ValuationAgencyException,TrxParameterException,TransactionException;
	public IValuationAgencyTrxValue makerCloseRejectedValuationAgency(ITrxContext anITrxContext, IValuationAgencyTrxValue anIValuationAgencyTrxValue) throws ValuationAgencyException,TrxParameterException,TransactionException;
	public IValuationAgencyTrxValue makerEditRejectedValuationAgency(ITrxContext anITrxContext, IValuationAgencyTrxValue anIValuationAgencyTrxValue, IValuationAgency anValuationAgency) throws ValuationAgencyException,TrxParameterException,TransactionException;
	public IValuationAgencyTrxValue getValuationAgencyTrxValue(long aValuationAgencyId) throws ValuationAgencyException,TrxParameterException,TransactionException;
	public IValuationAgencyTrxValue makerUpdateSaveCreateValuationAgency(ITrxContext anITrxContext, IValuationAgencyTrxValue anICCValuationAgencyTrxValue, IValuationAgency anICCValuationAgency)
	throws ValuationAgencyException,TrxParameterException,TransactionException;
	public IValuationAgencyTrxValue makerDisableValuationAgency(ITrxContext anITrxContext, IValuationAgencyTrxValue anICCValuationAgencyTrxValue, IValuationAgency anICCValuationAgency)throws ValuationAgencyException,TrxParameterException,TransactionException;
	public IValuationAgencyTrxValue makerEnableValuationAgency(ITrxContext anITrxContext, IValuationAgencyTrxValue anICCValuationAgencyTrxValue, IValuationAgency anICCValuationAgency)throws ValuationAgencyException,TrxParameterException,TransactionException;
	public IValuationAgencyTrxValue makerUpdateSaveUpdateValuationAgency(ITrxContext anITrxContext, IValuationAgencyTrxValue anICCValuationAgencyTrxValue, IValuationAgency anICCValuationAgency)throws ValuationAgencyException,TrxParameterException,TransactionException;
	public IValuationAgencyTrxValue makerSaveValuationAgency(ITrxContext anITrxContext, IValuationAgency anICCValuationAgency)throws ValuationAgencyException,TrxParameterException,TransactionException;
	public IValuationAgencyTrxValue makerCreateValuationAgency(ITrxContext anITrxContext, IValuationAgencyTrxValue anICCValuationAgencyTrxValue, IValuationAgency anICCValuationAgency) throws ValuationAgencyException,TrxParameterException,TransactionException ;
	public boolean isVACodeUnique(String rmCode);	
	public List getCountryList(long countryId) throws ValuationAgencyException;
	public List getCityList(long stateId) throws ValuationAgencyException;
	
	//**********************FOR UPLOAD********************************

	
	public boolean isPrevFileUploadPending() throws ValuationAgencyException,TrxParameterException,TransactionException;
    
	public IValuationAgencyTrxValue makerInsertMapperValuationAgency(ITrxContext anITrxContext, OBFileMapperID obFileMapperID)throws ValuationAgencyException,TrxParameterException,TransactionException;
	
	public int insertValuationAgency(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList) throws ValuationAgencyException,TrxParameterException,TransactionException;
   
	public IValuationAgencyTrxValue getInsertFileByTrxID(String aTrxID) throws ValuationAgencyException,TransactionException,CommandProcessingException;

	public List getAllStage(String searchBy, String login) throws ValuationAgencyException,TrxParameterException,TransactionException;

	public IValuationAgencyTrxValue checkerApproveInsertValuationAgency(ITrxContext anITrxContext, IValuationAgencyTrxValue anIValuationAgencyTrxValue) throws ValuationAgencyException,TrxParameterException,TransactionException;
	public List getFileMasterList(String searchBy) throws ValuationAgencyException,TrxParameterException,TransactionException;
	public IValuationAgency insertActualValuationAgency(String sysId) throws ValuationAgencyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public IValuationAgencyTrxValue checkerCreateValuationAgency(ITrxContext anITrxContext,IValuationAgency anICCValuationAgency, String refStage)throws ValuationAgencyException,TrxParameterException,TransactionException;
	public IValuationAgencyTrxValue checkerRejectInsertValuationAgency(ITrxContext anITrxContext, IValuationAgencyTrxValue anIValuationAgencyTrxValue) throws ValuationAgencyException,TrxParameterException,TransactionException;

	public IValuationAgencyTrxValue makerInsertCloseRejectedValuationAgency(ITrxContext anITrxContext, IValuationAgencyTrxValue anIValuationAgencyTrxValue) throws ValuationAgencyException,TrxParameterException,TransactionException;
	
	public boolean isUniqueCode(String branchCode) throws ValuationAgencyException,TrxParameterException,TransactionException;

	public boolean isValuationNameUnique(String valuationName) throws ValuationAgencyException,TrxParameterException,TransactionException;
	
	public String isCodeExisting(String countryCode,String regionCode,String stateCode,String cityCode) throws ValuationAgencyException,TrxParameterException,TransactionException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws ValuationAgencyException,TrxParameterException,TransactionException;
	
	//********** Addedy by Dattatray Thorat for Property - Commercial Security
	
	public String getValuationAgencyName(String companyId);
		
	}
