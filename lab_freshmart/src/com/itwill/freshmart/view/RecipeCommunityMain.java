package com.itwill.freshmart.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class RecipeCommunityMain {

	private static final String[] SEARCH_TYPE = { "제목", "내용", "제목+내용", "작성자" };

	private static final String[] COLUMN_NAMES = { "번호", "제목", "작성자", "작성시간" };

	private JFrame frame;
	private JPanel searchPanel;
	private JComboBox comboBox;
	private JTextField textField;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	private JTable table;
	private JPanel buttonPanel;
	private JButton btnList;
	private JButton btnCreateRecipe;
	private JButton btnUpdate;
	private JButton btnExit;
	private JButton btnLike;
	private boolean isLiked = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecipeCommunityMain window = new RecipeCommunityMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RecipeCommunityMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Recipe Community");
		frame.setBounds(100, 100, 586, 587);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		searchPanel = new JPanel();
		frame.getContentPane().add(searchPanel, BorderLayout.NORTH);

		comboBox = new JComboBox();

		final DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>(SEARCH_TYPE);
		comboBox.setModel(comboBoxModel);

		comboBox.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		searchPanel.add(comboBox);

		textField = new JTextField();
		textField.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		searchPanel.add(textField);
		textField.setColumns(10);

		btnSearch = new JButton("검색");
		btnSearch.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		searchPanel.add(btnSearch);

		btnLike = new JButton();
		ImageIcon heartIcon = new ImageIcon("C:\\Users\\itwill\\Desktop\\Heart\\like.png");
		heartIcon = new ImageIcon(heartIcon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		btnLike.setIcon(heartIcon);
		btnLike.setPreferredSize(btnSearch.getPreferredSize());
		btnLike.setContentAreaFilled(false);
		btnLike.setBorderPainted(false);
		searchPanel.add(btnLike);

		btnLike.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleLikeStatus();
			}
		});

		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);

		buttonPanel = new JPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		btnList = new JButton("목록");
		btnList.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		buttonPanel.add(btnList);

		btnCreateRecipe = new JButton("레시피 작성");
		btnCreateRecipe.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		buttonPanel.add(btnCreateRecipe);

		btnUpdate = new JButton("수정");
		btnUpdate.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		buttonPanel.add(btnUpdate);

		btnExit = new JButton("닫기");
		btnExit.addActionListener(e -> frame.dispose());
		btnExit.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		buttonPanel.add(btnExit);
	}

	private void toggleLikeStatus() {
		isLiked = !isLiked;
		if (isLiked) {
			ImageIcon likedIcon = new ImageIcon("C:\\Users\\itwill\\Desktop\\Heart\\like.png");
			likedIcon = new ImageIcon(likedIcon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
			btnLike.setIcon(likedIcon);
		} else {
			ImageIcon nonLikedIcon = new ImageIcon("C:\\Users\\itwill\\Desktop\\Heart\\non_like.png");
			nonLikedIcon = new ImageIcon(
					nonLikedIcon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
			btnLike.setIcon(nonLikedIcon);
		}
	}

}
