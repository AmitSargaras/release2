package com.integrosys.cms.host.eai.limit.inquiry.listing;

import com.integrosys.cms.host.eai.OriginatingBookingLocation;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * Credit application to be used as the response back to caller
 * 
 * @author Chong Jun Yong
 * 
 */
public class CreditApplication {
	private String hostAANumber;

	private String losAANumber;

	private String applicationSource;

	private String applicationType;

	private String applicationLawType;

	private String applicationApproveDate;

	private String applicationStatus;

	private OriginatingBookingLocation originatingBookingLocation;

	private MainProfile mainProfile;

	public CreditApplication() {
	}

	public CreditApplication(LimitProfile limitProfile) {
		this.hostAANumber = limitProfile.getHostAANumber();
		this.losAANumber = limitProfile.getLOSAANumber();
		this.applicationSource = limitProfile.getAASource();
		this.applicationLawType = limitProfile.getAALawType();
		this.applicationApproveDate = MessageDate.getInstance().getString(limitProfile.getJDOAAApproveDate());
		this.applicationStatus = limitProfile.getAAStatus();
		this.applicationType = limitProfile.getAAType();
		this.originatingBookingLocation = limitProfile.getOriginatingLocation();
	}

	public String getApplicationApproveDate() {
		return applicationApproveDate;
	}

	public String getApplicationLawType() {
		return applicationLawType;
	}

	public String getApplicationSource() {
		return applicationSource;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public String getHostAANumber() {
		return hostAANumber;
	}

	public String getLosAANumber() {
		return losAANumber;
	}

	public MainProfile getMainProfile() {
		return mainProfile;
	}

	public OriginatingBookingLocation getOriginatingBookingLocation() {
		return originatingBookingLocation;
	}

	public void setApplicationApproveDate(String applicationApproveDate) {
		this.applicationApproveDate = applicationApproveDate;
	}

	public void setApplicationLawType(String applicationLawType) {
		this.applicationLawType = applicationLawType;
	}

	public void setApplicationSource(String applicationSource) {
		this.applicationSource = applicationSource;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public void setHostAANumber(String hostAANumber) {
		this.hostAANumber = hostAANumber;
	}

	public void setLosAANumber(String losAANumber) {
		this.losAANumber = losAANumber;
	}

	public void setMainProfile(MainProfile mainProfile) {
		this.mainProfile = mainProfile;
	}

	public void setOriginatingBookingLocation(OriginatingBookingLocation originatingBookingLocation) {
		this.originatingBookingLocation = originatingBookingLocation;
	}

}
