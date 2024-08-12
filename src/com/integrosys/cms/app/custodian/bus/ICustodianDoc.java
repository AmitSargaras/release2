/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/ICustodianDoc.java,v 1.17 2005/08/09 04:39:37 whuang Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//java
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * This interface defines the list of attributes that will be available to the
 * custodian document
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/08/09 04:39:37 $ Tag: $Name: $
 */

public interface ICustodianDoc extends Serializable, IValueObject {
	// db fields
	public long getCustodianDocID();

	public long getLimitProfileID();

	public long getCheckListID();

	public long getPledgorID();

	public long getSubProfileID();

	public String getDocType();

	public String getDocSubType();

	public long getVersionTime();

	public Date getLastUpdateDate();

	public ArrayList getUpdatedCheckListItemRefArrayList();// CR-107

	public ICustodianDocItem getCustodianDocItem(int index);

	public ArrayList getCustodianDocItems();

	// CR34
	public String getReversalRemarks();

	public long getRevRemarksUpdatedBy();

	public long getReversalID();

	public String getReversalRmkUpdatedUserInfo();

	public void setReversalRemarks(String reversalRemarks);

	public void setRevRemarksUpdatedBy(long revRemarksUpdatedBy);

	public void setReversalID(long reversalID);

	public void setReversalRmkUpdatedUserInfo(String userInfo);

	public void setCustodianDocID(long aCustodianDocID);

	public void setLimitProfileID(long aLimitProfileID);

	public void setCheckListID(long aCheckListID);

	public void setPledgorID(long aPledgorID);

	public void setSubProfileID(long aSubProfileID);

	public void setDocType(String aDocType);

	public void setDocSubType(String aDocSubType);

	public void setVersionTime(long versionTime);

	public void setLastUpdateDate(Date lastUpdateDate);

	public void setUpdatedCheckListItemRefArrayList(ArrayList checkListItemRefStr);// CR
																					// -
																					// 107

	public void addCustodianDocItem(ICustodianDocItem item);

	public void removeCustodianDocItem(ICustodianDocItem item);

	public void setCustodianDocItems(ArrayList items);

	public CCCustodianInfo getCCCustodianInfo();

	public CollateralCustodianInfo getCollateralCustodianInfo();

	public long getCollateralID();

	public void setCCCustodianInfo(CCCustodianInfo aCCCustodianInfo);

	public void setCollateralCustodianInfo(CollateralCustodianInfo aCollateralCustodianInfo);

	public void setCollateralID(long aCollateralID);

}