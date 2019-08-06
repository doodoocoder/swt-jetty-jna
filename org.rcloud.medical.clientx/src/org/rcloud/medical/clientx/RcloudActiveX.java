package org.rcloud.medical.clientx;

import java.text.SimpleDateFormat;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 
 * @author      chenhuaijin
 * @CreateTime  
 * @version     1.0.0
 * @description 
 */
public class RcloudActiveX{

	private static ActiveXComponent component;
	private static String call_method = "yh_interface_call";
	private static String init_method = "yh_interface_init";

	private static String destroy_method = "yh_interface_destroy";
	
	private static String event_success="初始化医保com组件成功";
	private static String event_fail="初始化医保com组件失败，请先注册com组件";
	public static String errorCodeFilter = "-4294967295";
	public static String java_library_path="java.library.path";
	public static String good_place="c:\\rcloud-medical\\jre\\bin";//因为把jacob.dll也打进了程序中，这样程序不会去找path，减少不确定性
	
	
	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void loadCom() {
		Properties properties = System.getProperties();
		properties.setProperty(java_library_path, good_place);
		try {
			component = new ActiveXComponent("yinhai.hbnew.interface");	
		}catch(Exception e) {
			LocalRecord.logError("***加载银海com组件出错,请确认已安装银海医保程序:***",e);
			return;
		}
		LocalRecord.logInfo("加载本地银海com组件成功");
	}

	/**
	 * 
	 * @description 这个初始化方法会访问远程服务
	 * @author       chenhuaijin
	 * @CreateTime   
	 */
	
	public static void initActiveX() {
		
		Long aint_appcode = new Long(-100000);
		String astr_appmsg = "";
		Variant v_aint_appcode = new Variant(aint_appcode, true);
		Variant v_astr_appmsg = new Variant(astr_appmsg, true);
		try {
			init(v_aint_appcode, v_astr_appmsg);
		} catch (Exception e) {// -4294967295 regular error
			;
		}
		Long valueOf = Long.valueOf(v_aint_appcode.toString());
		String realCode = v_aint_appcode.toString();
		if (valueOf >= 0 || StringUtils.contains(errorCodeFilter, realCode)) {//
			LogUtil.log(event_success);
		} else {// 一般是由于没有安装银海的程序导致
			LogUtil.log(event_fail);
			return;

		}

	}

	
	public static void invoke(String astr_jybh, String astr_jysr, Variant astr_jylsh, Variant astr_jyyzm,
			Variant astr_jysc_xml, Variant aint_appcode, Variant astr_appmsg) {
		Dispatch invokeObj = component.getObject();
		Dispatch.call(invokeObj, call_method, astr_jybh, astr_jysr, astr_jylsh, astr_jyyzm, astr_jysc_xml, aint_appcode,
				astr_appmsg);
		return;
	}

	private static  void init(Variant aint_appcode, Variant astr_appmsg) {
		Dispatch invokeObj = component.getObject();
		Dispatch.call(invokeObj, init_method, aint_appcode, astr_appmsg);
		return;
	}

	public static void destroy() {
		if(component != null) {//当加载了本地com组件时才释放内存
			Dispatch invokeObj = component.getObject();
			Dispatch.call(invokeObj, destroy_method);
			return;
		}
		
	}



}