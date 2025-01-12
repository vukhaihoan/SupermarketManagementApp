package sma.View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import sma.Controller.DBOperation;
import sma.Modal.Customer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.sql.*;

public class CustomerManagement extends JFrame {

	private JPanel contentPane;
	private JTextField txtCustomerId;
	private JTextField txtAddress;
	private JTextField txtCustomerName;
	private JTextField txtPhonenumbers;
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// CustomerManagement frame = new CustomerManagement();
					// frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CustomerManagement(int i) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Quản Lý Khách Hàng");
		setBounds(100, 100, 1129, 627);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 1095, 87);
		panel.setBackground(new Color(192, 192, 192));
		panel.setForeground(new Color(128, 128, 0));
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Quản Lý Khách Hàng");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblNewLabel.setBounds(10, 10, 1075, 68);
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 107, 1095, 157);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Thông Tin Khách Hàng");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(0, 0, 243, 32);
		panel_1.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("ID Khách Hàng:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(10, 42, 126, 32);
		panel_1.add(lblNewLabel_2);

		txtCustomerId = new JTextField();
		txtCustomerId.setEditable(false);
		txtCustomerId.setBounds(146, 42, 209, 38);
		panel_1.add(txtCustomerId);
		txtCustomerId.setColumns(10);

		JLabel lblNewLabel_2_1 = new JLabel("Địa Chỉ:");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_1.setBounds(10, 103, 126, 32);
		panel_1.add(lblNewLabel_2_1);

		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		txtAddress.setBounds(146, 103, 209, 38);
		panel_1.add(txtAddress);

		JLabel lblNewLabel_2_2 = new JLabel("Tên Khách Hàng:");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_2.setBounds(531, 42, 126, 32);
		panel_1.add(lblNewLabel_2_2);

		txtCustomerName = new JTextField();
		txtCustomerName.setColumns(10);
		txtCustomerName.setBounds(667, 42, 209, 38);
		panel_1.add(txtCustomerName);

		JLabel lblNewLabel_2_3 = new JLabel("Số Điện Thoại:");
		lblNewLabel_2_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_3.setBounds(531, 103, 126, 32);
		panel_1.add(lblNewLabel_2_3);

		txtPhonenumbers = new JTextField();
		txtPhonenumbers.setColumns(10);
		txtPhonenumbers.setBounds(667, 103, 209, 38);
		panel_1.add(txtPhonenumbers);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 275, 1095, 256);
		contentPane.add(scrollPane);

		table = new JTable(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				txtCustomerId.setText(String.valueOf(table.getValueAt(row, 1)));
				txtCustomerName.setText(String.valueOf(table.getValueAt(row, 2)));
				txtPhonenumbers.setText(String.valueOf(table.getValueAt(row, 3)));
				txtAddress.setText(String.valueOf(table.getValueAt(row, 4)));
			}
		});

		String[] columnNames = { "", "Id Khách hàng", "Tên Khách Hàng", "Số Điện Thoại", "Địa Chỉ", "Level" };
		model.setColumnIdentifiers(columnNames);

		scrollPane.setViewportView(table);
		searchData();
		table.getColumnModel().getColumn(0).setMaxWidth(30);
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 542, 1095, 39);
		contentPane.add(panel_2);

		JButton btnNewButton = new JButton("Thêm Khách Hàng");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddCustomerDialog addCustomerDialog = new AddCustomerDialog();
				addCustomerDialog.setLocationRelativeTo(null);
				addCustomerDialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						txtCustomerId.setText("");
						txtCustomerName.setText("");
						txtPhonenumbers.setText("");
						txtAddress.setText("");
						searchData();
						int lastRowIndex = table.getModel().getRowCount() - 1;
						table.setRowSelectionInterval(lastRowIndex, lastRowIndex);
						table.scrollRectToVisible(table.getCellRect(lastRowIndex, 0, true));
					}
				});

				addCustomerDialog.show();

			}
		});

		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnDelete = new JButton("Xoá");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showOptionDialog(null, "Bạn có chắc chắn muốn xoá?", "Xác nhận xoá",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				if (result == JOptionPane.YES_OPTION) {
					Vector<Integer> v = new Vector<>();
					for (int i = 0; i < table.getRowCount(); i++) {

						if (table.getValueAt(i, 0).toString() == "true")
							v.add(Integer.parseInt(table.getValueAt(i, 1).toString()));
					}
					for (int i = 0; i < v.size(); i++) {

						DBOperation.deleteCustomer(v.get(i), conn);
					}
					txtCustomerId.setText("");
					txtCustomerName.setText("");
					txtPhonenumbers.setText("");
					txtAddress.setText("");
					searchData();
				} else {

				}
			}
		});
		panel_2.add(btnDelete);
		panel_2.add(btnNewButton);

		JButton btnUpdate = new JButton("Cập Nhật");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int customerId = Integer.parseInt(txtCustomerId.getText());
				String customerName = txtCustomerName.getText();
				String phoneNumbers = txtPhonenumbers.getText();
				String address = txtAddress.getText();

				Customer customer = new Customer();
				customer.setCustomerId(customerId);
				customer.setCustomerName(customerName);
				customer.setPhoneNumbers(phoneNumbers);
				customer.setAddress(address);

				DBOperation.updateCustomer(customer, conn);

				if (customerId == 0 || customerName == null || phoneNumbers == null || address == null) {
					JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin.");
				} else {
					JOptionPane.showMessageDialog(null, "Cập nhật khách hàng thành công!");
				}

				searchData();

			}
		});
		panel_2.add(btnUpdate);

		JButton btnSearch = new JButton("Tìm Kiếm");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				searchData();
			}
		});
		panel_2.add(btnSearch);

		JButton btnResetTable = new JButton("Đặt Lại");
		btnResetTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				txtCustomerId.setText("");
				txtCustomerName.setText("");
				txtPhonenumbers.setText("");
				txtAddress.setText("");
				searchData();
			}
		});
		panel_2.add(btnResetTable);

		JButton btnItemList = new JButton("Hoá Đơn");
		btnItemList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (table.isRowSelected(table.getSelectedRow())) {
					int row = table.getSelectedRow();
					int customerId = Integer.parseInt(table.getValueAt(row, 1).toString());
					Customer customer = DBOperation.queryCustomer(customerId, conn);

					CustomerInvoice customerInvoice = new CustomerInvoice(customer);
					customerInvoice.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Vui Lòng chọn khách hàng!");
				}
			}
		});

		JButton btnAddInvoice = new JButton("Thêm Hoá Đơn");
		btnAddInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int row = table.getSelectedRow();

				if (table.isRowSelected(row)) {
					int customerId = Integer.parseInt(table.getValueAt(row, 1).toString());
					String customerName = table.getValueAt(row, 2).toString();
					String phonenumber = table.getValueAt(row, 3).toString();
					PutSelectedItem2 putSelectedItem = new PutSelectedItem2(customerId, customerName, phonenumber, i);
					putSelectedItem.show();
					putSelectedItem.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							txtCustomerId.setText("");
							txtCustomerName.setText("");
							txtPhonenumbers.setText("");
							txtAddress.setText("");
							searchData();
						}
					});
				} else {
					PutSelectedItem putSelectedItem = new PutSelectedItem(i);
					putSelectedItem.show();
					putSelectedItem.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							txtCustomerId.setText("");
							txtCustomerName.setText("");
							txtPhonenumbers.setText("");
							txtAddress.setText("");
							searchData();
						}
					});
				}

			}
		});
		panel_2.add(btnAddInvoice);
		panel_2.add(btnItemList);

		JButton btnCancel = new JButton("Hủy");
		btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String sql = "SELECT * FROM user WHERE USER_ID = ?";
				PreparedStatement statement;
				try {
					statement = conn.prepareStatement(sql);
					statement.setInt(1, i);
					ResultSet result = statement.executeQuery();
					result.next();
					if (result.getString("ROLE").equals("ADMIN")) {

						AdminManagement adminManagement = new AdminManagement(i);
						adminManagement.setVisible(true);
						adminManagement.setLocationRelativeTo(null);
						dispose();

					} else {
						SaleManagement SaleManagement = new SaleManagement(i);
						SaleManagement.setVisible(true);
						SaleManagement.setLocationRelativeTo(null);
						dispose();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		panel_2.add(btnCancel);

	}

	public void searchData() {

		Map<String, String> conditionMap = new HashMap<String, String>();

		String customerName = txtCustomerName.getText();
		if (txtCustomerId.getText() != null && !txtCustomerId.getText().isEmpty()) {
			conditionMap.put(DBOperation.customerId, "%" + txtCustomerId.getText().toLowerCase() + "%");
		}
		if (customerName != null && !customerName.isEmpty()) {
			conditionMap.put(DBOperation.customerName, "%" + customerName.toLowerCase() + "%");
		}
		if (txtPhonenumbers.getText() != null && !txtPhonenumbers.getText().isEmpty()) {
			conditionMap.put(DBOperation.phoneNumbers, "%" + txtPhonenumbers.getText().toLowerCase() + "%");
		}
		List<Customer> customers = DBOperation.queryCustomer(conditionMap, conn);
		table.setModel(model);

		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}

		for (Customer s : customers) {
			Object[] o = new Object[7];
			o[0] = false;
			o[1] = s.getCustomerId();
			o[2] = s.getCustomerName();
			o[3] = s.getPhoneNumbers();
			o[4] = s.getAddress();
			o[5] = s.getLevel();
			model.addRow(o);
		}

		this.invalidate();
		this.repaint();

	}

}
