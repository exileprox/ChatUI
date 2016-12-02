/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import chatclient.ChatClientMainFrame;
import g53sqm.chat.server.Server;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import org.assertj.swing.core.BasicComponentFinder;
import org.assertj.swing.core.ComponentFinder;
import org.assertj.swing.core.Settings;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.exception.ComponentLookupException;
import static org.assertj.swing.finder.WindowFinder.findFrame;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Chris
 */
public class IntegrationTest {

    private FrameFixture window;

    @Before
    public void setUp() throws Exception {
        ChatClientMainFrame frame = GuiActionRunner.execute(() -> new ChatClientMainFrame());
        window = new FrameFixture(frame);
        window.show();
    }

    @Test
    public void shouldShowErrorWhenPortNumberIsNotNonNegativeNumber() {

        window.textBox("jTextFieldHost").deleteText();
        window.textBox("jTextFieldPort").deleteText();
        window.textBox("jTextFieldHost").enterText("Some random text");
        window.textBox("jTextFieldPort").enterText("Some random text");
        window.button("jbuttonConnect").click();
        System.out.println((window.textBox("jTextPaneStatus").text()));
        String text = window.textBox("jTextPaneStatus").text();
        assertEquals("Port number must be a non-negative number", text.trim());
    }

    @Test
    public void shouldShowWelcomeWhenSuccessfullyConnectToServer() {
        Server server = new Server(9090);
        try {
            server.initializeSocketServer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        server.run();

        window.textBox("jTextFieldHost").deleteText();
        window.textBox("jTextFieldPort").deleteText();
        window.textBox("jTextFieldHost").enterText("localhost");
        window.textBox("jTextFieldPort").enterText("9090");
        window.button("jbuttonConnect").click();
        String text = window.textBox("jTextPaneStatus").text();
        assertEquals("Welcome to the chat server, there are currelty 1 user(s) online", text.trim());
    }

    @Test
    public void shouldShowUserNameWhenListCommandIsInvoked() {
        Server server = new Server(9091);
        try {
            server.initializeSocketServer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        server.run();

        window.textBox("jTextFieldHost").deleteText();
        window.textBox("jTextFieldPort").deleteText();
        window.textBox("jTextFieldHost").enterText("localhost");
        window.textBox("jTextFieldPort").enterText("9091");
        window.button("jbuttonConnect").click();
        window.textBox("jTextFieldName").deleteText();
        window.textBox("jTextFieldName").enterText("Chris");
        window.button("jButtonRegister").click();
        window.comboBox("jComboBoxCommand").selectItem(0);
        window.button("jButtonSend").click();
        String text = window.textBox("jTextPaneStatus").text();
        assertEquals("Chris,", text.trim());
    }

    @After
    public void tearDown() throws Exception {
        window.cleanUp();
    }

}
