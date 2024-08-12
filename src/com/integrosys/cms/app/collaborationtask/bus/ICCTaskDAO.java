/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/ICCTaskDAO.java,v 1.2 2003/09/08 13:16:27 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * This interface defines the constant specific to the sc certificate table and
 * the methods required by the sc certificate
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/08 13:16:27 $ Tag: $Name: $
 */
public interface ICCTaskDAO extends ICCTaskTableConstants {
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
	 * To get the number of CC task that satisfy the criteria
	 * @param aCriteria of CCTaskSearchCriteria type
	 * @return int - the number of CC task that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfCCTask(CCTaskSearchCriteria aCriteria) throws SearchDAOException;

	/**
	 * To get the list of CC task that satisfy the criteria
	 * @param aCritieria of CCTaskSearchCritieria type
	 * @return CCTaskSearchResult[] - the list of CC task
	 * @throws SearchDAOException
	 */
	public CCTaskSearchResult[] getCCTask(CCTaskSearchCriteria aCriteria) throws SearchDAOException;

}