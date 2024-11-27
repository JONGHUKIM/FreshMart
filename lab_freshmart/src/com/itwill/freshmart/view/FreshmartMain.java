package com.itwill.freshmart.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class FreshmartMain {

	private JFrame frame;
	private JPanel btnPanel;
	private JButton btnRefrigerator;
	private JButton btnFreezer;
	private JButton btnTodayWhatIeat;
	private JButton btnRefrigeratorStorage;
	private JButton btnFreezerStorage;
	private JButton btnShare;
	private JButton btnRecipe;
	private JLabel lblFreshmartVer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FreshmartMain window = new FreshmartMain();
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
	public FreshmartMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 439, 599);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnPanel = new JPanel();
		btnPanel.setBounds(0, 0, 423, 560);
		frame.getContentPane().add(btnPanel);
		btnPanel.setLayout(null);
		
		btnFreezer = new JButton("냉동실");
		btnFreezer.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnFreezer.setBounds(12, 10, 195, 135);
		btnPanel.add(btnFreezer);
		
		btnRefrigerator = new JButton("냉장실");
		btnRefrigerator.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnRefrigerator.setBounds(216, 10, 195, 135);
		btnPanel.add(btnRefrigerator);
		
		btnFreezerStorage = new JButton("냉동보관");
		btnFreezerStorage.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnFreezerStorage.setBounds(12, 155, 195, 135);
		btnPanel.add(btnFreezerStorage);
		
		btnRefrigeratorStorage = new JButton("냉장보관");
		btnRefrigeratorStorage.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnRefrigeratorStorage.setBounds(216, 155, 195, 135);
		btnPanel.add(btnRefrigeratorStorage);
		
		btnTodayWhatIeat = new JButton("오늘 뭐 먹지?");
		btnTodayWhatIeat.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnTodayWhatIeat.setBounds(12, 300, 195, 250);
		btnPanel.add(btnTodayWhatIeat);
		
		btnShare = new JButton("식재료 공유");
		btnShare.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		btnShare.setBounds(216, 390, 195, 48);
		btnPanel.add(btnShare);
		
		btnRecipe = new JButton("레시피 커뮤니티");
		btnRecipe.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		btnRecipe.setBounds(216, 450, 195, 48);
		btnPanel.add(btnRecipe);
		
		lblFreshmartVer = new JLabel("FreshMart Ver 1.0");
		lblFreshmartVer.setHorizontalAlignment(SwingConstants.CENTER);
		lblFreshmartVer.setFont(new Font("D2Coding", Font.BOLD | Font.ITALIC, 14));
		lblFreshmartVer.setBounds(216, 508, 195, 42);
		btnPanel.add(lblFreshmartVer);
	}
}
