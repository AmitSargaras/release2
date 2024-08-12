/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ICheckListSummary.java,v 1.5 2006/08/14 10:58:33 jzhai Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author $Author: jzhai $
 * @version $Revision: 1.5 $
 * @since $Date: 2006/08/14 10:58:33 $ Tag: $Name: $
 */
public interface ICheckListSummary extends java.io.Serializable {
	public long getCheckListID();

	public void setCheckListID(long aCheckListID);

	public long getCheckListReferenceID();

	public void setCheckListReferenceID(long aCheckListID);

	public String getCheckListStatus();

	public void setCheckListStatus(String aCheckListStatus);

	public boolean getAllowDeleteInd();

	public void setAllowDeleteInd(boolean anAllowDeleteInd);

	public boolean getSameCountryInd();

	public void setSameCountryInd(boolean aSameCountryInd);

	public String getTrxID();

	public void setTrxID(String trxID);

	public String getTrxStatus();

	public void setTrxStatus(String status);

	public String getTrxFromState();

	public void setTrxFromState(String fromState);

	public ICheckListTrxValue getTrxValue();

	public void setTrxValue(ICheckListTrxValue trxValue);

	public String getDisableTaskInd();

	public void setDisableTaskInd(String aDisableTaskInd);

	public ICMSTrxValue getTaskTrx();

	public void setTaskTrx(ICMSTrxValue taskStageTrx);

	public String getCustCategory();

	public void setCustCategory(String aCustCategory);

	public String getApplicationType();

	public void setApplicationType(String applicationType);
}
