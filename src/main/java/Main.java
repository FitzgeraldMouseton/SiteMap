import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;

import java.util.Collections;
import java.util.HashSet;

public class Main {

    private static int count = 0;

    public static void main(String[] args) throws IOException {

//        File folder = new File(path);
//        if (! folder.exists()) {
//            folder.mkdir();
//        }

        URL url = new URL("https://skillbox.ru/courses");

        Document document = Jsoup.connect(url.toString()).get();

        HashSet<String> links = new HashSet<>();

        Elements linksToNextPages = document.select("a[href]");
        linksToNextPages.forEach(l -> links.add(l.attr("abs:href")));


        childIteration(links, url);

        //createSiteMap(links, "https://skillbox.ru/company/");

    }

    private static void childIteration(HashSet<String> links, URL url){
        links.forEach(l -> {
            URL nextUrl = null;
            String[] parts = null;
            String address = null;
            try {
                nextUrl = new URL(l);
                parts = nextUrl.getPath().split("/");
                address = url.getHost() + url.getPath();
            } catch (MalformedURLException e) {
            }

            if (nextUrl != null && nextUrl.toString().contains(address)) {
                System.out.println(l);
                System.out.println("Address: " + address);
            }
        });
    }

    private static void createSiteMap(HashSet<String> links, String url) throws IOException {

        Document document = Jsoup.connect(url).get();

        Elements linksOnPage = document.select("a[href]");

        //HashSet<String> links = new HashSet<>();

        for (Element link : linksOnPage) {

            if (count == 200){
                System.exit(5);
            }
            String str = link.attr("abs:href");
            if(str.contains("https://skillbox")) {
                if (links.add(str)){
                    Files.write(Paths.get("data/dd.txt"), Collections.singleton(str), StandardOpenOption.APPEND);
                    System.out.println(str);
                } else {
                    count++;
                }
            }
        }
        links.forEach(l -> {
            try {
                createSiteMap(links, l);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
