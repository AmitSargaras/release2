package com.integrosys.cms.app.diary.bus;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * This interface defines the operations that are provided by a diary item
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/09/30 11:32:23 $ Tag: $Name: $
 */
public interface IDiaryItem extends Serializable, IValueObject {

	/**
	 * gets the country allowed for viewing
	 * @return String - allowed country
	 */
	public String getAllowedCountry();

	/**
	 * retrieves the customer name
	 * @return String - name
	 */
	public String getCustomerName();

	/**
	 * retrieves the LE ID of the customer
	 * @return long - customer LE ID
	 */
	public String getCustomerReference();

	/**
	 * retrieves the customer segment of this diary item
	 * @return String - customer segment
	 */
	public String getCustomerSegment();

	/**
	 * retrieves the description of the diary item
	 * @return String - item description
	 */
	public String getDescription();

	/**
	 * retrieves the due date of diary item
	 * @return Date - diary item due date
	 */
	public Date getDueDate();

	/**
	 * retrieves the expiry date of diary item
	 * @return date - diary item expiry date
	 */
	public Date getExpiryDate();

	/**
	 * retrieves the FAM of diary item
	 * @return String - FAM name
	 */
	public String getFAM();

	/**
	 * retrieves the item id of diary item
	 * @return long - item id
	 */
	public long getItemID();

	/**
	 * retrieves the person who last updated the item
	 * @return string - person who last updated item
	 */
	public String getLastUpdatedBy();

	/**
	 * retrieves the last updated time
	 * @return Date - time of update
	 */
	public Date getLastUpdatedTime();

	/**
	 * retrieves the narrative text of the diary item
	 * @return String - narrative text
	 */
	public String getNarration();

	/**
	 * tests whether a diary item is obsolete
	 * @return boolean - diary item status
	 */
	public boolean getObsoleteInd();

	public long getTeamId();

	/**
	 * retrieves the team type id of this diary item
	 * @return long - team type id
	 */
	public long getTeamTypeID();

	/**
	 * sets the country allowed for viewing
	 * @param country - allowed country
	 */
	public void setAllowedCountry(String country);

	/**
	 * sets the customer name in diary item
	 * @param name - a customer
	 */
	public void setCustomerName(String name);

	/**
	 * sets the LE ID of the customer
	 * @param customerReference - customer LE ID
	 */
	public void setCustomerReference(String customerReference);

	/**
	 * sets the customer segment for diary item
	 * @param seg - customer segment
	 */
	public void setCustomerSegment(String seg);

	/**
	 * sets the description for the diary item
	 * @param desc - item description
	 */
	public void setDescription(String desc);

	/**
	 * sets the due date of diary item
	 * @param dueDate - diary item due date
	 */
	public void setDueDate(Date dueDate);

	/**
	 * sets the expiry date of diary item
	 * @param expiryDate - diary item expiry date
	 */
	public void setExpiryDate(Date expiryDate);

	/**
	 * sets the FAM for this diary item
	 * @param fam - fam name
	 */
	public void setFAM(String fam);

	/**
	 * sets the id of diary item
	 * @param itemID - item id
	 */
	public void setItemID(long itemID);

	/**
	 * sets the person identity who updates the item
	 * @param who - person updating item
	 */
	public void setLastUpdatedBy(String who);

	/**
	 * sets the time of updating
	 * @param when - update timestamp
	 */
	public void setLastUpdatedTime(Date when);

	/**
	 * sets the narrative text of the diary item
	 * @param narration
	 */
	public void setNarration(String narration);

	/**
	 * sets the obselete status for a diary item
	 * @param obsolete
	 */
	public void setObsoleteInd(boolean obsolete);

	public void setTeamId(long teamId);

	/**
	 * sets the team type id used for diary
	 * @param teamTypeID
	 */
	public void setTeamTypeID(long teamTypeID);
	
	public long getDiaryNumber();

	public void setDiaryNumber(long diaryNumber);
	
	public String getRegion();

	public void setRegion(String region);
	
	public String getFacilityBoardCategory();

	public void setFacilityBoardCategory(String facilityBoardCategory) ;
	
	public String getFacilityLineNo();

	public void setFacilityLineNo(String facilityLineNo) ;
	
	public String getFacilitySerialNo();

	public void setFacilitySerialNo(String facilitySerialNo);
	
	public String getDropLineOD();

	public void setDropLineOD(String dropLineOD);

	public String getActivity();

	public void setActivity(String activity);

	public FormFile getOdScheduleUploadFile();

	public void setOdScheduleUploadFile(FormFile odScheduleUploadFile);
	
	public String getMakerId();

	public void setMakerId(String makerId) ;
	
	public Date getMakerDateTime();

	public void setMakerDateTime(Date makerDateTime);
	
	public String getStatus();

	public void setStatus(String status);
	
	public String getAction();

	public void setAction(String action);

	public String getCloseBy() ;

	public void setCloseBy(String closeBy) ;

	public String getCloseDate();

	public void setCloseDate(String closeDate);

	public Date getNextTargetDate();

	public void setNextTargetDate(Date nextTargetDate);
	
	public String getLinkEvent();

	public void setLinkEvent(String linkEvent);
	
	public String getUploadFileError();

	public void setUploadFileError(String uploadFileError);

	public String getIsDelete();

	public void setIsDelete(String isDelete);
	
	public String getMonth();

	public void setMonth(String month) ;

	public String getClosingAmount();

	public void setClosingAmount(String closingAmount);
	
	public String getCustomerSegmentName();

	public void setCustomerSegmentName(String customerSegmentName);
}
