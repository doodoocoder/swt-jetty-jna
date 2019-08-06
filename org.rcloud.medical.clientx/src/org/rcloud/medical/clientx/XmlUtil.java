package org.rcloud.medical.clientx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XmlUtil {
	
	public Document ToXml(ResultSet rs) throws Exception {
		ResultSetMetaData rstr = null;
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");

		Element rsElement = document.addElement("xml");
		rstr = rs.getMetaData();

		Element fields = rsElement.addElement("Fields");
		for (int i = 1; i < rstr.getColumnCount(); i++) {
			Element field = fields.addElement("Field");
			field.addAttribute("ColName", rstr.getColumnName(i).toString());
			field.addAttribute("TypeName", rstr.getColumnTypeName(i).toString());
			field.addAttribute("MaxLength", String.valueOf(rstr.getColumnDisplaySize(i)));
		}
		String Str_Value = "";
		Element value = rsElement.addElement("rows");
		while (rs.next()) {
			for (int i = 0; i < rstr.getColumnCount(); i += 1) {
				Element fieldvalue = value.addElement("row");
				if (rs.getString(i) == null) {
					Str_Value = "";
				} else {
					Str_Value = rs.getString(i);
				}
				fieldvalue.addAttribute(rstr.getColumnName(i).toString(), Str_Value);
			}
		}
		return document;
	}

	public Document ToXml(ResultSet rs, String Filename) throws Exception {
		ResultSetMetaData rstr = null;
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");

		Element rsElement = document.addElement("xml");
		rstr = rs.getMetaData();

		Element fields = rsElement.addElement("Fields");
		for (int i = 1; i <= rstr.getColumnCount(); i++) {
			Element field = fields.addElement("Field");
			field.addAttribute("ColName", rstr.getColumnName(i).toString());
			field.addAttribute("TypeName", rstr.getColumnTypeName(i).toString());
			field.addAttribute("MaxLength", String.valueOf(rstr.getColumnDisplaySize(i)));
		}
		Element value = rsElement.addElement("rows");
		while (rs.next()) {
			for (int i = 0; i < rstr.getColumnCount(); i += 1) {
				Element fieldvalue = value.addElement("row");
				fieldvalue.addAttribute(rstr.getColumnName(i).toString(), rs.getString(i));
			}
		}
		formatXMLFile(document, Filename);
		return document;
	}

	public boolean formatXMLFile(Document document, String filename) {
		boolean returnValue = false;
		XMLWriter output = null;
		OutputFormat format = null;
		try {
			format = OutputFormat.createPrettyPrint();
			format.setEncoding("GBK");
			output = new XMLWriter(new FileWriter(new File(filename)), format);
			output.write(document);
			returnValue = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}
    @SuppressWarnings("rawtypes")
	public static Map<String, Object> Dom2Map(Document doc){ 
        Map<String, Object> map = new HashMap<String, Object>(); 
        if(doc == null) 
            return map; 
        Element root = doc.getRootElement(); 
        for (Iterator iterator = root.elementIterator(); iterator.hasNext();) { 
            Element e = (Element) iterator.next(); 
            List list = e.elements(); 
            if(list.size() > 0){ 
                map.put(e.getName(), Dom2Map(e)); 
            }else 
                map.put(e.getName(), e.getText()); 
        } 
        return map; 
    } 
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String,Object> Dom2Map(Element e){ 
        Map<String,Object> map = new HashMap<String,Object>(); 
         List list = e.elements(); 
        if(list.size() > 0){ 
            for (int i = 0;i < list.size(); i++) { 
                Element iter = (Element) list.get(i); 
                List mapList = new ArrayList(); 
                 
                if(iter.elements().size() > 0){ 
                    Map m = Dom2Map(iter); 
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName()); 
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = new ArrayList(); 
                            mapList.add(obj); 
                            mapList.add(m); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List) obj; 
                            mapList.add(m); 
                        } 
                        map.put(iter.getName(), mapList); 
                    }else 
                        map.put(iter.getName(), m); 
                } 
                else{ 
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName()); 
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = new ArrayList(); 
                            mapList.add(obj); 
                            mapList.add(iter.getText()); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List) obj; 
                            mapList.add(iter.getText()); 
                        } 
                        map.put(iter.getName(), mapList); 
                    }else 
                        map.put(iter.getName(), iter.getText()); 
                } 
            } 
        }else 
            map.put(e.getName(), e.getText()); 
        return map; 
    } 
    
}