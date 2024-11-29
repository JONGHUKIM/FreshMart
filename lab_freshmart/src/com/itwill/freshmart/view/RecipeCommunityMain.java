package com.itwill.freshmart.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.itwill.freshmart.controller.RecipeCommunityDao;
import com.itwill.freshmart.model.RecipeCommunity;
import com.itwill.freshmart.view.RecipeCommunityCreateFrame.CreateNotify;
import com.itwill.freshmart.view.RecipeCommunityDetails.UpdateNotify;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ScrollPaneConstants;

public class RecipeCommunityMain implements CreateNotify, UpdateNotify {

	private static final String[] SEARCH_TYPE = { "제목", "내용", "제목+내용", "작성자" };

	private static final String[] COLUMN_NAMES = { "좋아요", "번호", "제목", "작성자", "작성시간" };

	private JTextField textSearchKeyword;
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
	private DefaultTableModel model;

	private RecipeCommunityDao recipeCommunityDao;
	private JButton btnDelete;

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
		recipeCommunityDao = RecipeCommunityDao.INSTANCE;
		initialize();
		initializeTable();
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
		btnSearch.addActionListener(e -> searchRecipe());
		btnSearch.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		searchPanel.add(btnSearch);

		btnLike = new JButton();
		btnLike.setBackground(new Color(0, 0, 0));
		ImageIcon heartIcon = new ImageIcon("C:\\Users\\itwill\\Desktop\\Heart\\non_like.png");
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
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		  table = new JTable();
	        table.getTableHeader().setFont(new Font("D2Coding", Font.PLAIN, 20));
	        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
	        table.setFont(new Font("D2Coding", Font.PLAIN, 20));
	        table.setRowHeight(40);
	        scrollPane.setViewportView(table);

	        model = new DefaultTableModel(null, COLUMN_NAMES);
	        table.setModel(model);

		buttonPanel = new JPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		btnList = new JButton("목록");
		btnList.addActionListener(e -> initializeTable());
		btnList.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		buttonPanel.add(btnList);

		btnCreateRecipe = new JButton("레시피 작성");
		btnCreateRecipe.addActionListener(
				e -> RecipeCommunityCreateFrame.showRecipeCommunityCreateFrame(frame, RecipeCommunityMain.this));
		btnCreateRecipe.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		buttonPanel.add(btnCreateRecipe);

		btnUpdate = new JButton("수정");
		btnUpdate.addActionListener(e -> showRecipeCommunityDetails());
		btnUpdate.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		buttonPanel.add(btnUpdate);

		btnExit = new JButton("닫기");
		btnExit.addActionListener(e -> frame.dispose());

		btnDelete = new JButton("삭제");
		btnDelete.addActionListener(e -> deleteRecipe());
		btnDelete.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		buttonPanel.add(btnDelete);
		btnExit.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		buttonPanel.add(btnExit);
	}

	private void searchRecipe() {
		
    int type = comboBox.getSelectedIndex();
        
        // 검색어를 JTextField에서 읽음.
        String keyword = textSearchKeyword.getText();
        if (keyword.equals("")) {
            JOptionPane.showMessageDialog(
                    frame, 
                    "검색어를 입력하세요.", 
                    "경고", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        List<RecipeCommunity> blogs = recipeCommunityDao.read(type, keyword);
        resetTableModel(blogs);
	}

	private void showRecipeCommunityDetails() {
		  int index = table.getSelectedRow();
	        if (index == -1) { // 선택된 행이 없는 경우
	            JOptionPane.showMessageDialog(
	                    frame, 
	                    "테이블에서 상세보기를 할 행을 먼저 선택하세요.", 
	                    "경고", 
	                    JOptionPane.WARNING_MESSAGE);
	            return;
	        }
	        
	        // 선택된 행에서 블로그 아이디 값을 찾음.
	        Integer id = (Integer) model.getValueAt(index, 0);
	        
	        // 블로그 상세보기 창을 실행.
	        RecipeCommunityDetails.showRecipeCommunityDetails(frame, RecipeCommunityMain.this, id);
	}

	private void deleteRecipe() {

		int index = table.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(frame, "테이블에서 삭제할 행을 먼저 선택하세요.", "WARNING", JOptionPane.WARNING_MESSAGE);
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(frame, "정말 삭제할까요?", "삭제 확인", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			Integer id = (Integer) model.getValueAt(index, 0);

			int result = recipeCommunityDao.delete(id);
			if (result == 1) {
				initializeTable();
				JOptionPane.showMessageDialog(frame, "삭제 성공");
			} else {
				JOptionPane.showMessageDialog(frame, "삭제 실패");
			}
		}

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

	 private void initializeTable() {
	  
	        try {
	            List<RecipeCommunity> list = recipeCommunityDao.read();
	            resetTableModel(list);
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(frame, "작성된 레시피가 없음");
	            e.printStackTrace(); 
	        }
	    }

	    private void resetTableModel(List<RecipeCommunity> list) {
	        model.setRowCount(0);  // 테이블 데이터 초기화
	        for (RecipeCommunity b : list) {
	            Object[] rowData = { b.getLiked(), b.getId(), b.getTitle(), b.getAuthor(), b.getCreatedTime() };
	            model.addRow(rowData);
	        }
	    }

	@Override
	public void notifyCreateSuccess() {
		initializeTable(); // 테이블 새로고침.
	}
	
    @Override
    public void notifyUpdateSuccess() {
        initializeTable(); // 테이블 새로고침.
    }

}
