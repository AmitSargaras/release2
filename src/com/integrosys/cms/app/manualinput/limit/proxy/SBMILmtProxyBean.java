/*
 * Created on 2007-2-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.limit.proxy;

import java.rmi.RemoteException;
import java.util.List;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.LmtColSearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.MakerCheckerUserUtil;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.SBLimitManager;
import com.integrosys.cms.app.limit.bus.SBLimitManagerHome;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitTrxValue;
import com.integrosys.cms.app.manualinput.aa.bus.TradeAgreementDAO;
import com.integrosys.cms.app.manualinput.limit.trx.MILmtTransactionController;
import com.integrosys.cms.app.manualinput.limit.trx.TransactionActionConst;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SBMILmtProxyBean implements SessionBean {
	private SessionContext _context = null;

	/**
	 * Called by the container to create a session bean instance. Its parameters
	 * typically contain the information the client uses to customize the bean
	 * instance for its use. It requires a matching pair in the bean class and
	 * its home interface.
	 */
	public void ejbCreate() {
	}

	/**
	 * A container invokes this method before it ends the life of the session
	 * object. This happens as a result of a client's invoking a remove
	 * operation, or when a container decides to terminate the session object
	 * after a timeout. This method is called with no transaction context.
	 */
	public void ejbRemove() {

	}

	/**
	 * The activate method is called when the instance is activated from its
	 * 'passive' state. The instance should acquire any resource that it has
	 * released earlier in the ejbPassivate() method. This method is called with
	 * no transaction context.
	 */
	public void ejbActivate() {

	}

	/**
	 * The passivate method is called before the instance enters the 'passive'
	 * state. The instance should release any resources that it can re-acquire
	 * later in the ejbActivate() method. After the passivate method completes,
	 * the instance must be in a state that allows the container to use the Java
	 * Serialization protocol to externalize and store away the instance's
	 * state. This method is called with no transaction context.
	 */
	public void ejbPassivate() {

	}

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation. The enterprise Bean instance should store the
	 * reference to the context object in an instance variable. This method is
	 * called with no transaction context.
	 */
	public void setSessionContext(javax.ejb.SessionContext sc) {
		_context = sc;
	}

	public ILimitTrxValue searchLimitByLmtId(String lmtId) throws LimitException {
		try {
			DefaultLogger.debug(this, "********** Start SBMILmtProxyBean searchLimitByLmtId function 109 lmtID " + lmtId);
			ICMSTrxValue cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(lmtId, ICMSConstant.INSTANCE_LIMIT);
			DefaultLogger.debug(this, "********** End SBMILmtProxyBean searchLimitByLmtId function 111  lmtID " + lmtId);
			OBLimitTrxValue trxValue = new OBLimitTrxValue(cmsTrxValue);
			String refId = trxValue.getReferenceID();
			DefaultLogger.debug(this, "********** In SBMILmtProxyBean searchLimitByLmtId function 114  refId: " + refId);
			if ((refId != null) && !(Long.parseLong(refId) == ICMSConstant.LONG_INVALID_VALUE)) {
				SBLimitManager manager = getSBLimitManager();
				ILimit limit = manager.getLimit(Long.parseLong(refId));
				trxValue.setLimit(limit);
			}
			DefaultLogger.debug(this, "********** After Actual SBMILmtProxyBean searchLimitByLmtId function 120  refId " + refId);
			String stgRefId = trxValue.getStagingReferenceID();
			if ((stgRefId != null) && !(Long.parseLong(stgRefId) == ICMSConstant.LONG_INVALID_VALUE)) {
				SBLimitManager manager = getSBLimitManagerStaging();
				ILimit limit = manager.getLimit(Long.parseLong(stgRefId));
				trxValue.setStagingLimit(limit);
			}
			DefaultLogger.debug(this, "********** Before prepareTrxLimit SBMILmtProxyBean searchLimitByLmtId function 127  stgRefId " + stgRefId);
			MILmtProxyHelper helper = new MILmtProxyHelper();
			ILimitTrxValue newTrxValue = helper.prepareTrxLimit(trxValue);
			DefaultLogger.debug(this, "********** After prepareTrxLimit SBMILmtProxyBean searchLimitByLmtId function 130  stgRefId " + stgRefId);
			return newTrxValue;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new LimitException("Exception in method searchLimitByLmtId");
		}
	}

	public ILimitTrxValue searchLimitByTrxId(String trxId) throws LimitException {
		try {
			DefaultLogger.debug(this, "********** Start SBMILmtProxyBean searchLimitByTrxId function 140 trxId " + trxId);
			ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(trxId);
			DefaultLogger.debug(this, "********** End SBMILmtProxyBean searchLimitByTrxId function 142 trxId " + trxId);
			OBLimitTrxValue trxValue = new OBLimitTrxValue(cmsTrxValue);
			String refId = trxValue.getReferenceID();
			DefaultLogger.debug(this, "********** In SBMILmtProxyBean searchLimitByTrxId function 145 refId " + refId);
			if ((refId != null) && !(Long.parseLong(refId) == ICMSConstant.LONG_INVALID_VALUE)) {
				SBLimitManager manager = getSBLimitManager();
				ILimit limit = manager.getLimit(Long.parseLong(refId));
				trxValue.setLimit(limit);
			}

			String stgRefId = trxValue.getStagingReferenceID();
			if ((stgRefId != null) && !(Long.parseLong(stgRefId) == ICMSConstant.LONG_INVALID_VALUE)) {
				SBLimitManager manager = getSBLimitManagerStaging();
				ILimit limit = manager.getLimit(Long.parseLong(stgRefId));
				trxValue.setStagingLimit(limit);
			}
			MILmtProxyHelper helper = new MILmtProxyHelper();
			ILimitTrxValue newTrxValue = helper.prepareTrxLimit(trxValue);
			DefaultLogger.debug(this, "********** Before return SBMILmtProxyBean searchLimitByTrxId function 160 stgRefId " + stgRefId);
			return newTrxValue;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new LimitException("Exception in method searchLimitByTrxId");
		}
	}

	public SearchResult searchCollateralByIdSubtype(LmtColSearchCriteria criteria) throws SearchDAOException {
		return CollateralDAOFactory.getDAO().searchCollateralByIdSubtype(criteria);
	}

	public List getSecSubtypeList() throws SearchDAOException {
		return CollateralDAOFactory.getDAO().getSecSubtypeList();
	}

	public List getLimitSummaryListByAA(String aaId) throws LimitException {
		return new LimitDAO().getLimitSummaryListByAA(aaId);
	}
	
	public List getLimitSummaryListByCustID(String aaId) throws LimitException {  //Shiv 280911
		return new LimitDAO().getLimitSummaryListByCustID(aaId);				//Shiv 280911
	}																		//Shiv 280911
	
	public List getLimitSummaryListByCustID(String aaId, String facilityId) throws LimitException {  //Shiv 101011
		return new LimitDAO().getLimitSummaryListByCustID(aaId, facilityId);				//Shiv 101011
	}																		//Shiv 101011
	
	public List getLimitTranchListByFacilityFor(String facilityId, String facilityFor) throws LimitException {  //Shiv 101011
		return new LimitDAO().getLimitTranchListByFacilityFor(facilityId, facilityFor);				//Shiv 101011
	}																		//Shiv 101011
	
	public String[] getLimitTranchListByCustID(String facilityId, String serialNo) throws LimitException {  //Shiv 101011
		return new LimitDAO().getLimitTranchListByCustID(facilityId, serialNo);				//Shiv 101011
	}																		//Shiv 101011
	
	public List getLimitListByFacilityFor(String aaId, String facilityId) throws LimitException {  //Shiv 280911
		return new LimitDAO().getLimitListByFacilityFor(aaId, facilityId);				//Shiv 280911
	}																		//Shiv 280911

	public String getRemainingPropertyValue() throws LimitException {
		return new LimitDAO().getRemainingPropertyValue();
	}
	
	public List getOuterLimitList(String aaId) throws LimitException {
		return new LimitDAO().getOuterLimitList(aaId);
	}

	public List getFacilityNameByAAId(String custid) throws LimitException {
		return new LimitDAO().getFacilityNameByAAId(custid);
	}

	public boolean checkDuplicateLmt(String lmtId) throws LimitException {
		return new LimitDAO().checkLimitExists(lmtId);
	}

	public Amount getNetoutStandingForAccount(String accountId, String limitCurrency) throws LimitException {
		return new LimitDAO().getNetoutStandingForAccount(accountId, limitCurrency);
	}

	public String getAgreementByAA(String aaId) throws SearchDAOException {
		return new TradeAgreementDAO().getAgreementByAA(aaId);
	}

	public String getFacilityGroupByAA(String aaId) throws SearchDAOException {
		return new TradeAgreementDAO().getFacilityGroupByAA(aaId);
	}

	public ICMSTrxResult createLimitTrx(ITrxContext ctx, ILimitTrxValue value, boolean isSave) throws LimitException {
		try {
			
			MILmtProxyHelper helper = new MILmtProxyHelper();
			
			value.setStatus(ICMSConstant.STATE_ND);
		
			value.setFromState(ICMSConstant.STATE_ND);
			
			helper.constructTrxValue(ctx, value);
		
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			if (!isSave) {
				
				param.setAction(TransactionActionConst.ACTION_MANUAL_CREATE_LMT);
			}
			else {
				
				param.setAction(TransactionActionConst.ACTION_MANUAL_SAVE_LMT);
			}
			
			ITrxController controller = new MILmtTransactionController();
			
			return (ICMSTrxResult) controller.operate(value, param);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			ex.printStackTrace();
			throw new LimitException("Exception in createLimitTrx");
		}
	}

	public ICMSTrxResult createSubmitFacility(ITrxContext ctx, ILimitTrxValue value, boolean isDelete) throws LimitException {
		
		/*res = createLimitTrx(ctx, value, false);
		limitTrxValue = (OBLimitTrxValue)proxy.searchLimitByTrxId(res.getTrxValue().getTransactionID());
		//Setting user related information in the context
		context = setContextAsPerUserId(context,"CPUADM_C");
		context.setLimitProfile(profile);
		context.setCustomer(cust);
		//Checker approve process
		response = proxy.checkerApproveLmtTrx(context, limitTrxValue);*/
		
		MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
		ctx = mcUtil.setContextForMaker();
		
		OBCMSTrxValue transaction = new OBCMSTrxValue();
		ICMSTrxResult trxResult=null;
		try {
			
			if(value.getTransactionID() == null && !isDelete){
			trxResult = createLimitTrx(ctx, value, false);
			if (trxResult != null) {
				transaction = (OBCMSTrxValue) trxResult.getTrxValue();
			}
			value = (OBLimitTrxValue) searchLimitByTrxId(trxResult.getTrxValue().getTransactionID());
			}
			else if(!isDelete){
				trxResult = makerUpdateLmtTrx(ctx, value, false);
				if (trxResult != null) {
					transaction = (OBCMSTrxValue) trxResult.getTrxValue();
				}
				value =(OBLimitTrxValue)searchLimitByTrxId(trxResult.getTrxValue().getTransactionID());		
			}
			else {
				trxResult = makerDeleteLmtTrx(ctx, value);
				if (trxResult != null) {
					transaction = (OBCMSTrxValue) trxResult.getTrxValue();
				}
				value =(OBLimitTrxValue)searchLimitByTrxId(trxResult.getTrxValue().getTransactionID());		
			}
			ctx = mcUtil.setContextForChecker();
			trxResult = checkerApproveLmtTrx(ctx, value);
		}catch (LimitException e) {
			rollback();
			throw e;
		}
		catch (Exception e) {
			rollback();
			throw new LimitException("Exception caught! " + e.toString(), e);
		}
		return trxResult;
	}
	
	
	
