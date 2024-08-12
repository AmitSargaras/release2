/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/IContact.java,v 1.2 2003/06/17 05:08:10 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

/**
 * This interface represents a contact information which includes address, email
 * and phone numbers.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/17 05:08:10 $ Tag: $Name: $
 */
public interface IDirector extends java.io.Serializable {
	
	public long getDirectorID();

	public void setDirectorID(long directorID);

	public String getRelatedType() ;

	public void setRelatedType(String relatedType) ;

	public String getRelationship();

	public void setRelationship(String relationship);

	public String getDirectorEmail();

	public void setDirectorEmail(String directorEmail);

	public String getDirectorFax();

	public void setDirectorFax(String directorFax);

	public String getDirectorTelNo();

	public void setDirectorTelNo(String directorTelNo) ;

	public String getDirectorCountry();

	public void setDirectorCountry(String directorCountry) ;

	public String getDirectorState() ;

	public void setDirectorState(String directorState) ;

	public String getDirectorCity() ;

	public void setDirectorCity(String directorCity) ;

	public String getDirectorRegion();

	public void setDirectorRegion(String directorRegion); 

	public String getDirectorPostCode() ;

	public void setDirectorPostCode(String directorPostCode) ;

	public String getDirectorAddress3();

	public void setDirectorAddress3(String directorAddress3) ;

	public String getDirectorAddress2() ;

	public void setDirectorAddress2(String directorAddress2) ;

	public String getDirectorAddress1() ;

	public void setDirectorAddress1(String directorAddress1) ;

	public String getPercentageOfControl() ;

	public void setPercentageOfControl(String percentageOfControl) ;

	public String getFullName() ;

	public void setFullName(String fullName);

	public String getNamePrefix() ;

	public void setNamePrefix(String namePrefix);

	public String getBusinessEntityName() ;

	public void setBusinessEntityName(String businessEntityName); 

	public String getDirectorPan() ;

	public void setDirectorPan(String directorPan);
	
	public String getDirectorAadhar();

	public void setDirectorAadhar(String directorAadhar);

	public String getRelatedDUNSNo() ;

	public void setRelatedDUNSNo(String relatedDUNSNo);

	public String getDinNo() ;

	public void setDinNo(String dinNo);

	public String getDirectorName() ;

	public void setDirectorName(String directorName);
	
    public long getLEID();
	
	public void setLEID(long value);
	
	public String getDirStdCodeTelNo();

	public void setDirStdCodeTelNo(String dirStdCodeTelNo); 
	
	public String getDirStdCodeTelex();

	public void setDirStdCodeTelex(String dirStdCodeTelex) ;


}