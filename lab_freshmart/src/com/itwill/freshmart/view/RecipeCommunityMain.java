package com.itwill.freshmart.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.itwill.freshmart.controller.RecipeCommunityDao;
import com.itwill.freshmart.model.RecipeCommunity;
import com.itwill.freshmart.view.RecipeCommunityCreateFrame.CreateNotify;
import com.itwill.freshmart.view.RecipeCommunityDetails.UpdateNotify;

public class RecipeCommunityMain extends JFrame implements CreateNotify, UpdateNotify {

	private static final String[] SEARCH_TYPE = { "제목", "내용", "제목+내용", "작성자" };
	private static final String[] COLUMN_NAMES = { "찜목록", "번호", "제목", "작성자", "작성시간", "상태"};

	private JTextField textSearchKeyword;
	private JFrame frame;
	private JPanel searchPanel;
	private JComboBox comboBox;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	private JTable table;
	private JPanel buttonPanel;
	private JButton btnList;
	private JButton btnCreateRecipe;
	private JButton btnUpdate;
	private JButton btnExit;
	private JButton btnLike;
	private DefaultTableModel model;

	private RecipeCommunityDao recipeCommunityDao;
	private JButton btnDelete;

	private boolean isLiked = false;

	public static void showRecipeCommunityMain(JFrame parentFrame) {
		EventQueue.invokeLater(() -> {
			try {
				RecipeCommunityMain frame = new RecipeCommunityMain(parentFrame);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public RecipeCommunityMain(JFrame parentFrame) {
		recipeCommunityDao = RecipeCommunityDao.INSTANCE;
		initialize();
		initializeTable();
	}


	private void initialize() {
		setTitle("Recipe Community");
		setBounds(100, 100, 586, 587);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		   Toolkit toolkit = Toolkit.getDefaultToolkit();
	        Image img = toolkit.getImage("C:\\Users\\MYCOM\\Desktop\\RefrigeratorStorageImage\\2.jpg");
	        this.setIconImage(img);
		
		searchPanel = new JPanel();
		this.getContentPane().add(searchPanel, BorderLayout.NORTH);

		comboBox = new JComboBox();
		final DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(SEARCH_TYPE);
		comboBox.setModel(comboBoxModel);
		comboBox.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		searchPanel.add(comboBox);

		textSearchKeyword = new JTextField();
		textSearchKeyword.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		searchPanel.add(textSearchKeyword);
		textSearchKeyword.setColumns(10);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(e -> searchRecipe());
		btnSearch.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		searchPanel.add(btnSearch);

		btnLike = new JButton();
		btnLike.setBackground(new Color(0, 0, 0));
		updateLikeButton();
		btnLike.setPreferredSize(btnSearch.getPreferredSize());
		btnLike.setContentAreaFilled(false);
		btnLike.setBorderPainted(false);
		searchPanel.add(btnLike);

		btnLike.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLiked = !isLiked;
				updateLikeButton();
				filterTableByLikeStatus();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.getTableHeader().setFont(new Font("D2Coding", Font.PLAIN, 20));
		table.getTableHeader().setPreferredSize(new Dimension(0, 40));
		table.setFont(new Font("D2Coding", Font.PLAIN, 20));
		table.setRowHeight(40);
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());

				// "좋아요 버튼" 열이 클릭되었는지 확인
				if (col == 0) {
					Integer id = (Integer) model.getValueAt(row, 1); // ID 가져오기
					toggleLikeStatus(id, row);
				}
			}
		});

		model = new DefaultTableModel(null, COLUMN_NAMES);
		table.setModel(model);

		buttonPanel = new JPanel();
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		btnList = new JButton("목록");
		btnList.addActionListener(e -> initializeTable());
		btnList.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		buttonPanel.add(btnList);

		btnCreateRecipe = new JButton("레시피 작성");
		btnCreateRecipe.addActionListener(
				e -> RecipeCommunityCreateFrame.showRecipeCommunityCreateFrame(frame, RecipeCommunityMain.this));
		btnCreateRecipe.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		buttonPanel.add(btnCreateRecipe);

		btnUpdate = new JButton("상세보기");
		btnUpdate.addActionListener(e -> showRecipeCommunityDetails());
		btnUpdate.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		buttonPanel.add(btnUpdate);

		btnExit = new JButton("닫기");
		btnExit.addActionListener(e -> this.dispose());

		btnDelete = new JButton("삭제");
		btnDelete.addActionListener(e -> deleteRecipe());
		btnDelete.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		buttonPanel.add(btnDelete);
		btnExit.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		buttonPanel.add(btnExit);
	}

