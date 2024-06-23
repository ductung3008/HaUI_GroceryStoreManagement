package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.Icon;
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
import controller.ProductController;
import dao.BillDAO;
import dao.ProductDAO;
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
		categoryBtn.setBounds(366, 425, 200, 32);
		mainPanel.add(categoryBtn);

		JButton productBtn = new JButton("Quản lý sản phẩm");
		productBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ProductManagementView();
			}
		});
		productBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		productBtn.setBounds(366, 500, 200, 32);
		mainPanel.add(productBtn);

		JButton billBtn = new JButton("Quản lý hóa đơn");
		billBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new BillManagementView(user);
			}
		});
		billBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		billBtn.setBounds(366, 578, 200, 32);
		mainPanel.add(billBtn);

		JButton verifyUserBtn = new JButton("Xác minh tài khoản");
		verifyUserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VerifyUserView(user);
			}
		});
		verifyUserBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		verifyUserBtn.setBounds(700, 425, 200, 32);
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
					frame.getContentPane().add(new ChartPanel(barChart));
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		statBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		statBtn.setBounds(700, 500, 200, 32);
		mainPanel.add(statBtn);

		JButton changePassBtn = new JButton("Đổi mật khẩu");
		changePassBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChangePasswordView(user);
			}
		});
		changePassBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		changePassBtn.setBounds(700, 578, 200, 32);
		mainPanel.add(changePassBtn);

		JPanel productPanel = new JPanel();
		productPanel.setBounds(200, 300, 250, 101);
		mainPanel.add(productPanel);
		productPanel.setLayout(null);

		ImageIcon productIcon = new ImageIcon(getClass().getResource("/resources/product-icon.png"));
		Image productImg = productIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		productIcon = new ImageIcon(productImg);
		JLabel productImage = new JLabel(productIcon);
		productImage.setForeground(new Color(255, 200, 0));
		productImage.setBounds(0, 0, 101, 101);
		productImage.setOpaque(true);
		productPanel.add(productImage);

		JLabel productTitle = new JLabel("Sản phẩm tồn kho", SwingConstants.CENTER);
		productTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		productTitle.setBounds(111, 11, 139, 21);
		productPanel.add(productTitle);

		ProductDAO productDAO = new ProductDAO();
		ProductController productController = new ProductController(productDAO, null);
		try {
			int productNums = productController.getAllProducts().size();
			JLabel productNum = new JLabel(String.valueOf(productNums), SwingConstants.CENTER);
			productNum.setFont(new Font("Tahoma", Font.PLAIN, 40));
			productNum.setBounds(111, 43, 139, 47);
			productPanel.add(productNum);
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}

		ImageIcon billIcon = new ImageIcon(getClass().getResource("/resources/bill-icon.png"));
		Image billImg = billIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		billIcon = new ImageIcon(billImg);
		productPanel.add(productImage);
		JPanel billPanel = new JPanel();
		billPanel.setLayout(null);
		billPanel.setBounds(505, 300, 250, 101);
		mainPanel.add(billPanel);

		JLabel billImage = new JLabel((Icon) billIcon);
		billImage.setOpaque(true);
		billImage.setForeground(Color.ORANGE);
		billImage.setBounds(0, 0, 101, 101);
		billPanel.add(billImage);

		JLabel billTitle = new JLabel("Hoá đơn đã tạo", SwingConstants.CENTER);
		billTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		billTitle.setBounds(111, 11, 139, 21);
		billPanel.add(billTitle);

		BillDAO billDAO = new BillDAO();
		BillController billController = new BillController(billDAO, null);
		try {
			int billNums = billController.getAllBills().size();
			JLabel billNum = new JLabel(String.valueOf(billNums), SwingConstants.CENTER);
			billNum.setFont(new Font("Tahoma", Font.PLAIN, 40));
			billNum.setBounds(111, 43, 139, 47);
			billPanel.add(billNum);
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}

		JPanel revenuePanel = new JPanel();
		revenuePanel.setLayout(null);
		revenuePanel.setBounds(810, 300, 300, 101);
		mainPanel.add(revenuePanel);

		ImageIcon revenueIcon = new ImageIcon(getClass().getResource("/resources/revenue-icon.png"));
		Image revenueImg = revenueIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		revenueIcon = new ImageIcon(revenueImg);
		productPanel.add(productImage);

		JLabel revenueImage = new JLabel((Icon) revenueIcon);
		revenueImage.setOpaque(true);
		revenueImage.setForeground(Color.ORANGE);
		revenueImage.setBounds(0, 0, 101, 101);
		revenuePanel.add(revenueImage);

		JLabel revenueTitle = new JLabel("Doanh thu tháng này", SwingConstants.CENTER);
		revenueTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		revenueTitle.setBounds(111, 11, 189, 21);
		revenuePanel.add(revenueTitle);

		try {
			double revenue = 0;
			List<Bill> bills;
			bills = billController.getAllBills();
			LocalDate today = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
			String todayMonth = today.format(formatter);
			for (Bill bill : bills) {
				String month = bill.getDate().substring(3, 5);
				if (todayMonth.equals(month)) {
					revenue += bill.getTotal();
				}
			}
			revenue /= 1000000;
			String revenueStr = String.format("%.2f", revenue).concat("tr.đ");
			JLabel revenueNum = new JLabel(revenueStr, SwingConstants.CENTER);
			revenueNum.setFont(new Font("Tahoma", Font.PLAIN, 40));
			revenueNum.setBounds(111, 43, 189, 47);
			revenuePanel.add(revenueNum);
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}

		setLocationRelativeTo(null);
		setVisible(true);
	}
}
