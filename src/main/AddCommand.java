import java.io.IOException;

public class AddCommand extends Command {
    private Task task;
    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws Exception_handler, IOException {
        taskList.addTask(task);
        ui.println("Got it. I've added this task: ");
        ui.println(task);
        ui.println("Now you have " + taskList.getSize() + " tasks in the List");
        storage.writeToFile(taskList.getListOfTask());
    }

    public boolean isExit() {
        return false;
    }
}