	private void updateLikeButton() {
		String imagePath = isLiked ? "C:\\Users\\MYCOM\\Desktop\\like\\like.png"
				: "C:\\Users\\MYCOM\\Desktop\\like\\non_like.png";
		ImageIcon heartIcon = new ImageIcon(imagePath);
		heartIcon = new ImageIcon(heartIcon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		btnLike.setIcon(heartIcon);
	}

	private void filterTableByLikeStatus() {
		List<RecipeCommunity> list = recipeCommunityDao.read();
		List<RecipeCommunity> filteredList = new ArrayList();

		for (RecipeCommunity b : list) {
			if (isLiked && b.getLiked().equals("Y")) {
				filteredList.add(b);
			} else if (!isLiked && b.getLiked().equals("N")) {
				filteredList.add(b);
			}
		}

		resetTableModel(filteredList);
	}

	private void searchRecipe() {
		int type = comboBox.getSelectedIndex();
		String keyword = textSearchKeyword.getText();
		if (keyword.equals("")) {
			JOptionPane.showMessageDialog(this, "검색어를 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE);
			return;
		}

		List<RecipeCommunity> recipeCommunities = recipeCommunityDao.read(type, keyword);
		resetTableModel(recipeCommunities);
	}

	private void showRecipeCommunityDetails() {
		int index = table.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(this, "테이블에서 상세보기를 할 행을 먼저 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Integer id = (Integer) model.getValueAt(index, 1);
		RecipeCommunityDetails.showRecipeCommunityDetails(this, RecipeCommunityMain.this, id);
	}

	private void deleteRecipe() {
		int index = table.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(this, "테이블에서 삭제할 행을 먼저 선택하세요.", "WARNING", JOptionPane.WARNING_MESSAGE);
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(this, "정말 삭제할까요?", "삭제 확인", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			Integer id = (Integer) model.getValueAt(index, 1);
			int result = recipeCommunityDao.delete(id);
			if (result == 1) {
				initializeTable();
				JOptionPane.showMessageDialog(this, "삭제 성공");
			} else {
				JOptionPane.showMessageDialog(this, "삭제 실패");
			}
		}
	}

	private void resetTableModel(List<RecipeCommunity> list) {
	    model.setRowCount(0);
	    for (RecipeCommunity b : list) {
	        String imagePath = b.getLiked().equals("Y") ? "C:\\Users\\MYCOM\\Desktop\\like\\like.png"
	                : "C:\\Users\\MYCOM\\Desktop\\like\\non_like.png";
	        ImageIcon likeIcon = new ImageIcon(imagePath);
	        likeIcon = new ImageIcon(likeIcon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));

	        Object[] rowData = { likeIcon, b.getId(), b.getTitle(), b.getAuthor(), b.getCreatedTime() };
	        model.addRow(rowData);
	    }

	    // 첫 번째 열에 대한 렌더러 설정 (이미지 아이콘을 표시)
	    table.getColumnModel().getColumn(0).setCellRenderer(new LikeButtonRenderer());
	}

	class LikeButtonRenderer extends JLabel implements TableCellRenderer {

		@Override
		public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
			setIcon((ImageIcon) value);
			setHorizontalAlignment(JLabel.CENTER);
			return this;
		}
	}

	private void toggleLikeStatus(Integer id, int row) {

	    ImageIcon currentIcon = (ImageIcon) model.getValueAt(row, 0);

	    String currentLikeStatus = (String) model.getValueAt(row, 5); 

	
	    if (currentLikeStatus == null) {
	        currentLikeStatus = "N";
	    }


	    String newStatus = currentLikeStatus.equals("Y") ? "N" : "Y";

	    String newImagePath = newStatus.equals("Y") 
	            ? "C:\\Users\\MYCOM\\Desktop\\like\\like.png" 
	            : "C:\\Users\\MYCOM\\Desktop\\like\\non_like.png";


	    boolean isLikedUpdated = recipeCommunityDao.toggleLiked(id);
	    if (isLikedUpdated) {

	        ImageIcon updatedIcon = new ImageIcon(new ImageIcon(newImagePath).getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));

	        model.setValueAt(updatedIcon, row, 0);

	        model.setValueAt(newStatus, row, 5);

	        table.repaint();
	       
	    }
	}

	
	
	@Override
	public void notifyCreateSuccess() {
		initializeTable();
	}

	@Override
	public void notifyUpdateSuccess() {
		initializeTable();
	}

	private void initializeTable() {
		List<RecipeCommunity> list = recipeCommunityDao.read();
		resetTableModel(list);
	}
}