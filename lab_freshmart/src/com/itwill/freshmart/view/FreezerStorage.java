package com.itwill.freshmart.view;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.itwill.freshmart.controller.FreshmartDao;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class FreezerStorage extends JFrame {

	public interface FreezerNotify {
		void notifyCreateSuccess();
	}

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField foodNameField;
	private JLabel imageLabel;
	private JTextField expirationDateField;
	private JTextField foodQuantityField;
	private String imagePath;
	private JLabel lblFoodName;
	private JLabel lblFoodCategory;
	private JLabel lblExpirationDate;
	private JLabel lblFoodQuantity;

	private JComboBox<String> categoryComboBox_1;
	private JButton btnSearch;

	private Component parentComponent;
	private FreezerNotify app;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnList;
	private JButton btnClose;
	private JScrollPane scrollPane;
	private JTable table;

	public static void showFreezerStorage(Component parentComponent, FreezerNotify app) {
		EventQueue.invokeLater(() -> {
			try {
				FreezerStorage frame = new FreezerStorage(parentComponent, app);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private FreezerStorage(Component parentComponent, FreezerNotify app) {

		this.parentComponent = parentComponent;
		this.app = app;
		initialize();
	}

	public void initialize() {
		
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		setTitle("냉동실");

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("C:\\Users\\MYCOM\\Desktop\\RefrigeratorStorageImage\\2.jpg");

		this.setIconImage(img);

		setSize(480, 567);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// setBounds(100, 100, 482, 308);
		
		setLocationRelativeTo(parentComponent);
		contentPane.setLayout(null);
		

		lblFoodName = new JLabel("식품 이름:");
		lblFoodName.setBounds(237, 10, 80, 20);
		lblFoodName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		contentPane.add(lblFoodName);

		foodNameField = new JTextField();
		foodNameField.setBounds(317, 10, 131, 25);
		foodNameField.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		contentPane.add(foodNameField);
		foodNameField.setColumns(10);

		lblFoodCategory = new JLabel("식품 유형:");
		lblFoodCategory.setBounds(237, 40, 80, 20);
		lblFoodCategory.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		contentPane.add(lblFoodCategory);

		categoryComboBox_1 = new JComboBox<>();
		categoryComboBox_1.setBounds(317, 40, 131, 25);
		categoryComboBox_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		contentPane.add(categoryComboBox_1);

		List<String> foodCategories = FreshmartDao.INSTANCE.getFoodCategoryList();
		for (String category : foodCategories) {
			categoryComboBox_1.addItem(category);
		}

		lblExpirationDate = new JLabel("유통 기한");
		lblExpirationDate.setBounds(296, 100, 80, 20);
		lblExpirationDate.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		contentPane.add(lblExpirationDate);

		expirationDateField = new JTextField();
		expirationDateField.setBounds(237, 130, 211, 129);
		expirationDateField.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		contentPane.add(expirationDateField);
		expirationDateField.setColumns(10);

		lblFoodQuantity = new JLabel("식품 개수:");
		lblFoodQuantity.setBounds(237, 70, 80, 20);
		lblFoodQuantity.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		contentPane.add(lblFoodQuantity);

		foodQuantityField = new JTextField();
		foodQuantityField.setBounds(317, 70, 131, 25);
		foodQuantityField.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		contentPane.add(foodQuantityField);
		foodQuantityField.setColumns(10);

		imageLabel = new JLabel("이미지가 선택되지 않았습니다.");
		imageLabel.setBounds(12, 10, 212, 249);
		imageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(imageLabel);

		btnSearch = new JButton("검색");
		btnSearch.setBounds(0, 488, 80, 30);
		btnSearch.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnSearch.addActionListener(e -> {
			dispose();
		});
		contentPane.add(btnSearch);
		
		btnUpdate = new JButton("수정");
		btnUpdate.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnUpdate.setBounds(92, 488, 80, 30);
		contentPane.add(btnUpdate);
		
		btnDelete = new JButton("삭제");
		btnDelete.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnDelete.setBounds(184, 488, 80, 30);
		contentPane.add(btnDelete);
		
		btnList = new JButton("정렬");
		btnList.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnList.setBounds(276, 488, 80, 30);
		contentPane.add(btnList);
		
		btnClose = new JButton("닫기");
		btnClose.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnClose.setBounds(368, 488, 80, 30);
		contentPane.add(btnClose);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 269, 436, 209);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
}
