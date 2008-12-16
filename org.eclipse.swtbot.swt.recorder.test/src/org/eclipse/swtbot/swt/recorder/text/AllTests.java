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
package org.eclipse.swtbot.swt.recorder.text;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.swtbot.swt.recorder.widgets.text");
		//$JUnit-BEGIN$
		suite.addTestSuite(TabSelectionListenerTest.class);
		suite.addTestSuite(ShellEventListenerTest.class);
		suite.addTestSuite(PushButtonSelectionListenerTest.class);
		suite.addTestSuite(CheckBoxSelectionListenerTest.class);
		suite.addTestSuite(RadioButtonSelectionListenerTest.class);
		//$JUnit-END$
		return suite;
	}

}
