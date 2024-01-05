package sma.View;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdatepicker.impl.JDatePickerImpl;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import sma.Controller.DBOperation;
import sma.Modal.Campaign;
import sma.Modal.CustomerLevel;
import sma.business.CampaignTask;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JSpinner;
import javax.swing.JScrollBar;

public class CampaignManagement extends JFrame {

	JPanel contentPane;
	JTextField txtCampaignId;
	JTextField txtCampaignName;
	JTextField txtCampaignCode;
	JTextField txtContent;
	JDatePanelImpl datePanel;
	JDatePickerImpl datePicker;
	private JTable table;
	DefaultTableModel model = new DefaultTableModel() {
		@Override
		public Class<?> getColumnClass(int column) {
			switch (column) {
				case 0:
					return Boolean.class;
				default:
					return String.class;
			}
		}
	};
	static Connection conn = DBOperation.createConnection("jdbc:mysql://localhost:3306/my_database", "root", "");
	private JTextField txtStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CampaignManagement frame = new CampaignManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CampaignManagement() {
		setTitle("Campaign Management");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1084, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 302, 299);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Campaign ID:");
		lblNewLabel.setBounds(10, 10, 127, 26);
		panel.add(lblNewLabel);

		JLabel lblCampaignName = new JLabel("Campaign Name:");
		lblCampaignName.setBounds(10, 46, 127, 26);
		panel.add(lblCampaignName);

		JLabel lblTargetCampaign = new JLabel("Target Customer:");
		lblTargetCampaign.setBounds(10, 82, 127, 26);
		panel.add(lblTargetCampaign);

		JLabel lblCampaignCode = new JLabel("Campaign code:");
		lblCampaignCode.setBounds(10, 118, 127, 26);
		panel.add(lblCampaignCode);

		JLabel lblTimestamp = new JLabel("Message content:");
		lblTimestamp.setBounds(10, 190, 127, 26);
		panel.add(lblTimestamp);

		UtilDateModel model1 = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(model1, p);

		JLabel lblTimestamp_1 = new JLabel("Timestamp:");
		lblTimestamp_1.setBounds(10, 226, 127, 26);
		panel.add(lblTimestamp_1);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(147, 223, 145, 26);
		panel.add(datePicker);

		JComboBox cbTargetCustomer = new JComboBox();
		cbTargetCustomer.setBounds(147, 82, 145, 26);
		panel.add(cbTargetCustomer);

		List<CustomerLevel> customerLevels = DBOperation.queryCustomerLevel(conn);
		for (CustomerLevel c : customerLevels) {
			cbTargetCustomer.addItem(c.getLevel());
		}

		txtCampaignId = new JTextField();
		txtCampaignId.setBounds(147, 11, 145, 26);
		panel.add(txtCampaignId);
		txtCampaignId.setColumns(10);

		txtCampaignName = new JTextField();
		txtCampaignName.setColumns(10);
		txtCampaignName.setBounds(147, 47, 145, 26);
		panel.add(txtCampaignName);

		txtCampaignCode = new JTextField();
		txtCampaignCode.setColumns(10);
		txtCampaignCode.setBounds(147, 119, 145, 26);
		panel.add(txtCampaignCode);

		txtContent = new JTextField();
		txtContent.setColumns(10);
		txtContent.setBounds(147, 188, 145, 26);
		panel.add(txtContent);

		JLabel lblTimestamp_1_1 = new JLabel("hh : mm : ss");
		lblTimestamp_1_1.setBounds(10, 259, 127, 26);
		panel.add(lblTimestamp_1_1);

