/***********/
/* PACKAGE */
/***********/
package IR;
import MIPS.sir_MIPS_a_lot;
import TEMP.*;

import java.io.PrintWriter;
import java.util.Objects;
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
	public TEMP accessViolation;
	public TEMP zeroDiv;
	public TEMP invalidPtr = TEMP_FACTORY.getInstance().getFreshNamedTEMP("CONST_WORD_SIZE");
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
	public static final String  strPrefix = "DATA_STR_";
	public static final String  exitOnAccessViolation = "EXIT_ACCESS_VIOLATION";
	public static final String  exitOnZeroDiv = "EXIT_ZERO_DIV";
	public static final String  exitOnInvalidPointer = "EXIT_INVALID_POINTER";
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
		graph.PrintMe();
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
		IRcommand curr = head;
		IRcommandList next = tail;
		while ((curr != null) && (next != null))
		{
			if (curr instanceof IRcommand_Label) {
				if (((IRcommand_Label) curr).label_name.equals(label)) {
					return next.head;
				}
			}
			curr = next.head;
			next = next.tail;
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
		sir_MIPS_a_lot.getInstance().dataSection();
		if (dataHead != null) dataHead.MIPSme();
		if (dataTail != null) dataTail.MIPSme();
		sir_MIPS_a_lot.getInstance().textSection();
		new IRcommand_Jump_Label("main").MIPSme();
		if (head != null) head.MIPSme();
		if (tail != null) tail.MIPSme();
		new IRcommand_Label("main").MIPSme();
		new IRcommand_Jump_And_Link(funcLabelPrefix + "main").MIPSme();
		// exit gracefully
		new IRcommand_Label(endProgLabel).MIPSme();
		new IRcommand_Exit().MIPSme();
		// exit on access violation
		new IRcommand_Label(exitOnAccessViolation).MIPSme();
		new IRcommand_Print_String(IR.getInstance().accessViolation).MIPSme();
		new IRcommand_Exit().MIPSme();
		// exit on zero division
		new IRcommand_Label(exitOnZeroDiv).MIPSme();
		new IRcommand_Print_String(IR.getInstance().zeroDiv).MIPSme();
		new IRcommand_Exit().MIPSme();
		// exit on invalid pointer
		new IRcommand_Label(exitOnInvalidPointer).MIPSme();
		new IRcommand_Print_String(IR.getInstance().invalidPtr).MIPSme();
		new IRcommand_Exit().MIPSme();
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
			// number constants
			instance.maxIntTemp = TEMP_FACTORY.getInstance().getFreshNamedTEMP("CONST_MAX_INT");
			instance.minIntTemp = TEMP_FACTORY.getInstance().getFreshNamedTEMP("CONST_MIN_INT");
			instance.wordSizeTemp = TEMP_FACTORY.getInstance().getFreshNamedTEMP("CONST_WORD_SIZE");
			instance.Add_IRdata(new IRdata_Global_Var(instance.maxIntTemp, "" + MAX_INT));
			instance.Add_IRdata(new IRdata_Global_Var(instance.minIntTemp, "" + MIN_INT));
			instance.Add_IRdata(new IRdata_Global_Var(instance.wordSizeTemp, "" + sir_MIPS_a_lot.WORD_SIZE));
			// string constants
			TEMP accessViolationData = TEMP_FACTORY.getInstance().getFreshNamedTEMP(strPrefix + "ERR_ACCESS_VIOLATION");
			TEMP zeroDivData = TEMP_FACTORY.getInstance().getFreshNamedTEMP(strPrefix + "ERR_ZERO_DIV");
			TEMP invalidPtrData = TEMP_FACTORY.getInstance().getFreshNamedTEMP(strPrefix + "ERR_INVALID_POINTER");
			instance.Add_IRdata(new IRdata_Constant_String(accessViolationData, "Access Violation"));
			instance.Add_IRdata(new IRdata_Constant_String(zeroDivData, "Illegal Division By Zero"));
			instance.Add_IRdata(new IRdata_Constant_String(invalidPtrData, "Invalid Pointer Dereference"));
			instance.accessViolation = TEMP_FACTORY.getInstance().getFreshNamedTEMP("ERR_ACCESS_VIOLATION");
			instance.zeroDiv = TEMP_FACTORY.getInstance().getFreshNamedTEMP("ERR_ZERO_DIV");
			instance.invalidPtr = TEMP_FACTORY.getInstance().getFreshNamedTEMP("ERR_INVALID_POINTER");
			IR.getInstance().Add_IRdata(new IRdata_Global_Var(instance.accessViolation, accessViolationData.toString()));
			IR.getInstance().Add_IRdata(new IRdata_Global_Var(instance.zeroDiv, zeroDivData.toString()));
			IR.getInstance().Add_IRdata(new IRdata_Global_Var(instance.invalidPtr, invalidPtrData.toString()));

			// unique registers
			instance.sp = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$sp");
			instance.fp = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$fp");
			instance.ra = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$ra");
			instance.v0 = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$v0");
			instance.v1 = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$v1");
			instance.a0 = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$a0");
			instance.a1 = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$a1");
			instance.s0 = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$s0");
			instance.s1 = TEMP_FACTORY.getInstance().getFreshNamedTEMP("$s1");

			// output setup
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
