package com.integrosys.cms.ui.poi.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterDAOFactory;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMasterDao;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * @author $Author: Sandeep Shinde
 * @version 1.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class RefreshFacility extends AbstractCommand {

	private String facility;

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {

		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "facilityList", "java.util.List", REQUEST_SCOPE },
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
	CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		try {
			String event = (String) map.get("event");
			DefaultLogger.debug(this, "---------------Inside RefreshFacility---event--is----"+event);
		
			if (event.equals("refresh_rm_id")) {
				long regionId = Long.parseLong((String) map.get("regionId"));
				DefaultLogger.debug(this, "---------------Inside RefreshFacility---regionId--is----"+regionId);
				resultMap.put("facilityList",getNewFacilityList(String.valueOf(regionId)));
			}
			resultMap.put("event", event);
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException(
			"Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private List getNewFacilityList(String rmRegion) {
		List lbValList = new ArrayList();
		List idList = new ArrayList();
		try {
			DefaultLogger.debug(this, "------------------Inside getNewRelationshipMgrList(String rmRegion)-------------"+rmRegion);
			IFacilityNewMasterDao facilityDAO = (IFacilityNewMasterDao) BeanHouse.get("facilityDAO");
			DefaultLogger.debug(this, "------------------relationshipMgrDAO-------------"+facilityDAO);
			SearchResult idListsr = (SearchResult) FacilityNewMasterDAOFactory.getFacilityNewMasterDAO();

			if (idListsr != null) {
				  idList = new ArrayList(idListsr.getResultList());
			}
			
			for (int i = 0; i < idList.size(); i++) {
				IFacilityNewMaster mgr = (IFacilityNewMaster) idList.get(i);
				if (mgr.getStatus().equals("ACTIVE")) {
					String id = Long.toString(mgr.getId());
					String val = mgr.getNewFacilityName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		DefaultLogger.debug(this, "------------------lbValList-------------"+lbValList.size());
		return CommonUtil.sortDropdown(lbValList);
	}
}
