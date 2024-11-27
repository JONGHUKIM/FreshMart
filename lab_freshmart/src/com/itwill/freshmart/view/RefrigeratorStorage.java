package com.itwill.freshmart.view;

import java.awt.EventQueue;
import java.io.File;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.itwill.freshmart.controller.FreshmartDao;
import com.itwill.freshmart.model.Freshmart;

public class RefrigeratorStorage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField foodNameField;
    private JLabel imageLabel;
    private JComboBox<String> storageComboBox;
    private String imagePath;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                RefrigeratorStorage frame = new RefrigeratorStorage();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public RefrigeratorStorage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblFoodName = new JLabel("식품 이름:");
        lblFoodName.setBounds(20, 20, 80, 20);
        contentPane.add(lblFoodName);

        foodNameField = new JTextField();
        foodNameField.setBounds(100, 20, 200, 25);
        contentPane.add(foodNameField);
        foodNameField.setColumns(10);

        JLabel lblStorage = new JLabel("보관 장소:");
        lblStorage.setBounds(20, 60, 80, 20);
        contentPane.add(lblStorage);

        storageComboBox = new JComboBox<>(new String[]{"냉장실", "냉동실"});
        storageComboBox.setBounds(100, 60, 200, 25);
        contentPane.add(storageComboBox);

        JLabel lblImage = new JLabel("이미지:");
        lblImage.setBounds(20, 100, 80, 20);
        contentPane.add(lblImage);

        imageLabel = new JLabel("이미지가 선택되지 않았습니다.");
        imageLabel.setBounds(20, 130, 400, 300);
        contentPane.add(imageLabel);

        JButton btnSelectImage = new JButton("사진 첨부");
        btnSelectImage.setBounds(320, 60, 100, 25);
        btnSelectImage.addActionListener(e -> showImageChooser());
        contentPane.add(btnSelectImage);

        JButton btnSave = new JButton("저장");
        btnSave.setBounds(170, 450, 100, 30);
        btnSave.addActionListener(e -> saveFoodItem());
        contentPane.add(btnSave);
    }

    private void showImageChooser() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            imagePath = selectedFile.getAbsolutePath();

            try {
                ImageIcon icon = new ImageIcon(imagePath);
                imageLabel.setIcon(icon);
                imageLabel.setText("");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "이미지를 불러올 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFoodItem() {
        String foodName = foodNameField.getText();
        String storage = (String) storageComboBox.getSelectedItem();

        if (foodName.isEmpty() || imagePath == null) {
            JOptionPane.showMessageDialog(this, "모든 항목을 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Freshmart freshmart = Freshmart.builder()
                .typeid(1)
                .foodname(foodName)
                .expirationdate(LocalDate.now())
                .storage(storage.equals("냉장실") ? "냉장실" : "냉동실")
                .foodquantity(1)
                .img(imagePath)
                .build();

            int result = FreshmartDao.INSTANCE.create(freshmart);
            if (result == 1) {
                JOptionPane.showMessageDialog(this, "저장되었습니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "저장에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }
}