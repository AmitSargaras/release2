package com.integrosys.cms.ui.tatdoc;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.common.TrxContextForm;

public class TatDocForm extends TrxContextForm implements java.io.Serializable {

	private static final long serialVersionUID = 2195521128031798305L;

	// page requirement
	private String limitProfileID;

    private String tatTrackID;

	// Customer Details
	private String cif;

	private String customerName;

	// Pre-Disbursement
	private String solicitorInstructionDate;

	private String fileFromBizCenterDate;

	private String[] documentReceivedDate;

	private String[] docSentToLegalDeptDate;

	private String[] docReceivedFromLegalDeptDate;

	private String[] documentReturnedDate;

	private String docReadyForPADate;

	private String PAExecutedDate;

    /* Using Variable - Pre */
	private String preDisburseRemarks;

    private String[] preTrackingStageID;

    private String[] preTatParamItemID;

    private String[] preStartDateString;

    private String[] preEndDateString;

    private String[] preActualDateString;

    private String[] preTatApplicable;

    private String[] preReason;

    private String[] preIsStageActive;
    /* End Using Variable - Pre */

	// Disbursement
	private String solicitorAdviseReleaseDate;

	private String disbursementDocCompletedDate;

	private String disbursementDate;

    /* Using Variable  - In */
	private String disbursementRemarks;

    private String[] inTrackingStageID;

    private String[] inTatParamItemID;

    private String[] inStartDateString;

    private String[] inEndDateString;

    private String[] inActualDateString;

    private String[] inTatApplicable;

    private String[] inReason;

    private String[] inIsStageActive;
    /* End Using Variable - In */

	// Post-Disbursement
	private String docCompletionDate;

//	private String postDisbursementRemarks;

	private String oldDisDocComDateExit;

	private String approvalDate;

	private String isPAOrSolInvolvementReq;

    /* Using Variable  - Post */
    private String postDisburseRemarks;

    private String[] postTrackingStageID;

    private String[] postTatParamItemID;

    private String[] postStartDateString;

    private String[] postEndDateString;

    private String[] postActualDateString;

    private String[] postTatApplicable;

    private String[] postReason;

    private String[] postIsStageActive;
    /* End Using Variable - Post */

    private String allowConfirm;

	public static final String MAPPER = "com.integrosys.cms.ui.tatdoc.TatDocMapper";

