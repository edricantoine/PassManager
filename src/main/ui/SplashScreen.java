package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen {

    private JFrame splashFrame;
    private JPanel splashPanel;
    private ImageIcon splashImage;
    private JLabel splashTextLabel;
    private JButton splashButton;
    private JLabel splashLabel;

    public SplashScreen() {

        splashFrame = new JFrame("Welcome");
        splashPanel = new JPanel();
        splashImage = new ImageIcon("./data/gears.png");
        splashLabel = new JLabel(splashImage);
        splashTextLabel = new JLabel("Welcome to Edric's Password Manager");
        splashButton = new JButton("OK");
        splashButton.setActionCommand("OK");
        splashButton.addActionListener(new EnterListener());
        setUpSplashSwing();
        setUpSplashGUI();
    }

    public void setUpSplashSwing() {
        splashPanel.add(splashTextLabel);
        splashPanel.add(splashLabel);
        splashPanel.add(splashButton);

        JPanel splashPane = new JPanel();
        splashPane.setLayout(new BoxLayout(splashPane, BoxLayout.Y_AXIS));
        splashPane.add(splashLabel);
        splashPane.setOpaque(true);

        splashPanel.add(splashPane,BorderLayout.PAGE_END);
    }

    public void setUpSplashGUI() {
        splashFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        splashPanel.setOpaque(true);
        splashFrame.setContentPane(splashPanel);
        splashFrame.pack();
        splashFrame.setVisible(true);

    }

    public class EnterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new PassManagerAppGui();
            splashFrame.dispose();
        }
    }

}
