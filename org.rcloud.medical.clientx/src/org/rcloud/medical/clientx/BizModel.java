package org.rcloud.medical.clientx;

import java.util.ArrayList;
import java.util.List;

public class BizModel {
	private static List<Biz> FROINT_TEXT = new ArrayList<Biz>();//
	
	
	
	
	public List<Biz> elements(){
		
		return FROINT_TEXT;
	}
	
	public static void add(Biz biz) {
		FROINT_TEXT.add(biz);
		
	}
	

}
