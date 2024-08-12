/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.bizstructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.OBTeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Command class to add the new bizstructure by admin maker on the corresponding
 * event...
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/11/27 07:38:01 $ Tag: $Name: $
 */
public class GetSegmentByCtryCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public GetSegmentByCtryCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "mapTeam", "com.integrosys.component.bizstructure.app.bus.OBTeam", SERVICE_SCOPE },
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
		return (new String[][] { { "segmentCodeLabel", "java.util.Collection", REQUEST_SCOPE },
				{ "segmentCodeValue", "java.util.Collection", REQUEST_SCOPE },
        { "apptypeLabel", "java.util.Collection", REQUEST_SCOPE },
                { "apptypeValue", "java.util.Collection", REQUEST_SCOPE }
        });
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
		ArrayList valueList = new ArrayList();
		ArrayList labelList = new ArrayList();
        ArrayList valueList2 = new ArrayList();
		ArrayList labelList2 = new ArrayList();
        OBTeam team = (OBTeam) map.get("mapTeam");
		if (team != null) {
			String[] ctryCodes = team.getCountryCodes();
			DefaultLogger.debug(this, "GetSegmentByCtryCmd ctryCodes = " + (ctryCodes == null ? 0 : ctryCodes.length));
			if ((ctryCodes != null) && (ctryCodes.length > 0)) {
				CommonCodeList segmentCodes= null;
                CommonCodeList applicationtype = null ;

                if (PropertiesConstantHelper.isFilterByApplicationType()) {
                     applicationtype = CommonCodeList.getInstance(ctryCodes, null, ICMSConstant.COMMON_CODE_APPLICATION_TYPE);
            	}
                if(PropertiesConstantHelper.isFilterByBusinessUnit())  segmentCodes = CommonCodeList.getInstance(ctryCodes, null, ICMSConstant.BIZ_SGMT);

				
				ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
				if (MaintainTeamUtil.isSuperUser(user.getLoginID())) {
					valueList = (ArrayList) segmentCodes.getCommonCodeValues();
					labelList = (ArrayList) segmentCodes.getCommonCodeLabels();
                    valueList2 = (ArrayList) applicationtype.getCommonCodeValues();
					labelList2 = (ArrayList) applicationtype.getCommonCodeLabels();
                }
				else {
					Map sgmtCodeMap = segmentCodes.getLabelValueMap();
					ITeam currentUserTeam = (ITeam) map.get(IGlobalConstant.USER_TEAM);
					if (currentUserTeam != null) {
						String[] sgmtArray = currentUserTeam.getSegmentCodes();
						if (sgmtArray != null) {
							for (int index = 0; index < sgmtArray.length; index++) {
								String label = (String) sgmtCodeMap.get(sgmtArray[index]);
								if (label != null) {
									valueList.add(sgmtArray[index]);
									labelList.add(label);
								}
							}
						}
					}
					DefaultLogger.debug(this, "Total No of sgmts: " + sgmtCodeMap.size());
				}
			}

		}
		DefaultLogger.debug(this, "No of sgmts: " + valueList.size());
		resultMap.put("segmentCodeLabel", labelList);
		resultMap.put("segmentCodeValue", valueList);
        resultMap.put("apptypeLabel", labelList2);
		resultMap.put("apptypeValue", valueList2);

        DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
