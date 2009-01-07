/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.resolvers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Widget;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class TabItemResolver implements IChildrenResolver, IParentResolver {

	public boolean canResolve(Widget w) {
		return w instanceof TabItem;
	}

	public List getChildren(Widget w) {
		ArrayList children = new ArrayList();
		children.add(((TabItem) w).getControl());
		return children;
	}

	public Widget getParent(Widget w) {
		return (canResolve(w)) ? ((TabItem) w).getParent() : null;
	}

	public Class[] getResolvableClasses() {
		return new Class[] { TabItem.class };
	}

	public boolean hasChildren(Widget w) {
		return (canResolve(w)) && ((TabItem) w).getControl() != null;
	}

	public boolean hasParent(Widget w) {
		return getParent(w) != null;
	}

}
