package sma.View;

import javax.swing.*;

import sma.Controller.DBOperation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ChangePasswordDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JButton changeButton;

    static Connection conn = DBOperation.createConnection("jdbc:mysql://localhost:3306/my_database", "root", "");

    public ChangePasswordDialog(int id, String username) {
        setTitle("Đổi mật khẩu");

        // Create components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel currentPasswordLabel = new JLabel("Current Password:");
        JLabel newPasswordLabel = new JLabel("New Password:");
        usernameField = new JTextField(20);
        usernameField.setText(username);
        currentPasswordField = new JPasswordField(20);
        newPasswordField = new JPasswordField(20);
        changeButton = new JButton("Change");

        // Set layout
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(currentPasswordLabel);
        panel.add(currentPasswordField);
        panel.add(newPasswordLabel);
        panel.add(newPasswordField);
        panel.add(changeButton);

        // Add action listener to the change button
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform password change logic here
                String username = usernameField.getText();
                String currentPassword = new String(currentPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());

                // TODO: Implement password change logic

                // get the password from the database
                String query = "SELECT PASSWORD FROM user WHERE USER_NAME = ?";

                try {
                    PreparedStatement statement = conn.prepareStatement(query);
                    statement.setString(1, username);
                    ResultSet result = statement.executeQuery();

                    if (result.next()) {
                        String password = result.getString("password");

                        if (password.equals(currentPassword)) {
                            // update the password
                            String updateQuery = "UPDATE user SET PASSWORD = ? WHERE USER_NAME = ?";
                            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
                            updateStatement.setString(1, newPassword);
                            updateStatement.setString(2, username);
                            updateStatement.executeUpdate();

                            JOptionPane.showMessageDialog(null, "Password changed successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect current password!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "User not found!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                // Close the dialog
                dispose();
            }
        });

        // Set dialog properties
        setContentPane(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        setResizable(false);
    }

}
