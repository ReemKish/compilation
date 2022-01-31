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

public class TEMP_LIST
{
    public TEMP head;
    public TEMP_LIST tail;

    public TEMP_LIST()
    {
        this.head = null;
        this.tail = null;
    }

    public TEMP_LIST(TEMP head, TEMP_LIST tail)
    {
        this.head = head;
        this.tail = tail;
    }
    public void append(TEMP t){
        this.tail = this;
        this.head = t;
    }
}
