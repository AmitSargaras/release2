/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genddn;

import com.integrosys.cms.ui.common.TrxContextForm;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/07/25 17:58:52 $ Tag: $Name: $
 */

public class GenerateDDNForm extends TrxContextForm implements Serializable {

	//private String bcaAppDate = "";

	private String creditOfficerName = "";

	private String seniorCreditOfficerName = "";

	private String creditOfficerSgnNo = "";

	private String seniorCreditOfficerSgnNo = "";

	//private String creditOfficerLocationCountry;

	//private String seniorCreditOfficerLocationCountry;

	//private String creditOfficerLocationOrgCode;

	//private String seniorCreditOfficerLocationOrgCode;

	private String remarksSCC = "";

	//private String[] ddnLimit;

	//private String ddnDate;

	//private String ddnDays;

	//private String ddnExtDate;

	//private String appDate;

	//private String approvedBy = "";

	//private String ddnIssuedIndex = "";

	//private String checkedIndexes = "";

	//private String[] appAmtCurrCode;

	//private String[] appLimit;

	//private String[] maturityDate;

	// private String[] ddnLimitLeft; //cr36

    private String releaseTo = "";

    private String[] docNumber;

    private String[] docCode;

    private String[] docDesc;

    private String[] dateDefer;

    private String[] dateOfReturn;

    private String[] docStatus;

    private String[] actionParty;

    private String[] theApprovalDate;

    private String[] approvedBy;

    /*
	public String[] getAppAmtCurrCode() {
		return appAmtCurrCode;
	}

	public void setAppAmtCurrCode(String[] appAmtCurrCode) {
		this.appAmtCurrCode = appAmtCurrCode;
	}

	public String[] getAppLimit() {
		return appLimit;
	}

	public void setAppLimit(String[] appLimit) {
		this.appLimit = appLimit;
	}

	public String getCheckedIndexes() {
		return checkedIndexes;
	}

	public void setCheckedIndexes(String checkedIndexes) {
		this.checkedIndexes = checkedIndexes;
	}

	public String getBcaAppDate() {
		return bcaAppDate;
	}

	public void setBcaAppDate(String aBcaAppDate) {
		bcaAppDate = aBcaAppDate;
	}

	public String getDdnIssuedIndex() {
		return ddnIssuedIndex;
	}

	public void setDdnIssuedIndex(String ddnIssuedIndex) {
		this.ddnIssuedIndex = ddnIssuedIndex;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getDdnExtDate() {
		return ddnExtDate;
	}

	public void setDdnExtDate(String ddnExtDate) {
		this.ddnExtDate = ddnExtDate;
	}

	public String getDdnDate() {
		return ddnDate;
	}

	public void setDdnDate(String ddnDate) {
		this.ddnDate = ddnDate;
	}

	public String getDdnDays() {
		return ddnDays;
	}

	public void setDdnDays(String ddnDays) {
		this.ddnDays = ddnDays;
	}

	public String[] getDdnLimit() {
		return ddnLimit;
	}

	public void setDdnLimit(String[] ddnLimit) {
		this.ddnLimit = ddnLimit;
	}
	*/

	/*
	 * public String[] getDdnLimitLeft() { return ddnLimitLeft; }
	 * 
	 * public void setDdnLimitLeft(String[] ddnLimitLeft) { this.ddnLimitLeft =
	 * ddnLimitLeft; }
	 */
	public String getCreditOfficerName() {
		return creditOfficerName;
	}

	public void setCreditOfficerName(String creditOfficerName) {
		this.creditOfficerName = creditOfficerName;
	}

	public String getCreditOfficerSgnNo() {
		return creditOfficerSgnNo;
	}

	public void setCreditOfficerSgnNo(String creditOfficerSgnNo) {
		this.creditOfficerSgnNo = creditOfficerSgnNo;
	}

	public String getRemarksSCC() {
		return remarksSCC;
	}

	public void setRemarksSCC(String remarksSCC) {
		this.remarksSCC = remarksSCC;
	}

	public String getSeniorCreditOfficerName() {
		return seniorCreditOfficerName;
	}

	public void setSeniorCreditOfficerName(String seniorCreditOfficerName) {
		this.seniorCreditOfficerName = seniorCreditOfficerName;
	}

	public String getSeniorCreditOfficerSgnNo() {
		return seniorCreditOfficerSgnNo;
	}

