package com.integrosys.cms.ui.limit.facility.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.FacilityException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.proxy.IFacilityProxy;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.host.stp.common.StpErrorMessageFetcherImpl;
import com.integrosys.cms.host.stp.bus.IStpTransBusManager;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class ReadFacilityMasterCommand extends FacilityMainCommand {
    private List allowedInsuranceProductTypeList;
    private IStpTransBusManager stpTransBusManager;

    public List getAllowedInsuranceProductTypeList() {
        return allowedInsuranceProductTypeList;
    }

    public void setAllowedInsuranceProductTypeList(List allowedInsuranceProductTypeList) {
        this.allowedInsuranceProductTypeList = allowedInsuranceProductTypeList;
    }

    public IStpTransBusManager getStpTransBusManager() {
        return stpTransBusManager;
    }

    public void setStpTransBusManager(IStpTransBusManager stpTransBusManager) {
        this.stpTransBusManager = stpTransBusManager;
    }

    public String[][] getParameterDescriptor() {
		return (new String[][] { { "cmsLimitId", "java.lang.String", REQUEST_SCOPE },
				{ "listLimits", "java.util.List", SERVICE_SCOPE }, { "nextTab", "java.lang.String", REQUEST_SCOPE },
				{ "limit", "com.integrosys.cms.app.limit.bus.ILimit", SERVICE_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "trxId", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "facilityTrxValue", "com.integrosys.cms.app.limit.trx.IFacilityTrxValue", SERVICE_SCOPE },
				{ "tabState", "java.lang.String", SERVICE_SCOPE },
				{ "errorMap", "java.util.Map", SERVICE_SCOPE }});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "limit", "com.integrosys.cms.app.limit.bus.ILimit", SERVICE_SCOPE },
				{ "currentTab", "java.lang.String", SERVICE_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "facilityTrxValue", "com.integrosys.cms.app.limit.trx.IFacilityTrxValue", SERVICE_SCOPE },
				{ FacilityMasterForm.MAPPER, "com.integrosys.cms.app.limit.bus.IFacilityMaster", FORM_SCOPE },
				{ "tabState", "java.lang.String", SERVICE_SCOPE },
                { "errorMsg", "java.lang.String", SERVICE_SCOPE },
                { "trxToState", "java.lang.String", SERVICE_SCOPE},
                { "allowInsurance", "java.lang.String", SERVICE_SCOPE},
                { "defaultRevolvingInd", "java.lang.String", REQUEST_SCOPE},
                { "islamicStpMsgType", "java.lang.String", SERVICE_SCOPE}});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			// for ui tab
			String currentTab = null;
			String event = (String) map.get("event");
			String oldTabState = (String) map.get("tabState");
			String tabState = null;
			if(oldTabState==null){
				if (EVENT_PROCESS.equals(event)) {
					tabState = EVENT_PROCESS;
				}
				else if (FacilityMainAction.EVENT_PREPARE_CLOSE.equals(event)) {
					tabState = FacilityMainAction.EVENT_PREPARE_CLOSE;
				}
				oldTabState = tabState;
			}
			result.put("tabState", oldTabState);

            Map errorMap = (Map) map.get("errorMap");
            if (errorMap != null) {
                ActionErrors errorMessages = (ActionErrors) errorMap.get("facilityMaster");
                if (errorMessages != null) {
                    DefaultLogger.warn(this, "there is error message for facilityMaster, "
                            + "please retrieve from the display page.");
                    returnMap.put(MESSAGE_LIST, errorMessages);
                }
            }

			IFacilityTrxValue facilityTrxValue = null;
			IFacilityProxy proxy = getFacilityProxy();
			ILimit limit = (ILimit) map.get("limit");
			ILimit limitObj = null;
			if (limit != null) {
				limitObj = new OBLimit(limit);
			}

			if (map.get("trxId") == null) {
				// get proxy, using id to get trxValue, get object from
				// actual, put it in session
				long cmsLimitId = 0;
				if (map.get("cmsLimitId") != null) {
					cmsLimitId = Long.parseLong(((String) map.get("cmsLimitId")));
				}
				else if (map.get("limit") != null) {
					cmsLimitId = limit.getLimitID();
				}

				List listLimits = (List) map.get("listLimits");
				if (listLimits != null) {
					if (limit == null || (limit.getLimitID() != cmsLimitId)) {
						ILimit[] limits = (ILimit[]) listLimits.toArray(new ILimit[0]);
						for (int i = 0; i < limits.length; i++) {
							if (cmsLimitId == limits[i].getLimitID()) {
								limitObj = new OBLimit(limits[i]);
								result.put("limit", limits[i]);
								break;
							}
						}
					}
				}

				if (cmsLimitId == 0 && map.get("facilityTrxValue") != null) {
					facilityTrxValue = (IFacilityTrxValue) map.get("facilityTrxValue");
					cmsLimitId = facilityTrxValue.getStagingFacilityMaster().getLimit().getLimitID();
				}
				facilityTrxValue = proxy.retrieveFacilityMasterTransactionByCmsLimitId(cmsLimitId);
				if (facilityTrxValue != null && ICMSConstant.STATE_CLOSED.equals(facilityTrxValue.getStatus())) {
					facilityTrxValue = null;
                }
			}
			else {
				String trxId = (String) map.get("trxId");
				facilityTrxValue = proxy.retrieveFacilityMasterTransactionByTransactionId(trxId);
            }

            //Andy Wong, 20 Jan 2009: set Stp validation error message into trx status
            if (StringUtils.equals(facilityTrxValue.getStatus(), ICMSConstant.STATE_PENDING_PERFECTION)
                    || StringUtils.equals(facilityTrxValue.getStatus(), ICMSConstant.STATE_PENDING_RETRY)
                    || StringUtils.equals(facilityTrxValue.getStatus(), ICMSConstant.STATE_REJECTED_UPDATE)
                    || StringUtils.equals(facilityTrxValue.getStatus(), ICMSConstant.STATE_DRAFT)) {
                currentTab = FacilityMainAction.TAB_MASTER_WO_FRAME;
            } else {
                String nextTab = (String) map.get("nextTab");
                if (StringUtils.isNotBlank(nextTab)) {
                    currentTab = nextTab;
                } else {
                    currentTab = FacilityMainAction.TAB_MASTER;
                }
            }
            result.put("currentTab", currentTab);

            String error = null;
            if(facilityTrxValue != null){
                StpErrorMessageFetcherImpl iStpErrorMessageFetcher = (StpErrorMessageFetcherImpl) BeanHouse.get("stpErrorMessageFetcher");
                ArrayList aList = (ArrayList)iStpErrorMessageFetcher.getErrorMessage(facilityTrxValue.getTransactionID());
                if(aList != null && aList.size() > 0){
                    error = (String)aList.get(0);
                }
            }
            result.put("errorMsg", error);

            if (map.get("facilityMasterObj") == null) {
				IFacilityMaster facilityMasterObj = null;
				// set the value of facilityMasterObj in session to value
				// existed in DB (only for the first time)
				if (facilityTrxValue != null) {
					if (facilityTrxValue.getStagingFacilityMaster() != null
							&& (FacilityMainAction.EVENT_PREPARE_CLOSE.equals(event) || FacilityMainAction.EVENT_PROCESS
									.equals(event)) || FacilityMainAction.TAB_MASTER_CHECKER.equals(event)
									|| FacilityMainAction.TAB_MASTER_CHECKER_PROCESS.equals(event)
									|| FacilityMainAction.TAB_MASTER_VIEW_WO_FRAME.equals(event)
									|| FacilityMainAction.EVENT_VIEW.equals(event)) {
						facilityMasterObj = facilityTrxValue.getStagingFacilityMaster();
					}
					else if (facilityTrxValue.getFacilityMaster() != null) {
						facilityMasterObj = facilityTrxValue.getFacilityMaster();
					}
				}
				if (facilityMasterObj == null) {
					facilityMasterObj = new OBFacilityMaster();
					facilityMasterObj.setLimit(limitObj);
				}
				facilityMasterObj = FacilityUtil.createObjectsInsideFacilityMaster(facilityMasterObj);

                // set defaultRevolvingIndicator to do defaulting on Revolving Indicator field
                String defaultRevolvingIndicator = checkDefaultRevolvingInd(facilityMasterObj.getLimit().getFacilityCode());
                if ((ICMSConstant.TRUE_VALUE).equals(defaultRevolvingIndicator)) {
                    facilityMasterObj.setRevolvingIndicator(ICMSConstant.FALSE_VALUE);
                }

                result.put("trxToState", facilityTrxValue.getToState());
                result.put("facilityMasterObj", facilityMasterObj);
				result.put("facilityTrxValue", facilityTrxValue);
				result.put(FacilityMasterForm.MAPPER, facilityMasterObj);
                result.put("defaultRevolvingInd", defaultRevolvingIndicator);
            }
			else {
                IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");

                // set defaultRevolvingIndicator to do defaulting on Revolving Indicator field
                String defaultRevolvingIndicator = checkDefaultRevolvingInd(facilityMasterObj.getLimit().getFacilityCode());
                if ((ICMSConstant.TRUE_VALUE).equals(defaultRevolvingIndicator)) {
                    facilityMasterObj.setRevolvingIndicator(ICMSConstant.FALSE_VALUE);
                }

                result.put(FacilityMasterForm.MAPPER, facilityMasterObj);
                result.put("defaultRevolvingInd", defaultRevolvingIndicator);
            }

            //Andy Wong, 20 Jan 2009: set allowInsurance flag to enable/disable facility insurance tab
            String limitProductType = getFacilityProxy().getProductGroupByProductCode(
                    facilityTrxValue.getFacilityMaster().getLimit().getProductDesc());
            String allowInsurance = "N";
            for (Iterator iterator = allowedInsuranceProductTypeList.iterator(); iterator.hasNext();) {
                String productCode = (String) iterator.next();
                if(StringUtils.equals(productCode, limitProductType)) {
                    allowInsurance = "Y";
                    break;
                }
            }
            result.put("allowInsurance", allowInsurance);

            //Set islamicStpMsgType to do filtering on islamic / BBA tab
            String islamicStpMessageType = getStpTransBusManager().getStpIslamicLoanType(
                    facilityTrxValue.getFacilityMaster().getLimit().getProductDesc(),
                    facilityTrxValue.getFacilityMaster().getLimit().getFacilityCode());
            result.put("islamicStpMsgType", islamicStpMessageType);
        }
		catch (FacilityException ex) {
			throw new CommandProcessingException("failed to retrieve facility using cms limit id", ex);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

    private String checkDefaultRevolvingInd (String facilityCode) {
        String defaultRevolvingInd = ICMSConstant.FALSE_VALUE;
        DefaultLogger.debug(this, "Facility Code ^^^^^^^ " + facilityCode);
        String revolvingIndicator = getFacilityProxy().getRevolvingFlagByFacilityCode(facilityCode);
        DefaultLogger.debug(this, "Revolving Indicator ^^^^^^^ " + revolvingIndicator);

        if (StringUtils.isNotBlank(revolvingIndicator) && (ICMSConstant.FALSE_VALUE).equals(revolvingIndicator)) {
            defaultRevolvingInd = ICMSConstant.TRUE_VALUE;
        }
        return defaultRevolvingInd;
    }


}
