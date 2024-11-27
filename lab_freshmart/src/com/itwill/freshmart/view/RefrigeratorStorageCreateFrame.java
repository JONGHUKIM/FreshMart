package com.itwill.freshmart.view;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import javax.swing.border.EmptyBorder;

import com.itwill.freshmart.controller.FreshmartDao;
import com.itwill.freshmart.model.Freshmart;
import javax.swing.SwingConstants;
import java.awt.Font;

public class RefrigeratorStorageCreateFrame extends JFrame {

	public interface CreateNotify {
		void notifyCreateSuccess();
	}

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField foodNameField;
	private JLabel imageLabel;
	private JComboBox<String> storageComboBox;
	private JTextField expirationDateField;
	private JTextField foodQuantityField;
	private String imagePath;
	private JLabel lblFoodName;
	private JLabel lblStorage;
	private JLabel lblFoodCategory;
	private JLabel lblExpirationDate;
	private JLabel lblFoodQuantity;
	private JLabel lblImage;
	private JButton btnSelectImage;
	private JButton btnSave;
	private JComboBox<String> categoryComboBox_1;
	private JButton btnCancel;

	private Component parentComponent;
	private CreateNotify app;

	public static void showRefrigeratorStorageCreateFrame(Component parentComponent, CreateNotify app) {
		EventQueue.invokeLater(() -> {
			try {
				RefrigeratorStorageCreateFrame frame = new RefrigeratorStorageCreateFrame(parentComponent, app);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private RefrigeratorStorageCreateFrame(Component parentComponent, CreateNotify app) {

		this.parentComponent = parentComponent;
		this.app = app;
		initialize();
	}

	public void initialize() {
		setTitle("보관하기");

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("C:\\Users\\MYCOM\\Desktop\\RefrigeratorStorageImage\\2.jpg");

		this.setIconImage(img);

		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 482, 308);
		
		setLocationRelativeTo(parentComponent);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblFoodName = new JLabel("식품 이름:");
		lblFoodName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblFoodName.setBounds(236, 74, 80, 20);
		contentPane.add(lblFoodName);

		foodNameField = new JTextField();
		foodNameField.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		foodNameField.setBounds(316, 74, 131, 25);
		contentPane.add(foodNameField);
		foodNameField.setColumns(10);

		lblStorage = new JLabel("보관 장소:");
		lblStorage.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblStorage.setBounds(236, 104, 80, 20);
		contentPane.add(lblStorage);

		storageComboBox = new JComboBox<>(new String[] { "냉장실", "냉동실" });
		storageComboBox.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		storageComboBox.setBounds(316, 104, 131, 25);
		contentPane.add(storageComboBox);

		lblFoodCategory = new JLabel("식품 유형:");
		lblFoodCategory.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblFoodCategory.setBounds(236, 134, 80, 20);
		contentPane.add(lblFoodCategory);

		categoryComboBox_1 = new JComboBox<>();
		categoryComboBox_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		categoryComboBox_1.setBounds(316, 134, 131, 25);
		contentPane.add(categoryComboBox_1);

		List<String> foodCategories = FreshmartDao.INSTANCE.getFoodCategoryList();
		for (String category : foodCategories) {
			categoryComboBox_1.addItem(category);
		}

		lblExpirationDate = new JLabel("유통 기한:");
		lblExpirationDate.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblExpirationDate.setBounds(236, 164, 80, 20);
		contentPane.add(lblExpirationDate);

		expirationDateField = new JTextField();
		expirationDateField.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		expirationDateField.setBounds(316, 164, 131, 25);
		contentPane.add(expirationDateField);
		expirationDateField.setColumns(10);

		lblFoodQuantity = new JLabel("식품 갯수:");
		lblFoodQuantity.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblFoodQuantity.setBounds(236, 194, 80, 20);
		contentPane.add(lblFoodQuantity);

		foodQuantityField = new JTextField();
		foodQuantityField.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		foodQuantityField.setBounds(316, 194, 131, 25);
		contentPane.add(foodQuantityField);
		foodQuantityField.setColumns(10);

		lblImage = new JLabel("이미지:");
		lblImage.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblImage.setBounds(12, 10, 80, 20);
		contentPane.add(lblImage);

		imageLabel = new JLabel("이미지가 선택되지 않았습니다.");
		imageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setBounds(12, 34, 212, 225);
		contentPane.add(imageLabel);

		btnSelectImage = new JButton("사진 첨부");
		btnSelectImage.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnSelectImage.setBounds(67, 8, 157, 25);
		btnSelectImage.addActionListener(e -> showImageChooser());
		contentPane.add(btnSelectImage);

		btnSave = new JButton("저장");
		btnSave.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnSave.setBounds(236, 229, 100, 30);
		btnSave.addActionListener(e -> saveFoodItem(categoryComboBox_1));
		contentPane.add(btnSave);

		btnCancel = new JButton("취소");
		btnCancel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnCancel.setBounds(348, 229, 100, 30);
		btnCancel.addActionListener(e -> {
			dispose();
		});
		contentPane.add(btnCancel);
	}

	private void showImageChooser() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			imagePath = selectedFile.getAbsolutePath();

			try {

				ImageIcon originalIcon = new ImageIcon(imagePath);

				int labelWidth = imageLabel.getWidth();
				int labelHeight = imageLabel.getHeight();

				Image image = originalIcon.getImage();
				Image resizedImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);

				ImageIcon resizedIcon = new ImageIcon(resizedImage);
				imageLabel.setIcon(resizedIcon);
				imageLabel.setText("");

			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "이미지를 불러올 수 없습니다.", "WARNIG", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private void saveFoodItem(JComboBox<String> categoryComboBox) {
		String foodName = foodNameField.getText();
		String storage = (String) storageComboBox.getSelectedItem();
		String selectedCategory = (String) categoryComboBox.getSelectedItem();
		String expirationDateStr = expirationDateField.getText();
		String foodQuantityStr = foodQuantityField.getText();

		if (foodName.isEmpty() || storage == null || selectedCategory == null || expirationDateStr.isEmpty()
				|| foodQuantityStr.isEmpty()) {
			JOptionPane.showMessageDialog(this, "모든 항목을 입력해주세요.", "WARNIG", JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {

			if (expirationDateStr.isEmpty()) {
				JOptionPane.showMessageDialog(this, "올바른 날짜를 입력하세요.", "WARNIG", JOptionPane.WARNING_MESSAGE);
				return;
			}

			DateTimeFormatter[] formatters = { DateTimeFormatter.ofPattern("yyyyMMdd"),
					DateTimeFormatter.ofPattern("yyyy-MM-dd"), DateTimeFormatter.ofPattern("yyyy/MM/dd") };

			LocalDate expirationDate = null;

			for (DateTimeFormatter formatter : formatters) {
				try {
					expirationDate = LocalDate.parse(expirationDateStr, formatter);
					break;
				} catch (DateTimeParseException e) {

				}
			}

			if (expirationDate == null) {
				JOptionPane.showMessageDialog(this, "올바른 날짜를 입력하세요.", "WARNIG", JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (expirationDate.isBefore(LocalDate.now())) {
				JOptionPane.showMessageDialog(this, "올바른 유통기한을 입력하세요.", "WARNIG", JOptionPane.WARNING_MESSAGE);
				return;
			}

			int foodQuantity = 0;
			try {
				foodQuantity = Integer.parseInt(foodQuantityStr);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "올바른 갯수를 입력하세요.", "WARNIG", JOptionPane.WARNING_MESSAGE);
				return;
			}

			int typeid = FreshmartDao.INSTANCE.getFoodCategoryIdByName(selectedCategory);

			Freshmart freshmart = Freshmart.builder().typeid(typeid).foodname(foodName).expirationdate(expirationDate)
					.storage(storage.equals("냉장실") ? "냉장실" : "냉동실").foodquantity(foodQuantity).img(imagePath).build();

			int result = FreshmartDao.INSTANCE.create(freshmart);
			if (result == 1) {
				JOptionPane.showMessageDialog(this, "저장되었습니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "저장에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
		}
	}
}