package view;

import java.awt.Image;

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
import util.SendMail;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class SignupView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JTextField usernameField;
	private JTextField passwordField;
	private JTextField rePasswordField;
	private JTextField emailField;
	private JTextField otpField;
	private SendMail sm;
	private UserDAO userDAO;

	/**
	 * Create the frame.
	 */
	public SignupView() {
		sm = new SendMail();
		userDAO = new UserDAO();
		Image icon = Toolkit.getDefaultToolkit().getImage(SignupView.class.getResource("/resources/logo.png"));
		this.setIconImage(icon);
		setResizable(false);
		setTitle("Đăng ký tài khoản");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 760);
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

		JPanel signupPanel = new JPanel();
		mainPanel.add(signupPanel, BorderLayout.CENTER);
		signupPanel.setLayout(null);

		JPanel usernamePanel = new JPanel();
		usernamePanel.setBounds(169, 11, 299, 79);
		signupPanel.add(usernamePanel);
		usernamePanel.setLayout(null);

		JLabel usernameLabel = new JLabel("Tên đăng nhập");
		usernameLabel.setBounds(4, 8, 120, 17);
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
		passwordPanel.setBounds(169, 88, 299, 79);
		signupPanel.add(passwordPanel);
		passwordPanel.setLayout(null);

		JLabel passwordLabel = new JLabel("Mật khẩu");
		passwordLabel.setBounds(4, 8, 58, 17);
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordPanel.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setToolTipText("Hãy nhập tên mật khẩu");
		passwordField.setBounds(0, 32, 299, 36);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField.setColumns(15);
		passwordField.setBorder(BorderFactory.createCompoundBorder(passwordField.getBorder(),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		passwordPanel.add(passwordField);

		JButton signupBtn = new JButton("ĐĂNG KÝ");
		signupBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!FormUtils.ValidateForm(mainPanel)) {
					JOptionPane.showMessageDialog(SignupView.this, "Vui lòng nhập đầy đủ thông tin", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String username = usernameField.getText();
				String password = passwordField.getText();
				String rePassword = rePasswordField.getText();
				String email = emailField.getText();
				String otp = otpField.getText();

				if (!password.equals(rePassword)) {
					JOptionPane.showMessageDialog(SignupView.this, "Vui lòng kiểm tra lại mật khẩu", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (!otp.equals(sm.getOtp())) {
					JOptionPane.showMessageDialog(SignupView.this, "Vui lòng kiểm tra lại mã xác nhận", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				User user = new User(username, email, password, false);
				try {
					if (!userDAO.add(user)) {
						JOptionPane.showMessageDialog(SignupView.this,
								"Đã tồn tại tài khoản với tên tài khoản hoặc email này. Vui lòng thử lại.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				JOptionPane.showMessageDialog(SignupView.this, "Đăng ký thành công");

				FormUtils.resetForm(mainPanel);
				dispose();
				new LoginView();
			}
		});
		signupBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		signupBtn.setBounds(169, 413, 299, 32);
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/signup_icon.png"));
			Image sizedImg = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
			signupBtn.setIcon(new ImageIcon(sizedImg));
		} catch (Exception e) {
			System.out.println(e);
		}
		signupPanel.add(signupBtn);

		JPanel loginPanel = new JPanel();
		loginPanel.setBounds(169, 456, 299, 43);
		signupPanel.add(loginPanel);
		loginPanel.setLayout(null);

		JLabel loginLabel = new JLabel("Đã có tài khoản?");
		loginLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		loginLabel.setBounds(4, 11, 176, 14);
		loginPanel.add(loginLabel);

		JButton loginBtn = new JButton("ĐĂNG NHẬP");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormUtils.resetForm(mainPanel);
				dispose();
				new LoginView();
			}
		});
		loginBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		loginBtn.setBounds(155, 2, 133, 32);
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/login_icon.png"));
			Image sizedImg = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
			loginBtn.setIcon(new ImageIcon(sizedImg));
		} catch (Exception e) {
			System.out.println(e);
		}
		loginPanel.add(loginBtn);

		JPanel rePasswordPanel = new JPanel();
		rePasswordPanel.setLayout(null);
		rePasswordPanel.setBounds(169, 167, 299, 79);
		signupPanel.add(rePasswordPanel);

		JLabel rePasswordLabel = new JLabel("Nhập lại mật khẩu");
		rePasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rePasswordLabel.setBounds(4, 8, 130, 17);
		rePasswordPanel.add(rePasswordLabel);

		rePasswordField = new JPasswordField();
		rePasswordField.setToolTipText("Hãy nhập lại mật khẩu");
		rePasswordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rePasswordField.setColumns(15);
		rePasswordField.setBorder(BorderFactory.createCompoundBorder(rePasswordField.getBorder(),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		rePasswordField.setBounds(0, 32, 299, 36);
		rePasswordPanel.add(rePasswordField);

		JPanel emailPanel = new JPanel();
		emailPanel.setLayout(null);
		emailPanel.setBounds(169, 245, 299, 79);
		signupPanel.add(emailPanel);

		JLabel emailLabel = new JLabel("Email");
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		emailLabel.setBounds(4, 8, 130, 17);
		emailPanel.add(emailLabel);

		emailField = new JTextField();
		emailField.setToolTipText("Hãy nhập email");
		emailField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		emailField.setColumns(15);
		emailField.setBorder(BorderFactory.createCompoundBorder(emailField.getBorder(),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		emailField.setBounds(0, 32, 299, 36);
		emailPanel.add(emailField);

		JPanel otpPanel = new JPanel();
		otpPanel.setLayout(null);
		otpPanel.setBounds(169, 323, 299, 79);
		signupPanel.add(otpPanel);

		JLabel otpLabel = new JLabel("Mã xác nhận");
		otpLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		otpLabel.setBounds(4, 8, 130, 17);
		otpPanel.add(otpLabel);

		otpField = new JTextField();
		otpField.setToolTipText("Hãy nhập mã xác nhận");
		otpField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		otpField.setColumns(15);
		otpField.setBorder(BorderFactory.createCompoundBorder(otpField.getBorder(),

				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		otpField.setBounds(0, 32, 299, 36);
		otpPanel.add(otpField);

		JButton otpBtn = new JButton("GỬI MÃ");
		otpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String email = emailField.getText();

				if (email.isEmpty()) {
					JOptionPane.showMessageDialog(SignupView.this, "Vui lòng điền email.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					if (userDAO.isUserExist(username, email)) {
						JOptionPane.showMessageDialog(SignupView.this,
								"Đã tồn tại tài khoản với tên tài khoản hoặc email này. Vui lòng thử lại.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				if (!sm.sendOtp(emailField.getText())) {
					JOptionPane.showMessageDialog(SignupView.this,
							"Có lỗi trong quá trình lấy mã xác nhận. Vui lòng thử lại", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				JOptionPane.showMessageDialog(SignupView.this, "Mã xác nhận đã được gửi vào email. Vui lòng kiểm tra.");
			}
		});
		otpBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		otpBtn.setBounds(478, 354, 84, 37);
		signupPanel.add(otpBtn);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
