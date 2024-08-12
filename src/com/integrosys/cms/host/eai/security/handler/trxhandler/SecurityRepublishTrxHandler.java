/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/SecurityRepublishTrxHandler.java,v 1.4 2003/12/05 10:59:44 slong Exp $
 */
package com.integrosys.cms.host.eai.security.handler.trxhandler;

import java.util.Map;

import com.integrosys.cms.host.eai.EAITransactionException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;

/**
 * Transaction handler for republishing security.
 * 
 * @author $Author: slong $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/12/05 10:59:44 $ Tag: $Name: $
 */
public class SecurityRepublishTrxHandler extends SecurityTrxHandler {
	private static final String SECURITY_KEY = IEaiConstant.SECURITY_REPUBLISH_KEY;

	/**
	 * Return a transaction handler key used for the HashMap returned by
	 * getTransaction() method.
	 * 
	 * @return a String representation of security transaction key
	 */
	public String getTrxKey() {
		return SECURITY_KEY;
	}

	/**
	 * Check if the security is changed.
	 * 
	 * @param security of type ApprovedSecurity
	 * @return boolean
	 */
	protected boolean isSecurityChanged(ApprovedSecurity security) {
		return true;
	}

	/**
	 * Check if it is to delete the approved security.
	 * 
	 * @param security of type ApprovedSecurity
	 * @return boolean
	 */
	protected boolean isDeleteSecurity(ApprovedSecurity security) {
		return false;
	}

	public Map getTransaction(Message msg, Map flatMessage) throws EAITransactionException {
		return super.getTransaction(msg, flatMessage);
		/*
		 * try { ICollateralTrxValue trxValue = null; ICollateralProxy proxy =
		 * CollateralProxyFactory.getProxy(); Map trxMap = new HashMap();
		 * 
		 * ApprovedSecurity approvedSec = ((SecurityMessageBody)
		 * msg.getMsgBody()).getSecurityDetail();
		 * 
		 * if (approvedSec.getOldSecurityId() != null) {
		 * approvedSec.setOldSecurityId
		 * (approvedSec.getOldSecurityId().toUpperCase()); }
		 * 
		 * if (approvedSec.getSecurityId() != null) {
		 * approvedSec.setSecurityId(approvedSec.getSecurityId().toUpperCase());
		 * } approvedSec.setSourceId(msg.getMsgHeader().getSource());
		 * 
		 * long cmsCollateralId = getCollateralIdBySecurityId(approvedSec);
		 * 
		 * if (isSecurityChanged(approvedSec)) { if (cmsCollateralId ==
		 * ICMSConstant.LONG_INVALID_VALUE) { trxValue = new
		 * OBCollateralTrxValue(); } else { trxValue =
		 * proxy.getCollateralTrxValue(new OBTrxContext(), cmsCollateralId); }
		 * 
		 * approvedSec.setCMSSecurityId(cmsCollateralId);
		 * trxMap.put(approvedSec.getSecurityId(), trxValue); }
		 * 
		 * flatMessage.put(getTrxKey(), trxMap); return flatMessage; } catch
		 * (Exception e) { throw new
		 * EAITransactionException("Caught Exception while getting Transaction",
		 * e); }
		 */
	}

	protected Map prepareTrxValuesMap(Message msg, Message stagingMsg, Map oritrx) {
		/*
		 * Map trxMap = (HashMap) oritrx; ApprovedSecurity sec =
		 * ((SecurityMessageBody) msg.getMsgBody()).getSecurityDetail();
		 * ApprovedSecurity stage = ((SecurityMessageBody)
		 * stagingMsg.getMsgBody()).getSecurityDetail();
		 * 
		 * if (isSecurityChanged(sec)) { ICollateralTrxValue trxValue =
		 * (ICollateralTrxValue) trxMap.get(sec.getSecurityId()); if (trxValue
		 * != null && trxValue.getReferenceID() == null) { OBCollateral col =
		 * new OBCollateral(); col.setCollateralID(sec.getCMSSecurityId());
		 * OBCollateralSubType st = new OBCollateralSubType();
		 * col.setCollateralSubType(st); col.setCollateralType(st);
		 * trxValue.setCollateral(col); if (stage != null) { OBCollateral
		 * stageCol = new OBCollateral();
		 * stageCol.setCollateralID(stage.getCMSSecurityId());
		 * trxValue.setStagingCollateral(stageCol); } }
		 * 
		 * trxValue.getCollateral().setSCISecurityID(sec.getSecurityId());
		 * 
		 * }
		 * 
		 * return trxMap;
		 */
		return super.prepareTrxValuesMap(msg, stagingMsg, oritrx);
	}


}