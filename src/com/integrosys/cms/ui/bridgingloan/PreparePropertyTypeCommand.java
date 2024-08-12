package com.integrosys.cms.ui.bridgingloan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IPropertyType;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: Apr 15, 2007 Time: 9:11:28 PM To
 * change this template use File | Settings | File Templates.
 */
public class PreparePropertyTypeCommand extends AbstractCommand {

	public PreparePropertyTypeCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "propertyTypeLabels", "java.util.List", REQUEST_SCOPE },
				{ "propertyTypeValues", "java.util.List", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside PreparePropertyTypeCommand doExecute()");
		String event = (String) map.get("event");
		DefaultLogger.debug(this, "event=" + event);

		try {
			IBridgingLoan objBridgingLoan = (IBridgingLoan) map.get("objBridgingLoan");
			DefaultLogger.debug(this, "objBridgingLoan: " + objBridgingLoan);

			CommonCodeList propertyTypeList = CommonCodeList.getInstance(null, ICMSUIConstant.PROPERTY_TYPE, true);
			ArrayList propertyTypeLabels = new ArrayList(propertyTypeList.getCommonCodeLabels());
			ArrayList propertyTypeValues = new ArrayList(propertyTypeList.getCommonCodeValues());

			// Filter during maker create
			if (PropertyTypeAction.EVENT_MAKER_PREPARE_CREATE.equals(event)
					|| PropertyTypeAction.EVENT_CREATE.equals(event)) {
				IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
				if ((trxValue != null) && (trxValue.getStagingBridgingLoan() != null)
						&& (trxValue.getStagingBridgingLoan().getPropertyTypeList() != null)) {
					IPropertyType[] objPropertyTypeList = trxValue.getStagingBridgingLoan().getPropertyTypeList();
					HashMap propertyTypeMap = new HashMap();
					for (int i = 0; i < objPropertyTypeList.length; i++) {
						if (!objPropertyTypeList[i].getIsDeletedInd()) { // not
																			// include
																			// deleted
							propertyTypeMap.put(objPropertyTypeList[i].getPropertyType(), objPropertyTypeList[i]
									.getPropertyType());
						}
					}
					HashMap hm = filterList(propertyTypeValues, propertyTypeLabels, propertyTypeMap);
					propertyTypeLabels = (ArrayList) hm.get("labels");
					propertyTypeValues = (ArrayList) hm.get("values");
				}
			}
			resultMap.put("propertyTypeLabels", propertyTypeLabels);
			resultMap.put("propertyTypeValues", propertyTypeValues);

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}

	/**
	 * This method is to filter a list, which will remove previous selected
	 * value
	 * 
	 * @param values - list of value
	 * @param labels - list of labels
	 * @param hm - previous selected value
	 * @return HashMap with the Result
	 */
	public HashMap filterList(List values, List labels, HashMap hm) {
		for (int i = values.size() - 1; i > -1; i--) {
			if (hm.get(values.get(i)) != null) {
				values.remove(i);
				labels.remove(i);
			}
		}
		HashMap returnHm = new HashMap();
		returnHm.put("values", values);
		returnHm.put("labels", labels);
		return returnHm;
	}
}