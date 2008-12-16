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
package org.eclipse.swtbot.swt.finder.utils;

import junit.framework.TestCase;

public class TableCollectionTest extends TestCase  {

	public void testHashCodeIsSameForSameTableCollections() throws Exception {
		assertEquals(emptyTableCollection().hashCode(), emptyTableCollection().hashCode());
		assertEquals(collection().hashCode(), collection().hashCode());
	}

	public void testEqualsForSameTableCollections() throws Exception {
		assertTrue(emptyTableCollection().equals(emptyTableCollection()));
		assertTrue(collection().equals(collection()));

		final TableCollection collection = collection();
		assertTrue(collection.equals(collection));

		assertFalse(emptyTableCollection().equals(collection()));
		assertFalse(collection().equals(null));
		assertFalse(collection().equals(this));
	}

	public void testToString() throws Exception {
		assertEquals("[Hello, world]\n" + "[goodbye, world]\n", collection().toString());
	}

	private TableCollection emptyTableCollection() {
		return new TableCollection();
	}

	private TableCollection collection() {
		return new TableCollection(new TableRow[] { new TableRow(new String[] { "Hello", "world" }),
				new TableRow(new String[] { "goodbye", "world" }) });
	}
}
