package com.integrosys.cms.host.eai.covenant.bus;

import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.support.MessageDate;

public class CovenantItem implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long cMSCovenantItemID;

	private Long recurrentDocId;

	private Long cMSconvenantItemRefID;
	
	private Long losConditionPrecedentId;

	private String covenantCondition;

	private String docEndDate;

	private String dueDate;

	private String remarks;

	private String isParameterized;

	private String isDeleted;

	private String oneOff = ICMSConstant.TRUE_VALUE;

	private String riskTrigger = ICMSConstant.TRUE_VALUE;

	private Long frequencyUnit;

	private String frequencyUom;

	private String sourceID;

	private String updateStatusIndicator;

	private String changeIndicator;

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public Long getCMSconvenantItemRefID() {
		return cMSconvenantItemRefID;
	}

	public Long getCMSCovenantItemID() {
		return cMSCovenantItemID;
	}

	public String getCovenantCondition() {
		return covenantCondition;
	}

	public String getDocEndDate() {
		return docEndDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public Long getFrequencyUnit() {
		return frequencyUnit;
	}

	public String getFrequencyUom() {
		return frequencyUom;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public String getIsParameterized() {
		return isParameterized;
	}

	public Date getJDODocEndDate() {
		return MessageDate.getInstance().getDate(docEndDate);
	}

	public Date getJDODueDate() {
		return MessageDate.getInstance().getDate(dueDate);
	}

	public Long getLosConditionPrecedentId() {
		return losConditionPrecedentId;
	}

	public String getOneOff() {
		return oneOff;
	}

	public Long getRecurrentDocId() {
		return recurrentDocId;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getRiskTrigger() {
		return riskTrigger;
	}

	public String getSourceID() {
		return sourceID;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setCMSconvenantItemRefID(Long sconvenantItemRefID) {
		cMSconvenantItemRefID = sconvenantItemRefID;
	}

	public void setCMSCovenantItemID(Long covenantItemID) {
		cMSCovenantItemID = covenantItemID;
	}

	public void setCovenantCondition(String covenantCondition) {
		this.covenantCondition = covenantCondition;
	}

	public void setDocEndDate(String docEndDate) {
		this.docEndDate = docEndDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public void setFrequencyUnit(Long frequencyUnit) {
		this.frequencyUnit = frequencyUnit;
	}

	public void setFrequencyUom(String frequencyUom) {
		this.frequencyUom = frequencyUom;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setIsParameterized(String isParameterized) {
		this.isParameterized = isParameterized;
	}

	public void setJDODocEndDate(Date docEndDate) {
		this.docEndDate = MessageDate.getInstance().getString(docEndDate);
	}

	public void setJDODueDate(Date dueDate) {
		this.dueDate = MessageDate.getInstance().getString(dueDate);
	}

	public void setLosConditionPrecedentId(Long losConditionPrecedentId) {
		this.losConditionPrecedentId = losConditionPrecedentId;
	}

	public void setOneOff(String oneOff) {
		this.oneOff = oneOff;
	}

	public void setRecurrentDocId(Long recurrentDocId) {
		this.recurrentDocId = recurrentDocId;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setRiskTrigger(String riskTrigger) {
		this.riskTrigger = riskTrigger;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("CovenantItem [");
		buf.append("covenantCondition=");
		buf.append(covenantCondition);
		buf.append(", docEndDate=");
		buf.append(docEndDate);
		buf.append(", dueDate=");
		buf.append(dueDate);
		buf.append(", frequencyUnit=");
		buf.append(frequencyUnit);
		buf.append(", frequencyUom=");
		buf.append(frequencyUom);
		buf.append(", remarks=");
		buf.append(remarks);
		buf.append(", isParameterized=");
		buf.append(isParameterized);
		buf.append(", oneOff=");
		buf.append(oneOff);
		buf.append(", riskTrigger=");
		buf.append(riskTrigger);
		buf.append(", sourceID=");
		buf.append(sourceID);
		buf.append("]");
		return buf.toString();
	}

}
