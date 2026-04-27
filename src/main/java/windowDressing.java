
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;




public class windowDressing {

    private static final int BALL_CODE_POS = 1;

    //Game object
    fileChooser fc;
    typingGame game;

    private final int WIDTH = 400;
    private final int HEIGHT = 300;
    private String tabSize;

    //Frame
    private JFrame frame;

    // How to switch screens
    private CardLayout cardLayout;
    private JPanel panelContainer;

    //Panels
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;

    //Currently public things maybe change
    public int errorAmount;
    public Timer timer;
    long startTime;




    public windowDressing()
    {
        fc = new fileChooser();
        frame = new JFrame("Typing Game");
        errorAmount = 0;
        initWindow();
    }

    /** initWindow
     *  Initializes the JFrame
     *      Creates a Panel Container to tranfer thorugh panels easier
     * 
     */
    private void initWindow()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH,HEIGHT);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelContainer = new JPanel(cardLayout);

        initPanel1();
        initPanel2();
        initPanel3();


        frame.add(panelContainer);
        
        frame.setVisible(true);
    }

    /** initPanel1
     *  Initializes panel1 or the "Home Screen"
     * 
     */
    private void initPanel1()
    {
        panel1 = new JPanel();
        JButton button = new JButton("Start");
        button.addActionListener(e -> showGame());

        JLabel label = new JLabel("Click the Button");


        panel1.add(label);
        panel1.add(button);

        panelContainer.add(panel1, "menu");
    }

    /** initPanel2
     *  Initializes panel2 or the "game Screen"
     * 
     */
    private void initPanel2()
    {
        panel2 = new JPanel();

        panel2.setBackground(Color.BLUE);



        
        //Initialize java code
        fc.javaCode();
        game = new typingGame();
        //game.loadRandBlock(fc);
        game.loadBlock(fc, BALL_CODE_POS);
        String currWord = game.getCurrentBlock();
        tabSize = game.getTabSize();
        
        

        //Sets the Text Area with the words to type
        System.out.println(currWord);
        JTextArea toType = new JTextArea(currWord);
        toType.setEditable(false);
        toType.setPreferredSize(new Dimension(200, 100));
        toType.setText(toType.getText().replace("\t",tabSize));


        //Sets the text area for the user to type in
        JTextArea textArea = new JTextArea("Default text...");
        textArea.setPreferredSize(new Dimension(200, 100));
        textArea.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "insert-tab");
        textArea.getActionMap().put("insert-tab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.insert(tabSize, textArea.getCaretPosition()); // 4 spaces
            }
        });
        
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { System.out.println("changedUpdate"); }
            public void removeUpdate(DocumentEvent e) { System.out.println("removeUpdate"); }
            public void insertUpdate(DocumentEvent e) 
            { 
                if(game.checkDone(textArea.getText()))
                {
            
                    showEnd();
                }
                

            }
        });

        
        panel2.add(toType);
        panel2.add(textArea);

        panelContainer.add(panel2, "game");
    }

    /** initPanel3
     *  Initializes panel3 or the "End Screen"
     * 
     */
    private void initPanel3()
    {
        panel3 = new JPanel();

        panel3.setBackground(Color.BLACK);
        
        JTextArea errors = new JTextArea("Errors: " + errorAmount);
        errors.setEditable(false);
        errors.setPreferredSize(new Dimension(200, 100));
        panel3.add(errors);

        JTextArea timeTake = new JTextArea("Total Time: " + startTime/1000.0 + " Seconds");
        timeTake.setEditable(false);
        timeTake.setPreferredSize(new Dimension(200, 100));
        panel3.add(timeTake);




        panelContainer.add(panel3, "end");
    }

    /** updatePostGame
     *  Updates the necessary data after a game is over
     * 
     */
    // public void updatePostGame()
    // {
    //     startTime = System.currentTimeMillis() - startTime;
    //     errorAmount = game.checkAfterMethod();
    //     initPanel3();
    // }
    

    

    /** showGame
     *  Switches the panel to the game panel
     * 
     */
    private void showGame()
    {
        startTime = System.currentTimeMillis();
        timer = new Timer(1000, e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            double seconds = elapsed / 1000.0;

    
        });
        timer.start();
        
        cardLayout.show(panelContainer, "game");
    }

    /** showEnd
     *  Switches the panel to the end panel
     * 
     */
    private void showEnd()
    {
        timer.stop();
        startTime = System.currentTimeMillis() - startTime;
        errorAmount = game.checkAfterMethod();
        //Updates Panel3
        initPanel3();
        System.out.println("Errors: " + game.checkAfterMethod() + " Total Time: " + startTime/1000.0);
        
        //updatePostGame();
        cardLayout.show(panelContainer, "end");
        
    }



    








}