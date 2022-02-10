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

public class IRcommand_Load extends IRcommand
{
	TEMP dst;
	TEMP src;
	int offset;
	
	public IRcommand_Load(TEMP dst, TEMP src, int offset)
	{
		this.dst = dst;
		this.src = src;
		this.offset = offset;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().load(dst, src, offset);
	}

	public void printMe() { IR.getInstance().fileNewLine(); IR.getInstance().filePrintln(dst + " = " + offset + "(" + src + ")"); }
}
