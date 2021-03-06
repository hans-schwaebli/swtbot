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

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.bidimap.DualTreeBidiMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ArmEvent;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;

/**
 * This maps SWT events to the Strings.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public abstract class SWTBotEvents {
	private static final BidiMap	EVENTS	= new DualTreeBidiMap();
	static {
		EVENTS.put("Activate", new Integer(SWT.Activate)); //$NON-NLS-1$
		EVENTS.put("Arm", new Integer(SWT.Arm)); //$NON-NLS-1$
		EVENTS.put("Close", new Integer(SWT.Close)); //$NON-NLS-1$
		EVENTS.put("Collapse", new Integer(SWT.Collapse)); //$NON-NLS-1$
		EVENTS.put("Deactivate", new Integer(SWT.Deactivate)); //$NON-NLS-1$
		EVENTS.put("DefaultSelection", new Integer(SWT.DefaultSelection)); //$NON-NLS-1$
		EVENTS.put("Deiconify", new Integer(SWT.Deiconify)); //$NON-NLS-1$
		EVENTS.put("Dispose", new Integer(SWT.Dispose)); //$NON-NLS-1$
		EVENTS.put("DragDetect", new Integer(SWT.DragDetect)); //$NON-NLS-1$
		EVENTS.put("EraseItem", new Integer(SWT.EraseItem)); //$NON-NLS-1$
		EVENTS.put("Expand", new Integer(SWT.Expand)); //$NON-NLS-1$
		EVENTS.put("FocusIn", new Integer(SWT.FocusIn)); //$NON-NLS-1$
		EVENTS.put("FocusOut", new Integer(SWT.FocusOut)); //$NON-NLS-1$
		EVENTS.put("HardKeyDown", new Integer(SWT.HardKeyDown)); //$NON-NLS-1$
		EVENTS.put("HardKeyUp", new Integer(SWT.HardKeyUp)); //$NON-NLS-1$
		EVENTS.put("Help", new Integer(SWT.Help)); //$NON-NLS-1$
		EVENTS.put("Hide", new Integer(SWT.Hide)); //$NON-NLS-1$
		EVENTS.put("Iconify", new Integer(SWT.Iconify)); //$NON-NLS-1$
		EVENTS.put("KeyDown", new Integer(SWT.KeyDown)); //$NON-NLS-1$
		EVENTS.put("KeyUp", new Integer(SWT.KeyUp)); //$NON-NLS-1$
		EVENTS.put("MeasureItem", new Integer(SWT.MeasureItem)); //$NON-NLS-1$
		EVENTS.put("MenuDetect", new Integer(SWT.MenuDetect)); //$NON-NLS-1$
		EVENTS.put("Modify", new Integer(SWT.Modify)); //$NON-NLS-1$
		EVENTS.put("MouseDoubleClick", new Integer(SWT.MouseDoubleClick)); //$NON-NLS-1$
		EVENTS.put("MouseDown", new Integer(SWT.MouseDown)); //$NON-NLS-1$
		EVENTS.put("MouseEnter", new Integer(SWT.MouseEnter)); //$NON-NLS-1$
		EVENTS.put("MouseExit", new Integer(SWT.MouseExit)); //$NON-NLS-1$
		EVENTS.put("MouseHover", new Integer(SWT.MouseHover)); //$NON-NLS-1$
		EVENTS.put("MouseMove", new Integer(SWT.MouseMove)); //$NON-NLS-1$
		EVENTS.put("MouseUp", new Integer(SWT.MouseUp)); //$NON-NLS-1$
		EVENTS.put("MouseWheel", new Integer(SWT.MouseWheel)); //$NON-NLS-1$
		EVENTS.put("Move", new Integer(SWT.Move)); //$NON-NLS-1$
		EVENTS.put("Paint", new Integer(SWT.Paint)); //$NON-NLS-1$
		EVENTS.put("PaintItem", new Integer(SWT.PaintItem)); //$NON-NLS-1$
		EVENTS.put("Resize", new Integer(SWT.Resize)); //$NON-NLS-1$
		EVENTS.put("Selection", new Integer(SWT.Selection)); //$NON-NLS-1$
		EVENTS.put("SetData", new Integer(SWT.SetData)); //$NON-NLS-1$
		EVENTS.put("Settings", new Integer(SWT.Settings)); // note: this event only goes to Display //$NON-NLS-1$
		EVENTS.put("Show", new Integer(SWT.Show)); //$NON-NLS-1$
		EVENTS.put("Traverse", new Integer(SWT.Traverse)); //$NON-NLS-1$
		EVENTS.put("Verify", new Integer(SWT.Verify)); //$NON-NLS-1$
	}

	/**
	 * Converts the event to a string for display.
	 * 
	 * @param event the event.
	 * @return the string representation of the display.
	 */
	public static String toString(int event) {
		return (String) EVENTS.getKey(new Integer(event));
	}

	/**
	 * Lists all the events.
	 * 
	 * @return all the events.
	 */
	public static int[] events() {
		int[] events = new int[EVENTS.size()];
		int i = 0;

		MapIterator it = EVENTS.mapIterator();
		while (it.hasNext()) {
			it.next();
			Integer value = (Integer) it.getValue();
			events[i++] = value.intValue();
		}

		return events;
	}

	/**
	 * @param event the event to be converted to a string.
	 * @return the typed event corresponding to the <code>event</code>.
	 */
	public static String toString(Event event) {
		String toString = toString(event.type) + " [" + event.type + "]: "; //$NON-NLS-1$ //$NON-NLS-2$
		switch (event.type) {
		case SWT.KeyDown:
		case SWT.KeyUp:
			toString += new KeyEvent(event).toString();
			break;
		case SWT.MouseDown:
		case SWT.MouseUp:
		case SWT.MouseMove:
		case SWT.MouseEnter:
		case SWT.MouseExit:
		case SWT.MouseDoubleClick:
		case SWT.MouseWheel:
		case SWT.MouseHover:
			toString += new MouseEvent(event).toString();
			break;
		case SWT.Paint:
			toString += new PaintEvent(event).toString();
			break;
		case SWT.Move:
		case SWT.Resize:
			toString += new ControlEvent(event).toString();
			break;
		case SWT.Dispose:
			toString += new DisposeEvent(event).toString();
			break;
		case SWT.Selection:
		case SWT.DefaultSelection:
			toString += new SelectionEvent(event).toString();
			break;
		case SWT.FocusIn:
		case SWT.FocusOut:
			toString += new FocusEvent(event).toString();
			break;
		case SWT.Expand:
		case SWT.Collapse:
			toString += new TreeEvent(event).toString();
			break;
		case SWT.Iconify:
		case SWT.Deiconify:
		case SWT.Close:
		case SWT.Activate:
		case SWT.Deactivate:
			toString += new ShellEvent(event).toString();
			break;
		case SWT.Show:
		case SWT.Hide:
			toString += event.widget instanceof Menu ? new MenuEvent(event).toString() : event.toString();
			break;
		case SWT.Modify:
			toString += new ModifyEvent(event).toString();
			break;
		case SWT.Verify:
			toString += new VerifyEvent(event).toString();
			break;
		case SWT.Help:
			toString += new HelpEvent(event).toString();
			break;
		case SWT.Arm:
			toString += new ArmEvent(event).toString();
			break;
		case SWT.Traverse:
			toString += new TraverseEvent(event).toString();
			break;
		case SWT.HardKeyDown:
		case SWT.HardKeyUp:
		case SWT.DragDetect:
		case SWT.MenuDetect:
		case SWT.SetData:
		default:
			toString += event.toString();
		}
		return toString;
	}
}
