package ru.nsu.fit.titkov.notebook;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import picocli.CommandLine;

import java.io.*;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;
import java.util.stream.Collectors;

public class Notebook {
    private static final String JSON_NOTES_FILE = "notes.txt";
    private static final Type TYPETOKEN_FOR_USERNOTES_LIST = new TypeToken<ArrayList<UserNote>>() {}.getType();
    private static List<UserNote> notes;

    private static void printList(List<UserNote> list) {
        for (UserNote note : list) {
            System.out.println(note);
        }
    }

    private static void readJsonToList() throws IOException {
        Gson gson = new Gson();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(JSON_NOTES_FILE))) {
            String fileContent = bufferedReader.readLine();
            notes = gson.fromJson(fileContent, TYPETOKEN_FOR_USERNOTES_LIST);
        } catch (FileNotFoundException e) {
            notes = new ArrayList<>();
        }
    }

    private static void saveNotesToJson() {
        Gson gson = new Gson();
        String JSON = gson.toJson(notes);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(JSON_NOTES_FILE))) {
            bufferedWriter.write(JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addNote(UserNote note) {
        notes.add(note);
        saveNotesToJson();
    }

    private static void removeNote(String title) {
        notes.removeIf(a -> a.getTitle().equals(title));
        saveNotesToJson();
    }

    /**
     * The main function, which take arguments and make specific actions based on arguments
     * if the first argument is "add":
     *     If there are one more arg, then the new note added with the title arg
     *     If there are two more args, then the new note added with the title arg1 and description arg2
     *
     * if the first argument is "rm":
     *     If there are one more arg, then the note with the title arg1 will be removed from notes list
     *
     * if the first argument is "show":
     *     If there are no more args, then the program will print all notes
     *     If there are two more args, then the program will print notes,
     *          which date is more than arg1 date and less than arg2 date
     *     If there are three or more args, then the program will print notes,
     *          which date is more than arg1 date and less than arg2 date,
     *          and the title contains at least one keyword from arg3 to argn
     *
     * In other case there will be alert about unexpected command
     * @param args - args from terminal
     */
    public static void main(String[] args) throws ParseException {
        CommandArgs commandArgs = new CommandArgs();
        new CommandLine(commandArgs).parseArgs(args);


        try {
            readJsonToList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (commandArgs.addNote != null) {
            if (commandArgs.parameters == null) {
                System.out.println("There are not enough arguments to add note!");
                return;
            }

            String description = "";
            if (commandArgs.parameters.length >= 2) {
                description = commandArgs.parameters[1];
            }
            UserNote note = new UserNote(commandArgs.parameters[0], description);
            addNote(note);
            return;
        }

        if (commandArgs.noteToRemove != null) {
            removeNote(commandArgs.noteToRemove);
            return;
        }

        if (commandArgs.showNotes != null) {
            if (commandArgs.parameters == null) {
                printList(notes);
                return;
            } else if (commandArgs.parameters.length >= 2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                Date dateBottom = dateFormat.parse(commandArgs.parameters[0]);
                Date dateUp = dateFormat.parse(commandArgs.parameters[1]);

                List<UserNote> notesInTimePeriod = notes.stream()
                        .map(note -> note.getDate().after(dateBottom) && note.getDate().before(dateUp) ? note : null)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                if (commandArgs.parameters.length == 2) {
                    printList(notesInTimePeriod);
                    return;
                }

                List<UserNote> notesWithKeyWords = new ArrayList<>();
                for (int i = 2; i < commandArgs.parameters.length; i++) {
                    int index = i;
                    List<UserNote> notesWithKeyWord = notes.stream()
                            .map(note -> note.getTitle().contains(args[index]) ? note : null)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    notesWithKeyWords.addAll(notesWithKeyWord);
                }

                printList(notesWithKeyWords);
                return;
            }
        }

        System.out.println("Unexpected command");
    }
}
