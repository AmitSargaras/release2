/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/ICCCertificateDAO.java,v 1.6 2003/11/06 10:47:19 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * This interface defines the constant specific to the cc certificate table and
 * the methods required by the cc certificate
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/11/06 10:47:19 $ Tag: $Name: $
 */
public interface ICCCertificateDAO extends ICCCertificateTableConstants {
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
	 * Get the cc certificate ID
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return long - the CCC ID
	 * @throws SearchDAOException on errors
	 */
	public long getCCCID(CCCertificateSearchCriteria aCriteria) throws SearchDAOException;

	/**
	 * Get the cc certificate trx ID
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return String - the CCC TRX ID
	 * @throws SearchDAOException on errors
	 */
	public String getCCCTrxID(CCCertificateSearchCriteria aCriteria) throws SearchDAOException;

	/**
	 * Get the list of cc certificate trx based on the limit profile ID
	 * @param aLimitProfileID of long type
	 * @return CCCertificateSearchResult[] - the list of cc certificate trx info
	 * @throws SearchDAOException on errors
	 */
	public CCCertificateSearchResult[] getNoOfCCCGenerated(long aLimitProfileID) throws SearchDAOException;

	/**
	 * To get the number of cc certificate that satisfy the criteria
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return int - the number of cc certificate that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfCCCertificate(CCCertificateSearchCriteria aCriteria) throws SearchDAOException;
}