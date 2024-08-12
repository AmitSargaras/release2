/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/ICustodianDocItemSearchResult.java,v 1.4 2005/10/10 08:04:24 lini Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//java
import java.io.Serializable;
import java.util.Date;

/**
 * This interface defines the list of attributes that will be available to the
 * search listing of custodian document item
 * 
 * @author $Author: lini $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/10/10 08:04:24 $ Tag: $Name: $
 */
public interface ICustodianDocItemSearchResult extends Serializable {
	public long getCustodianDocItemID();

	public String getDocNo();

	public long getDocItemRef();

	public String getDocDescription();

	public String getStatus();

	public String getItemStatus();

	public String getStageItemStatus();

	public String getCPCDisplayStatus();

	public String getDocType();

	public long getCheckListItemID();

	public String getCPCCustStatus();

	public Date getCPCCustDate();

	public Date getStageCPCDate();

	public String getDisplayStatus();

	public Date getTrxDate();

	public String getDocItemNarration();

	public Date getDocDate();

	public Date getDocExpiryDate();

    public String getSecEnvAdd();

       public String getSecEnvCab();

       public String getSecEnvDrw();

       public String getSecEnvBarcode();

    public void setCustodianDocItemID(long aCustodianDocItemID);

	public void setDocNo(String aDocNo);

	public void setDocItemRef(long aDocItemRef);

	public void setDocDescription(String aDocDescription);

	public void setStatus(String aStatus);

	public void setItemStatus(String itemStatus);

	public void setStageItemStatus(String stageItemStatus);

	public void setDocType(String aDocType);

	public void setCheckListItemID(long aCheckListItemID);

	public void setCPCCustStatus(String aCPCCustStatus);

	public void setCPCCustDate(Date aCPCCustDate);

	public void setStageCPCDate(Date stageCpcDate);

	public void setTrxDate(Date trxDate);

	public void setDocItemNarration(String narration);

	public void setDocDate(Date docDate);

	public void setDocExpiryDate(Date docExpiryDate);

    public void setSecEnvAdd(String SecEnvAdd);

        public void setSecEnvCab(String SecEnvCab);

        public void setSecEnvDrw(String SecEnvDrw);

        public void setSecEnvBarcode(String setEnvBarCode);


}
