// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
//
// This file is part of Wildebeest
//
// Wildebeest is free software: you can redistribute it and/or modify it under
// the terms of the GNU General Public License v2 as published by the Free
// Software Foundation.
//
// Wildebeest is distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
// A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along with
// Wildebeest.  If not, see http://www.gnu.org/licenses/gpl-2.0.html

package co.zd.wb.service;

import co.zd.wb.Assertion;
import co.zd.wb.AssertionFailedException;
import co.zd.wb.AssertionResponse;
import co.zd.wb.IndeterminateStateException;
import co.zd.wb.Resource;
import co.zd.wb.Migration;
import co.zd.wb.MigrationFailedException;
import co.zd.wb.MigrationNotPossibleException;

public interface Logger
{
	void assertionStart(Assertion assertion);
	
	void assertionComplete(Assertion assertion, AssertionResponse response);
	
	void migrationStart(
		Resource resource,
		Migration migration);
	
	void migrationComplete(
		Resource resource,
		Migration migration);
	
	void indeterminateState(
		IndeterminateStateException e);
	
	void assertionFailed(
		AssertionFailedException e);

	void migrationNotPossible(
		MigrationNotPossibleException e);
	
	void migrationFailed(
		MigrationFailedException e);
	
	void logLine(String message);
}