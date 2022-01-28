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
	String var_name;
	TEMP src;

	public IRcommand_Array_Set(String var_name, TEMP src)
	{
		this.src      = src;
		this.var_name = var_name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().store(var_name,src);
	}
}
