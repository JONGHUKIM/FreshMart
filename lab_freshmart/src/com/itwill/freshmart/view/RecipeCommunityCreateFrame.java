package com.itwill.freshmart.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itwill.freshmart.controller.RecipeCommunityDao;
import com.itwill.freshmart.model.RecipeCommunity;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RecipeCommunityCreateFrame extends JFrame {

	public interface CreateNotify {
		void notifyCreateSuccess();
	}

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel mainPanel;
	private JPanel btnPanel;
	private JLabel lblTitle;
	private JTextField textTitle;
	private JLabel lblContent;
	private JTextArea textContent;
	private JTextField textAuthor;
	private JLabel lblAuthor;
	private JButton buttonSave;
	private JScrollPane scrollPane;
	private JButton buttonCancel;

	private RecipeCommunityDao recipeCommunityDao;
	private Component parentComponent;
	private CreateNotify app;

	/**
	 * Launch the application.
	 */
	public static void showRecipeCommunityCreateFrame(Component parentComponent, CreateNotify app) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecipeCommunityCreateFrame frame = new RecipeCommunityCreateFrame(parentComponent, app);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private RecipeCommunityCreateFrame(Component parentComponent, CreateNotify app) {
		this.recipeCommunityDao = recipeCommunityDao.INSTANCE;
		this.parentComponent = parentComponent;
		this.app = app;
		initialize();
	}

	/**
	 * Create the frame.
	 */
	public void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Recipe Share");
		setBounds(100, 100, 459, 544);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setLocationRelativeTo(parentComponent);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnPanel = new JPanel();
		btnPanel.setBounds(5, 461, 433, 39);
		contentPane.add(btnPanel);
		btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		buttonSave = new JButton("저장");
		buttonSave.addActionListener(e -> createNewRecipeCommunity());
		buttonSave.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnPanel.add(buttonSave);

		buttonCancel = new JButton("취소");
		buttonCancel.addActionListener(e -> dispose());
		buttonCancel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnPanel.add(buttonCancel);

		mainPanel = new JPanel();
		mainPanel.setBounds(5, 5, 433, 446);
		contentPane.add(mainPanel);
		mainPanel.setLayout(null);

		lblTitle = new JLabel("제목");
		lblTitle.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 15));
		lblTitle.setBounds(12, 10, 74, 29);
		mainPanel.add(lblTitle);

		textTitle = new JTextField();
		textTitle.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		textTitle.setBounds(12, 49, 409, 35);
		mainPanel.add(textTitle);
		textTitle.setColumns(10);

		lblContent = new JLabel("내용");
		lblContent.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 15));
		lblContent.setBounds(12, 94, 74, 29);
		mainPanel.add(lblContent);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 133, 409, 224);
		mainPanel.add(scrollPane);

		textContent = new JTextArea();
		textContent.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		scrollPane.setViewportView(textContent);

		lblAuthor = new JLabel("작성자");
		lblAuthor.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 15));
		lblAuthor.setBounds(12, 367, 74, 29);
		mainPanel.add(lblAuthor);

		textAuthor = new JTextField();
		textAuthor.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		textAuthor.setColumns(10);
		textAuthor.setBounds(12, 406, 409, 35);
		mainPanel.add(textAuthor);
	}

	private void createNewRecipeCommunity() {
		String title = textTitle.getText();
		String content = textContent.getText();
		String author = textAuthor.getText();

		if (title.equals("") || content.equals("") || author.equals("")) {
			JOptionPane.showMessageDialog(RecipeCommunityCreateFrame.this, "제목, 내용, 작성자는 반드시 입력해야합니다.", "WARNING",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		RecipeCommunity recipeCommunity = RecipeCommunity.builder().title(title).content(content).author(author)
				.build();

		int result = recipeCommunityDao.create(recipeCommunity);
		if (result == 1) {
			JOptionPane.showMessageDialog(RecipeCommunityCreateFrame.this, "레시피 작성 완료!");
			
			app.notifyCreateSuccess();
			
			dispose();
		} else {
			JOptionPane.showMessageDialog(RecipeCommunityCreateFrame.this, "레시피 작성 실패");
		}

	}
}
