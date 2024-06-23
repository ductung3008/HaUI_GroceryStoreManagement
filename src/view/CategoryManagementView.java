package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import javax.imageio.ImageIO;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import controller.CategoryController;
import dao.CategoryDAO;
import model.Category;
import model.User;
import util.ButtonHover;

public class CategoryManagementView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JTextField searchField;
	private Timer searchTimer;
	private JTable table;
	private CategoryController categoryController;
	private CategoryDAO categoryDAO;

	/**
	 * Create the frame.
	 */
	public CategoryManagementView(User user) {
		categoryDAO = new CategoryDAO();
		categoryController = new CategoryController(categoryDAO, this);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				new Home(user);
			}
		});

		this.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(CategoryManagementView.class.getResource("/resources/product-management1.png")));
		setResizable(false);
		setTitle("HaUI Grocery Store Management");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 840, 720);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout(0, 0));

		JLabel titleLabel = new JLabel("QUẢN LÝ LOẠI SẢN PHẨM", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		mainPanel.add(titleLabel, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		mainPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel headerPanel = new JPanel();
		panel.add(headerPanel, BorderLayout.NORTH);
		FlowLayout fl_headerPanel = new FlowLayout(FlowLayout.LEFT, 5, 5);
		headerPanel.setLayout(fl_headerPanel);

		JButton addBtn = new JButton("THÊM");
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CategoryView(categoryController, null, false);
			}
		});

		addBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		headerPanel.add(addBtn);
		addBtn.setOpaque(false);
		addBtn.setContentAreaFilled(false);
		addBtn.setBorderPainted(false);
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/add-icon.png"));
			Image sizedImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			addBtn.setIcon(new ImageIcon(sizedImg));
			addBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			addBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		} catch (Exception e) {
			System.out.println(e);
		}
		ButtonHover.addButtonHover(addBtn);

		JButton editBtn = new JButton("SỬA");
		editBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Category category = getSelectedCategory();
				if (category == null) {
					JOptionPane.showMessageDialog(CategoryManagementView.this, "Vui lòng chọn loại sản phẩm", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				new CategoryView(categoryController, category, true);
			}
		});
		editBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		headerPanel.add(editBtn);
		editBtn.setOpaque(false);
		editBtn.setContentAreaFilled(false);
		editBtn.setBorderPainted(false);
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/edit-icon.png"));
			Image sizedImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			editBtn.setIcon(new ImageIcon(sizedImg));
			editBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			editBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		} catch (Exception e) {
			System.out.println(e);
		}
		ButtonHover.addButtonHover(editBtn);

		JButton deleteBtn = new JButton("XÓA");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Category category = getSelectedCategory();
				if (category == null) {
					JOptionPane.showMessageDialog(CategoryManagementView.this, "Vui lòng chọn loại sản phẩm", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				int option = JOptionPane.showConfirmDialog(CategoryManagementView.this,
						"Bạn có chắc chắn muốn xóa không?");

				if (option == 0) {
					try {
						if (!categoryController.deleteCategory(category)) {
							JOptionPane.showMessageDialog(CategoryManagementView.this, "Xóa thất bại", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					} catch (HeadlessException | ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}

					try {
						updateCategoryTable(categoryController.getAllCategories());
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(CategoryManagementView.this, "Xóa thành công");
				}
			}
		});
		deleteBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		headerPanel.add(deleteBtn);
		deleteBtn.setOpaque(false);
		deleteBtn.setContentAreaFilled(false);
		deleteBtn.setBorderPainted(false);
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/delete-icon.png"));
			Image sizedImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			deleteBtn.setIcon(new ImageIcon(sizedImg));
			deleteBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			deleteBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		} catch (Exception e) {
			System.out.println(e);
		}
		ButtonHover.addButtonHover(deleteBtn);

		JButton detailBtn = new JButton("XEM CHI TIẾT");
		detailBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Category category = getSelectedCategory();
				if (category == null) {
					JOptionPane.showMessageDialog(CategoryManagementView.this, "Vui lòng chọn loại sản phẩm", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				new ProductByCategoryView(category);
			}
		});
		detailBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		headerPanel.add(detailBtn);
		detailBtn.setOpaque(false);
		detailBtn.setContentAreaFilled(false);
		detailBtn.setBorderPainted(false);
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/detail-icon.png"));
			Image sizedImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			detailBtn.setIcon(new ImageIcon(sizedImg));
			detailBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			detailBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		} catch (Exception e) {
			System.out.println(e);
		}
		ButtonHover.addButtonHover(detailBtn);

		JButton exportBtn = new JButton("XUẤT RA PDF");
		exportBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MessageFormat header = new MessageFormat(titleLabel.getText());
				MessageFormat footer = new MessageFormat("HaUI Grocery Store");
				try {
					PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
					set.add(OrientationRequested.PORTRAIT);
					table.print(JTable.PrintMode.FIT_WIDTH, header, footer, true, set, true);
					JOptionPane.showMessageDialog(null, "Print successfully");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		exportBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		headerPanel.add(exportBtn);
		exportBtn.setOpaque(false);
		exportBtn.setContentAreaFilled(false);
		exportBtn.setBorderPainted(false);
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/export-to-pdf-icon.png"));
			Image sizedImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			exportBtn.setIcon(new ImageIcon(sizedImg));
			exportBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			exportBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		} catch (Exception e) {
			System.out.println(e);
		}
		ButtonHover.addButtonHover(exportBtn);

		JPanel searchPanel = new JPanel();
		headerPanel.add(searchPanel);
		searchPanel.setLayout(new BorderLayout(0, 0));

		searchTimer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = searchField.getText();
				try {
					categoryController.searchCategories(name);
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		searchTimer.setRepeats(false);

		searchField = new JTextField();
		searchField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		searchPanel.add(searchField);
		searchField.setColumns(20);
		searchField.setBorder(BorderFactory.createCompoundBorder(searchField.getBorder(),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		searchField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				restartTimer();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				restartTimer();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				restartTimer();
			}

			private void restartTimer() {
				if (searchTimer.isRunning()) {
					searchTimer.restart();
				} else {
					searchTimer.start();
				}
			}
		});

		JLabel searchLabel = new JLabel("Tìm kiếm");
		searchLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		searchPanel.add(searchLabel, BorderLayout.NORTH);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "T\u00EAn lo\u1EA1i s\u1EA3n ph\u1EA9m", "M\u00F4 t\u1EA3" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(0).setMinWidth(40);
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.setRowHeight(30);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollPane, BorderLayout.CENTER);

		try {
			updateCategoryTable(categoryController.getAllCategories());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void updateCategoryTable(List<Category> categories) {
		DefaultTableModel dtm = (DefaultTableModel) this.table.getModel();
		dtm.setRowCount(0);
		categories.forEach(category -> {
			dtm.addRow(
					new Object[] { String.valueOf(category.getId()), category.getName(), category.getDescription() });
		});
	}

	public Category getSelectedCategory() {
		DefaultTableModel dtm = (DefaultTableModel) this.table.getModel();
		int row = table.getSelectedRow();
		if (row == -1)
			return null;
		int id = Integer.valueOf(String.valueOf(dtm.getValueAt(row, 0)));
		try {
			Category category = categoryController.getCategoryById(id);
			return category;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
