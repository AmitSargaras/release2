package com.integrosys.cms.app.userrole.bus;

import com.integrosys.cms.ui.userrole.IUserModule;

public class OBUserModule implements IUserModule{
	
	public long moduleId;
	
	public String moduleName;
	
	public String moduleType;

	public long getModuleId() {
		return moduleId;
	}

	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

}
