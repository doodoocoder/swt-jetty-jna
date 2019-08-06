package org.rcloud.medical.clientx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jacob.com.Variant;

/**
 * 
 * @author chenhuaijin
 * @CreateTime
 * @version 1.0.0
 * @description 接受请求，处理请求
 */
public class WebServer {

	private static Server server = null;

	private static final int RCLOUD_PORT = 8999;//

	private static WebServer singleton;

	private WebServer() {

	}

	public static WebServer getInstance() {
		if (singleton != null) {
			return singleton;
		} else {
			singleton = new WebServer();
			return singleton;
		}
	}

	/*
	 *
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	public void start() throws Exception {

		startJetty();

	}

	/**
	 * 
	 * @description
	 * @author chenhuaijin
	 * @CreateTime
	 * @throws Exception
	 */
	public void stop() throws Exception {

		if (server != null) {

			server.stop();

		}
	}

	/**
	 * 
	 * @description yinhai/accept
	 * @author chenhuaijin
	 * @CreateTime
	 */
	private void startJetty() {
		server = new Server(RCLOUD_PORT);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");

		ServletHolder sh = new ServletHolder(new YinHaiServlet());
		context.addServlet(sh, "/yinhai/accept");
		
		ServletHolder mapSh = new ServletHolder(new MappingCodesServlet());
		context.addServlet(mapSh, "/yinhai/getMappingCodes");

		ServletHandler handler = new ServletHandler();
		FilterHolder fh = handler.addFilterWithMapping(CrossOriginFilter.class, "/*",
				EnumSet.of(DispatcherType.REQUEST));
		fh.setInitParameter("allowedOrigins", "*");
		fh.setInitParameter("allowedMethods", "POST,HEAD");
		fh.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
		context.addFilter(fh, "/*", EnumSet.of(DispatcherType.REQUEST));


		server.setHandler(context);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("serial")
	private class MappingCodesServlet extends HttpServlet {
		@Override
		protected void doGet(HttpServletRequest request, //
				HttpServletResponse response) throws IOException {

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println("<h1>success</h1>");
			LogUtil.log("success");
			LocalRecord.logInfo("测试操作成功");
		}
		@Override
		protected void doPost(HttpServletRequest request, //
				HttpServletResponse response) throws IOException{
			String filePath="C:\\rcloud-medical\\query\\query.txt";
	    	Return<List<String>> result = new Return<List<String>>();
	    	result.setCode("200");
	    	result.setSuccess(true);
	    	List<String> codes=new ArrayList<String>();
	    	//检查C盘客户端文件夹是否存在
	    	File c = new File("C:\\rcloud-medical");
			if(c.exists()){
				//检查查询记录文件是否存在，若存在则删除
				File queryFile = new File(filePath);
				File parentFile = queryFile.getParentFile();
				if(!parentFile.exists()){
					parentFile.mkdirs();
				}
				if(queryFile.exists()){
					queryFile.delete();
				}
				try {
					queryFile.createNewFile();
				} catch (Exception e) {
					result.setCode("400");
			    	result.setSuccess(false);
			    	result.setMessage("医保目录编码获取失败["+e.getMessage()+"]");
			    	
			    	response.setContentType("application/json;charset=utf-8");
					response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().println(JSON.toJSONString(result));
			    	return ;
				}
				//创建保存查询记录的文件
				
				String astr_jybh="82";
		    	String astr_jysr="<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"yes\" ?><input><ake005></ake005><outputfile>"+filePath+"</outputfile></input>";
		    	Return<YinHaiResult> accept = accept(astr_jybh, astr_jysr);
		    	if(accept.getSuccess()){
		    		//   下载成功后解析文件，获取映射好的医疗机构目录编码
		    		try {
						getCodes(filePath,codes);
						result.setData(codes);
					} catch (Exception e) {
						result.setCode("400");
				    	result.setSuccess(false);
				    	result.setMessage("医保目录编码获取失败["+e.getMessage()+"]");
					}
		    	}else{
		    		result.setCode("400");
			    	result.setSuccess(false);
			    	result.setMessage("医保目录下载失败["+accept.getMessage()+"]");
		    	}
			}else{
				result.setCode("400");
		    	result.setSuccess(false);
		    	result.setMessage("医保目录下载失败，客户端目录不存在！");
			} 
			
			response.setContentType("application/json;charset=utf-8");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println(JSON.toJSONString(result));
			
		}
	}
	
	private List<String> getCodes(String filePath,List<String> codes) throws Exception{
    	File file = new File(filePath);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while((line=br.readLine())!=null){ //循环读取行
			  String[] segments = line.split("\t"); //按tab分割
			     codes.add(segments[2]);
			}
			return codes;
		
    }
	
    private Return<YinHaiResult> accept(String astr_jybh,String astr_jysr){
    	//astr_jybh="82";
    	//astr_jysr="<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"yes\" ?><input><ake005></ake005><outputfile>E:\\RCLOUD\\湖北村卫生室管理系统\\医保\\query.txt</outputfile></input>";
    	String astr_jylsh="";
    	String astr_jyyzm="";
    	String astr_jysc_xml="";
    	Long aint_appcode=new Long(-100000);
    	String astr_appmsg="";
    	Variant v_astr_jylsh = new Variant(astr_jylsh, true); 
    	Variant v_astr_jyyzm = new Variant(astr_jyyzm, true); 
    	Variant v_astr_jysc_xml = new Variant(astr_jysc_xml, true);
    	Variant v_aint_appcode = new Variant(aint_appcode, true); 
    	Variant v_astr_appmsg = new Variant(astr_appmsg, true); 
    	Map<String, Object> map=null;
    	Return<YinHaiResult> result = new Return<YinHaiResult>();
    	result.setCode("200");
    	result.setSuccess(true);
    	if(StringUtils.isNotBlank(astr_jysr)){
    		try {
    			RcloudActiveX.invoke(astr_jybh, astr_jysr,  v_astr_jylsh,
    					v_astr_jyyzm, v_astr_jysc_xml, v_aint_appcode, v_astr_appmsg);
			} catch (Exception e) {
				;
			}
    		Long valueOf = Long.valueOf(v_aint_appcode.toString());
			if(valueOf>=0||StringUtils.contains(RcloudActiveX.errorCodeFilter, v_aint_appcode.toString())){
    			result.setMessage("操作成功!");
			}else{
				result.setCode("400");
	        	result.setSuccess(false);
	        	result.setMessage("操作失败!"+v_aint_appcode.toString()+":"+v_astr_appmsg.toString());
			}
    	}else{
    		result.setCode("400");
        	result.setSuccess(false);
        	result.setMessage("操作失败！输入参数为空！");
    	}
    	astr_jysc_xml = v_astr_jysc_xml.toString();
    	
    	if(StringUtils.isNoneBlank(astr_jysc_xml)){
    		try {
    			Document parseText = DocumentHelper.parseText(astr_jysc_xml);
    			map = XmlUtil.Dom2Map(parseText);
    		} catch (DocumentException e) {
    			result.setCode("400");
            	result.setSuccess(false);
            	result.setMessage("操作失败！医保接口返回转换失败");
    			e.printStackTrace();
    		}
    	}
    	YinHaiResult yinHaiResult = new YinHaiResult(v_astr_jylsh.toString(),v_astr_jyyzm.toString(),astr_jysc_xml,v_aint_appcode.toString(),v_astr_appmsg.toString(),map);
    	result.setData(yinHaiResult);
    	result.setTime(new Date());
    	return result ;
    }
	/**
	 * 
	 * @author chenhuaijin
	 * @CreateTime
	 * @version 1.0.0
	 * @description
	 */
	@SuppressWarnings("serial")
	private class YinHaiServlet extends HttpServlet {
		@Override
		protected void doGet(HttpServletRequest request, //
				HttpServletResponse response) throws IOException {

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println("<h1>success</h1>");
			LogUtil.log("success");
			LocalRecord.logInfo("测试操作成功");
		}
		 /**
	     * @param astr_jybh     交易编号
	     * @param astr_jysr     交易输入
	     * @param astr_jylsh    交易流水号
	     * @param astr_jyyzm    交易验证码
	     * @param astr_jysc_xml 交易输出
	     * @param aint_appcode  交易标志
	     * @param astr_appmsg   交易信息
	     * @return 调用的结果
	     */
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
			InputStream is = null;

			is = request.getInputStream();
			StringBuilder sb = new StringBuilder();
			byte[] b = new byte[1024];
			for (int n; (n = is.read(b)) != -1;) {
				sb.append(new String(b, 0, n));
			}
			String jsonStr = sb.toString();
			JSONObject json = JSON.parseObject(jsonStr);
			String astr_jybh = json.getString("astr_jybh");
			String astr_jysr = json.getString("astr_jysr");
			String astr_jylsh = "";
			String astr_jyyzm = "";
			String astr_jysc_xml = "";
			Long aint_appcode = new Long(-100000);
			String astr_appmsg = "";
			Variant v_astr_jylsh = new Variant(astr_jylsh, true);
			Variant v_astr_jyyzm = new Variant(astr_jyyzm, true);
			Variant v_astr_jysc_xml = new Variant(astr_jysc_xml, true);
			Variant v_aint_appcode = new Variant(aint_appcode, true);
			Variant v_astr_appmsg = new Variant(astr_appmsg, true);
			Map<String, Object> map = null;
			Return<YinHaiResult> result = new Return<YinHaiResult>();
			result.setCode("200");
			result.setSuccess(true);
			if (StringUtils.isNotBlank(astr_jysr)) {
				try {
					RcloudActiveX.invoke(astr_jybh, astr_jysr, v_astr_jylsh, v_astr_jyyzm, v_astr_jysc_xml,
							v_aint_appcode, v_astr_appmsg);
				} catch (Throwable e) {//调用银海接口，错误也能支付成功，原因未知，不处理异常
					;
					
				}
				Long valueOf = Long.valueOf(v_aint_appcode.toString());
				String realCode = v_aint_appcode.toString();

				if (valueOf >= 0 || StringUtils.contains(RcloudActiveX.errorCodeFilter, realCode)) {
					result.setMessage("调用成功！");
					
				} else {
					String msg = "调用失败！" + v_aint_appcode.toString() + ":" + v_astr_appmsg.toString();
					result.setCode("400");
					result.setSuccess(false);
					result.setMessage(msg);
				}
			} else {
				String msg = "调用失败！输入参数为空！";
				result.setCode("400");
				result.setSuccess(false);
				result.setMessage(msg);
				LocalRecord.logError("调用失败！输入参数为空！");
			}
			astr_jysc_xml = v_astr_jysc_xml.toString();
			if (StringUtils.isNoneBlank(astr_jysc_xml)) {
				try {
					Document parseText = DocumentHelper.parseText(astr_jysc_xml);
					map = XmlUtil.Dom2Map(parseText);
				} catch (DocumentException e) {
					result.setCode("400");
					result.setSuccess(false);
					result.setMessage("操作失败！医保接口返回转换失败");
					LocalRecord.logError("操作失败！医保接口返回转换失败", e);
					response.setContentType("application/json;charset=utf-8");
					response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().println(JSON.toJSONString(result));
					return;
				}
			}
			YinHaiResult yinHaiResult = new YinHaiResult(v_astr_jylsh.toString(), v_astr_jyyzm.toString(),
					astr_jysc_xml, v_aint_appcode.toString(), v_astr_appmsg.toString(), map);
			result.setData(yinHaiResult);
			result.setTime(new Date());

			response.setContentType("application/json;charset=utf-8");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println(JSON.toJSONString(result));

		}
	}

}
