package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_NEW_EXP extends AST_EXP
{
	public AST_TYPE t;
	public AST_EXP e;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_NEW_EXP(AST_TYPE t, AST_EXP e)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== newExp -> new type [[exp]]\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.e = e;
		this.t = t;
	}

	/************************************************/
	/* The printing message for an INT EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST INT EXP */
		/*******************************/
		System.out.format("AST NODE New Exp\n");

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("New Exp")
		);
	}

	public TYPE SemantMe(){
		if(e == null) {
			return t.SemantMe();
		}
		else{
			return new TYPE_ARRAY(t.SemantMe());
		}
	}
}
