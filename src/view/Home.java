package view;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home extends JFrame {

	private static final long serialVersionUID = 1L;
	private User user;
	private JPanel mainPanel;

	public static void main(String[] args) {
		new Home(new User());
	}
	
	/**
	 * Create the frame.
	 */
	public Home(User user) {
		this.user = user;
		Image icon = Toolkit.getDefaultToolkit().getImage(SignupView.class.getResource("/resources/logo.png"));
		this.setIconImage(icon);
		setResizable(false);
		setTitle("HaUI Grocery Store Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainPanel);
		mainPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 348, 50);
		mainPanel.add(panel);
		panel.setLayout(null);
		
		String helloText = "Xin chào, " + user.getUsername() + "!";
		JLabel helloLabel = new JLabel(helloText);
		helloLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		helloLabel.setBounds(10, 11, 328, 28);
		panel.add(helloLabel);
		
		JLabel titleLabel = new JLabel("Hệ thống quản lý tiệm tạp hóa HaUI", SwingConstants.CENTER);
		titleLabel.setForeground(Color.ORANGE);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		titleLabel.setBounds(0, 41, 1264, 101);
		mainPanel.add(titleLabel);
		
		JButton logoutBtn = new JButton("ĐĂNG XUẤT");
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(Home.this, "Bạn sẽ đăng xuất khỏi hệ thống?");
				if (option == 0) {
					dispose();
					new LoginView();
				}
			}
		});
		logoutBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		logoutBtn.setBounds(1128, 11, 126, 32);
		mainPanel.add(logoutBtn);
		
		ImageIcon logoIcon = new ImageIcon(getClass().getResource("/resources/haui_logo.png"));
		Image logoImg = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		logoIcon = new ImageIcon(logoImg);
		JLabel logoLabel = new JLabel(logoIcon);
		logoLabel.setBounds(10, 131, 1244, 150);
		mainPanel.add(logoLabel);
		
		JButton btnNewButton = new JButton("Quản lý loại sản phẩm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CategoryManagementView();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(365, 315, 200, 32);
		mainPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Quản lý sản phẩm");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.setBounds(365, 390, 200, 32);
		mainPanel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Quản lý hóa đơn");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_2.setBounds(365, 468, 200, 32);
		mainPanel.add(btnNewButton_2);
		
		JButton verifyUserBtn = new JButton("Xác minh tài khoản");
		verifyUserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VerifyUserView(user);
			}
		});
		verifyUserBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		verifyUserBtn.setBounds(699, 315, 200, 32);
		mainPanel.add(verifyUserBtn);
		
		JButton btnNewButton_4 = new JButton("Thống kê");
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_4.setBounds(699, 390, 200, 32);
		mainPanel.add(btnNewButton_4);
		
		JButton changePassBtn = new JButton("Đổi mật khẩu");
		changePassBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChangePasswordView(user);
			}
		});
		changePassBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		changePassBtn.setBounds(699, 468, 200, 32);
		mainPanel.add(changePassBtn);
		
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
