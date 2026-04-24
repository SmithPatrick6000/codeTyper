
import javax.swing.*;

public class windowDressing {

    private final int defOp = JFrame.EXIT_ON_CLOSE;
    private final int width = 400;
    private final int height = 300;
    
    
    JFrame frame;

    public windowDressing()
    {
        frame = new JFrame("Window?");
        initWindow();
        

    }

    private void initWindow()
    {
        frame.setDefaultCloseOperation(defOp);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void initPanel1()
    {
        JPanel panel1 = new JPanel();

        

    }






}