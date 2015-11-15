package ahj.swingrouter.testapp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ahj.swingrouter.history.History;
import ahj.swingrouter.history.HistoryEvent;
import ahj.swingrouter.history.HistoryListener;
import ahj.swingrouter.router.Router;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class AppFrame extends JFrame {
	private final AppContext context;
	private final JButton back = new JButton("<");
	private final JButton forward = new JButton(">");
	private final JTextField address = new JTextField();
	
	public AppFrame() {
		super("History/Router Demonstration");

		address.setEnabled(false);
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				getContext().getHistory().back();
			}
		});
		forward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				getContext().getHistory().forward();
			}
		});
		
		JPanel buttons = new JPanel(new MigLayout("insets 0"));
		buttons.add(back);
		buttons.add(forward);
		buttons.add(address, "pushx, growx");
		
		JPanel content = new JPanel(new BorderLayout());
		
		getContentPane().add(buttons, BorderLayout.NORTH);
		getContentPane().add(content, BorderLayout.CENTER);
		
		History history = new History();
		history.addHistoryListener(new HistoryListener() {
			public void historyChanged(HistoryEvent event) {
				updateViewState(event.getUrl());
			}
		});
		
		Router router = new Router(this, history);
		router.setDefaultToken("home");
		router.setDefaultContainer(content);
		
		context = new AppContext(history, router);

		updateViewState(null);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 500);
	}
	
	public AppContext getContext() {
		return context;
	}
	
	private void updateViewState(String token) {
		address.setText(token);

		History history = getContext().getHistory();
		back.setEnabled(history.canGoBack());
		forward.setEnabled(history.canGoForward());
	}
	
	public static void main(String...args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JFrame frame = new AppFrame();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.setVisible(true);
			}
		});
	}
}