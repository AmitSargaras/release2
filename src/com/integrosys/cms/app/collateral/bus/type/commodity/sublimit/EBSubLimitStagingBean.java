/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/sublimit/EBSubLimitStagingBean.java,v 1.1 2005/10/06 05:49:52 hmbao Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity.sublimit;

import javax.ejb.CreateException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-30
 * @Tag com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.
 *      EBSubLimitStagingBean.java
 */
public abstract class EBSubLimitStagingBean extends EBSubLimitBean {
	protected long generateSubLimitPK() throws CreateException {
		DefaultLogger.debug(this, "generateSubLimitPK");
		String sequenceName = ICMSConstant.SEQUENCE_CMD_SUBLIMIT_STAGING_SEQ;
		try {
			String seq = new SequenceManager().getSeqNum(sequenceName, true);
			return Long.parseLong(seq);
		}
		catch (Exception e) {
			throw new CreateException("Exception in generating Sequence '" + sequenceName + "' \n The exception is : "
					+ e);
		}
	}
}
