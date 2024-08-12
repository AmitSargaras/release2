/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.bankentitybranch;

import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimit;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryRating;

import java.io.Serializable;
import java.util.Collection;

public interface IBankEntityBranchParamList extends Serializable {

   public String getLastActionBy();

    public void setLastActionBy(String lastActionBy);

    public String getLastRemarks();

    public void setLastRemarks(String lastRemarks);

    public String getRemarks();

    public void setRemarks(String remarks);

    public Collection getBankEntityBranchParams();

    public void setBankEntityBranchParams(Collection bankEntityBranchParams);

    public String[] getDeleteItems();

    public void setDeleteItems(String[] deleteItems);

}