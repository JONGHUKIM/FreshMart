package com.itwill.freshmart.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.itwill.freshmart.controller.FreshmartDao;
import com.itwill.freshmart.model.Freshmart;

public class RecommendFoodFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel imageLabel;
	private JLabel lblFoodName;
	private JLabel lblFoodCategory;
	private JLabel lblExpirationDate;
	private JLabel lblFoodQuantity;
	private Component parentComponent;
	private JLabel foodNameLabel;
	private JLabel foodQuantityLabel;
	private JLabel expirationDateLabel;

	private FreshmartDao freshmartDao;

	/**
	 * Launch the application.
	 */
	public static void ShowRecommendFoodFrame(Component parentComponent) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecommendFoodFrame frame = new RecommendFoodFrame(parentComponent);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public RecommendFoodFrame(Component parentComponent) {
		freshmartDao = FreshmartDao.INSTANCE;
		this.parentComponent = parentComponent;
		initialize();
	}

	/**
	 * Create the frame.
	 */
	// Import는 동일합니다.
	public void initialize() {
	    contentPane = new JPanel();
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    setContentPane(contentPane);

	    setTitle("오늘 뭐 먹지?");
	    Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Image img = toolkit.getImage("C:\\Users\\MYCOM\\Desktop\\RefrigeratorStorageImage\\2.jpg");
	    this.setIconImage(img);

	    setSize(510, 368);
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

	    JLabel storageLocationLabel = new JLabel("저장 위치:");
	    storageLocationLabel.setBounds(237, 218, 80, 20);
	    storageLocationLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
	    contentPane.add(storageLocationLabel);

	    JLabel storageLabel = new JLabel();
	    storageLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    storageLabel.setBounds(317, 218, 131, 25);
	    storageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
	    contentPane.add(storageLabel);

	    imageLabel = new JLabel("이미지가 선택되지 않았습니다.");
	    imageLabel.setBounds(20, 10, 212, 203);
	    imageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
	    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    contentPane.add(imageLabel);

	    JLabel recipeSuggestionLabel = new JLabel("더 맛있게 먹을 수 있는 레시피가 궁금하다면?");
	    recipeSuggestionLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
	    recipeSuggestionLabel.setBounds(20, 248, 435, 20);
	    contentPane.add(recipeSuggestionLabel);

	    // 음식 데이터 가져오기
	    List<Freshmart> fridgeFoodList = freshmartDao.readByExpirationDateAsc("냉장실");
	    List<Freshmart> freezerFoodList = freshmartDao.readByExpirationDateAsc("냉동실");

	    if (fridgeFoodList.isEmpty() && freezerFoodList.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "추천할 수 있는 음식이 없습니다.");
	        return;
	    }

	    // 데이터 병합 및 정렬
	    List<Freshmart> allFoods = new ArrayList<>();
	    allFoods.addAll(fridgeFoodList);
	    allFoods.addAll(freezerFoodList);
	    allFoods.sort(Comparator.comparing(Freshmart::getExpirationdate));

	    // 동일 유통기한 음식 무작위 선택
	    LocalDate nearestExpirationDate = allFoods.get(0).getExpirationdate();
	    List<Freshmart> sameExpirationFoods = allFoods.stream()
	        .filter(food -> food.getExpirationdate().equals(nearestExpirationDate))
	        .toList();
	    Random random = new Random();
	    Freshmart recommendedFood = sameExpirationFoods.get(random.nextInt(sameExpirationFoods.size()));

	    // UI에 데이터 표시
	    if (recommendedFood != null) {
	        foodNameLabel.setText(recommendedFood.getFoodname());
	        categoryLabel.setText(freshmartDao.getFoodCategoryNameById(recommendedFood.getTypeid()));
	        foodQuantityLabel.setText(String.valueOf(recommendedFood.getFoodquantity()));

	        LocalDate expirationDate = recommendedFood.getExpirationdate();
	        long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), expirationDate);

	        if (daysRemaining > 0) {
	            expirationDateLabel.setText(expirationDate + " / " + daysRemaining + "일 남음");
	            expirationDateLabel.setForeground(daysRemaining <= 5 ? Color.RED : Color.BLACK);
	        } else if (daysRemaining == 0) {
	            expirationDateLabel.setText("0일 남음");
	            expirationDateLabel.setForeground(Color.RED);
	        } else {
	            expirationDateLabel.setText("이미 만료됨");
	            expirationDateLabel.setForeground(Color.RED);
	        }
	        storageLabel.setText(recommendedFood.getStorage());

	        String imagePath = recommendedFood.getIMG();
	        if (imagePath != null && !imagePath.isEmpty()) {
	            ImageIcon imageIcon = new ImageIcon(imagePath);
	            Image randImg = imageIcon.getImage();
	            Image scaledImg = randImg.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
	            imageLabel.setIcon(new ImageIcon(scaledImg));
	        } else {
	            imageLabel.setText("이미지 없음");
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "추천할 수 있는 음식이 없습니다.");
	    }
	}


}