package com.integrosys.cms.host.eai.security.bus;

import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class PledgorCreditGrade implements java.io.Serializable {

	private long cmsPledgorId;

	private long cmsId;

	private long creditGradeId;

	private StandardCode creditGradeType;

	private StandardCode creditGradeCode;

	private Date creditGradeEffectiveDate;

	private String creditGradeReasonForChange;

	private String updateStatusIndicator;

	private Date updateDate;

	private String changeIndicator;

	public long getCmsId() {
		return cmsId;
	}

	public void setCmsId(long cmsId) {
		this.cmsId = cmsId;
	}

	public long getCreditGradeId() {
		return creditGradeId;
	}

	public void setCreditGradeId(long creditGradeId) {
		this.creditGradeId = creditGradeId;
	}

	public StandardCode getCreditGradeType() {
		return creditGradeType;
	}

	public void setCreditGradeType(StandardCode creditGradeType) {
		this.creditGradeType = creditGradeType;
	}

	public StandardCode getCreditGradeCode() {
		return creditGradeCode;
	}

	public void setCreditGradeCode(StandardCode creditGradeCode) {
		this.creditGradeCode = creditGradeCode;
	}

	public String getCreditGradeEffectiveDate() {
		return MessageDate.getInstance().getString(this.creditGradeEffectiveDate);
	}

	public void setCreditGradeEffectiveDate(String creditGradeEffectiveDate) {
		this.creditGradeEffectiveDate = MessageDate.getInstance().getDate(creditGradeEffectiveDate);
	}

	public Date getJDOCreditGradeEffectiveDate() {
		return this.creditGradeEffectiveDate;
	}

	public void setJDOCreditGradeEffectiveDate(Date creditGradeEffectiveDate) {
		this.creditGradeEffectiveDate = creditGradeEffectiveDate;
	}

	public String getCreditGradeReasonForChange() {
		return creditGradeReasonForChange;
	}

	public void setCreditGradeReasonForChange(String creditGradeReasonForChange) {
		this.creditGradeReasonForChange = creditGradeReasonForChange;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public String getUpdateDate() {
		return MessageDate.getInstance().getString(this.updateDate);
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = MessageDate.getInstance().getDate(updateDate);
	}

	public Date getJDOUpdateDate() {
		return this.updateDate;
	}

	public void setJDOUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public long getCmsPledgorId() {
		return cmsPledgorId;
	}

	public void setCmsPledgorId(long cmsPledgorId) {
		this.cmsPledgorId = cmsPledgorId;
	}

}
