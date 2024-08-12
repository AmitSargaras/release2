package com.integrosys.cms.ui.collateral.pledgor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

public class DeletePledgorCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "pledgorRemove", "java.util.List", FORM_SCOPE }, { "subtype", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");

			ICollateral iCol = trxValue.getStagingCollateral();
			// ICollateralPledgor[] pledgor = iCol.getPledgors();
			List removePledgors = (List) map.get("pledgorRemove");
			if (removePledgors != null) {
				ICollateralPledgor[] pledgors = iCol.getPledgors();
				List pledgorList = new ArrayList();
				if (pledgors != null) {
					for (int i = 0; i < pledgors.length; i++) {
						boolean isFound = false;
						for (int j = 0; j < removePledgors.size(); j++) {
							if (removePledgors.get(j).equals(pledgors[i].getLegalID())) {
								isFound = true;
							}
						}
						if (!isFound) {
							pledgorList.add(pledgors[i]);
						}
					}
				}
				iCol.setPledgors((ICollateralPledgor[]) pledgorList.toArray(new ICollateralPledgor[0]));
				trxValue.setStagingCollateral(iCol);
			}
			result.put("serviceColObj", trxValue);
			result.put("subtype", map.get("subtype"));
		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute", e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}