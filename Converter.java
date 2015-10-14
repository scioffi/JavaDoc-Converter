import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

class Converter {

	public static void main(String[] args) {
        System.out.println("Michael wuz here");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter URL: ");
        // e.g. "http://www.nytimes.com"
        // still doesn't work with Wikipedia becuase it forces HTTPS
        String page = pullFromURL(sc.nextLine());
        if (page != null)
            System.out.println(page);
        sc.close();
	}

    /**
     * Download an HTML webpage via HTTP
     * NOTE: HTTPS sites will cause this method to fail
     * @param target URL string of javadoc web page to download
     * @return string of the entire webpage content (HTML)
     */
    static String pullFromURL(String target) {
        StringBuilder content = new StringBuilder();
        String nextln = "";

        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader ISReader = null;
        BufferedReader contentReader = null;

        try {
            url = new URL(target);
        } catch (MalformedURLException e) {
            System.err.println("ERR:Page not found");
            System.err.println(e.getMessage());
            return null;
        }

        try {
            connection = (HttpURLConnection)url.openConnection();
        } catch (IOException e) {
            System.err.println("ERR:Connection to page could not be established");
            System.err.println(e.getMessage());
            return null;
        }

        try {
            ISReader = new InputStreamReader((InputStream)connection.getContent());
        } catch (IOException e) {
            System.err.println("ERR:Input stream could not be opened");
            System.err.println(e.getMessage());
            return null;
        }

        contentReader = new BufferedReader(ISReader);

        for(;;) {
            try {
                nextln = contentReader.readLine();
            } catch (IOException e) {
                System.err.println("ERR:Problem reading page content");
                System.err.println(e.getMessage());
                return null;
            }
            if (nextln == null)
                break;
            content.append(nextln);
            content.append("\n");
        }

        content.deleteCharAt(content.length()-1);
        return content.toString();
    }

}