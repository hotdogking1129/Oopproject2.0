package part1;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.JPanel;
import javax.swing.JButton;

public class MyFrame extends JFrame {
	
	JButton button;
	
	MyFrame(){
		
		//Declare item
		ImageIcon image = new ImageIcon("93370412_p0.jpg");
		ImageIcon image2= new ImageIcon("image1.png");
		Border border = BorderFactory.createLineBorder(Color.WHITE,3);
		
		/*
		//Set Label
		JLabel label = new JLabel();
		label.setText("Fight for Love");
		label.setIcon(image2);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setForeground(new Color(250,250,250));
		label.setFont(new Font("MV Boli",Font.PLAIN,80));
		label.setIconTextGap(10);
		label.setBackground(Color.darkGray);
		label.setOpaque(true);
		label.setBorder(border);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setBounds(100,100,600,600);
		*/
		
		//Set frame
		JFrame frame = new JFrame();
		frame.setTitle("Food Ordering System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setSize(1280,720);
		frame.setLayout(null);
		frame.setVisible(true);
		//frame.add(label);
		//frame.pack();

		//Set Background
		frame.setIconImage(image.getImage());
		frame.getContentPane().setBackground(Color.white);
		
		/*
		//Set Panel
		JPanel redPanel = new JPanel();
		redPanel.setBackground(Color.red);
		redPanel.setBounds(0,0,700,700);
		frame.add(redPanel);
		redPanel.add(label);
		*/
		
		button = new JButton();
		button.setBounds(100,100,250,150);
		button.addActionListener(e -> System.out.println("Arh!!!"));
		button.setText("click me to fuck me");
		//button.setIcon(image);
		button.setFont(new Font("New Times Romen",Font.BOLD,20));
		button.setVerticalAlignment(JButton.CENTER);
		button.setHorizontalAlignment(JButton.CENTER);
		frame.add(button);
		
	}	
}
