package org.rcloud.medical.clientx;

import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;
/**
 * 
 * @author      chenhuaijin
 * @CreateTime  2019年3月7日 上午10:34:40
 * @version     1.0.0
 * @description 将本地日志持久化到日志中
 */
public class LocalRecord {  
    /** 
     * 错误输入日志 
     */  
    public static final Logger log = LoggerFactory.getLogger(LocalRecord.class);  
  
    /** 
     * 记录一直 info信息 
     *  
     * @param message 
     */  
    public static void logInfo(String message) {  
        StringBuilder s = new StringBuilder();  
        s.append((message));  
        log.info(s.toString());  
    }  
  
    public static void logInfo(String message, Throwable e) {  
        StringBuilder s = new StringBuilder();  
        s.append(("exception : -->>"));  
        s.append((message));  
        log.info(s.toString(), e);  
    }  
  
    public static void logWarn(String message) {  
        StringBuilder s = new StringBuilder();  
        s.append((message));  
  
        log.warn(s.toString());  
    }  
  
    public static void logWarn(String message, Throwable e) {  
        StringBuilder s = new StringBuilder();  
        s.append(("warn : -->>"));  
        s.append((message));  
        log.warn(s.toString(), e);  
    }  
  
    public static void logDebug(String message) {  
        StringBuilder s = new StringBuilder();  
        s.append((message));  
        log.debug(s.toString());  
    }  
  
    public static void logDebug(String message, Throwable e) {  
        StringBuilder s = new StringBuilder();  
        s.append(("exception : -->>"));  
        s.append((message));  
        log.debug(s.toString(), e);  
    }  
  
    public static void logError(String message) {  
        StringBuilder s = new StringBuilder();  
        s.append(message);  
        log.error(s.toString());  
    }  
  
    /** 
     * 记录日志错误信息 
     *  
     * @param message 
     * @param e 
     */  
    public static void logError(String message, Throwable e) {  
        StringBuilder s = new StringBuilder();  
        s.append(("exception : -->>"));  
        s.append((message));  
        log.error(s.toString(), e);  
    }  
} 