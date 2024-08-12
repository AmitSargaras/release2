/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/common/UOMWrapper.java,v 1.9 2004/08/19 04:46:08 wltan Exp $
 */
package com.integrosys.cms.app.commodity.common;

import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure;

/**
 * This class represents the unit of measure wrapper. It can be a common unit of
 * measure or a user-defined unit of measure.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.9 $
 * @since $Date: 2004/08/19 04:46:08 $ Tag: $Name: $
 */
public class UOMWrapper implements Serializable {

	private String iD; // to conform to javabeans naming convention and to not
						// modify signature

	private String label;

	private IUnitofMeasure commodityUOM;

	public static int COMMON_UOM = 0;

	public static int COMMODITY_UOM = 1;

	public UOMWrapper() {
	}

	/**
	 * Constructor that takes in a common uom.
	 * 
	 * @param anID
	 * @param aLabel
	 */
	protected UOMWrapper(String anID, String aLabel) {
		iD = anID;
		label = aLabel;
	}

	/**
	 * Constructor that takes in a user-defined uom, i.e. unit of measure
	 * object.
	 * 
	 * @param anUOM
	 */
	protected UOMWrapper(IUnitofMeasure anUOM) {
		iD = Long.toString(anUOM.getUnitofMeasureID());
		label = anUOM.getName();
		commodityUOM = anUOM;
	}

	public int getType() {
		return (commodityUOM == null) ? COMMON_UOM : COMMODITY_UOM;
	}

	/**
	 * Gets id of the unit of measure.
	 * 
	 * @return code of the common uom if type is common uom
	 * @return id of the user-defined uom if type is user-defined uom
	 */
	public String getID() {
		return iD;
	}

	/**
	 * Sets id of the unit of measure.
	 * 
	 * @param id - String representing ID of unit of measure
	 */
	public void setID(String id) {
		this.iD = id;
	}

	/**
	 * Gets label of the unit of measure.
	 * 
	 * @return label of the common uom if type is common uom
	 * @return name of the user-defined uom if type is user-defined uom
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets label of the unit of measure.
	 * 
	 * @param label - String representing name of the unit of measure
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets IUnitofMeasure associated with this unit of measure if this is of
	 * type COMMODITY_UOM.
	 * 
	 * @return IUnitofMeasure - if this is of type COMMODITY_UOM
	 * @return null - if this is of type COMMON_UOM
	 */
	public IUnitofMeasure getCommodityUOM() {
		return (getType() == COMMODITY_UOM) ? commodityUOM : null;
	}

	/**
	 * Gets the conversion rate to convert from this UOM to a predefined common
	 * market UOM
	 * 
	 * @return QuantityConversionRate
	 */
	public QuantityConversionRate getMarketUOMConversionRate() {
		if ((getType() == COMMODITY_UOM) && (commodityUOM != null) && (commodityUOM.getMarketQuantity() != null)
				&& (commodityUOM.getMarketQuantity().getQuantity() != null)
				&& (commodityUOM.getMarketQuantity().getUnitofMeasure() != null)) {

			String fromUnit = Long.toString(commodityUOM.getUnitofMeasureID());
			String toUnit = commodityUOM.getMarketQuantity().getUnitofMeasure().getID();
			return new QuantityConversionRate(new ConversionKey(fromUnit, toUnit), commodityUOM.getMarketQuantity()
					.getQuantity());
		}
		return new QuantityConversionRate(new ConversionKey(getID(), getID()), 1);
	}

	/**
	 * Gets the conversion rate to convert from this UOM to a predefined common
	 * metric UOM
	 * 
	 * @return QuantityConversionRate
	 */
	public QuantityConversionRate getMetricUOMConversionRate() {
		if ((getType() == COMMODITY_UOM) && (commodityUOM != null) && (commodityUOM.getMetricQuantity() != null)
				&& (commodityUOM.getMetricQuantity().getQuantity() != null)
				&& (commodityUOM.getMetricQuantity().getUnitofMeasure() != null)) {

			String fromUnit = Long.toString(commodityUOM.getUnitofMeasureID());
			String toUnit = commodityUOM.getMetricQuantity().getUnitofMeasure().getID();
			return new QuantityConversionRate(new ConversionKey(fromUnit, toUnit), commodityUOM.getMetricQuantity()
					.getQuantity());
		}
		return new QuantityConversionRate(new ConversionKey(getID(), getID()), 1);
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof UOMWrapper)) {
			return false;
		}

		final UOMWrapper uomWrapper = (UOMWrapper) o;

		if (iD != null ? !iD.equals(uomWrapper.iD) : uomWrapper.iD != null) {
			return false;
		}
		if (label != null ? !label.equals(uomWrapper.label) : uomWrapper.label != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result;
		result = (iD != null ? iD.hashCode() : 0);
		result = 29 * result + (label != null ? label.hashCode() : 0);
		return result;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
