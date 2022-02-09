/***********/
/* PACKAGE */
/***********/
package IR;
import MIPS.sir_MIPS_a_lot;
import TEMP.*;
/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public class IR
{
	private IRcommand head=null;
	private IRcommandList tail=null;
	private int labelCounter = 0;
	private static final int MAX_INT = 32767;
	private static final int MIN_INT = -32768;
	public TEMP maxIntTemp;
	public TEMP minIntTemp;
	public TEMP sp;
	public TEMP fp;
	public TEMP ra;
	public TEMP v0;
	public TEMP v1;
	public static String funcLabelPrefix = "FUNC_LABEL";

	/******************/
	/* Add IR command */
	/******************/
	public void Add_IRcommand(IRcommand cmd)
	{
		if ((head == null) && (tail == null))
		{
			this.head = cmd;
		}
		else if ((head != null) && (tail == null))
		{
			this.tail = new IRcommandList(cmd,null);
		}
		else
		{
			IRcommandList it = tail;
			while ((it != null) && (it.tail != null))
			{
				it = it.tail;
			}
			it.tail = new IRcommandList(cmd,null);
		}
	}

	public void truncate_int(TEMP dst){
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
		// done!;
	}

	public int getLabelIndex(){
		labelCounter++;
		return labelCounter;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		if (head != null) head.MIPSme();
		if (tail != null) tail.MIPSme();
	}
	public void printMe(){
		if (head != null) head.printMe();
		if (tail != null) tail.printMe();
	}

	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static IR instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected IR() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static IR getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new IR();
			instance.maxIntTemp = TEMP_FACTORY.getInstance().getFreshTEMP();
			instance.minIntTemp = TEMP_FACTORY.getInstance().getFreshTEMP();
			instance.Add_IRcommand(new IRcommandConstInt(instance.maxIntTemp, MAX_INT));
			instance.Add_IRcommand(new IRcommandConstInt(instance.minIntTemp, MIN_INT));

			instance.maxIntTemp = TEMP_FACTORY.getInstance().getFreshTEMP();
			instance.minIntTemp = TEMP_FACTORY.getInstance().getFreshTEMP();
			instance.sp = TEMP_FACTORY.getInstance().getFreshTEMP();
			instance.fp = TEMP_FACTORY.getInstance().getFreshTEMP();
			instance.ra = TEMP_FACTORY.getInstance().getFreshTEMP();
			instance.v0 = TEMP_FACTORY.getInstance().getFreshTEMP();
			instance.v1 = TEMP_FACTORY.getInstance().getFreshTEMP();

		}
		return instance;
	}
}
