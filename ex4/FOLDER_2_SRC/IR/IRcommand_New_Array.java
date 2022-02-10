/***********/
/* PACKAGE */
/***********/
package IR;
import TEMP.*;
import TYPES.*;
/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

import MIPS.sir_MIPS_a_lot;
/* TODO - copy-pasted from another IRcommand, adjustments required */

public class IRcommand_New_Array extends IRcommand
{
	TEMP pointer;
	TEMP len;

	public IRcommand_New_Array(TEMP pointer,  TEMP len)
	{
		this.pointer = pointer;
		this.len = len;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//TODO: implement MIPSme

		// li $v0, 9
		sir_MIPS_a_lot.getInstance().li(IR.getInstance().v0, 9);
		// move $a0, len
		sir_MIPS_a_lot.getInstance().move(IR.getInstance().a0, len);
		// addi $a0, $a0, 1
		sir_MIPS_a_lot.getInstance().addi(IR.getInstance().a0, IR.getInstance().a0, 1);
		// mul $a0, $a0, 4
		sir_MIPS_a_lot.getInstance().mul(IR.getInstance().a0, IR.getInstance().a0, IR.getInstance().wordSizeTemp);
		// syscall
		sir_MIPS_a_lot.getInstance().syscall();
		// move pointer, $v0
		sir_MIPS_a_lot.getInstance().move(pointer, IR.getInstance().v0);
		// sw len, 0(pointer)
		sir_MIPS_a_lot.getInstance().store(len, pointer, 0);
	}

	public void printMe() { IR.getInstance().fileNewLine(); IR.getInstance().filePrintln(pointer + " = new_array, " + len); }
}
