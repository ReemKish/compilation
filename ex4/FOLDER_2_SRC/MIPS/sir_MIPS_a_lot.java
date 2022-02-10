/***********/
/* PACKAGE */
/***********/
package MIPS;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.io.PrintWriter;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import IR.IR;
import TEMP.*;

public class sir_MIPS_a_lot
{
	public static final int WORD_SIZE=4;
	/***********************/
	/* The file writer ... */
	/***********************/
	private PrintWriter fileWriter;

	/***********************/
	/* The file writer ... */
	/***********************/
	public void finalizeFile()
	{
		fileWriter.format("%s:\n", IR.endProgLabel);
		fileWriter.print("\tsyscall\n");
		fileWriter.close();
	}
	/* TODO: there shouldn't be a dedicated print_int function.
	*   instead, we should have a generic syscall function that
	*   receives the syscall type as an argument*/
	public void print_int(TEMP t)
	{
		int idx=t.getSerialNumber();
		// fileWriter.format("\taddi $a0,%s,0\n",idx);
		fileWriter.format("\tmove $a0,%s\n",t.toString());
		fileWriter.format("\tli $v0,1\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tli $a0,32\n");
		fileWriter.format("\tli $v0,11\n");
		fileWriter.format("\tsyscall\n");
	}
	public void syscall(){
		fileWriter.format("\tsyscall\n");
	}
	//public TEMP addressLocalVar(int serialLocalVarNum)
	//{
	//	TEMP t  = TEMP_FACTORY.getInstance().getFreshTEMP();
	//	int idx = t.getSerialNumber();
	//
	//	fileWriter.format("\taddi %s,$fp,%d\n",idx,-serialLocalVarNum*WORD_SIZE);
	//	
	//	return t;
	//}
	public void storeGlobalVariable(TEMP label, String word){
		fileWriter.format("\t%s: .word %s\n", label, word);
	}
	public void storeString(TEMP label, String str){
		fileWriter.format("\t%s: .asciiz %s\n", label, str);
	}
	public void allocate(String var_name)
	{
		fileWriter.format(".data\n");
		fileWriter.format("\tglobal_%s: .word 721\n",var_name);
	}
	public void load(TEMP dst, TEMP src, int offset)
	{
		fileWriter.format("\tlw %s, %d(%s)\n", dst.toString(), offset, src.toString());
	}
	public void store(TEMP src, TEMP dst, int offset)
	{
		fileWriter.format("\tsw %s, %d(%s)\n", src.toString(), offset*WORD_SIZE, dst.toString());
	}
	public void li(TEMP t,int value)
	{
		fileWriter.format("\tli %s,%d\n",t.toString(),value);
	}
	public void la(TEMP dst, TEMP src)
	{
		fileWriter.format("\tla %s,0(%s)\n",dst.toString(),src.toString());
	}
	public void move(TEMP dst, TEMP src)
	{
		fileWriter.format("\tmove %s, %s\n",dst.toString(),src.toString());
	}
	public void lb(TEMP dst,TEMP src)
	{
		fileWriter.format("\tlb %s,0(%s)\n",dst.toString(),src.toString());
	}
	public void add(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		fileWriter.format("\tadd %s,%s,%s\n",dst.toString(),oprnd1.toString(),oprnd2.toString());
	}
	public void sub(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		fileWriter.format("\tadd %s,%s,%s\n",dst.toString(),oprnd1.toString(),oprnd2.toString());
	}
	public void mul(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		fileWriter.format("\tmul %s,%s,%s\n",dst.toString(),oprnd1.toString(),oprnd2.toString());
	}
	public void div(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		fileWriter.format("\tdiv %s,%s,%s\n",dst.toString(),oprnd1.toString(),oprnd2.toString());
	}

	public void addi(TEMP dst,TEMP oprnd1,int value)
	{
		fileWriter.format("\taddi %s, %s, %d\n",dst.toString(),oprnd1.toString(),value);
	}

	public void label(String inlabel)
	{
		if (inlabel.equals("main"))
		{
			fileWriter.format(".text\n");
		}
		fileWriter.format("%s:\n",inlabel);
	}	
	public void jump(String inlabel)
	{
		fileWriter.format("\tj %s\n",inlabel);
	}
	public void jal(String inlabel)
	{
		fileWriter.format("\tjal %s\n",inlabel);
	}
	public void blt(TEMP oprnd1,TEMP oprnd2,String label)
	{
		fileWriter.format("\tblt %s,%s,%s\n",oprnd1.toString(),oprnd2.toString(),label);
	}
	public void bltz(TEMP oprnd1,String label)
	{
		fileWriter.format("\tbltz %s, %s\n",oprnd1.toString(),label);
	}
	public void bge(TEMP oprnd1,TEMP oprnd2,String label)
	{
		fileWriter.format("\tbge %s,%s,%s\n",oprnd1.toString(),oprnd2.toString(),label);
	}
	public void bne(TEMP oprnd1,TEMP oprnd2,String label)
	{
		fileWriter.format("\tbne %s,%s,%s\n",oprnd1.toString(),oprnd2.toString(),label);
	}
	public void beq(TEMP oprnd1,TEMP oprnd2,String label)
	{
		fileWriter.format("\tbeq %s,%s,%s\n",oprnd1.toString(),oprnd2.toString(),label);
	}
	public void ble(TEMP oprnd1, TEMP oprnd2, String label)
	{
		fileWriter.format("\tble %s,%s,%s\n",oprnd1.toString(),oprnd2.toString(),label);
	}
	public void beqz(TEMP oprnd1,String label)
	{
		fileWriter.format("\tbeq %s,$zero,%s\n",oprnd1.toString(),label);
	}
	public void ret(TEMP res)
	{
		// epilogue
		fileWriter.format("\tmove $v0 %s\n", res);
		fileWriter.format("\tmove $sp, $fp\n");
		fileWriter.format("\tlw $fp, 0($sp)\n");
		fileWriter.format("\tlw $ra, 4($sp)\n");
		fileWriter.format("\taddi $sp, $sp, 8\n");
		fileWriter.format("\tjr $ra\n");
	}


	
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static sir_MIPS_a_lot instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected sir_MIPS_a_lot() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static sir_MIPS_a_lot getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new sir_MIPS_a_lot();

			try
			{
				/*********************************************************************************/
				/* [1] Open the MIPS text file and write data section with error message strings */
				/*********************************************************************************/
				String dirname="./FOLDER_5_OUTPUT/";
				String filename=String.format("MIPS.txt");

				/***************************************/
				/* [2] Open MIPS text file for writing */
				/***************************************/
				instance.fileWriter = new PrintWriter(dirname+filename);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			/*****************************************************/
			/* [3] Print data section with error message strings */
			/*****************************************************/
			instance.fileWriter.print(".data\n");
			instance.fileWriter.print("string_access_violation: .asciiz \"Access Violation\"\n");
			instance.fileWriter.print("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"\n");
			instance.fileWriter.print("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"\n");
		}
		return instance;
	}
}
