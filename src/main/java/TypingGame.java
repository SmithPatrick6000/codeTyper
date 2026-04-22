
import java.util.Scanner;

public class TypingGame {


    private String typeWord;
    
    
    Scanner scanner = new Scanner(System.in);

    public TypingGame()
    {
        setTypeWord("Yellow");
    }


    public void promptUser()
    {
        System.out.println("Print" + typeWord);

        long startTime = System.nanoTime();

        String userWord = scanner.nextLine();
        long endTime = System.nanoTime();
        long duration = ((endTime - startTime)/1000000);
        if (userWord == null ? getTypeWord() == null : userWord.equals(getTypeWord()))
        {
            
            System.out.println("You did it in " + duration + " milliseconds");
        }
        else
        {
            System.out.println("Brother How");
        }


    }





    private String getTypeWord()
    {
        return this.typeWord;
    }
    private void setTypeWord(String typeWord)
    {
        this.typeWord = typeWord;
    }




}
