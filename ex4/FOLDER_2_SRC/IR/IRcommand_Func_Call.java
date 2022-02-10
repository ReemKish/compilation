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

import AST.AST_EXP_LIST;
import MIPS.sir_MIPS_a_lot;
import TEMP.*;
import TYPES.TYPE_FUNCTION;

public class IRcommand_Func_Call extends IRcommand
{
	/* TODO - copy-pasted from another IRcommand, adjustments required */
	TYPE_FUNCTION func;
	TEMP_LIST args;
	TEMP resReg;

	public IRcommand_Func_Call(TEMP resReg, TYPE_FUNCTION func, TEMP_LIST args)
	{
		this.func = func;
		this.args = args;
		this.resReg = resReg;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		TEMP sp = IR.getInstance().sp;
		if(func.isSysCall){
			int sysCallNum = func.sysCallNum;
			// TODO: implement system call
			// hint - use codegen1.pptx presentation (tirgul 10), slide 15 for reference
			// sysCallNum should already contain the correct Syscall number
		}
		/*TODO: possibly need to reverse list before printing MIPs*/
		int argCount = 0;
		for (TEMP_LIST a = args; a != null; a=a.tail)
		{
			argCount++;
			TEMP arg = a.head;
			sir_MIPS_a_lot.getInstance().addi(sp, sp, -sir_MIPS_a_lot.WORD_SIZE);
			sir_MIPS_a_lot.getInstance().store(arg, sp, 0);
		}
		sir_MIPS_a_lot.getInstance().jal(IR.funcLabelPrefix + func.name);
		sir_MIPS_a_lot.getInstance().addi(sp, sp, sir_MIPS_a_lot.WORD_SIZE * argCount);
		sir_MIPS_a_lot.getInstance().move(resReg, IR.getInstance().v0);

	}

	public void printMe() { IR.getInstance().fileNewLine(); IR.getInstance().filePrintln(resReg + " = call " + func.name + " " + (args != null ? args : "")); }
}
