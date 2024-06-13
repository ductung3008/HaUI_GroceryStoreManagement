package view;

import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import dao.UserDAO;
import model.User;
import util.FormUtils;
import util.HashPassword;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JTextField usernameField;
	private JTextField passwordField;
	private UserDAO userDAO;

	/**
	 * Create the frame.
	 */
	public LoginView() {
		userDAO = new UserDAO();
		setTitle("Đăng nhập tài khoản");
		Image icon = Toolkit.getDefaultToolkit().getImage(SignupView.class.getResource("/resources/logo.png"));
		this.setIconImage(icon);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout(0, 0));

		ImageIcon logoIcon = new ImageIcon(getClass().getResource("/resources/haui_logo.png"));
		Image logoImg = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		logoIcon = new ImageIcon(logoImg);
		JLabel logoLabel = new JLabel(logoIcon);
		JPanel logoPanel = new JPanel();
		logoPanel.add(logoLabel);
		logoPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		mainPanel.add(logoPanel, BorderLayout.NORTH);

		JPanel loginPanel = new JPanel();
		mainPanel.add(loginPanel, BorderLayout.CENTER);
		loginPanel.setLayout(null);

		JPanel usernamePanel = new JPanel();
		usernamePanel.setBounds(480, 11, 299, 79);
		loginPanel.add(usernamePanel);
		usernamePanel.setLayout(null);

		JLabel usernameLabel = new JLabel("Tên đăng nhập");
		usernameLabel.setBounds(4, 8, 106, 17);
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		usernamePanel.add(usernameLabel);

		usernameField = new JTextField();
		usernameField.setToolTipText("Hãy nhập tên đăng nhập");
		usernameField.setBounds(0, 32, 299, 36);
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		usernameLabel.setLabelFor(usernameField);
		usernamePanel.add(usernameField);
		usernameField.setColumns(15);
		usernameField.setBorder(BorderFactory.createCompoundBorder(usernameField.getBorder(),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		JPanel passwordPanel = new JPanel();
		passwordPanel.setBounds(480, 88, 299, 79);
		loginPanel.add(passwordPanel);
		passwordPanel.setLayout(null);

		JLabel passwordLabel = new JLabel("Mật khẩu");
		passwordLabel.setBounds(4, 8, 58, 17);
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordPanel.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setToolTipText("Hãy nhập mật khẩu");
		passwordField.setBounds(0, 32, 299, 36);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField.setColumns(15);
		passwordField.setBorder(BorderFactory.createCompoundBorder(passwordField.getBorder(),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		passwordPanel.add(passwordField);

		JButton loginBtn = new JButton("ĐĂNG NHẬP");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!FormUtils.ValidateForm(mainPanel)) {
					JOptionPane.showMessageDialog(LoginView.this, "Vui lòng nhập đầy đủ thông tin", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String username = usernameField.getText();
				String password = passwordField.getText();

				try {
					User user = userDAO.get(u -> u.getUsername().equals(username));

					if (user == null || !user.getPassword().equals(HashPassword.hashPassword(password))) {
						JOptionPane.showMessageDialog(LoginView.this, "Tên đăng nhập hoặc mật khẩu không chính xác.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				JOptionPane.showMessageDialog(LoginView.this, "Đăng nhập thành công");
			}
		});
		loginBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		loginBtn.setBounds(480, 178, 299, 32);
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/login_icon.png"));
			Image sizedImg = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
			loginBtn.setIcon(new ImageIcon(sizedImg));
		} catch (Exception e) {
			System.out.println(e);
		}
		loginPanel.add(loginBtn);

		JPanel panel = new JPanel();
		panel.setBounds(480, 221, 299, 79);
		loginPanel.add(panel);
		panel.setLayout(null);

		JLabel signupLabel = new JLabel("Chưa có tài khoản?");
		signupLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		signupLabel.setBounds(4, 11, 176, 14);
		panel.add(signupLabel);

		JButton signupBtn = new JButton("ĐĂNG KÝ");
		signupBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormUtils.resetForm(mainPanel);
				dispose();
				new SignupView();
			}
		});
		signupBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		signupBtn.setBounds(148, 2, 113, 32);
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/signup_icon.png"));
			Image sizedImg = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
			signupBtn.setIcon(new ImageIcon(sizedImg));
		} catch (Exception e) {
			System.out.println(e);
		}
		panel.add(signupBtn);

		JLabel forgotPasswordLabel = new JLabel("Quên mật khẩu?");
		forgotPasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		forgotPasswordLabel.setBounds(4, 51, 154, 17);
		panel.add(forgotPasswordLabel);

		JButton forgotPasswordBtn = new JButton("LẤY LẠI MẬT KHẨU");
		forgotPasswordBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormUtils.resetForm(mainPanel);
				dispose();
				new ResetPasswordView();
			}
		});
		forgotPasswordBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		forgotPasswordBtn.setBounds(114, 43, 185, 32);
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/forgot_password_icon.png"));
			Image sizedImg = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
			forgotPasswordBtn.setIcon(new ImageIcon(sizedImg));
		} catch (Exception e) {
			System.out.println(e);
		}
		panel.add(forgotPasswordBtn);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
