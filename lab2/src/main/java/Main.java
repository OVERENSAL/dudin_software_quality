import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static String mainSite = "http://91.210.252.240/broken-links/";
    public static Set<String> links = Collections.newSetFromMap(new ConcurrentHashMap<>());
    public static ArrayList<String> validLinksList = new ArrayList<>();
    public static ArrayList<String> invalidLinksList = new ArrayList<>();

    public static void main(String[] args) {
        File validLinks = new File("valid.txt");
        File invalidLinks = new File("invalid.txt");

        getLinks(mainSite);

        writeLinksToFile(validLinks, validLinksList);
        writeLinksToFile(invalidLinks, invalidLinksList);
    }

    public static void getLinks(String currSite) {
        int code = 0;
        try {
            Document doc = Jsoup.connect(currSite)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 YaBrowser/20.8.3.115 Yowser/2.5 Safari/537.36")
                    .referrer("http://www.yandex.ru")
                    .get();

            Elements elements = doc.select("a[href]");
            for (Element element : elements) {
                links.add(element.attr("href"));
            }
            for (String link: links) {
                currSite = (isOtherHost(link)) ? link : mainSite + link;
                if (!isChecked(currSite)) {
                    code = getResponseCode(currSite);
                    if (code == 200) {
                        validLinksList.add(currSite);
                        if (!isOtherHost(link))
                            getLinks(currSite);
                    } else {
                        invalidLinksList.add(currSite);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getResponseCode(String site) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(site);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            return connection.getResponseCode();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return 404;
    }

    public static boolean isChecked(String link) {
        return validLinksList.contains(link) || invalidLinksList.contains(link);
    }

    public static boolean isOtherHost(String link) {
        return link.contains("http");
    }

    public static void writeLinksToFile(File file, ArrayList<String> list) {
        try (FileWriter writer = new FileWriter(file)) {
            for (String s : list) {
                writer.write(s + " " + getResponseCode(s) + "\n");
            }
            writer.write("\n");
            writer.write("The number of links: " + list.size() + "\n");
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd.YYYY kk:mm");
            writer.write("Checked on " + dateFormat.format(date) + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
