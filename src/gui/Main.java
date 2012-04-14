package gui;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		Toolkit.getDefaultToolkit().getSystemEventQueue()
				.push(new EventQueue() {
					@Override
					protected void dispatchEvent(AWTEvent event) {
						if (event instanceof KeyEvent) {
							KeyEvent keyEvent = (KeyEvent) event;
							if (KeyEvent.KEY_RELEASED == keyEvent.getID()
									&& KeyEvent.VK_ESCAPE == keyEvent
											.getKeyCode()
									&& keyEvent.getSource() instanceof JDialog) {
								((JDialog) keyEvent.getSource()).dispose();
							}
						}
						super.dispatchEvent(event);
					}
				});
		new Haupt_Frame();
	}
}
