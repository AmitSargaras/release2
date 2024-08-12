/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.bizstructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.OBTeam;
import com.integrosys.component.commondata.app.bus.ICodeCategoryEntry;
import com.integrosys.component.commondata.app.bus.OBCodeCategoryEntry;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Command class to add the new bizstructure by admin maker on the corresponding
 * event...
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/11/27 07:38:01 $ Tag: $Name: $
 */
public class GetOrgByCtryCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public GetOrgByCtryCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "Team", "com.integrosys.component.bizstructure.app.bus.OBTeam", FORM_SCOPE },
				{ "mapTeam", "com.integrosys.component.bizstructure.app.bus.OBTeam", SERVICE_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "mapTeam", "com.integrosys.component.bizstructure.app.bus.OBTeam", SERVICE_SCOPE },
				{ "orgListByCtry", "java.util.Collection", REQUEST_SCOPE },
				{ "orgListByCtryValue", "java.util.Collection", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		OBTeam team = (OBTeam) map.get("Team");
		ArrayList valueList = new ArrayList();
		ArrayList labelList = new ArrayList();

		boolean requireOrgCodeFilter = BizStructureHelper.requireOrgCodeFilter();

		if (team == null) {
			team = (OBTeam) map.get("mapTeam");
		}
		if (team != null) {
			String[] ctryCodes = team.getCountryCodes();
			DefaultLogger.debug(this, "GetOrgByCtry ctryCodes = " + (ctryCodes == null ? 0 : ctryCodes.length));

			if ((ctryCodes != null) && (ctryCodes.length > 0)) {
				String[] orgGroupCodeArray = team.getOrgGroupCode();

				HashMap orgCodeMap = new HashMap();
				ArrayList orgCodeValueList = new ArrayList();
				ArrayList orgCodeLabelList = new ArrayList();

				if (!requireOrgCodeFilter) {
					CommonCodeList orgCodes = CommonCodeList.getInstance(ctryCodes, null,
							ICMSConstant.CATEGORY_CODE_BKGLOC, null, true);
					orgCodeValueList.addAll((ArrayList) orgCodes.getCommonCodeValues());
					orgCodeLabelList.addAll((ArrayList) orgCodes.getCommonCodeLabels());
					orgCodeMap.putAll(orgCodes.getLabelValueMap());
				}
				else if ((orgGroupCodeArray != null) && (orgGroupCodeArray.length > 0)) {
					for (int index = 0; index < orgGroupCodeArray.length; index++) {
						CommonCodeList orgCodes = CommonCodeList.getInstance(ctryCodes, null,
								ICMSConstant.CATEGORY_CODE_BKGLOC, orgGroupCodeArray[index], true);
						orgCodeValueList.addAll((ArrayList) orgCodes.getCommonCodeValues());
						orgCodeLabelList.addAll((ArrayList) orgCodes.getCommonCodeLabels());
						orgCodeMap.putAll(orgCodes.getLabelValueMap());
					}
				}
				DefaultLogger.debug(this, "Total No of orgs: " + orgCodeMap.size());
				ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
				if (MaintainTeamUtil.isSuperUser(user.getLoginID())) {
					valueList = orgCodeValueList;
					labelList = orgCodeLabelList;
				}
				else if (orgCodeMap.size() > 0) {
					ITeam currentUserTeam = (ITeam) map.get(IGlobalConstant.USER_TEAM);
					if (currentUserTeam != null) {
						String[] orgArray = currentUserTeam.getOrganisationCodes();
						if ((orgArray != null) && (orgArray.length > 0)) {
							ArrayList lvList = new ArrayList();
							for (int index = 0; index < orgArray.length; index++) {
								String entryName = (String) orgCodeMap.get(orgArray[index]);
								if (entryName != null) {
									String label = entryName + " (" + orgArray[index] + ")";
									OBCodeCategoryEntry entryLV = new OBCodeCategoryEntry(orgArray[index], label, true);
									lvList.add(entryLV);
								}
							}
							ICodeCategoryEntry[] lvArray = (ICodeCategoryEntry[]) lvList
									.toArray(new ICodeCategoryEntry[0]);
							Arrays.sort(lvArray);
							for (int ii = 0; ii < lvArray.length; ii++) {
								valueList.add(lvArray[ii].getEntryCode());
								labelList.add(lvArray[ii].getEntryName());
							}
						}
					}
				}
			}

			resultMap.put("mapTeam", team);
		}
		DefaultLogger.debug(this, "No of orgs: " + valueList.size());
		resultMap.put("orgListByCtry", labelList);
		resultMap.put("orgListByCtryValue", valueList);
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
