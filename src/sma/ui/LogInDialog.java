package sma.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sma.db.DBOperation;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JEditorPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class LogInDialog extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserId;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogInDialog frame = new LogInDialog();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LogInDialog() {

		setTitle("Log in Dialog");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setBounds(100, 100, 613, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 124, 309, 82);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("User ID:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 10, 87, 20);
		panel.add(lblNewLabel);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassword.setBounds(10, 47, 87, 24);
		panel.add(lblPassword);

		txtUserId = new JTextField();
		txtUserId.setBounds(99, 10, 137, 20);
		panel.add(txtUserId);
		txtUserId.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(99, 49, 137, 22);
		panel.add(txtPassword);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 10, 309, 104);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("LOG IN DIALOG");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 10, 289, 84);
		panel_1.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setIcon(new ImageIcon(LogInDialog.class.getResource("/sma/ui/LogIn.jpg")));
		lblNewLabel_2.setBounds(329, 10, 260, 204);
		contentPane.add(lblNewLabel_2);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(489, 235, 100, 28);
		contentPane.add(btnCancel);

		JButton btnLogIn = new JButton("Log in");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userId = txtUserId.getText();
				char[] password = txtPassword.getPassword();
				String passwordString = new String(password);

				if(userId.isEmpty() || passwordString.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill empty fields!");
				} else {
					Connection conn = DBOperation.createConnection("jdbc:mysql://localhost:3306/supermarket", "phuocvo", "123456");

					String sql = "SELECT * FROM user WHERE USER_ID = ? AND PASSWORD = ?";
					PreparedStatement statement;
					try {
						statement = conn.prepareStatement(sql);
						statement.setString(1, userId);
						statement.setString(2, passwordString);
						ResultSet result = statement.executeQuery();
						if(result.next()) {
							String role = result.getString("role");
							if(role.equals("SALE_PERSON")) {
								
								SaleManagement saleManagement = new SaleManagement();
								saleManagement.setLocationRelativeTo(null);
								saleManagement.setVisible(true);
								dispose();
							}else {
								
								ItemManagement itemManagement = new ItemManagement();
								itemManagement.setLocationRelativeTo(null);
								itemManagement.setVisible(true);
								dispose();
							}
						} else {
							JOptionPane.showMessageDialog(null, "Invalid user_id or password.");
							txtUserId.setText("");
							txtPassword.setText("");
						}
						conn.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});


		btnLogIn.setBounds(378, 235, 100, 28);
		contentPane.add(btnLogIn);
	}
}
