/*******************************************************************************
 * Copyright (c) 2009 David Green and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     David Green - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.dsl;

import org.eclipse.swtbot.eclipse.finder.SWTEclipseBot;

/**
 * A DSL for manipulating the Eclipse IDE
 * 
 * @author David Green
 */
public class Eclipse {

	public static Workbench workbench() {
		return workbench(new SWTEclipseBot());
	}

	public static Workbench workbench(SWTEclipseBot eclipseBot) {
		return new DefaultWorkbench(eclipseBot);
	}

}
