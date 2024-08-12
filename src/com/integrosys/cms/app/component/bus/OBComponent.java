package com.integrosys.cms.app.component.bus;

public class OBComponent implements IComponent,Cloneable {
	
	public OBComponent(){
		
	}
	
		private long id;		
		private long versionTime;
		private String status;
		private String deprecated;
		private String componentType;
		private String componentName;
		private String componentCode;
		private String hasInsurance;
		//Start:-------->Abhishek Naik
		private String debtors;
		private String age;
		// End:-------->Abhishek Naik 
		//start Santosh
		private String componentCategory;
		private String applicableForDp;
		//end santosh
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public long getVersionTime() {
			return versionTime;
		}
		public void setVersionTime(long versionTime) {
			this.versionTime = versionTime;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getDeprecated() {
			return deprecated;
		}
		public void setDeprecated(String deprecated) {
			this.deprecated = deprecated;
		}
		public String getComponentType() {
			return componentType;
		}
		public void setComponentType(String componentType) {
			this.componentType = componentType;
		}
		public String getComponentName() {
			return componentName;
		}
		public void setComponentName(String componentName) {
			this.componentName = componentName;
		}
		public String getComponentCode() {
			return componentCode;
		}
		public void setComponentCode(String componentCode) {
			this.componentCode = componentCode;
		}
		public String getHasInsurance() {
			return hasInsurance;
		}
		public void setHasInsurance(String hasInsurance) {
			this.hasInsurance = hasInsurance;
		}
		
		
		//Start:-------->Abhishek Naik
		public String getDebtors() {
			return debtors;
		}
		public void setDebtors(String debtors) {
			this.debtors = debtors;
		}
		public String getAge() {
			return age;
		}
		public void setAge(String age) {
			this.age = age;
		}
		// End:-------->Abhishek Naik 
		public Object clone() throws CloneNotSupportedException {
			 
			 OBComponent copyObj = new OBComponent();
			 copyObj.setComponentCode(this.componentCode);
			 copyObj.setComponentName(this.componentName);
			 copyObj.setComponentType(this.componentType);
			 copyObj.setDeprecated(this.deprecated);
			 copyObj.setHasInsurance(this.hasInsurance);
			 copyObj.setId(this.id);
			 copyObj.setStatus(this.status);
			 copyObj.setVersionTime(this.versionTime);
			//Start:-------->Abhishek Naik
			 copyObj.setAge(this.age);
			 copyObj.setDebtors(this.debtors);
			// End:-------->Abhishek Naik 
			 
			//Start:-------->Santosh
			 copyObj.setComponentCategory(this.componentCategory);
			 copyObj.setApplicableForDp(this.applicableForDp);
			// End:-------->Santosh 
			 return copyObj;
			 }
		
		//start santosh
		public String getComponentCategory() {
			return componentCategory;
		}

		public void setComponentCategory(String componentCategory) {
			this.componentCategory = componentCategory;
		}

		public String getApplicableForDp() {
			return applicableForDp;
		}

		public void setApplicableForDp(String applicableForDp) {
			this.applicableForDp = applicableForDp;
		}
		//end santosh
}
