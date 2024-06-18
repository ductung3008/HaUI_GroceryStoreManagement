package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import controller.BillController;
import dao.BillDAO;
import model.Bill;
import model.User;

public class Home extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;

	/**
	 * Create the frame.
	 */
	public Home(User user) {
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

		JButton categoryBtn = new JButton("Quản lý loại sản phẩm");
		categoryBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CategoryManagementView();
			}
		});
		categoryBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		categoryBtn.setBounds(365, 315, 200, 32);
		mainPanel.add(categoryBtn);

		JButton productBtn = new JButton("Quản lý sản phẩm");
		productBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ProductManagementView();
			}
		});
		productBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		productBtn.setBounds(365, 390, 200, 32);
		mainPanel.add(productBtn);

		JButton billBtn = new JButton("Quản lý hóa đơn");
		billBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new BillManagementView(user);
			}
		});
		billBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		billBtn.setBounds(365, 468, 200, 32);
		mainPanel.add(billBtn);

		JButton verifyUserBtn = new JButton("Xác minh tài khoản");
		verifyUserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VerifyUserView(user);
			}
		});
		verifyUserBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		verifyUserBtn.setBounds(699, 315, 200, 32);
		mainPanel.add(verifyUserBtn);

		JButton statBtn = new JButton("Thống kê");
		statBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				Map<String, Double> revenue = new TreeMap<String, Double>();

				BillDAO billDAO = new BillDAO();
				BillController billController = new BillController(billDAO, null);
				try {
					List<Bill> bills = billController.getAllBills();

					for (int i = 1; i <= 12; i++) {
						String month = String.format("%02d", i);
						revenue.put(month, revenue.getOrDefault(month, 0.0));
					}

					for (Bill bill : bills) {
						String month = bill.getDate().substring(3, 5);
						revenue.put(month, revenue.getOrDefault(month, 0.0) + bill.getTotal());
					}

					for (Map.Entry<String, Double> entry : revenue.entrySet()) {
						dataset.addValue(entry.getValue(), "Doanh thu (đồng)", entry.getKey());
					}

					JFreeChart barChart = ChartFactory.createBarChart("Thống kê doanh thu", "Tháng", "Doanh thu (đồng)",
							dataset, PlotOrientation.VERTICAL, true, true, false);

					JFrame frame = new JFrame();
					frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					frame.setSize(960, 540);
					frame.add(new ChartPanel(barChart));
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		statBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		statBtn.setBounds(699, 390, 200, 32);
		mainPanel.add(statBtn);

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
