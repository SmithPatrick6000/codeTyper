import java.util.ArrayList;

public class typingGame {
    
    public static final String TAB_SIZE = "    ";
    private String currentBlock;

    private String parsedCurr;
    private String parsedUser;
    

    public typingGame()
    {

    }

    /** loadRandBlock
     *  Loads a random block of code
     * 
     * @param fc
     */
    public void loadRandBlock(fileChooser fc)
    {
        currentBlock = fc.getRandomBlock();
        setParsedCurr(currentBlock.replace("\r\n", "\n").replace("\t", TAB_SIZE));
    }

    /** loadBlock
     *  Loads a specific block of code
     *      Need to know the exact position of it
     * 
     * @param fc
     * @param id
     */
    public void loadBlock(fileChooser fc, int id)
    {
        currentBlock = fc.getBlock(id);
        setParsedCurr(currentBlock.replace("\r\n", "\n").replace("\t", TAB_SIZE));
    }

    public String getCurrentBlock()
    {
        return this.currentBlock;
    }

    /** checkDone
     *  Compares length of the User Input to the Current Word
     * 
     * @param userIn - The current User input
     * @return - true if the length is the same, false if not
     */
    public boolean checkDone(String userIn)
    {
        
        userIn = userIn.replace("\r\n", "\n").replace("\t", TAB_SIZE);
        if (getParsedCurr().length() == userIn.length()) 
        {
            setParsedUser(userIn);
            return true;
        }

        return false;
    }


    /** checkAfterMethod
     *  Compares the UserInput to the CurrentWord
     *      Right now just outputs the number of errors
     * 
     * @return - The number of errors the user committed
     */
    public String[] checkAfterMethod()
{
    if (getParsedUser() == null)
        return new String[0];

    ArrayList<String> errorList = new ArrayList<>();

    int limit = Math.min(getParsedCurr().length(), getParsedUser().length());

    for (int i = 0; i < limit; i++)
    {
        if (getParsedCurr().charAt(i) != getParsedUser().charAt(i))
        {
            errorList.add(
                "Should be: " + getParsedCurr().charAt(i) +
                " User Input: " + getParsedUser().charAt(i)
            );
        }
    }

    return errorList.toArray(new String[0]);
}



    public String getTabSize()
    {
        return this.TAB_SIZE;
    }



    public String getParsedCurr()
    {
        return this.parsedCurr;
    }
    public void setParsedCurr(String parsedCurr)
    {
        this.parsedCurr = parsedCurr;
    }
    public String getParsedUser()
    {
        return this.parsedUser;
    }
    public void setParsedUser(String parsedUser)
    {
        this.parsedUser = parsedUser;
    }


}
