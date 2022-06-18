package de.fhws.fiw.fds.sutton.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LinkToMapParser {

    public static HashMap<String, String> parseLinks(List<String> values)
    {
        HashMap<String, String> map = new HashMap<>();

        for(String s : values)
        {
            String[] parts = s.split(";");
            map.put(getRel(parts[1]), getLink(parts[0]));

        }
        return map;
    }

    private static String getRel(String string)
    {
        string = string.substring(string.indexOf("\"") + 1);
        string = string.substring(0, string.indexOf("\""));
        return string;
    }

    private static String getLink(String string)
    {
        if(string.contains("<"))
        {
            return string.substring(string.indexOf("<") + 1, string.indexOf(">"));
        }
        else return "";
    }
}
