package com.integrosys.cms.app.eventmonitor.securityriskparamexceed;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.others.IOthersCollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.AbstractCollateralTrxOperation;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

/**
 * <p>
 * Interceptor to be used to intercept Transaction Operation of collateral which
 * required to send notification after the security margin is exceed the margin
 * that being setup globally.
 * 
 * <p>
 * This will intercept the
 * {@link AbstractCollateralTrxOperation#performProcess(com.integrosys.base.businfra.transaction.ITrxValue)}
 * , before invoke the actual method, will check for whether required to send
 * notification, after collateral has been updated then send notification based
 * on the flag created earlier.
 * 
 * <p>
 * The actual transaction operation must handled by Proxy and the methods should
 * be intecepted by this interceptor.
 * 
 * @author Chong Jun Yong
 * @since 22.10.2008
 * 
 * 
 */
public class SecurityMarginExceedTrxOperationInterceptor implements MethodInterceptor {

	private final Logger logger = LoggerFactory.getLogger(SecurityMarginExceedTrxOperationInterceptor.class);

	private final static String RULE_SECURITY_RISK_PARAM_EXCEED = "R_COL_RISK_PARAM_EXCEED";

	private final static String EVENT_SECURITY_RISK_PARAM_EXCEED = "EV_COL_RISK_PARAM_EXCEED";

	private ICustomerDAO customerDao;

	/**
	 * @param customerDao the customerDao to set
	 */
	public void setCustomerDao(ICustomerDAO customerDao) {
		this.customerDao = customerDao;
	}

	/**
	 * @return the customerDao
	 */
	public ICustomerDAO getCustomerDao() {
		return customerDao;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		Object[] arguments = invocation.getArguments();
		AbstractCollateralTrxOperation trxOperation = (AbstractCollateralTrxOperation) invocation.getThis();

		if (method.getName().equals("performProcess")) {
			Double[] marginList = checkSendNotification((ICollateralTrxValue) arguments[0]);

			ITrxResult result = (ITrxResult) method.invoke(trxOperation, arguments);

			if ((marginList != null) && (marginList.length > 0)) {
				sendSecurityRiskParamNotification((ICollateralTrxValue) result.getTrxValue(), marginList);
			}
			
			return result;
		}

		return method.invoke(trxOperation, arguments);
	}

	/**
	 * Helper method to check whether security margin exceed country crp margin
	 * notification is needed or not
	 * @param trxValue is of type ICollateralTrxValue
	 * @return Double[] list of margin - null if notification is not needed
	 */
	private Double[] checkSendNotification(ICollateralTrxValue trxValue) {
		ICollateral actualCol = trxValue.getCollateral();
		ICollateral stageCol = trxValue.getStagingCollateral();

		if ((actualCol instanceof IOthersCollateral) || (actualCol instanceof ICommodityCollateral)) {
			return null;
		}

		double crpMargin = getCRP(actualCol);

		// for asset based post dated cheque
		// if cheque margin changed and
		// cheque margin > crpMargin need to send notification
		if (actualCol instanceof IAssetPostDatedCheque) {
			IPostDatedCheque[] stageChequeList = ((IAssetPostDatedCheque) stageCol).getPostDatedCheques();

			if ((stageChequeList != null) && (stageChequeList.length > 0)) {
				Map actualChequeMap = getChequeMapByRef((IAssetPostDatedCheque) actualCol);
				List colMarginList = new ArrayList();
				for (int i = 0; i < stageChequeList.length; i++) {
					IPostDatedCheque actualObj = (IPostDatedCheque) actualChequeMap.get(String
							.valueOf(stageChequeList[i].getRefID()));
					double tmpMargin = ICMSConstant.DOUBLE_INVALID_VALUE;
					if (actualObj != null) {
						tmpMargin = actualObj.getMargin();
					}
					double tmpStgMargin = stageChequeList[i].getMargin();
					if (requiredMarginCheck(tmpMargin, tmpStgMargin)) {
						if (tmpStgMargin > crpMargin) {
							colMarginList.add(new Double(tmpStgMargin * 100));
						}
					}
				}

				double actualMargin = actualCol.getMargin();
				double stageMargin = stageCol.getMargin();
				if (requiredMarginCheck(actualMargin, stageMargin)) {
					if (stageMargin > crpMargin) {
						colMarginList.add(new Double(stageMargin * 100));
					}
				}

				if ((colMarginList != null) && (colMarginList.size() > 0)) {
					return (Double[]) colMarginList.toArray(new Double[0]);
				}
			}
			return null;

		}

		// for remaining security subtypes
		// if security margin changed and security margin > crp margin
		// need to send notification
		double actualMargin = actualCol.getMargin();
		double stageMargin = stageCol.getMargin();

		if (requiredMarginCheck(actualMargin, stageMargin)) {
			if (stageMargin > crpMargin) {
				return (new Double[] { new Double(stageMargin * 100) });
			}

		}
		return null;
	}

	/**
	 * Helper method to get CRP value given the collateral. It is based on
	 * security location and security subtype code.
	 * 
	 * @param col of type ICollateral
	 * @return CRP value
	 */
	private double getCRP(ICollateral col) {
		ICollateralParameter param = getSecurityParameter(col);
		if (param != null) {
			double crpMargin = param.getThresholdPercent();
			return (crpMargin / 100);
		}
		return ICMSConstant.DOUBLE_INVALID_VALUE;
	}

