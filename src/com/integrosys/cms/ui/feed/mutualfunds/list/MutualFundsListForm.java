package com.integrosys.cms.ui.feed.mutualfunds.list;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 * This class implements FormBean
 */
public class MutualFundsListForm extends TrxContextForm implements java.io.Serializable {

	public static final String MAPPER = "com.integrosys.cms.ui.feed.mutualfunds.list.MutualFundsListMapper";

	private String[] updatedCurrentNAV;

	private String[] chkDeletes;

	private String[] schemeNames;
	
	private String[] schemeCodes;
	
	private String[] schemeTypeArr;
	
	private String[] schemeTypeList;
	
	private String[] startDateArr;
	
	private String[] expiryDateArr;
	
	private String[] lastUpdatedDate;
	
	private String targetOffset = "-1";

	/**
	 * @return the schemeTypeList
	 */
	public String[] getSchemeTypeList() {
		return schemeTypeList;
	}

	/**
	 * @param schemeTypeList the schemeTypeList to set
	 */
	public void setSchemeTypeList(String[] schemeTypeList) {
		this.schemeTypeList = schemeTypeList;
	}

	/**
	 * @return the schemeCodes
	 */
	public String[] getSchemeCodes() {
		return schemeCodes;
	}

	/**
	 * @param schemeCodes the schemeCodes to set
	 */
	public void setSchemeCodes(String[] schemeCodes) {
		this.schemeCodes = schemeCodes;
	}

	/**
	 * @return the schemeTypeArr
	 */
	public String[] getSchemeTypeArr() {
		return schemeTypeArr;
	}

	/**
	 * @param schemeTypeArr the schemeTypeArr to set
	 */
	public void setSchemeTypeArr(String[] schemeTypeArr) {
		this.schemeTypeArr = schemeTypeArr;
	}

	/**
	 * @return the startDateArr
	 */
	public String[] getStartDateArr() {
		return startDateArr;
	}

	/**
	 * @param startDateArr the startDateArr to set
	 */
	public void setStartDateArr(String[] startDateArr) {
		this.startDateArr = startDateArr;
	}

	/**
	 * @return the expiryDateArr
	 */
	public String[] getExpiryDateArr() {
		return expiryDateArr;
	}

	/**
	 * @param expiryDateArr the expiryDateArr to set
	 */
	public void setExpiryDateArr(String[] expiryDateArr) {
		this.expiryDateArr = expiryDateArr;
	}
	
	public String[] getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String[] lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String[] getSchemeNames() {
		return schemeNames;
	}

	public String[] getChkDeletes() {
		return chkDeletes;
	}
	
	public String[] getUpdatedCurrentNAV() {
		return updatedCurrentNAV;
	}

	public void setUpdatedCurrentNAV(String[] updatedCurrentNAV) {
		this.updatedCurrentNAV = updatedCurrentNAV;
	}

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax [(key, MapperClassname)]
	 * 
	 * @return 2-dimensional String Array
	 */
	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

	public String getTargetOffset() {
		return targetOffset;
	}

	public void setSchemeNames(String[] schemeNames) {
		this.schemeNames = schemeNames;
	}

	public void setChkDeletes(String[] chkDeletes) {
		this.chkDeletes = chkDeletes;
	}

	public void setTargetOffset(String targetOffset) {
		this.targetOffset = targetOffset;
	}

}
