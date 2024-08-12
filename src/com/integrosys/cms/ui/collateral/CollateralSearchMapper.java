package com.integrosys.cms.ui.collateral;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria;

public class CollateralSearchMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public CollateralSearchMapper() {
		DefaultLogger.debug(this, "Inside constructor");
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 * @throws com.integrosys.base.uiinfra.exception.MapperException on errors
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		DefaultLogger.debug(this, "Inside mapForm to ob ");
		CollateralSearchForm aForm = (CollateralSearchForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		CollateralSearchCriteria cSearch = new CollateralSearchCriteria();
		if ((aForm.getSecurityId() != null) && !aForm.getSecurityId().equals("")) {
			DefaultLogger.debug(this, "aForm.getSecurityId()" + aForm.getSecurityId());
			cSearch.setSecurityID(aForm.getSecurityId());
		}

		cSearch.setAaSearchType(aForm.getAaSearchType());
		cSearch.setSecuritySearchType(aForm.getSecuritySearchType());
		
		// FOR BASIC SEARCH type
		cSearch.setSecurityType(aForm.getSecurityType());
		cSearch.setSecuritySubType(aForm.getSecuritySubType());
		cSearch.setSecurityLoc(aForm.getSecurityLoc());

		// FOR ADVANCE SEARCH
		cSearch.setAdvanceSearch(aForm.getAdvanceSearch());
		cSearch.setAdvanceSearchType(aForm.getAdvanceSearchType());

		cSearch.setCustomerName(aForm.getCustomerName());
		cSearch.setLeIDType(aForm.getLeIDType());
		cSearch.setLegalID(aForm.getLegalID());
		cSearch.setIdNO(aForm.getIdNO());
		cSearch.setAaNumber(aForm.getAaNumber());
		cSearch.setBranchCode(aForm.getBranchCode());

		// FOR ADVANCE SEARCH type-------------------->Asset based = Vehicle
		cSearch.setAssetType(aForm.getVehType());
		cSearch.setRegN0(aForm.getRegN0());
		cSearch.setChassissNo(aForm.getChassissNo());

		// FOR ADVANCE SEARCH type-------------------->- Asset based = Gold
		cSearch.setItemType(aForm.getItemType());
		cSearch.setPurchaseReceiptNo(aForm.getPurchaseReceiptNo());
		cSearch.setGoldGrade(aForm.getGoldGrade());

		// FOR ADVANCE SEARCH type-------------------->Plant and Equipment
		cSearch.setAssetType(aForm.getAssetType());
		cSearch.setSerialNo(aForm.getSerialNo());
		cSearch.setYearOfManufacture(aForm.getYearOfManufacture());
		cSearch.setModelNo(aForm.getModelNo());

		// FOR ADVANCE SEARCH type-------------------->Vessel
		cSearch.setFlagRegistered(aForm.getFlagRegistered());
		cSearch.setBuiltYear(aForm.getBuiltYear());

		// FOR ADVANCE SEARCH type-------------------->Aircraft
		cSearch.setAircraftType(aForm.getAircraftType());

		// FOR ADVANCE SEARCH type-------------------->Guarantee
		cSearch.setIssuer(aForm.getIssuer());
		cSearch.setStandbyLCNo(aForm.getStandbyLCNo());
		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getFromExpDate())) {
			cSearch.setFromExpDate(DateUtil.convertDate(locale, aForm.getFromExpDate()));
		}
		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getToExpDate())) {
			cSearch.setToExpDate(DateUtil.convertDate(locale, aForm.getToExpDate()));
		}

		// FOR ADVANCE SEARCH type-------------------->Property
		cSearch.setTitleNo(aForm.getTitleNo());
		cSearch.setTitleTypeCD(aForm.getTitleTypeCD());
		cSearch.setStateCD(aForm.getStateCD());
		cSearch.setDistrictCD(aForm.getDistrictCD());
		cSearch.setMukimCD(aForm.getMukimCD());
		cSearch.setTitleNoPrefix(aForm.getTitleNoPrefix());

		DefaultLogger.debug(this, "Going out of mapForm to ob ");
		return cSearch;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 * @throws com.integrosys.base.uiinfra.exception.MapperException on errors
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		CollateralSearchForm aForm = (CollateralSearchForm) cForm;
		SearchResult sr = (SearchResult) obj;

		if (obj != null) {
			aForm.setCurrentIndex(sr.getCurrentIndex());
			aForm.setNumItems(sr.getNItems());
			DefaultLogger.debug(this, "Before putting vector result");
			aForm.setSearchResult(sr.getResultList());
		}
		else {
			aForm.setSearchResult(null);
			aForm.setCurrentIndex(0);
			aForm.setStartIndex(0);
			aForm.setNumItems(0);
		}

		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

}