	private boolean requiredMarginCheck(double actualMargin, double stageMargin) {
		if (!isInvalidMargin(stageMargin) && (stageMargin != actualMargin)) {
			return true;
		}
		return false;
	}

	private Map getChequeMapByRef(IAssetPostDatedCheque chequeCol) {
		IPostDatedCheque[] chequeList = chequeCol.getPostDatedCheques();
		Map chequeMap = new HashMap();
		if (chequeList != null) {
			for (int i = 0; i < chequeList.length; i++) {
				chequeMap.put(String.valueOf(chequeList[i].getRefID()), chequeList[i]);
			}
		}
		return chequeMap;
	}

	/**
	 * Helper method to get collateral parameter by subtype code and country.
	 * 
	 * @param col of type ICollateral
	 * @return ICollateralParameter
	 */
	private ICollateralParameter getSecurityParameter(ICollateral col) {
		try {
			String ctry = col.getCollateralLocation();
			String subtype = col.getCollateralSubType().getSubTypeCode();

			ICollateralParameter param = null;
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			param = proxy.getCollateralParameter(ctry, subtype);

			return param;
		}
		catch (Exception e) {
			logger.warn("failed to retrieve collateral parameter for country [" + col.getCollateralLocation()
					+ "] subtype [" + col.getCollateralSubType().getSubTypeCode() + "]", e);
			return null;
		}
	}

	private boolean isInvalidMargin(double margin) {
		if (margin == ICMSConstant.DOUBLE_INVALID_VALUE) {
			return true;
		}
		if (margin == ICMSConstant.DOUBLE_INVALID_VALUE / 100) {
			return true;
		}

		return false;
	}

	private void sendSecurityRiskParamNotification(ICollateralTrxValue trxValue, Double[] marginList)
			throws TrxOperationException {
		try {
			ICollateral col = trxValue.getCollateral();

			OBSecurityRiskInfo info = new OBSecurityRiskInfo();
			info.setSecurityID(col.getSCISecurityID());
			info.setSecurityType(col.getCollateralType().getTypeName());
			info.setSecuritySubType(col.getCollateralSubType().getSubTypeName());
			info.setSecurityLocation(col.getCollateralLocation());

			double crpMargin = getCRP(col);

			if (crpMargin != ICMSConstant.DOUBLE_INVALID_VALUE) {
				info.setCountryMargin(crpMargin * 100);
			}
			else {
				info.setCountryMargin(0);
			}

			info.setSecurityMargin(marginList);

			OBCustomerSearchResult[] customerArray = getCustomerArray(col);
			info.setCustomerList(customerArray);
			String originCountry = customerArray[0].getInstrOrigLocation().getCountryCode();
			List secCtyList = new ArrayList();

			for (int index = 1; index < customerArray.length; index++) {
				String ctyCode = customerArray[index].getInstrOrigLocation().getCountryCode();
				if (!originCountry.equals(ctyCode) && !secCtyList.contains(ctyCode)) {
					secCtyList.add(ctyCode);
				}
			}

			info.setOriginatingCountry(originCountry);
			if ((secCtyList != null) && (secCtyList.size() > 0)) {
				info.setSecondaryCountryList((String[]) secCtyList.toArray(new String[0]));
			}

			SecurityRiskParamExceedListener listener = new SecurityRiskParamExceedListener();

			List list = new ArrayList();
			list.add(info);
			list.add("");
			list.add(constructRuleParam());
			listener.fireEvent(EVENT_SECURITY_RISK_PARAM_EXCEED, list);
		}
		catch (Exception e) {
			throw new TrxOperationException("failed to send security risk parameter notification for collateral ["
					+ trxValue.getCollateral().getCollateralID() + "]", e);
		}
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	private IRuleParam constructRuleParam() {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID(RULE_SECURITY_RISK_PARAM_EXCEED);
		param.setSysDate(DateUtil.getDate());
		param.setRuleNum(-1);
		return param;
	}

	private OBCustomerSearchResult[] getCustomerArray(ICollateral col) throws SearchDAOException {
		List mbLmtIDList = new ArrayList();
		List cbLmtIDList = new ArrayList();

		ICollateralLimitMap[] limitMapList = col.getCurrentCollateralLimits();

		for (int i = 0; i < limitMapList.length; i++) {
			if (ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER.equals(limitMapList[i].getCustomerCategory())) {
				if (!cbLmtIDList.contains(String.valueOf(limitMapList[i].getCoBorrowerLimitID()))) {
					cbLmtIDList.add(String.valueOf(limitMapList[i].getCoBorrowerLimitID()));
				}
			}
			else {
				if (!mbLmtIDList.contains(String.valueOf(limitMapList[i].getLimitID()))) {
					mbLmtIDList.add(String.valueOf(limitMapList[i].getLimitID()));
				}
			}
		}

		Collection customerList = new ArrayList();
		if (mbLmtIDList.size() > 0) {
			customerList.addAll(getCustomerDao().getMBInfoByLimitIDList(mbLmtIDList));
		}

		if (cbLmtIDList.size() > 0) {
			customerList.addAll(getCustomerDao().getCBInfoByLimitIDList(cbLmtIDList));
		}

		return (OBCustomerSearchResult[]) customerList.toArray(new OBCustomerSearchResult[0]);
	}

}
