package view;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.UserDAO;
import model.User;
import util.FormUtils;
import util.HashPassword;

public class ChangePasswordView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JTextField curPassField;
	private JTextField rePassField;
	private JTextField newPassField;
	private UserDAO userDAO;

	/**
	 * Create the frame.
	 */
	public ChangePasswordView(User user) {
		userDAO = new UserDAO();
		Image icon = Toolkit.getDefaultToolkit().getImage(SignupView.class.getResource("/resources/logo.png"));
		this.setIconImage(icon);
		setResizable(false);
		setTitle("Đổi mật khẩu");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainPanel);
		mainPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("ĐỔI MẬT KHẨU", SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setBounds(0, 0, 434, 42);
		mainPanel.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBounds(41, 53, 329, 42);
		mainPanel.add(panel);
		panel.setLayout(null);

		JLabel curPassLabel = new JLabel("Mật khẩu hiện tại");
		curPassLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		curPassLabel.setBounds(10, 11, 116, 20);
		panel.add(curPassLabel);

		curPassField = new JPasswordField();
		curPassField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		curPassField.setBounds(125, 0, 204, 42);
		curPassField.setColumns(10);
		curPassField.setBorder(BorderFactory.createCompoundBorder(curPassField.getBorder(),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		panel.add(curPassField);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(41, 159, 329, 42);
		mainPanel.add(panel_1);

		JLabel rePassLabel = new JLabel("Nhập lại mật khẩu");
		rePassLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rePassLabel.setBounds(10, 11, 116, 20);
		panel_1.add(rePassLabel);

		rePassField = new JPasswordField();
		rePassField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rePassField.setColumns(10);
		rePassField.setBounds(125, 0, 204, 42);
		rePassField.setBorder(BorderFactory.createCompoundBorder(rePassField.getBorder(),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		panel_1.add(rePassField);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(41, 106, 329, 42);
		mainPanel.add(panel_2);

		JLabel newPassLabel = new JLabel("Mật khẩu mới");
		newPassLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		newPassLabel.setBounds(10, 11, 116, 20);
		panel_2.add(newPassLabel);

		newPassField = new JPasswordField();
		newPassField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		newPassField.setColumns(10);
		newPassField.setBounds(125, 0, 204, 42);
		newPassField.setBorder(BorderFactory.createCompoundBorder(newPassField.getBorder(),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		panel_2.add(newPassField);

		JButton btnNewButton = new JButton("ĐỔI MẬT KHẨU");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!FormUtils.ValidateForm(mainPanel)) {
					JOptionPane.showMessageDialog(ChangePasswordView.this, "Vui lòng nhập đầy đủ thông tin", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String curPass = curPassField.getText();
				String newPass = newPassField.getText();
				String rePass = rePassField.getText();

				if (!user.getPassword().equals(HashPassword.hashPassword(curPass))) {
					JOptionPane.showMessageDialog(ChangePasswordView.this, "Mật khẩu sai. Vui lòng thử lại.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (newPass.equals(curPass)) {
					JOptionPane.showMessageDialog(ChangePasswordView.this, "Mật khẩu mới không được trùng mật khẩu cũ.",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (!newPass.equals(rePass)) {
					JOptionPane.showMessageDialog(ChangePasswordView.this, "Mật khẩu không khớp. Vui lòng nhập lại.",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				user.setPassword(HashPassword.hashPassword(newPass));
				try {
					userDAO.update(user);
					JOptionPane.showMessageDialog(ChangePasswordView.this, "Thay đổi mật khẩu thành công.");
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}

				dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(147, 212, 137, 38);
		mainPanel.add(btnNewButton);

		setLocationRelativeTo(null);
		setVisible(true);
	}
}
