package com.integrosys.cms.app.userrole.bus;

import com.integrosys.cms.ui.userrole.IUserModule;
import com.integrosys.cms.ui.userrole.IUserRole;

public class OBUserRole implements IUserRole{
	
	private long accessId;
	
	private long roleId;
	
	private IUserModule moduleId;
	
	private String addAccess = "";
	
	private String editAccess = "";

	private String deleteAccess = "";
	
	private String viewAccess = "";

	private String approveAccess = "";

	public long getAccessId() {
		return accessId;
	}

	public void setAccessId(long accessId) {
		this.accessId = accessId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public IUserModule getModuleId() {
		return moduleId;
	}

	public void setModuleId(IUserModule moduleId) {
		this.moduleId = moduleId;
	}

	public String getAddAccess() {
		return addAccess;
	}

	public void setAddAccess(String addAccess) {
		this.addAccess = addAccess;
	}

	public String getEditAccess() {
		return editAccess;
	}

	public void setEditAccess(String editAccess) {
		this.editAccess = editAccess;
	}

	public String getDeleteAccess() {
		return deleteAccess;
	}

	public void setDeleteAccess(String deleteAccess) {
		this.deleteAccess = deleteAccess;
	}

	public String getViewAccess() {
		return viewAccess;
	}

	public void setViewAccess(String viewAccess) {
		this.viewAccess = viewAccess;
	}

	public String getApproveAccess() {
		return approveAccess;
	}

	public void setApproveAccess(String approveAccess) {
		this.approveAccess = approveAccess;
	}
	
	

}
