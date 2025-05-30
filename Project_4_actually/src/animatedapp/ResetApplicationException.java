package animatedapp;


/**
 * An exception used to signal that we want to reset the application 
 * in the current thread.
 * 
 * @author Charles Hoot
 * @version 5.0
 */

public final class ResetApplicationException extends RuntimeException
{
	public ResetApplicationException(String s)
	{
		super(s);
	}
}