package com.integrosys.cms.app.user.bus;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.bus.ICityDAO;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.bus.ICountryDAO;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.bus.ICommonUserRegion;
import com.integrosys.component.user.app.bus.ICommonUserSegment;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.bus.OBCommonUserRegion;
import com.integrosys.component.user.app.bus.OBCommonUserSegment;
import com.integrosys.component.user.app.bus.OBRoleType;

/**
 * <dl>
 * <dt><b> Purpose: </b>
 * <dd>Bean object to represent the user entity
 * <p>
 * <dt><b> Usage:</b>
 * <dd>
 * <p>
 * <dt><b>Version Control:</b>
 * <dd>$Revision: 1.1 $<br>
 * $Date: 2003/07/30 07:35:04 $<br>
 * $Author: kllee $<br>
 * </dl>
 */

public class OBCMSUser extends OBCommonUser {

	/**
     */
	public OBCMSUser() {
		super();

		setDefaultRoleType();
	}

	/**
     */
	public OBCMSUser(ICommonUser user) {
		super(user);

		setDefaultRoleType();
	}

	private void setDefaultRoleType() {
		OBRoleType role = new OBRoleType();
		role.setRoleTypeID(0); // default value

		super.setRoleType(role);
	}
	
	/*Added by archana - start*/
	private FormFile fileUpload;
	

	/**
	 * @return the fileUpload
	 */
	public FormFile getFileUpload() {
		return fileUpload;
	}

	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	/*Added by archana - start*/
}
