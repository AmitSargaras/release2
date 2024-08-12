package com.integrosys.cms.ui.creditriskparam.unittrust;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * CreditRiskParamUnitTrustForm Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class CreditRiskParamUnitTrustForm extends TrxContextForm implements java.io.Serializable {

	public static final String MAPPER = "com.integrosys.cms.ui.creditriskparam.unittrust.CreditRiskParamUnitTrustMapper";

	private String[] updatedMoa;

	private String[] updatedMaxCap;

	private String[] chkSuspended;

	private String targetOffset = "-1";

	private String groupFeedId = "";

	private String trxId = "";

	public String[] getUpdatedMoa() {
		return updatedMoa;
	}

	public String getUpdatedMoa(int i) {
		return updatedMoa[i];
	}

	public void setUpdatedMoa(String[] updatedMoa) {
		this.updatedMoa = updatedMoa;
	}

	public String[] getUpdatedMaxCap() {
		return updatedMaxCap;
	}

	public String getUpdatedMaxCap(int i) {
		return updatedMaxCap[i];
	}

	public void setUpdatedMaxCap(String[] updatedMaxCap) {
		this.updatedMaxCap = updatedMaxCap;
	}

	public String[] getChkSuspended() {
		return chkSuspended;
	}

	public String getChkSuspended(int i) {
		return chkSuspended[i];
	}

	public void setChkSuspended(String[] chkSuspended) {
		this.chkSuspended = chkSuspended;
	}

	public String getTargetOffset() {
		return targetOffset;
	}

	public void setTargetOffset(String targetOffset) {
		this.targetOffset = targetOffset;
	}

	public String getGroupFeedId() {
		return groupFeedId;
	}

	public void setGroupFeedId(String groupFeedId) {
		this.groupFeedId = groupFeedId;
	}

	public String getTrxId() {
		return trxId;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax [(key, MapperClassname)]
	 * 
	 * @return 2-dimensional String Array
	 */
	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

}
