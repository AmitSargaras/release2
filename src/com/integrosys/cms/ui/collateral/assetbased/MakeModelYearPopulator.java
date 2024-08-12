package com.integrosys.cms.ui.collateral.assetbased;

import java.util.Collection;
import java.util.Collections;

import com.integrosys.cms.ui.collateral.CMSLabelValueBean;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.collateral.CollateralUiUtil;
import com.integrosys.cms.ui.common.CommonCodeList;

/**
 * <p>
 * Populator to come out the list of model (by brand) and list of year of
 * manufacture (by model)
 * <p>
 * The collection return is a collection of {@link CMSLabelValueBean}.
 * <p>
 * If the parameter passed in is null, empty list will be returned.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class MakeModelYearPopulator {

	/**
	 * To retrieve collection of {@link CMSLabelValueBean} of the model based on
	 * the brand provided.
	 * 
	 * @param brand brand of the asset based collateral
	 * @return collection of {@link CMSLabelValueBean} else empty list if the
	 *         brand is null
	 */
	public static Collection retrieveModelLabelValueBeanCollections(String brand) {
		if (brand == null) {
			return Collections.EMPTY_LIST;
		}

		CommonCodeList commonCode = CommonCodeList.getInstance(null, null, CategoryCodeConstant.ASSET_MODEL_TYPE,
				false, brand);
		Collection modelLabelValueBeanList = CollateralUiUtil.getLVBeanList(commonCode.getCommonCodeLabels(),
				commonCode.getCommonCodeValues());

		modelLabelValueBeanList = (modelLabelValueBeanList == null) ? Collections.EMPTY_LIST : modelLabelValueBeanList;

		return modelLabelValueBeanList;
	}

	/**
	 * To retrieve collection of {@link CMSLabelValueBean} of Year of
	 * manufacture based on the model provided.
	 * 
	 * @param model model of the asset based collateral
	 * @return collection of {@link CMSLabelValueBean} else empty list if the
	 *         brand is null
	 */
	public static Collection retrieveYearOfManufactureLabelValueBeanCollections(String model) {
		if (model == null) {
			return Collections.EMPTY_LIST;
		}

		CommonCodeList commonCode = CommonCodeList.getInstance(null, null, CategoryCodeConstant.MODEL_YEAR_MAP, false,
				model);

		Collection yearOfManufactureLabelValueBeanList = CollateralUiUtil.getLVBeanList(commonCode
				.getCommonCodeLabels(), commonCode.getCommonCodeValues());

		return yearOfManufactureLabelValueBeanList;
	}
}
