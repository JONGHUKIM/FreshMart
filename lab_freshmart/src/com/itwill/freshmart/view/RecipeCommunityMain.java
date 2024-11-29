package com.itwill.freshmart.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class RecipeCommunityMain {

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
		frame.setBounds(100, 100, 586, 587);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		searchPanel = new JPanel();
		frame.getContentPane().add(searchPanel, BorderLayout.NORTH);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		searchPanel.add(comboBox);
		
		textField = new JTextField();
		textField.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		searchPanel.add(textField);
		textField.setColumns(10);
		
		btnSearch = new JButton("검색");
		btnSearch.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		searchPanel.add(btnSearch);
		
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
		btnExit.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		buttonPanel.add(btnExit);
	}

}
