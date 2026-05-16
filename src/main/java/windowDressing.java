import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class windowDressing {

    private static final int BALL_CODE_POS = 1;
    private int gamemode;

    // Game object
    fileChooser fc;
    typingGame game;

    private final int WIDTH = 400;
    private final int HEIGHT = 300;
    private String tabSize;

    // Frame
    private JFrame frame;

    // Screen switching
    private CardLayout cardLayout;
    private JPanel panelContainer;

    // Panels
    private JPanel titlePanel;
    private JPanel panel2;
    private JPanel panel3;

    // Data
    public int errorAmount;
    public String correct;
    public String user;
    public String[] errorList;
    public Timer timer;
    long startTime;
    String[] modes = {"Java","C"};

    // Logo
    private Image logoImage;
    private JLabel title;

    public windowDressing()
    {
        fc = new fileChooser();
        frame = new JFrame("Typing Game");

        errorAmount = 0;
        errorList = new String[1];

        correct = "Def";
        user = "Def";

        initWindow();
    }

    private void initWindow()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        cardLayout = new CardLayout();
        panelContainer = new JPanel(cardLayout);

        initPanels();

        frame.add(panelContainer);
        frame.setVisible(true);

        // Ensure logo is scaled AFTER frame exists
        SwingUtilities.invokeLater(() -> resizeLogo());

        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                resizeLogo();
            }
        });
    }

    public void initPanels()
    {
        initTitlePanel();
        fc.javaCode();
        initPanel2();
        initPanel3();
    }


    private void initTitlePanel()
    {
        titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(Color.WHITE);

        JButton button = new JButton("Start");
        button.addActionListener(e -> showGame());

        JComboBox<String> dropdown = new JComboBox<>(modes);

        dropdown.addActionListener(e -> {
            String selected = (String) dropdown.getSelectedItem();
            switch (selected)
            {
                case "Java":
                    javaMode();
                    break;
                case "C":
                    cMode();
                    break;
                default:
                    System.err.print("No Mode Selected");
            }
        });

        JLabel label = new JLabel("Click the Button");
        label.setForeground(Color.WHITE);

        // Load logo
        logoImage = new ImageIcon("resources/title.png").getImage();
        title = new JLabel();

        // GridBag layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 0;
        titlePanel.add(title, gbc);

        gbc.gridy = 1;
        titlePanel.add(label, gbc);

        gbc.gridy = 2;
        titlePanel.add(button, gbc);

        gbc.gridy = 3;
        titlePanel.add(dropdown, gbc);

        panelContainer.add(titlePanel, "menu");
    }

    private void resizeLogo()
    {
        if (logoImage == null) {
            System.out.println("Logo image is null");
            return;
        }

        int w = frame.getWidth();
        int h = frame.getHeight();

        if (w <= 0 || h <= 0) return;

        Image scaled = logoImage.getScaledInstance(
                Math.max(300, w / 3),
                Math.max(100, h / 4),
                Image.SCALE_SMOOTH
        );

        title.setIcon(new ImageIcon(scaled));
        title.revalidate();
        title.repaint();
    }


    private void initPanel2()
    {
        panel2 = new JPanel(new GridBagLayout());
        panel2.setBackground(Color.BLACK);

        errorAmount = 0;

        game = new typingGame();
        game.loadRandBlock(fc);

        String currWord = game.getCurrentBlock();
        tabSize = game.getTabSize();

        JTextArea toType = new JTextArea(currWord);
        toType.setEditable(false);
        toType.setLineWrap(true);
        toType.setWrapStyleWord(true);

        JTextArea textArea = new JTextArea("");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        textArea.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "insert-tab");
        textArea.getActionMap().put("insert-tab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.insert(tabSize, textArea.getCaretPosition());
            }
        });

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}

            public void removeUpdate(DocumentEvent e) {}

            public void insertUpdate(DocumentEvent e)
            {
                if (game.checkDone(textArea.getText()))
                {
                    showEnd();
                }
            }
        });

        JScrollPane scroll1 = new JScrollPane(toType);
        JScrollPane scroll2 = new JScrollPane(textArea);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        panel2.add(scroll1, gbc);

        gbc.gridy = 1;
        panel2.add(scroll2, gbc);

        panelContainer.add(panel2, "game");
    }

    private void initPanel3()
    {
        panel3 = new JPanel(new GridBagLayout());
        panel3.setBackground(Color.BLACK);

        JTextPane correctPane = new JTextPane();
        JTextPane userPane = new JTextPane();

        correctPane.setBackground(Color.BLACK);
        userPane.setBackground(Color.BLACK);

        correctPane.setEditable(false);
        userPane.setEditable(false);

        if (game == null)
        {
            correct = "";
            user = "";
        }
        else
        {
            correct = game.getParsedCurr();
            user = game.getParsedUser();

            if (correct == null) correct = "";
            if (user == null) user = "";
        }

        setColoredText(correctPane, correct, correct);
        setColoredText(userPane, user, correct);

        JTextArea errors = new JTextArea();
        StringBuilder sb = new StringBuilder();

        sb.append("Errors: ").append(errorAmount).append("\n");
        errors.setText(sb.toString());
        errors.setEditable(false);
        int charAmount = game.getParsedCurr().length();

        JTextArea timeTake = new JTextArea(
                "Total Time: " + startTime / 1000.0 +
                " Seconds\nWPM: " + ((charAmount / 5)) / (startTime /60000.0)
        );
        timeTake.setEditable(false);

        JButton againButton = new JButton("Play Again?");
        againButton.addActionListener(e -> showGame());
        JButton titleButton = new JButton("Menu");
        titleButton.addActionListener(e -> showTitle());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        panel3.add(new JScrollPane(correctPane), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        panel3.add(new JScrollPane(userPane), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        panel3.add(errors, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel3.add(timeTake, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel3.add(againButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel3.add(titleButton, gbc);



        panelContainer.add(panel3, "end");
    }

    private void setColoredText (JTextPane pane, String text, String reference)
    {
        StyledDocument doc = pane.getStyledDocument();
        pane.setText("");

        StyleContext sc = StyleContext.getDefaultStyleContext();

        for (int i = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);

            Color color;
            
            if (i < reference.length() && text.charAt(i) == reference.charAt(i))
            {
                color = Color.GREEN;
            }
            else
            {
                color = Color.RED;
            }

            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, 
                                    StyleConstants.Foreground, color);
            
            try 
            {
                doc.insertString(doc.getLength(), String.valueOf(c), aset);
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
            
        }
    }


    private void showTitle()
    {
        //initTitlePanel();
        cardLayout.show(panelContainer, "menu");
    }

    private void showGame()
    {
        startTime = System.currentTimeMillis();

        timer = new Timer(1000, e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            double seconds = elapsed / 1000.0;
        });

        timer.start();

        initPanel2();
        cardLayout.show(panelContainer, "game");
    }

    private void showEnd()
    {
        timer.stop();
        startTime = System.currentTimeMillis() - startTime;

        errorList = game.checkAfterMethod();
        errorAmount = errorList.length;

        initPanel3();
        cardLayout.show(panelContainer, "end");
    }

    public void javaMode()
    {
        fc.javaCode();
    }

    public void cMode()
    {
        fc.cCode();
    }
}