package com.integrosys.cms.host.eai.limit.bus;

import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.OriginatingBookingLocation;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * A entity to represent a Credit Application sent/published from LOS system.
 * 
 * @author marvin
 * @author Chong Jun Yong
 */
public class LimitProfile implements java.io.Serializable {

	private static final long serialVersionUID = -8630622547439957716L;

	private long limitProfileId = ICMSConstant.LONG_INVALID_VALUE;

	private Long CMSLimitProfileId;

	private String CIFId;

	private String CIFSource;

	private long subProfileId;

	private String LOSAANumber;

	private String oldLOSAANumber;

	private String hostAANumber;

	private String AAApproveDate;

	private OriginatingBookingLocation originatingLocation;

	private Date annualReviewDate;

	private Date extensionDate;

	private String AAStatus;

	private Date AACreateDate;

	private String LORequired;

	private String AAType;

	private String AALawType;

	private Date extensionDateLOAcceptance;

	private String isMigratedIndicator = ICMSConstant.FALSE_VALUE;

	private String updateStatusIndicator;

	private String solicitorInstructionDate;

	private String changeIndicator;

	/**
	 * Following fields are not used by eai messaging and not mentioned in CA001
	 * message
	 **/
	private String AASource;

	private long cmsSubProfileId;

	private String limitProfileType;

	private MainProfile mainProfile;

	private String hasBLInd;

	private String hasCFInd;

	private String loginId;

	private String PASolicitorInvolvementRequiredInd;

	public String getAAApproveDate() {
		return AAApproveDate;
	}

	public String getAACreateDate() {
		return MessageDate.getInstance().getString(AACreateDate);
	}

	public String getAALawType() {
		return AALawType;
	}

	public String getAASource() {
		return AASource;
	}

	public String getAAStatus() {
		return AAStatus;
	}

	public String getAAType() {
		return AAType;
	}

