/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.entitylimit.list;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Entity Limit Form class
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class EntityLimitListForm extends TrxContextForm implements
		Serializable {

	private static final long serialVersionUID = 1L;
	
	private String[] names;
	private String[] cifSources;
	private String[] cifs;
	
	private String[] limitCurrencys;
	private String[] limitAmounts;
	private String[] limits;
	private String[] limitLastReviewDates;
	
	private String[] checkSelects;
	private String editedPos;
	private String targetOffset = "-1";
	
	
	public String getEditedPos() {
		return editedPos;
	}
	public void setEditedPos(String editedPos) {
		this.editedPos = editedPos;
	}
	public String[] getLimits() {
		return limits;
	}
	public void setLimits(String[] limits) {
		this.limits = limits;
	}
	public String[] getCifSources() {
		return cifSources;
	}
	public void setCifSources(String[] cifSources) {
		this.cifSources = cifSources;
	}
	public String[] getNames() {
		return names;
	}
	public void setNames(String[] names) {
		this.names = names;
	}
	public String[] getCifs() {
		return cifs;
	}
	public void setCifs(String[] cifs) {
		this.cifs = cifs;
	}
	public String[] getLimitCurrencys() {
		return limitCurrencys;
	}
	public void setLimitCurrencys(String[] limitCurrencys) {
		this.limitCurrencys = limitCurrencys;
	}
	public String[] getLimitAmounts() {
		return limitAmounts;
	}
	public void setLimitAmounts(String[] limitAmounts) {
		this.limitAmounts = limitAmounts;
	}
	public String[] getLimitLastReviewDates() {
		return limitLastReviewDates;
	}
	public void setLimitLastReviewDates(String[] limitLastReviewDates) {
		this.limitLastReviewDates = limitLastReviewDates;
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
                              {"entityLimitMap",MAPPER}};
    }

    public static final String MAPPER = "com.integrosys.cms.ui.creditriskparam.entitylimit.list.EntityLimitListMapper";

}
