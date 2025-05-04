package animatedapp;

public class RecursiveMaxOfArray {

    /**
     * Compute the maximum of a range of values in an array recursively.
     *
     * @param data   An array of integers.
     * @param from  The low index for searching for the maximum.
     * @param to    The high index for searching for the maximum.
     * 
     * preconditions: from LTE Zero to, from LTE 0, to LT length, length GT 0
     *                
     * @return     The maximum value in the array.
     */
    
    public  int max(int data[], int from, int to)
    {
        int result = 0;
        if (data == null || data.length == 0)
            throw new BadArgumentsForMaxException("Array is null or empty.");
        if (from < 0 || to >= data.length)
            throw new BadArgumentsForMaxException("Index out of bounds.");
        if (from > to)
            throw new BadArgumentsForMaxException("From index greater than to index.");

        // Base case
        if (from == to)
            return data[from];

        // Recursive case
        int maxOfRest = max(data, from + 1, to);
        return Math.max(data[from], maxOfRest);
    

        // ADD YOUR CODE HERE
//vvvvvvvvv ADDED CODE vvvvvvvvvvvvvvvvvvvvvvvvvvvvvv        

    }
    

}