public ICMSTrxResult createSubmitFacilityRest(ITrxContext ctx, ILimitTrxValue value, boolean isDelete) throws LimitException {
		
		/*res = createLimitTrx(ctx, value, false);
		limitTrxValue = (OBLimitTrxValue)proxy.searchLimitByTrxId(res.getTrxValue().getTransactionID());
		//Setting user related information in the context
		context = setContextAsPerUserId(context,"CPUADM_C");
		context.setLimitProfile(profile);
		context.setCustomer(cust);
		//Checker approve process
		response = proxy.checkerApproveLmtTrx(context, limitTrxValue);*/
		
		MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
		ctx = mcUtil.setContextForMaker();
		
		OBCMSTrxValue transaction = new OBCMSTrxValue();
		ICMSTrxResult trxResult=null;
		try {
			
			if(value.getTransactionID() == null && !isDelete){
			trxResult = createLimitTrx(ctx, value, false);
			if (trxResult != null) {
				transaction = (OBCMSTrxValue) trxResult.getTrxValue();
			}
			value = (OBLimitTrxValue) searchLimitByTrxId(trxResult.getTrxValue().getTransactionID());
			}
			else if(!isDelete){
				trxResult = makerUpdateLmtTrx(ctx, value, false);
				if (trxResult != null) {
					transaction = (OBCMSTrxValue) trxResult.getTrxValue();
				}
				value =(OBLimitTrxValue)searchLimitByTrxId(trxResult.getTrxValue().getTransactionID());		
			}
			else {
				trxResult = makerDeleteLmtTrx(ctx, value);
				if (trxResult != null) {
					transaction = (OBCMSTrxValue) trxResult.getTrxValue();
				}
				value =(OBLimitTrxValue)searchLimitByTrxId(trxResult.getTrxValue().getTransactionID());		
			}
			//ctx = mcUtil.setContextForChecker();
			//trxResult = checkerApproveLmtTrx(ctx, value);
		}catch (LimitException e) {
			rollback();
			throw e;
		}
		catch (Exception e) {
			rollback();
			throw new LimitException("Exception caught! " + e.toString(), e);
		}
		return trxResult;
	}
	
	
	public ICMSTrxResult makerUpdateLmtTrx(ITrxContext ctx, ILimitTrxValue value, boolean isSave) throws LimitException {
		try {
			
			MILmtProxyHelper helper = new MILmtProxyHelper();
		
			helper.constructTrxValue(ctx, value);
			
			OBCMSTrxParameter param = new OBCMSTrxParameter();
		
			if (!isSave) {
			
				param.setAction(TransactionActionConst.ACTION_MANUAL_UPDATE_LMT);
			}
			else {
				
				param.setAction(TransactionActionConst.ACTION_MANUAL_SAVE_LMT);
			}
			
			ITrxController controller = new MILmtTransactionController();
			
			return (ICMSTrxResult) controller.operate(value, param);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			ex.printStackTrace();
			throw new LimitException("Exception in makerUpdateLmtTrx");
		}
	}

	public ICMSTrxResult makerCloseLmtTrx(ITrxContext ctx, ILimitTrxValue value) throws LimitException {
		try {
			MILmtProxyHelper helper = new MILmtProxyHelper();
			helper.constructTrxValue(ctx, value);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(TransactionActionConst.ACTION_MANUAL_CLOSE_LMT);

			ITrxController controller = new MILmtTransactionController();
			return (ICMSTrxResult) controller.operate(value, param);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			ex.printStackTrace();
			throw new LimitException("Exception in makerCloseLmtTrx");
		}
	}

	public ICMSTrxResult makerDeleteLmtTrx(ITrxContext ctx, ILimitTrxValue value) throws LimitException {
		try {
			MILmtProxyHelper helper = new MILmtProxyHelper();
			helper.constructTrxValue(ctx, value);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(TransactionActionConst.ACTION_MANUAL_DELETE_LMT);

			ITrxController controller = new MILmtTransactionController();
			return (ICMSTrxResult) controller.operate(value, param);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			ex.printStackTrace();
			throw new LimitException("Exception in makerCloseLmtTrx");
		}
	}

	public ICMSTrxResult checkerRejectLmtTrx(ITrxContext ctx, ILimitTrxValue value) throws LimitException {
		try {
			MILmtProxyHelper helper = new MILmtProxyHelper();
			helper.constructTrxValue(ctx, value);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(TransactionActionConst.ACTION_MANUAL_REJECT_LMT);

			ITrxController controller = new MILmtTransactionController();
			return (ICMSTrxResult) controller.operate(value, param);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			ex.printStackTrace();
			throw new LimitException("Exception in checkerRejectLmtTrx");
		}
	}

	public ICMSTrxResult checkerApproveLmtTrx(ITrxContext ctx, ILimitTrxValue value) throws LimitException {
		try {
			MILmtProxyHelper helper = new MILmtProxyHelper();
			helper.constructTrxValue(ctx, value);
			// pass the manualinput stage, setback transaction subtype to null
			// value.setTransactionSubType(null);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(TransactionActionConst.ACTION_MANUAL_APPROVE_LMT);

			ITrxController controller = new MILmtTransactionController();
			return (ICMSTrxResult) controller.operate(value, param);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			ex.printStackTrace();
			throw new LimitException("Exception in checkerApproveLmtTrx");
		}
	}

	private SBCMSTrxManager getTrxManager() throws TransactionException {
		SBCMSTrxManager mgr = (SBCMSTrxManager) BeanController.getEJB(ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME,
				SBCMSTrxManagerHome.class.getName());
		if (null == mgr) {
			throw new TransactionException("SBCMSTrxManager is null!");
		}
		else {
			return mgr;
		}
	}

	private SBLimitManager getSBLimitManager() throws TransactionException {
		SBLimitManager home = (SBLimitManager) BeanController.getEJB(ICMSJNDIConstant.SB_LIMIT_MGR_JNDI,
				SBLimitManagerHome.class.getName());
		if (null != home) {
			return home;
		}
		else {
			throw new TransactionException("SBLimitManager is null!");
		}
	}

	/**
	 * Get the SBLimitManager remote interface
	 * 
	 * @return SBLimitManager
	 */
	private SBLimitManager getSBLimitManagerStaging() throws TransactionException {
		SBLimitManager home = (SBLimitManager) BeanController.getEJB(ICMSJNDIConstant.SB_LIMIT_MGR_JNDI_STAGING,
				SBLimitManagerHome.class.getName());
		if (null != home) {
			return home;
		}
		else {
			throw new TransactionException("SBLimitManager is null!");
		}
	}
	
	public List getFacNameList(String facGroup) throws LimitException {
		return new LimitDAO().getFacNameList(facGroup);
	}
	
	public List getFacDetailList(String facName, String custID) throws LimitException {
		return new LimitDAO().getFacDetailList(facName, custID);
	}
	
	public List getFacDetailListRest(String facName, String custID) throws LimitException {
		return new LimitDAO().getFacDetailListRest(facName, custID);
	}
	public List getRelationShipMngrList() throws LimitException {
		return new LimitDAO().getRelationShipMngrList();
	}
	
	
	public List getSubFacNameList(String profileID) throws LimitException {
		return new LimitDAO().getSubFacNameList(profileID);
	}
	
	public List getSubPartyNameList(String profileID) throws LimitException {
		return new LimitDAO().getSubPartyNameList(profileID);
	}
	
	public List getSubSecurityList(long profileID) throws LimitException {
		return new LimitDAO().getSubSecurityList(profileID);
	}
	
	public List getSystemID(String system, String custID) throws LimitException {
		return new LimitDAO().getSystemID(system, custID);
	}
	
	public List getVendorDtls(String custID) throws LimitException{
		return new LimitDAO().getVendorDtls(custID);
	}
	
	public String getBorrowerScmFlag(String custID) throws LimitException{
		return new LimitDAO().getBorrowerScmFlag(custID);
	} 
	
	public double getReleaseAmountForParty(String custID) throws LimitException{
		return new LimitDAO().getReleaseAmountForParty(custID);
	} 
	
	
	public List getLiabilityIDList(String profileID) throws LimitException {
		return new LimitDAO().getLiabilityIDList(profileID);
	}

	public String getFacilityName(String lmtId) throws SearchDAOException{
		return new LimitDAO().getFacilityName(lmtId);
	}
	
	protected void rollback() throws LimitException {
		try {
			_context.setRollbackOnly();
		}
		catch (Exception e) {
			throw new LimitException(e.toString());
		}
	}
	
	/*public void updateSanctionedLimitToZero(String camId) throws RemoteException,LimitException{
		new LimitDAO().updateSanctionedLimitToZero(camId);
	}*/
	
	//added by santosh
	public List getRejectedLimitSummaryListByAA() throws LimitException {
		return new LimitDAO().getRejectedLimitSummaryListByAA();
	}
	public List getRejectedLimitSummaryListByAA(String searchCriteria) throws LimitException {
		return new LimitDAO().getRejectedLimitSummaryListByAA(searchCriteria);
	}
	//end santosh
}
