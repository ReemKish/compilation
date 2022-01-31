/***********/
/* PACKAGE */
/***********/
package IR;
import TEMP.*;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

import MIPS.sir_MIPS_a_lot;
/* TODO - copy-pasted from another IRcommand, adjustments required */

public class IRcommand_Return extends IRcommand
{
	TEMP val;

	public IRcommand_Return(TEMP val)
	{
		this.val = val;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().ret(val);
	}
}
