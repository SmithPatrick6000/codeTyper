
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

public class fileChooser {

    // File that holds the names of other files to be used
    public String resFile = "resources/filenamebank.txt";

    FileReader fr;
    BufferedReader br;


    private String[] filenameBank;
    private String[] wordsToType;

    private static Random rand;;
    
    
    Scanner scanner = new Scanner(System.in);

    public fileChooser() 
    {
        rand = new Random();
        initRandom();
        
        
    }
    public String initRandom()
    {
        initFilenameBank(resFile);
        loadWordBank("resources/"+randomWordBank(filenameBank));
        return this.wordsToType[0];
    }

    
    /** randomWordBank
     *  Gets a random part out of any array
     * 
     * @return
     */
    private <T> T randomWordBank(T[] array) 
    {
        int n = rand.nextInt(array.length);
        return array[n];
    }

    
    /** initFilenameBank
     *  Creates an array of all the different potential files to pull from
     * 
     * @param fileName
     */
    private void initFilenameBank(String fileName)
    {
        
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            
            int docSize = Integer.parseInt(br.readLine());

            String[] temp = new String[docSize];
            for (int i = 0; i < docSize; i++)
            {
                temp[i] = br.readLine();
            }
            br.close();
            this.filenameBank = temp;

        } catch (Exception e) {
            System.err.println("initFilenameBank Error: " + e);
        }

    }



    /** loadWordBank
     *  Currently loads a whole file into a single String
     *      May change as this develops
     * 
     * @param filename
     */
    private void loadWordBank(String filename) 
    {
        try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);
            StringBuilder temp = new StringBuilder();
            String line;
            while((line = br.readLine()) != null)
            {
                temp.append(line);
            }
            String[] tempArray = new String[1];
            tempArray[0] = temp.toString();
            this.wordsToType = tempArray;
            
        } catch (Exception e) {
            System.err.println("loadWordBank Error: " + e);
        }
        

    }




}
