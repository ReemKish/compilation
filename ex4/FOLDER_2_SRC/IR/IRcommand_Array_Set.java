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
	TEMP pointer;
	TEMP offset;
	TEMP src;

	public IRcommand_Array_Set(TEMP pointer, TEMP offset, TEMP src)
	{
		this.src = src;
		this.pointer = pointer;
		this.offset = offset;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//TODO: fix: endProgLabel should be an ABORT
		TEMP s0 = IR.getInstance().s0;
		//$t2 = offset, $t1 = pointer, $t0 = dst
		// bltz $t2, abort (abort if offset < 0)
		sir_MIPS_a_lot.getInstance().bltz(offset, IR.endProgLabel);
		// lw $s0, 0($t1)
		sir_MIPS_a_lot.getInstance().load(s0, pointer, 0);
		// bge $t2, $s0, abort (abort if offset >= len)
		sir_MIPS_a_lot.getInstance().bge(offset, s0, IR.endProgLabel);
		// move $s0, $t2
		sir_MIPS_a_lot.getInstance().move(s0, offset);
		// add $s0, $s0, 1
		sir_MIPS_a_lot.getInstance().addi(s0, s0, 1);
		// mul $s0, $s0, 4
		sir_MIPS_a_lot.getInstance().mul(s0, s0, IR.getInstance().wordSizeTemp);
		// add $s0, $t1, $s0
		sir_MIPS_a_lot.getInstance().add(s0, pointer,offset);
		// lw $t0, 0($s0)
		sir_MIPS_a_lot.getInstance().store(src, s0, 0);	}

	public void printMe() { IR.getInstance().fileNewLine(); IR.getInstance().filePrintln("array_set " + pointer + ", " + offset + ", " + src); }
}
