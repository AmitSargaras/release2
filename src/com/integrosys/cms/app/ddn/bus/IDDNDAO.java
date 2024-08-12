/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/IDDNDAO.java,v 1.3 2005/06/08 06:39:06 htli Exp $
 */
package com.integrosys.cms.app.ddn.bus;

//java
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * This interface defines the constant specific to the sc certificate table and
 * the methods required by the sc certificate
 * 
 * @author $Author: htli $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/06/08 06:39:06 $ Tag: $Name: $
 */
public interface IDDNDAO extends IDDNTableConstants {
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
	 * To get the DDNID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return long - the DDN ID
	 * @throws SearchDAOException
	 */
	public long getDDNIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException;

	/**
	 * To get the number of ddn that satisfy the criteria
	 * @param aCriteria of DDNSearchCriteria type
	 * @return int - the number of ddn that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfDDN(DDNSearchCriteria aCriteria) throws SearchDAOException;

}