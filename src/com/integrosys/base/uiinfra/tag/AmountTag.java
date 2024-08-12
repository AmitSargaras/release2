/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/common/tag/AmountTag.java,v 1.1 2004/07/28 09:46:57 lyng Exp $
 */
package com.integrosys.base.uiinfra.tag;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspTagException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyFormatter;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.uiinfra.tag.LocaleTag;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * This tag formats the amount object.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/07/28 09:46:57 $ Tag: $Name: $
 */
public class AmountTag extends LocaleTag {
	public static final String PARAM_PRINT_ALL = "all";

	public static final String PARAM_PRINT_CURRENCY = "currency";

	public static final String PARAM_PRINT_AMOUNT = "amount";

	public static final String PARAM_PRINT_SYMBOL = "symbol";

	private static final String NULL_STR = "-";

	private static final String EMPTY_STR = "";

	private Amount _amount = null;

	private String _param = null;

	private String _decimal = null;

	private Locale _locale = null;

	private CurrencyFormatter _formatter = null;

	/**
	 * Default constructor.
	 */
	public AmountTag() {
		super();
	}

	public void setAmount(Amount amt) {
		_amount = amt;
	}

	public Amount getAmount() {
		return _amount;
	}

	public void setParam(String param) {
		_param = param;
	}

	public String getParam() {
		return _param;
	}

	public String getDecimal() {
		return _decimal;
	}

	public void setDecimal(String decimal) {
		_decimal = decimal;
	}

	public Locale getLocale() {
		return _locale;
	}

	public void setLocale(Locale locale) {
		this._locale = locale;
	}

	/**
	 * JSP tag.
	 * 
	 * @return int
	 * @throws javax.servlet.jsp.JspException
	 */
	public int doEndTag() throws javax.servlet.jsp.JspException {
		try {
			pageContext.getOut().print(getOutputString());
			return EVAL_PAGE;
		}
		catch (IOException ioe) {
			super.error(this, "Caught IOException!", ioe);
			throw new JspTagException(ioe.toString());
		}
		catch (Exception e) {
			super.error(this, "Caught Unknown Exception!", e);
			throw new JspTagException(e.toString());
		}
	}

	/**
	 * Helper method to get output string based on the attributes given.
	 * 
	 * @return String
	 * @throws Exception on any errors encountered
	 */
	private String getOutputString() throws Exception {
		if ((_amount == null) || (_amount.getCurrencyCode() == null) || (_amount.getAmountAsBigDecimal() == null)
				|| (_amount.getCurrencyCodeAsObject() == null)) {
			return formatNull();
		}

		if (_amount.getCurrencyCode() != null) {
			_formatter = CurrencyManager.getCurrencyFormatter(_amount.getCurrencyCodeAsObject());
		}

		if ((_param == null) || _param.equals(PARAM_PRINT_ALL)) {
			return formatAll();
		}
		else if (_param.equals(PARAM_PRINT_CURRENCY)) {
			return formatCurrency();
		}
		else if (_param.equals(PARAM_PRINT_AMOUNT)) {
			return formatAmount();
		}
		else if (_param.equals(PARAM_PRINT_SYMBOL)) {
			return formatSymbol();
		}
		else {
			return formatAll();
		}
	}

	/**
	 * Helper method to format amount with its currency in the format of
	 * [CCYCODE+ " " + amount].
	 * 
	 * @return formated amount with currency
	 * @throws Exception on errors encountered
	 */
	private String formatAll() throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append(formatCurrency());
		buf.append(" ");
		buf.append(formatAmount());
		return buf.toString();
	}

	/**
	 * Helper method to format value that is null.
	 * 
	 * @return null string representation "-"
	 */
	private String formatNull() {
		return NULL_STR;
	}

	/**
	 * Helper method to format currency. If currency is null, it will format it
	 * as empty string.
	 * 
	 * @return formatted currency based on locale
	 */
	private String formatCurrency() {
		if (_formatter == null) {
			return EMPTY_STR;
		}
		return _formatter.getDisplayCode(_locale);
	}

	/**
	 * Helper method to get currency symbol based on the locale.
	 * 
	 * @return String
	 */
	private String formatSymbol() {
		return _formatter.getCurrencySymbol(_locale);
	}

	/**
	 * Helper method to format amount object.
	 * 
	 * @return String representation of amount
	 * @throws Exception on any errors encountered
	 */
	private String formatAmount() throws Exception {
		int dec = 0;
		if ((this.getDecimal() != null) && (this.getDecimal().length() != 0)) {
			dec = Integer.parseInt(this.getDecimal());
		}

		if (getLocale() == null) {
			setLocale(Locale.getDefault()); // if no locale is being input, just
											// use server locale :D
		}

		String displayAmount = UIUtil.formatNumber(_amount.getAmountAsBigDecimal(), dec, _locale);

		if (_amount.getAmount() < 0) {
			displayAmount = "<font color='#FF0000'>" + displayAmount + "</font>";
		}
		return displayAmount;
	}
}
