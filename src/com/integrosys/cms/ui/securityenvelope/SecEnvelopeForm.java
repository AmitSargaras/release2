package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.cms.ui.common.TrxContextForm;
import java.io.Serializable;
import java.util.List;

/**
* Describe this class.
* Purpose: To set get and set method for the value needed by Auto Valuation Parameters
* Description: Have set and get method to store the screen value and get the value from other command class
*
* @author $Author$<br>
* @version $Revision$
* @since $Date$
* Tag: $Name$
*/

public class SecEnvelopeForm extends TrxContextForm implements Serializable {
		
     private List secEnvelopeItemList;
	 private String[] deletedItemList;
     private String secLspLmtProfileId;

	/**
     * This method defines a String array which tells what object is to be formed from the form and using what mapper classes to form it.
     * @return input
     */

    public String[][] getMapper() {

        String[][] input = {
                {"SecurityEnvelope", "com.integrosys.cms.ui.securityenvelope.SecEnvelopeMapper"},
                {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"}
            };
        return input;

    }

    public String getSecLspLmtProfileId() {
		return secLspLmtProfileId;
	}

	public void setSecLspLmtProfileId(String secLspLmtProfileId) {
		this.secLspLmtProfileId = secLspLmtProfileId;
	}

	public List getSecEnvelopeItemList() {
		return secEnvelopeItemList;
	}

	public void setSecEnvelopeItemList(List secEnvelopeItemList) {
		this.secEnvelopeItemList = secEnvelopeItemList;
	}

	public String[] getDeletedItemList() {
		return deletedItemList;
	}

	public void setDeletedItemList(String[] deletedItemList) {
		this.deletedItemList = deletedItemList;
	}

}



