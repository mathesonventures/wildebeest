# Code Standard

## Introduction

This document captures the coding standard that applies to all code in Wildebeest.

NOTE: This is currently a **rough** minimum viable capture of the applicable standards.  This will be formalized based off a common industry-best-practice standard, and corresponding IntelliJ profile will be added to make compliance easy.

## Java

- All files must have the standard code header that asserts copyright and GNU GPLv2 license.  See Materials/code_header_java.txt for the header
- Maximum line length is 120
- Indent with tabs.  It's recommended to set your IDE to present tabs as four spaces
- Braces on following line in all cases
- Single space character after if, while, switch, etc - e.g. if (condition) not if(condition)
- Class fields aka member variables should be named with pure camel-case e.g "thisIsMyField".  No prefix such as "_" is necessary.
- Method level annotations such as @Override should sit on the previous line above the method.

Javadoc elements with three parts are formatted with the second part at column 21 and the third part at column 49 - e.g:

```
	/**
	 * Creates a new AnsiSqlTableDoesNotExistAssertion.
	 * 
	 * @param       assertionId                 the ID for the new assertion.
	 * @param       seqNum                      the ordinal index of the new assertion within it's parent container.
	 * @param       schemaName                  the name of the schema to check.
	 * @param       tableName                   the name of the table to check.
	 * @since                                   4.0
	 */
```

Javadoc elements with two parts are formatted with the second part at column 49 - e.g:

```
/**
 * Base class for building DatabaseInstance implementations.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
```

