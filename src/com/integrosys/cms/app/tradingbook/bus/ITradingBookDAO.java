/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Interface for trading book DAO.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface ITradingBookDAO {
	public ICPAgreementDetail getCounterPartyAgreementSummary(long limitProfileID, long agreementID)
			throws SearchDAOException;

	public ICPAgreementDetail getCounterPartyAgreementSummary(long agreementID) throws SearchDAOException;

	public IDealValuation[] getDealValuationByAgreementID(long agreementID) throws SearchDAOException;

	public ICashMargin[] getCashMarginByAgreementID(long agreementID) throws SearchDAOException;

}
