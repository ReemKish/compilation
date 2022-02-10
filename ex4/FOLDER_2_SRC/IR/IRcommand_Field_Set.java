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

public class IRcommand_Field_Set extends IRcommand
{
	TEMP dst;
	String field;
	TEMP src;


	public IRcommand_Field_Set(TEMP dst, String field, TEMP src)
	{
		this.dst      = dst;
		this.field = field;
		this.src = src;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		/*TODO: implement*/
	}

	public void printMe() { IR.getInstance().fileNewLine(); IR.getInstance().filePrintln("field_set " + dst + ", " + field + ", " + src); }
}
