package com.itwill.freshmart.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.itwill.freshmart.controller.FreshmartDao;
import com.itwill.freshmart.model.Freshmart;

public class FreezerStorage extends JFrame {

	public interface FreezerNotify {
		void notifyUpdateSuccess();
	}

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel imageLabel;
	private JLabel lblFoodName;
	private JLabel lblFoodCategory;
	private JLabel lblExpirationDate;
	private JLabel lblFoodQuantity;
	private JButton btnDetails;
	private Component parentComponent;
	private FreezerNotify app;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnList;
	private JButton btnClose;
	private JScrollPane scrollPane;
	private JTable table;

	private JLabel foodNameLabel;
	private JLabel foodQuantityLabel;
	private JLabel expirationDateLabel;

	private FreshmartDao freshmartDao;

	public static void showFreezerStorage(Component parentComponent,
			RefrigeratorStorageCreateFrame refrigeratorStorageCreateFrame, FreezerNotify app) {
		EventQueue.invokeLater(() -> {
			try {
				FreezerStorage frame = new FreezerStorage(parentComponent, refrigeratorStorageCreateFrame, app);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public FreezerStorage(Component parentComponent, RefrigeratorStorageCreateFrame refrigeratorStorageCreateFrame,
			FreezerNotify app) {
		this.freshmartDao = FreshmartDao.INSTANCE;
		this.app = app;
		this.parentComponent = parentComponent;
		initialize();
		initializeTable();
		setLocationRelativeTo(parentComponent);
	}

	public void initialize() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		setTitle("냉동실");

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("C:\\Users\\MYCOM\\Desktop\\RefrigeratorStorageImage\\2.jpg");
		this.setIconImage(img);

		setSize(492, 567);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLocationRelativeTo(parentComponent);
		contentPane.setLayout(null);

		lblFoodName = new JLabel("식품 이름:");
		lblFoodName.setBounds(237, 20, 80, 20);
		lblFoodName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		contentPane.add(lblFoodName);

		foodNameLabel = new JLabel();
		foodNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		foodNameLabel.setBounds(317, 20, 131, 25);
		foodNameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		contentPane.add(foodNameLabel);

		lblExpirationDate = new JLabel("유통 기한");
		lblExpirationDate.setBounds(314, 123, 80, 20);
		lblExpirationDate.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		contentPane.add(lblExpirationDate);

		expirationDateLabel = new JLabel();
		expirationDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		expirationDateLabel.setBounds(244, 153, 211, 60);
		expirationDateLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		contentPane.add(expirationDateLabel);

		lblFoodQuantity = new JLabel("식품 개수:");
		lblFoodQuantity.setBounds(237, 90, 80, 20);
		lblFoodQuantity.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		contentPane.add(lblFoodQuantity);

		foodQuantityLabel = new JLabel();
		foodQuantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		foodQuantityLabel.setBounds(317, 88, 131, 25);
		foodQuantityLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		contentPane.add(foodQuantityLabel);

		lblFoodCategory = new JLabel("식품 유형:");
		lblFoodCategory.setBounds(237, 55, 80, 20);
		lblFoodCategory.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		contentPane.add(lblFoodCategory);

		JLabel categoryLabel = new JLabel();
		categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		categoryLabel.setBounds(317, 55, 131, 25);
		categoryLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		contentPane.add(categoryLabel);

		imageLabel = new JLabel("이미지가 선택되지 않았습니다.");
		imageLabel.setBounds(20, 10, 212, 203);
		imageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(imageLabel);

		List<Freshmart> list = freshmartDao.readByStorage("냉동실");
		if (!list.isEmpty()) {
			Freshmart firstItem = list.get(0);
			foodNameLabel.setText(firstItem.getFoodname());

			String categoryName = freshmartDao.getFoodCategoryNameById(firstItem.getTypeid());
			categoryLabel.setText(categoryName);

			foodQuantityLabel.setText(String.valueOf(firstItem.getFoodquantity()));

			LocalDate expirationDate = firstItem.getExpirationdate();
			LocalDate currentDate = LocalDate.now();

			long daysRemaining = ChronoUnit.DAYS.between(currentDate, expirationDate);

			String formattedExpirationDate = expirationDate.toString();

			if (daysRemaining > 0) {
				expirationDateLabel.setText(formattedExpirationDate + " / " + daysRemaining + "일 남음");
				if (daysRemaining <= 5) {
					expirationDateLabel.setForeground(Color.RED);
				} else {
					expirationDateLabel.setForeground(Color.BLACK);
				}
			} else if (daysRemaining == 0) {
				expirationDateLabel.setText(formattedExpirationDate + " / 오늘 만료!");
				expirationDateLabel.setForeground(Color.RED);
				JOptionPane.showMessageDialog(FreezerStorage.this, "유통기한은 오늘이 마지막날입니다!", "경고", JOptionPane.WARNING_MESSAGE);
			} else {
				expirationDateLabel.setText(formattedExpirationDate + " / " + Math.abs(daysRemaining) + "일 지남");
				expirationDateLabel.setForeground(Color.RED);
			} }

			btnDetails = new JButton("상세");
			btnDetails.setBounds(20, 488, 80, 30);
			btnDetails.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			contentPane.add(btnDetails);

			btnDetails.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();

					if (selectedRow != -1) {
						String foodName = (String) table.getValueAt(selectedRow, 0);

						Object foodCategoryObj = table.getValueAt(selectedRow, 1);
						String foodCategory = foodCategoryObj instanceof Integer
								? ((Integer) foodCategoryObj).toString()
								: (String) foodCategoryObj;

						Integer foodQuantity = (Integer) table.getValueAt(selectedRow, 2);
						String foodQuantityStr = foodQuantity != null ? foodQuantity.toString() : "N/A";
						LocalDate expirationDate = (LocalDate) table.getValueAt(selectedRow, 3);
						foodNameLabel.setText(foodName);
						foodQuantityLabel.setText(foodQuantityStr);
						expirationDateLabel.setText(expirationDate.toString());

						LocalDate currentDate = LocalDate.now();
						long daysRemaining = ChronoUnit.DAYS.between(currentDate, expirationDate);

						if (daysRemaining > 0) {
							expirationDateLabel.setText(expirationDate + " / " + daysRemaining + "일 남음");
							if (daysRemaining <= 5) {
								expirationDateLabel.setForeground(Color.RED);
							} else {
								expirationDateLabel.setForeground(Color.BLACK);
							}
						} else if (daysRemaining == 0) {
							expirationDateLabel.setText(expirationDate + " / 오늘 만료!");
							expirationDateLabel.setForeground(Color.RED);
							JOptionPane.showMessageDialog(FreezerStorage.this, "유통기한은 오늘이 마지막날입니다!", "경고",
									JOptionPane.WARNING_MESSAGE);
						} else {
							expirationDateLabel.setText(expirationDate + " / " + Math.abs(daysRemaining) + "일 지남");
							expirationDateLabel.setForeground(Color.RED);
						}

						String imagePath = freshmartDao.getFoodImagePath(foodName);
						if (imagePath != null && !imagePath.isEmpty()) {
							ImageIcon imageIcon = new ImageIcon(imagePath);
							Image image = imageIcon.getImage();
							Image scaledImage = image.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(),
									Image.SCALE_SMOOTH);
							imageLabel.setIcon(new ImageIcon(scaledImage));
							imageLabel.setText("");
						} else {
							imageLabel.setIcon(null);
							imageLabel.setText("이미지가 없습니다.");
						}

					} else {
						JOptionPane.showMessageDialog(FreezerStorage.this, "행을 선택하세요", "경고", JOptionPane.WARNING_MESSAGE);
					}
				}
			});

