package co.zd.wb;

import co.mv.protium.system.NotImplementedException;

public class FakeLogger implements Logger
{
	// <editor-fold desc="InvalidStateSpecifiedException" defaultstate="collapsed">

	private InvalidStateSpecifiedException _invalidStateSpecifiedException = null;
	private boolean _invalidStateSpecifiedException_set = false;

	public InvalidStateSpecifiedException getInvalidStateSpecifiedException() {
		if(!_invalidStateSpecifiedException_set) {
			throw new IllegalStateException("invalidStateSpecifiedException not set.");
		}
		if(_invalidStateSpecifiedException == null) {
			throw new IllegalStateException("invalidStateSpecifiedException should not be null");
		}
		return _invalidStateSpecifiedException;
	}

	private void setInvalidStateSpecifiedException(
		InvalidStateSpecifiedException value) {
		if(value == null) {
			throw new IllegalArgumentException("invalidStateSpecifiedException cannot be null");
		}
		boolean changing = !_invalidStateSpecifiedException_set || _invalidStateSpecifiedException != value;
		if(changing) {
			_invalidStateSpecifiedException_set = true;
			_invalidStateSpecifiedException = value;
		}
	}

	private void clearInvalidStateSpecifiedException() {
		if(_invalidStateSpecifiedException_set) {
			_invalidStateSpecifiedException_set = true;
			_invalidStateSpecifiedException = null;
		}
	}

	public boolean hasInvalidStateSpecifiedException() {
		return _invalidStateSpecifiedException_set;
	}

	// </editor-fold>
    
	// <editor-fold desc="UnknownStateSpecifiedException" defaultstate="collapsed">

	private UnknownStateSpecifiedException _unknownStateSpecifiedException = null;
	private boolean _unknownStateSpecifiedException_set = false;

	public UnknownStateSpecifiedException getUnknownStateSpecifiedException() {
		if(!_unknownStateSpecifiedException_set) {
			throw new IllegalStateException("unknownStateSpecifiedException not set.");
		}
		if(_unknownStateSpecifiedException == null) {
			throw new IllegalStateException("unknownStateSpecifiedException should not be null");
		}
		return _unknownStateSpecifiedException;
	}

	private void setUnknownStateSpecifiedException(
		UnknownStateSpecifiedException value) {
		if(value == null) {
			throw new IllegalArgumentException("unknownStateSpecifiedException cannot be null");
		}
		boolean changing = !_unknownStateSpecifiedException_set || _unknownStateSpecifiedException != value;
		if(changing) {
			_unknownStateSpecifiedException_set = true;
			_unknownStateSpecifiedException = value;
		}
	}

	private void clearUnknownStateSpecifiedException() {
		if(_unknownStateSpecifiedException_set) {
			_unknownStateSpecifiedException_set = true;
			_unknownStateSpecifiedException = null;
		}
	}

	public boolean hasUnknownStateSpecifiedException() {
		return _unknownStateSpecifiedException_set;
	}

	// </editor-fold>
    
    @Override public void assertionStart(Assertion assertion)
    {
        throw new NotImplementedException();
    }

    @Override public void assertionComplete(Assertion assertion, AssertionResponse response)
    {
        throw new NotImplementedException();
    }

    @Override public void invalidStateSpecified(InvalidStateSpecifiedException e)
    {
        this.setInvalidStateSpecifiedException(e);
    }

    @Override public void unknownStateSpecified(UnknownStateSpecifiedException e)
    {
        this.setUnknownStateSpecifiedException(e);
    }

    @Override public void migrationStart(Resource resource, Migration migration)
    {
        throw new NotImplementedException();
    }

    @Override public void migrationComplete(Resource resource, Migration migration)
    {
        throw new NotImplementedException();
    }

    @Override public void indeterminateState(IndeterminateStateException e)
    {
        throw new NotImplementedException();
    }

    @Override public void assertionFailed(AssertionFailedException e)
    {
        throw new NotImplementedException();
    }

    @Override public void migrationNotPossible(MigrationNotPossibleException e)
    {
        throw new NotImplementedException();
    }

    @Override public void migrationFailed(MigrationFailedException e)
    {
        throw new NotImplementedException();
    }

    @Override public void jumpStateFailed(JumpStateFailedException e)
    {
        throw new NotImplementedException();
    }

    @Override public void logLine(String message)
    {
        throw new NotImplementedException();
    }
}