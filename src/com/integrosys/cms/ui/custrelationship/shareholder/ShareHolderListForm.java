package com.integrosys.cms.ui.custrelationship.shareholder;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class ShareHolderListForm extends TrxContextForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String[] percentages;
	private String[] checkSelects;
	private String targetOffset = "-1";
	
	public String[] getPercentages() {
		return percentages;
	}
	public void setPercentages(String[] percentages) {
		this.percentages = percentages;
	}
	public String[] getCheckSelects() {
		return checkSelects;
	}
	public void setCheckSelects(String[] checkSelects) {
		this.checkSelects = checkSelects;
	}
	public String getTargetOffset() {
		return targetOffset;
	}
	public void setTargetOffset(String targetOffset) {
		this.targetOffset = targetOffset;
	}
	
    /**
     * This method defines a String array which tells what object is to be formed from the form and using what mapper classes to form it.
     * it has a syntax [(key, MapperClassname)]
     *
     * @return 2-dimensional String Array
     */
    public String[][] getMapper() {
        return new String[][]{{MAPPER, MAPPER},
                              {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"},
                              {"shareHolderMap",MAPPER}};
    }

    public static final String MAPPER = "com.integrosys.cms.ui.custrelationship.shareholder.ShareHolderListMapper";
	
}
