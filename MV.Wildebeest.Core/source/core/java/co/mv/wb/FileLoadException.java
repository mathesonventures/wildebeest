// Wildebeest Migration Framework
// Copyright © 2013 - 2018, Matheson Ventures Pte Ltd
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

package co.mv.wb;

import co.mv.wb.framework.ArgumentNullException;

import java.io.File;

/**
 * Indicates that a resource or instance could not be loaded because of a problem accessing the file.
 *
 * @since 4.0
 */
public class FileLoadException extends Exception
{
	private final File file;

	public FileLoadException(
		File file)
	{
		if (file == null) throw new ArgumentNullException("file");

		this.file = file;
	}

	public File getFile()
	{
		return file;
	}
}
