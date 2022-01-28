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
	String var_name;

	public IRcommand_Field_Set(TEMP dst, String var_name)
	{
		this.dst      = dst;
		this.var_name = var_name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().load(dst,var_name);
	}
}
