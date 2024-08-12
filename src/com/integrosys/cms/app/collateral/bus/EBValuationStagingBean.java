/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBValuationStagingBean.java,v 1.1 2003/11/18 02:19:55 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for collateral valuation entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/11/18 02:19:55 $ Tag: $Name: $
 */
public abstract class EBValuationStagingBean extends EBValuationBean {
	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param valuation of type IValuation
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IValuation valuation) throws CreateException {
		try {
			String valID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_VALUATION, true);
			AccessorUtil.copyValue(valuation, this, EXCLUDE_METHOD);
			setEBValuationID(new Long(valID));

			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}
}