package com.integrosys.base.techinfra.converter;

import org.apache.commons.beanutils.locale.converters.StringLocaleConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class EnhancedStringLocaleConverter extends StringLocaleConverter {
    /** All logging goes through this logger */
    private Log log = LogFactory.getLog(StringLocaleConverter.class);     
    
    private int decimal;
    private boolean hasDecimalSeperator = true;
	private boolean hasGroupSeperator = true;
	
    // ----------------------------------------------------------- Constructors

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will throw a {@link org.apache.commons.beanutils.ConversionException}
     * if a conversion error occurs. The locale is the default locale for
     * this instance of the Java Virtual Machine and an unlocalized pattern is used
     * for the convertion.
     *
     */
    public EnhancedStringLocaleConverter() {

        this(false);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will throw a {@link org.apache.commons.beanutils.ConversionException}
     * if a conversion error occurs. The locale is the default locale for
     * this instance of the Java Virtual Machine.
     *
     * @param locPattern    Indicate whether the pattern is localized or not
     */
    public EnhancedStringLocaleConverter(boolean locPattern) {

        this(Locale.getDefault(), locPattern);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will throw a {@link org.apache.commons.beanutils.ConversionException}
     * if a conversion error occurs. An unlocalized pattern is used for the convertion.
     *
     * @param locale        The locale
     */
    public EnhancedStringLocaleConverter(Locale locale) {

        this(locale, false);
    }    
    
    public EnhancedStringLocaleConverter(Locale locale, int decimal) {

        this(locale, false, decimal);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will throw a {@link org.apache.commons.beanutils.ConversionException}
     * if a conversion error occurs.
     *
     * @param locale        The locale
     * @param locPattern    Indicate whether the pattern is localized or not
     */
    public EnhancedStringLocaleConverter(Locale locale, boolean locPattern) {

        this(locale, (String) null, locPattern, 0);
    }
    
    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will throw a {@link org.apache.commons.beanutils.ConversionException}
     * if a conversion error occurs.
     *
     * @param locale        The locale
     * @param locPattern    Indicate whether the pattern is localized or not
     */
    public EnhancedStringLocaleConverter(Locale locale, boolean locPattern, int decimal) {

        this(locale, (String) null, locPattern, decimal);
    }    

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will throw a {@link org.apache.commons.beanutils.ConversionException}
     * if a conversion error occurs. An unlocalized pattern is used for the convertion.
     *
     * @param locale        The locale
     * @param pattern       The convertion pattern
     */
    public EnhancedStringLocaleConverter(Locale locale, String pattern) {

        this(locale, pattern, false, 0);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will throw a {@link org.apache.commons.beanutils.ConversionException}
     * if a conversion error occurs.
     *
     * @param locale        The locale
     * @param pattern       The convertion pattern
     * @param locPattern    Indicate whether the pattern is localized or not
     */
    public EnhancedStringLocaleConverter(Locale locale, String pattern, boolean locPattern, int decimal) {

        super(locale, pattern, locPattern);
        this.decimal = decimal;
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will return the specified default value
     * if a conversion error occurs. The locale is the default locale for
     * this instance of the Java Virtual Machine and an unlocalized pattern is used
     * for the convertion.
     *
     * @param defaultValue  The default value to be returned
     */
    public EnhancedStringLocaleConverter(Object defaultValue) {

        this(defaultValue, false);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will return the specified default value
     * if a conversion error occurs. The locale is the default locale for
     * this instance of the Java Virtual Machine.
     *
     * @param defaultValue  The default value to be returned
     * @param locPattern    Indicate whether the pattern is localized or not
     */
    public EnhancedStringLocaleConverter(Object defaultValue, boolean locPattern) {

        this(defaultValue, Locale.getDefault(), locPattern);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will return the specified default value
     * if a conversion error occurs. An unlocalized pattern is used for the convertion.
     *
     * @param defaultValue  The default value to be returned
     * @param locale        The locale
     */
    public EnhancedStringLocaleConverter(Object defaultValue, Locale locale) {

        this(defaultValue, locale, false);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will return the specified default value
     * if a conversion error occurs.
     *
     * @param defaultValue  The default value to be returned
     * @param locale        The locale
     * @param locPattern    Indicate whether the pattern is localized or not
     */
    public EnhancedStringLocaleConverter(Object defaultValue, Locale locale, boolean locPattern) {

        this(defaultValue, locale, null, locPattern);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will return the specified default value
     * if a conversion error occurs. An unlocalized pattern is used for the convertion.
     *
     * @param defaultValue  The default value to be returned
     * @param locale        The locale
     * @param pattern       The convertion pattern
     */
    public EnhancedStringLocaleConverter(Object defaultValue, Locale locale, String pattern) {

        this(defaultValue, locale, pattern, false);
    }

    /**
     * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter}
     * that will return the specified default value
     * if a conversion error occurs.
     *
     * @param defaultValue  The default value to be returned
     * @param locale        The locale
     * @param pattern       The convertion pattern
     * @param locPattern    Indicate whether the pattern is localized or not
     */
    public EnhancedStringLocaleConverter(Object defaultValue, Locale locale, String pattern, boolean locPattern) {    	
        super(defaultValue, locale, pattern, locPattern);
    }    

	public void setHasDecimalSeperator(boolean hasDecimalSeperator) {
		this.hasDecimalSeperator = hasDecimalSeperator;	
	}

	public void setHasGroupSeperator(boolean hasGroupSeperator) {
		this.hasGroupSeperator = hasGroupSeperator;
	}
	
	public EnhancedStringLocaleConverter setHasSeperator(boolean hasSeperator) {
		this.hasDecimalSeperator = hasSeperator;	
		this.hasGroupSeperator = hasSeperator;
		return this;
	}

	/**
     * Convert the specified locale-sensitive input object into an output object of the
     * specified type.
     *
     * @param value The input object to be converted
     * @param pattern The pattern is used for the convertion
     * @return The converted value
     *
     * @exception org.apache.commons.beanutils.ConversionException if conversion
     * cannot be performed successfully
     * @throws ParseException if an error occurs
     */
    protected Object parse(Object value, String pattern) throws ParseException {

        String result = null;
        int decimalSeperatorIndex = -1;
        
        if ((value instanceof Double) ||
                (value instanceof BigDecimal) ||
                (value instanceof Float)) {

        	DecimalFormat decFormat = getDecimalFormat(locale, pattern);
        	if (!hasDecimalSeperator) {
        		decFormat.setDecimalSeparatorAlwaysShown(false);

        		if (this.pattern != null) {
        			char decimalSeparator = decFormat.getDecimalFormatSymbols().getDecimalSeparator();
        			decimalSeperatorIndex = this.pattern.indexOf(decimalSeparator);
        			if (decimalSeperatorIndex >= 0) {
        				this.decimal = (this.pattern.substring(decimalSeperatorIndex + 1)).length();
        			}        			
        		}
        	}        	
        	if (!hasGroupSeperator) {
        		decFormat.setGroupingUsed(false);
        	}

        	decFormat.setMinimumFractionDigits(decimal);
	        decFormat.setMaximumFractionDigits(decimal);

            result = decFormat.format(((Number) value).doubleValue());
            if (!hasDecimalSeperator && decimalSeperatorIndex >= 0) {
            	result = result.substring(0, decimalSeperatorIndex) + result.substring(decimalSeperatorIndex + 1);
            }
        }
        else {
        	result = (String) super.parse(value, pattern);
        }

        return result;
    }
    
    /**
     * Make an instance of DecimalFormat.
     *
     * @param locale The locale
     * @param pattern The pattern is used for the convertion
     * @return The format for the locale and pattern
     *
     * @exception ConversionException if conversion cannot be performed
     *  successfully
     * @throws ParseException if an error occurs parsing a String to a Number
     */
    private DecimalFormat getDecimalFormat(Locale locale, String pattern) {

        DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getInstance(locale);

        // if some constructors default pattern to null, it makes only sense to handle null pattern gracefully
        if (pattern != null) {
            if (locPattern) {
                numberFormat.applyLocalizedPattern(pattern);
            } else {
                numberFormat.applyPattern(pattern);
            }
        } else {
            log.debug("No pattern provided, using default.");
        }

        return numberFormat;
    }    
}
