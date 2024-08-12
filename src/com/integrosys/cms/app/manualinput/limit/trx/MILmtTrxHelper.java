/*
 * Created on Feb 14, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.limit.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.bus.SBLimitManager;
import com.integrosys.cms.app.limit.bus.SBLimitManagerHome;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.security.trx.MISecTrxHelper;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MILmtTrxHelper {
	public ILimitTrxValue createActualLimit(ILimitTrxValue trxValue) throws Exception {
		SBLimitManager manager = getSBLimitManager();
		ILimit stgLmt = trxValue.getStagingLimit();
		ILimit newLmt = manager.createLimitWithAccounts(stgLmt);
		trxValue.setReferenceID(String.valueOf(newLmt.getLimitID()));
		trxValue.setLimit(newLmt);
		return trxValue;
	}

	public ILimitTrxValue createStagingLimit(ILimitTrxValue trxValue) throws Exception {
		SBLimitManager manager = getSBLimitManagerStaging();
		ILimit stgLmt = trxValue.getStagingLimit();
		
		
		
		if(null!=stgLmt){
			
			ICollateralAllocation[] alloc = stgLmt.getCollateralAllocations();
			
			if(null!=alloc){
		for (int i = 0; i < alloc.length; i++) {
			ICollateralAllocation value = alloc[i];
			
			if(null!=value){
				
			DefaultLogger.debug(this, "I value :  " + i +"====");
			
			DefaultLogger.debug(this, "value.getHostStatus(): " + value.getHostStatus()+"====");
			DefaultLogger.debug(this, "value.getSourceLmtId(): " + value.getSourceLmtId()+"====");
			DefaultLogger.debug(this, "value.getCustomerCategory(): " + value.getCustomerCategory()+"====");
			DefaultLogger.debug(this, "value.getLimitProfileID(): " + value.getLimitProfileID()+"====");
			DefaultLogger.debug(this, "value.getSourceID(): " + value.getSourceID()+"====");
			DefaultLogger.debug(this, "value.getLmtSecurityCoverage(): " + value.getLmtSecurityCoverage()+"====");
			DefaultLogger.debug(this, "value.getCpsSecurityId(): " + value.getCpsSecurityId()+"====");
			
			DefaultLogger.debug(this, "value.getCoborrowerLimitID(): " + value.getCoborrowerLimitID()+"====");
			DefaultLogger.debug(this, "value.getLimitID(): " + value.getLimitID()+"====");
			DefaultLogger.debug(this, "value.getSCILimitSecMapID(): " + value.getSCILimitSecMapID()+"====");
			
			ICollateral col = value.getCollateral();
			
			if(null != col){
				
				DefaultLogger.debug(this, "col.getCollateralID(): " + col.getCollateralID()+"====");
			}
			
			}
		}
			}
		}
		
		ILimit newStgLmt = manager.createLimitWithAccounts(stgLmt);
		trxValue.setStagingReferenceID(String.valueOf(newStgLmt.getLimitID()));
		trxValue.setStagingLimit(newStgLmt);
		return trxValue;
	}

	public ILimitTrxValue updateActualLimitFromStaging(ILimitTrxValue trxValue) throws Exception {
		SBLimitManager manager = getSBLimitManager();
		ILimit actualLimit = trxValue.getLimit();
		ILimit stagingLimit = trxValue.getStagingLimit();
		ILimit newLimit = new OBLimit(stagingLimit);
		newLimit.setLimitID(actualLimit.getLimitID());
		newLimit.setVersionTime(actualLimit.getVersionTime());
		if(trxValue.getStatus().equals("PENDING_DELETE")){
		newLimit.setLimitStatus(trxValue.getToState());	
		}
		//newLimit = manager.updateLimitWithAccounts(actualLimit, newLimit);
		newLimit = manager.updateLimitWithUdfAccounts(actualLimit, newLimit);
		trxValue.setLimit(newLimit);

		return trxValue;
	}

	public ILimitTrxValue createActualLimitFromStaging(ILimitTrxValue trxValue) throws Exception {
		ILimit actualLimit = trxValue.getLimit();
		ILimit stagingLimit = trxValue.getStagingLimit();
		ILimit newLimit = new OBLimit(stagingLimit);
		// newLimit.setVersionTime(actualLimit.getVersionTime());
		newLimit = getSBLimitManager().createLimitWithAccounts(newLimit);
		trxValue.setLimit(newLimit);
		trxValue.setReferenceID(String.valueOf(newLimit.getLimitID()));
		return trxValue;
	}

	public ITrxResult prepareResult(ILimitTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	public boolean checkLimitSecMappingChanged(ILimitTrxValue trxValue) {
		ILimit actualLimit = trxValue.getLimit();
		ILimit stagingLimit = trxValue.getStagingLimit();
		int origSize = 0;
		int newSize = 0;
		if ((actualLimit != null) && (actualLimit.getCollateralAllocations() != null)) {
			origSize = actualLimit.getCollateralAllocations().length;
		}
		if ((stagingLimit != null) && (stagingLimit.getCollateralAllocations() != null)) {
			newSize = stagingLimit.getCollateralAllocations().length;
		}
//		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&& checkLimitSecMappingChanged: " + origSize + "  " + newSize);
		if ((origSize == 0) && (newSize == 0)) {
			return false;
		}
		else if (origSize >= newSize) {
			// if( actualLimit != null )
			ICollateralAllocation[] actualAlloc = actualLimit.getCollateralAllocations();
			ICollateralAllocation[] stagingAlloc = stagingLimit.getCollateralAllocations();

			boolean addNew = false;
			if (stagingAlloc != null) {

				for (int i = 0; i < stagingAlloc.length; i++) {
					boolean found = false;
					ICollateral stagingCol = stagingAlloc[i].getCollateral();

					for (int j = 0; j < actualAlloc.length; j++) {
						ICollateral actualCol = actualAlloc[j].getCollateral();

						if (actualCol.getSCISecurityID().equals(stagingCol.getSCISecurityID())) {

							found = true;
							break;
						}
					}

					if (!found) {
						addNew = true;
					}
				}
			}
			return addNew;
		}
		else {
			return true;
		}
	}

	public void processCheckList(ILimitTrxValue trxValue) throws Exception {
		ILimit actualLimit = trxValue.getLimit();
		if (actualLimit != null) {
//			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&& process checkList Actual Limit ID: "+ actualLimit.getLimitID());
			getSBLimitManager().processCheckList(String.valueOf(actualLimit.getLimitID()));
		}
	}

	public void performAutoLimitLinkCharge(ILimitTrxValue trxValue) throws Exception {
		try {
			MISecTrxHelper helper = new MISecTrxHelper();
			ILimit actualLimit = trxValue.getLimit();
			ILimit stagingLimit = trxValue.getStagingLimit();
			ICollateral actualCol = null;
			ICollateral stagingCol = null;
			if (actualLimit != null) {
				DefaultLogger.debug(this, "actualLimit != null");

				ICollateralAllocation[] colAllocList = actualLimit.getCollateralAllocations();
				if (colAllocList != null) {

					for (int i = 0; i < colAllocList.length; i++) {

						DefaultLogger.debug(this, "colAllocList, i=" + i);
						ICollateral col = colAllocList[i].getCollateral();

						// auto create limit charge map
						if(colAllocList[i].getHostStatus()!=null && !(colAllocList[i].getHostStatus().trim().equals(""))){
						if (!colAllocList[i].getHostStatus().equals(ICMSConstant.HOST_STATUS_DELETE)) {

							if (helper.isColReqLinkCharge(col)) {
								DefaultLogger.debug(this, "colAllocList, getHostStatus="
										+ colAllocList[i].getHostStatus());

								Long chrgID = helper.getUnlinkChargeDetailID(actualLimit, col);
								if (chrgID!= null) {

									helper.createLimitChargeMap(actualLimit.getLimitID(), chrgID.longValue(), col,
											colAllocList[i]);
								}

							}
						}
						else {
							// remove from lmt charge map
							helper.removeLimitChargeMap(actualLimit.getLimitID(), col);

						}
						}
					}// end for

				}// end colAllocList != null

			} // end actualLimit != null

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw e;
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

}
