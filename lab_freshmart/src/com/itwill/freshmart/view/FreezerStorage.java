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
	private JButton btnCheck;

	private Component parentComponent;
	private FreezerNotify app;

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
		contentPane.setLayout(null);
		
		setTitle("냉동실");

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("C:\\Users\\MYCOM\\Desktop\\RefrigeratorStorageImage\\2.jpg");

		this.setIconImage(img);

		setSize(482, 585);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 482, 308);
		
		setLocationRelativeTo(parentComponent);
		

		lblFoodName = new JLabel("식품 이름:");
		lblFoodName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblFoodName.setBounds(237, 10, 80, 20);
		contentPane.add(lblFoodName);

		foodNameField = new JTextField();
		foodNameField.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		foodNameField.setBounds(317, 10, 131, 25);
		contentPane.add(foodNameField);
		foodNameField.setColumns(10);

		lblFoodCategory = new JLabel("식품 유형:");
		lblFoodCategory.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblFoodCategory.setBounds(237, 40, 80, 20);
		contentPane.add(lblFoodCategory);

		categoryComboBox_1 = new JComboBox<>();
		categoryComboBox_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		categoryComboBox_1.setBounds(317, 40, 131, 25);
		contentPane.add(categoryComboBox_1);

		List<String> foodCategories = FreshmartDao.INSTANCE.getFoodCategoryList();
		for (String category : foodCategories) {
			categoryComboBox_1.addItem(category);
		}

		lblExpirationDate = new JLabel("유통 기한:");
		lblExpirationDate.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblExpirationDate.setBounds(237, 70, 80, 20);
		contentPane.add(lblExpirationDate);

		expirationDateField = new JTextField();
		expirationDateField.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		expirationDateField.setBounds(317, 70, 131, 25);
		contentPane.add(expirationDateField);
		expirationDateField.setColumns(10);

		lblFoodQuantity = new JLabel("식품 개수:");
		lblFoodQuantity.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblFoodQuantity.setBounds(237, 100, 80, 20);
		contentPane.add(lblFoodQuantity);

		foodQuantityField = new JTextField();
		foodQuantityField.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		foodQuantityField.setBounds(317, 100, 131, 25);
		contentPane.add(foodQuantityField);
		foodQuantityField.setColumns(10);

		imageLabel = new JLabel("이미지가 선택되지 않았습니다.");
		imageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setBounds(12, 10, 212, 249);
		contentPane.add(imageLabel);

		btnCheck = new JButton("확인");
		btnCheck.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnCheck.setBounds(237, 130, 212, 30);
		btnCheck.addActionListener(e -> {
			dispose();
		});
		contentPane.add(btnCheck);
	}
}
