package com.integrosys.cms.app.cersaiMapping.bus;

import java.util.Date;

public class OBCersaiMapping implements ICersaiMapping,Cloneable {
		
		private long id;
		private long versionTime;

		
		private String masterName;
		private String operationName;
		private String status;
		
		private long masterId = 0l;
		private String deprecated;
		private Date creationDate;
		private String createBy;
		private Date lastUpdateDate;
		private String lastUpdateBy;
		private String climsValue;
		private String cersaiValue;
		private String[] updatedCersaiValue;
		private String[] updatedClimsValue;
		private String[] climsValues;
		ICersaiMapping[] feedEntriesArr;
		ICersaiMapping[] masterValueList;
		
		
		
		public ICersaiMapping[] getMasterValueList() {
			return masterValueList;
		}
		public void setMasterValueList(ICersaiMapping[] masterValueList) {
			this.masterValueList = masterValueList;
		}
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
		
		public String getOperationName() {
			return operationName;
		}
		public void setOperationName(String operationName) {
			this.operationName = operationName;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public long getMasterId() {
			return masterId;
		}
		public void setMasterId(long masterId) {
			this.masterId = masterId;
		}
		public String getDeprecated() {
			return deprecated;
		}
		public void setDeprecated(String deprecated) {
			this.deprecated = deprecated;
		}
		public Date getCreationDate() {
			return creationDate;
		}
		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}
		public String getCreateBy() {
			return createBy;
		}
		public void setCreateBy(String createBy) {
			this.createBy = createBy;
		}
		public Date getLastUpdateDate() {
			return lastUpdateDate;
		}
		public void setLastUpdateDate(Date lastUpdateDate) {
			this.lastUpdateDate = lastUpdateDate;
		}
		public String getLastUpdateBy() {
			return lastUpdateBy;
		}
		public void setLastUpdateBy(String lastUpdateBy) {
			this.lastUpdateBy = lastUpdateBy;
		}
		public String getClimsValue() {
			return climsValue;
		}
		public void setClimsValue(String climsValue) {
			this.climsValue = climsValue;
		}
		public String getCersaiValue() {
			return cersaiValue;
		}
		public void setCersaiValue(String cersaiValue) {
			this.cersaiValue = cersaiValue;
		}
		public String getMasterName() {
			return masterName;
		}
		public void setMasterName(String masterName) {
			this.masterName = masterName;
		}
		public String[] getUpdatedCersaiValue() {
			return updatedCersaiValue;
		}
		public void setUpdatedCersaiValue(String[] updatedCersaiValue) {
			this.updatedCersaiValue = updatedCersaiValue;
		}
		public String[] getClimsValues() {
			return climsValues;
		}
		public void setClimsValues(String[] climsValues) {
			this.climsValues = climsValues;
		}
		public ICersaiMapping[] getFeedEntriesArr() {
			return feedEntriesArr;
		}
		public void setFeedEntriesArr(ICersaiMapping[] feedEntriesArr) {
			this.feedEntriesArr = feedEntriesArr;
		}
		@Override
		public String[] getUpdatedClimsValue() {
			return updatedClimsValue;
		}
		@Override
		public void setUpdatedClimsValue(String[] updatedClimsValue) {
			this.updatedClimsValue = updatedClimsValue;
			
		}
		
		
	}

