package AST;
import TEMP.TEMP;
import TYPES.*;
import SYMBOL_TABLE.*;

public abstract class AST_VAR extends AST_Node
{
    /* TODO: add SemantMe implementation to classes inheriting from AST_VAR */
    public TYPE SemantMe() throws SemanticException { return null; }
}
