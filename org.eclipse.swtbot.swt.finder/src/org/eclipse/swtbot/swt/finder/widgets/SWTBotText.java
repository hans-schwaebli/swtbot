/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Paulin - http://swtbot.org/bugzilla/show_bug.cgi?id=36
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;



import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;

/**
 * This represents a {@link Text} widget.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotText.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
@SWTBotWidget(clasz = Text.class, preferredName = "text", referenceBy = { ReferenceBy.LABEL, ReferenceBy.TEXT, ReferenceBy.TOOLTIP })
public class SWTBotText extends AbstractSWTBot<Text> {

	private static final int	TYPE_INTERVAL	= 50;

	/**
	 * Constructs a new instance of this object.
	 *
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotText(Text w) throws WidgetNotFoundException {
		super(w);
	}

	/**
	 * Sets the text of the widget.
	 *
	 * @param text the text to be set.
	 */
	public void setText(final String text) {
		assertEnabled();
		asyncExec(new VoidResult() {
			public void run() {
				widget.setText(text);
			}
		});
	}

	/**
	 * Types the string in the text box.
	 *
	 * @param text the text to be typed.
	 * @since 1.2
	 */
	public void typeText(final String text) {
		typeText(text, TYPE_INTERVAL);
	}

	/**
	 * Types the string in the text box.
	 *
	 * @param text the text to be typed.
	 * @param interval the interval between consecutive key strokes.
	 * @since 1.2
	 */
	public void typeText(final String text, int interval) {
		log.debug(MessageFormat.format("Inserting text:{0} into text {1}", text, this));

		setFocus();
		for (int i = 0; i < text.length(); i++) {
			notifyKeyboardEvent(SWT.NONE, text.charAt(i));
			sleep(interval);
		}
	}

	/**
	 * FIXME need some work for CTRL|SHIFT + 1 the 1 is to be sent as '!' in this case.
	 *
	 * @param modificationKey the modification key.
	 * @param c the character.
	 * @see Event#character
	 * @see Event#stateMask
	 * @since 1.2
	 */
	public void notifyKeyboardEvent(int modificationKey, char c) {
		setFocus();
		notifyKeyboardEvent(modificationKey, c, 0);
	}

	/**
	 * @param modificationKey the modification key.
	 * @param c the character.
	 * @param keyCode the keycode.
	 * @see Event#keyCode
	 * @see Event#character
	 * @see Event#stateMask
	 * @since 1.2
	 */
	public void notifyKeyboardEvent(int modificationKey, char c, int keyCode) {
		log.debug(MessageFormat.format("Enquing keyboard notification: {0}", toString(modificationKey, c)));

		assertEnabled();

		notify(SWT.KeyDown, keyEvent(modificationKey, c, keyCode));
		notify(SWT.KeyUp, keyEvent(modificationKey, c, keyCode));

		setText(getText() + c);
	}

	/**
	 * @param c the character.
	 * @param modificationKey the modification key.
	 * @param keyCode the keycode.
	 * @return a key event with the specified keys.
	 * @see Event#keyCode
	 * @see Event#character
	 * @see Event#stateMask
	 * @since 1.2
	 */
	protected Event keyEvent(int modificationKey, char c, int keyCode) {
		Event keyEvent = createEvent();
		keyEvent.stateMask = modificationKey;
		keyEvent.character = c;
		keyEvent.keyCode = keyCode;

		return keyEvent;
	}

	private String toString(int modificationKey, char c) {
		String mod = "";
		if ((modificationKey & SWT.CTRL) != 0)
			mod += "SWT.CTRL + ";
		if ((modificationKey & SWT.SHIFT) != 0)
			mod += "SWT.SHIFT + ";
		int lastPlus = mod.lastIndexOf(" + ");
		if (lastPlus == (mod.length() - 3))
			mod = mod.substring(0, mod.length() - 3) + " + ";
		mod = mod + c;
		return mod;
	}
}
