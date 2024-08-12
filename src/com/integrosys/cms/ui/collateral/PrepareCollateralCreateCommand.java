/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/PrepareCollateralCommand.java,v 1.20 2007/02/22 17:45:00 Jerlin Exp $
 */

package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.math.RandomUtils;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDetailFactory;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.bus.OBCollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.OBCollateralPledgor;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.OBCollateralType;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: Jerlin $<br>
 * @version $Revision: 1.20 $
 * @since $Date: 2007/02/22 17:45:00 $ Tag: $Name: $
 */
public class PrepareCollateralCreateCommand extends AbstractCommand {

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", SERVICE_SCOPE },
				// { "event", "java.lang.String", SERVICE_SCOPE },
				// { "event", "java.lang.String", REQUEST_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE },
				{ "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE },
				{ "from_page", "java.lang.String", SERVICE_SCOPE }, });
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "subtypeCode", "java.lang.String", SERVICE_SCOPE },
				{ "frameRequested", "java.lang.String", REQUEST_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		String subtype = (String) map.get("subtype");

		ICollateralTrxValue colTrxValue = new OBCollateralTrxValue();
		colTrxValue.setStatus(ICMSConstant.STATE_DRAFT);

		ICollateral col = null;

		String typeCode = subtype.substring(0, 2);

		OBCollateralSubType colSubType = new OBCollateralSubType(subtype);
		try {
			col = CollateralDetailFactory.getOB(colSubType);
		}
		catch (CollateralException e) {
			throw new CommandProcessingException("failed to get collateral subtype ob class for subtype [" + subtype
					+ "]", e);
		}

		col.setCreateDate(new Date());

		colSubType.setSubTypeName(CommonCodeList.getInstance(CategoryCodeConstant.COMMON_CODE_SECURITY_SUB_TYPE)
				.getCommonCodeLabel(subtype));
		colSubType.setTypeCode(typeCode);
		colSubType.setTypeName(CommonCodeList.getInstance(CategoryCodeConstant.COMMON_CODE_SECURITY_TYPE)
				.getCommonCodeLabel(typeCode));
		col.setCollateralSubType(colSubType);

		OBCollateralType colType = new OBCollateralType();
		colType.setTypeCode(typeCode);
		colType.setTypeName(CommonCodeList.getInstance(CategoryCodeConstant.COMMON_CODE_SECURITY_TYPE)
				.getCommonCodeLabel(typeCode));
		col.setCollateralType(colType);

		col.setSCITypeValue(typeCode);
		col.setSCISubTypeValue(subtype);
		col.setCollateralStatus(CollateralConstant.HOST_COL_STATUS_ACTIVE);
		col.setSCICurrencyCode(IGlobalConstant.DEFAULT_CURRENCY);
		col.setCurrencyCode(IGlobalConstant.DEFAULT_CURRENCY);
		col.setSourceId(ICMSConstant.SOURCE_SYSTEM_CMS);
		col.setStatus(ICMSConstant.STATE_ACTIVE);

		ILimitProfile lmtProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		if (!PropertiesConstantHelper.isValidSTPFacilityLoadingApplicationType(lmtProfile.getApplicationType())) {
			// link all the facilities of the limit profile to the
			// collateral
			ILimit[] limitList = lmtProfile.getLimits();
			if (limitList != null) {
				ArrayList limitSecMapList = new ArrayList();
				for (int i = 0; i < limitList.length; i++) {
					ICollateralLimitMap limitSecMap = new OBCollateralLimitMap();
					limitSecMap.setChargeID(RandomUtils.nextLong());
					try {
						String chargeID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COL_LIMIT_MAP, true);
						limitSecMap.setSCISysGenID(Long.parseLong(chargeID));
					}
					catch (Exception e) {
						throw new CommandProcessingException("failed to get sequence of ["
								+ ICMSConstant.SEQUENCE_COL_LIMIT_MAP + "] using sequence manager ["
								+ SequenceManager.class + "]", e);
					}
					limitSecMap.setLimitID(limitList[i].getLimitID());
					limitSecMap.setSCILimitID(limitList[i].getLimitRef());
					limitSecMap.setCmsLimitProfileId(limitList[i].getLimitProfileID());
					limitSecMap.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER);
				//	limitSecMap.setCpsSecurityId("1234");
					limitSecMap.setSCIStatus(ICMSConstant.HOST_STATUS_INSERT);
					limitSecMap.setChangeIndicator(new Character(ICMSConstant.YES.charAt(0)));
					limitSecMap.setSourceID(ICMSConstant.SOURCE_SYSTEM_CMS);

					limitSecMapList.add(limitSecMap);
				}
				col.setCollateralLimits((ICollateralLimitMap[]) limitSecMapList.toArray(new ICollateralLimitMap[0]));
			}
		}
		if (ICMSConstant.COLTYPE_NOCOLLATERAL.equals(subtype)) {
			ICMSCustomer customerObj = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			ICollateralPledgor[] pledgors = new ICollateralPledgor[1];
			ICollateralPledgor p = new OBCollateralPledgor();
			p.setLegalID(customerObj.getCMSLegalEntity().getLEReference());
			p.setDomicileCountry(customerObj.getDomicileCountry());
			p.setLegalType(customerObj.getLegalEntity().getLegalConstitution());
			p.setLegalIDSource(customerObj.getCMSLegalEntity().getLegalIDSource());
			p.setPledgorName(customerObj.getCustomerName());
			p.setPledgorRelnshipCode("RELATIONSHIP");
			pledgors[0] = p;

			col.setPledgors(pledgors);
		}

		colTrxValue.setStagingCollateral(col);
		colTrxValue.setLimitProfileID(lmtProfile.getLimitProfileID());

		result.put("from_page", map.get("event"));
		result.put("serviceColObj", colTrxValue);
		result.put("subtype", subtype);
		result.put("form.collateralObject", colTrxValue.getStagingCollateral());
		// result.put("event", "create");

		String frame = (String) map.get("frameRequested");
		if (frame == null) {
			frame = (String) map.get("frame");
		}

		result.put("frame", frame);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
