package org.rcloud.medical.clientx;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;

public class ViewContentProvider implements IStructuredContentProvider {
	BizModel input;
	ListViewer view;

	@Override
	public Object[] getElements(Object inputElement) {
		return input.elements().toArray();
		
	}
	
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {//
		viewer = (ListViewer)viewer;
		input = (BizModel)newInput;
	}
	
	

}
