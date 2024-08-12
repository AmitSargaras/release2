/*
 * Created on Apr 2, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.security.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ILimitChargeMap;
import com.integrosys.cms.app.collateral.bus.OBLimitChargeMap;
import com.integrosys.cms.app.collateral.bus.SBCollateralBusManager;
import com.integrosys.cms.app.collateral.bus.SBCollateralBusManagerHome;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.sharesecurity.bus.SBShareSecurityManager;
import com.integrosys.cms.app.sharesecurity.bus.SBShareSecurityManagerHome;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MISecTrxHelper {
	public ICollateralTrxValue createActualCollateral(ICollateralTrxValue trxValue) throws Exception {
		SBCollateralBusManager manager = getSBCollateralManager();
		ICollateral stgCol = trxValue.getStagingCollateral();

		ICollateral newCol = manager.createCollateralWithPledgor(stgCol);
		trxValue.setReferenceID(String.valueOf(newCol.getCollateralID()));
		trxValue.setCollateral(newCol);
		return trxValue;
	}

	public ICollateralTrxValue createStagingCollateral(ICollateralTrxValue trxValue) throws Exception {
		SBCollateralBusManager manager = getSBCollateralManagerStaging();
		ICollateral stgCol = trxValue.getStagingCollateral();
		stgCol.setSourceId(ICMSConstant.SOURCE_SYSTEM_CMS);

		ICollateral newCol = manager.createCollateralWithPledgor(stgCol);
		trxValue.setStagingReferenceID(String.valueOf(newCol.getCollateralID()));
		//trxValue.setLegalName(newCol.getSCISecurityID());
		trxValue.setLegalName(trxValue.getLegalName());
		//trxValue.setLimitProfileID(trxValue.get)
		trxValue.setCustomerName(newCol.getCollateralSubType().getSubTypeName());
		trxValue.setStagingCollateral(newCol);
		return trxValue;
	}

	public ICollateralTrxValue updateActualColFromStaging(ICollateralTrxValue trxValue) throws Exception {
		SBCollateralBusManager manager = getSBCollateralManager();
		ICollateral actualCol = trxValue.getCollateral();
		ICollateral stagingCol = trxValue.getStagingCollateral();
		stagingCol.setCollateralID(actualCol.getCollateralID());
		stagingCol.setVersionTime(actualCol.getVersionTime());
		ICollateral newCol = manager.updateCollateral(stagingCol);
		//trxValue.setLegalName(newCol.getSCISecurityID());
		trxValue.setLegalName(trxValue.getLegalName());
		trxValue.setCustomerName(newCol.getCollateralSubType().getSubTypeName());
		trxValue.setCollateral(newCol);
		return trxValue;
	}

	public ICollateralTrxValue createActualColFromStaging(ICollateralTrxValue trxValue) throws Exception {
		SBCollateralBusManager manager = getSBCollateralManager();
		ICollateral actualCol = trxValue.getCollateral();
		ICollateral stagingCol = trxValue.getStagingCollateral();
		ICollateral newCol = (ICollateral) AccessorUtil.deepClone(stagingCol);
		newCol = manager.createCollateralWithPledgor(newCol);
		//trxValue.setLegalName(newCol.getSCISecurityID());
		trxValue.setLegalName(trxValue.getLegalName());
		trxValue.setCustomerName(newCol.getCollateralSubType().getSubTypeName());
		trxValue.setCollateral(newCol);
		trxValue.setReferenceID(String.valueOf(newCol.getCollateralID()));
		return trxValue;
	}

	public void createLimitChargeMap(long cmsLimitID, long chargeDetailID, ICollateral actualCol,
			ICollateralAllocation colAlloc) throws Exception {
		DefaultLogger.debug(this, "cmsLimitID=" + cmsLimitID);
		DefaultLogger.debug(this, "chargeDetailID=" + chargeDetailID);

		ICMSTrxValue trxVal = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(actualCol.getCollateralID()),
				ICMSConstant.INSTANCE_COLLATERAL);

		String stagingColID = trxVal.getStagingReferenceID();
		DefaultLogger.debug(this, "staging collateral ID=" + stagingColID);

		SBCollateralBusManager manager = getSBCollateralManager();
		SBCollateralBusManager stagingManager = getSBCollateralManagerStaging();

		ICollateralLimitMap actualColLmtMap = manager.getCollateralLimitMap(actualCol.getCollateralID(), cmsLimitID);

		ILimitChargeMap lmtChargeMap = new OBLimitChargeMap();
		lmtChargeMap.setCollateralID(actualCol.getCollateralID());
		lmtChargeMap.setLimitID(cmsLimitID);
		lmtChargeMap.setChargeDetailID(chargeDetailID);

		if (actualColLmtMap != null) {
			lmtChargeMap.setChargeID(actualColLmtMap.getChargeID()); // update
																		// charge
																		// ID
			lmtChargeMap.setCoBorrowerLimitID(actualColLmtMap.getCoBorrowerLimitID());
			lmtChargeMap.setCustomerCategory(actualColLmtMap.getCustomerCategory());
		}
		else {
			lmtChargeMap.setChargeID(colAlloc.getChargeID());
			lmtChargeMap.setCoBorrowerLimitID(colAlloc.getCoborrowerLimitID());
			lmtChargeMap.setCustomerCategory(colAlloc.getCustomerCategory());
		}
		manager.createLimitChargeMap(lmtChargeMap);
		if (stagingColID != null) {
			lmtChargeMap.setCollateralID(Long.parseLong(stagingColID));
			stagingManager.createLimitChargeMap(lmtChargeMap);

		}
	}

	public boolean isColReqLinkCharge(ICollateral col) {
		if ((col != null) && (col.getCollateralType() != null) && (col.getCollateralSubType() != null)) {
			if (!(col.getCollateralType().getTypeCode().equals(ICMSConstant.SECURITY_TYPE_PROPERTY)
					&& col.getCollateralType().getTypeCode().equals(ICMSConstant.SECURITY_TYPE_OTHERS)
					&& col.getCollateralSubType().getSubTypeCode().equals(ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE)
					&& col.getCollateralSubType().getSubTypeCode().equals(
							ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_AIRCRAFT)
					&& col.getCollateralSubType().getSubTypeCode()
							.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_OTHERS)
					&& col.getCollateralSubType().getSubTypeCode().equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT) && col
					.getCollateralSubType().getSubTypeCode().equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH))) {
				return true;
			}
		}
		return false;
	}

	public void removeLimitChargeMap(long cmsLimitID, ICollateral actualCol) throws Exception {
		DefaultLogger.debug(this, "cmsLimitID=" + cmsLimitID);

		SBCollateralBusManager manager = getSBCollateralManager();
		SBCollateralBusManager stagingManager = getSBCollateralManagerStaging();
		ICollateralDAO dao = CollateralDAOFactory.getDAO();

		ILimitChargeMap lmtChargeMap = dao.getLimitChargeMapToUnlink(cmsLimitID, actualCol.getCollateralID());

		if (lmtChargeMap != null) {
			ICMSTrxValue trxVal = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(actualCol.getCollateralID()),
					ICMSConstant.INSTANCE_COLLATERAL);

			String stagingColID = trxVal.getStagingReferenceID();
			DefaultLogger.debug(this, "staging collateral ID=" + stagingColID);

			manager.removeLimitChargeMap(lmtChargeMap);

			lmtChargeMap.setCollateralID(Long.parseLong(stagingColID));
			stagingManager.removeLimitChargeMap(lmtChargeMap);

		}

	}

	public Long getUnlinkChargeDetailID(ILimit lmt, ICollateral col) throws Exception {

		ICollateralDAO dao = CollateralDAOFactory.getDAO();
		return dao.getUnlinkChargeDetailID(lmt.getLimitID(), col.getCollateralID());

	}

	public ITrxResult prepareResult(ICollateralTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	public void createShareSecForGCMS(long secId, String sourceSecId) throws Exception {
		SBShareSecurityManager manager = getSBShareSecurityManager();
		manager.createShareSecForGCMS(secId, sourceSecId);
	}

	private SBCollateralBusManager getSBCollateralManager() throws TransactionException {
		SBCollateralBusManager home = (SBCollateralBusManager) (BeanController.getEJB(
				ICMSJNDIConstant.SB_COLLATERAL_MGR_JNDI, SBCollateralBusManagerHome.class.getName()));
		if (null != home) {
			return home;
		}
		else {
			throw new TransactionException("SBCollateraBusManager is null!");
		}
	}

	private SBCollateralBusManager getSBCollateralManagerStaging() throws TransactionException {
		SBCollateralBusManager home = (SBCollateralBusManager) (BeanController.getEJB(
				ICMSJNDIConstant.SB_COLLATERAL_MGR_STAGING_JNDI, SBCollateralBusManagerHome.class.getName()));
		if (null != home) {
			return home;
		}
		else {
			throw new TransactionException("SBCollateraBusManager is null!");
		}
	}

	private SBShareSecurityManager getSBShareSecurityManager() throws TransactionException {
		SBShareSecurityManager home = (SBShareSecurityManager) (BeanController.getEJB(
				ICMSJNDIConstant.SB_SHARE_SECURITY_MGR_JNDI, SBShareSecurityManagerHome.class.getName()));
		if (null != home) {
			return home;
		}
		else {
			throw new TransactionException("SBShareSecurityManager is null!");
		}
	}

	protected SBCMSTrxManager getTrxManager() throws TrxOperationException {
		SBCMSTrxManager mgr = (SBCMSTrxManager) BeanController.getEJB(ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME,
				SBCMSTrxManagerHome.class.getName());
		if (null == mgr) {
			throw new TrxOperationException("SBCMSTrxManager is null!");
		}
		else {
			return mgr;
		}
	}
}
