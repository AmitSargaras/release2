package com.integrosys.cms.app.checklist.bus;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Aug 28, 2006 Time: 7:29:03 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISecurityCustomer extends Serializable {
	public long getSecurityID();

	public void setSecurityID(long securityID);

	public long getCollateralID();

	public void setCollateralID(long collateralID);

	public String getCustomerCategory();

	public void setCustomerCategory(String customerCategory);

	public long getCustomerID();

	public void setCustomerID(long customerID);

	public long getLeID();

	public void setLeID(long leID);

	public String getLeName();

	public void setLeName(String leName);

	public long getLspID();

	public void setLspID(long lspID);

	public String getSegment();

	public void setSegment(String segment);

}
