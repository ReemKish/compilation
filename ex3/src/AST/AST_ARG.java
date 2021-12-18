package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_ARG extends AST_Node
{

	public String name;
	AST_TYPE type;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_ARG(AST_TYPE type, String name)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== arg -> type %s\n", name);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
		this.type = type;
	}
	public TYPE SemantMe() {return null;}

	/************************************************/
	/* The printing message for an INT EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST INT EXP */
		/*******************************/
		System.out.format("AST ARG %s %s\n", type.type, name);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("ARGUMENT type %s\n",name)
		);
	}
}