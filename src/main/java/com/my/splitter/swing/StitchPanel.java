package com.my.splitter.swing;

import javax.swing.AbstractListModel;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ListModel;
import javax.swing.border.EtchedBorder;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import com.my.splitter.file.FileUtils;
import com.my.splitter.swing.model.FileListModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class StitchPanel extends JPanel {
	private JTextField textField;
	private final JFileChooser fc = new JFileChooser();
	FileListModel listModel = new FileListModel();
	private final JList fileList = new JList(listModel);
	

	/**
	 * Create the panel.
	 */
	public StitchPanel() {
		setBounds(10, 26, 545, 273);
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 6, 425, 20);
		textField.setColumns(10);
		add(textField);
		
		JButton button = new JButton("Browse");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fc.showDialog(StitchPanel.this, "Pick up base file") == JFileChooser.APPROVE_OPTION) {
					populateFileList(fc.getSelectedFile());
				}
			}
		});
		button.setBounds(445, 5, 90, 23);
		add(button);
		
		JPanel fileInfoPanel = new JPanel();
		fileInfoPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		fileInfoPanel.setBounds(10, 37, 525, 225);
		fileInfoPanel.setLayout(null);
		add(fileInfoPanel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 200, 178);
		fileInfoPanel.add(scrollPane);
		
		scrollPane.setViewportView(fileList);
		
		JLabel lblPartsList = new JLabel("Parts list");
		lblPartsList.setBounds(28, 11, 65, 14);
		fileInfoPanel.add(lblPartsList);
		
		JButton btnStitch = new JButton("Stitch");
		btnStitch.setBounds(426, 191, 89, 23);
		fileInfoPanel.add(btnStitch);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(220, 36, 89, 23);
		fileInfoPanel.add(btnAdd);
		
		JButton button_1 = new JButton("Remove");
		button_1.setBounds(220, 70, 89, 23);
		fileInfoPanel.add(button_1);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(220, 104, 89, 23);
		fileInfoPanel.add(btnClear);
		

	}


	private void populateFileList(File selectedFile) {
		List<Path> list = FileUtils.guessFileList(selectedFile.toPath());
		listModel.setList(list);
	}
}
