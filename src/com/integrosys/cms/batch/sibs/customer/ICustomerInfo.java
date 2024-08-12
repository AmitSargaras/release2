/**
 * 
 */
package com.integrosys.cms.batch.sibs.customer;

import java.util.Date;

/**
 * @author gp loh
 * @date 10 oct 08 1922hr
 *
 */
public interface ICustomerInfo extends ICustomer {


		/**
		 * Get the customer name.
		 *
		 * @return String
		 */
		public String getCustomerName();

		/**
		 * Get the legal constitution
		 *
		 * @return String
		 */
		public String getLegalConstitution();

		/**
		 * Get the legal country
		 *
		 * @return String
		 */
		public String getLegalRegCountry();

		/**
		 * Get the customer short name.
		 *
		 * @return String
		 */
		public String getCustomerShortName();


		/**
		 * Get the customer type.
		 *
		 * @return String
		 */
		public String getCustomerType();

		/**
		 * Get the ISIC Code.
		 *
		 * @return String
		 */
		public String getIsicCode();

		/**
		 * Get the ID No.
		 *
		 * @return String
		 */
		public String getIdNo();

		/**
		 * Get the Id type.
		 *
		 * @return String
		 */
		public String getIdType();

		/**
		 * Get the customer relationship start date
		 *
		 * @return Date
		 */
		public Date getCustomerRelationshipStartDate();

		/**
		 * Get the incorporated date
		 *
		 * @return Date
		 */
		public Date getIncorporatedDate();

		/**
		 * Get the Secondary ID No
		 *
		 * @return String
		 */
		public String getSecIdNo();

		/**
		 * Get the Secondary ID Type
		 *
		 * @return String
		 */
		public String getSecIdType();

		/**
		 * Get the customer address type
		 *
		 * @return String
		 */
		public String getAddrType();

		/**
		 * Get the residential address/town/city
		 *
		 * @return String
		 */
		public String getAddr1();

		/**
		 * Get the residential address/town/city
		 *
		 * @return String
		 */
		public String getAddr2();

		/**
		 * Get the residential address/town/city
		 *
		 * @return String
		 */
		public String getAddr3();

		/**
		 * Get the residential address/town/city
		 *
		 * @return String
		 */
		public String getAddr4();

		/**
		 * Get the postal code
		 *
		 * @return String
		 */
		public String getPostCode();

		/**
		 * Get the residential country
		 *
		 * @return String
		 */
		public String getResCountry();

		/**
		 * Get the language
		 *
		 * @return String
		 */
		public String getLanguage();

		/**
		 * Get the secondary address type
		 *
		 * @return String
		 */
		public String getSecAddrType();

		/**
		 * Get the secondary residential address/town/city
		 *
		 * @return String
		 */
		public String getSecAddr1();

		/**
		 * Get the secondary residential address/town/city
		 *
		 * @return String
		 */
		public String getSecAddr2();

		/**
		 * Get the secondary residential address/town/city
		 *
		 * @return String
		 */
		public String getSecAddr3();

		/**
		 * Get the secondary residential address/town/city
		 *
		 * @return String
		 */
		public String getSecAddr4();

		/**
		 * Get the secondary residential postal code
		 *
		 * @return String
		 */
		public String getSecPostcode();

		/**
		 * Set the customer name.
		 *
		 * @param value is of type String
		 */
		public void setCustomerName(String value);

		/**
		 * Set the customer name.
		 *
		 * @param value is of type String
		 */
		public void setCustomerShortName(String value);

		public void setLegalConstitution( String temp );

		public void setLegalRegCountry( String tempCtry );

		/**
		 * Set the ID No.
		 *
		 * @param value is of type String
		 */
		public void setIdNo(String idNumber);

		/**
		 * Set the ID Type.
		 *
		 * @param value is of type String
		 */
		public void setIdType(String idNoType);

		/**
		 * Set the customer relationship start date
		 *
		 * @param value is of type Date
		 */
		public void setCustomerRelationshipStartDate(Date value);

		/**
		 * Set the customer incorporated date
		 *
		 * @param value is of type Date
		 */
		public void setIncorporatedDate(Date value);

		/**
		 * Set the customer type
		 *
		 * @param value is of type String
		 */

		public void setCustomerType(String value);

		/**
		 * Set the ISIC code
		 *
		 * @param value is of type String
		 */

		public void setIsicCode(String iSicCode);

		/**
		 * set the Secondary ID No
		 *
		 * @param value is of type String
		 */
		public void setSecIdNo(String secIdNumber);

		/**
		 * Set the Secondary ID Type
		 *
		 * @param value is of type  String
		 */
		public void setSecIdType(String secIdType);

		/**
		 * Set the primary address Type
		 *
		 * @param value is of type  String
		 */
		public  void setAddrType(String priAddrType);

		/**
		 * Set the residential address/town/city
		 *
		 * @param value is of type String
		 */
		public void setAddr1(String priAddr1);

		/**
		 * Set the residential address/town/city
		 *
		 * @param value is of type String
		 */
		public void setAddr2(String priAddr2);

		/**
		 * Set the residential address/town/city
		 *
		 * @param value is of type String
		 */
		public void setAddr3(String priAddr3);

		/**
		 * Set the residential address/town/city
		 *
		 * @param value is of type String
		 */
		public void setAddr4(String priAddr4);

		/**
		 * Set the residential postal code
		 *
		 * @param value is of type String
		 */
		public void setPostCode(String priPostcode);

		/**
		 * Set the residential country
		 *
		 * @param value is of type String
		 */
		public void setResCountry(String resCountry);

		/**
		 * Set the langauge
		 *
		 * @param value is of type String
		 */
		public void setLanguage(String lang);

		/**
		 * Set the secondary residential address type
		 *
		 * @param value is of type String
		 */
		public void setSecAddrType(String sAddrType);

		/**
		 * Set the secondary residential address/town/city
		 *
		 * @param value is of type String
		 */
		public void setSecAddr1(String sAddr1);

		/**
		 * Set the secondary residential address/town/city
		 *
		 * @param value is of type String
		 */
		public void setSecAddr2(String sAddr2);

		/**
		 * Set the secondary residential address/town/city
		 *
		 * @param value is of type String
		 */
		public void setSecAddr3(String sAddr3);

		/**
		 * Set the secondary residential address/town/city
		 *
		 * @param value is of type String
		 */
		public void setSecAddr4(String sAddr4);

		/**
		 * Set the secondary residential postal code
		 *
		 * @param value if of type String
		 */
		public void setSecPostcode(String sPostcode);

}
