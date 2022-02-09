package TYPES;

public class TYPE_FUNCTION extends TYPE
{
	/***********************************/
	/* The return type of the function */
	/***********************************/
	public TYPE returnType;

	/*************************/
	/* types of input params */
	/*************************/
	public TYPE_LIST params;
	public int isSysCall = 0;
	public int sysCallNum = 0;
	public int localVarCount = 0;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_FUNCTION(TYPE returnType,String name,TYPE_LIST params)
	{
		this.name = name;
		this.returnType = returnType;
		this.params = params;
	}
	public TYPE_FUNCTION(TYPE returnType, String name, int sysCallNum, TYPE_LIST params)
	{
		this.name = name;
		this.returnType = returnType;
		this.params = params;
		this.isSysCall = 1;
		this.sysCallNum = sysCallNum;
	}
}
