/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

import MIPS.sir_MIPS_a_lot;
import TEMP.TEMP;
/* TODO - copy-pasted from another IRcommand, adjustments required */

public class IRcommand_Array_Set extends IRcommand
{
	TEMP arr;
	TEMP offset;
	TEMP src;

	public IRcommand_Array_Set(TEMP arr, TEMP offset, TEMP src)
	{
		this.src = src;
		this.arr = arr;
		this.offset = offset;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//sir_MIPS_a_lot.getInstance().store(var_name,src);
	}

	/* TODO */
	public void printMe() { IR.getInstance().fileNewLine(); IR.getInstance().filePrintln("array_set " + arr + ", " + offset + ", " + src); }
}
