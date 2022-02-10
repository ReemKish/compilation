/***********/
/* PACKAGE */
/***********/
package IR;
import MIPS.sir_MIPS_a_lot;
import TEMP.*;

import java.io.PrintWriter;
/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public class IR
{
	public IRcommand head=null;
	public IRcommandList tail=null;
	private IRdata dataHead=null;
	private IRdataList dataTail=null;
	private int labelCounter = 0;
	private static final int MAX_INT = 32767;
	private static final int MIN_INT = -32768;
	public TEMP maxIntTemp;
	public TEMP minIntTemp;
	public TEMP wordSizeTemp;
	public TEMP sp;
	public TEMP fp;
	public TEMP ra;
	public TEMP v0;
	public TEMP v1;
	public TEMP a0;
	public TEMP a1;
	public TEMP s0;
	public TEMP s1;
	public static final String funcLabelPrefix = "FUNC_LABEL_";
	public static final String endProgLabel = "END_PROGRAM";
	public static final String  globalVarPrefix = "GLOBAL_VAR_";
	protected static PrintWriter fileWriter;
	private static int line_index=1;

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

	public void finalizeFile()
	{
		fileWriter.format("%s:\n", endProgLabel);
		fileWriter.print("\tsyscall\n");
		fileWriter.close();
	}

	public void fileNewLine() {fileNewLine(true); fileWriter.print("\t"); }
	public void fileNewLine(boolean notab) { if(notab) {System.out.print(line_index++ + ".");} else fileNewLine(); }
	public void filePrintln(String line) { fileWriter.println(line); }

	/******************************************/
	/* Generate the register allocation table */
	/******************************************/
	public void genRegAlloc() {
		IR_Graph graph = new IR_Graph();
	}

	/******************/
	/* Add IR command */
	/******************/
	public void Add_IRdata(IRdata data)
	{
		if ((dataHead == null) && (dataTail == null))
		{
			this.dataHead = data;
		}
		else if ((dataHead != null) && (dataTail == null))
		{
			this.dataTail = new IRdataList(data,null);
		}
		else
		{
			IRdataList it = dataTail;
			while ((it != null) && (it.tail != null))
			{
				it = it.tail;
			}
			it.tail = new IRdataList(data,null);
		}
	}

	/*
	* Returns the first command after a label
	*/
	public IRcommand findCmdAtLabel(String label) {
		if ((head == null) && (tail == null))
		{
			return null;
		}
		else if ((head != null) && (tail == null))
		{
			if (head instanceof IRcommand_Label) {
				if (((IRcommand_Label) head).label_name.equals(label)) {
					return  head;
				}
			}
		}
		else
		{
			IRcommand curr = head;
			IRcommandList next = tail;
			while ((curr != null) && (next != null))
			{
				if (curr instanceof IRcommand_Label) {
					if (((IRcommand_Label) curr).label_name.equals(label)) {
						return curr;
					}
				}
				curr = next.head;
				next = next.tail;
			}
		}
		return null;
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
		if (dataHead != null) dataHead.MIPSme();
		if (dataTail != null) dataTail.MIPSme();
		new IRcommand_Jump_Label(funcLabelPrefix + "main").MIPSme();
		if (head != null) head.MIPSme();
		if (tail != null) tail.MIPSme();
	}
	public void printMe(){
		if (dataHead != null) dataHead.printMe();
		if (dataTail != null) dataTail.printMe();
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
			instance.maxIntTemp = TEMP_FACTORY.getInstance().getFreshNamedTEMP("CONST_MAX_INT");
			instance.minIntTemp = TEMP_FACTORY.getInstance().getFreshNamedTEMP("CONST_MIN_INT");
			instance.wordSizeTemp = TEMP_FACTORY.getInstance().getFreshNamedTEMP("CONST_WORD_SIZE");
			instance.Add_IRdata(new IRdata_Global_Var(instance.maxIntTemp, "" + MAX_INT));
			instance.Add_IRdata(new IRdata_Global_Var(instance.minIntTemp, "" + MIN_INT));
			instance.Add_IRdata(new IRdata_Global_Var(instance.wordSizeTemp, "" + sir_MIPS_a_lot.WORD_SIZE));
			instance.sp = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$sp");
			instance.fp = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$fp");
			instance.ra = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$ra");
			instance.v0 = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$v0");
			instance.v1 = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$v1");
			instance.a0 = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$a0");
			instance.a1 = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$a1");
			instance.s0 = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$s0");
			instance.s1 = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$s1");
			String dirname="./FOLDER_5_OUTPUT/";
			String filename=String.format("IR.txt");
			try { instance.fileWriter = new PrintWriter(dirname+filename); }
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		return instance;
	}
}
