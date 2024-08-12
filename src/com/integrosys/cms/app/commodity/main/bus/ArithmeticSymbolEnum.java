/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/ArithmeticSymbolEnum.java,v 1.2 2004/06/04 04:52:40 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

import java.util.Collection;
import java.util.Vector;

/**
 * The ArithmeticSymbolEnum Type class contains all the valid types of
 * Operators. It will throw ArithmeticSymbolEnumException if the
 * ArithmeticSymbolEnum type is invalid. Valid types are - PLUS, MINUS,
 * PLUS_OR_MINUS. or 1, 2, or 3 respectively. You can create objects of this
 * type in one four ways. For example:
 * 
 * <pre>
 * ArithmeticSymbolEnum t = new ArithmeticSymbolEnum(&quot;PLUS&quot;);
 * 
 * ArithmeticSymbolEnum t1 = new ArithmeticSymbolEnum(1);
 * 
 * ArithmeticSymbolEnum t2 = new ArithmeticSymbolEnum(ArithmeticSymbolEnum._PLUS);
 * 
 * ArithmeticSymbolEnum t3 = new ArithmeticSymbolEnum(ArithmeticSymbolEnum.PLUS);
 * </pre>
 * 
 * Known Bugs: None found so far. Concurrency Strategy: None found so far.
 * 
 * @version
 * @author Dayanand.v
 * 
 * @since $Date: 2002/11/30
 */

public class ArithmeticSymbolEnum implements java.io.Serializable {
	public static final int _PLUS = 1;

	public static final int _MINUS = 2;

	public static final int _PLUS_OR_MINUS = 3;

	final public static ArithmeticSymbolEnum PLUS = new ArithmeticSymbolEnum(_PLUS);

	final public static ArithmeticSymbolEnum MINUS = new ArithmeticSymbolEnum(_MINUS);

	final public static ArithmeticSymbolEnum PLUS_OR_MINUS = new ArithmeticSymbolEnum(_PLUS_OR_MINUS);

	private int value;

	public ArithmeticSymbolEnum(int value) {
		this.value = value;
	}

	public ArithmeticSymbolEnum(String value) throws ArithmeticSymbolEnumException {
		if (value.equalsIgnoreCase("Plus")) {
			this.value = _PLUS;
		}
		else if (value.equalsIgnoreCase("Minus")) {
			this.value = _MINUS;
		}
		else if (value.equalsIgnoreCase("PlusOrMinus")) {
			this.value = _PLUS_OR_MINUS;
		}
		else {
			throw new ArithmeticSymbolEnumException("ArithmeticSymbolEnumException: '" + value + "' is invalid ");
		}
	}

	/**
	 * The method returns integer value of the ArithmeticSymbolEnum
	 * @return a valid integer value equivalent to ArithmeticSymbolEnum
	 * 
	 * @see
	 * @since
	 */

	public int intValue() {
		return value;
	}

	/**
	 * The static method (Factory method) creates an ArithmeticSymbolEnum object
	 * based on the integer ArithmeticSymbolEnum type. It will throw
	 * ArithmeticSymbolEnumException if the passed integer value is invalid.
	 * 
	 * @param value integer that indicates the ArithmeticSymbolEnum.
	 * @return a valid ArithmeticSymbolEnum if given Value is valid. If it's not
	 *         valid, the method will throw an ArithmeticSymbolEnumException.
	 * @exception ArithmeticSymbolEnumException Is thrown if the
	 *            ArithmeticSymbolEnum value is invalid.
	 * 
	 * @see
	 * @since
	 */

	public static ArithmeticSymbolEnum parse(int value) throws ArithmeticSymbolEnumException {
		if (_PLUS == value) {
			return PLUS;
		}
		else if (_MINUS == value) {
			return MINUS;
		}
		else if (_PLUS_OR_MINUS == value) {
			return PLUS_OR_MINUS;
		}
		else {
			throw new ArithmeticSymbolEnumException("ArithmeticSymbolEnumException: Type [" + value + "] is invalid");
		}

	}