		SpinnerDateModel model2 = new SpinnerDateModel();
		model2.setCalendarField(Calendar.SECOND);
		JSpinner spinner = new JSpinner(model2);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "HH:mm:ss");
		spinner.setEditor(editor);
		spinner.setBounds(147, 259, 145, 26);
		JSpinner.DefaultEditor editor1 = (JSpinner.DefaultEditor) spinner.getEditor();
		editor1.getTextField().setHorizontalAlignment(JTextField.CENTER);
		panel.add(spinner);

		JLabel lblTimestamp_1_1_1 = new JLabel("Status:");
		lblTimestamp_1_1_1.setBounds(10, 155, 127, 26);
		panel.add(lblTimestamp_1_1_1);

		txtStatus = new JTextField();
		txtStatus.setColumns(10);
		txtStatus.setBounds(147, 156, 145, 26);
		panel.add(txtStatus);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(965, 384, 92, 21);
		contentPane.add(btnCancel);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 320, 302, 85);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchData();
			}
		});
		btnReset.setBounds(169, 48, 123, 28);
		panel_2.add(btnReset);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (authenticator()) {

					AddCampaignDialog addCampaignDialog = new AddCampaignDialog();
					addCampaignDialog.setLocationRelativeTo(null);
					addCampaignDialog.setVisible(true);
					addCampaignDialog.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							int lastRowIndex = table.getModel().getRowCount() - 1;
							table.setRowSelectionInterval(lastRowIndex, lastRowIndex);
							table.scrollRectToVisible(table.getCellRect(lastRowIndex, 0, true));
							btnReset.doClick();
							int n = table.getRowCount();
							table.setRowSelectionInterval(n - 1, n - 1);
						}
					});

				}

			}
		});
		btnAdd.setBounds(10, 10, 123, 28);
		panel_2.add(btnAdd);

		JButton btnNewButton_1_1 = new JButton("Update");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Campaign campaign = getCampaign(cbTargetCustomer, model2);
				String result = DBOperation.updateCampaign(campaign, conn);
				if ("Successful".equals(result)) {
					JOptionPane.showMessageDialog(null, "Campaign updated successfully!");
				} else {
					JOptionPane.showMessageDialog(null, "Failed to update campaign. Please try again.");
				}
				searchData();

			}
		});
		btnNewButton_1_1.setBounds(169, 10, 123, 28);
		panel_2.add(btnNewButton_1_1);

		JButton btnNewButton_1_2 = new JButton("Delete");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int result = JOptionPane.showOptionDialog(null, "Confirm to delete?", "Confirm Delete",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				if (result == JOptionPane.YES_OPTION) {

					int rowCount = table.getRowCount();
					for (int i = rowCount - 1; i >= 0; i--) {
						if (Boolean.TRUE.equals(table.getValueAt(i, 0))) {
							int campaignId = Integer.parseInt(table.getValueAt(i, 1).toString());
							DBOperation.deleteCampaign(campaignId, conn);
						}
					}
				}
				searchData();
			}
		});
		btnNewButton_1_2.setBounds(10, 48, 123, 28);
		panel_2.add(btnNewButton_1_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(322, 10, 735, 259);
		contentPane.add(scrollPane);

		JButton btnNewButton = new JButton("Execute");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "Please choose a campaign to execute!");
					return;
				} else {
					Timer timer = new Timer(); // creating timer
					TimerTask task = new CampaignTask(); // creating timer task
					String selectedDate = datePicker.getJFormattedTextField().getText();
					String selectedTime = model2.getValue().toString();
					SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
					Date date;
					try {
						date = originalFormat.parse(selectedTime);
						SimpleDateFormat targetFormat = new SimpleDateFormat("HH:mm:ss");
						String formattedTime = targetFormat.format(date);
						Date taskRunningTime = null;
						taskRunningTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
								.parse(selectedDate + " " + formattedTime);
						System.out.println("Performing the given task");
						timer.schedule(task, taskRunningTime); // scheduling the task
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		btnNewButton.setBounds(863, 384, 92, 21);
		contentPane.add(btnNewButton);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				int row = table.getSelectedRow();
				txtCampaignId.setText(table.getValueAt(row, 1).toString());
				txtCampaignName.setText(table.getValueAt(row, 2).toString());
				setCustomerLevel(table.getValueAt(row, 3).toString(), cbTargetCustomer);
				txtCampaignCode.setText(table.getValueAt(row, 4).toString());
				if (table.getValueAt(row, 5).toString().equals(null)) {
					txtStatus.setText(null);
				} else {
					txtStatus.setText(table.getValueAt(row, 5).toString());
				}
				txtContent.setText(table.getValueAt(row, 6).toString());
				String date = String.valueOf(table.getValueAt(row, 7));
				datePicker.getJFormattedTextField().setText(date.substring(0, 10));
				String txtTime = date.substring(11, 19);
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				Date date1;
				try {
					date1 = sdf.parse(txtTime);
					model2.setValue(date1);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		String[] columnNames = { "", "Campaign ID", "Campaign Name", "Target_customer", "Campaign Code", "status",
				"Msg_content", "Campaign_timestamp" };
		model.setColumnIdentifiers(columnNames);
		scrollPane.setViewportView(table);
		searchData();
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(1).setMaxWidth(50);
		table.getColumnModel().getColumn(3).setMaxWidth(50);
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);

	}

	public void setCustomerLevel(String level, JComboBox cb) {

		for (int i = 0; i < cb.getItemCount(); i++) {

			if (cb.getItemAt(i).equals(level)) {
				cb.setSelectedItem(level);
			}
		}
	}

	public void searchData() {

		List<Campaign> campaigns = DBOperation.queryCampaign(conn);

		table.setModel(model);
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
		for (Campaign c : campaigns) {
			Object[] o = new Object[10];
			o[0] = false;
			o[1] = c.getCampaignId();
			o[2] = c.getCampaignName();
			o[3] = c.getTarget_customer();
			o[4] = c.getCampaignCode();
			o[5] = c.getStatus();
			o[6] = c.getMsg_content();
			o[7] = c.getCampaign_timestamp();
			model.addRow(o);

		}
		this.invalidate();
		this.repaint();
	}

	public boolean authenticator() {

		if (txtCampaignId.getText().equals(null) || txtCampaignName.equals(null) ||
				txtCampaignCode.getText().equals(null) || txtStatus.getText().equals(null) ||
				txtContent.getText().equals(null) || equals(null)) {

			JOptionPane.showMessageDialog(null, "Please fill all empty field!");
			return false;

		} else {
			return true;
		}
	}

	public Campaign getCampaign(JComboBox cb, SpinnerDateModel model2) {
		String campaignId = txtCampaignId.getText();
		String campaignName = txtCampaignName.getText();
		String campaignCode = txtCampaignCode.getText();
		String status = txtStatus.getText();
		String content = txtContent.getText();
		String targetCustomer = cb.getSelectedItem().toString();
		String selectedDate = datePicker.getJFormattedTextField().getText();
		Date date = (Date) model2.getValue();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String selectedTime = sdf.format(date);
		String taskRunningTime = selectedDate + " " + selectedTime;

		Campaign campaign = new Campaign();
		campaign.setCampaignId(Integer.parseInt(campaignId));
		campaign.setCampaignName(campaignName);
		campaign.setCampaignCode(campaignCode);
		campaign.setStatus(status);
		campaign.setMsg_content(content);
		campaign.setTarget_customer(targetCustomer);
		campaign.setCampaign_timestamp(taskRunningTime);

		return campaign;
	}
}
