package com.integrosys.cms.app.ws.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.ws.jax.common.CMSException;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "UdfDetailLimitRequestDTO")
public class UdfDetailLimitRequestDTO {

	@XmlElement(name = "name", required=true)
	private String name;
	
	@XmlElement(name = "value", required=true)
	private String value;
	
	@XmlElement(name = "type", required=true)
	private String type;
	
	@XmlTransient
	private String label;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getMandatoryErrors() {
		if(StringUtils.isEmpty(this.getName()) || 
				StringUtils.isEmpty(this.getType()) ||
				StringUtils.isEmpty(this.getValue())) {
			return "Name, Type and Value are mandatory fields in UDF tag";
		}
		
		return null;
	}
	
	public String getLengthErrors() {
		if(StringUtils.isNotEmpty(this.getName()) && this.getName().length() > 500) {
			return "Name exceeded max expected length for udf name [" + this.getName() + "]";
		}
		
		if(StringUtils.isNotEmpty(this.getType()) && this.getType().length() > 1) {
			return "Type exceeded max expected length for udf name [" + this.getName() + "]";
		}
		
		if(StringUtils.isNotEmpty(this.getValue()) && this.getValue().length() > 2000) {
			return "Value exceeded max expected length for udf name [" + this.getName() + "]";
		}
		
		return null;
	}
	
	public String getDataErrors() {
		if(StringUtils.isNotEmpty(this.getName()) && !StringUtils.isNumeric(this.getName())) {
			return "Name is not in expected format for udf name [" + this.getName() + "]";
		}

		if(StringUtils.isNotEmpty(this.getType())) {
			if(!StringUtils.isNumeric(this.getType())) {
				return "Type is not in expected format for udf name [" + this.getName() + "]";
			}else 
				if(!("1".equals(this.getType()) || "2".equals(this.getType()) ||
						"2".equals(this.getType()) || "3".equals(this.getType()) ||
						"4".equals(this.getType()) || "5".equals(this.getType()) ||
						"6".equals(this.getType()) || "7".equals(this.getType()))) {
					return "Type should in 1 (Text box), 2 (Text area)"
							+ ", 3 (Radio button), 4 (Select box), 5 (Check Box)"
							+ ", 6 (Date), 7 (Numeric Text) for udf name [" + this.getName() + "]";
				}
		}
		
		return null;
	}
	
	public String getExtraDataErrors(IUdf udf) {
		if(StringUtils.isNotEmpty(this.getType()) && StringUtils.isNotEmpty(this.getValue())) {
			if("3".equals(this.getType()) || "4".equals(this.getType())
					|| "5".equals(this.getType())) {
				if(udf.getOptions() == null)
					return "No options maintained for udf name [" + this.getName() + "]";
				String[] options = udf.getOptions().split(",");
				boolean found = false;
				for(String option: options) {
					if(this.getValue().equals(option.trim())) {
						found = true;
						break;
					}
				}
				if(!found) {
					return "Value does't match any options maintained for udf name [" + this.getName() + "]";
				}
			}else if("6".equals(this.getType())) {
				try {
					Date date = new SimpleDateFormat("dd/MM/yyyy").parse(this.getValue());
					String actualDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
					if(!this.getValue().equals(actualDate)) {
						return "Date must be a valid date and in dd/MM/yyyy format for udf name [" + this.getName() + "]";
					}
				}catch(Exception ex) {
					return "Date must be a valid date and in dd/MM/yyyy format for udf name [" + this.getName() + "]";
				}
			}else if("7".equals(this.getType())) {
				if(!StringUtils.isNumeric(this.getValue())){
					return "Only numeric characters expected for udf name [" + this.getName() + "]";
				}
			}
				
		}
		
		return null;
	}
	
}