	// Other
	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "previousDisbursementCompletionDate", "com.integrosys.cms.ui.tatdoc.TatDocFormCompletionMapper" } };
	}

	// Setter & Getter

	public String getIsPAOrSolInvolvementReq() {
		return isPAOrSolInvolvementReq;
	}

	public void setIsPAOrSolInvolvementReq(String isPAOrSolInvolvementReq) {
		this.isPAOrSolInvolvementReq = isPAOrSolInvolvementReq;
	}

	public String getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getOldDisDocComDateExit() {
		return oldDisDocComDateExit;
	}

	public void setOldDisDocComDateExit(String oldDisDocComDateExit) {
		this.oldDisDocComDateExit = oldDisDocComDateExit;
	}

	public String getLimitProfileID() {
		return limitProfileID;
	}

	public void setLimitProfileID(String limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSolicitorInstructionDate() {
		return solicitorInstructionDate;
	}

	public void setSolicitorInstructionDate(String solicitorInstructionDate) {
		this.solicitorInstructionDate = solicitorInstructionDate;
	}

	public String getFileFromBizCenterDate() {
		return fileFromBizCenterDate;
	}

	public void setFileFromBizCenterDate(String fileFromBizCenterDate) {
		this.fileFromBizCenterDate = fileFromBizCenterDate;
	}

	public String[] getDocumentReceivedDate() {
		return documentReceivedDate;
	}

	public void setDocumentReceivedDate(String[] documentReceivedDate) {
		this.documentReceivedDate = documentReceivedDate;
	}

	/*
	 * public String getDocumentReceivedDate(int index) { return
	 * documentReceivedDate[index]; }
	 * 
	 * public void setDocumentReceivedDate(int index, String
	 * documentReceivedDate) { this.documentReceivedDate[index] =
	 * documentReceivedDate; }
	 */

	public String[] getDocSentToLegalDeptDate() {
		return docSentToLegalDeptDate;
	}

	public void setDocSentToLegalDeptDate(String[] docSentToLegalDeptDate) {
		this.docSentToLegalDeptDate = docSentToLegalDeptDate;
	}

	public String[] getDocReceivedFromLegalDeptDate() {
		return docReceivedFromLegalDeptDate;
	}

	public void setDocReceivedFromLegalDeptDate(String[] docReceivedFromLegalDeptDate) {
		this.docReceivedFromLegalDeptDate = docReceivedFromLegalDeptDate;
	}

	public String[] getDocumentReturnedDate() {
		return documentReturnedDate;
	}

	public void setDocumentReturnedDate(String[] documentReturnedDate) {
		this.documentReturnedDate = documentReturnedDate;
	}

	public String getDocReadyForPADate() {
		return docReadyForPADate;
	}

	public void setDocReadyForPADate(String docReadyForPADate) {
		this.docReadyForPADate = docReadyForPADate;
	}

	public String getPAExecutedDate() {
		return PAExecutedDate;
	}

	public void setPAExecutedDate(String executedDate) {
		PAExecutedDate = executedDate;
	}

	public String getPreDisburseRemarks() {
		return preDisburseRemarks;
	}

	public void setPreDisburseRemarks(String preDisburseRemarks) {
		this.preDisburseRemarks = preDisburseRemarks;
	}

	public String getSolicitorAdviseReleaseDate() {
		return solicitorAdviseReleaseDate;
	}

	public void setSolicitorAdviseReleaseDate(String solicitorAdviseReleaseDate) {
		this.solicitorAdviseReleaseDate = solicitorAdviseReleaseDate;
	}

	public String getDisbursementDocCompletedDate() {
		return disbursementDocCompletedDate;
	}

	public void setDisbursementDocCompletedDate(String disbursementDocCompletedDate) {
		this.disbursementDocCompletedDate = disbursementDocCompletedDate;
	}

	public String getDisbursementDate() {
		return disbursementDate;
	}

	public void setDisbursementDate(String disbursementDate) {
		this.disbursementDate = disbursementDate;
	}

	public String getDisbursementRemarks() {
		return disbursementRemarks;
	}

	public void setDisbursementRemarks(String disbursementRemarks) {
		this.disbursementRemarks = disbursementRemarks;
	}

	public String getDocCompletionDate() {
		return docCompletionDate;
	}

	public void setDocCompletionDate(String docCompletionDate) {
		this.docCompletionDate = docCompletionDate;
	}

//	public String getPostDisbursementRemarks() {
//		return postDisbursementRemarks;
//	}
//
//	public void setPostDisbursementRemarks(String postDisbursementRemarks) {
//		this.postDisbursementRemarks = postDisbursementRemarks;
//	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

    public String[] getPreTrackingStageID() {
        return preTrackingStageID;
    }

    public void setPreTrackingStageID(String[] preTrackingStageID) {
        this.preTrackingStageID = preTrackingStageID;
    }

    public String[] getPreTatParamItemID() {
        return preTatParamItemID;
    }

    public void setPreTatParamItemID(String[] preTatParamItemID) {
        this.preTatParamItemID = preTatParamItemID;
    }

    public String[] getPreStartDateString() {
        return preStartDateString;
    }

    public void setPreStartDateString(String[] preStartDateString) {
        this.preStartDateString = preStartDateString;
    }

    public String[] getPreEndDateString() {
        return preEndDateString;
    }

    public void setPreEndDateString(String[] preEndDateString) {
        this.preEndDateString = preEndDateString;
    }

    public String[] getPreActualDateString() {
        return preActualDateString;
    }

    public void setPreActualDateString(String[] preActualDateString) {
        this.preActualDateString = preActualDateString;
    }

    public String[] getPreTatApplicable() {
        return preTatApplicable;
    }

    public void setPreTatApplicable(String[] preTatApplicable) {
        this.preTatApplicable = preTatApplicable;
    }

    public String[] getPreReason() {
        return preReason;
    }

    public void setPreReason(String[] preReason) {
        this.preReason = preReason;
    }

    public String[] getInTrackingStageID() {
        return inTrackingStageID;
    }

    public void setInTrackingStageID(String[] inTrackingStageID) {
        this.inTrackingStageID = inTrackingStageID;
    }

    public String[] getInTatParamItemID() {
        return inTatParamItemID;
    }

    public void setInTatParamItemID(String[] inTatParamItemID) {
        this.inTatParamItemID = inTatParamItemID;
    }

    public String[] getInStartDateString() {
        return inStartDateString;
    }

    public void setInStartDateString(String[] inStartDateString) {
        this.inStartDateString = inStartDateString;
    }

    public String[] getInEndDateString() {
        return inEndDateString;
    }

    public void setInEndDateString(String[] inEndDateString) {
        this.inEndDateString = inEndDateString;
    }

    public String[] getInActualDateString() {
        return inActualDateString;
    }

    public void setInActualDateString(String[] inActualDateString) {
        this.inActualDateString = inActualDateString;
    }

    public String[] getInTatApplicable() {
        return inTatApplicable;
    }

    public void setInTatApplicable(String[] inTatApplicable) {
        this.inTatApplicable = inTatApplicable;
    }

    public String[] getInReason() {
        return inReason;
    }

    public void setInReason(String[] inReason) {
        this.inReason = inReason;
    }

    public String getPostDisburseRemarks() {
        return postDisburseRemarks;
    }

    public void setPostDisburseRemarks(String postDisburseRemarks) {
        this.postDisburseRemarks = postDisburseRemarks;
    }

    public String[] getPostTrackingStageID() {
        return postTrackingStageID;
    }

    public void setPostTrackingStageID(String[] postTrackingStageID) {
        this.postTrackingStageID = postTrackingStageID;
    }

    public String[] getPostTatParamItemID() {
        return postTatParamItemID;
    }

    public void setPostTatParamItemID(String[] postTatParamItemID) {
        this.postTatParamItemID = postTatParamItemID;
    }

    public String[] getPostStartDateString() {
        return postStartDateString;
    }

    public void setPostStartDateString(String[] postStartDateString) {
        this.postStartDateString = postStartDateString;
    }

    public String[] getPostEndDateString() {
        return postEndDateString;
    }

    public void setPostEndDateString(String[] postEndDateString) {
        this.postEndDateString = postEndDateString;
    }

    public String[] getPostActualDateString() {
        return postActualDateString;
    }

    public void setPostActualDateString(String[] postActualDateString) {
        this.postActualDateString = postActualDateString;
    }

    public String[] getPostTatApplicable() {
        return postTatApplicable;
    }

    public void setPostTatApplicable(String[] postTatApplicable) {
        this.postTatApplicable = postTatApplicable;
    }

    public String[] getPostReason() {
        return postReason;
    }

    public void setPostReason(String[] postReason) {
        this.postReason = postReason;
    }

    public String[] getPreIsStageActive() {
        return preIsStageActive;
    }

    public void setPreIsStageActive(String[] preIsStageActive) {
        this.preIsStageActive = preIsStageActive;
    }

    public String[] getInIsStageActive() {
        return inIsStageActive;
    }

    public void setInIsStageActive(String[] inIsStageActive) {
        this.inIsStageActive = inIsStageActive;
    }

    public String[] getPostIsStageActive() {
        return postIsStageActive;
    }

    public void setPostIsStageActive(String[] postIsStageActive) {
        this.postIsStageActive = postIsStageActive;
    }

    public String getAllowConfirm() {
        return allowConfirm;
    }

    public void setAllowConfirm(String allowConfirm) {
        this.allowConfirm = allowConfirm;
    }

    public String getTatTrackID() {
        return tatTrackID;
    }

    public void setTatTrackID(String tatTrackID) {
        this.tatTrackID = tatTrackID;
    }
}
