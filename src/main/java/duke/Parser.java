package duke;

import java.text.ParseException;

/**
 * Represents a Parser object in the Duke. A <code>Parser</code> object parses
 * string command given by user and creates new Command object.
 */
public class Parser {

    /**
     * Returns a new Command object.
     * If the command is invalid, Duke Exception is thrown.
     *
     * @param fullCommand  String containing command from user input.
     * @return new Command object.
     * @throws DukeException  If command is invalid.
     */
    public static Command parse(String fullCommand) throws DukeException {
        String[] arr = fullCommand.split(" ", 2);
        String firstWord = arr[0];
        switch (firstWord) {
        case "tag":
            String tagDescription = arr[1];
            if (tagDescription.isEmpty()) {
                throw new DukeException("OOPS!!! The description of a tag cannot be empty.");
            }
            String[] indexTag = tagDescription.split(" ");
            int indexOfTag = Integer.parseInt(indexTag[0]);
            String wordOfTag = indexTag[1];
            if (wordOfTag.isEmpty()) {
                throw new DukeException("OOPS!!! The tag word cannot be empty.");
            }
            return new TagCommand(indexOfTag, wordOfTag);
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "delete":
            int index = Integer.parseInt(arr[1]);
            return new DeleteCommand(index);
        case "done":
            int indexDone = Integer.parseInt(arr[1]);
            return new DoneCommand(indexDone);
        case "todo":
            String what = arr[1];
            if (what.isEmpty()) {
                throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
            }
            Todo todo = new Todo(what);
            return new AddCommand(todo);
        case "deadline":
            String when = arr[1];
            if (when.isEmpty()) {
                throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
            }
            String[] parts = when.split("/by");
            if (parts.length == 1) {
                throw new DukeException("OOPS!!! The time of a deadline cannot be empty.");
            }
            String desc = parts[0];
            String time = parts[1];
            Deadline deadline = new Deadline(desc);
            try {
                deadline.parseTime(time);
            } catch (ParseException ex) {
                throw new DukeException(ex.getMessage());
            }
            return new AddCommand(deadline);
        case "event":
            String where = arr[1];
            if (where.isEmpty()) {
                throw new DukeException("OOPS!!! The description of an event cannot be empty.");
            }
            String[] partsE = where.split("/at");
            if (partsE.length == 1) {
                throw new DukeException("OOPS!!! The time of an event cannot be empty.");
            }
            String descE = partsE[0];
            String timeE = partsE[1];
            Event event = new Event(descE);
            try {
                event.parseTime(timeE);
            } catch (ParseException ex) {
                throw new DukeException(ex.getMessage());
            }
            return new AddCommand(event);
        case "find":
            String word = arr[1];
            if (word.isEmpty()) {
                throw new DukeException("OOPS!!! I'm sorry, but I don't know what to find :-(");
            }
            return new FindCommand(word);
        default:
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
