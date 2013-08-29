package com.my.splitter.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;

import com.my.splitter.file.FileUtils;
import com.my.splitter.file.OperationResult;
import com.my.splitter.file.Stitcher;
import com.my.splitter.file.notify.Notifier;
import com.my.splitter.file.notify.ProgressBarAdapter;
import com.my.splitter.file.notify.StitchInformer;
import com.my.splitter.swing.model.PathObject;


/**
 * Stitcher GUI panel
 * @author mgruzman
 *
 */
@SuppressWarnings("serial")
public class StitchPanel extends JPanel {
	private static final String FILENAME_PATTERN = "^([a-zA-Z0-9\\s\\._-]+)$";
	private static final String DEFAULTNAME = "bulkfile";
	private JTextField textField;
	private final JFileChooser fc = new JFileChooser();
	private final DefaultListModel<PathObject> listModel = new DefaultListModel<PathObject>();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final JList fileList = new JList(listModel);
	private final JButton btnClear = new JButton("Clear");
	private final JButton removeBtn = new JButton("Remove");
	private final JButton btnStitch = new JButton("Stitch");
	private final JProgressBar progressBar = new JProgressBar();
	private JTextField resultingNameText;
	


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
				if(fc.showDialog(StitchPanel.this, "Select") == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fc.getSelectedFile();
					textField.setText(selectedFile.getPath());
					populateFileList(selectedFile);
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
		
		listModel.addListDataListener(new ChunckListListener(listModel, btnClear, btnStitch));
		fileList.addListSelectionListener(new FileListSelectionListener(removeBtn));
		scrollPane.setViewportView(fileList);
		
		JLabel lblPartsList = new JLabel("Parts list");
		lblPartsList.setBounds(28, 11, 65, 14);
		fileInfoPanel.add(lblPartsList);
		btnStitch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStitch.setEnabled(false);
				if(!validateResultingName()) {
					JOptionPane.showMessageDialog(getParent(), "Not a valid resulting file name!");
					btnStitch.setEnabled(true);
					return;
				}
				progressBar.setVisible(true);
				stitchFiles();
			}
		});
		
		btnStitch.setEnabled(false);
		btnStitch.setBounds(426, 191, 89, 23);
		fileInfoPanel.add(btnStitch);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fc.showDialog(StitchPanel.this, "Add") == JFileChooser.APPROVE_OPTION) {
					int index = fileList.getSelectedIndex();
					if(index < 0) { index = listModel.getSize(); } else { index += 1; }
					listModel.insertElementAt(new PathObject(fc.getSelectedFile().toPath()), index);
				}
			}
		});
		btnAdd.setBounds(220, 36, 89, 23);
		fileInfoPanel.add(btnAdd);
		
		removeBtn.setEnabled(false);
		removeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = fileList.getSelectedIndex();
				if(index >= 0) {
					listModel.removeElementAt(index);
					resetPanel();
				}
			}
		});
		removeBtn.setBounds(220, 70, 89, 23);
		fileInfoPanel.add(removeBtn);
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetPanel();
				listModel.clear();
			}
		});
		btnClear.setBounds(220, 104, 89, 23);
		btnClear.setEnabled(false);
		fileInfoPanel.add(btnClear);
		
		progressBar.setVisible(false);
		progressBar.setBounds(220, 191, 184, 14);
		fileInfoPanel.add(progressBar);
		
		JLabel resultingFileLabel = new JLabel("Resulting file name");
		resultingFileLabel.setBounds(240, 160, 108, 14);
		fileInfoPanel.add(resultingFileLabel);
		
		resultingNameText = new JTextField();
		resultingNameText.setText(DEFAULTNAME);
		resultingNameText.setBounds(358, 160, 157, 20);
		fileInfoPanel.add(resultingNameText);
		resultingNameText.setColumns(10);
		

	}


	protected boolean validateResultingName() {
		String s = resultingNameText.getText();
		return s.matches(FILENAME_PATTERN);
	}


	protected void stitchFiles() {
		
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			
			@Override
			protected Void doInBackground() throws Exception {
				List<Path> list = new ArrayList<>();
				for(Object o: listModel.toArray()) {
					list.add(((PathObject) o).getPath());
				}
				Notifier stitchInformer = new StitchInformer(new ProgressBarAdapter(progressBar));
				Stitcher stitcher = new Stitcher(list, stitchInformer , resultingNameText.getText());
				OperationResult r = stitcher.stitch();
				btnStitch.setEnabled(true);
				resetProgressBar();
				JOptionPane.showMessageDialog(getParent(), r.getMessage());
				return null;
			}
		};
		worker.execute();
		
	}


	protected void resetProgressBar() {
		progressBar.setVisible(false);
		progressBar.setValue(0);
	}


	private void resetPanel() {
		textField.setText("");
	}


	private void populateFileList(File selectedFile) {
		List<Path> list = FileUtils.guessFileList(selectedFile.toPath());
		listModel.clear();
		for(Path p : list) {
			listModel.insertElementAt(new PathObject(p), listModel.getSize()); 
		}
	}
}
