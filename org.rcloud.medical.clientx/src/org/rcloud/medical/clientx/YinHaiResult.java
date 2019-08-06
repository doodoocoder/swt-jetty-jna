package org.rcloud.medical.clientx;

import java.util.Map;

public class YinHaiResult {
	private String astr_jylsh;
	private String astr_jyyzm;
	private String astr_jysc_xml;
	private String aint_appcode;
	private String astr_appmsg;
	private Map<String, Object> aobj;
	
	
	
	
	/**
	 * 
	 */
	public YinHaiResult() {
		super();
	}
	/**
	 * @param astr_jylsh
	 * @param astr_jyyzm
	 * @param astr_jysc_xml
	 * @param aint_appcode
	 * @param astr_appmsg
	 */
	public YinHaiResult(String astr_jylsh, String astr_jyyzm,
			String astr_jysc_xml, String aint_appcode, String astr_appmsg,Map<String, Object> aobj) {
		super();
		this.astr_jylsh = astr_jylsh;
		this.astr_jyyzm = astr_jyyzm;
		this.astr_jysc_xml = astr_jysc_xml;
		this.aint_appcode = aint_appcode;
		this.astr_appmsg = astr_appmsg;
		this.aobj = aobj;
	}
	public String getAstr_jylsh() {
		return astr_jylsh;
	}
	public void setAstr_jylsh(String astr_jylsh) {
		this.astr_jylsh = astr_jylsh;
	}
	public String getAstr_jyyzm() {
		return astr_jyyzm;
	}
	public void setAstr_jyyzm(String astr_jyyzm) {
		this.astr_jyyzm = astr_jyyzm;
	}
	public String getAstr_jysc_xml() {
		return astr_jysc_xml;
	}
	public void setAstr_jysc_xml(String astr_jysc_xml) {
		this.astr_jysc_xml = astr_jysc_xml;
	}
	public String getAint_appcode() {
		return aint_appcode;
	}
	public void setAint_appcode(String aint_appcode) {
		this.aint_appcode = aint_appcode;
	}
	public String getAstr_appmsg() {
		return astr_appmsg;
	}
	public void setAstr_appmsg(String astr_appmsg) {
		this.astr_appmsg = astr_appmsg;
	}
	
	public Map<String, Object> getAobj() {
		return aobj;
	}
	public void setAobj(Map<String, Object> aobj) {
		this.aobj = aobj;
	}
	@Override
	public String toString() {
		return "YinHaiResult [astr_jylsh=" + astr_jylsh + ", astr_jyyzm="
				+ astr_jyyzm + ", astr_jysc_xml=" + astr_jysc_xml
				+ ", aint_appcode=" + aint_appcode + ", astr_appmsg="
				+ astr_appmsg + "]";
	}
	
	
	
}
