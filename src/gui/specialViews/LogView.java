package gui.specialViews;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;



public class LogView extends JDialog {
	private JTextArea t;

	public LogView(JFrame owner, String text) {
		super(owner, "Log", true);
		setSize(500, 300);
		setLocationRelativeTo(owner);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		t = new JTextArea(text);
		t.setFont(new Font("Courier New", Font.PLAIN, 12));
		t.setEditable(false);
		t.setLineWrap(true);
		t.setWrapStyleWord(true);

		JScrollPane p = new JScrollPane(t);
		p.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		p.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(p);

		setVisible(true);
	}
}
