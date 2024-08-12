/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/MarketUnitEnumException.java,v 1.2 2004/06/04 04:52:40 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

/**
 * MarketUnitEnumException is an exception class used by MarketUnitEnum class.
 * @author Dayanand.V
 * @version
 * @since
 * @see MarketUnitEnum $Date: 2002/11/30
 */
public class MarketUnitEnumException extends java.lang.Exception {
	public MarketUnitEnumException() {
	}

	public MarketUnitEnumException(String s) {
		super(s);
	}

	public MarketUnitEnumException(Exception e) {
		super(e.toString());
	}
}
