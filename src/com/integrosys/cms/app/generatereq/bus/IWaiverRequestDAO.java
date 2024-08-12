/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/IWaiverRequestDAO.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * This interface defines the constant specific to the waiver request table and
 * the methods required by the waiver request
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public interface IWaiverRequestDAO extends IWaiverRequestTableConstants {
	public static final String TRX_TABLE = ICMSTrxTableConstants.TRX_TBL_NAME;

	public static final String TRX_ID = ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID;

	public static final String TRX_TYPE = ICMSTrxTableConstants.TRXTBL_TRANSACTION_TYPE;

	public static final String TRX_STATUS = ICMSTrxTableConstants.TRXTBL_STATUS;

	public static final String TRX_REF_ID = ICMSTrxTableConstants.TRXTBL_REFERENCE_ID;

	public static final String TRX_STAGE_REF_ID = ICMSTrxTableConstants.TRXTBL_STAGING_REFERENCE_ID;

	public static final String TRX_ID_PREF = TRX_TABLE + "." + TRX_ID;

	public static final String TRX_TYPE_PREF = TRX_TABLE + "." + TRX_TYPE;

	public static final String TRX_STATUS_PREF = TRX_TABLE + "." + TRX_STATUS;

	public static final String TRX_REF_ID_PREF = TRX_TABLE + "." + TRX_REF_ID;

	public static final String TRX_STAGE_REF_ID_PREF = TRX_TABLE + "." + TRX_STAGE_REF_ID;

	/**
	 * To get the number of waiver request that satisfy the criteria
	 * @param aCriteria of WaiverRequestSearchCriteria type
	 * @return int - the number of waiver request that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfWaiverRequest(WaiverRequestSearchCriteria aCriteria) throws SearchDAOException;

}