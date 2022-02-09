package TYPES;

public class TYPE_FOR_SCOPE_BOUNDARIES extends TYPE
{
	/****************/
	/* CTROR(S) ... */
	/****************/
	private int arg_offset = 0;
	private int var_offset = 0;
	private int field_offset = 0;
	public TYPE_FOR_SCOPE_BOUNDARIES(String name)
	{
		this.name = name;
	}
	public int getArgOffset(){
		arg_offset -=1;
		return arg_offset;
	}
	public int getVarOffset(){
		var_offset +=1;
		return var_offset;
	}
	public int getFieldOffset(){
		field_offset +=1;
		return field_offset;
	}
}
