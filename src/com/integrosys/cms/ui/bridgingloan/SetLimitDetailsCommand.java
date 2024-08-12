package com.integrosys.cms.ui.bridgingloan;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.bridgingloan.trx.OBBridgingLoanTrxValue;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 21, 2007 Time: 3:54:40 PM To
 * change this template use File | Settings | File Templates.
 */
public class SetLimitDetailsCommand extends AbstractCommand {

	public SetLimitDetailsCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "limitID", "java.lang.String", REQUEST_SCOPE },
				{ "sourceLimit", "java.lang.String", REQUEST_SCOPE },
				{ "productDescription", "java.lang.String", REQUEST_SCOPE },
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "limitProfileID", "java.lang.String", SERVICE_SCOPE },
				{ "limitID", "java.lang.String", SERVICE_SCOPE },
				{ "sourceLimit", "java.lang.String", SERVICE_SCOPE },
				{ "productDescription", "java.lang.String", SERVICE_SCOPE },
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside SetLimitDetailsCommand doExecute()");

		try {
			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");

			if (trxValue == null) {
				trxValue = new OBBridgingLoanTrxValue();
			}

			if ((String) map.get("limitID") != null) {
				trxValue = new OBBridgingLoanTrxValue();
				IBridgingLoan newBridgingLoan = new OBBridgingLoan();
				newBridgingLoan.setLimitProfileID(Long.parseLong((String) map.get("limitProfileID")));
				newBridgingLoan.setLimitID(Long.parseLong((String) map.get("limitID")));
				newBridgingLoan.setSourceLimit((String) map.get("sourceLimit"));
				newBridgingLoan.setProductDescription((String) map.get("productDescription"));
				trxValue.setStagingBridgingLoan(newBridgingLoan);
			}
			resultMap.put("bridgingLoanTrxValue", trxValue);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}
}