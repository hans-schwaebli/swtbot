/*******************************************************************************
 * Copyright (c) 2008 Cedric Chabanois and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cedric Chabanois - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.IntResult;
import org.hamcrest.SelfDescribing;

/**
 * This represents a {@link CLabel} widget.
 * 
 * @author Cedric Chabanois &lt;cchabanois [at] no-log [dot] org&gt;
 * @version $Id$
 * @since 1.0
 */
@SWTBotWidget(clasz = CLabel.class, preferredName = "clabel", referenceBy = { ReferenceBy.MNEMONIC })
public class SWTBotCLabel extends AbstractSWTBot<CLabel> {

	/**
	 * Constructs an instance of this using the given finder and text to search for.
	 * 
	 * @param w the widget.
 	 * @throws WidgetNotFoundException if the widget is not found.
	 * @since 2.0
	 */
	public SWTBotCLabel(CLabel w) throws WidgetNotFoundException {
		this(w, null);
	}

	/**
	 * Constructs an instance of this using the given finder and text to search for.
	 * 
	 * @param w the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is not found.
	 * @since 2.0
	 */
	public SWTBotCLabel(CLabel w, SelfDescribing description) throws WidgetNotFoundException {
		super(w, description);
	}

	/**
	 * Return the CLabel's image or <code>null</code>.
	 * 
	 * @return the image of the label or null
	 */
	public Image image() {
		return widget.getImage();
	}

	/**
	 * Returns the alignment. The alignment style (LEFT, CENTER or RIGHT) is returned.
	 * 
	 * @return SWT.LEFT, SWT.RIGHT or SWT.CENTER
	 */
	public int alignment() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getAlignment();
			}
		});
	}

}
