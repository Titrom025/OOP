package ru.nsu.fit.titkov.notebook;

import picocli.CommandLine.*;

public class CommandArgs {
    @Option(names = "-rm")
    String noteToRemove;

    @Option(names = "-show")
    Boolean showNotes;

    @Option(names = "-add")
    Boolean addNote;

    @Parameters(paramLabel = "-show")
    String[] parameters;

}
