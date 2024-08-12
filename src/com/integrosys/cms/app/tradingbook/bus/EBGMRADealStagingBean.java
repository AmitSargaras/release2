/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import javax.ejb.CreateException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for staging GMRA Deal entity.
 * 
 * @author $Author: Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBGMRADealStagingBean extends EBGMRADealBean {
	protected static final String[] EXCLUDE_METHOD = new String[] { "getCMSDealID", "getAgreementID" };

	public Long ejbCreate(long agreementID, IGMRADeal gmraDeal) throws CreateException {
		if (null == gmraDeal) {
			throw new CreateException("IGMRADeal is null!");
		}
		else if (ICMSConstant.LONG_INVALID_VALUE == agreementID) {
			throw new CreateException("Agreement ID is uninitialised!");
		}

		try {

			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getPKSequenceName(), true));
			DefaultLogger.debug(this, "Creating GMRA Deal with ID: " + pk);
			setDealIDPK(new Long(pk));
			AccessorUtil.copyValue(gmraDeal, this, EXCLUDE_METHOD);
			setAgreementIDFK(new Long(agreementID));
			setAgreementType(ICMSConstant.GMRA_TYPE);

			setVersionTime(VersionGenerator.getVersionNumber());
			return new Long(pk);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	protected String getPKSequenceName() {
		return ICMSConstant.SEQUENCE_TB_DEAL_STAGE;
	}

}