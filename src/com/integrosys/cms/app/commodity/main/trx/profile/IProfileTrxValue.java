/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/profile/IProfileTrxValue.java,v 1.3 2006/03/23 09:24:54 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.profile;

import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ProfileSearchCriteria;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents the transaction value object for Components.
 * 
 * @author $Author: hmbao $
 * @version $
 * @since $Date: 2006/03/23 09:24:54 $ Tag: $Name: $
 */
public interface IProfileTrxValue extends ICMSTrxValue {
	public IProfile[] getProfile();

	public IProfile[] getStagingProfile();

	public void setProfile(IProfile[] value);

	public void setStagingProfile(IProfile[] value);

	public ProfileSearchCriteria getSearchCriteria();

	public void setSearchCriteria(ProfileSearchCriteria searchCriteria);
}
