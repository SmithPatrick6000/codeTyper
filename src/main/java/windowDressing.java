
import java.awt.*;
import javax.swing.*;



public class windowDressing {

    //Game object
    fileChooser fc;

    private final int width = 400;
    private final int height = 300;

    //Frame
    private JFrame frame;

    // How to switch screens
    private CardLayout cardLayout;
    private JPanel panelContainer;

    //Panels
    private JPanel panel1;
    private JPanel panel2;

    public windowDressing()
    {
        fc = new fileChooser();
        frame = new JFrame("Typing Game");
        initWindow();
    }

    private void initWindow()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelContainer = new JPanel(cardLayout);

        initPanel1();
        initPanel2();

        panelContainer.add(panel1, "menu");
        panelContainer.add(panel2, "game");

        frame.add(panelContainer);
        
        frame.setVisible(true);
    }

    private void initPanel1()
    {
        panel1 = new JPanel();
        JButton button = new JButton("Start");
        button.addActionListener(e -> showGame());

        JLabel label = new JLabel("Click the Button");


        panel1.add(label);
        panel1.add(button);
    }

    private void initPanel2()
    {
        panel2 = new JPanel();

        JTextField textField = new JTextField("Default text...");
        textField.setPreferredSize(new Dimension(200, 30));

        String words = fc.initRandom();

        JLabel toType = new JLabel(words);

        panel2.add(toType);
        panel2.add(textField);
    }

    private void showGame()
    {
        cardLayout.show(panelContainer, "game");
    }








}