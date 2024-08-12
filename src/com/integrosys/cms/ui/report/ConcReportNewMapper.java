/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/report/ConcReportNewMapper.java,v 1.2 2003/08/26 05:19:14 btchng Exp $
 */

package com.integrosys.cms.ui.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;

/**
 * The Mapper for ConcReportNewForm.
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/26 05:19:14 $ Tag: $Name: $
 */
public class ConcReportNewMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm form, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...)");

		ConcReportNewForm newForm = (ConcReportNewForm) form;

		String event = newForm.getEvent();

		DefaultLogger.debug(this, "event :" + event);

		List returnList = new ArrayList();

        //Andy Wong, 1 July 2010: fix adhoc request not persisted in CMS_REPORT_REQUEST
		if (ConcReportNewAction.EVENT_GENERATE.equals(event)
                || ConcReportNewAction.EVENT_GENERATE_LMS.equals(event)) {
			for (int i = 0; i < newForm.getChecks().length; i++) {
				returnList.add(newForm.getChecks()[i]);
			}
		}

		return returnList;

	}

	/*
	 * public Object mapFormToOB(CommonForm form, HashMap hashMap) throws
	 * MapperException {
	 * 
	 * DefaultLogger.debug(this, "entering mapFormToOB(...)");
	 * 
	 * ConcReportNewForm newForm = (ConcReportNewForm)form;
	 * 
	 * String event = newForm.getEvent();
	 * 
	 * if (ConcReportNewAction.EVENT_GENERATE.equals(event)) {
	 * 
	 * List returnList = new ArrayList();
	 * 
	 * // 13 fields in total. returnList.add(newForm.getReportName());
	 * returnList.add(newForm.getReportType());
	 * returnList.add(newForm.getStockExCode());
	 * returnList.add(newForm.getStockSharesNumSecSubtype());
	 * returnList.add(newForm.getStockSharesDenSecType());
	 * returnList.add(newForm.getSecStockExNumSecSubtype());
	 * returnList.add(newForm.getSecStockExDenSecType());
	 * returnList.add(newForm.getSecStockExDenSecSubtype());
	 * returnList.add(newForm.getSecSecTypeRegion());
	 * returnList.add(newForm.getSecSecTypeListing());
	 * returnList.add(newForm.getPropertyListing());
	 * returnList.add(newForm.getPropertyCountryCode());
	 * returnList.add(newForm.getCurrencySecTypeListing());
	 * 
	 * return returnList;
	 * 
	 * } else if (ConcReportNewAction.EVENT_SAMPLE.equals(event)) {
	 * 
	 * return newForm.getSample();
	 * 
	 * }
	 * 
	 * DefaultLogger.debug(this, "Unhandled event in mapFormToOB = " + event);
	 * 
	 * return null; }
	 */

	public CommonForm mapOBToForm(CommonForm form, Object object, HashMap hashMap) throws MapperException {
		return null; // To change body of implemented methods use Options | File
						// Templates.
	}
}
