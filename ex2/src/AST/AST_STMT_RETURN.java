package AST;

public class AST_STMT_RETURN extends AST_STMT
{
	public AST_EXP val;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_RETURN(AST_EXP val)
	{
		this.val = val;
	}
}