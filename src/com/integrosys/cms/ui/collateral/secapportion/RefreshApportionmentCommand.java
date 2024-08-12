/*
 * Created on Jun 28, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.secapportion;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.OBSecApportionLmtDtl;
import com.integrosys.cms.app.collateral.bus.OBSecApportionmentDtl;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RefreshApportionmentCommand extends AbstractCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "limitDtlList", "java.util.List", SERVICE_SCOPE },
				{ "apportionstate", "java.lang.String", REQUEST_SCOPE },
				{ "availableCollateralAmt", "java.lang.String", REQUEST_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE }, { "leId", "java.lang.String", REQUEST_SCOPE },
				{ "subProfileId", "java.lang.String", REQUEST_SCOPE },
				{ "limitId", "java.lang.String", REQUEST_SCOPE }, { "chargeDtlId", "java.lang.String", REQUEST_SCOPE }, };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.base.uiinfra.common.ICommand#getResultDescriptor()
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { "form.apportionLmtDtl", "java.lang.Object", FORM_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.ICommand#doExecute(java.util.HashMap)
	 */
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			List limitDtl = (List) (map.get("limitDtlList"));
			if (limitDtl == null) {
				throw new Exception("limit detail is null!");
			}
			SecApportionmentViewHelper helper = new SecApportionmentViewHelper(limitDtl);
			String from_event = (String) map.get("from_event");

			OBSecApportionmentDtl newApportionment = new OBSecApportionmentDtl();
			String state = (String) (map.get("apportionstate"));
			if (state.equals("1")) {
				// initial state
			}
			else if (state.equals("2")) {
				// le id is selected populate the le id and le name
				String leId = (String) (map.get("leId"));
				newApportionment.setLeId(leId);
				newApportionment.setLeName(helper.getLeNameByLimitId(leId));
			}
			else if (state.equals("3")) {
				// subprofile id is selected
				String leId = (String) (map.get("leId"));
				newApportionment.setLeId(leId);
				newApportionment.setLeName(helper.getLeNameByLimitId(leId));
				newApportionment.setSubProfileId((String) (map.get("subProfileId")));
			}
			else if (state.equals("4")) {
				// limit id is selected, populate the limit details also
				String leId = (String) (map.get("leId"));
				newApportionment.setLeId(leId);
				newApportionment.setLeName(helper.getLeNameByLimitId(leId));
				newApportionment.setSubProfileId((String) (map.get("subProfileId")));
				String limitId = (String) (map.get("limitId"));
				String chargeId = (String) (map.get("chargeDtlId"));
				newApportionment.setLimitID(Long.parseLong(limitId));
				OBSecApportionLmtDtl curLimitDtl = helper.getLimitDetailByLimitCharge(limitId, chargeId);
				newApportionment.setProductDesc(curLimitDtl.getProductDesc());
				newApportionment.setApprovedLimitCcy(curLimitDtl.getApprovedLimitCcy());
				newApportionment.setApprovedLimitAmt(curLimitDtl.getApprovedLimitAmt());
				newApportionment.setActivatedLimitCcy(curLimitDtl.getActivatedLimitCcy());
				newApportionment.setActivatedLimitAmt(curLimitDtl.getActivatedLimitAmt());
			}

			result.put("form.apportionLmtDtl", newApportionment);
			result.put("subtype", map.get("subtype"));
			result.put("from_event", from_event);
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return temp;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CommandProcessingException("Error when processing RefreshApportionmentCommand");
		}
	}
}
