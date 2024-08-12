package com.integrosys.cms.ui.imageTag;
/**
 *@author abhijit.rudrakshawar
 *$Form Bean for Image Tag
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

public class ImageTagAddForm extends TrxContextForm implements Serializable {

	private String imgId;
	
	private String imgFileName;

	private String imgSize;

	private String imgDepricated;

	private String versionTime;

	private String custId;

	private String custName;
	
	private FormFile imageFile;
	
	private String imageFilePath;
	
	private String securityId;
	
	private ArrayList securityList;
	
	private Collection col;
	
	private String [] selectedForSubmission ;
	

	

	public String[] getSelectedForSubmission() {
		return selectedForSubmission;
	}

	public void setSelectedForSubmission(String[] selectedForSubmission) {
		this.selectedForSubmission = selectedForSubmission;
	}

	public Collection getCol() {
		return col;
	}

	public void setCol(Collection col) {
		this.col = col;
	}

	public ArrayList getSecurityList() {
		return securityList;
	}

	public void setSecurityList(ArrayList securityList) {
		this.securityList = securityList;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getImgFileName() {
		return imgFileName;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	

	public String getImgDepricated() {
		return imgDepricated;
	}

	public void setImgDepricated(String imgDepricated) {
		this.imgDepricated = imgDepricated;
	}


	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public String getImgSize() {
		return imgSize;
	}

	public void setImgSize(String imgSize) {
		this.imgSize = imgSize;
	}

	public String getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String[][] getMapper() {
		String[][] input = { { "ImageTagAddObj", IMAGETAGADD_MAPPER }	,
				{"event","java.lang.String"},
				{ "imageTagAddForm", "com.integrosys.cms.ui.imageTag.ImageTagAddForm"},
				{ "obImageTagAddList", "java.util.ArrayList"},
				
		};
		return input;
	}

	public static final String IMAGETAGADD_MAPPER = "com.integrosys.cms.ui.imageTag.ImageTagAddMapper";

	public FormFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(FormFile imageFile) {
		this.imageFile = imageFile;
	}

	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	

}
