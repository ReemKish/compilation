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
import TYPES.TYPE_FUNCTION;

import java.util.HashSet;
import java.util.Set;

public class IRcommand_Func_Call extends IRcommand
{
	/* TODO - copy-pasted from another IRcommand, adjustments required */
	TYPE_FUNCTION func;
	TEMP_LIST args;
	TEMP dst;

	public IRcommand_Func_Call(TEMP dst, TYPE_FUNCTION func, TEMP_LIST args)
	{
		this.func = func;
		this.args = args;
		this.dst = dst;
	}
	public Set<TEMP> usedRegs() {
		Set<TEMP> used_regs = new HashSet<TEMP>();
		TEMP_LIST curr = args;
		while(curr != null && curr.head != null) {
			used_regs.add(curr.head);
			curr = curr.tail;
		}
		return used_regs;
	}
	public TEMP modifiedReg() { return dst;}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		TEMP sp = IR.getInstance().sp;
		if(func.isSysCall){
			int sysCallNum = func.sysCallNum;
			switch (sysCallNum){
				case 1:
					sir_MIPS_a_lot.getInstance().print_int(args.head);
					break;
				case 4:
					sir_MIPS_a_lot.getInstance().print_string(args.head);
					break;
				case 10:
					sir_MIPS_a_lot.getInstance().exit();
					break;
			}
			return;
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
		sir_MIPS_a_lot.getInstance().move(dst, IR.getInstance().v0);

	}

	public void printMe() { IR.getInstance().fileNewLine(); IR.getInstance().filePrintln(dst + " = call " + func.name + " " + (args != null ? args : "")); }
}
