package co.mv.stm.model;

/**
 * The response from an {@link Assertion}'s {@link Assertion#apply()} method.
 * 
 * Note: this is not to be confused with an AssertionResult, which is created from an AssertionResponse but is returned
 * by aggregate functions such as {@link Resource#assertState()}
 * 
 * @author brendonm
 */
public interface AssertionResponse
{
	boolean getResult();
	
	String getMessage();
}
