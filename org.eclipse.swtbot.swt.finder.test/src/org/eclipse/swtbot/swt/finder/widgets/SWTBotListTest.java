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


import org.eclipse.swt.widgets.List;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotListTest.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public class SWTBotListTest extends AbstractSWTTestCase {

	private SWTBot	bot;

	public void testNotFindingListByNameThrowsException() throws Exception {
		try {
			bot.listInGroup("some list");
			fail("Was expecting a WidgetNotFoundException");
		} catch (WidgetNotFoundException expected) {
			pass();
		}
	}

	public void testNotFindingListThrowsException() throws Exception {
		bot.tabItem("Button").activate();
		try {
			bot.list();
			fail("Was expecting a WidgetNotFoundException");
		} catch (WidgetNotFoundException expected) {
			pass();
		}
	}

	public void testFindsAListWithoutLabel() throws Exception {
		SWTBotList list = bot.list();
		assertNotNull(list.widget);
		assertEquals(List.class, list.widget.getClass());
	}

	public void testFindsAListWithALabel() throws Exception {
		SWTBotList list = bot.listInGroup("List");
		assertNotNull(list.widget);
		assertEquals(List.class, list.widget.getClass());

		bot.tabItem("Button").activate();
		try {
			bot.listInGroup("List");
			fail("Expecting a WidgetNotFoundException");
		} catch (WidgetNotFoundException expected) {
			pass();
		}
	}

	public void testSetsAndGetsSingleSelectionByText() throws Exception {
		SWTBotList list = bot.listInGroup("List");
		list.select("Bananas");

		assertEquals(1, list.selectionCount());
		assertEquals("Bananas", list.selection()[0]);
	}

	public void testSetsAndGetsSingleSelectionByIndex() throws Exception {
		SWTBotList list = bot.listInGroup("List");
		list.select(3);

		assertEquals(1, list.selectionCount());
		assertEquals("Grapefruit", list.selection()[0]);
	}

	public void testSetsAndGetsMultipleSelectionByText() throws Exception {
		bot.radio("SWT.MULTI").click();
		SWTBotList list = bot.listInGroup("List");
		list.select(new String[] { "Grapefruit", "Peaches", "Apricots" });

		assertEquals(3, list.selectionCount());
		assertEquals("Grapefruit", list.selection()[0]);
		assertEquals("Peaches", list.selection()[1]);
		assertEquals("Apricots", list.selection()[2]);
	}

	public void testSetsAndGetsMultipleSelectionByIndex() throws Exception {
		bot.radio("SWT.MULTI").click();
		SWTBotList list = bot.listInGroup("List");
		list.select(new int[] { 2, 4, 6 });

		assertEquals(3, list.selectionCount());
		assertEquals("Bananas", list.selection()[0]);
		assertEquals("Peaches", list.selection()[1]);
		assertEquals("Apricots", list.selection()[2]);
	}

	public void testUnSelectsSelection() throws Exception {
		SWTBotList list = bot.listInGroup("List");
		list.select(3);
		assertEquals(1, list.selectionCount());
		list.unselect();
		assertEquals(0, list.selectionCount());
	}

	public void testGetsIndexOfItem() throws Exception {
		SWTBotList list = bot.listInGroup("List");
		assertEquals(2, list.indexOf("Bananas"));
	}

	public void testGetsItemAtIndex() throws Exception {
		SWTBotList list = bot.listInGroup("List");
		assertEquals("Bananas", list.itemAt(2));
	}

	public void testSelectionNotifiesListeners() throws Exception {
		SWTBotList list = bot.listInGroup("List");
		bot.checkBox("Listen").select();
		bot.button("Clear").click();

		list = bot.listInGroup("List");
		SWTBotText text = bot.textInGroup("Listeners");
		assertText("", text);

		list.select(3);
		assertTextContains("Selection [13]: SelectionEvent{List {} ", text.widget);
		assertTextContains("MouseUp [4]: MouseEvent{List {} ", text.widget);
		assertTextContains("MouseDown [3]: MouseEvent{List {} ", text.widget);
	}

	public void testDeSelectNotifiesListeners() throws Exception {
		SWTBotList list = bot.listInGroup("List");
		list.select(3);
		bot.checkBox("Listen").select();
		bot.button("Clear").click();

		list = bot.listInGroup("List");
		SWTBotText text = bot.textInGroup("Listeners");
		assertText("", text);

		list.unselect();
		assertTextContains("Selection [13]: SelectionEvent{List {} ", text.widget);
		assertTextContains("MouseUp [4]: MouseEvent{List {} ", text.widget);
		assertTextContains("MouseDown [3]: MouseEvent{List {} ", text.widget);
	}

	public void testThrowsExceptionInCaseOfInvalidIndexBasedSelection() throws Exception {
		SWTBot bot = new SWTBot();
		SWTBotList list = bot.listInGroup("List");
		try {
			list.select(100);
			fail("Was expecting an exception");
		} catch (RuntimeException e) {
			assertEquals("assertion failed: The index (100) is more than the number of items (9) in the list.", e.getMessage());
		}
	}

	public void testThrowsExceptionInCaseOfInvalidTextBasedSelection() throws Exception {
		SWTBot bot = new SWTBot();
		SWTBotList list = bot.listInGroup("List");
		try {
			list.select("non existent item");
			fail("Was expecting an exception");
		} catch (RuntimeException e) {
			assertEquals("assertion failed: Item `non existent item' not found in list.", e.getMessage());
		}
	}

	protected void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
		bot.tabItem("List").activate();
	}

}
