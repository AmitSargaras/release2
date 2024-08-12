/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.list;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author user
 *
 */
public class CustRelationshipListForm extends TrxContextForm implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private String[] names;
	private String[] dobs;
	private String[] ages;
	private String[] entityRels;
	private String[] countries;
	private String[] custRemarks; 
	private String[] checkSelects;
	private String targetOffset = "-1";
	public String[] getNames() {
		return names;
	}
	public void setNames(String[] names) {
		this.names = names;
	}
	public String[] getDobs() {
		return dobs;
	}
	public void setDobs(String[] dobs) {
		this.dobs = dobs;
	}
	public String[] getAges() {
		return ages;
	}
	public void setAges(String[] ages) {
		this.ages = ages;
	}
	public String[] getEntityRels() {
		return entityRels;
	}
	public void setEntityRels(String[] entityRels) {
		this.entityRels = entityRels;
	}
	public String[] getCountries() {
		return countries;
	}
	public void setCountries(String[] countries) {
		this.countries = countries;
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
	public String[] getCustRemarks() {
		return custRemarks;
	}
	public void setCustRemarks(String[] custRemarks) {
		this.custRemarks = custRemarks;
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
                              {"custRelationshipMap",MAPPER}};
    }

    public static final String MAPPER = "com.integrosys.cms.ui.custrelationship.list.CustRelationshipListMapper";

}
