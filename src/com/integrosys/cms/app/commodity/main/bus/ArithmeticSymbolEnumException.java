/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/ArithmeticSymbolEnumException.java,v 1.2 2004/06/04 04:52:40 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

/**
 * ArithmeticSymbolEnumException is an exception class used by MarketUnitEnum
 * class.
 * @author Dayanand.V
 * @version
 * @since
 * @see ArithmeticSymbolEnum $Date: 2002/11/30
 */
public class ArithmeticSymbolEnumException extends Exception {
	public ArithmeticSymbolEnumException() {
	}

	public ArithmeticSymbolEnumException(String s) {
		super(s);
	}

	public ArithmeticSymbolEnumException(Exception e) {
		super(e.toString());
	}
}
