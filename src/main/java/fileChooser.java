
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class fileChooser {

    // File that holds the names of other files to be used
    //public String resFile = "resources/filenamebank.txt";
    FileReader fr;
    BufferedReader br;

    //The file with the different java code options
    private final String javaCodePath = "resources/javabank.txt";
    private final String cCodePath = "resources/cbank.txt";

    // Holds the loaded blocks of code from the chosen language
    ArrayList<String> blocks;


    Random rand;

    public fileChooser()
    {
    }

    /** javaCode
     *  Call when the java option is selected.  Loads java code into the ArrayList
     * 
     */
    public void javaCode()
    {
        loadCode(javaCodePath);
    }
    public void cCode()
    {
        loadCode(cCodePath);
    }

    /** loadCode
     *  Loads the code from the selected file and puts each block into an ArrayList
     * 
     * @param filename - The name of the file to open
     */
    public void loadCode(String filename)
    {
        // Makes sure old data is no longer in blocks
        blocks = new ArrayList<>();

        try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);
            Scanner scanner = new Scanner(br);
            scanner.useDelimiter("--DELIMITER--");
            
            String block;


            while (scanner.hasNext())
            {
                block = scanner.next();
                while (block.startsWith("\n") || block.startsWith("\r"))
                {
                    block = block.substring(1);
                }
                blocks.add(block);
                
            }
            scanner.close();


        } catch (Exception e) {
        }

    }

    /** getRandomBlock 
     *  Returns a random block of code to type
     * 
     * @return - Block of code
     */
    public String getRandomBlock()
    {
        rand = new Random();
        
        return blocks.get(rand.nextInt((blocks.size())));
    }

    /** getBlock
     *  Gets a specified block from a file
     *      You have to know the specific position beforehand
     *          Mostly for testing purposes
     * 
     * @param id - The Location of the code block
     * @return - Block of code
     */
    public String getBlock(int id)
    {
        return blocks.get(id);
    }

    /** printBlocks
     *  Prints all blocks of code in the blocks array
     * 
     */
    public void printBlocks()
    {
        for(int i = 0; i < blocks.size();i++)
        {
            //System.out.print("Start");
            System.out.print(blocks.get(i));
            //System.out.print("End");
        }
    }

}
