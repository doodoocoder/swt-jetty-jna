package org.rcloud.medical.clientx;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * 
 * @author      chenhuaijin
 * @CreateTime  2019年3月7日 上午9:24:46
 * @version     1.0.0
 * @description 监听数据变化
 */
public class FrontView extends ViewPart {
	public static final String ID = "org.rcloud.medical.clientx.front";
	private  ListViewer listViewer;

	public FrontView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		listViewer = new ListViewer(parent, SWT.BORDER | SWT.MULTI
				| SWT.V_SCROLL);
		getSite().setSelectionProvider(listViewer);
		listViewer.setLabelProvider(new ViewerLabelProvider());
		listViewer.setContentProvider(new ViewContentProvider());
		listViewer.setInput(new BizModel());	
		
	}

	@Override
	public void setFocus() {

	}

	

}
