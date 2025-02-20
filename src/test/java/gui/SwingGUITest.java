package gui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import states.Context;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Unit test for the SwingGUI class.
 * This class tests the initialization of the SwingGUI and ensures that
 * the buttons and labels are correctly instantiated and added to the GUI.
 */
class SwingGUITest {

    private SwingGUI swingGUI;

    /**
     * Sets up the environment for each test.
     * Initializes a new instance of SwingGUI and the associated context.
     */
    @BeforeEach
    void setUp() {
        Context context = new Context();

        // Initialize SwingGUI with the context
        swingGUI = new SwingGUI(context);
        swingGUI.initGUI();
    }

    /**
     * Cleans up after each test.
     * Disposes of any open windows to avoid resource leaks.
     */
    @AfterEach
    void tearDown() {
        // Close all open windows after each test
        for (java.awt.Window window : java.awt.Window.getWindows()) {
            window.dispose();
        }
    }

    /**
     * Tests the initialization of the GUI components.
     * Ensures that the buttons and labels are properly created.
     */
    @Test
    void testInitGUI() {
        // Assert that the buttons and labels are initialized
        assertNotNull(swingGUI.b1, "Button b1 should be initialized");
        assertNotNull(swingGUI.b2, "Button b2 should be initialized");
        assertNotNull(swingGUI.b3, "Button b3 should be initialized");
        assertNotNull(swingGUI.myText1, "Label myText1 should be initialized");
        assertNotNull(swingGUI.myText2, "Label myText2 should be initialized");
        assertNotNull(swingGUI.myText3, "Label myText3 should be initialized");

        // Assert that the buttons are instances of JButton
        assertInstanceOf(JButton.class, swingGUI.b1, "b1 should be a JButton");
        assertInstanceOf(JButton.class, swingGUI.b2, "b2 should be a JButton");
        assertInstanceOf(JButton.class, swingGUI.b3, "b3 should be a JButton");

        // Assert that the labels are instances of JLabel
        assertInstanceOf(JLabel.class, swingGUI.myText1, "myText1 should be a JLabel");
        assertInstanceOf(JLabel.class, swingGUI.myText2, "myText2 should be a JLabel");
        assertInstanceOf(JLabel.class, swingGUI.myText3, "myText3 should be a JLabel");
    }
}
