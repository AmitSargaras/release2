/**
* Copyright Integro Technologies Pte Ltd
*/
package com.integrosys.cms.ui.creditriskparam.internallimit.list;

import java.io.Serializable;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.common.TrxContextForm;

/**
* Describe this class.
* Purpose: To set get and set method for the value needed by Internal Limit Parameter
* Description: Have set and get method to store the screen value and get the value from other command class
*
* @author $Author: Grace Teh$<br>
* @version $Revision$
* @since $Date: 28/05/2008$
* Tag: $Name$
*/

public class InternalLmtParameterForm extends TrxContextForm implements Serializable {

	  private static final long serialVersionUID = 1L;
	  private long groupId;
    private String internalLmtDesc[];
    private String capitalFundAmt[];
    private String totalLoanAdvAmt[];
    private String gp5LmtPercentage[];  
    private String internalLmtPercentage[];
    private String capitalFundAmtCurrencyCode[];
    private String totalLoanAdvAmtCurrencyCode[];
    private String status[];



    public String[] getInternalLmtDesc() {

        return internalLmtDesc;

    }

    public void setInternalLmtDesc(String[] internalLmtDesc) {

        this.internalLmtDesc = internalLmtDesc;

    }

    public String[] getCapitalFundAmt() {

        return capitalFundAmt;

    }

    public void setCapitalFundAmt(String[] capitalFundAmt) {

        this.capitalFundAmt = capitalFundAmt;

    }

    public String[] getTotalLoanAdvAmt() {

        return totalLoanAdvAmt;

    }


    public void setTotalLoanAdvAmt(String[] totalLoanAdvAmt) {

        this.totalLoanAdvAmt = totalLoanAdvAmt;

    }


    public String[] getGp5LmtPercentage() {

        return gp5LmtPercentage;

    }

    public void setGp5LmtPercentage(String[] gp5LmtPercentage) {

        this.gp5LmtPercentage = gp5LmtPercentage;

    }

    public String[] getInternalLmtPercentage() {

        return internalLmtPercentage;

    }

    public void setInternalLmtPercentage(String[] internalLmtPercentage) {

        this.internalLmtPercentage = internalLmtPercentage;

    }

    public String[] getCapitalFundAmtCurrencyCode() {

        return capitalFundAmtCurrencyCode;

    }

    public void setCapitalFundAmtCurrencyCode(String[] capitalFundAmtCurrencyCode) {

        this.capitalFundAmtCurrencyCode = capitalFundAmtCurrencyCode;

    }
   
   public String[] getTotalLoanAdvAmtCurrencyCode() {

        return totalLoanAdvAmtCurrencyCode;

    }

    public void setTotalLoanAdvAmtCurrencyCode(String[] totalLoanAdvAmtCurrencyCode) {

        this.totalLoanAdvAmtCurrencyCode = totalLoanAdvAmtCurrencyCode;

    }
               
    public String[] getStatus() {
		    return status;
	  }

	  public void setStatus(String[] status) {
		    this.status = status;
	  }
    
    public String toString() {

        return AccessorUtil.printMethodValue(this);

    }

    public String[][] getMapper() {

        String[][] input = {
                {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"},
                {"theILParamList", "com.integrosys.cms.ui.creditriskparam.internallimit.list.InternalLmtParameterMapper"},
                {"obInternalLmtParam", "com.integrosys.cms.ui.creditriskparam.internallimit.list.InternalLmtParameterMapper"},
             };

        return input;
    }
}
