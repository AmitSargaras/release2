package com.integrosys.cms.host.eai.customer.bus;

import com.integrosys.cms.host.eai.StandardCode;

/**
 * Entity Relationship Interface
 * @author allen
 *
 */
public interface IEntityRelationship {

	public abstract long getCmsCustomerID();

	public abstract void setCmsCustomerID(long cmsCustomerID);

	public abstract String getLastModifiedDt();

	public abstract void setLastModifiedDt(String lastModifiedDt);

	public abstract java.util.Date getJDOLastModifiedDt();

	public abstract void setJDOLastModifiedDt(java.util.Date lastModifiedDt);

	public abstract String getLastModifiedUsr();

	public abstract void setLastModifiedUsr(String lastModifiedUsr);

	public abstract double getPercentOwn();

	public abstract void setPercentOwn(double percentOwn);

	public abstract StandardCode getRelationship();

	public abstract void setRelationship(StandardCode relationship);

	public abstract String getRemark();

	public abstract void setRemark(String remark);

	public abstract String getCifName();

	public abstract void setCifName(String cifName);

	public abstract String getCifNo();

	public abstract void setCifNo(String cifNo);

	public abstract String getCifSource();

	public abstract void setCifSource(String cifSource);

	public abstract long getMainCmsCustomerID();

	public abstract void setMainCmsCustomerID(long mainCmsCustomerID);

	public abstract long getSciLeRelId();

	public abstract void setSciLeRelId(long sciLeRelId);

}