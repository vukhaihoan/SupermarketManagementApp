package sma.View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import sma.Controller.DBOperation;
import sma.Modal.Category;
import sma.Modal.Item;
import sma.Modal.Measurement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class ItemManagement extends JFrame {

	private JPanel contentPane;
	private JTextField txtItemId;
	private JTextField txtItemName;
	private JTextField txtQuantity;
	private JTextField txtUnitPrice;
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
					// ItemManagement frame = new ItemManagement();
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
	public ItemManagement(int i) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 889, 717);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 855, 65);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("ITEM MANAGEMENT");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 10, 835, 45);
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 85, 855, 148);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("   ITEM INFORMATION");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(0, 0, 164, 26);
		panel_1.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Item ID:");
		lblNewLabel_2.setBounds(10, 36, 86, 26);
		panel_1.add(lblNewLabel_2);

		txtItemId = new JTextField();
		txtItemId.setEnabled(false);
		txtItemId.setEditable(false);
		txtItemId.setBounds(108, 40, 127, 22);
		panel_1.add(txtItemId);
		txtItemId.setColumns(10);

		JLabel lblNewLabel_2_1 = new JLabel("Measurement:");
		lblNewLabel_2_1.setBounds(10, 88, 86, 26);
		panel_1.add(lblNewLabel_2_1);

		JLabel lblNewLabel_2_2 = new JLabel("Item Name:");
		lblNewLabel_2_2.setBounds(316, 36, 86, 26);
		panel_1.add(lblNewLabel_2_2);

		txtItemName = new JTextField();
		txtItemName.setColumns(10);
		txtItemName.setBounds(414, 40, 127, 22);
		panel_1.add(txtItemName);

		JLabel lblNewLabel_2_3 = new JLabel("Quantity:");
		lblNewLabel_2_3.setBounds(316, 88, 86, 26);
		panel_1.add(lblNewLabel_2_3);

		txtQuantity = new JTextField();
		txtQuantity.setColumns(10);
		txtQuantity.setBounds(414, 92, 127, 22);
		panel_1.add(txtQuantity);

		JLabel lblNewLabel_2_4 = new JLabel("Category:");
		lblNewLabel_2_4.setBounds(620, 36, 86, 26);
		panel_1.add(lblNewLabel_2_4);

		JLabel lblNewLabel_2_5 = new JLabel("Unit Price:");
		lblNewLabel_2_5.setBounds(620, 88, 86, 26);
		panel_1.add(lblNewLabel_2_5);

		txtUnitPrice = new JTextField();
		txtUnitPrice.setColumns(10);
		txtUnitPrice.setBounds(718, 92, 127, 22);
		panel_1.add(txtUnitPrice);

		List<Category> categories = DBOperation.queryCategories(conn);

		JComboBox cbCategory = new JComboBox();
		cbCategory.setBounds(716, 36, 129, 26);
		panel_1.add(cbCategory);

		for (Category c : categories) {

			cbCategory.addItem(c.getCategory());

		}

		List<Measurement> measurements = DBOperation.queryMeasurement(conn);
		JComboBox cbMeasurement = new JComboBox();
		cbMeasurement.setBounds(108, 91, 129, 26);
		panel_1.add(cbMeasurement);

		for (Measurement m : measurements) {

			cbMeasurement.addItem(m.getMeasurement());

		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 244, 855, 266);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				int row = table.getSelectedRow();

				txtItemId.setText(String.valueOf(table.getValueAt(row, 1)));
				txtItemName.setText(String.valueOf(table.getValueAt(row, 2)));
				setSelectedValue(cbCategory, table.getValueAt(row, 3).toString());
				setSelectedValue(cbMeasurement, table.getValueAt(row, 4).toString());
				txtQuantity.setText(String.valueOf(table.getValueAt(row, 5)));
				txtUnitPrice.setText(String.valueOf(table.getValueAt(row, 6)));

			}
		});
		String[] columnNames = { "", "Item ID", "Item Name", "Category", "Measurement", "Remaining", "Unit Price" };
		(model).setColumnIdentifiers(columnNames);

		scrollPane.setViewportView(table);
		searchData();
		table.getColumnModel().getColumn(0).setMaxWidth(30);
		table.setBounds(10, 458, 1, 1);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 518, 855, 162);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				AddItemDialog addItemDialog = new AddItemDialog();
				addItemDialog.setLocationRelativeTo(null);
				addItemDialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {

						txtItemId.setText("");
						txtItemName.setText("");
						txtQuantity.setText("");
						txtUnitPrice.setText("");
						cbCategory.setSelectedItem(null);
						cbMeasurement.setSelectedItem(null);
						searchData();
					}
				});
				addItemDialog.show();
			}
		});
		btnAdd.setBounds(10, 10, 143, 42);
		panel_2.add(btnAdd);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				searchData();
			}
		});
		btnSearch.setBounds(238, 10, 143, 42);
		panel_2.add(btnSearch);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int itemId = Integer.parseInt(txtItemId.getText());
				String itemName = txtItemName.getText();
				String category = cbCategory.getSelectedItem().toString();
				String measurement = cbMeasurement.getSelectedItem().toString();
				int quantity = Integer.parseInt(txtQuantity.getText());
				float unitPrice = Float.parseFloat(txtUnitPrice.getText());
				Item item = new Item();
				if (itemId == 0 || itemName == null || itemName.isEmpty()
						|| category == null || category.isEmpty() || measurement == null || measurement.isEmpty()
						|| txtQuantity == null || txtQuantity.getText().isEmpty() || txtUnitPrice == null
						|| txtUnitPrice.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill in all the required information.");

				} else {

					item.setItemId(itemId);
					item.setItemName(itemName);
					item.setCategory(category);
					item.setMeasurement(measurement);
					item.setQuantity(quantity);
					item.setUnitPrice(unitPrice);
					JOptionPane.showMessageDialog(null, "Update a item successfully!");
					String result = DBOperation.updateItem(item, conn);
				}

				searchData();
			}
		});
		btnUpdate.setBounds(10, 81, 143, 42);
		panel_2.add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int result = JOptionPane.showOptionDialog(null, "Confirm to delete?", "Confirm Delete",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);

				if (result == JOptionPane.YES_OPTION) {
					int column = 1;
					int row = table.getSelectedRow();
					int selectedItemId = Integer.parseInt(table.getModel().getValueAt(row, column).toString());
					Vector<Integer> v = new Vector<>();
					for (int i = 0; i < table.getRowCount(); i++) {

						if (table.getValueAt(i, 0).toString() == "true")
							v.add(Integer.parseInt(table.getValueAt(i, 1).toString()));
					}
					for (int i = 0; i < v.size(); i++) {

						DBOperation.deleteItem(v.get(i), conn);
					}

					txtItemId.setText("");
					txtItemName.setText("");
					txtQuantity.setText("");
					txtUnitPrice.setText("");
					cbCategory.setSelectedItem(null);
					cbMeasurement.setSelectedItem(null);
					searchData();
				} else {

				}

			}
		});
		btnDelete.setBounds(238, 81, 143, 42);
		panel_2.add(btnDelete);

		JButton btnResetTable = new JButton("Reset Table");
		btnResetTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				txtItemId.setText("");
				txtItemName.setText("");
				txtQuantity.setText("");
				txtUnitPrice.setText("");
				cbCategory.setSelectedItem(null);
				cbMeasurement.setSelectedItem(null);
				searchData();
			}
		});
		btnResetTable.setBounds(469, 10, 143, 42);
		panel_2.add(btnResetTable);

		JButton btnCancel = new JButton("Log out");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (i == 13) {
					LogInDialog logInDialog = new LogInDialog();
					logInDialog.setLocationRelativeTo(null);
					logInDialog.setVisible(true);
					dispose();
				} else {
					AdminManagement adminManagement = new AdminManagement(i);
					adminManagement.setLocationRelativeTo(null);
					adminManagement.setVisible(true);
					dispose();
				}
			}
		});
		btnCancel.setBounds(729, 124, 116, 28);
		panel_2.add(btnCancel);
	}

	public static void setSelectedValue(JComboBox comboBox, String value) {
		for (int i = 0; i < comboBox.getItemCount(); i++) {
			if (comboBox.getItemAt(i).equals(value)) {
				comboBox.setSelectedIndex(i);
				break;
			}
		}
	}

	public void searchData() {

		Map<String, String> conditionMap = new HashMap<String, String>();

		String itemName = txtItemName.getText();
		if (txtItemId.getText() != null && !txtItemId.getText().isEmpty()) {
			conditionMap.put(DBOperation.itemId, txtItemId.getText());
		}
		if (itemName != null && !itemName.isEmpty()) {
			conditionMap.put(DBOperation.itemName, itemName);
		}
		List<Item> items = DBOperation.queryItem(conditionMap, conn);
		table.setModel(model);

		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
		for (Item s : items) {
			Object[] o = new Object[10];
			o[0] = false;
			o[1] = s.getItemId();
			o[2] = s.getItemName();
			o[3] = s.getCategory();
			o[4] = s.getMeasurement();
			o[5] = s.getQuantity();
			o[6] = s.getUnitPrice();
			model.addRow(o);
		}

		this.invalidate();
		this.repaint();

	}
}
