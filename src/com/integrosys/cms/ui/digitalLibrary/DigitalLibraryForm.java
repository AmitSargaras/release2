package com.integrosys.cms.ui.digitalLibrary;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * This class implements FormBean
 */
public class DigitalLibraryForm extends TrxContextForm implements java.io.Serializable {

	public static final String MAPPER = "com.integrosys.cms.ui.digitalLibrary.DigitalLibraryMapper";

	private String[] climsDocCategory;
	
	private String climsDocCategoryCode;

	private String[] digiLibDocCategory;

	private String targetOffset = "-1";
	
	public String getClimsDocCategoryCode() {
		return climsDocCategoryCode;
	}

	public void setClimsDocCategoryCode(String climsDocCategoryCode) {
		this.climsDocCategoryCode = climsDocCategoryCode;
	}

	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

	public String[] getClimsDocCategory() {
		return climsDocCategory;
	}

	public void setClimsDocCategory(String[] climsDocCategory) {
		this.climsDocCategory = climsDocCategory;
	}

	public String[] getDigiLibDocCategory() {
		return digiLibDocCategory;
	}

	public void setDigiLibDocCategory(String[] digiLibDocCategory) {
		this.digiLibDocCategory = digiLibDocCategory;
	}

	public String getTargetOffset() {
		return targetOffset;
	}

	public void setTargetOffset(String targetOffset) {
		this.targetOffset = targetOffset;
	}
}
