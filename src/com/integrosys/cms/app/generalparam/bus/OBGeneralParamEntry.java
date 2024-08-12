package com.integrosys.cms.app.generalparam.bus;

import java.util.Date;

/**
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class OBGeneralParamEntry implements IGeneralParamEntry {
	
	private String paramCode;
	
	private String paramName;
	
	private String uiView;
	
	private String paramValue;
	
	private String handUpTime;
	
	private Date lastUpdatedDate;

	private long paramID;

	private long generalParamEntryRef;

	private long versionTime;
	
	
	/**
	 * @return the paramID
	 */
	public long getParamID() {
		return paramID;
	}

	/**
	 * @param paramID the paramID to set
	 */
	public void setParamID(long paramID) {
		this.paramID = paramID;
	}

	public String getHandUpTime() {
		return handUpTime;
	}

	/**
	 * @param handUpTime the handUpTime to set
	 */
	public void setHandUpTime(String handUpTime) {
		this.handUpTime = handUpTime;
	}
	
	/**
	 * @return the paramCode
	 */
	public String getParamCode() {
		return paramCode;
	}

	/**
	 * @param paramCode the paramCode to set
	 */
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	/**
	 * @return the paramName
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * @param paramName the paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * @return the uiView
	 */
	public String getUiView() {
		return uiView;
	}

	/**
	 * @param uiView the uiView to set
	 */
	public void setUiView(String uiView) {
		this.uiView = uiView;
	}

	/**
	 * @return the paramValue
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * @param paramValue the paramValue to set
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

		/**
	 * @return the generalParamEntryRef
	 */
	public long getGeneralParamEntryRef() {
		return generalParamEntryRef;
	}

	/**
	 * @param generalParamEntryRef the generalParamEntryRef to set
	 */
	public void setGeneralParamEntryRef(long generalParamEntryRef) {
		this.generalParamEntryRef = generalParamEntryRef;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public long getVersionTime() {
		return this.versionTime;
	}

	public void setLastUpdatedDate(Date lastUpdateDate) {
		this.lastUpdatedDate = lastUpdateDate;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	
	public long getgeneralParamEntryRef() {
		return generalParamEntryRef;
	}

	public void setgeneralParamEntryRef(long generalParamEntryRef) {
		this.generalParamEntryRef = generalParamEntryRef;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((paramCode == null) ? 0 : paramCode.hashCode());
		result = prime * result + ((paramName == null) ? 0 : paramName.hashCode());

		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		final OBGeneralParamEntry other = (OBGeneralParamEntry) obj;
		if (paramCode == null) {
			if (other.paramCode != null) {
				return false;
			}
		}
		else if (!paramCode.equals(other.paramCode)) {
			return false;
		}
		
		if (paramName == null) {
			if (other.paramName != null) {
				return false;
			}
		}
		else if (!paramName.equals(other.paramName)) {
			return false;
		}

		return true;
	}
}