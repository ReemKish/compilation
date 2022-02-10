package AST;
import IR.IR;
import IR.IRcommand_Label;
import TEMP.TEMP;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_PROGRAM extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_DEC head;
	public AST_PROGRAM tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_PROGRAM(int line, AST_DEC head, AST_PROGRAM tail)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (tail != null) System.out.print("====================== program -> dec program\n");
		if (tail == null) System.out.print("====================== program -> dec      \n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.line = ++line;
		this.head = head;
		this.tail = tail;
	}

	public TYPE SemantMe() throws SemanticException
	{
		if (head != null) head.SemantMe();
		if (tail != null) tail.SemantMe();

		return null;
	}

	public TEMP IRme()
	{
		if (head != null) head.IRme();
		if (tail != null) tail.IRme();
		IR.getInstance().Add_IRcommand(new IRcommand_Label(IR.endProgLabel));

		return null;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE PROGRAM\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (head != null) {
			head.PrintMe();
		}
		if (tail != null) tail.PrintMe();
		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"PROGRAM\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
	
}
