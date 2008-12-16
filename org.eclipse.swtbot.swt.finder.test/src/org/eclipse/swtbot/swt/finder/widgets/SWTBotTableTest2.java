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
package org.eclipse.swtbot.swt.finder.widgets;


import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotTableTest2.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public class SWTBotTableTest2 extends AbstractSWTTestCase {

	private static final String	FIRST_NAME	= "First Name";
	private static final String	LAST_NAME	= "Last Name";
	private SWTBot				bot;
	private SWTBotTable			table;

	public void testFindsTableHeader() throws Exception {
		Widget tableHeader = table.header(LAST_NAME).widget;
		assertEquals(TableColumn.class, tableHeader.getClass());
		assertText(LAST_NAME, tableHeader);
	}

	public void testClicksTableHeader() throws Exception {
		table.header(LAST_NAME).click();
		assertEquals("last2", table.cell(0, LAST_NAME));
		assertEquals("last4", table.cell(1, LAST_NAME));
		assertEquals("last6", table.cell(2, LAST_NAME));

		table.header(LAST_NAME).click();
		assertEquals("last6", table.cell(0, LAST_NAME));
		assertEquals("last4", table.cell(1, LAST_NAME));
		assertEquals("last2", table.cell(2, LAST_NAME));

		table.header(FIRST_NAME).click();
		assertEquals("first1", table.cell(0, FIRST_NAME));
		assertEquals("first2", table.cell(1, FIRST_NAME));
		assertEquals("first3", table.cell(2, FIRST_NAME));

		table.header(FIRST_NAME).click();
		assertEquals("first3", table.cell(0, FIRST_NAME));
		assertEquals("first2", table.cell(1, FIRST_NAME));
		assertEquals("first1", table.cell(2, FIRST_NAME));
	}

	protected void setUp() throws Exception {
		super.setUp();
		populateData();
		bot = new  SWTBot();
		table = bot.table();
	}

	protected Shell getFocusShell() {
		return menuShell;
	}

	private void populateData() {
		display.syncExec(new Runnable() {
			public void run() {
				menuExample.clearAddressbook();
				menuExample.addAddressBook(row1());
				menuExample.addAddressBook(row2());
				menuExample.addAddressBook(row3());
			}
		});
	}

	private String[] row1() {
		return new String[] { "last2", "first1", "business phone3", "home phone2", "email@addres.ss", "fax number" };
	}

	private String[] row2() {
		return new String[] { "last6", "first2", "business phone2", "home phone1", "email@addres.ss", "fax number" };
	}

	private String[] row3() {
		return new String[] { "last4", "first3", "business phone1", "home phone3", "email@addres.ss", "fax number" };
	}
}
