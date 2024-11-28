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
	private JButton btnClose;
	private ButtonGroup buttonGroup;

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

	    imageLabel = new JLabel("이미지가 선택되지 않았습니다.");
	    imageLabel.setBounds(20, 10, 212, 203);
	    imageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
	    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    contentPane.add(imageLabel);
	    
        JLabel recipeSuggestionLabel = new JLabel("더 맛있게 먹을 수 있는 레시피가 궁금하다면?");
        recipeSuggestionLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        recipeSuggestionLabel.setBounds(20, 248, 435, 20);
        contentPane.add(recipeSuggestionLabel);

	    Freshmart fridgeFood = freshmartDao.recommendFood("냉장실");
	    Freshmart freezerFood = freshmartDao.recommendFood("냉동실");

	    Freshmart recommendedFood = (Math.random() < 0.5) ? fridgeFood : freezerFood;

	    if (recommendedFood != null) {
	        String foodName = recommendedFood.getFoodname();
	        foodNameLabel.setText(foodName);

	        JLabel todayFoodLabel = new JLabel("오늘은 " + foodName + "!!!!");
	        todayFoodLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
	        todayFoodLabel.setBounds(20, 220, 450, 30);
	        contentPane.add(todayFoodLabel);
	        
	        String categoryName = freshmartDao.getFoodCategoryNameById(recommendedFood.getTypeid());
	        categoryLabel.setText(categoryName);

	        foodQuantityLabel.setText(String.valueOf(recommendedFood.getFoodquantity()));

	        LocalDate expirationDate = recommendedFood.getExpirationdate();
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
	            expirationDateLabel.setText("유통기한 만료");
	            expirationDateLabel.setForeground(Color.RED);
	        } else {
	            expirationDateLabel.setText("이미 만료됨");
	            expirationDateLabel.setForeground(Color.RED);
	        }

	        String imagePath = recommendedFood.getIMG();
	        if (imagePath != null && !imagePath.isEmpty()) {
	            ImageIcon imageIcon = new ImageIcon(imagePath);
	            Image randimg = imageIcon.getImage();
	            Image scaledImg = randimg.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
	            imageIcon = new ImageIcon(scaledImg);
	            imageLabel.setIcon(imageIcon);
	        }


	    } else {
	        JOptionPane.showMessageDialog(this, "추천할 수 있는 음식이 없습니다.");
	    }
	    
	}
}
