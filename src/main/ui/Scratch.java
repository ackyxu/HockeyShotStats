package ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Scratch {
    public Scratch() throws IOException {
        System.out.print(parseText().get(4));
    }


    public List<String> parseText() throws IOException {
        System.out.println("Enter the file location");
        Scanner matchjson = new Scanner(System.in);
        String file = matchjson.next();
        List<String> strs = new ArrayList<>();

        FileReader input;
        input = new FileReader(file);


        try (BufferedReader br = new BufferedReader(input)) {
            String line;
            while ((line = br.readLine()) != null) {
                strs.add(line);
            }
        }

        return strs;
    }
    //MODIFIES: None
    //EFFECT: Parse/Format event data of a match into LiveData
    //Events are stored as a Array (JSON) with multiple Object (JSON).  This method will breakdown each Object and
    //extract the data needed to construct a LiveData
    //NOTE: In Phase 1, this method will parse a raw String that comprise of three types of Objects in a order list
    //Three Objects(results, about, coordinates) makes up an event, and all the Objects for an event are in sequence
    //Event Object has nested data, separated by "|"
    //JSON file parse into CSV via https://codebeautify.org/jsonviewer

    private List<String> parseEvent(String str) {
        //NOTE:  In Phase 2, the code bellow will be changed to accommodate parsing a JSON file.
        List<String> lstr = Arrays.asList(str.split(","));

        for (int i = 0; i < lstr.size(); i += 3) {

            String result = lstr.get(i);
            String about = lstr.get(i + 1);
            String coordinates = lstr.get(i + 2);

            List<String> resultList = Arrays.asList(result.split("|"));
            List<String> aboutList = Arrays.asList(about.split("|"));
            List<String> coordinateList = Arrays.asList(coordinates.split("|"));

        }

        return lstr;



    }
}

