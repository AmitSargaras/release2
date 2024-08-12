package com.integrosys.cms.host.eai.customer.bus;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * JDO For Entity Relationship
 * 
 * @author allen
 * 
 */
public final class EntityRelationship implements java.io.Serializable, IEntityRelationship {

	// SCI_LE_REL_ID CMS_CUSTOMER_ID REL_NUM
	// REL_VALUE PERCENT_OWN VERSION_TIME REMARK
	// LAST_MODIFIED_DT LAST_MODIFIED_USR
	private long sciLeRelId;

	private long cmsCustomerID;

	private long mainCmsCustomerID;

	private String cifNo;

	private String cifSource;

	private String cifName;

	private StandardCode relationship;

	private double percentOwn;

	private String remark;

	private String lastModifiedUsr;

	private String lastModifiedDt;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #getCmsCustomerID()
	 */
	public final long getCmsCustomerID() {
		return cmsCustomerID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #setCmsCustomerID(long)
	 */
	public final void setCmsCustomerID(long cmsCustomerID) {
		this.cmsCustomerID = cmsCustomerID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #getLastModifiedDt()
	 */
	public final String getLastModifiedDt() {
		return lastModifiedDt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #setLastModifiedDt(java.lang.String)
	 */
	public final void setLastModifiedDt(String lastModifiedDt) {
		this.lastModifiedDt = lastModifiedDt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #getJDOLastModifiedDt()
	 */
	public final java.util.Date getJDOLastModifiedDt() {
		return MessageDate.getInstance().getDate(lastModifiedDt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #setJDOLastModifiedDt(java.util.Date)
	 */
	public final void setJDOLastModifiedDt(java.util.Date lastModifiedDt) {
		this.lastModifiedDt = MessageDate.getInstance().getString(lastModifiedDt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #getLastModifiedUsr()
	 */
	public final String getLastModifiedUsr() {
		return lastModifiedUsr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #setLastModifiedUsr(java.lang.String)
	 */
	public final void setLastModifiedUsr(String lastModifiedUsr) {
		this.lastModifiedUsr = lastModifiedUsr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #getPercentOwn()
	 */
	public final double getPercentOwn() {
		return percentOwn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #setPercentOwn(double)
	 */
	public final void setPercentOwn(double percentOwn) {
		this.percentOwn = percentOwn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #getRelationship()
	 */
	public final StandardCode getRelationship() {
		return relationship;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #setRelationship(com.integrosys.cms.host.message.castor.eai.StandardCode)
	 */
	public final void setRelationship(StandardCode relationship) {
		this.relationship = relationship;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #getRemark()
	 */
	public final String getRemark() {
		return remark;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #setRemark(java.lang.String)
	 */
	public final void setRemark(String remark) {
		this.remark = remark;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #getCifName()
	 */
	public final String getCifName() {
		return cifName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #setCifName(java.lang.String)
	 */
	public final void setCifName(String cifName) {
		this.cifName = cifName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #getCifNo()
	 */
	public final String getCifNo() {
		return cifNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #setCifNo(java.lang.String)
	 */
	public final void setCifNo(String cifNo) {
		this.cifNo = cifNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #getCifSource()
	 */
	public final String getCifSource() {
		return cifSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #setCifSource(java.lang.String)
	 */
	public final void setCifSource(String cifSource) {
		this.cifSource = cifSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #getMainCmsCustomerID()
	 */
	public final long getMainCmsCustomerID() {
		return mainCmsCustomerID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #setMainCmsCustomerID(long)
	 */
	public final void setMainCmsCustomerID(long mainCmsCustomerID) {
		this.mainCmsCustomerID = mainCmsCustomerID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #getSciLeRelId()
	 */
	public final long getSciLeRelId() {
		return sciLeRelId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.host.message.castor.eai.customer.ICMSEntityRelationship
	 * #setSciLeRelId(long)
	 */
	public final void setSciLeRelId(long sciLeRelId) {
		this.sciLeRelId = sciLeRelId;
	}

	private char updateStatusIndicator;

	private char changeIndicator;

	public final char getChangeIndicator() {
		return changeIndicator;
	}

	public final void setChangeIndicator(char changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public final char getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public final void setUpdateStatusIndicator(char updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

}
