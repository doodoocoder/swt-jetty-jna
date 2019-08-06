package org.rcloud.medical.clientx;

import java.io.Serializable;
import java.util.Date;

/**
 * @author      lanxuyu
 * @CreateTime 
 * @version     1.0.0
 * @description TODO
 */
public class Return<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1356393095089250346L;
	private String code;
	private Boolean success; 
	private String message;
	private Date time;
	private T data;
	/**
	 * @param code
	 * @param message
	 * @param data
	 */
	public Return() {
		super();
	}
	
	/**
	 * @param code
	 * @param success
	 * @param message
	 * @param time
	 * @param data
	 */
	public Return(String code, Boolean success, String message, Date time, T data) {
		super();
		this.code = code;
		this.success = success;
		this.message = message;
		this.time = time;
		this.data = data;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Return [code=" + code + ", success=" + success + ", message="
				+ message + ", time=" + time + ", data=" + data + "]";
	}
	
	
	
}
