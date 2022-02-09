/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public abstract class IRcommand
{
	/*****************/
	/* Label Factory */
	/*****************/
	private static int line_index=1;
	protected static int label_counter=0;
	public    static String getFreshLabel(String msg)
	{
		return String.format("Label_%d_%s",label_counter++,msg);
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public abstract void MIPSme();

	public void printMe() { System.out.println(this.getClass().getSimpleName()); }

	public void printLine() {System.out.print(line_index++ + ".\t"); }
	public void printLine(boolean notab) { if(notab) {System.out.print(line_index++ + ".");} else printLine(); }
}
