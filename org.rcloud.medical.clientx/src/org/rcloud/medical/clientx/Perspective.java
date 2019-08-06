package org.rcloud.medical.clientx;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	@Override	
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.addStandaloneView(FrontView.ID, false, IPageLayout.RIGHT,
				0.8f, layout.getEditorArea());
	}
}
