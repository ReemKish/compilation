/***********/
/* PACKAGE */
/***********/
package TEMP;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */

import java.util.HashMap;
import java.util.Map;

/*******************/

public class TEMP
{
	private final int serial;
	private final boolean serialized;
	private final String name;
	public static Map<TEMP, Integer> tempToReg = new HashMap<>();  /* TEMP serial number to MIPS register (t0,...,t9) */
	
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
		if(tempToReg.containsKey(this)) {
			return "$t" + tempToReg.get(this);
		} else if (this.serialized) {
			return this.name + String.valueOf(serial);
		}
		return this.name;
	}
}
