package com.my.splitter;

import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Window.Type;

public class Main {

	private JFrame frame;
	private JTextField textField;

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
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(43, 90, 10, 10);
		frame.getContentPane().add(panel);
		
		textField = new JTextField();
		textField.setBounds(10, 11, 318, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(338, 10, 89, 23);
		frame.getContentPane().add(btnBrowse);
	}
}
