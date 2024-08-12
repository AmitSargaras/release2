package com.integrosys.cms.ui.collateral.marketablesec;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.dbsupport.JdbcResultGenerator;
import com.integrosys.base.uiinfra.common.AbstractOptionsListAction;

/**
 * <p>
 * Retrieve the counters of the portfolio added for the marketable securities.
 * <p>
 * Request must passed in the valid marketable securities subtype id.
 * @author Chong Jun Yong
 * 
 */
public class GetCountersByCollateralSubtypeAction extends AbstractOptionsListAction {

	private JdbcResultGenerator stockNameAndCodeResultGenerator;

	private String[] validMarketableSubType;

	public void setStockNameAndCodeResultGenerator(JdbcResultGenerator stockNameAndCodeResultGenerator) {
		this.stockNameAndCodeResultGenerator = stockNameAndCodeResultGenerator;
	}

	/**
	 * The valid marketable security sub type id that can invoke this action.
	 * @param validMarketableSubType valid marketable security sub type id
	 */
	public void setValidMarketableSubType(String[] validMarketableSubType) {
		this.validMarketableSubType = validMarketableSubType;
	}

	protected boolean preCheck(ActionForm form, HttpServletRequest request) {
		String marketableSubType = request.getParameter("marketableSubType");
		if (!ArrayUtils.contains(this.validMarketableSubType, marketableSubType)) {
			return false;
		}
		return true;
	}

	protected String extractLabel(Object option) {
		String[] labelValue = (String[]) option;
		return labelValue[0];
	}

	protected String extractValue(Object option) {
		String[] labelValue = (String[]) option;
		return labelValue[1];
	}

	protected void afterProcessOptions(StringBuffer optionsBuf) {
		optionsBuf.append("<option label=\"ALL\" value=\"ALL\" />");
	}

	protected List generateOptionsList(ActionForm form, HttpServletRequest request) {
		String marketableSubType = request.getParameter("marketableSubType");
		return (List) this.stockNameAndCodeResultGenerator.getResult(new Object[] { marketableSubType });
	}
}
