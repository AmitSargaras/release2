/*
 * Created on 2007-2-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import static com.integrosys.cms.ui.manualinput.IManualInputConstants.CO_BORROWER_LIST;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.OBLimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PrepareCreateLmtCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },});

	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "dispFieldMapper", "java.lang.Object", FORM_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				
				{ CO_BORROWER_LIST, List.class.getName(), SERVICE_SCOPE },
				{ "inrValue", "java.lang.String", SERVICE_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
			ICommonUser user = (ICommonUser) (map.get(IGlobalConstant.USER));
			String limitProfileId = (String) (map.get("limitProfileID"));

			long lmtProfId = Long.parseLong(limitProfileId);
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitProfile profile = limitProxy.getLimitProfile(lmtProfId);

			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer cust = custProxy.getCustomer(profile.getCustomerID());

			OBLimitTrxValue limitTrxValue = new OBLimitTrxValue();
			OBLimit newLmt = new OBLimit();
			newLmt.setLimitProfileID(lmtProfId);
			newLmt.setBookingLocation(new OBBookingLocation());

			limitTrxValue.setStagingLimit(newLmt);

			// set the location for limit
			limitTrxValue.setCustomerID(profile.getCustomerID());
			limitTrxValue.setCustomerName(cust.getCustomerName());
			limitTrxValue.setLegalID(profile.getLEReference());
			limitTrxValue.setLegalName(cust.getLegalEntity().getLegalName());
			limitTrxValue.setTeamID(team.getTeamID());
			limitTrxValue.setLimitProfileID(lmtProfId);
			limitTrxValue.setLimitProfileReferenceNumber(profile.getBCAReference());

			// default country currency to the users country
			newLmt.getBookingLocation().setCountryCode(user.getCountry());
			
			/*Ccommented for HDFC and made currency to INR*/
			//String defaultCurrency = CurrencyList.getInstance().getCurrencyCodeByCountry(user.getCountry());
			String defaultCurrency = "INR";
			newLmt.setApprovedLimitAmount(new Amount(ICMSConstant.DOUBLE_INVALID_VALUE, defaultCurrency));
			
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			List lmtList = proxy.getLimitSummaryListByAA(limitProfileId);
			List lmtListFormated = helper.formatLimitListView(lmtList, locale);
			
			BigDecimal funded = new BigDecimal("0");
			BigDecimal nonFunded = new BigDecimal("0");
			BigDecimal memoExposure = new BigDecimal("0");
			BigDecimal convertedAmount = new BigDecimal("0");
			BigDecimal exchangeRate = new BigDecimal("1");
			
			for(int i = 0; i < lmtListFormated.size(); i++){
				LimitListSummaryItem lstSummaryItem = (LimitListSummaryItem) lmtListFormated.get(i);
				if(!AbstractCommonMapper.isEmptyOrNull(lstSummaryItem.getCurrencyCode())){
					 IForexFeedProxy frxPxy = (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
					 exchangeRate = frxPxy.getExchangeRateWithINR(lstSummaryItem.getCurrencyCode().trim());
				}
				if(lstSummaryItem.getFacilityTypeCode().equalsIgnoreCase("FUNDED") && "No".equalsIgnoreCase(lstSummaryItem.getIsSubLimit())){
					convertedAmount = exchangeRate.multiply(new BigDecimal(lstSummaryItem.getActualSecCoverage()));
					funded = funded.add(convertedAmount);
				}
				if(lstSummaryItem.getFacilityTypeCode().equalsIgnoreCase("NON_FUNDED") && "No".equalsIgnoreCase(lstSummaryItem.getIsSubLimit())){
					convertedAmount = exchangeRate.multiply(new BigDecimal(lstSummaryItem.getActualSecCoverage()));
					nonFunded = nonFunded.add(convertedAmount);
				}
				if(lstSummaryItem.getFacilityTypeCode().equalsIgnoreCase("MEMO_EXPOSURE") && "No".equalsIgnoreCase(lstSummaryItem.getIsSubLimit())){
					convertedAmount = exchangeRate.multiply(new BigDecimal(lstSummaryItem.getActualSecCoverage()));
					memoExposure = memoExposure.add(convertedAmount);
				}
			}
			
			BigDecimal totFunded = new BigDecimal("0");
			BigDecimal totNonFunded = new BigDecimal("0");
			BigDecimal totMemoExposure = new BigDecimal("0");
			if(cust.getTotalFundedLimit() != null){
			totFunded = new BigDecimal(cust.getTotalFundedLimit()).subtract(funded);
			}
			if(cust.getTotalNonFundedLimit() != null){
				totNonFunded = new BigDecimal(cust.getTotalNonFundedLimit()).subtract(nonFunded);
			}
			if(cust.getMemoExposure() != null){
				totMemoExposure = new BigDecimal(cust.getMemoExposure()).subtract(memoExposure);
			}
			
			ILimitDAO limit = LimitDAOFactory.getDAO();
			List<ICoBorrowerDetails> coBorrowerList = limit.getPartyCoBorrowerDetails(profile.getCustomerID());
			result.put(CO_BORROWER_LIST, coBorrowerList);
			
			result.put("fundedAmount", totFunded.toString());
			result.put("nonFundedAmount", totNonFunded.toString());
			result.put("memoExposer", totMemoExposure.toString());
			result.put("sanctionedLimit", cust.getTotalSanctionedLimit());
			result.put("lmtTrxObj", limitTrxValue);
			result.put("dispFieldMapper", limitTrxValue);
			result.put("limitProfileID", limitProfileId);
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
