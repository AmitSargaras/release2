package com.integrosys.cms.host.stp.core;
import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Clob;

import com.integrosys.cms.host.stp.trade.support.ClobImpl;
import org.exolab.castor.mapping.GeneralizedFieldHandler;

public class ClobFieldHandler extends GeneralizedFieldHandler {

    /**
    * Creates a new ClobFieldHandler instance
     * http://wangrui.javaeye.com/blog/150742
     */
   /* public ClobFieldHandler() {
        super();
    }*/

    /**
     * This method is used to convert the value when the getValue method is called. The getValue method will obtain the
     * actual field value from given 'parent' object. This convert method is then invoked with the field's value. The
     * value returned from this method will be the actual value returned by getValue method.
     *
     * @param value the object value to convert after performing a get operation
     * @return the converted value.
     */
    public Object convertUponGet(Object value) {
         if(value instanceof Clob)
            return convertClobToString(value);
         else if(value instanceof String)
            return convertStringToClob(value);
         else
            return value;
    }

    public Object convertClobToString(Object value) {
        if (value == null) {
            return null;
        }

        try {
            Clob clob = (Clob) value;
            clob.getCharacterStream().reset();
            int i = 0;
            StringBuffer msgBuffer = new StringBuffer();
			if (clob != null) {
				BufferedReader clobBuff = new BufferedReader(clob.getCharacterStream());
				String strClob = clobBuff.readLine();
				while (strClob != null) {
                    i++;
					msgBuffer.append(strClob);
					strClob = clobBuff.readLine();
				}
			}
            if(msgBuffer.toString().trim().length() > 0)
			    return msgBuffer.toString();
            else
                return new String(String.valueOf(i));
        } catch (Exception ex) {
            //throw new IllegalArgumentException(ex.toString());
            return null;
        }
    }

    /**
     * This method is used to convert the value when the setValue method is called. The setValue method will call this
     * method to obtain the converted value. The converted value will then be used as the value to set for the field.
     *
     * @param value the object value to convert before performing a set operation
     * @return the converted value.
     */
    public Object convertUponSet(Object value) {
        if(value instanceof String)
            return convertStringToClob(value);
        else if(value instanceof Clob)
            return convertClobToString(value);
        else
            return value;
    }

    public Object convertStringToClob(Object value) {
        if(value != null){
            String str = (String) value;

            return new ClobImpl(new StringReader(str), str.length());
        }else
            return null;
    }

    /**
    * Returns the class type for the field that this GeneralizedFieldHandler converts to and from. This should be the
     * type that is used in the object model.
    *
     * @return the class type of of the field
     */
    public Class getFieldType() {
        //return String.class;
        return Clob.class;
    }

    /**
     * Creates a new instance of the object described by this field.
     *
     * @param parent The object for which the field is created
     * @return A new instance of the field's value  
     * @throws IllegalStateException This field is a simple type and cannot be instantiated
     */
    /*public Object newInstance(Object parent) throws IllegalStateException {
      // return (Clob) new ClobFieldHandler().convertStringToClob(parent);
        // -- Since it's marked as a string...just return null,
        // -- it's not needed.
       return null;
    }*/

}