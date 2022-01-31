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
import TEMP.*;

public class IRcommand_Func_Call extends IRcommand
{
	/* TODO - copy-pasted from another IRcommand, adjustments required */
	String label_name;
	TEMP_LIST args;
	TEMP resReg;

	public IRcommand_Func_Call(TEMP resReg, String label_name, TEMP_LIST args)
	{
		this.label_name = label_name;
		this.args = args;
		this.resReg = resReg;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().jump(label_name);
	}
}
