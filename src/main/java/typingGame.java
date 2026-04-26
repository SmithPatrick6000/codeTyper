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
    public int checkAfterMethod()
    {
        System.out.println(getParsedCurr());
        System.out.println(getParsedUser());
        int errorNum = 0;
        for(int i = 0; i < getParsedCurr().length(); i++)
        {
            if(getParsedCurr().charAt(i) != getParsedUser().charAt(i))
            {
                System.out.println("Should be: " + getParsedCurr().charAt(i) + " User Input: " + getParsedUser().charAt(i));
                errorNum++;
            }
        }
        
        return errorNum;
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