			btnUpdate = new JButton("수정");
			btnUpdate.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			btnUpdate.setBounds(112, 488, 80, 30);
			contentPane.add(btnUpdate);

			btnDelete = new JButton("삭제");
			btnDelete.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			btnDelete.setBounds(204, 488, 80, 30);
			contentPane.add(btnDelete);

			btnList = new JButton("정렬");
			btnList.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			btnList.setBounds(296, 488, 80, 30);
			contentPane.add(btnList);

			btnClose = new JButton("닫기");
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnClose.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			btnClose.setBounds(384, 488, 80, 30);
			contentPane.add(btnClose);

			scrollPane = new JScrollPane();
			scrollPane.setBounds(30, 223, 436, 255);
			contentPane.add(scrollPane);

			table = new JTable();
			scrollPane.setViewportView(table);
			Font tableFont = new Font("맑은 고딕", Font.BOLD, 14);
		    table.setFont(tableFont);
		    
		    Font headerFont = new Font("맑은 고딕", Font.BOLD, 16);
		    table.getTableHeader().setFont(headerFont);
		    table.getTableHeader().setBackground(Color.LIGHT_GRAY);
	}

	private static final String[] COLUMN_NAMES = { "식품 이름", "식품 유형", "개수", "유통기한" };

	private void initializeTable() {
		List<Freshmart> list = FreshmartDao.INSTANCE.readByStorage("냉동실");
		resetTableModel(list);
	}

	private void resetTableModel(List<Freshmart> list) {
		DefaultTableModel model = new DefaultTableModel(null, COLUMN_NAMES);

		for (Freshmart f : list) {
			Object[] rowData = { f.getFoodname(), f.getTypeid(), f.getFoodquantity(), f.getExpirationdate() };
			model.addRow(rowData);
		}

		table.setModel(model);
	}

}
