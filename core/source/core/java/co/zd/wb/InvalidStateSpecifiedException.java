package co.zd.wb;

public class InvalidStateSpecifiedException extends Exception
{
    public InvalidStateSpecifiedException(String specifiedState)
    {
        super(String.format("Specified state is not valid: \"%s\"", specifiedState));
        
        this.setSpecifiedState(specifiedState);
    }
    
	// <editor-fold desc="SpecifiedState" defaultstate="collapsed">

	private String _specifiedState = null;
	private boolean _specifiedState_set = false;

	public String getSpecifiedState() {
		if(!_specifiedState_set) {
			throw new IllegalStateException("specifiedState not set.");
		}
		if(_specifiedState == null) {
			throw new IllegalStateException("specifiedState should not be null");
		}
		return _specifiedState;
	}

	private void setSpecifiedState(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("specifiedState cannot be null");
		}
		boolean changing = !_specifiedState_set || _specifiedState != value;
		if(changing) {
			_specifiedState_set = true;
			_specifiedState = value;
		}
	}

	private void clearSpecifiedState() {
		if(_specifiedState_set) {
			_specifiedState_set = true;
			_specifiedState = null;
		}
	}

	private boolean hasSpecifiedState() {
		return _specifiedState_set;
	}

	// </editor-fold>
}