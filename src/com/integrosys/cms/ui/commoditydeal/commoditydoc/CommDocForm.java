/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/CommDocForm.java,v 1.3 2004/06/09 10:50:50 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/09 10:50:50 $ Tag: $Name: $
 */

public class CommDocForm extends TrxContextForm implements Serializable {
	private String[] deleteFinancingDoc;

	private String docStatusConfirm = "";

	private String sccIssuedDate = "";

	private String[] deleteTitleDoc;

	public String[] getDeleteFinancingDoc() {
		return deleteFinancingDoc;
	}

	public void setDeleteFinancingDoc(String[] deleteFinancingDoc) {
		this.deleteFinancingDoc = deleteFinancingDoc;
	}

	public String getDocStatusConfirm() {
		return docStatusConfirm;
	}

	public void setDocStatusConfirm(String docStatusConfirm) {
		this.docStatusConfirm = docStatusConfirm;
	}

	public String getSccIssuedDate() {
		return sccIssuedDate;
	}

	public void setSccIssuedDate(String sccIssuedDate) {
		this.sccIssuedDate = sccIssuedDate;
	}

	public String[] getDeleteTitleDoc() {
		return deleteTitleDoc;
	}

	public void setDeleteTitleDoc(String[] deleteTitleDoc) {
		this.deleteTitleDoc = deleteTitleDoc;
	}

	public String[][] getMapper() {
		String[][] input = { { "commDocObj", "com.integrosys.cms.ui.commoditydeal.commoditydoc.CommDocMapper" }, };
		return input;
	}
}
