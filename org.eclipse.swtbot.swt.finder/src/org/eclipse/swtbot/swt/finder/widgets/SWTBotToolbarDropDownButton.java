/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Ketan Padegaonkar - http://swtbot.org/bugzilla/show_bug.cgi?id=88
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.Style;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.EventContextMenuFinder;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;

/**
 * This represents a toolbar item that is a drop down button.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 1.2
 */
@SWTBotWidget(clasz = ToolItem.class, preferredName = "toolbarDropDownButton", style = @Style(name = "SWT.DROP_DOWN", value = SWT.DROP_DOWN), referenceBy = {
		ReferenceBy.MNEMONIC, ReferenceBy.TOOLTIP })
public class SWTBotToolbarDropDownButton extends SWTBotToolbarButton {

	/**
	 * Construcst an new instance of this item.
	 * 
	 * @param w the tool item.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotToolbarDropDownButton(ToolItem w) throws WidgetNotFoundException {
		this(w, null);
	}

	/**
	 * Construcst an new instance of this item.
	 * 
	 * @param w the tool item.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotToolbarDropDownButton(ToolItem w, SelfDescribing description) throws WidgetNotFoundException {
		super(w, description);
		Assert.isTrue(SWTUtils.hasStyle(w, SWT.DROP_DOWN), "Expecting a drop down button."); //$NON-NLS-1$

	}

	/**
	 * Finds the submenu inside this menu item.
	 * 
	 * @param menuItem the submenu to search
	 * @return the menu item with the specified text
	 * @throws WidgetNotFoundException if the menuItem could not be found
	 * @since 1.0
	 */
	public SWTBotMenu menuItem(String menuItem) throws WidgetNotFoundException {
		EventContextMenuFinder menuFinder = new EventContextMenuFinder();
		try {
			menuFinder.register();
			log.debug(MessageFormat.format("Clicking on {0}", this)); //$NON-NLS-1$
			assertEnabled();
			notify(SWT.MouseEnter);
			notify(SWT.MouseMove);
			notify(SWT.Activate);
			notify(SWT.FocusIn);
			notify(SWT.MouseDown);
			notify(SWT.MouseUp);
			notify(SWT.Selection, arrowEvent());
			notify(SWT.MouseHover);
			notify(SWT.MouseMove);
			notify(SWT.MouseExit);
			notify(SWT.Deactivate);
			notify(SWT.FocusOut);
			log.debug(MessageFormat.format("Clicked on {0}", this)); //$NON-NLS-1$

			Matcher<? extends Widget> matcher = withMnemonic(menuItem);
			List<?> findMenus = menuFinder.findMenus(new SWTBot().activeShell().widget, matcher, true);
			log.debug(findMenus);
			if (findMenus.isEmpty())
				throw new WidgetNotFoundException("Could not find a menu item"); //$NON-NLS-1$
			return new SWTBotMenu((MenuItem) findMenus.get(0), matcher);
		} finally {
			menuFinder.unregister();
		}
	}

	/**
	 * Gets the arrow event.
	 * 
	 * @return The event.
	 */
	private Event arrowEvent() {
		Event event = createEvent();
		event.detail = SWT.ARROW;
		return event;
	}
}
