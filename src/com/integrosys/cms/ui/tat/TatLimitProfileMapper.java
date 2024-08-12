/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/tat/TatLimitProfileMapper.java,v 1.19 2005/08/05 06:18:41 lyng Exp $
 */

package com.integrosys.cms.ui.tat;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;

/**
 * Mapper class is used to map form values to objects and vice versa
 * @author $Author: lyng $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2005/08/05 06:18:41 $ Tag: $Name: $
 */
public class TatLimitProfileMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public TatLimitProfileMapper() {
		DefaultLogger.debug(this, "Inside constructor");
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "trxValue", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "bcaLocalInd", "java.lang.String", REQUEST_SCOPE },
				{ "tatCreateDate", "java.lang.String", REQUEST_SCOPE },
				{ "bflRequired", "java.lang.String", REQUEST_SCOPE } }

		);
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @throws com.integrosys.base.uiinfra.exception.MapperException on errors
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		TatsForm aform = (TatsForm) cForm;
		OBLimitProfileTrxValue trxVal = (OBLimitProfileTrxValue) inputs.get("trxValue");
		ILimitProfile temp;
		temp = trxVal.getLimitProfile();
		if (temp == null) {
			throw new MapperException("The limitprofile ob is null in mapper");
		}
		temp.setTATCreateDate(DateUtil.convertDate(aform.getTatCreateDate()));
		temp.setBCACreateDate(DateUtil.convertDate(aform.getBcaCreateDate()));
		temp.setBflIndUpdateDate(DateUtil.getDate());

		if (isEmptyOrNull(aform.getExtendedDateBFL())) {
			temp.setExtendedBFLIssuanceDate(null);
		}
		else {
			temp.setExtendedBFLIssuanceDate(DateUtil.convertDate(aform.getExtendedDateBFL()));
		}

		if (aform.getBflRequired().equals("N")) {
			temp.setBFLRequiredInd(false);
			temp.setExtendedBFLIssuanceDate(null);
		}
		else {
			temp.setBFLRequiredInd(true);
		}
		if (aform.getBcaLocalInd().equals("N")) {
			temp.setBCALocalInd(false);
		}
		else {
			temp.setBCALocalInd(true);
		}
		if (temp == null) {
			throw new MapperException("The limitprofile ob is null in mapper....");
		}
		return (temp);

		// return cSearch;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @throws com.integrosys.base.uiinfra.exception.MapperException on errors
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		TatsForm aForm = (TatsForm) cForm;
		if (obj != null) {
			OBLimitProfile ob = (OBLimitProfile) obj;
			boolean ind = ob.getBFLRequiredInd();
			String bcaLocalInd = (String) map.get("bcaLocalInd");
			String tatCreateDate = (String) map.get("tatCreateDate");
			String bflRequired = (String) map.get("bflRequired");
			if (!((aForm.getEvent().equals("refresh")) || (aForm.getEvent().equals("refreshresubmit")))) {
				if (ob.getBCALocalInd()) {
					bcaLocalInd = "Y";
				}
				else {
					bcaLocalInd = "N";
				}
			}

			Date latestBFLDate = ob.getCleanSpecialBFLIssuanceDate();

			if (latestBFLDate != null) {
				Date maxBFLExtendedDate = CommonUtil.rollUpDateByDays(latestBFLDate,
						ICMSConstant.BFL_TAT_MAX_EXTENDED_DAY);
				aForm.setMaxBFLExtendedDate(DateUtil.formatDate(locale, maxBFLExtendedDate));
			}
			else {
				aForm.setMaxBFLExtendedDate(null);
			}

			Date dt[] = new Date[2];
			ILimitProxy limitproxy = LimitProxyFactory.getProxy();
			/* CR104 change the calculation for due date */
			try {
				if (bcaLocalInd != null) {
					if (!((aForm.getEvent().equals("refresh")) || (aForm.getEvent().equals("refreshresubmit")))) {
						// if (ob.getApprovalDate() != null) {
						if (latestBFLDate != null) {
							if (ob.getBFLRequiredInd()) {
								if ((ob.getOriginatingLocation().getCountryCode() != null)
										&& (aForm.getCustomerSegment() != null)) {
									// dt =
									// limitproxy.getBFLDueDates(ob.getRenewalInd
									// (), aForm.getCustomerSegment(),
									// ob.getOriginatingLocation
									// ().getCountryCode(),
									// ob.getApprovalDate());
									dt = limitproxy.getBFLDueDates(ob.getRenewalInd(), aForm.getCustomerSegment(), ob
											.getOriginatingLocation().getCountryCode(), latestBFLDate);
								}
								if (bcaLocalInd.equals("Y")) {
									if (dt[0] != null) {
										aForm.setDueDate(DateUtil.formatDate(locale, dt[0]));
									}
									else {
										aForm.setDueDate(null);
									}
								}
								else {
									if (dt[1] != null) {
										aForm.setDueDate(DateUtil.formatDate(locale, dt[1]));
									}
									else {
										aForm.setDueDate(null);
									}
								}
							}
							else {
								aForm.setDueDate("-");// Due date calculation is
														// not required as BFL
														// requiredInd is No
							}
						}

					}
					else {
						// if (ob.getApprovalDate() != null) {
						if (latestBFLDate != null) {
							if ((bflRequired != null) && bflRequired.equals("Y")) {
								// if (bcaLocalInd.equals("Y")) {
								// dt =
								// limitproxy.getBFLDueDates(ob.getRenewalInd(),
								// aForm.getCustomerSegment(),
								// ob.getOriginatingLocation
								// ().getCountryCode(),ob.getApprovalDate());
								dt = limitproxy.getBFLDueDates(ob.getRenewalInd(), aForm.getCustomerSegment(), ob
										.getOriginatingLocation().getCountryCode(), latestBFLDate);
								// } else {
								// dt =
								// limitproxy.getBFLDueDates(ob.getRenewalInd(),
								// aForm.getCustomerSegment(),
								// ob.getOriginatingLocation
								// ().getCountryCode(),ob.getApprovalDate());
								// }
								if (bcaLocalInd.equals("Y")) {
									if (dt[0] != null) {
										aForm.setDueDate(DateUtil.formatDate(locale, dt[0]));
									}
									else {
										aForm.setDueDate(null);
									}
								}
								else {
									if (dt[1] != null) {
										aForm.setDueDate(DateUtil.formatDate(locale, dt[1]));
									}
									else {
										aForm.setDueDate(null);
									}
								}
							}
							else {
								aForm.setDueDate("-");// Due date calculation is
														// not required as BFL
														// requiredInd is No
							}
						}
					}
				}
			}
			catch (Exception e) {
				DefaultLogger.debug(this, "got exception in doExecute" + e);
			}

			if (!((aForm.getEvent().equals("refresh")) || (aForm.getEvent().equals("refreshresubmit")))) {
				if (ob.getApprovalDate() != null) {
					aForm.setApprovalDate(DateUtil.formatDate(locale, ob.getApprovalDate()));
				}
				if (ob.getBCACreateDate() != null) {
					aForm.setBcaCreateDate(DateUtil.formatDate(locale, ob.getBCACreateDate()));
				}
				if (ob.getTATCreateDate() != null) {
					aForm.setTatCreateDate(DateUtil.formatDate(locale, ob.getTATCreateDate()));
				}
				if (ob.getExtendedBFLIssuanceDate() != null) {
					aForm.setExtendedDateBFL(DateUtil.formatDate(locale, ob.getExtendedBFLIssuanceDate()));
				}
				if (!ind) {
					aForm.setBflRequired("N");
				}
				else {
					aForm.setBflRequired("Y");
				}
				if (ob.getBflIndUpdateDate() != null) {
					aForm.setBflIndUpdateDate(DateUtil.formatDate(locale, ob.getBflIndUpdateDate()));
				}
				if (ob.getBCALocalInd()) {
					aForm.setBcaLocalInd("Y");
				}
				else {
					aForm.setBcaLocalInd("N");
				}
				if (ob.getBCAStatus() != null) {
					aForm.setBcaStatus(ob.getBCAStatus());
				}
			}

		}
		else {
			DefaultLogger.debug(this, "obj is null");
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

}
