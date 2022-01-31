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

public class IRcommand_Field_Access extends IRcommand
{
	TEMP dst;
	TEMP base;
	String fieldName;

	public IRcommand_Field_Access(TEMP dst, TEMP base, String fieldName)
	{
		this.dst      = dst;
		this.base      = base;
		this.fieldName = fieldName;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		// TODO: implement me!
	}
}
