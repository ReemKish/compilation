/***********/
/* PACKAGE */
/***********/
package IR;
import TEMP.*;
import TYPES.*;
/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

import MIPS.sir_MIPS_a_lot;
/* TODO - copy-pasted from another IRcommand, adjustments required */

public class IRcommand_New_Class extends IRcommand
{
	TYPE className;
	TEMP pointer;

	public IRcommand_New_Class(TEMP pointer, TYPE className)
	{
		this.pointer = pointer;
		this.className = className;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//TODO: implement MIPSme
	}

	public void printMe() { IR.getInstance().fileNewLine(); IR.getInstance().filePrintln(pointer + " = new_class " + className); }
}
