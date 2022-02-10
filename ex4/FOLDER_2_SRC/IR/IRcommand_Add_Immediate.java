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

public class IRcommand_Add_Immediate extends IRcommand
{
	public TEMP dst;
	public TEMP src;
	public int immediate;

	public IRcommand_Add_Immediate(TEMP dst, TEMP src, int immediate)
	{
		this.dst = dst;
		this.src = src;
		this.immediate = immediate;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().addi(dst, src, immediate);
	}

	public void printMe() { IR.getInstance().fileNewLine(); IR.getInstance().filePrintln(dst + " = addi " + src + ", " + immediate); }
}
