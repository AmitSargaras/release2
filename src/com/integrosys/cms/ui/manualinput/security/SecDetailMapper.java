/*
 * Created on Apr 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.manualinput.limit.EventConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecDetailMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "secTrxObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "lmtTrxObj", ILimitTrxValue.class.getName() , SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "itemType", "java.lang.String", REQUEST_SCOPE }, });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.IMapper#mapOBToForm(com.integrosys
	 * .base.uiinfra.common.CommonForm, java.lang.Object, java.util.HashMap)
	 */
	public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs) throws MapperException {
		// TODO Auto-generated method stub
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {
			String event = (String) (inputs.get("event"));
			MISecurityUIHelper helper = new MISecurityUIHelper();
			ICollateral colObj = (ICollateral) obj;
			SecDetailForm secDetailForm = (SecDetailForm) commonForm;
			if (colObj.getCollateralID() != ICMSConstant.LONG_INVALID_VALUE) {
				secDetailForm.setSecurityId(String.valueOf(colObj.getCollateralID()));
			}
			secDetailForm.setSciColId(colObj.getSCISecurityID());
			secDetailForm.setSecBookingCountry(colObj.getCollateralLocation());
			secDetailForm.setCollateralCode(colObj.getCollateralCode());
			secDetailForm.setSecBookingCountryDesc(helper.getOrigBookingCtryDesc(colObj.getCollateralLocation()));
			if(colObj.getSecurityOrganization() != null) {
				secDetailForm.setSecBookingOrg(colObj.getSecurityOrganization());
			}
			secDetailForm.setSecBookingOrgDesc(helper.getOrigBookingLocDesc(colObj.getCollateralLocation(), colObj
					.getSecurityOrganization()));
			if (colObj.getCollateralSubType() != null) {
				ICollateralSubType subtp = colObj.getCollateralSubType();
				secDetailForm.setSecSubtype(subtp.getSubTypeCode());
				secDetailForm.setSecSubtypeDesc(subtp.getSubTypeName());
				secDetailForm.setSecType(subtp.getTypeCode());
				secDetailForm.setSecTypeDesc(subtp.getTypeName());
			}
			secDetailForm.setSecCurrency(colObj.getCurrencyCode());
			/*
			 * if (colObj.getFSV() != null) {
			 * secDetailForm.setFsv(CurrencyManager.convertToString(Locale.US,
			 * colObj.getFSV())); }
			 */
			secDetailForm.setSecReferenceNote(colObj.getSCIReferenceNote());
			renderPledgorSummary(colObj, secDetailForm, helper);

			ICollateralTrxValue secTrxObj = (ICollateralTrxValue) (inputs.get("secTrxObj"));
			setChangedInd(event, secTrxObj.getCollateral(), secTrxObj.getStagingCollateral(), secDetailForm);
			return secDetailForm;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException();
		}
	}

	public static void renderPledgorSummary(ICollateral col, SecDetailForm secDetailForm, MISecurityUIHelper helper) {
		ICollateralPledgor[] pledgors = col.getPledgors();
		List summaryList = new ArrayList();

		if (pledgors != null) {
			String[] secPledgorRelnshipList = new String[pledgors.length];
			for (int i = 0; i < pledgors.length; i++) {
				ICollateralPledgor nextLink = pledgors[i];
				PledgorSummaryItem nextItem = new PledgorSummaryItem();
				nextItem.setPledgorSecLinkId(String.valueOf(nextLink.getMapID()));
				nextItem.setCustomerName(nextLink.getPledgorName());
				nextItem.setLeIdType(helper.getLeSystemDesc(nextLink.getLegalIDSource()));
				nextItem.setLeId(nextLink.getLegalID());

				nextItem.setIdType(helper.getIDTypeDesc(nextLink.getPlgIdType()));
				nextItem.setIdNo(nextLink.getPlgIdNumText());
				// nextItem.setSecPledgorRelnship(helper.
				// getPledgorRelationshipDesc(nextLink.getPledgorRelnship()));

				nextItem.setHeaderClass("index");
				if (i % 2 == 0) {
					nextItem.setRowClass("odd");
				}
				else {
					nextItem.setRowClass("even");
				}
				summaryList.add(nextItem);
				secPledgorRelnshipList[i] = nextLink.getPledgorRelnship();

			}
			secDetailForm.setSecPledgorRelnshipList(secPledgorRelnshipList);

		}
		else {

		}
		secDetailForm.setPledgorList(summaryList);
	}

	private void setChangedInd(String event, ICollateral origCol, ICollateral stagingCol, SecDetailForm secDetailForm) {
		try {
			if (!EventConstant.EVENT_PROCESS.equals(event) && !EventConstant.EVENT_PROCESS_RETURN.equals(event)) {
				return;
			}
			if (!CompareOBUtil.compOB(origCol, stagingCol, "collateralLocation")) {
				secDetailForm.setSecBookingCountryClass("fieldnamediff");
			}
			if (!CompareOBUtil.compOB(origCol, stagingCol, "securityOrganization")) {
				secDetailForm.setSecBookingOrgClass("fieldnamediff");
			}
			ICollateralSubType subType = null;
			if (origCol != null) {
				subType = origCol.getCollateralSubType();
			}
			if (!CompareOBUtil.compOB(subType, stagingCol.getCollateralSubType(), "typeName")) {
				secDetailForm.setSecTypeClass("fieldnamediff");
			}
			if (!CompareOBUtil.compOB(subType, stagingCol.getCollateralSubType(), "subTypeName")) {
				secDetailForm.setSecSubtypeClass("fieldnamediff");
			}
			if (!CompareOBUtil.compOB(origCol, stagingCol, "currencyCode")) {
				secDetailForm.setSecCurrencyClass("fieldnamediff");
			}
			/*
			 * if (!CompareOBUtil.compOB(origCol.getFSV(), stagingCol.getFSV(),
			 * "amount")) { secDetailForm.setFsvClass("fieldnamediff"); }
			 */
			if (!CompareOBUtil.compOB(origCol, stagingCol, "sCIReferenceNote")) {
				secDetailForm.setSecReferenceNoteClass("fieldnamediff");
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void deletePledgorLink(ICollateral col, SecDetailForm secDetailForm) {
		MISecurityUIHelper helper = new MISecurityUIHelper();
		ICollateralPledgor[] pledgors = col.getPledgors();
		String[] deletedInd = secDetailForm.getDeletedPledgor();

		if ((pledgors != null) && (deletedInd != null) && (deletedInd.length > 0)) {
			List tempList = new ArrayList();
			for (int i = 0; i < pledgors.length; i++) {
				tempList.add(pledgors[i]);
			}
			List res = helper.deleteItem(tempList, deletedInd);
			col.setPledgors((ICollateralPledgor[]) (res.toArray(new ICollateralPledgor[0])));

			secDetailForm.setDeletedPledgor(new String[0]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.IMapper#mapFormToOB(com.integrosys
	 * .base.uiinfra.common.CommonForm, java.util.HashMap)
	 */
	public Object mapFormToOB(CommonForm commonForm, HashMap inputs) throws MapperException {
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		String event = (String) (inputs.get("event"));
		String itemType = (String) (inputs.get("itemType"));
		try {
			ICollateral col = this.getCollateralObj(inputs);
			SecDetailForm secDetailForm = (SecDetailForm) commonForm;
			// col.setSCISecurityID(secDetailForm.getSciColId());
			col.setCollateralLocation(secDetailForm.getSecBookingCountry());
			col.setSecurityOrganization(secDetailForm.getSecBookingOrg());
			ICollateralSubType subtp = col.getCollateralSubType();
			if (subtp == null) {
				subtp = new OBCollateralSubType();
				col.setCollateralSubType(subtp);
			}
			subtp.setTypeCode(secDetailForm.getSecType());
			subtp.setTypeName(secDetailForm.getSecTypeDesc());
			subtp.setSubTypeCode(secDetailForm.getSecSubtype());
			subtp.setSubTypeName(secDetailForm.getSecSubtypeDesc());
			col.setSCITypeValue(secDetailForm.getSecType());
			col.setSCISubTypeValue(secDetailForm.getSecSubtype());
			col.setMonitorFrequency(secDetailForm.getMonitorFrequency());
			col.setMonitorProcess(secDetailForm.getMonitorProcess());
			col.setCollateralCode(secDetailForm.getCollateralCode());
			/*
			 * if (!AbstractCommonMapper.isEmptyOrNull(secDetailForm.getFsv())
			 * &&
			 * !AbstractCommonMapper.isEmptyOrNull(secDetailForm.getSecCurrency
			 * ())) { Amount amt = CurrencyManager.convertToAmount(locale,
			 * secDetailForm.getSecCurrency(), secDetailForm.getFsv());
			 * col.setFSV(amt);
			 * col.setFSVCcyCode(secDetailForm.getSecCurrency());
			 * col.setCMV(amt);
			 * col.setCMVCcyCode(secDetailForm.getSecCurrency()); }
			 */
			col.setCurrencyCode(secDetailForm.getSecCurrency());
			col.setSCICurrencyCode(secDetailForm.getSecCurrency());
			col.setSCIReferenceNote(secDetailForm.getSecReferenceNote());
			col.setStatus(ICMSConstant.STATE_ACTIVE);			
			col.setCollateralCode(secDetailForm.getCollateralCode());
			col.setSecPriority(secDetailForm.getSecPriority());
			if(EventConstant.EVENT_CREATE.equals(event) && col.getCollateralSubType() != null && 
					ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE.equals(col.getCollateralSubType().getSubTypeCode())) {
				ILimitTrxValue lmtTrxObj = (ILimitTrxValue) (inputs.get("lmtTrxObj"));
				if(lmtTrxObj != null && lmtTrxObj.getStagingLimit() !=null)
					col.setBankingArrangement(lmtTrxObj.getStagingLimit().getBankingArrangement());
			}
				
			ICollateralPledgor[] pledgors = col.getPledgors();
			String[] relnshipList = secDetailForm.getSecPledgorRelnshipList();
			if ((pledgors != null) && (relnshipList != null) && (relnshipList.length > 0)) {
				for (int i = 0; i < pledgors.length; i++) {
					for (int j = 0; j < relnshipList.length; j++) {
						if (i == j) {
							pledgors[i].setPledgorRelnship(relnshipList[j]);
							pledgors[i].setPledgorRelnshipCode(ICMSConstant.CATEGORY_CODE_PLEDGOR_RELNSHIP);
							break;
						}
					}
				}
				col.setPledgors(pledgors);
			}
			if (EventConstant.EVENT_DELETE_ITEM.equals(event)) {
				if ("pledgor".equals(itemType)) {
					deletePledgorLink(col, secDetailForm);
				}
			}

			return col;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException();
		}
	}

	private ICollateral getCollateralObj(HashMap inputs) {
		ICollateralTrxValue trxValue = (ICollateralTrxValue) (inputs.get("secTrxObj"));
		String event = (String) (inputs.get("event"));
		return trxValue.getStagingCollateral();
	}

}
