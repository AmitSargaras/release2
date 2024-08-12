/**
 * 
 */
package com.integrosys.cms.ui.creditriskparam.exemptedinst.list;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Exempted Institution Action class
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class ExemptedInstListForm extends TrxContextForm implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private String[] checkSelects;
	private String targetOffset = "-1";
	
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
                              {"exemptedInstMap",MAPPER}};
    }

    public static final String MAPPER = "com.integrosys.cms.ui.creditriskparam.exemptedinst.list.ExemptedInstListMapper";

}
