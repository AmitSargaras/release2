/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/ICollateralTrxDAO.java,v 1.9 2006/07/19 11:10:42 wltan Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import java.util.Map;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.StpTrxStatusReadyIndicator;

/**
 * DAO for collateral transaction.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/07/19 11:10:42 $ Tag: $Name: $
 */
public interface ICollateralTrxDAO {

	public static final String COLLATERAL_STATUS_ACTIVE = "1";

	public static final String COLLATERAL_STATUS_RELEASED = "3";

	/**
	 * Get security sub type ID for a collateral transaction.
	 * 
	 * @param aTrxID - primitive long denoting the collateral transaction
	 * @return String denoting the security sub type
	 * @throws SearchDAOException
	 */
	public String[] getSecuritySubTypeForTrxByTrxID(long aTrxID) throws SearchDAOException;

	/**
	 * Get transaction value objects by a list of reference ids.
	 * 
	 * @param cols of type collaterals that contain reference id
	 * @return a list of transaction values
	 * @throws SearchDAOException on error searching the transaction
	 */
	public ICollateralTrxValue[] getTrxValueByRefIDs(ICollateral[] cols) throws SearchDAOException;

	/**
	 * Get transaction id given the reference id and transaction type. The refID
	 * can be actual reference id or staging reference id.
	 * 
	 * @param refID actual or staging reference id
	 * @param trxType transaction type
	 * @return transaction id
	 * @throws SearchDAOException on error getting the transaction id
	 */
	public String getTransactionID(String refID, String trxType) throws SearchDAOException;

	/**
	 * Get transaction id given its transaction reference id.
	 * 
	 * @param trxRefID parent transaction id
	 * @param trxType transaction type
	 * @return transaction id
	 * @throws SearchDAOException on error getting the transaction id
	 */
	public String getTrxIDByTrxRefID(String trxRefID, String trxType) throws SearchDAOException;

	/**
	 * Get collateral transaction ids which is related to transaction reference
	 * id.
	 * 
	 * @param trxRefID transaction reference id
	 * @return a list of collateral transaction ids belong to the trx reference
	 *         id
	 * @throws SearchDAOException on error getting the transaction ids
	 */
	public String[] getColTrxIDsByTrxRefID(String trxRefID) throws SearchDAOException;

	/**
	 * Get actual reference id given its transaction id.
	 * 
	 * @param trxID transaction id
	 * @return actual reference id
	 * @throws SearchDAOException on error getting the reference id
	 */
	public String getRefIDByTrxID(String trxID) throws SearchDAOException;

	/**
	 * Get staging reference id given its transaction id.
	 * 
	 * @param trxID transaction id
	 * @return staging reference id
	 * @throws SearchDAOException on error getting the staging reference id
	 */
	public String getStagingRefIDByTrxID(String trxID) throws SearchDAOException;

	/**
	 * Based on the reference ids to retrieve the transaction status and the stp
	 * ready indicator
	 * 
	 * @param referenceIds the reference ids, which is the cms collateral id
	 * @return a map which key is the cms collateral id, value is the instance
	 *         of {@link StpTrxStatusReadyIndicator}
	 */
	public Map retrieveTrxStatusByRefIds(Long[] cmsCollateralIds);

}
