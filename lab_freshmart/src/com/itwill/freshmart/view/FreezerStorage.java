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
import com.itwill.freshmart.view.RefrigeratorStorageCreateFrame.CreateNotify;

public class FreezerStorage extends JFrame {

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

	public static void showFreezerStorage(Component parentComponent, CreateNotify app) {
		EventQueue.invokeLater(() -> {
			try {
				FreezerStorage frame = new FreezerStorage(parentComponent, app);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private FreezerStorage(Component parentComponent, CreateNotify app) {

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

}
