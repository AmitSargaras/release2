package com.integrosys.cms.ui.userrole;

public interface IUserRole {
	
	public long getRoleId() ;

	public void setRoleId(long roleId) ;

	public IUserModule getModuleId() ;

	public void setModuleId(IUserModule moduleId) ;

	public String getAddAccess() ;

	public void setAddAccess(String addAccess) ;

	public String getEditAccess() ;

	public void setEditAccess(String editAccess) ;

	public String getDeleteAccess() ;

	public void setDeleteAccess(String deleteAccess) ;

	public String getViewAccess() ;

	public void setViewAccess(String viewAccess) ;

	public String getApproveAccess() ;

	public void setApproveAccess(String approveAccess) ;

}
