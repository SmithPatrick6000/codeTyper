
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class TypingGame {

    // File that holds the names of other files to be used
    public String resFile = "resources/filenamebank.txt";

    FileReader fr;
    BufferedReader br;


    private String[] filenameBank;
    private String[] wordsToType;

    private static Random rand;;
    
    
    Scanner scanner = new Scanner(System.in);

    public TypingGame() throws IOException
    {
        rand = new Random();
        initRandom();
        
        
    }
    private void initRandom() throws IOException
    {
        initFilenameBank(resFile);
        loadWordBank("resources/"+randomWordBank(filenameBank));
    }


    public void promptUser()
    {

        String typeWord = this.wordsToType[0];
        System.out.println("Print: " + typeWord);

        double startTime = System.nanoTime();

        String userWord = scanner.nextLine();
        double endTime = System.nanoTime();
        double duration = ((endTime - startTime)/1000000000);
        if (userWord == null ? typeWord == null : userWord.equals(typeWord))
        {
            
            System.out.println("You did it in " + duration + " seconds");
        }
        else
        {
            System.out.println("Brother How");
        }


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
     * @throws IOException
     */
    private void initFilenameBank(String fileName) throws IOException
    {
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

    }
    /** loadWordBank
     *  Currently loads a whole file into a single String
     *      May change as this develops
     * 
     * @param filename
     * @throws IOException
     */
    private void loadWordBank(String filename) throws IOException
    {
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

    }




}
