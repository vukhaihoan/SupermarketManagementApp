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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.awt.event.ActionEvent;
import sma.View.ChangePasswordDialog;

public class UserManagement extends JFrame {

    static Connection conn = DBOperation.createConnection("jdbc:mysql://localhost:3306/my_database", "root", "");

    private JPanel contentPane;
    private JTextField textFieldUsername;
    private JTextField textFieldPassword;
    private JComboBox<String> comboBoxRole;
    private JTable tableUsers;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // UserManagement frame = new UserManagement(15);
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
    public UserManagement(int i) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 717);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblUsername = new JLabel("Tài Khoản:");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblUsername.setBounds(30, 30, 80, 20);
        contentPane.add(lblUsername);

        textFieldUsername = new JTextField();
        textFieldUsername.setBounds(120, 30, 150, 20);
        contentPane.add(textFieldUsername);
        textFieldUsername.setColumns(10);

        JLabel lblPassword = new JLabel("Mật Khẩu:");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setBounds(30, 70, 80, 20);
        contentPane.add(lblPassword);

        textFieldPassword = new JTextField();
        textFieldPassword.setBounds(120, 70, 150, 20);
        contentPane.add(textFieldPassword);
        textFieldPassword.setColumns(10);

        // JLabel lblRole = new JLabel("Role:");
        // lblRole.setFont(new Font("Tahoma", Font.PLAIN, 14));
        // lblRole.setBounds(30, 110, 80, 20);
        // contentPane.add(lblRole);

        // comboBoxRole = new JComboBox<String>();
        // comboBoxRole.setBounds(120, 110, 150, 20);
        // contentPane.add(comboBoxRole);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 150, 800, 400);
        contentPane.add(scrollPane);

        tableUsers = new JTable();
        scrollPane.setViewportView(tableUsers);

        JButton btnAddUser = new JButton("Thêm người dùng");
        btnAddUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // add user
                String username = textFieldUsername.getText();
                String password = textFieldPassword.getText();
                String role = "SALE_PERSON";

                if (username.equals("") || password.equals("")) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                    return;
                }
                try {
                    // kiểm tra xem username đã tồn tại chưa
                    String sqlCheck = "SELECT * FROM user WHERE USER_NAME = ?";
                    PreparedStatement statementCheck = conn.prepareStatement(sqlCheck);
                    statementCheck.setString(1, username);
                    ResultSet resultSet = statementCheck.executeQuery();
                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "Tên người dùng đã tồn tại!");
                        return;
                    }
                    String sql = "INSERT INTO user (USER_NAME, PASSWORD, ROLE) VALUES (?, ?, ?)";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, username);
                    statement.setString(2, password);
                    statement.setString(3, role);
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Thêm người dùng thành công!");
                        loadUsersData();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Thêm người dùng thất bại!");
                    ex.printStackTrace();
                }
            }
        });
        btnAddUser.setBounds(30, 570, 100, 30);
        contentPane.add(btnAddUser);

        JButton btnDeleteUser = new JButton("Xóa người dùng");
        btnDeleteUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // delete user
                int row = tableUsers.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn người dùng cần xóa!");
                    return;
                }
                int userId = (int) tableUsers.getValueAt(row, 0);
                try {
                    // make sure user is not admin
                    String sqlCheck = "SELECT * FROM user WHERE USER_ID = ? AND ROLE = 'ADMIN'";
                    PreparedStatement statementCheck = conn.prepareStatement(sqlCheck);
                    statementCheck.setInt(1, userId);
                    ResultSet resultSet = statementCheck.executeQuery();
                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "Không thể xóa admin!");
                        return;
                    }

                    // confirm delete
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa người dùng này?",
                            "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.NO_OPTION) {
                        return;
                    }

                    String sql = "DELETE FROM user WHERE USER_ID = ?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setInt(1, userId);
                    int rowsDeleted = statement.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(null, "Xóa người dùng thành công!");
                        loadUsersData();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Xóa người dùng thất bại!");
                    ex.printStackTrace();
                }
            }
        });
        btnDeleteUser.setBounds(140, 570, 100, 30);
        contentPane.add(btnDeleteUser);

        // add change password button , change password dialog

        JButton btnChangePassword = new JButton("Đổi mật khẩu");

        btnChangePassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // change password
                int row = tableUsers.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn người dùng cần đổi mật khẩu!");
                    return;
                }
                int userId = (int) tableUsers.getValueAt(row, 0);
                String username = (String) tableUsers.getValueAt(row, 1);
                ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(userId, username);
                changePasswordDialog.setLocationRelativeTo(null);
                changePasswordDialog.setVisible(true);
            }
        });

        btnChangePassword.setBounds(250, 570, 150, 30);
        contentPane.add(btnChangePassword);

        JButton btnBack = new JButton("Trở về");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // back
                AdminManagement adminManagement = new AdminManagement(i);
                adminManagement.setLocationRelativeTo(null);
                adminManagement.setVisible(true);
                dispose();
            }
        });

        btnBack.setBounds(410, 570, 100, 30);
        contentPane.add(btnBack);

        // // Populate combo box with roles
        // comboBoxRole.addItem("Admin");
        // comboBoxRole.addItem("User");

        // Load users data into table
        loadUsersData();
    }

    private void loadUsersData() {
        try {
            String sql = "SELECT * FROM user";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Create vector to store column names
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Id Người Dùng");
            columnNames.add("Tên Người Dùng");
            // columnNames.add("Role");

            // Create vector to store data
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();

            while (resultSet.next()) {
                // log user data
                System.out.println(resultSet.getInt("USER_ID"));
                System.out.println(resultSet.getString("USER_NAME"));
                Vector<Object> vector = new Vector<Object>();
                vector.add(resultSet.getInt("USER_ID"));
                vector.add(resultSet.getString("USER_NAME"));
                // vector.add(resultSet.getString("role"));
                data.add(vector);
            }

            // Create default table model
            DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames);
            tableUsers.setModel(defaultTableModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
