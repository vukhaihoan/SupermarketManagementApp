package sma.View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sma.Controller.DBOperation;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;

public class AdminManagement extends JFrame {

	private JPanel contentPane;
	static Connection conn = DBOperation.createConnection("jdbc:mysql://localhost:3306/my_database", "root", "");
	private int k = LogInDialog.getBoothId();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// AdminManagement frame = new AdminManagement(i);
					// frame.setVisible(true);
					// frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param i
	 */
	public AdminManagement(int i) {
		setTitle("Admin Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 1200, 139);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 1200, 82);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton btnNewButton = new JButton("Quản lý khách hàng");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// CustomerManagement customerManagement = new CustomerManagement(k);
				CustomerManagement customerManagement = new CustomerManagement(i);
				customerManagement.setLocationRelativeTo(null);
				customerManagement.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(10, 27, 213, 40);
		panel.add(btnNewButton);

		JButton btnInvoiceManagement = new JButton("Quản lý hóa đơn");
		btnInvoiceManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PutSelectedItem putSelectedItem = new PutSelectedItem(i);
				putSelectedItem.setVisible(true);
				putSelectedItem.setLocationRelativeTo(null);
				dispose();
			}
		});
		btnInvoiceManagement.setBounds(233, 27, 213, 40);
		panel.add(btnInvoiceManagement);

		JButton btnInvoiceManagement_1 = new JButton("Quản lý mặt hàng");
		btnInvoiceManagement_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ItemManagement itemManagement = new ItemManagement(i);
				itemManagement.setLocationRelativeTo(null);
				itemManagement.setVisible(true);
				dispose();
			}
		});
		btnInvoiceManagement_1.setBounds(454, 27, 213, 40);
		panel.add(btnInvoiceManagement_1);

		JButton btnUserManagement = new JButton("Quản lý người dùng");

		btnUserManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserManagement userManagement = new UserManagement(i);
				userManagement.setLocationRelativeTo(null);
				userManagement.setVisible(true);
				dispose();
			}
		});

		btnUserManagement.setBounds(684, 27, 300, 40);
		panel.add(btnUserManagement);
	}
}
