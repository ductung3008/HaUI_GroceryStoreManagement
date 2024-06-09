package view;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;

public class AuthenticationView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel authenticationPanel;
	private JTextField usernameField;
	private JTextField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthenticationView frame = new AuthenticationView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AuthenticationView() {
		setTitle("HaUI Grocery Store Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		authenticationPanel = new JPanel();
		authenticationPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(authenticationPanel);
		authenticationPanel.setLayout(new BorderLayout(0, 0));

		ImageIcon logoIcon = new ImageIcon(getClass().getResource("/resources/haui_logo.png"));
		Image logoImg = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		logoIcon = new ImageIcon(logoImg);
		JLabel logoLabel = new JLabel(logoIcon);
		JPanel logoPanel = new JPanel();
		logoPanel.add(logoLabel);
		logoPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		authenticationPanel.add(logoPanel, BorderLayout.NORTH);

		JPanel loginPanel = new JPanel();
		authenticationPanel.add(loginPanel, BorderLayout.CENTER);
		loginPanel.setLayout(null);

		JPanel usernamePanel = new JPanel();
		usernamePanel.setBounds(480, 11, 299, 79);
		loginPanel.add(usernamePanel);
		usernamePanel.setLayout(null);

		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(4, 8, 61, 17);
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		usernamePanel.add(usernameLabel);

		usernameField = new JTextField();
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

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(4, 8, 58, 17);
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordPanel.add(passwordLabel);

		passwordField = new JTextField();
		passwordField.setBounds(0, 32, 299, 36);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField.setColumns(15);
		passwordField.setBorder(BorderFactory.createCompoundBorder(passwordField.getBorder(),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		passwordPanel.add(passwordField);
		
		JButton loginBtn = new JButton("LOGIN");
		loginBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		loginBtn.setBounds(480, 178, 299, 32);
		loginPanel.add(loginBtn);
		
		JPanel panel = new JPanel();
		panel.setBounds(480, 221, 310, 79);
		loginPanel.add(panel);
		panel.setLayout(null);
		
		JLabel signupLabel = new JLabel("Don't have an account?");
		signupLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		signupLabel.setBounds(4, 11, 176, 14);
		panel.add(signupLabel);
		
		JButton signupBtn = new JButton("SIGN UP");
		signupBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		signupBtn.setBounds(186, 2, 90, 32);
		panel.add(signupBtn);
		
		JLabel forgotPasswordLabel = new JLabel("Forgot your password?");
		forgotPasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		forgotPasswordLabel.setBounds(4, 51, 154, 17);
		panel.add(forgotPasswordLabel);
		
		JButton btnNewButton = new JButton("RESET PASSWORD");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(155, 43, 154, 32);
		panel.add(btnNewButton);
	}
}
