package com.my.splitter;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.my.splitter.file.Splitter;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;
import javax.swing.JToggleButton;

public class Main {

	private JFrame frame;
	private JTextField textField;
	private final JFileChooser fc = new JFileChooser();
	private final JToggleButton tglbtnSplit = new JToggleButton("Split");
	private final JToggleButton tglbtnStich = new JToggleButton("Stich");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 571, 338);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(43, 90, 10, 10);
		contentPane.add(panel);

		textField = new JTextField();
		textField.setBounds(10, 28, 446, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = fc.showDialog(frame, "Pick up the file to split");
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					JOptionPane.showMessageDialog(frame,
							"File: " + file.toPath());
					new Splitter(file).split(50);
					
				}
			}
		});
		btnBrowse.setBounds(466, 27, 89, 23);
		contentPane.add(btnBrowse);
		
		JPanel fileInfoPanel = new JPanel();
		fileInfoPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		fileInfoPanel.setBounds(10, 54, 545, 245);
		frame.getContentPane().add(fileInfoPanel);
		fileInfoPanel.setLayout(null);
		
		JButton splitButton = new JButton("Split");
		splitButton.setBounds(462, 211, 73, 23);
		fileInfoPanel.add(splitButton);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBounds(10, 1, 545, 23);
		frame.getContentPane().add(toolBar);
		
		tglbtnSplit.setSelected(true);
		tglbtnSplit.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					tglbtnStich.setSelected(false);
				}
			}
		});
		toolBar.add(tglbtnSplit);
		
		tglbtnStich.setSelected(false);
		tglbtnStich.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					tglbtnSplit.setSelected(false);
				}
			}
		});
		toolBar.add(tglbtnStich);
	}
}
