/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/SecurityTrxHandler.java,v 1.16 2004/03/11 08:54:03 lyng Exp $
 */
package com.integrosys.cms.host.eai.security.handler.trxhandler;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.host.eai.EAITransactionException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonTrxHandler;
import com.integrosys.cms.host.eai.security.SCISecurityHelper;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.ISecurityDao;
import com.integrosys.cms.host.eai.security.bus.SecuritySource;

/**
 * Transaction handler for security.
 * 
 * @author lyng
 * @author Chong Jun Yong
 * @since 11.03.2004
 */
public class SecurityTrxHandler extends AbstractCommonTrxHandler {

	private static final long serialVersionUID = -786783939331588486L;

	private ISecurityDao securityDao;

	private ICollateralProxy collateralProxy;

	public ISecurityDao getSecurityDao() {
		return securityDao;
	}

	public void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}

	public void setCollateralProxy(ICollateralProxy collateralProxy) {
		this.collateralProxy = collateralProxy;
	}

	public ICollateralProxy getCollateralProxy() {
		return collateralProxy;
	}

	private static SCISecurityHelper helper = SCISecurityHelper.getInstance();

	protected Map prepareTrxValuesMap(Message msg, Message stagingMsg, Map trxMap) {
		ApprovedSecurity sec = ((SecurityMessageBody) msg.getMsgBody()).getSecurityDetail();
		ApprovedSecurity stage = ((SecurityMessageBody) stagingMsg.getMsgBody()).getSecurityDetail();

		if (isSecurityChanged(sec)) {

			ICollateralTrxValue trxValue = (ICollateralTrxValue) trxMap.get(sec.getLOSSecurityId());

			if ((trxValue != null) && (trxValue.getReferenceID() == null)) {
				OBCollateral col = new OBCollateral();
				col.setCollateralID(sec.getCMSSecurityId());
				OBCollateralSubType st = new OBCollateralSubType();
				col.setCollateralSubType(st);
				col.setCollateralType(st);
				trxValue.setCollateral(col);
				if (stage != null) {
					OBCollateral stageCol = new OBCollateral();
					stageCol.setCollateralID(stage.getCMSSecurityId());
					trxValue.setStagingCollateral(stageCol);
				}
			}

			trxValue.getCollateral().setSCISecurityID(sec.getLOSSecurityId());
		}

		return trxMap;
	}

	/**
	 * Return a transaction handler key used for the HashMap returned by
	 * getTransaction() method.
	 * 
	 * @return a String representation of security transaction key
	 */
	public String getTrxKey() {
		return SECURITY_KEY;
	}

	public Map getTransaction(Message msg, Map flatMessage) throws EAITransactionException {
		try {
			logger.debug(this + " .. getting the transaction");
			ICollateralTrxValue trxValue = null;
			ICollateralProxy proxy = getCollateralProxy();
			HashMap trxMap = new HashMap();

			ApprovedSecurity approvedSec = ((SecurityMessageBody) msg.getMsgBody()).getSecurityDetail();

			if (approvedSec.getLOSSecurityId() != null) {
				approvedSec.setOldSecurityId(approvedSec.getLOSSecurityId().toUpperCase());
			}

			if (approvedSec.getLOSSecurityId() != null) {
				approvedSec.setLOSSecurityId(approvedSec.getLOSSecurityId().toUpperCase());
			}

			if (isSecurityChanged(approvedSec)) {
				ApprovedSecurity tmpSec = getApprovedSecurityForProcessing(approvedSec);
				if (tmpSec == null) {
					trxValue = new OBCollateralTrxValue();
				}
				else {
					trxValue = proxy.getCollateralTrxValue(new OBTrxContext(), new Long(tmpSec.getCMSSecurityId())
							.longValue());
				}
				trxMap.put(approvedSec.getLOSSecurityId(), trxValue);
			}
			flatMessage.put(getTrxKey(), trxMap);
			return flatMessage;
		}
		catch (Exception e) {
			throw new EAITransactionException("Caught Exception while getting Transaction", e);
		}
	}

	protected long getCollateralIdBySecurityId(ApprovedSecurity aa) {
		long collaterlId = ICMSConstant.LONG_INVALID_VALUE;
		SecuritySource securitySource = new SecuritySource();

		Map parameters = new HashMap();
		parameters.put("sourceSecurityId", aa.getLOSSecurityId());
		parameters.put("sourceId", aa.getSourceId());
		parameters.put("securitySubTypeId", aa.getSecuritySubType().getStandardCodeValue());

		securitySource = (SecuritySource) getSecurityDao().retrieveObjectByParameters(parameters, SecuritySource.class);

		if (securitySource != null) {
			collaterlId = securitySource.getCMSSecurityId();
		}

		return collaterlId;
	}

	protected void hostExecution(Map trxValueMap, Message msg) throws EAITransactionException {
		ICollateralProxy proxy = CollateralProxyFactory.getProxy();

		HashMap trxMap = (HashMap) trxValueMap;
		ApprovedSecurity security = ((SecurityMessageBody) msg.getMsgBody()).getSecurityDetail();
		// Only persist into transaction table if the security is not-shared
		// security (owner of the security)
		if (isSecurityChanged(security) && !helper.isShareSecurity(security)) {
			ICollateralTrxValue trxValue = (ICollateralTrxValue) trxMap.get(security.getLOSSecurityId());

			try {
				if ((trxValue != null) && (trxValue.getReferenceID() == null)) {
					trxValue = proxy.subscribeCreateCollateral(trxValue);
				}
				else if (isDeleteSecurity(security)) {
					trxValue = proxy.subscribeDeleteCollateral(trxValue);
				}
				else {
					trxValue = proxy.hostUpdateCollateral(trxValue.getTrxContext(), trxValue);
				}
			}
			catch (CollateralException e) {
				throw new EAITransactionException("Failed to under go collateral workflow, trx [" + trxValue
						+ "], msg header info [" + msg.getMsgHeader() + "]", e);
			}
		}

	}

	public String getOpDesc() {
		return null;
	}

	public String getOperationName() {
		return null;
	}

	/**
	 * Helper method to load approved security using castor.
	 * 
	 * @param cdb of type CastorDb
	 * @param security of type ApprovedSecurity
	 * @return approved security
	 */
	protected ApprovedSecurity loadApprovedSecurity(ApprovedSecurity security) {
		HashMap parameters = new HashMap();
		if(security.getCMSSecurityId() > 0 )
			parameters.put("CMSSecurityId",new Long( security.getCMSSecurityId()));
		else
			parameters.put("LOSSecurityId", security.getLOSSecurityId());
		return (ApprovedSecurity) getSecurityDao().retrieveObjectByParameters(parameters, ApprovedSecurity.class);
	}

	protected ApprovedSecurity getApprovedSecurityForProcessing(ApprovedSecurity security) {
		if (helper.isCreate(security)) {
			return null;
		}
		else if (helper.isUpdate(security) || helper.isDelete(security)) {
			return loadApprovedSecurity(security);
		}
		else {
			throw new IllegalStateException("Invalid Update Indicator! SCI security id: [" + security.getLOSSecurityId()
					+ "] indicator: [" + security.getUpdateStatusIndicator() + "]");
		}
	}

	/**
	 * Check if the security is changed.
	 * 
	 * @param security of type ApprovedSecurity
	 * @return boolean
	 */
	protected boolean isSecurityChanged(ApprovedSecurity security) {
		return helper.isSecurityChanged(security);
	}

	/**
	 * Check if it is to delete the approved security.
	 * 
	 * @param security of type ApprovedSecurity
	 * @return boolean
	 */
	protected boolean isDeleteSecurity(ApprovedSecurity security) {
		return helper.isDelete(security);
	}
}
