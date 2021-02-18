package ru.nsu.fit.titkov.notebook;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;

public class NotebookTest {
    @Test
    public void test1() {
        File file = new File("notes.txt");
        file.delete();

        try {
            Notebook.main(new String[]{"-add", "note1", "Description1"});
            Notebook.main(new String[]{"-add", "note2", "Description2"});
            Notebook.main(new String[]{"-add", "note3", "Description3"});
            Notebook.main(new String[]{"-show"});
            Notebook.main(new String[]{"-rm", "note2"});

            String fileContent;
            List<UserNote> notes;

            BufferedReader bufferedReader = new BufferedReader(new FileReader("notes.txt"));
            Type TYPETOKEN_FOR_USERNOTES_LIST = new TypeToken<ArrayList<UserNote>>() {}.getType();
            Gson gson = new Gson();
            fileContent = bufferedReader.readLine();
            notes = gson.fromJson(fileContent, TYPETOKEN_FOR_USERNOTES_LIST);

            Assert.assertEquals(notes.size(), 2);

            Notebook.main(new String[]{"-show", "05.12.2020 14:40", "15.12.2020 14:45"});
            Notebook.main(new String[]{"-show", "05.12.2020 14:40", "15.12.2020 14:45", "1"});
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {

        try {
            Notebook.main(new String[]{"-add"});
        } catch (ParseException e) {
            System.out.println("There are not enough arguments ");
        }

        try {
            Notebook.main(new String[]{"-rm"});
        } catch (Exception e) {
            System.out.println("There are not enough arguments ");
        }

        try {
            Notebook.main(new String[]{});
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}