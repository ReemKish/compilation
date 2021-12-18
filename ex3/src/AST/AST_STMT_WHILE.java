package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

import java.util.Objects;

public class AST_STMT_WHILE extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_WHILE(AST_EXP cond,AST_STMT_LIST body)
	{
		this.cond = cond;
		this.body = body;
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> WHILE(exp){stmtList}\n");
	}
	/*********************************************************/
	/* Printed Text */
	/*********************************************************/
	public void PrintMe()
	{
		System.out.print("WHILE STATEMENT\n");
		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
		if (cond != null) cond.PrintMe();
		if (body != null) body.PrintMe();
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("WHILE\ncond {body}"));
		if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cond.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
	}
	public TYPE SemantMe() {
		TYPE condType = cond.SemantMe();
		if(!Objects.equals(condType.name, TYPE_INT.getInstance().name)){
			System.out.format(">> ERROR [%d:%d] invalid condition\n",2,2);
			System.exit(0);
		}
	        /***************/
		/* begin scope */
		/***************/
		SYMBOL_TABLE.getInstance().beginScope();
		TYPE bodyType = body.SemantMe();
	        /***************/
		/* end scope */
		/***************/
		SYMBOL_TABLE.getInstance().endScope();
		return null;
	}
}