	public void setSeniorCreditOfficerSgnNo(String seniorCreditOfficerSgnNo) {
		this.seniorCreditOfficerSgnNo = seniorCreditOfficerSgnNo;
	}
    /*
	public String getCreditOfficerLocationCountry() {
		return creditOfficerLocationCountry;
	}

	public void setCreditOfficerLocationCountry(String creditOfficerLocationCountry) {
		this.creditOfficerLocationCountry = creditOfficerLocationCountry;
	}

	public String getSeniorCreditOfficerLocationCountry() {
		return seniorCreditOfficerLocationCountry;
	}

	public void setSeniorCreditOfficerLocationCountry(String seniorCreditOfficerLocationCountry) {
		this.seniorCreditOfficerLocationCountry = seniorCreditOfficerLocationCountry;
	}

	public String getCreditOfficerLocationOrgCode() {
		return creditOfficerLocationOrgCode;
	}

	public void setCreditOfficerLocationOrgCode(String creditOfficerLocationOrgCode) {
		this.creditOfficerLocationOrgCode = creditOfficerLocationOrgCode;
	}

	public String getSeniorCreditOfficerLocationOrgCode() {
		return seniorCreditOfficerLocationOrgCode;
	}

	public void setSeniorCreditOfficerLocationOrgCode(String seniorCreditOfficerLocationOrgCode) {
		this.seniorCreditOfficerLocationOrgCode = seniorCreditOfficerLocationOrgCode;
	}

	public String[] getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String[] maturityDate) {
		this.maturityDate = maturityDate;
	}
    */

    public String getReleaseTo() {
        return releaseTo;
    }

    public void setReleaseTo(String releaseTo) {
        this.releaseTo = releaseTo;
    }

    public String[] getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String[] docNumber) {
        this.docNumber = docNumber;
    }

    public String[] getDocCode() {
        return docCode;
    }

    public void setDocCode(String[] docCode) {
        this.docCode = docCode;
    }

    public String[] getDocDesc() {
        return docDesc;
    }

    public void setDocDesc(String[] docDesc) {
        this.docDesc = docDesc;
    }

    public String[] getDateDefer() {
        return dateDefer;
    }

    public void setDateDefer(String[] dateDefer) {
        this.dateDefer = dateDefer;
    }

    public String[] getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(String[] dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public String[] getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String[] docStatus) {
        this.docStatus = docStatus;
    }

    public String[] getActionParty() {
        return actionParty;
    }

    public void setActionParty(String[] actionParty) {
        this.actionParty = actionParty;
    }

    public String[] getTheApprovalDate() {
        return theApprovalDate;
    }

    public void setTheApprovalDate(String[] theApprovalDate) {
        this.theApprovalDate = theApprovalDate;
    }

    public String[] getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String[] approvedBy) {
        this.approvedBy = approvedBy;
    }

    /**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax (keyMapperclassname)
	 * 
	 * @return two-dimesnional String Array
	 */
	public String[][] getMapper() {
		String[][] input = { { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				// {"limitProfile",
				// "com.integrosys.cms.ui.genscc.LimitProfileMapper"},
				{ "cert", "com.integrosys.cms.ui.genddn.DDNMapper" },
				{ "certPrev", "com.integrosys.cms.ui.genddn.DDNMapper" }

		};
		return input;
	}

    public String toString() {
        return "GenerateDDNForm{" +
                "creditOfficerName='" + creditOfficerName + '\'' +
                ", seniorCreditOfficerName='" + seniorCreditOfficerName + '\'' +
                ", creditOfficerSgnNo='" + creditOfficerSgnNo + '\'' +
                ", seniorCreditOfficerSgnNo='" + seniorCreditOfficerSgnNo + '\'' +
                ", remarksSCC='" + remarksSCC + '\'' +
                ", releaseTo='" + releaseTo + '\'' +
                ", docNumber=" + (docNumber == null ? null : Arrays.asList(docNumber)) +
                ", docCode=" + (docCode == null ? null : Arrays.asList(docCode)) +
                ", docDesc=" + (docDesc == null ? null : Arrays.asList(docDesc)) +
                ", dateDefer=" + (dateDefer == null ? null : Arrays.asList(dateDefer)) +
                ", dateOfReturn=" + (dateOfReturn == null ? null : Arrays.asList(dateOfReturn)) +
                ", docStatus=" + (docStatus == null ? null : Arrays.asList(docStatus)) +
                ", actionParty=" + (actionParty == null ? null : Arrays.asList(actionParty)) +
                ", theApprovalDate=" + (theApprovalDate == null ? null : Arrays.asList(theApprovalDate)) +
                ", approvedBy=" + (approvedBy == null ? null : Arrays.asList(approvedBy)) +
                '}';
    }
}
