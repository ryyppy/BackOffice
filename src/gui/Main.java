package gui;

import java.io.File;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import logging.ConsoleAdapter;
import logging.FileAdapter;
import logging.Logger;
import logging.LoggerManager;
import logging.LoggingLevel;

public class Main {
	
	public static void main(String[] args) {
		Logger l = new Logger();
		try {
			l.addAdapter(new FileAdapter(new File("log"), "xmlImport"));
			//TODO: auf config ueberpruefen
			l.addAdapter(new ConsoleAdapter());
			
			LoggerManager.registerLogger("xmlImport", l);
			
			//TODO: auf config ueberpruefen
			LoggerManager.setLoggingLevel(LoggingLevel.DEBUG);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
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
//		Toolkit.getDefaultToolkit().getSystemEventQueue()
//				.push(new EventQueue() {
//					@Override
//					protected void dispatchEvent(AWTEvent event) {
//						if (event instanceof KeyEvent) {
//							KeyEvent keyEvent = (KeyEvent) event;
//							if (KeyEvent.KEY_RELEASED == keyEvent.getID()
//									&& KeyEvent.VK_ESCAPE == keyEvent
//											.getKeyCode()
//									&& keyEvent.getSource() instanceof JDialog) {
//								JDialog d = ((JDialog) keyEvent.getSource());
//									d.dispose();
//							}
//						}
//						super.dispatchEvent(event);
//					}
//				});
		new HauptFrame();
	}
}
