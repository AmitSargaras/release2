/*
* Copyright Integro Technologies Pte Ltd
* $Header$
*/

package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * ExemptFacilityException
 * Purpose:
 * Description:
 *
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
public class ExemptFacilityException extends OFAException {

        /**
         * Default Constructor
         */
        public ExemptFacilityException() {
//            super();
        }

        /**
         * Construct the exception with a string message
         *
         * @param msg is of type String
         */
        public ExemptFacilityException (String msg) {
          super(msg);
        }

        /**
         * Construct the exception with a throwable object
         *
         * @param obj is of type Throwable
         */
        public ExemptFacilityException (Throwable obj) {
            super(obj);
        }

        /**
         * Construct the exception with a string message
         * and a throwable object.
         *
         * @param msg is of type String
         * @param obj is of type Throwable
         */
        public ExemptFacilityException (String msg, Throwable obj) {
            super(msg, obj);
        }

}
