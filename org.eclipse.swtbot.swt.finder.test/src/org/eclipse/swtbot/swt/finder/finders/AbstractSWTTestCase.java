/*******************************************************************************
 * Copyright 2007 new SWTBot, http://swtbot.org/
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.finders;



import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.examples.addressbook.AddressBook;
import org.eclipse.swt.examples.clipboard.ClipboardExample;
import org.eclipse.swt.examples.controlexample.ControlExample;
import org.eclipse.swt.examples.controlexample.CustomControlExample;
import org.eclipse.swt.examples.dnd.DNDExample;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swtbot.swt.finder.SWTBotTestCase;
import org.eclipse.swtbot.swt.finder.alltests.Controls;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: AbstractSWTTestCase.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public abstract class AbstractSWTTestCase extends SWTBotTestCase {

	static {
		// System.setProperty(LogFactory.DIAGNOSTICS_DEST_PROPERTY, "STDOUT");
	}
	public static final Display					display					= Controls.getInstance().display;
	public static final Shell					controlShell			= Controls.getInstance().controlShell;
	public static final ControlExample			controlExample			= Controls.getInstance().controlExample;

	public static final Shell					menuShell				= Controls.getInstance().menuShell;
	public static final AddressBook				menuExample				= Controls.getInstance().menuExample;

	public static final Shell					customControlShell		= Controls.getInstance().customControlShell;
	public static final CustomControlExample	customControlExample	= Controls.getInstance().customControlExample;

	public static final Shell					clipboardExampleShell	= Controls.getInstance().clipboardExampleShell;
	public static final ClipboardExample		clipboardExample		= Controls.getInstance().clipboardExample;

	public static final Shell					dndShell				= Controls.getInstance().dndShell;
	public static final DNDExample				dndExample				= Controls.getInstance().dndExample;

	public static final Thread					UIThread				= Controls.getInstance().UIThread;

	protected final Logger						log;

	public AbstractSWTTestCase() {
		super();
		log = Logger.getLogger(getClass());
	}

	protected void setUp() throws Exception {
		log.debug(MessageFormat.format("Executing test: {0}#{1}", getClass(), getName()));
		log.debug(MessageFormat.format("Activating shell: {0}", SWTUtils.toString(getFocusShell())));
		SWTBotShell shell = new SWTBotShell(getFocusShell());
		shell.activate();
		shell.setFocus();
		//
		// display.syncExec(new Runnable() {
		// public void run() {
		// new SWTBot
		// getFocusShell().setActive();
		// }
		// });
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		log.debug(MessageFormat.format("Finished executing test: {0}#{1}", getClass(), getName()));
	}

	protected Shell getFocusShell() {
		return controlShell;
	}

	protected static boolean contains(String needle, String hayStack) {
		return (hayStack.indexOf(needle) >= 0);
	}

	protected Shell createShell(final String text) {
		return UIThreadRunnable.syncExec(new WidgetResult<Shell>() {
			public Shell run() {
				Shell shell = new Shell(display);
				shell.setText(text);
				shell.setLayout(new GridLayout(1, false));
				shell.open();
				return shell;
			}
		});
	}

	protected Table createTable(final Shell shell) {
		return UIThreadRunnable.syncExec(new WidgetResult<Table>() {
			public Table run() {
				Table table = new Table(shell, SWT.SINGLE | SWT.FULL_SELECTION);
				table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				table.setLinesVisible(true);
				table.setHeaderVisible(true);

				for (int i = 0; i < table.getColumnCount(); i++) {
					table.getColumn(i).pack();
				}
				shell.layout(true);
				return table;
			}
		});
	}

}
