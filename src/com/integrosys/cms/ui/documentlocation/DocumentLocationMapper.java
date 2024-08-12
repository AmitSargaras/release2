/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/documentlocation/DocumentLocationMapper.java,v 1.6 2004/04/07 03:05:43 hltan Exp $
 */
package com.integrosys.cms.ui.documentlocation;

import java.util.HashMap;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;
import com.integrosys.cms.app.documentlocation.trx.ICCDocumentLocationTrxValue;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/04/07 03:05:43 $ Tag: $Name: $
 */

public class DocumentLocationMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		DocumentLocationForm aForm = (DocumentLocationForm) cForm;

		ICCDocumentLocationTrxValue docLocTrxValue = (ICCDocumentLocationTrxValue) inputs.get("docLocTrxValue");
		ICCDocumentLocation obDocLoc = null;
		try {
			if (docLocTrxValue != null) {
				if (docLocTrxValue.getStagingCCDocumentLocation() != null) {
					obDocLoc = (ICCDocumentLocation) AccessorUtil.deepClone(docLocTrxValue
							.getStagingCCDocumentLocation());
				}
				else {
					obDocLoc = (ICCDocumentLocation) AccessorUtil.deepClone(docLocTrxValue.getCCDocumentLocation());
				}
			}
			else {
				obDocLoc = (ICCDocumentLocation) inputs.get("docLocationObj");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MapperException(e.getMessage());
		}

		IBookingLocation docLocation = obDocLoc.getOriginatingLocation();
		if (docLocation == null) {
			docLocation = new OBBookingLocation();
		}
		docLocation.setCountryCode(aForm.getDocOriginateLoc());
		docLocation.setOrganisationCode(aForm.getOrgCode());
		obDocLoc.setOriginatingLocation(docLocation);

		obDocLoc.setRemarks(aForm.getDocRemarks());

		return obDocLoc;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		DocumentLocationForm aForm = (DocumentLocationForm) cForm;
		ICCDocumentLocation doc = (ICCDocumentLocation) obj;

		aForm.setCustomerCategory(doc.getDocLocationCategory());
		if (!doc.getLegalRef().equals("0")) {
			aForm.setLeId(doc.getLegalRef());
		}
		else {
			aForm.setLeId("-");
		}
		aForm.setLegalName(doc.getLegalName());
		aForm.setCustomerType(doc.getCustomerType());

		IBookingLocation orgLoc = doc.getOriginatingLocation();
		if (orgLoc != null) {
			aForm.setDocOriginateLoc(orgLoc.getCountryCode());
			aForm.setOrgCode(orgLoc.getOrganisationCode());
		}

		aForm.setDocRemarks(doc.getRemarks());

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "docLocTrxValue", "com.integrosys.cms.app.documentlocation.trx.ICCDocumentLocationTrxValue",
						SERVICE_SCOPE },
				{ "docLocationObj", "com.integrosys.cms.app.documentlocation.bus.OBCCDocumentLocation", SERVICE_SCOPE }, });
	}
}
