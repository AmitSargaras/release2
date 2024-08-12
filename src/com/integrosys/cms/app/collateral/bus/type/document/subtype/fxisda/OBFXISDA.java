/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/document/subtype/fxisda/OBFXISDA.java,v 1.5 2003/07/21 05:57:43 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.document.subtype.fxisda;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.document.OBDocumentCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents an FX/Derivative Document of type ISDA/FEMA.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/07/21 05:57:43 $ Tag: $Name: $
 */
public class OBFXISDA extends OBDocumentCollateral implements IFXISDA {
	private Date iSDADate;

	private String iSDAProductDesc;

	private Date iFEMADate;

	private String iFEMAProductDesc;

	private Date iCOMDate;

	private String iCOMProductDesc;

	/**
	 * Default Constructor.
	 */
	public OBFXISDA() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_DOC_FX_ISDA));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IFXISDA
	 */
	public OBFXISDA(IFXISDA obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get date of ISDA agreement.
	 * 
	 * @return Date
	 */
	public Date getISDADate() {
		return iSDADate;
	}

	/**
	 * Set date of ISDA agreement.
	 * 
	 * @param iSDADate of type Date
	 */
	public void setISDADate(Date iSDADate) {
		this.iSDADate = iSDADate;
	}

	/**
	 * Get description of products in ISDA.
	 * 
	 * @return String
	 */
	public String getISDAProductDesc() {
		return iSDAProductDesc;
	}

	/**
	 * Set description of products in ISDA.
	 * 
	 * @param iSDAProductDesc of type String
	 */
	public void setISDAProductDesc(String iSDAProductDesc) {
		this.iSDAProductDesc = iSDAProductDesc;
	}

	/**
	 * Get date of IFEMA agreement.
	 * 
	 * @return Date
	 */
	public Date getIFEMADate() {
		return iFEMADate;
	}

	/**
	 * Set date of IFEMA agreement.
	 * 
	 * @param iFEMADate of type Date
	 */
	public void setIFEMADate(Date iFEMADate) {
		this.iFEMADate = iFEMADate;
	}

	/**
	 * Get description of products in IFEMA.
	 * 
	 * @return String
	 */
	public String getIFEMAProductDesc() {
		return iFEMAProductDesc;
	}

	/**
	 * Set description of products in IFEMA.
	 * 
	 * @param iFEMAProductDesc of type String
	 */
	public void setIFEMAProductDesc(String iFEMAProductDesc) {
		this.iFEMAProductDesc = iFEMAProductDesc;
	}

	/**
	 * Get date of ICOM document.
	 * 
	 * @return Date
	 */
	public Date getICOMDate() {
		return iCOMDate;
	}

	/**
	 * Set date of ICOM document.
	 * 
	 * @param iCOMDate of type Date
	 */
	public void setICOMDate(Date iCOMDate) {
		this.iCOMDate = iCOMDate;
	}

	/**
	 * Get description of products in ICOM terms.
	 * 
	 * @return String
	 */
	public String getICOMProductDesc() {
		return iCOMProductDesc;
	}

	/**
	 * Set description of products in ICOM terms.
	 * 
	 * @param iCOMProductDesc of type String
	 */
	public void setICOMProductDesc(String iCOMProductDesc) {
		this.iCOMProductDesc = iCOMProductDesc;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBFXISDA)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}