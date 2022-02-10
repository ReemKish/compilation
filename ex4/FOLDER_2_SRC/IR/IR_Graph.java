package IR;
import java.util.*;
public class IR_Graph {
    private Map<IRcommand, List<IRcommand>> graph;
    public  IR_Graph() {
        graph = new HashMap<IRcommand, List<IRcommand>>();
        IR ir = IR.getInstance();
        IRcommand head = ir.head;
        IRcommandList tail = ir.tail;

        while ((head != null) && (tail != null))
        {
            addCmd(head, tail.head);
            head = tail.head;
            tail = tail.tail;
        }
    }
    private void addCmd(IRcommand cmd, IRcommand next) {
        LinkedList<IRcommand> nextCmds = new LinkedList<IRcommand>();
        if(cmd instanceof IRcommand_Func_Call) {
            nextCmds.add(IR.getInstance().findCmdAtLabel("FUNC_LABEL_" + ((IRcommand_Func_Call) cmd).func.name));
        } else if(cmd instanceof IRcommand_Jump_If_Eq_To_Zero) {
            nextCmds.add(IR.getInstance().findCmdAtLabel(((IRcommand_Jump_If_Eq_To_Zero) cmd).label_name));
            nextCmds.add(next);
        } else if(cmd instanceof IRcommand_Jump_Label) {
            nextCmds.add(IR.getInstance().findCmdAtLabel(((IRcommand_Jump_Label) cmd).label_name));
        } else if(cmd instanceof IRcommand_Label) {
            return;
        }else {
            nextCmds.add(next);
        }

        graph.put(cmd, nextCmds);
    }

    public void PrintMe() {
        System.out.println("Graph:");
        for (Map.Entry<IRcommand,List<IRcommand>> entry : graph.entrySet())
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
    }


}
