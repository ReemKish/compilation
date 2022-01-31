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

public class IRcommand_Array_Access extends IRcommand
{
	TEMP dst;
	TEMP arr;
	TEMP offset;

	public IRcommand_Array_Access(TEMP dst, TEMP arr, TEMP offset)
	{
		this.dst = dst;
		this.arr = arr;
		this.offset = offset;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//TODO: implement me!
	}
}