	/**
	 * The static method (Factory method) creates an ArithmeticSymbolEnum object
	 * based on the integer ArithmeticSymbolEnum type. It will throw
	 * ArithmeticSymbolEnumException if the passed integer value is invalid.
	 * 
	 * @param value String that indicates the ArithmeticSymbolEnum.
	 * @return Returns a valid ArithmeticSymbolEnum if given Value is valid. If
	 *         it's not valid, the method will throw an
	 *         ArithmeticSymbolEnumException.
	 * @exception ArithmeticSymbolEnumException Is thrown if the
	 *            ArithmeticSymbolEnum value is invalid.
	 * 
	 * @see
	 * @since
	 */
	public static ArithmeticSymbolEnum parse(String value) throws ArithmeticSymbolEnumException {
		if (0 == value.compareToIgnoreCase(PLUS.toString())) {
			return PLUS;
		}
		else if (0 == value.compareToIgnoreCase(MINUS.toString())) {
			return MINUS;
		}
		else if (0 == value.compareToIgnoreCase(PLUS_OR_MINUS.toString())) {
			return PLUS_OR_MINUS;
		}
		else {
			throw new ArithmeticSymbolEnumException("ArithmeticSymbolEnumException: Type [" + value + "] is invalid");
		}

	}

	/**
	 * The method sets the given value for the ArithmeticSymbolEnum object. It
	 * will throw ArithmeticSymbolEnumException if the passed integer value is
	 * invalid.
	 * 
	 * @param value Integer that indicates the ArithmeticSymbolEnum.
	 * @exception ArithmeticSymbolEnumException Is thrown if the
	 *            ArithmeticSymbolEnum value is invalid.
	 * 
	 * @see
	 * @since
	 */
	public void setValue(int value) throws ArithmeticSymbolEnumException {
		if ((value == _PLUS) || (value == _MINUS) || (value == _PLUS_OR_MINUS)) {
			this.value = value;
		}
		else {
			throw new ArithmeticSymbolEnumException("ArithmeticSymbolEnumException: '" + value + "' is invalid ");
		}
	}

	/**
	 * The method sets the given value for the ArithmeticSymbolEnum object. It
	 * will throw ArithmeticSymbolEnumException if the passed integer value is
	 * invalid.
	 * 
	 * @param operator ArithmeticSymbolEnum object that indicates the
	 *        ArithmeticSymbolEnum.
	 * 
	 * @see
	 * @since
	 */
	public void setValue(ArithmeticSymbolEnum operator) {
		this.value = operator.value;
	}

	/**
	 * The method sets the given string value for the ArithmeticSymbolEnum
	 * object by converting in to integervalue . It will throw
	 * ArithmeticSymbolEnumException if the passed integer value is invalid.
	 * 
	 * @param val Integer that indicates the ArithmeticSymbolEnum.
	 * @exception ArithmeticSymbolEnumException Is thrown if the val is an
	 *            invalid string that represents ArithmeticSymbolEnum.
	 * 
	 * @see
	 * @since
	 */
	public void setValue(String val) throws ArithmeticSymbolEnumException {
		if (val.equalsIgnoreCase("Plus")) {
			this.value = _PLUS;
		}
		else if (val.equalsIgnoreCase("Minus")) {
			this.value = _MINUS;
		}
		else if (val.equalsIgnoreCase("PlusOrMinus")) {
			this.value = _PLUS_OR_MINUS;
		}
		else {
			throw new ArithmeticSymbolEnumException("ArithmeticSymbolEnumException: '" + value + "' is invalid ");
		}
	}

	/**
	 * The method gets all the available MarketUnit types.
	 * 
	 * @return Returns a collection of ArithmeticSymbolEnum objects.
	 * @see
	 * @since
	 */
	public static Collection getAllArithmeticSymbolEnums() {
		Vector types = new Vector();
		types.addElement(PLUS);
		types.addElement(MINUS);
		types.addElement(PLUS_OR_MINUS);
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
		if (value == _PLUS) {
			return "PLUS";
		}
		else if (value == _MINUS) {
			return "MINUS";
		}
		else if (value == _PLUS_OR_MINUS) {
			return "PLUS_OR_MINUS";
		}
		else {
			return null;
		}
	}

	/**
	 * The method checks for the equality of value of two a ArithmeticSymbolEnum
	 * object with another ArithmeticSymbolEnum object.
	 * 
	 * @return returns boolean value.
	 * 
	 * @see
	 * @since
	 */
	public boolean equals(ArithmeticSymbolEnum tu) {
		return ((tu instanceof ArithmeticSymbolEnum) && (((ArithmeticSymbolEnum) tu).intValue() == this.value));
	}
}