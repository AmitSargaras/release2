/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.creditriskparam.bus;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * ICreditRiskParamGroup Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface ICreditRiskParamGroup extends java.io.Serializable, IValueObject {

	OBCreditRiskParam[] getFeedEntries();

	String getType();

	String getSubType();

	String getSubTypeDescription();

	String getStockType();

	String getStockTypeDescription();

	long getCreditRiskParamGroupID();

	void setFeedEntries(OBCreditRiskParam[] param);

	void setType(String param);

	void setSubType(String param);

	void setSubTypeDescription(String param);

	void setStockType(String param);

	void setStockTypeDescription(String param);

	void setCreditRiskParamGroupID(long param);

	long getVersionTime();

	void setVersionTime(long versionTime);
}