	public String getAnnualReviewDate() {
		return MessageDate.getInstance().getString(annualReviewDate);
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public String getCIFId() {
		return CIFId;
	}

	public String getCIFSource() {
		return CIFSource;
	}

	public Long getCMSLimitProfileId() {
		return CMSLimitProfileId;
	}

	public long getCmsSubProfileId() {
		return cmsSubProfileId;
	}

	public String getExtensionDate() {
		return MessageDate.getInstance().getString(extensionDate);
	}

	public String getExtensionDateLOAcceptance() {
		return MessageDate.getInstance().getString(extensionDateLOAcceptance);
	}

	public String getHasBLInd() {
		return hasBLInd;
	}

	public String getHasCFInd() {
		return hasCFInd;
	}

	public String getHostAANumber() {
		return hostAANumber;
	}

	public String getIsMigratedIndicator() {
		return isMigratedIndicator;
	}

	public Date getJDOAAApproveDate() {
		return MessageDate.getInstance().getDate(AAApproveDate);
	}

	public Date getJDOAACreateDate() {
		return AACreateDate;
	}

	public Date getJDOAnnualReviewDate() {
		return annualReviewDate;
	}

	public Date getJDOExtensionDate() {
		return extensionDate;
	}

	public Date getJDOExtensionDateLOAcceptance() {
		return extensionDateLOAcceptance;
	}

	public Date getJDOSolicitorInstructionDate() {
		return MessageDate.getInstance().getDate(solicitorInstructionDate);
	}

	public long getLimitProfileId() {
		return limitProfileId;
	}

	public String getLimitProfileType() {
		if ((limitProfileType == null) || "".equals(limitProfileType)) {
			// Default is Banking
			limitProfileType = ICMSConstant.AA_TYPE_BANK;
		}
		return limitProfileType;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getLORequired() {
		return LORequired;
	}

	public String getLOSAANumber() {
		return LOSAANumber;
	}

	/**
	 * @return the mainProfile
	 */
	public final MainProfile getMainProfile() {
		return mainProfile;
	}

	public String getOldLOSAANumber() {
		return oldLOSAANumber;
	}

	public OriginatingBookingLocation getOriginatingLocation() {
		return originatingLocation;
	}

	public String getPASolicitorInvolvementRequiredInd() {
		return PASolicitorInvolvementRequiredInd;
	}

	public String getSolicitorInstructionDate() {
		return solicitorInstructionDate;
	}

	public long getSubProfileId() {
		return subProfileId;
		// return Long.parseLong(IEaiConstant.SUBPROFILE_ID);
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setAAApproveDate(String AAApproveDate) {
		this.AAApproveDate = AAApproveDate;
	}

	public void setAACreateDate(String AACreateDate) {
		this.AACreateDate = MessageDate.getInstance().getDate(AACreateDate);
	}

	public void setAALawType(String lawType) {
		AALawType = lawType;
	}

	public void setAASource(String AASource) {
		this.AASource = AASource;
	}

	public void setAAStatus(String AAStatus) {
		this.AAStatus = AAStatus;
	}

	public void setAAType(String AAType) {
		this.AAType = AAType;
	}

	public void setAnnualReviewDate(String annualReviewDate) {
		this.annualReviewDate = MessageDate.getInstance().getDate(annualReviewDate);
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setCIFId(String CIFId) {
		this.CIFId = CIFId;
	}

	public void setCIFSource(String CIFSource) {
		this.CIFSource = CIFSource;
	}

	public void setCMSLimitProfileId(Long limitProfileId) {
		CMSLimitProfileId = limitProfileId;
	}

	public void setCmsSubProfileId(long cmsSubProfileId) {
		this.cmsSubProfileId = cmsSubProfileId;
	}

	public void setExtensionDate(String extensionDate) {
		this.extensionDate = MessageDate.getInstance().getDate(extensionDate);
	}

	public void setExtensionDateLOAcceptance(String extensionDateLOAcceptance) {
		this.extensionDateLOAcceptance = MessageDate.getInstance().getDate(extensionDateLOAcceptance);
	}

	public void setHasBLInd(String hasBLInd) {
		this.hasBLInd = hasBLInd;
	}

	public void setHasCFInd(String hasCFInd) {
		this.hasCFInd = hasCFInd;
	}

	public void setHostAANumber(String hostAANumber) {
		this.hostAANumber = hostAANumber;
	}

	public void setIsMigratedIndicator(String isMigratedIndicator) {
		this.isMigratedIndicator = isMigratedIndicator;
	}

	public void setJDOAAApproveDate(Date AAApproveDate) {
		this.AAApproveDate = MessageDate.getInstance().getString(AAApproveDate);
	}

	public void setJDOAACreateDate(Date AACreateDate) {
		this.AACreateDate = AACreateDate;
	}

	public void setJDOAnnualReviewDate(Date annualReviewDate) {
		this.annualReviewDate = annualReviewDate;
	}

	public void setJDOExtensionDate(Date extensionDate) {
		this.extensionDate = extensionDate;
	}

	public void setJDOExtensionDateLOAcceptance(Date extensionDateLOAcceptance) {
		this.extensionDateLOAcceptance = extensionDateLOAcceptance;
	}

	public void setJDOSolicitorInstructionDate(Date solicitorInstructionDate) {
		this.solicitorInstructionDate = MessageDate.getInstance().getString(solicitorInstructionDate);
	}

	public void setLimitProfileId(long limitProfileId) {
		this.limitProfileId = limitProfileId;
	}

	public void setLimitProfileType(String limitProfileType) {
		this.limitProfileType = limitProfileType;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void setLORequired(String LORequired) {
		this.LORequired = LORequired;
	}

	public void setLOSAANumber(String LOSAANumber) {
		this.LOSAANumber = LOSAANumber;
	}

	/**
	 * @param mainProfile the mainProfile to set
	 */
	public final void setMainProfile(MainProfile mainProfile) {
		this.mainProfile = mainProfile;
	}

	public void setOldLOSAANumber(String oldLOSAANumber) {
		this.oldLOSAANumber = oldLOSAANumber;
	}

	public void setOriginatingLocation(OriginatingBookingLocation originatingLocation) {
		this.originatingLocation = originatingLocation;
	}

	public void setPASolicitorInvolvementRequiredInd(String PASolicitorInvolvementRequiredInd) {
		this.PASolicitorInvolvementRequiredInd = PASolicitorInvolvementRequiredInd;
	}

	public void setSolicitorInstructionDate(String solicitorInstructionDate) {
		this.solicitorInstructionDate = solicitorInstructionDate;
	}

	public void setSubProfileId(long subProfileId) {
		this.subProfileId = subProfileId;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("LimitProfile [");
		buf.append("CIFId=");
		buf.append(CIFId);
		buf.append(", LOSAANumber=");
		buf.append(LOSAANumber);
		buf.append(", hostAANumber=");
		buf.append(hostAANumber);
		buf.append(", cmsSubProfileId=");
		buf.append(cmsSubProfileId);
		buf.append(", AAApproveDate=");
		buf.append(AAApproveDate);
		buf.append(", AACreateDate=");
		buf.append(AACreateDate);
		buf.append(", AALawType=");
		buf.append(AALawType);
		buf.append(", AASource=");
		buf.append(AASource);
		buf.append(", AAStatus=");
		buf.append(AAStatus);
		buf.append(", AAType=");
		buf.append(AAType);
		buf.append(", CIFSource=");
		buf.append(CIFSource);
		buf.append(", originatingLocation=");
		buf.append(originatingLocation);
		buf.append("]");
		return buf.toString();
	}
}
