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
package org.eclipse.swtbot.swt.finder.matchers;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.inGroup;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withId;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withLabel;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withRegex;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withTextIgnoringCase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import java.util.List;


import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.finders.ControlFinder;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class WidgetMatcherFactoryTest extends AbstractSWTTestCase {

    private ControlFinder	controlFinder;

    public void testMatchesControlsWithLabel() throws Exception {
        List findControls = controlFinder.findControls(withLabel("TextTransfer:"));
        assertText("some\n" + "plain\n" + "text", (Widget) findControls.get(0));
        assertText("Copy", (Widget) findControls.get(1));
    }

    public void testMatchesControlsWithRegex() throws Exception {
        List findControls = controlFinder.findControls(withRegex(".*Transfer.*"));
        assertThat(findControls.size(), is(8));
    }

    public void testMatchesControlsWithText() throws Exception {
        List findControls = controlFinder.findControls(withText("some\n" + "plain\n" + "text"));
        assertThat(findControls.size(), is(1));
        assertThat(findControls.get(0), is(Text.class));
    }

    public void testMatchesControlsWithTextIgnoringCase() throws Exception {
        List findControls = controlFinder.findControls(withTextIgnoringCase("SOME\n" + "plain\n" + "TeXt"));
        assertThat(findControls.size(), is(1));
        assertThat(findControls.get(0), is(Text.class));
    }

    public void testMatchesControlsInGroup() throws Exception {
        List findControls = controlFinder.findControls(inGroup("Paste To:"));
        assertThat(findControls.size(), is(12));
        assertThat(findControls.get(0), is(Label.class));
        assertThat(findControls.get(1), is(Text.class));
        assertThat(findControls.get(2), is(Button.class));
        assertThat(findControls.get(3), is(Label.class));
    }

    public void testFindsControlsById() throws Exception {
        final Text text = (Text) bot.widget(allOf(withLabel("TextTransfer:"), inGroup("Copy From:")));
        UIThreadRunnable.syncExec(new VoidResult(){
            public void run() {
                text.setData("foo-text", "bar");
            }
        });
        assertSameWidget(text, bot.widget(withId("foo-text", "bar")));
    }

    protected void setUp() throws Exception {
        super.setUp();
        controlFinder = new ControlFinder();
    }

    protected Shell getFocusShell() {
        return clipboardExampleShell;
    }

}
