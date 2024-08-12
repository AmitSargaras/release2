/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/price/ICommodityPriceTrxDAOConstants.java,v 1.2 2004/06/04 04:53:53 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.price;

/**
 * Interface that defines constants for table and column names used in Commodity
 * Price trx DAO.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:53:53 $ Tag: $Name: $
 */
public interface ICommodityPriceTrxDAOConstants {
	// table name and column namd for transaction
	public static final String TRX_TABLE = "TRANSACTION";

	public static final String TRX_TRX_ID = "TRANSACTION_ID";

	public static final String TRX_REF_ID = "REFERENCE_ID";

	public static final String TRX_STAGE_REF_ID = "STAGING_REFERENCE_ID";

	public static final String TRX_TRX_TYPE = "TRANSACTION_TYPE";

	public static final String TRX_STATUS = "STATUS";
}
