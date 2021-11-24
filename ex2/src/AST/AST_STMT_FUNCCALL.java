package AST;

public class AST_STMT_FUNCCALL extends AST_STMT
{
	public String fName;
	public AST_VAR objName;
	public AST_EXP_LIST el;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_FUNCCALL(String fName, AST_VAR objName, AST_EXP_LIST el)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== exp -> [obj.]f(expLst);\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.fName = fName;
		this.objName = objName;
		this.el = el;
	}

	/*************************************************/
	/* The printing message for a binop exp AST node */
	/*************************************************/
	public void PrintMe()
	{

		/*********************************/
		/* CONVERT OP to a printable sOP */
		/*********************************/

		/*************************************/
		/* AST NODE TYPE = AST BINOP EXP */
		/*************************************/
		System.out.print("AST NODE FUNC CALL STMT\n");

		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
		if (fName != null) System.out.format("function %s", fName);
		if (objName != null) objName.PrintMe();
		if (el != null) el.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("FUNCCALL(%s)",fName));

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (el  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,el.SerialNumber);
		if (objName  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,objName.SerialNumber);
	}
}
