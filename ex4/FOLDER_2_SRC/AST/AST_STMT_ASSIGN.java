package AST;
import IR.*;
import TEMP.*;
import TYPES.*;

public class AST_STMT_ASSIGN extends AST_STMT
{
	/***************/
	/*  var := exp */
	/***************/
	public AST_VAR var;
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN(int line, AST_VAR var,AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> var ASSIGN exp SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.line = ++line;
		this.var = var;
		this.exp = exp;
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE ASSIGN STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (exp != null) exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ASSIGN\nleft := right\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}

	public TYPE SemantMe() throws SemanticException
	{
		TYPE v;

		/****************************/
		/* [1] Check that the variable exists */
		/****************************/
		v = var.SemantMe();
		if (v == null)
		{
			System.out.format(">> ERROR [%d:%d] non existent identifier\n",2,2);
			throw new SemanticException(this.line);
		}

		/**************************************/
		/* [2] Check that expression type matches var type */
		/**************************************/
		TYPE ex = exp.SemantMe();
		if (!ex.isInstanceOf(v))
		{
			System.out.format(">> ERROR [%d:%d] illegal type cast from %s to %s\n", 2, 2, ex.name, v.name);
			throw new SemanticException(this.line);
		}

		/*********************************************************/
		/* [4] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;
	}
	public TEMP IRme()
	{
		TEMP dst, offset;
		TEMP src = exp.IRme();
		if (var instanceof AST_VAR_SIMPLE) {
			AST_VAR_SIMPLE var_simple = (AST_VAR_SIMPLE) var;
			IR.getInstance().Add_IRcommand(new IRcommand_Store_Var(var_simple.name,src));
		} else if (var instanceof AST_VAR_FIELD) {
			AST_VAR_FIELD var_field = (AST_VAR_FIELD) var;
			AST_VAR_SIMPLE var_simple = (AST_VAR_SIMPLE) var_field.var;
			dst = var_simple.IRme();
			IR.getInstance().Add_IRcommand(new IRcommand_Field_Set(dst, var_field.fieldName, src));


		} else if (var instanceof AST_VAR_SUBSCRIPT) {
			AST_VAR_SUBSCRIPT var_subscript = (AST_VAR_SUBSCRIPT) var;
			AST_VAR_SIMPLE var_simple = (AST_VAR_SIMPLE) var_subscript.var;
			AST_EXP var_offset = (AST_EXP) var_subscript.subscript;
			dst = var_simple.IRme();
			offset = var_offset.IRme();
			IR.getInstance().Add_IRcommand(new IRcommand_Array_Set(dst, offset, src));

		}
		// TODO: store semantic annotations in class, and in particular store the offset
		// pass the offset to IRcommand_Store instead of 0



		return null;
	}
}
