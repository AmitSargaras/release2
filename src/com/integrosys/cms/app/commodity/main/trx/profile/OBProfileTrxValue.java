/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/profile/OBProfileTrxValue.java,v 1.3 2006/03/23 09:24:54 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.profile;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ProfileSearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This interface represents the transaction value object for Components.
 * 
 * @author $Author: hmbao $
 * @version $
 * @since $Date: 2006/03/23 09:24:54 $ Tag: $Name: $
 */
public class OBProfileTrxValue extends OBCMSTrxValue implements IProfileTrxValue {

	private IProfile[] profile = null;

	private IProfile[] stagingProfile = null;

	private ProfileSearchCriteria searchCriteria;

	public ProfileSearchCriteria getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(ProfileSearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public OBProfileTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_PROFILE);
	}

	public OBProfileTrxValue(IProfile[] obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	public OBProfileTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	public IProfile[] getProfile() {
		return profile;
	}

	public void setProfile(IProfile[] profile) {
		this.profile = profile;
	}

	public IProfile[] getStagingProfile() {
		return stagingProfile;
	}

	public void setStagingProfile(IProfile[] stagingProfile) {
		this.stagingProfile = stagingProfile;
	}

	public boolean equals(Object o) {
		return this.toString().equals(o.toString());
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}
