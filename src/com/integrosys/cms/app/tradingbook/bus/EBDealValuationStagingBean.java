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
 * Entity bean implementation for staging deal valuation entity.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBDealValuationStagingBean extends EBDealValuationBean {

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param cmsDealID of long
	 * @param value of type IDealValuation
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(long cmsDealID, IDealValuation value) throws CreateException {
		if (null == value) {
			throw new CreateException("IDealValuation is null!");
		}
		else if (ICMSConstant.LONG_INVALID_VALUE == cmsDealID) {
			throw new CreateException("cmsDealID is uninitialised!");
		}

		try {

			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getPKSequenceName(), true));
			DefaultLogger.debug(this, "Creating deal valuation with ID: " + pk);
			setDealValIDPK(new Long(pk));
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setDealIDFK(new Long(cmsDealID));

			if (value.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
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

	/**
	 * Get the sequence of primary key for this Deal Valuation.
	 * 
	 * @return String
	 */
	protected String getPKSequenceName() {
		return ICMSConstant.SEQUENCE_DEAL_VAL_STAGE;
	}

}