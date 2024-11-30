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

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.itwill.freshmart.controller.FreshmartDao;
import com.itwill.freshmart.model.Freshmart;

public class RefrigeratorStorage extends JFrame {

	public interface RefrigeratorStorageNotify {
		void notifyUpdateSuccess();
	}

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel imageLabel;
	private JLabel lblFoodName;
	private JLabel lblFoodCategory;
	private JLabel lblExpirationDate;
	private JLabel lblFoodQuantity;
	private Component parentComponent;
	private RefrigeratorStorageNotify app;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnList;
	private JButton btnClose;
	private JScrollPane scrollPane;
	private JTable table;
	private ButtonGroup buttonGroup;

	private JLabel foodNameLabel;
	private JLabel foodQuantityLabel;
	private JLabel expirationDateLabel;

	private FreshmartDao freshmartDao;

	public static void showRefrigeratorStorage(Component parentComponent,
			RefrigeratorStorageCreateFrame refrigeratorStorageCreateFrame, RefrigeratorStorageNotify app) {
		EventQueue.invokeLater(() -> {
			try {
				RefrigeratorStorage frame = new RefrigeratorStorage(parentComponent, refrigeratorStorageCreateFrame,
						app);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public RefrigeratorStorage(Component parentComponent, RefrigeratorStorageCreateFrame refrigeratorStorageCreateFrame,
			RefrigeratorStorageNotify app) {
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

		setTitle("냉장실");

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("C:\\Users\\MYCOM\\Desktop\\RefrigeratorStorageImage\\2.jpg");
		this.setIconImage(img);

		setSize(510, 578);
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
		imageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(imageLabel);

		List<Freshmart> list = freshmartDao.readByStorage("냉장실");
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
					JOptionPane.showMessageDialog(parentComponent, "유통기한이 임박한 상품이 있습니다!", "경고",
							JOptionPane.WARNING_MESSAGE);
				} else {
					expirationDateLabel.setForeground(Color.BLACK);
				}
			} else if (daysRemaining == 0) {
				expirationDateLabel.setText(formattedExpirationDate + " / 오늘 만료!");
				expirationDateLabel.setForeground(Color.RED);
				JOptionPane.showMessageDialog(parentComponent, "유통기한은 오늘이 마지막날입니다!", "경고",
						JOptionPane.WARNING_MESSAGE);
			} else {
				expirationDateLabel.setText(formattedExpirationDate + " / " + Math.abs(daysRemaining) + "일 지남");
				expirationDateLabel.setForeground(Color.RED);
			}
			 String imagePath = freshmartDao.getFoodImagePath(firstItem.getFoodname());
		        if (imagePath != null && !imagePath.isEmpty()) {
		            ImageIcon imageIcon = new ImageIcon(imagePath);
		            Image image = imageIcon.getImage();
		            Image scaledImage = image.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(),
		                    Image.SCALE_SMOOTH);
		            imageLabel.setIcon(new ImageIcon(scaledImage));
		            imageLabel.setText(""); // 텍스트 제거
		        } else {
		            imageLabel.setIcon(null);
		            imageLabel.setText("이미지가 없습니다.");
		        }
		}

		btnUpdate = new JButton("수정");
		btnUpdate.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();

			if (selectedRow != -1) {
				String foodName = (String) table.getValueAt(selectedRow, 0);
				Object foodCategoryObj = table.getValueAt(selectedRow, 1);
				String foodCategory = foodCategoryObj instanceof String ? (String) foodCategoryObj
						: String.valueOf(foodCategoryObj);
				Integer foodQuantity = (Integer) table.getValueAt(selectedRow, 2);
				LocalDate expirationDate = (LocalDate) table.getValueAt(selectedRow, 3);
				String imagePath = freshmartDao.getFoodImagePath(foodName);
				Component parentComponent = RefrigeratorStorage.this;
				int foodId = (Integer) table.getValueAt(selectedRow, 4);
				StorageUpdateFrame updateFrame = new StorageUpdateFrame(parentComponent, foodId, foodName, foodCategory,
						foodQuantity, expirationDate, imagePath);
				updateFrame.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(RefrigeratorStorage.this, "행을 선택하세요", "경고", JOptionPane.WARNING_MESSAGE);
			}
		});
		btnUpdate.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnUpdate.setBounds(40, 499, 80, 30);
		contentPane.add(btnUpdate);

		btnDelete = new JButton("삭제");
		btnDelete.addActionListener(e -> deleteRefrigerator());
		btnDelete.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnDelete.setBounds(132, 499, 80, 30);
		contentPane.add(btnDelete);

		btnList = new JButton("정렬");
		btnList.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnList.setBounds(224, 499, 80, 30);
		contentPane.add(btnList);

		btnClose = new JButton("닫기");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnClose.setBounds(375, 499, 80, 30);
		contentPane.add(btnClose);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 223, 436, 266);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		Font tableFont = new Font("맑은 고딕", Font.BOLD, 14);
		table.setFont(tableFont);

		rdBtnASC = new JRadioButton("Asc");
		rdBtnASC.setBounds(311, 495, 61, 20);
		contentPane.add(rdBtnASC);

		rdBtnDesc = new JRadioButton("Desc");
		rdBtnDesc.setBounds(311, 514, 64, 19);
		contentPane.add(rdBtnDesc);

		buttonGroup = new ButtonGroup();
		buttonGroup.add(rdBtnASC);
		buttonGroup.add(rdBtnDesc);

		rdBtnDesc.setSelected(true);

		btnList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedStorage = "냉장실";
				if (rdBtnASC.isSelected()) {
					ExpirationDateByasc(selectedStorage);
				} else if (rdBtnDesc.isSelected()) {
					ExpirationDateBydesc(selectedStorage);
				}
			}
		});

		Font headerFont = new Font("맑은 고딕", Font.BOLD, 16);
		table.getTableHeader().setFont(headerFont);
		table.getTableHeader().setBackground(Color.LIGHT_GRAY);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int selectedRow = table.getSelectedRow();

		        if (selectedRow != -1) {
		            String foodName = (String) table.getValueAt(selectedRow, 0);
		            String foodCategory = (String) table.getValueAt(selectedRow, 1); // 변경: String으로 캐스팅
		            Integer foodQuantity = (Integer) table.getValueAt(selectedRow, 2);
		            LocalDate expirationDate = (LocalDate) table.getValueAt(selectedRow, 3);

		            foodNameLabel.setText(foodName);
		            foodQuantityLabel.setText(foodQuantity != null ? foodQuantity.toString() : "N/A");
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
		                JOptionPane.showMessageDialog(RefrigeratorStorage.this, "유통기한은 오늘이 마지막날입니다!", "경고",
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
		        }
		    }
		});
	}

	private static final String[] COLUMN_NAMES = { "식품 이름", "식품 유형", "개수", "유통기한", "ID" };
	private JRadioButton rdBtnASC;
	private JRadioButton rdBtnDesc;

	private void initializeTable() {
		List<Freshmart> list = FreshmartDao.INSTANCE.readByStorage("냉장실");
		resetTableModel(list);

		table.getColumnModel().getColumn(4).setMaxWidth(0);
		table.getColumnModel().getColumn(4).setMinWidth(0);
		table.getColumnModel().getColumn(4).setPreferredWidth(0);
	}

	private void resetTableModel(List<Freshmart> list) {
	    DefaultTableModel model = new DefaultTableModel(null, COLUMN_NAMES);

	    for (Freshmart f : list) {
	        // typeId를 통해 카테고리 이름 가져오기
	        String categoryName = freshmartDao.getFoodCategoryNameById(f.getTypeid());
	        Object[] rowData = { 
	            f.getFoodname(), 
	            categoryName, // 카테고리 이름 추가
	            f.getFoodquantity(), 
	            f.getExpirationdate(), 
	            f.getId() 
	        };
	        model.addRow(rowData);
	    }

	    table.setModel(model);
	}

	private void deleteRefrigerator() {

		int index = table.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(RefrigeratorStorage.this, "테이블에서 삭제할 행을 먼저 선택하세요.", "경고",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(RefrigeratorStorage.this, "정말 삭제할까요?", "삭제 확인",
				JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			Integer id = (Integer) model.getValueAt(index, 4);

			int result = freshmartDao.delete(id);
			if (result == 1) {
				initializeTable();
				JOptionPane.showMessageDialog(RefrigeratorStorage.this, "삭제 성공");
			} else {
				JOptionPane.showMessageDialog(RefrigeratorStorage.this, "삭제 실패");
			}
		}

	}

	private void updateTableByExpirationDate(List<Freshmart> sortedList) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		for (Freshmart freshmart : sortedList) {
			Object[] rowData = new Object[] { freshmart.getFoodname(), freshmart.getTypeid(),
					freshmart.getFoodquantity(), freshmart.getExpirationdate(), };
			model.addRow(rowData);
		}
	}

	private void ExpirationDateBydesc(String storage) {
		List<Freshmart> sortedFreshmartList = FreshmartDao.INSTANCE.readByExpirationDateDesc(storage);
		updateTableByExpirationDate(sortedFreshmartList);
	}

	private void ExpirationDateByasc(String storage) {
		List<Freshmart> sortedFreshmartList = FreshmartDao.INSTANCE.readByExpirationDateAsc(storage);
		updateTableByExpirationDate(sortedFreshmartList);
	}
}