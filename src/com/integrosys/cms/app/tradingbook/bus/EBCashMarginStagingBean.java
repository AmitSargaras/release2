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
 * Entity bean implementation for staging cash margin entity.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBCashMarginStagingBean extends EBCashMarginBean {
	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param agreementID agreement ID
	 * @param cashMargin of type ICashMargin
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(long agreementID, ICashMargin cashMargin) throws CreateException {
		if (null == cashMargin) {
			throw new CreateException("ICashMargin is null!");
		}
		else if (ICMSConstant.LONG_INVALID_VALUE == agreementID) {
			throw new CreateException("Agreement ID is uninitialised!");
		}

		try {

			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_CASH_MARGIN_STAGE, true));
			DefaultLogger.debug(this, "Creating CASH MARGIN with ID: " + pk);
			setCashMarginIDPK(new Long(pk));

			AccessorUtil.copyValue(cashMargin, this, EXCLUDE_METHOD);
			setAgreementIDFK(new Long(agreementID));

			if (cashMargin.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
				setGroupID(pk);
			}
			setVersionTime(VersionGenerator.getVersionNumber());

			return new Long(pk);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

}