package com.integrosys.cms.ui.cci;

import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.ui.customer.CustomerSearchForm;

import java.util.List;

public class CounterpartySearchForm extends CustomerSearchForm implements java.io.Serializable {

    private String groupCCINo = "";
    private String customerSeach = "";
  //  private String pagination = "";


    private String[] customerID ;
    private String[] selectCustomerID ;
    private String[] chkDeletes ;


    private ICCICounterpartyDetails ICCICounterpartyDetails ;
    private List ICCICounterparty ;


    public String getCustomerSeach() {
        return customerSeach;
    }

    public void setCustomerSeach(String customerSeach) {
        this.customerSeach = customerSeach;
    }

  /*  public String getPagination() {
        return pagination;
    }

    public void setPagination(String pagination) {
        this.pagination = pagination;
    }
*/

    public ICCICounterpartyDetails getICCICounterpartyDetails() {
        return ICCICounterpartyDetails;
    }

    public void setICCICounterpartyDetails(ICCICounterpartyDetails ICCICounterpartyDetails) {
        this.ICCICounterpartyDetails = ICCICounterpartyDetails;
    }

    public List getICCICounterparty() {
        return ICCICounterparty;
    }

    public void setICCICounterparty(List ICCICounterparty) {
        this.ICCICounterparty = ICCICounterparty;
    }


    /**
	 * @return the selectCustomerID
	 */
	public final String[] getSelectCustomerID() {
		return selectCustomerID;
	}

	/**
	 * @param selectCustomerID the selectCustomerID to set
	 */
	public final void setSelectCustomerID(String[] selectCustomerID) {
		this.selectCustomerID = selectCustomerID;
	}

	public String[] getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String[] customerID) {
        this.customerID = customerID;
    }

    public String[] getChkDeletes() {
        return chkDeletes;
    }

    public void setChkDeletes(String[] chkDeletes) {
        this.chkDeletes = chkDeletes;
    }

    public String getGroupCCINo() {
        return groupCCINo;
    }

    public void setGroupCCINo(String groupCCINo) {
        this.groupCCINo = groupCCINo;
    }

    public void reset() {
           setCustomerName("");
           setLegalID("");
           setLeIDType("");
           setIdNO("");
           setGroupCCINo("");
       }


     public String[][] getMapper() {

        String[][] input = {
                { "DeleteCounterpartyListMapper", "com.integrosys.cms.ui.cci.DeleteCounterpartyListMapper" },
                { "counterpartySearchCriteria", "com.integrosys.cms.ui.cci.CounterpartySearchMapper" },
                { "counterpartyList", "com.integrosys.cms.ui.cci.CounterpartySearchListMapper" },

                {"ICCICounterpartyDetails", "com.integrosys.cms.ui.cci.CCICounterpartyDetailsMapper"},

                { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
                { MAPPER, MAPPER },
				{ "aCustomerSearchCriteria", "com.integrosys.cms.ui.customer.CustomerSearchMapper" },
				{ "customerSearchCriteria", "com.integrosys.cms.ui.customer.CustomerListMapper" },
				{ "customerList", "com.integrosys.cms.ui.customer.CustomerListMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" }
        };

        return input;
    }

    public static final String MAPPER = "com.integrosys.cms.ui.cci.CounterpartySearchMapper";
}
