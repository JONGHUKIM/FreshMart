package com.itwill.freshmart.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class RecommendFoodFrame extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Component parentComponent;

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

		this.parentComponent = parentComponent;
		initialize();
	}
	
	

	/**
	 * Create the frame.
	 */
	public void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
	}

}
