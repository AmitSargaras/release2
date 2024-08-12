/*
 * Copyright Integro Technologies Pte Ltd
 *
 */
package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.commondata.app.CommonDataSearchContext;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * <p>
 * Common Code Listing for a category code and as per request from the webapp.
 * It consist of a list of common codes labels to be shown on the screen, and a
 * list of common code value which required to be stored for the domain object.
 * Both list by right should be in synced to each other, using the index of
 * label in the list should be able to find the corresponding value in another
 * list.
 * <p>
 * This CommonCodeList assumes data fetched from <tt>CommonDataSingleton</tt>
 * will always get sorted according to the common code label. So, unless there
 * is any special ordering, such as some label have to appear first, the common
 * code list will always be sorted according to the label (the entry name)
 * <p>
 * Webapp using HTML's Options tag can facility the populate of code's value and
 * label using this common code list.
 * <p>
 * <b>Implementation Node: </b>Always consider to use
 * {@link #getInstance(CommonDataSearchContext, boolean, Collection)} to
 * construct the CommonCodeList instance, which can provide more way to retrieve
 * data from <tt>CommonDataSingleton</tt>.
 * @author Tan Hui Ling
 * @author Hii Hui Sieng
 * @author Chong Jun Yong
 * @since 2003/11/28
 * @see com.integrosys.component.commondata.app.CommonDataSearchContext
 * @see com.integrosys.component.commondata.app.CommonDataSingleton
 * @see com.integrosys.cms.ui.common.tag.CommonCodeTag
 */
public final class CommonCodeList {

	private Map commonCodeMap;

	private List commonCodeLabel;

	private List commonCodeValue;

	private Collection commonCodeInitial;

	private String commonCodeSource;

	private final String commonCategoryCode;

	private String referCode;

	private String[] commonCountryCodes;

	private Collection commonExcludeList;

	private boolean descWithCode = false;

	private CommonCodeList(String categoryCode) {
		this.commonCodeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(categoryCode);
		this.commonCodeLabel = new ArrayList();
		this.commonCodeValue = new ArrayList();
		this.commonCategoryCode = categoryCode;

		if (this.commonCategoryCode == null) {
			return;
		}
		prepareCommonCodeValueAndLabelList();
	}

	private CommonCodeList(CommonDataSearchContext context, boolean descWithCode, Collection excludeList) {
		this.commonCodeMap = new LinkedHashMap();
		this.commonCodeLabel = new ArrayList();
		this.commonCodeValue = new ArrayList();
		this.descWithCode = descWithCode;
		this.commonCategoryCode = context.getCategoryCode();

		if (context.getCategoryCode() == null) {
			return;
		}

		this.commonCountryCodes = context.getCountryList();
		this.commonExcludeList = excludeList;

		if (this.commonCountryCodes != null) {
			for (int i = 0; i < this.commonCountryCodes.length; i++) {
				prepareValueMapByCountry(context, i);
			}
		}
		else {
			prepareValueMapByCountry(context, ICMSConstant.INT_INVALID_VALUE);
		}

		prepareCommonCodeValueAndLabelList();
	}

	/**
	 * Construct a CommonCodeList instance providing the category code, category
	 * source, allowed countries, and reference code
	 * 
	 * @param countryCodes allowed country codes
	 * @param source common code's source
	 * @param categoryCode category code of the common codes
	 * @param referCode reference code of the common code to be retrieved
	 * @return CommonCodeList instance ready to be used
	 */
	public static CommonCodeList getInstance(String[] countryCodes, String source, String categoryCode, String referCode) {
		CommonDataSearchContext context = new CommonDataSearchContext(categoryCode, source, null, referCode);
		context.setCountryList(countryCodes);
		return new CommonCodeList(context, false, null);
	}

	/**
	 * Construct a CommonCodeList instance providing the category code, category
	 * source, allowed countries
	 * @param countryCodes allowed country codes
	 * @param source common code's source
	 * @param categoryCode category code of the common codes
	 * @return CommonCodeList instance ready to be used
	 */
	public static CommonCodeList getInstance(String[] countryCodes, String source, String categoryCode) {
		CommonDataSearchContext context = new CommonDataSearchContext(categoryCode, source, null, null);
		context.setCountryList(countryCodes);
		return new CommonCodeList(context, false, null);
	}

	/**
	 * Construct a CommonCodeList instance providing the category code, a
	 * allowed country
	 * @param country allowed country code
	 * @param categoryCode category code of the common codes
	 * @return CommonCodeList instance ready to be used
	 */
	public static CommonCodeList getInstance(String country, String categoryCode) {
		CommonDataSearchContext context = new CommonDataSearchContext(categoryCode);
		context.setCountry(country);
		return new CommonCodeList(context, false, null);
	}

	/**
	 * Construct a CommonCodeList instance with all the common codes belong to
	 * the category code provided.
	 * @param categoryCode category code of the common codes
	 * @return CommonCodeList instance ready to be used
	 */
	public static CommonCodeList getInstance(String categoryCode) {
		CommonDataSearchContext context = new CommonDataSearchContext(categoryCode);
		return new CommonCodeList(context, false, null);
	}

	/**
	 * Construct a CommonCodeList instance providing the category code, a
	 * allowed country, and also a indicator to indicate whether the common code
	 * label should append with the common code value
	 * @param country allowed country code
	 * @param categoryCode category code of the common codes
	 * @param descWithCode to indicate whether the result of the common code
	 *        label will append with the common code value
	 * @return CommonCodeList instance ready to be used
	 */
	public static CommonCodeList getInstance(String country, String categoryCode, boolean descWithCode) {
		CommonDataSearchContext context = new CommonDataSearchContext(categoryCode);
		context.setCountry(country);
		return new CommonCodeList(context, descWithCode, null);
	}

	/**
	 * Construct a CommonCodeList instance providing the category code, source
	 * of the common codes, allowed countries, and also a indicator to indicate
	 * whether the common code label should append with the common code value
	 * @param countryCodes allowed countries codes
	 * @param source source of the common codes
	 * @param categoryCode category code of the common codes
	 * @param descWithCode to indicate whether the result of the common code
	 *        label will append with the common code value
	 * @return CommonCodeList instance ready to be used
	 */
	public static CommonCodeList getInstance(String countryCodes[], String source, String categoryCode,
			boolean descWithCode) {
		CommonDataSearchContext context = new CommonDataSearchContext(categoryCode);
		context.setEntrySource(source);
		context.setCountryList(countryCodes);
		return new CommonCodeList(context, descWithCode, null);
	}

	/**
	 * Construct a CommonCodeList instance providing the category code, source
	 * of the common codes, a allowed country, reference code and a indicator to
	 * indicator whether the common code label should append with the common
	 * code value
	 * @param country allowed country code
	 * @param source source of the common codes
	 * @param categoryCode category code of the common codes
	 * @param descWithCode to indicate whether the result of the common code
	 *        label will append with the common code value
	 * @param refEntryCode reference code of the common code to be retrieved
	 * @return CommonCodeList instance ready to be used
	 */
	public static CommonCodeList getInstance(String country, String source, String categoryCode, boolean descWithCode,
			String refEntryCode) {
		CommonDataSearchContext context = new CommonDataSearchContext(categoryCode, source, country, refEntryCode);
		return new CommonCodeList(context, descWithCode, null);
	}

	/**
	 * Construct a CommonCodeList instance providing the category code, source
	 * of the common codes, allowed countries, reference code and a indicator to
	 * indicator whether the common code label should append with the common
	 * code value
	 * @param countryCodes allowed countries codes
	 * @param source source of the common codes
	 * @param categoryCode category code of the common codes
	 * @param descWithCode to indicate whether the result of the common code
	 *        label will append with the common code value
	 * @param refEntryCode reference code of the common code to be retrieved
	 * @return CommonCodeList instance ready to be used
	 */
	public static CommonCodeList getInstance(String[] countryCodes, String source, String categoryCode,
			String refEntryCode, boolean descWithCode) {
		CommonDataSearchContext context = new CommonDataSearchContext(categoryCode, source, null, refEntryCode);
		context.setCountryList(countryCodes);
		return new CommonCodeList(context, descWithCode, null);
	}

	/**
	 * Construct a CommonCodeList instance providing the category code, source
	 * of the common codes, a allowed country, reference code and a indicator to
	 * indicator whether the common code label should append with the common
	 * code value, and a list of common code entry code to be excluded from this
	 * common code list
	 * @param country allowed country code
	 * @param source source of the common codes
	 * @param categoryCode category code of the common codes
	 * @param descWithCode to indicate whether the result of the common code
	 *        label will append with the common code value
	 * @param refEntryCode reference code of the common code to be retrieved
	 * @param excludeList a list of common code entry code to be excluded
	 * @return CommonCodeList instance ready to be used
	 */
	public static CommonCodeList getInstance(String country, String source, String categoryCode, boolean descWithCode,
			String refEntryCode, Collection excludeList) {
		CommonDataSearchContext context = new CommonDataSearchContext(categoryCode, source, country, refEntryCode);
		return new CommonCodeList(context, descWithCode, excludeList);
	}

	/**
	 * <p>
	 * Construct a CommonCodeList instance providing a common data search
	 * context, a indicator to indicate whether the common code label should
	 * append with the common code value, and a list of common code entry code
	 * to be excluded from this common code list.
	 * <p>
	 * This is the prefered way to construct a instance of common code.
	 * @param context a context contains information to retrieve the common
	 *        codes required
	 * @param descWithCode to indicate whether the result of the common code
	 *        label will append with the common code value
	 * @param excludeList a list of common code entry code to be excluded
	 * @return CommonCodeList instance ready to be used
	 */
	public static CommonCodeList getInstance(CommonDataSearchContext context, boolean descWithCode,
			Collection excludeList) {
		return new CommonCodeList(context, descWithCode, excludeList);
	}

	public Collection getCommonCodeInitials() {
		if (commonCountryCodes != null) {
			for (int i = 0; i < commonCountryCodes.length; i++) {
				commonCodeInitial = CommonDataSingleton.getCodeCategoryInitials(commonCategoryCode, commonCodeSource,
						commonCountryCodes[i], referCode);
			}
		}
		else {
			commonCodeInitial = CommonDataSingleton.getCodeCategoryInitials(commonCategoryCode, commonCodeSource, null,
					referCode);
		}

		Collections.sort((List) commonCodeInitial);

		return commonCodeInitial;
	}

	public Collection getCommonCodeValues() {
		return this.commonCodeValue;
	}

	public Collection getCommonCodeLabels() {
		return this.commonCodeLabel;
	}

	public String getCommonCodeLabel(String entryCode) {
		if (entryCode == null) {
			return "";
		}

		if (!this.commonCodeMap.isEmpty()) {
			String entryName = (String) this.commonCodeMap.get(entryCode);
			if (entryName != null) {
				return (this.descWithCode) ? entryName + " (" + entryCode + ")" : entryName;
			}
		}
		return "";
	}

	public Map getLabelValueMap() {
		return this.commonCodeMap;
	}

	public List getOptionList() {
		List optionList = new ArrayList();
		for (int i = 0; i < commonCodeLabel.size(); i++) {
			LabelValueBean lvBean = new LabelValueBean((String) ((List) commonCodeLabel).get(i),
					(String) ((List) commonCodeValue).get(i));
			optionList.add(lvBean);
		}
		return optionList;
	}

	public static void refresh(String categoryCode) {
		if (categoryCode == null) {
			return;
		}
		CommonDataSingleton.refresh(categoryCode);
	}

	public static void refresh() {
		CommonDataSingleton.refresh();
	}

	private void prepareCommonCodeValueAndLabelList() {
		for (Iterator itr = this.commonCodeMap.entrySet().iterator(); itr.hasNext();) {
			Map.Entry commonCodeEntry = (Map.Entry) itr.next();
			String entryCode = (String) commonCodeEntry.getKey();
			String entryName = (String) commonCodeEntry.getValue();
			if ((this.commonExcludeList != null) && this.commonExcludeList.contains(entryCode)) {
				continue;
			}
			String desc = (this.descWithCode) ? entryName + " (" + entryCode + ")" : entryName;
			this.commonCodeValue.add(entryCode);
			this.commonCodeLabel.add(desc);
		}
	}

	private void prepareValueMapByCountry(CommonDataSearchContext context, int index) {
		if (context == null) {
			return;
		}

		if ((context.getCountryList() != null) && (context.getCountryList().length > index)) {
			context.setCountry(context.getCountryList()[index]);
		}

		Map tempCommonCodeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(context);
		this.commonCodeMap.putAll(tempCommonCodeMap);
	}
}
