/**
 * 
 */
package com.integrosys.cms.app.fileUpload.bus;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author shrikant.kalyadapu
 *
 */
public class OBExchangeRateAutoUpload {
	
	private String currency_code;
	private BigDecimal exchange_rate_old;
	private BigDecimal exchange_rate_new;
	private Date exchange_date;
	private Date upload_date;
	private String upload_status;
	private String file_name;
	private String upload_time;
	private String upload_status_message;
	public Date getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}
	public String getCurrency_code() {
		return currency_code;
	}
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
	public BigDecimal getExchange_rate_old() {
		return exchange_rate_old;
	}
	public void setExchange_rate_old(BigDecimal exchange_rate_old) {
		this.exchange_rate_old = exchange_rate_old;
	}
	public BigDecimal getExchange_rate_new() {
		return exchange_rate_new;
	}
	public void setExchange_rate_new(BigDecimal exchange_rate_new) {
		this.exchange_rate_new = exchange_rate_new;
	}
	public Date getExchange_date() {
		return exchange_date;
	}
	public void setExchange_date(Date exchange_date) {
		this.exchange_date = exchange_date;
	}
	public String getUpload_status() {
		return upload_status;
	}
	public void setUpload_status(String upload_status) {
		this.upload_status = upload_status;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getUpload_time() {
		return upload_time;
	}
	public void setUpload_time(String upload_time) {
		this.upload_time = upload_time;
	}
	public String getUpload_status_message() {
		return upload_status_message;
	}
	public void setUpload_status_message(String upload_status_message) {
		this.upload_status_message = upload_status_message;
	}
	
	
	
	

}
