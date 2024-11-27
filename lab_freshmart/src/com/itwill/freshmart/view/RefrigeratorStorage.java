package com.itwill.freshmart.view;

import java.awt.EventQueue;
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

public class RefrigeratorStorage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField foodNameField;
	private JLabel imageLabel;
	private JComboBox<String> storageComboBox;
	private JTextField expirationDateField;
	private JTextField foodQuantityField;
	private String imagePath;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				RefrigeratorStorage frame = new RefrigeratorStorage();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public RefrigeratorStorage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblFoodName = new JLabel("식품 이름:");
		lblFoodName.setBounds(20, 20, 80, 20);
		contentPane.add(lblFoodName);

		foodNameField = new JTextField();
		foodNameField.setBounds(100, 20, 200, 25);
		contentPane.add(foodNameField);
		foodNameField.setColumns(10);

		JLabel lblStorage = new JLabel("보관 장소:");
		lblStorage.setBounds(20, 60, 80, 20);
		contentPane.add(lblStorage);

		storageComboBox = new JComboBox<>(new String[] { "냉장실", "냉동실" });
		storageComboBox.setBounds(100, 60, 200, 25);
		contentPane.add(storageComboBox);

		JLabel lblFoodCategory = new JLabel("식품 유형:");
		lblFoodCategory.setBounds(20, 100, 80, 20);
		contentPane.add(lblFoodCategory);

		JComboBox<String> categoryComboBox = new JComboBox<>();
		categoryComboBox.setBounds(100, 100, 200, 25);
		contentPane.add(categoryComboBox);

		List<String> foodCategories = FreshmartDao.INSTANCE.getFoodCategoryList();
		for (String category : foodCategories) {
			categoryComboBox.addItem(category);
		}

		JLabel lblExpirationDate = new JLabel("유통 기한:");
		lblExpirationDate.setBounds(20, 140, 80, 20);
		contentPane.add(lblExpirationDate);

		expirationDateField = new JTextField();
		expirationDateField.setBounds(100, 140, 200, 25);
		contentPane.add(expirationDateField);
		expirationDateField.setColumns(10);

		JLabel lblFoodQuantity = new JLabel("식품 갯수:");
		lblFoodQuantity.setBounds(20, 180, 80, 20);
		contentPane.add(lblFoodQuantity);

		foodQuantityField = new JTextField();
		foodQuantityField.setBounds(100, 180, 200, 25);
		contentPane.add(foodQuantityField);
		foodQuantityField.setColumns(10);

		JLabel lblImage = new JLabel("이미지:");
		lblImage.setBounds(20, 220, 80, 20);
		contentPane.add(lblImage);

		imageLabel = new JLabel("이미지가 선택되지 않았습니다.");
		imageLabel.setBounds(20, 250, 400, 300);
		contentPane.add(imageLabel);

		JButton btnSelectImage = new JButton("사진 첨부");
		btnSelectImage.setBounds(320, 60, 100, 25);
		btnSelectImage.addActionListener(e -> showImageChooser());
		contentPane.add(btnSelectImage);

		JButton btnSave = new JButton("저장");
		btnSave.setBounds(170, 450, 100, 30);
		btnSave.addActionListener(e -> saveFoodItem(categoryComboBox));
		contentPane.add(btnSave);
	}

	private void showImageChooser() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			imagePath = selectedFile.getAbsolutePath();

			try {
				ImageIcon icon = new ImageIcon(imagePath);
				imageLabel.setIcon(icon);
				imageLabel.setText("");
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "이미지를 불러올 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(this, "모든 항목을 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {

			if (expirationDateStr.isEmpty()) {
				JOptionPane.showMessageDialog(this, "올바른 날짜를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(this, "올바른 날짜를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (expirationDate.isBefore(LocalDate.now())) {
				JOptionPane.showMessageDialog(this, "올바른 유통기한을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
				return;
			}

			int foodQuantity = 0;
			try {
				foodQuantity = Integer.parseInt(foodQuantityStr);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "올바른 갯수를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
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