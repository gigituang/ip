package duke;//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.Scanner;
//import java.util.List;
//import java.util.ArrayList;
//import java.io.File;
//import java.io.FileNotFoundException;
import java.io.IOException;
//import java.io.PrintWriter;
//import java.time.LocalDateTime;
//
//public class duke.Duke {
//    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
//    public static void main(String[] args) {
//        String logo = " ____        _        \n"
//                + "|  _ \\ _   _| | _____ \n"
//                + "| | | | | | | |/ / _ \\\n"
//                + "| |_| | |_| |   <  __/\n"
//                + "|____/ \\__,_|_|\\_\\___|\n";
//        System.out.println("Hello from\n" + logo);
//        System.out.println("Hello! i'm duke.Duke");
//        System.out.println("What can I do for you?");
//        Scanner sc = new Scanner(System.in);
//
//        while (sc.hasNext()) {
//            try {
//                List<duke.Task> taskList = importTask();
//                String[] s = sc.nextLine().split(" ");
//                if (s[0].equals("bye")) {
//                    if (s.length > 1) {
//                        throw new duke.Exception_handler("I'm sorry, but I don't know what that means :-(");
//                    }
//                    System.out.println("Bye. Hope to see you again soon!");
//                    break;
//                } else if (s[0].equals("list")) {
//                    int count = 1;
//                    for (duke.Task sample : taskList) {
//                        System.out.println(count + ". " + sample);
//                        count++;
//                    }
//                } else if (s[0].equals("mark")) {
//                    if (s.length < 2) {
//                        throw new duke.Exception_handler("☹ OOPS!!! That task doesn't exist or you failed to include a number.");
//                    }
//                    int index = Integer.parseInt(s[1]) - 1;
//                    if (index < 0 || index > taskList.size() - 1) {
//                        throw new duke.Exception_handler("☹ OOPS!!! That task doesn't exist or you failed to include a number.");
//                    }
//                    taskList.get(index).setDone();
//                    System.out.println("Nice! I've marked this task as done:");
//                    System.out.print(taskList.get(index));
//                    writeToFile(taskList);
//                } else if (s[0].equals("todo")) {
//                    if (s.length < 2) {
//                        throw new duke.Exception_handler("☹ OOPS!!! You're missing some descriptions for your event.");
//                    }
//                    String str = "";
//                    for (int i = 1; i < s.length; i++) {
//                        str = str + s[i] + " ";
//                    }
//                    duke.ToDos task = new duke.ToDos(str);
//                    taskList.add(task);
//                    System.out.println("Got it, I've added this task: ");
//                    System.out.println(task);
//                    System.out.println("Now you have " + taskList.size() + " tasks in the list.");
//                    writeToFile(taskList);
//                } else if (s[0].equals("event")) {
//                    if (s.length < 2) {
//                        throw new duke.Exception_handler("☹ OOPS!!! The description cof a todo cannot be empty.");
//                    }
//                    String str = "";
//                    String time = "";
//                    int index = 0;
//                    for (int i = 1; i < s.length; i++) {
//                        if (s[i].equals("/at")) {
//                            index = i + 1;
//                            break;
//                        } else {
//                            str = str + s[i] + " ";
//                        }
//                    }
//                    for (int k = index; k < s.length; k++) {
//                        time = time + s[k] + " ";
//                    }
//                    duke.Event event = new duke.Event(str, time);
//                    taskList.add(event);
//                    System.out.println("Got it. I've added this task:");
//                    System.out.println(event);
//                    System.out.println("Now you have " + taskList.size() + " tasks in the list");
//                    writeToFile(taskList);
//                } else if (s[0].equals("deadline")) {
//                    if (s.length < 2) {
//                        throw new duke.Exception_handler("☹ OOPS!!! You're missing some descriptions for your deadline.");
//                    }
//                    String time = "";
//                    String str = "";
//                    int index = 0;
//
//                    for (int i = 1; i < s.length; i++) {
//                        if (s[i].equals("/by")) {
//                            index = i + 1;
//                            break;
//                        } else {
//                            str = str + s[i] + " ";
//                        }
//                    }
//
//                    for (int k = index; k < s.length; k++) {
//                        time = time + s[k] + " ";
//                    }
//                    LocalDateTime dateTime = LocalDateTime.parse(time.trim(), dtf);
//                    duke.Deadline deadline = new duke.Deadline(str, dateTime);
//                    taskList.add(deadline);
//                    System.out.println("Got it. I've added this task: ");
//                    System.out.println(deadline);
//                    System.out.println("Now you have " + taskList.size() + " tasks in the list. ");
//                    writeToFile(taskList);
//                } else if (s[0].equals("delete")) {
//                    if (s.length < 2) {
//                        throw new duke.Exception_handler("Please enter an index");
//                    }
//                    int index = Integer.parseInt(s[1]) - 1;
//                    if (index < 0 || index >= taskList.size()) {
//                        throw new duke.Exception_handler("Index out of range or invalid");
//                    }
//                    duke.Task removed = taskList.remove(index);
//                    System.out.println("Noted. I've removed this task: ");
//                    System.out.println(removed);
//                    System.out.println("Now you have " + taskList.size() + " tasks in the list. ");
//                    writeToFile(taskList);
//                } else {
//                    throw new duke.Exception_handler("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
//                }
//            } catch (duke.Exception_handler e) {
//                System.out.println(e);
//            } catch (IOException ie) {
//                System.out.println("IOException");
//            } catch (DateTimeParseException dtpe) {
//                System.out.println("Please enter deadline in dd/mm/yyyy HHmm format");
//            }
//        }
//    }
public class Duke {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    /**
     * constructor for Duke
     * @param filePath path to source file
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList(storage.load());
    }

    /**
     * method to run the program
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine(); // show the divider line ("_______")
                Command c = Parser.parse(fullCommand);
                c.execute(taskList, ui, storage);
                isExit = c.isExit();
            } catch (Exception_handler | IOException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }


    public static void main(String[] args) {
        new Duke("/Users/gigi/Desktop/CS2103T/duke.txt").run();
    }
}

//import java.io.IOException;
//
//public class duke.Duke {
//    private duke.Storage storage;
//    private duke.TaskList tasks;
//    private duke.Ui ui;
//
//    public duke.Duke(String filePath) {
//        ui = new duke.Ui();
//        storage = new duke.Storage(filePath);
//        tasks = new duke.TaskList(storage.load());
//    }
//    public void run() {
//        ui.showWelcome();
//        boolean isExit = false;
//        while (!isExit) {
//            try {
//                String fullCommand = ui.readCommand()'
//                ui.showLine();
//                duke.Command c = duke.Parser.parse(fullCommand);
//                c.execute(tasks,ui,storage);
//                isExit = c.isExit();
//            } catch (duke.Exception_handler | IOException) {
//                ui.showError(e.getMessage);
//            } finally {
//                ui.showLine();
//            }
//        }
//    }
//    public static
//
//        public static List<duke.Task> importTask() throws FileNotFoundException {
//            List<duke.Task> tempList = new ArrayList<>();
//            File temp = new File("/Users/gigi/Desktop/CS2103T/duke.txt");
//            try {
//                Scanner fileScanner = new Scanner(temp);
//                while (fileScanner.hasNextLine()) {
//                    String[] token = fileScanner.nextLine().split("\\|");
//                    if (token.length <= 2) {
//                        throw new duke.Exception_handler("Invalid line");
//                    }
//                    if(token[0].equals("T")){
//                        tempList.add(new duke.ToDos(token[2], token[1]));
//                    } else if(token[0].equals("D")) {
//                        tempList.add(new duke.Deadline(token[2], token[1], LocalDateTime.parse(token[3],dtf)));
//                    } else if(token[0].equals("E")){
//                        tempList.add(new duke.Event(token[2], token[1], token[3]));
//                    } else{
//                        throw new duke.Exception_handler("Invalid task type");
//                    }
//                }
//                return tempList;
//            }
//            catch(duke.Exception_handler de){
//                System.out.println(de.getMessage());
//            }
//            catch(FileNotFoundException e){
//                System.out.println("File not found");
//            }
//            return tempList;
//        }
//        public static void writeToFile(List<duke.Task> tasks) throws IOException, FileNotFoundException {
//            File file = new File("/Users/gigi/Desktop/CS2103T/duke.txt");
//            PrintWriter pw = new PrintWriter(file);
//            String output = "";
//            for (duke.Task t : tasks) {
//                if (t.getType().equals("T")) {
//                    output += t.getType() + "|" + t.getIsDone() + "|" + t.getDescription() + "\n";
//                } else if (t.getType().equals("E")) {
//                    output += t.getType() + "|" + t.getIsDone() + "|" + t.getDescription() + "|"
//                            + ((duke.Event) t).getPlace() + "\n";
//                } else if (t.getType().equals("D")) {
//                    output += t.getType() + "|" + t.getIsDone() + "|" + t.getDescription() + "|"
//                            + ((duke.Deadline) t).getDue() + "\n";
//                }
//            }
//            pw.write(output);
//            pw.close();
//        }
//    }