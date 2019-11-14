/*================================================================
*   Copyright (C) 2019 Navin Xu. All rights reserved.
*   
*   Filename    ：Week10Homework.java
*   Author      ：Navin Xu
*   E-Mail      ：admin@navinxu.com
*   Create Date ：2019年11月15日
*   Description ：
================================================================*/
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Week10Homework extends JFrame {

	private JPanel contentPane;
	private JTextField tfdHeight;
	private JTextField tfdWeight;
	private JTextField tfdBMI;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Week10Homework frame = new Week10Homework();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// 我添加的
	private double weight = 0.0;
	private double height = 0.0;
	private JTextField tfdNotice;
	private JTextPane tpBMIRef;

	/**
	 * Create the frame.
	 */
	public Week10Homework() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblHeight = new JLabel("身高(m)：");
		lblHeight.setBounds(12, 24, 70, 22);
		contentPane.add(lblHeight);

		JLabel lblWeight = new JLabel("体重(kg)：");
		lblWeight.setBounds(12, 54, 70, 22);
		contentPane.add(lblWeight);

        // 身高
		tfdHeight = new JTextField();
		tfdHeight.setBounds(85, 26, 85, 22);
		contentPane.add(tfdHeight);
		tfdHeight.setColumns(10);

        // 体重
		tfdWeight = new JTextField();
		tfdWeight.setColumns(10);
		tfdWeight.setBounds(85, 56, 85, 22);
		contentPane.add(tfdWeight);

		JButton btnbmi = new JButton("开始计算BMI值");
		btnbmi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				height = Double.parseDouble(tfdHeight.getText());
				weight = Double.parseDouble(tfdWeight.getText());
				double bmi = weight / (height * height);
                // 将小数点精确到一位
				BigDecimal bDecimal = new BigDecimal(bmi).setScale(1, RoundingMode.FLOOR);
				bmi = bDecimal.doubleValue();

				tfdBMI.setText("" + bmi);
				Double d = bmi;
				/*
				 * < 18.5 过轻 某些疾病和某些癌症患病率增高
					18.5－23.9 正常
					24－27.9 超重
					>28 肥胖
				 */
				if (d.compareTo(Double.parseDouble("" + 18.5)) < 0) {
					tfdNotice.setText("您的 BMI 值过低！");
				} else if (d.compareTo(Double.parseDouble("" + 18.5)) >= 0 && d.compareTo(Double.parseDouble("" + 23.9)) <= 0) {
					tfdNotice.setText("您的 BMI 值正常！");
				} else if (d.compareTo(Double.parseDouble("" + 24)) >= 0 && d.compareTo(Double.parseDouble("" + 27.9)) <= 0) {
					tfdNotice.setText("您的 BMI 值表明您有点超重！");
				} else {
					tfdNotice.setText("您的 BMI 值表明您的为肥胖人士！");
				}
 			}
		});
		btnbmi.setBounds(12, 92, 137, 25);
		contentPane.add(btnbmi);

		JLabel lblbmi = new JLabel("以下是计算结果(体质指数BMI)：");
		lblbmi.setBounds(12, 129, 179, 15);
		contentPane.add(lblbmi);

		tfdBMI = new JTextField();
		tfdBMI.setBounds(12, 159, 199, 22);
		contentPane.add(tfdBMI);
		tfdBMI.setColumns(10);

		tfdNotice = new JTextField();
		tfdNotice.setBounds(12, 193, 199, 22);
		contentPane.add(tfdNotice);
		tfdNotice.setColumns(10);

		tpBMIRef = new JTextPane();
		tpBMIRef.setBackground(Color.ORANGE);
		tpBMIRef.setEditable(false);
		tpBMIRef.setBounds(238, 12, 179, 203);
		tpBMIRef.setText("BMI 参考对比值：\n\n"
				+ "< 18.5 过轻\n\n"
				+ "18.5－23.9 正常\n\n"
				+ "24－27.9 超重\n\n"
				+ ">28 肥胖");
		contentPane.add(tpBMIRef);
	}
}

