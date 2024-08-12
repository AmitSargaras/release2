package com.integrosys.cms.ui.relationshipmgr;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface ILocalCAD extends Serializable, IValueObject{

	public long getId();

	public void setId(long id);

	@Override
	public long getVersionTime();

	@Override
	public void setVersionTime(long arg0);
	
	public String getLocalCADEmployeeCode();

	public void setLocalCADEmployeeCode(String localCADEmployeeCode);

	public String getLocalCADName();

	public void setLocalCADName(String localCADName);

	public String getLocalCADEmailID();
	
	public void setLocalCADEmailID(String localCADEmailID);

	public String getLocalCADmobileNo();

	public void setLocalCADmobileNo(String localCADmobileNo);

	public String getLocalCADSupervisorName();

	public void setLocalCADSupervisorName(String localCADSupervisorName);

	public String getLocalCADSupervisorEmail();

	public void setLocalCADSupervisorEmail(String localCADSupervisorEmail);

	public String getLocalCADSupervisorMobileNo();

	public void setLocalCADSupervisorMobileNo(String localCADSupervisorMobileNo);

	public String getCreatedBy();

	public void setCreatedBy(String createdBy);
	
	public String getLastUpdateBy();

	public void setLastUpdateBy(String lastUpdateBy);

	public Date getCreationDate();

	public void setCreationDate(Date creationDate);

	public Date getLastUpdateDate();

	public void setLastUpdateDate(Date lastUpdateDate);

	public String getDeprecated();

	public void setDeprecated(String deprecated);

	public String getRelationshipMgrID();

	public void setRelationshipMgrID(String relationshipMgrID);

	public String getStatus();

	public void setStatus(String status);
}
