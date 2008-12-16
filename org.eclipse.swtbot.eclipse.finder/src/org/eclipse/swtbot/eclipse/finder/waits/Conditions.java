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
package org.eclipse.swtbot.eclipse.finder.waits;

import org.hamcrest.Matcher;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: Conditions.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public class Conditions extends org.eclipse.swtbot.swt.finder.waits.Conditions {
	/**
	 * @param matcher a matcher
	 * @return a condition that waits until the matcher evaluates to true.
	 */
	public static WaitForPart waitForPart(Matcher<?> matcher) {
		return new WaitForPart(matcher);
	}
}
