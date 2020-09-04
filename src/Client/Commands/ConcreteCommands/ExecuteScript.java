package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;

/**
 * ���������� ������� ���������� �������.
 */
public class ExecuteScript extends ACommand {
    private final CommandReceiver commandReceiver;
    private String path;

    public ExecuteScript(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    protected void execute(String[] args) throws StackOverflowError {
        try {
            if (args.length == 2) { path = args[1]; commandReceiver.executeScript(args[1]); }
            else { System.out.println("������������ ���������� ����������. ��� ������� �������� help."); }
        } catch (StackOverflowError ex) {
            System.out.println("������! ��������� ����� �� ������� �����.");
        }
    }

    @Override
    protected String writeInfo() {
        return "������� execute_script - execute_script file_name � ������� � ��������� ������ �� ���������� �����. ";
    }

    public String getPath() {
        return path;
    }
}