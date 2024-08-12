/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/ISCCertificateDAO.java,v 1.5 2003/11/06 10:48:05 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * This interface defines the constant specific to the sc certificate table and
 * the methods required by the sc certificate
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/11/06 10:48:05 $ Tag: $Name: $
 */
public interface ISCCertificateDAO extends ISCCertificateTableConstants {
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
	 * To get the SCCID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return long - the SCC ID
	 * @throws SearchDAOException
	 */
	public long getSCCIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException;

	/**
	 * To get the number of sc certificate that satisfy the criteria
	 * @param aCriteria of SCCertificateSearchCriteria type
	 * @return int - the number of cc certificate that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfSCCertificate(SCCertificateSearchCriteria aCriteria) throws SearchDAOException;

	/**
	 * To get the SCC Trx ID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return String - the SCC Trx ID
	 * @throws SearchDAOException on errors
	 */
	public String getSCCTrxIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException;

}