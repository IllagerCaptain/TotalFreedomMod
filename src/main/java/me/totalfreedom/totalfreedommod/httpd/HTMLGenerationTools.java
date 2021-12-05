package me.totalfreedom.totalfreedommod.httpd;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.Collection;
import java.util.Map;

public class HTMLGenerationTools
{
    public static String paragraph(String data)
    {
        return "<p>" + StringEscapeUtils.escapeHtml(data) + "</p>\r\n";
    }

    public static String heading(String data, int level)
    {
        return "<h" + level + ">" + StringEscapeUtils.escapeHtml(data) + "</h" + level + ">\r\n";
    }

    public static <K, V> String list(Map<K, V> map)
    {
        StringBuilder output = new StringBuilder();

        output.append("<ul>\r\n");

        for (Map.Entry<K, V> entry : map.entrySet())
        {
            output.append("<li>").append(StringEscapeUtils.escapeHtml(entry.getKey().toString() + " = " + entry.getValue().toString())).append("</li>\r\n");
        }

        output.append("</ul>\r\n");

        return output.toString();
    }

    public static <T> String list(Collection<T> list)
    {
        StringBuilder output = new StringBuilder();

        output.append("<ul>\r\n");

        for (T entry : list)
        {
            output.append("<li>").append(StringEscapeUtils.escapeHtml(entry.toString())).append("</li>\r\n");
        }

        output.append("</ul>\r\n");

        return output.toString();
    }
}
