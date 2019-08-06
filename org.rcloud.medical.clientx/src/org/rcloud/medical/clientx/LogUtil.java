package org.rcloud.medical.clientx;

import java.util.Date;
/**
 * 
 * @author      chenhuaijin
 * @CreateTime  2019年3月5日 上午1:06:54
 * @version     1.0.0
 * @description 记录错误的日志，并反馈到前台，要在初始化界面以后调用
 */
public class LogUtil {
	public static void log(String msg) {
		String date = RcloudActiveX.formatter.format(new Date());
    	Biz biz = new Biz();
		biz.setDate(date);
		biz.setEvent(msg);
		BizModel.add(biz);
	}

}
