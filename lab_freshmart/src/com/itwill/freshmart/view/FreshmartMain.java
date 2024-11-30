package com.itwill.freshmart.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class FreshmartMain {

    private JFrame frame;
    private JPanel btnPanel;
    private JButton btnRefrigerator;
    private JButton btnFreezer;
    private JButton btnTodayWhatIeat;
    private JButton btnRefrigeratorStorage;
    private JButton btnRecipe;
    private JLabel lblFreshmartVer;
    private JButton btnExit;

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
        // Frame 설정
        frame = new JFrame();
        frame.setBounds(100, 100, 439, 599);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("FreshMart");

        // 아이콘 이미지 설정
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image img = toolkit.getImage("C:\\Users\\MYCOM\\Desktop\\RefrigeratorStorageImage\\2.jpg");
        frame.setIconImage(img);
        
        ImageIcon background = new ImageIcon("C:\\Users\\MYCOM\\Desktop\\like\\hubo2.png");  // 배경 이미지 경로
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 93, 298, 506);  // 배경 이미지 크기
        frame.getContentPane().add(backgroundLabel);

        // 배경색상 설정 (다크 톤)
        frame.getContentPane().setBackground(new Color(47, 54, 64));

        // 패널 설정
        btnPanel = new JPanel();
        btnPanel.setBounds(0, 0, 423, 560);
        frame.getContentPane().add(btnPanel);
        btnPanel.setLayout(null);
        btnPanel.setBackground(new Color(47, 54, 64));

        // 버튼 폰트
        Font btnFont = new Font("맑은 고딕", Font.BOLD, 16);

        // 냉동실 보기 버튼
        btnFreezer = new JButton("냉동실 보기");
        btnFreezer.setFont(btnFont);
        btnFreezer.setBounds(12, 10, 195, 135);
        btnFreezer.setBackground(new Color(38, 50, 56));  // 다크한 배경
        btnFreezer.setForeground(Color.WHITE);  // 글자 색상
        btnFreezer.setFocusPainted(false);
        btnFreezer.setBorderPainted(false);
        btnFreezer.setOpaque(true);
        btnFreezer.addActionListener(e -> {
            // 냉동실 보기 클릭 시 동작하는 부분
            RefrigeratorStorageCreateFrame refrigeratorStorageCreateFrame = new RefrigeratorStorageCreateFrame(frame);
            FreezerStorage.showFreezerStorage(frame, refrigeratorStorageCreateFrame, new FreezerStorage.FreezerNotify() {
                @Override
                public void notifyUpdateSuccess() {
                    System.out.println("Update successful!");
                }
            });
        });
        btnPanel.add(btnFreezer);

        // 냉장실 보기 버튼
        btnRefrigerator = new JButton("냉장실 보기");
        btnRefrigerator.setFont(btnFont);
        btnRefrigerator.setBounds(216, 10, 195, 135);
        btnRefrigerator.setBackground(new Color(38, 50, 56));
        btnRefrigerator.setForeground(Color.WHITE);
        btnRefrigerator.setFocusPainted(false);
        btnRefrigerator.setBorderPainted(false);
        btnRefrigerator.setOpaque(true);
        btnRefrigerator.addActionListener(e -> {
            // 냉장실 보기 클릭 시 동작하는 부분
            RefrigeratorStorageCreateFrame refrigeratorStorageCreateFrame = new RefrigeratorStorageCreateFrame(frame);
            RefrigeratorStorage.showRefrigeratorStorage(frame, refrigeratorStorageCreateFrame, new RefrigeratorStorage.RefrigeratorStorageNotify() {
                @Override
                public void notifyUpdateSuccess() {
                    System.out.println("Update successful!");
                }
            });
        });
        btnPanel.add(btnRefrigerator);

        // 보관하기 버튼
        btnRefrigeratorStorage = new JButton("보관하기");
        btnRefrigeratorStorage.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        btnRefrigeratorStorage.setBounds(12, 155, 399, 135);
        btnRefrigeratorStorage.setBackground(new Color(38, 50, 56));
        btnRefrigeratorStorage.setForeground(Color.WHITE);
        btnRefrigeratorStorage.setFocusPainted(false);
        btnRefrigeratorStorage.setBorderPainted(false);
        btnRefrigeratorStorage.setOpaque(true);
        btnRefrigeratorStorage.addActionListener(e -> {
            // 보관하기 버튼 클릭 시 동작하는 부분
            RefrigeratorStorageCreateFrame.showRefrigeratorStorageCreateFrame(frame);
        });
        btnPanel.add(btnRefrigeratorStorage);

        // 오늘 뭐 먹지? 버튼
        btnTodayWhatIeat = new JButton("오늘 뭐 먹지?");
        btnTodayWhatIeat.setFont(btnFont);
        btnTodayWhatIeat.setBounds(12, 300, 195, 250);
        btnTodayWhatIeat.setBackground(new Color(38, 50, 56));
        btnTodayWhatIeat.setForeground(Color.WHITE);
        btnTodayWhatIeat.setFocusPainted(false);
        btnTodayWhatIeat.setBorderPainted(false);
        btnTodayWhatIeat.setOpaque(true);
        btnTodayWhatIeat.addActionListener(e -> {
            // 오늘 뭐 먹지? 클릭 시 동작하는 부분
            RecommendFoodFrame.ShowRecommendFoodFrame(frame);
        });
        btnPanel.add(btnTodayWhatIeat);

        // 레시피 커뮤니티 버튼
        btnRecipe = new JButton("레시피 커뮤니티");
        btnRecipe.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        btnRecipe.setBounds(216, 444, 195, 48);
        btnRecipe.setBackground(new Color(38, 50, 56));
        btnRecipe.setForeground(Color.WHITE);
        btnRecipe.setFocusPainted(false);
        btnRecipe.setBorderPainted(false);
        btnRecipe.setOpaque(true);
        btnRecipe.addActionListener(e -> {
            // 레시피 커뮤니티 클릭 시 동작하는 부분
            RecipeCommunityMain.showRecipeCommunityMain(frame);
        });
        btnPanel.add(btnRecipe);

        // FreshMart 버전 레이블
        lblFreshmartVer = new JLabel("FreshMart Ver 1.0");
        lblFreshmartVer.setHorizontalAlignment(SwingConstants.CENTER);
        lblFreshmartVer.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 14));
        lblFreshmartVer.setForeground(Color.WHITE);
        lblFreshmartVer.setBounds(216, 300, 195, 134);
        btnPanel.add(lblFreshmartVer);

        // 종료 버튼
        btnExit = new JButton("종료");
        btnExit.setFont(btnFont);
        btnExit.setBounds(216, 502, 195, 48);
        btnExit.setBackground(new Color(38, 50, 56));
        btnExit.setForeground(Color.WHITE);
        btnExit.setFocusPainted(false);
        btnExit.setBorderPainted(false);
        btnExit.setOpaque(true);
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();  // 종료 버튼 클릭 시 창을 닫음
            }
        });
        btnPanel.add(btnExit);
    }
}
