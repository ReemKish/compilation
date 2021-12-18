package AST;
import TYPES.*;

import java.util.Objects;

public class AST_EXP_BINOP extends AST_EXP
{
	int OP;
	String sOP;
	public AST_EXP left;
	public AST_EXP right;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_BINOP(AST_EXP left,AST_EXP right,int OP)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== exp -> exp BINOP exp\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.left = left;
		this.right = right;
		this.OP = OP;

		/*********************************/
		/* CONVERT OP to a printable sOP */
		/*********************************/
		if (OP == 0) {sOP = "+";}
		if (OP == 1) {sOP = "-";}
		if (OP == 2) {sOP = "*";}
		if (OP == 3) {sOP = "/";}
		if (OP == 4) {sOP = "<";}
		if (OP == 5) {sOP = ">";}
		if (OP == 6) {sOP = "=";}

	}
	
	/*************************************************/
	/* The printing message for a binop exp AST node */
	/*************************************************/
	public void PrintMe()
	{
		
		/*************************************/
		/* AST NODE TYPE = AST BINOP EXP */
		/*************************************/
		System.out.print("AST NODE BINOP EXP\n");

		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
		if (left != null) left.PrintMe();
		if (right != null) right.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("BINOP(%s)",sOP));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (left  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,left.SerialNumber);
		if (right != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,right.SerialNumber);
	}
	public TYPE SemantMe() {
		TYPE left_type = left.SemantMe();
		TYPE right_type = right.SemantMe();
		/****************************************/
		/* Type check for equality testing */
		/****************************************/
		if(Objects.equals(sOP, "=")){
			if(!left_type.isInstanceOf(right_type)){
				System.out.format(">> ERROR [%d:%d] cannot perform operation on types %s %s %s\n",2,2,left_type.name, sOP, right_type.name);
				System.exit(0);
			}
		}
		/****************************************/
		/* Type check for addition/concatenation */
		/****************************************/
		else if(Objects.equals(sOP, "+")){
			if(
					!(left_type.isInstanceOf(TYPE_INT.getInstance()) && right_type.isInstanceOf(TYPE_INT.getInstance()))
					|| !(left_type.isInstanceOf(TYPE_STRING.getInstance()) && right_type.isInstanceOf(TYPE_STRING.getInstance()))
			){
				System.out.format(">> ERROR [%d:%d] cannot perform operation on types %s %s %s\n",2,2,left_type.name, sOP, right_type.name);
				System.exit(0);
			}
		}
		/****************************************/
		/* Type check for all other BINOPs */
		/****************************************/
		else{

			if(left_type == null){
				System.out.print("");
			}
			if(
					!(left_type.isInstanceOf(TYPE_INT.getInstance()) && right_type.isInstanceOf(TYPE_INT.getInstance()))
			){
				System.out.format(">> ERROR [%d:%d] cannot perform operation on types %s %s %s\n",2,2,left_type.name, sOP, right_type.name);
				System.exit(0);
			}
		}
		/****************************************/
		/* Division by 0 check */
		/****************************************/
		if(Objects.equals(sOP, "/")){
			if(right instanceof AST_EXP_INT){
				if(((AST_EXP_INT) right).value == 0){
					System.out.format(">> ERROR [%d:%d] division by 0\n",2,2);
					System.exit(0);
				}
			}
		}
		return TYPE_INT.getInstance();
	}
}
