/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/MarketUnitEnum.java,v 1.2 2004/06/04 04:52:40 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

import java.util.Collection;
import java.util.Vector;

/**
 * The MarketUnitEnum Type class contains all the valid types of Operators. It
 * will throw MarketUnitEnumException if the MarketUnitEnum type is invalid.
 * Valid types are - POUNDS, TONS, BARREL and DIVIDE or 1, 2, 3, or 4
 * respectively. You can create objects of this type in one four ways. For
 * example:
 * 
 * <pre>
 * MarketUnitEnum t = new MarketUnitEnum(&quot;POUNDS&quot;);
 * 
 * MarketUnitEnum t1 = new MarketUnitEnum(1);
 * 
 * MarketUnitEnum t2 = new MarketUnitEnum(MarketUnitEnum._POUNDS);
 * 
 * MarketUnitEnum t3 = new MarketUnitEnum(MarketUnitEnum.POUNDS);
 * </pre>
 * 
 * Known Bugs: None found so far. Concurrency Strategy: None found so far.
 * 
 * @version
 * @author Dayanand.v
 * 
 * @since $Date: 2002/11/30
 */

public class MarketUnitEnum implements java.io.Serializable {
	public static final int _POUNDS = 1;

	public static final int _TONS = 2;

	public static final int _BARREL = 3;

	final public static MarketUnitEnum POUNDS = new MarketUnitEnum(_POUNDS);

	final public static MarketUnitEnum TONS = new MarketUnitEnum(_TONS);

	final public static MarketUnitEnum BARREL = new MarketUnitEnum(_BARREL);

	private int value;

	public MarketUnitEnum(int value) {
		this.value = value;
	}

	public MarketUnitEnum(String value) throws MarketUnitEnumException {
		if (value.equalsIgnoreCase("Pounds")) {
			this.value = _POUNDS;
		}
		else if (value.equalsIgnoreCase("Tons")) {
			this.value = _TONS;
		}
		else if (value.equalsIgnoreCase("Barrel")) {
			this.value = _BARREL;
		}
		else {
			throw new MarketUnitEnumException("MarketUnitEnumException: '" + value + "' is invalid ");
		}
	}

	/**
	 * The method returns integer value of the MarketUnitEnum
	 * @return a valid integer value equivalent to MarketUnitEnum
	 * 
	 * @see
	 * @since
	 */

	public int intValue() {
		return value;
	}

	/**
	 * The static method (Factory method) creates an MarketUnitEnum object based
	 * on the integer MarketUnitEnum type. It will throw MarketUnitEnumException
	 * if the passed integer value is invalid.
	 * 
	 * @param value integer that indicates the MarketUnitEnum.
	 * @return a valid MarketUnitEnum if given Value is valid. If it's not
	 *         valid, the method will throw an MarketUnitEnumException.
	 * @exception MarketUnitEnumException Is thrown if the MarketUnitEnum value
	 *            is invalid.
	 * 
	 * @see
	 * @since
	 */

	public static MarketUnitEnum parse(int value) throws MarketUnitEnumException {
		if (_POUNDS == value) {
			return POUNDS;
		}
		else if (_TONS == value) {
			return TONS;
		}
		else if (_BARREL == value) {
			return BARREL;
		}
		else {
			throw new MarketUnitEnumException("MarketUnitEnumException: Type [" + value + "] is invalid");
		}

	}

	/**
	 * The static method (Factory method) creates an MarketUnitEnum object based
	 * on the integer MarketUnitEnum type. It will throw MarketUnitEnumException
	 * if the passed integer value is invalid.
	 * 
	 * @param value String that indicates the MarketUnitEnum.
	 * @return Returns a valid MarketUnitEnum if given Value is valid. If it's
	 *         not valid, the method will throw an MarketUnitEnumException.
	 * @exception MarketUnitEnumException Is thrown if the MarketUnitEnum value
	 *            is invalid.
	 * 
	 * @see
	 * @since
	 */
	public static MarketUnitEnum parse(String value) throws MarketUnitEnumException {
		if (0 == value.compareToIgnoreCase(POUNDS.toString())) {
			return POUNDS;
		}
		else if (0 == value.compareToIgnoreCase(TONS.toString())) {
			return TONS;
		}
		else if (0 == value.compareToIgnoreCase(BARREL.toString())) {
			return BARREL;
		}
		else {
			throw new MarketUnitEnumException("MarketUnitEnumException: Type [" + value + "] is invalid");
		}

	}

	/**
	 * The method sets the given value for the MarketUnitEnum object. It will
	 * throw MarketUnitEnumException if the passed integer value is invalid.
	 * 
	 * @param value Integer that indicates the MarketUnitEnum.
	 * @exception MarketUnitEnumException Is thrown if the MarketUnitEnum value
	 *            is invalid.
	 * 
	 * @see
	 * @since
	 */
	public void setValue(int value) throws MarketUnitEnumException {
		if ((value == _POUNDS) || (value == _TONS) || (value == _BARREL)) {
			this.value = value;
		}
		else {
			throw new MarketUnitEnumException("MarketUnitEnumException: '" + value + "' is invalid ");
		}
	}

	/**
	 * The method sets the given value for the MarketUnitEnum object. It will
	 * throw MarketUnitEnumException if the passed integer value is invalid.
	 * 
	 * @param operator MarketUnitEnum object that indicates the MarketUnitEnum.
	 * 
	 * @see
	 * @since
	 */
	public void setValue(MarketUnitEnum operator) {
		this.value = operator.value;
	}

	/**
	 * The method sets the given string value for the MarketUnitEnum object by
	 * converting in to integervalue . It will throw MarketUnitEnumException if
	 * the passed integer value is invalid.
	 * 
	 * @param val Integer that indicates the MarketUnitEnum.
	 * @exception MarketUnitEnumException Is thrown if the val is an invalid
	 *            string that represents MarketUnitEnum.
	 * 
	 * @see
	 * @since
	 */
	public void setValue(String val) throws MarketUnitEnumException {
		if (val.equalsIgnoreCase("Pounds")) {
			this.value = _POUNDS;
		}
		else if (val.equalsIgnoreCase("Tons")) {
			this.value = _TONS;
		}
		else if (val.equalsIgnoreCase("Barrel")) {
			this.value = _BARREL;
		}
		else {
			throw new MarketUnitEnumException("MarketUnitEnumException: '" + value + "' is invalid ");
		}
	}

	/**
	 * The method gets all the available MarketUnit types.
	 * 
	 * @return Returns a collection of MarketUnitEnum objects.
	 * @see
	 * @since
	 */
	public static Collection getAllMarketUnitEnums() {
		Vector types = new Vector();
		types.addElement(POUNDS);
		types.addElement(TONS);
		types.addElement(BARREL);
		return types;
	}

	/**
	 * The method gets the MarketUnit as String .
	 * 
	 * @return Returns String of MarketUnit type.
	 * 
	 * @see
	 * @since
	 */
	public String toString() {
		if (value == _POUNDS) {
			return "POUNDS";
		}
		else if (value == _TONS) {
			return "TONS";
		}
		else if (value == _BARREL) {
			return "BARREL";
		}
		else {
			return null;
		}
	}

	/**
	 * The method checks for the equality of value of two a MarketUnitEnum
	 * object with another MarketUnitEnum object.
	 * 
	 * @return returns boolean value.
	 * 
	 * @see
	 * @since
	 */
	public boolean equals(MarketUnitEnum tu) {
		return ((tu instanceof MarketUnitEnum) && (((MarketUnitEnum) tu).intValue() == this.value));
	}
}