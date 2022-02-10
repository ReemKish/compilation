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

public class IRcommand_New_Array extends IRcommand
{
	TEMP pointer;
	TEMP len;
	TYPE className;

	public IRcommand_New_Array(TEMP pointer, TYPE className,  TEMP len)
	{
		this.pointer = pointer;
		this.len = len;
		this.className = className;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//TODO: implement MIPSme
	}

	public void printMe() { IR.getInstance().fileNewLine(); IR.getInstance().filePrintln(pointer + " = new_array " + className + ", " + len); }
}
