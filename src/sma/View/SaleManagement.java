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
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SaleManagement extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// SaleManagement frame = new SaleManagement(int i);
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
	public SaleManagement(int i) {
		setTitle("Quản lý bán hàng");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 493, 201);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 459, 141);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton btnNewButton = new JButton("Quản lý khách hàng");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

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
		btnInvoiceManagement.setBounds(236, 27, 213, 40);
		panel.add(btnInvoiceManagement);

		JButton btnLogOut = new JButton("Đăng xuất");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogInDialog logInDialog = new LogInDialog();
				logInDialog.setLocationRelativeTo(null);
				logInDialog.setVisible(true);
				dispose();
			}
		});
		btnLogOut.setBounds(360, 107, 89, 23);
		panel.add(btnLogOut);
	}
}
