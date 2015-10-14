import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

class Converter {

	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter URL: ");
        /*
         e.g.
         http://www.nytimes.com
         or
         https://www.wikipedia.org
         or
         http://partyhorse.party
        */
        String page = pullFromURL(sc.nextLine());
        if (page != null)
            System.out.println(page);
        sc.close();
	}

    /**
     * Download an HTML webpage via HTTP or HTTPS (SSL connections use Java's default certificate settings)
     * TODO: automatically add www. and then http:// if necessary when provided URL throws a MalformedURLException
     * @param target URL string of javadoc web page to download
     * @return string of the webpage content (e.g. raw HTML)
     */
    public static String pullFromURL(String target) {
        URL url;

        try {
            url = new URL(target);
        } catch (MalformedURLException e) {
            System.err.println("ERR:Invalid URL");
            System.err.println(e.getMessage());
            return null;
        }

        URLConnection connection;
        Reader reader;
        BufferedReader br;

        try {
            connection = url.openConnection();
            reader = new InputStreamReader(connection.getInputStream());
            br = new BufferedReader(reader);
        } catch (IOException e) {
            System.err.println("ERR:Could not initialize connection");
            System.err.println(e.getMessage());
            return null;
        }

        StringBuilder content = new StringBuilder();
        String nextln;

        for(;;) {
            try {
                nextln = br.readLine();
            } catch (IOException e) {
                System.err.println("ERR:Problem while reading page content");
                System.err.println(e.getMessage());
                return null;
            }
            if (nextln == null)
                break;
            content.append(nextln);
            content.append("\n");
        }

        try {
            br.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        content.deleteCharAt(content.length()-1);
        return content.toString();
    }

}