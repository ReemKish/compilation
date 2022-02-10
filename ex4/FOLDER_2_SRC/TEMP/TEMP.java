/***********/
/* PACKAGE */
/***********/
package TEMP;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public class TEMP
{
	private final int serial;
	private final boolean serialized;
	private final String name;
	
	public TEMP(int serial)
	{
		this.serialized = true;
		this.name = "$t";
		this.serial = serial;
	}

	public TEMP(String label)
	{
		this.serialized = false;
		this.name = label;
		this.serial = 0;
	}
	
	public int getSerialNumber()
	{
		return serial;
	}

	public String toString() {
		if (this.serialized) {
			return this.name + String.valueOf(serial);
		}
		return this.name;
	}
}
