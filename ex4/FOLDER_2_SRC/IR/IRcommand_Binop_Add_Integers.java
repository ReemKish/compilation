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

public class IRcommand_Binop_Add_Integers extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;
	
	public IRcommand_Binop_Add_Integers(TEMP dst,TEMP t1,TEMP t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		/*TODO: add these overflow checks to all other binary ops*/
		sir_MIPS_a_lot.getInstance().add(dst,t1,t2);
		String over_max_label = "over_max_"+IR.getInstance().getLabelIndex();
		String after_max_label = "after_max_fix_"+IR.getInstance().getLabelIndex();
		String under_min_label = "under_min"+IR.getInstance().getLabelIndex();
		String after_min_label = "after_min_fix_"+IR.getInstance().getLabelIndex();
		// check if the result is over the maximum int value
		sir_MIPS_a_lot.getInstance().ble(dst, IR.getInstance().maxIntTemp, after_max_label);
		// if so, truncate it (label for code readability only)
		sir_MIPS_a_lot.getInstance().label(over_max_label);
		sir_MIPS_a_lot.getInstance().move(dst, IR.getInstance().maxIntTemp);
		// check if the result is under the minimum int value
		sir_MIPS_a_lot.getInstance().label(after_max_label);
		sir_MIPS_a_lot.getInstance().ble(IR.getInstance().minIntTemp, dst, after_min_label);
		// if so, truncate it (label for code readability only)
		sir_MIPS_a_lot.getInstance().label(under_min_label);
		sir_MIPS_a_lot.getInstance().move(dst, IR.getInstance().minIntTemp);
		sir_MIPS_a_lot.getInstance().label(after_min_label);
		// done!
	}
}
