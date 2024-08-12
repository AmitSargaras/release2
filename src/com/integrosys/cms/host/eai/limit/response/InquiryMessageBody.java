package com.integrosys.cms.host.eai.limit.response;

import java.util.Date;

import com.integrosys.cms.host.eai.EAIBody;
import com.integrosys.component.user.app.bus.ICommonUser;

public class InquiryMessageBody extends EAIBody {
	private long cmsLimitProfileId;

	private String losAANumber;

	private Date stpDate;

	private ICommonUser user;

	public long getCmsLimitProfileId() {
		return cmsLimitProfileId;
	}

	public String getLosAANumber() {
		return losAANumber;
	}

	public Date getStpDate() {
		return stpDate;
	}

	public ICommonUser getUser() {
		return user;
	}

	public void setCmsLimitProfileId(long cmsLimitProfileId) {
		this.cmsLimitProfileId = cmsLimitProfileId;
	}

	public void setLosAANumber(String losAANumber) {
		this.losAANumber = losAANumber;
	}

	public void setStpDate(Date stpDate) {
		this.stpDate = stpDate;
	}

	public void setUser(ICommonUser user) {
		this.user = user;
	}

}
