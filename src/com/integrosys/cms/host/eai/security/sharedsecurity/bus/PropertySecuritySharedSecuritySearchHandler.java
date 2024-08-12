package com.integrosys.cms.host.eai.security.sharedsecurity.bus;

import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria;
import com.integrosys.cms.app.collateral.bus.ICollateralSearchResult;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.security.bus.property.PropertySecurity;

/**
 * Share Security Search Handler for All property sub types
 * 
 * @author Chong Jun Yong
 * 
 */
public class PropertySecuritySharedSecuritySearchHandler extends AbstractSharedSecuritySearchHandler {

	protected void doPopulateDetailItem(SharedSecurityResultItem result, ICollateralSearchResult security) {
		PropertySecurity property = (PropertySecurity) getSecurityDao().retrieve(new Long(security.getCollateralID()),
				PropertySecurity.class);

		PropertySecurity item = new PropertySecurity();

		StandardCode state = property.getState();
		if (state != null) {
			state.setStandardCodeNumber("STATE");
		}
		item.setState(state);

		StandardCode district = property.getDistrict();
		if (district != null) {
			district.setStandardCodeNumber("DISTRICT");
		}
		item.setDistrict(district);

		StandardCode mukim = property.getDistrict();
		if (mukim != null) {
			mukim.setStandardCodeNumber("MUKIM");
		}
		item.setMukim(mukim);

		StandardCode titleType = property.getTitleType();
		if (titleType != null) {
			titleType.setStandardCodeNumber("TITLE_TYPE");
		}
		item.setTitleType(titleType);

		StandardCode titleNumberPrefix = property.getTitleNumberPrefix();
		if (titleNumberPrefix != null) {
			titleNumberPrefix.setStandardCodeNumber("TITLE_TYPE");
		}
		item.setTitleNumberPrefix(titleNumberPrefix);

		item.setTitleNumber(property.getTitleNumber());

		result.setProperty(item);

	}

	protected void doPrepareCollateralSearchCriteria(CollateralSearchCriteria criteria,
			SharedSecuritySearch searchCriteria) {
		PropertySecurity property = searchCriteria.getProperty();

		if (property.getState() != null && StringUtils.isNotBlank(property.getState().getStandardCodeValue())) {
			criteria.setStateCD(property.getState().getStandardCodeValue());
		}

		if (property.getDistrict() != null && StringUtils.isNotBlank(property.getDistrict().getStandardCodeValue())) {
			criteria.setDistrictCD(property.getDistrict().getStandardCodeValue());
		}

		if (property.getMukim() != null && StringUtils.isNotBlank(property.getMukim().getStandardCodeValue())) {
			criteria.setMukimCD(property.getMukim().getStandardCodeValue());
		}

		if (property.getTitleType() != null && StringUtils.isNotBlank(property.getTitleType().getStandardCodeValue())) {
			criteria.setTitleTypeCD(property.getTitleType().getStandardCodeValue());
		}

		if (property.getTitleNumberPrefix() != null
				&& StringUtils.isNotBlank(property.getTitleNumberPrefix().getStandardCodeValue())) {
			criteria.setTitleNoPrefix(property.getTitleNumberPrefix().getStandardCodeValue());
		}

		if (StringUtils.isNotBlank(property.getTitleNumber())) {
			criteria.setTitleNo(property.getTitleNumber());
		}
	}
}
