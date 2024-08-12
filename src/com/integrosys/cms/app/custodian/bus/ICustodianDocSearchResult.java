/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/ICustodianDocSearchResult.java,v 1.13 2006/08/30 12:08:57 jzhai Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//java
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * This interface defines the list of attributes that will be available to the
 * search listing of custodian document
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2006/08/30 12:08:57 $ Tag: $Name: $
 */
public interface ICustodianDocSearchResult extends Serializable {
	public long getCustodianDocID();

	public long getCheckListID();

	public String getCheckListStatus();

	public String getTrxId();

	public String getTrxStatus();

	public Date getTrxDate();

	public String getCategory();

	public String getSubCategory();

	public long getLimitProfileID();

	public long getSubProfileID();

	public long getCollateralID();

	public long getPledgorID();  

	public ICustodianDocItemSearchResult[] getCustodianDocItems();

	public String getReversalRemarks();

	public String getReversalRmkUpdatedUserInfo();

	public void setCustodianDocID(long aCustodianDocID);

	public void setCheckListID(long aCheckListID);

	public void setCheckListStatus(String aCheckListStatus);

	public void setTrxId(String aTrxId);

	public void setTrxStatus(String aTrxStatus);

	public void setTrxDate(Date aTrxDate);

	public void setCategory(String category);

	public void setSubCategory(String subCategory);

	public void setLimitProfileID(long limitProfileID);

	public void setSubProfileID(long subProfileID);

	public void setCollateralID(long collateralID);

	public void setPledgorID(long pledgorID);       

	public void setCustodianDocItems(ICustodianDocItemSearchResult[] items);

	public void setReversalRemarks(String remarks);

	public void setReversalRmkUpdatedUserInfo(String userInfo);

	public String getLegalID();    //edit

	public void setLegalID(String legalID);  //edit

	public String getLegalName();

	public void setLegalName(String legalName);

	public ArrayList getCustomerList();

	public void setCustomerList(ArrayList customerList);

}
