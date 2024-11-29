package com.itwill.freshmart.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class RecipeCommunityDetails extends JFrame {

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecipeCommunityDetails frame = new RecipeCommunityDetails();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RecipeCommunityDetails() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Update Recipe");
		setBounds(100, 100, 450, 631);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

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
		btnUpdate.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnPanel.add(btnUpdate);
		
		btnCancel = new JButton("취소");
		btnCancel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnPanel.add(btnCancel);
	}

}
