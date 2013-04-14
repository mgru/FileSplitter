package com.my.splitter;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.my.splitter.file.Splitter;

public class Main {

	private JFrame frame;
	private JTextField textField;
	private final JFileChooser fc = new JFileChooser();

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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(43, 90, 10, 10);
		contentPane.add(panel);

		textField = new JTextField();
		textField.setBounds(10, 11, 318, 20);
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
		btnBrowse.setBounds(338, 10, 89, 23);
		contentPane.add(btnBrowse);
	}
}
