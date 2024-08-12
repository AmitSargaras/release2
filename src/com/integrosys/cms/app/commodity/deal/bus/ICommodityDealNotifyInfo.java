/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/ICommodityDealNotifyInfo.java,v 1.4 2004/11/30 03:50:28 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/11/30 03:50:28 $ Tag: $Name: $
 */
public interface ICommodityDealNotifyInfo extends java.io.Serializable {

	public String getTransactionID();

	public void setTransactionID(String transactionID);

	public String getOperationName();

	public void setOperationName(String operationName);

	public ICommodityDeal getDeal();

	public void setDeal(ICommodityDeal deal);

	public ICommodityCollateral getCommodity();

	public void setCommodity(ICommodityCollateral commodity);

	public String getOriginatingCountry();

	public void setOriginatingCountry(String originatingCountry);

	public long getCustomerID();

	public void setCustomerID(long customerID);

	public String getCustomerName();

	public void setCustomerName(String customerName);

	public long getTrxUserID();

	public void setTrxUserID(long trxUserID);

	public long getTrxUserTeamID();

	public void setTrxUserTeamID(long trxUserTeamID);
}
