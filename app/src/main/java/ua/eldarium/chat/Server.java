package ua.eldarium.chat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.*;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by asus on 06.07.2016.
 */
public class Server extends NanoHTTPD{

    ArrayList<String> log;

    public Server(int port) {
        super(port);
    }

    public Server(String hostname, int port) {
        super(hostname, port);
    }

    public Server() throws IOException {
        super(65335);
        log=new ArrayList<String>();
        start(NanoHTTPD.SOCKET_READ_TIMEOUT,false);
    }

    @Override
    public Response serve(IHTTPSession session) {

        StringBuilder page= new StringBuilder();
        page.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n<meta charset=\"utf-8\">\n" +
                "<style type=\"text/css\">"+
                "body {\n" +
                "    font:12px arial;\n" +
                "    color: #222;\n" +
                "    text-align:center;\n" +
                "    padding:35px; }\n" +
                "  \n" +
                "form, p, span {\n" +
                "    margin:0;\n" +
                "    padding:0; }\n" +
                "  \n" +
                "input { font:12px arial; }\n" +
                "  \n" +
                "a {\n" +
                "    color:#0000FF;\n" +
                "    text-decoration:none; }\n" +
                "  \n" +
                "    a:hover { text-decoration:underline; }\n" +
                "  \n" +
                "#wrapper, #loginform {\n" +
                "    margin:0 auto;\n" +
                "    padding-bottom:25px;\n" +
                "    background:#EBF4FB;\n" +
                "    width:504px;\n" +
                "    border:1px solid #ACD8F0; }\n" +
                "  \n" +
                "#loginform { padding-top:18px; }\n" +
                "  \n" +
                "    #loginform p { margin: 5px; }\n" +
                "  \n" +
                "#chatbox {\n" +
                "    text-align:left;\n" +
                "    margin:0 auto;\n" +
                "    margin-bottom:25px;\n" +
                "    padding:10px;\n" +
                "    background:#fff;\n" +
                "    height:270px;\n" +
                "    width:430px;\n" +
                "    border:1px solid #ACD8F0;\n" +
                "    overflow:auto; }\n" +
                "  \n" +
                "#usermsg {\n" +
                "    width:395px;\n" +
                "    border:1px solid #ACD8F0; }\n" +
                "  \n" +
                "#submit { width: 60px; }\n" +
                "  \n" +
                ".error { color: #ff0000; }\n" +
                "  \n" +
                "#menu { padding:12.5px 25px 12.5px 25px; }\n" +
                "  \n" +
                ".welcome { float:left; }\n" +
                "  \n" +
                ".logout { float:right; }\n" +
                "  \n" +
                ".msgln { margin:0 0 2px 0; }"+
                "</style>"+
                "<title>Chat</title>\n" +
                "<link type=\"text/css\" rel=\"stylesheet\" href=\"style.css\" />\n" +
                "</head>\n" +
                "<body> \n" +
                "<div id=\"wrapper\">\n" +
                "    <div id=\"menu\">\n" +
                "        <p class=\"logout\"><a id=\"exit\" href=\"#\">Exit Chat</a></p>\n" +
                "        <div style=\"clear:both\"></div>\n" +
                "    </div>\n" +
                "     \n" +
                "    <div id=\"chatbox\">");
        Map<String, String> parms = session.getParms();
        if (parms.get("usermsg") != null) {
            StringBuilder message = new StringBuilder("<div class = msgln>");
            message.append(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            message.append(" :: ");
            message.append(parms.get("usermsg"));
            message.append("\n</div>");
            log.add(message.toString());
        }
        for (String s : log){
            page.append(s);
            page.append('\n');
        }
        page.append("</div>\n" +
                "     \n" +
                "    <form method=\"get\" name=\"message\" action=\"\">\n" +
                "        <input name=\"usermsg\" type=\"text\" id=\"usermsg\" size=\"63\" />\n" +
                "        <input name=\"submitmsg\" type=\"submit\"  id=\"submitmsg\" value=\"Send\" />\n" +
                "    </form>\n" +
                "</div>\n" +
                "<script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js\"></script>\n" +
                "<script type=\"text/javascript\">\n" +
                "// jQuery Document\n" +
                "$(document).ready(function(){\n" +
                " \n" +
                "$(\"#submitmsg\").click(function(){\t\n" +
//                        "\t\tvar clientmsg = $(\"#usermsg\").val();\n" +
//                        "\t\t$.get(\"/\", {text: clientmsg});\t\t\t\t\n" +
//                        "\t\t$(\"#usermsg\").attr(\"value\", \"\");\n" +
//                        "\t\treturn false;\n" +
//                        "\t});"+
                "alert(\"fgfdgd\")"+
                "});\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>");
        return newFixedLengthResponse( page.toString() );
    }
}
