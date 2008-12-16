/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.examples.helloworld;

import java.util.ResourceBundle;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/*
 * This example builds on HelloWorld1 and demonstrates how to draw directly on an SWT Control.
 */
public class HelloWorld5 {
	private static ResourceBundle	resHello	= ResourceBundle.getBundle("examples_helloworld");

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new HelloWorld5().open(display);
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}

	public Shell open(Display display) {
		final Color red = new Color(display, 0xFF, 0, 0);
		final Shell shell = new Shell(display);
		shell.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent event) {
				GC gc = event.gc;
				gc.setForeground(red);
				Rectangle rect = shell.getClientArea();
				gc.drawRectangle(rect.x + 10, rect.y + 10, rect.width - 20, rect.height - 20);
				gc.drawString(HelloWorld5.resHello.getString("Hello_world"), rect.x + 20, rect.y + 20);
			}
		});
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				red.dispose();
			}
		});
		shell.open();
		return shell;
	}
}
