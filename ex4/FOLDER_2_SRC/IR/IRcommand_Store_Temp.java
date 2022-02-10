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
import TEMP.*;
import MIPS.*;

public class IRcommand_Store_Temp extends IRcommand
{
	TEMP dst;
	TEMP src;
	int offset;
	
	public IRcommand_Store_Temp(TEMP src, TEMP dst, int offset)
	{
		this.src = src;
		this.dst = dst;
		this.offset = offset;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().store(dst, src, offset);
	}

	public void printMe() { super.printLine(); System.out.println(dst + " = " + src); }
}
