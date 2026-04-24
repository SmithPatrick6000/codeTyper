
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class TypingGame {

    // File that holds the names of other files to be used
    public String resFile = "resources/filenamebank.txt";

    private String[] wordBank;

    private static Random rand;;
    
    
    Scanner scanner = new Scanner(System.in);

    public TypingGame() throws IOException
    {
        rand = new Random();
        initFilenameBank(resFile);
        
    }


    public void promptUser()
    {

        String typeWord = randomWordBank(wordBank);
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
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);


        int docSize = Integer.parseInt(br.readLine());

        String[] temp = new String[docSize];
        for (int i = 0; i < docSize; i++)
        {
            temp[i] = br.readLine();
        }
        br.close();
        this.wordBank = temp;

    }




}
