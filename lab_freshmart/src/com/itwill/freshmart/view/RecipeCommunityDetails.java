package com.itwill.freshmart.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itwill.freshmart.controller.RecipeCommunityDao;
import com.itwill.freshmart.model.RecipeCommunity;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RecipeCommunityDetails extends JFrame {

	public interface UpdateNotify {
		void notifyUpdateSuccess();
	}

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel btnPanel;
	private JPanel mainPanel;
	private JLabel lblId;
	private JTextField textIdField;
	private JTextField textTitleField;
	private JLabel lblTitle;
	private JTextArea textContentArea;
	private JLabel lblContent;
	private JTextField textAuthorField;
	private JTextField textCreatedTimeField;
	private JTextField textModifiedTimeField;
	private JLabel lblAuthor;
	private JLabel lblCreatedTime;
	private JLabel lblModifiedTime;
	private JButton btnUpdate;
	private JButton btnCancel;

	private RecipeCommunityDao recipeCommunityDao;
	private Component parentComponent;
	private UpdateNotify app;
	private final Integer id;

	/**
	 * Launch the application.
	 */
	public static void showRecipeCommunityDetails(Component parentComponent, UpdateNotify app, Integer id) {
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                RecipeCommunityDetails frame = new RecipeCommunityDetails(parentComponent, app, id);
	                frame.setVisible(true);
	                
	                // 부모 컴포넌트를 기준으로 중앙에 위치시킴
	                frame.setLocationRelativeTo(parentComponent);  // 이 부분이 중앙에 띄우는 코드입니다.
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
	private RecipeCommunityDetails(Component parentComponent, UpdateNotify app, Integer id) {
		this.recipeCommunityDao = RecipeCommunityDao.INSTANCE;
		this.parentComponent = parentComponent;
		this.app = app;
		this.id = id;

		initialize(); // UI 컴포넌트들을 생성, 초기화.
		initializeRecipeCommunity(); // 아이디(PK)로 검색한 블로그 내용을 JTextField와 JTextArea에 씀.
	}

	/**
	 * Create the frame.
	 */
	public void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Update Recipe");
		setBounds(100, 100, 450, 631);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		   Toolkit toolkit = Toolkit.getDefaultToolkit();
	        Image img = toolkit.getImage("C:\\Users\\MYCOM\\Desktop\\RefrigeratorStorageImage\\2.jpg");
	        this.setIconImage(img);

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		mainPanel = new JPanel();
		contentPane.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);

		lblId = new JLabel("번호");
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		lblId.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 20));
		lblId.setBounds(0, 10, 100, 46);
		mainPanel.add(lblId);

		textIdField = new JTextField();
		textIdField.setEditable(false);
		textIdField.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		textIdField.setBounds(112, 10, 300, 46);
		mainPanel.add(textIdField);
		textIdField.setColumns(10);

		lblTitle = new JLabel("제목");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 20));
		lblTitle.setBounds(0, 66, 100, 46);
		mainPanel.add(lblTitle);

		textTitleField = new JTextField();
		textTitleField.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		textTitleField.setColumns(10);
		textTitleField.setBounds(112, 66, 300, 46);
		mainPanel.add(textTitleField);

		lblContent = new JLabel("내용");
		lblContent.setHorizontalAlignment(SwingConstants.CENTER);
		lblContent.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 20));
		lblContent.setBounds(0, 122, 100, 46);
		mainPanel.add(lblContent);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 178, 424, 181);
		mainPanel.add(scrollPane);

		textContentArea = new JTextArea();
		textContentArea.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		scrollPane.setViewportView(textContentArea);

		lblAuthor = new JLabel("작성자");
		lblAuthor.setHorizontalAlignment(SwingConstants.CENTER);
		lblAuthor.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 20));
		lblAuthor.setBounds(0, 369, 100, 46);
		mainPanel.add(lblAuthor);

		textAuthorField = new JTextField();
		textAuthorField.setEditable(false);
		textAuthorField.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		textAuthorField.setColumns(10);
		textAuthorField.setBounds(112, 369, 300, 46);
		mainPanel.add(textAuthorField);

		lblCreatedTime = new JLabel("작성시간");
		lblCreatedTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreatedTime.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 20));
		lblCreatedTime.setBounds(0, 425, 100, 46);
		mainPanel.add(lblCreatedTime);

		textCreatedTimeField = new JTextField();
		textCreatedTimeField.setEditable(false);
		textCreatedTimeField.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		textCreatedTimeField.setColumns(10);
		textCreatedTimeField.setBounds(112, 425, 300, 46);
		mainPanel.add(textCreatedTimeField);

		lblModifiedTime = new JLabel("수정시간");
		lblModifiedTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblModifiedTime.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 20));
		lblModifiedTime.setBounds(0, 481, 100, 46);
		mainPanel.add(lblModifiedTime);

		textModifiedTimeField = new JTextField();
		textModifiedTimeField.setEditable(false);
		textModifiedTimeField.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		textModifiedTimeField.setColumns(10);
		textModifiedTimeField.setBounds(112, 481, 300, 46);
		mainPanel.add(textModifiedTimeField);

		btnPanel = new JPanel();
		btnPanel.setBounds(0, 528, 424, 54);
		mainPanel.add(btnPanel);

		btnUpdate = new JButton("수정");
		btnUpdate.addActionListener(e -> updateRecipeCommunity());
		btnUpdate.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnPanel.add(btnUpdate);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(e -> dispose());
		btnCancel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnPanel.add(btnCancel);
	}

	private void updateRecipeCommunity() {

		String title = textTitleField.getText();
		String content = textContentArea.getText();
		if (title.equals("") || content.equals("")) {
			JOptionPane.showMessageDialog(RecipeCommunityDetails.this, "제목과 내용은 반드시 입력하세요.", "WARNING",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		RecipeCommunity recipeCommunity = RecipeCommunity.builder().id(id).title(title).content(content).build();
		int result = recipeCommunityDao.update(recipeCommunity);
		
		if ( result == 1) {
			JOptionPane.showMessageDialog(RecipeCommunityDetails.this, "수정이 되었습니다!");
			dispose();
		} else {
			JOptionPane.showMessageDialog(RecipeCommunityDetails.this, "수정에 실패했습니다");
		}

	}
	
	private void initializeRecipeCommunity() {
		RecipeCommunity recipeCommunity = recipeCommunityDao.read(id);
		
		textIdField.setText(recipeCommunity.getId().toString());
		textTitleField.setText(recipeCommunity.getTitle());
		textContentArea.setText(recipeCommunity.getContent());
		textAuthorField.setText(recipeCommunity.getAuthor());
		textCreatedTimeField.setText(recipeCommunity.getCreatedTime().toString());
		textModifiedTimeField.setText(recipeCommunity.getModifiedTime().toString());
	}

}
