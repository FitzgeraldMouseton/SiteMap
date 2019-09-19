import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class SiteMapCreator extends RecursiveAction {

    private URL url;
    private int count;

    SiteMapCreator(URL url) {
        this.url = url;
        count = 2;
    }

    @Override
    protected void compute() {

        String pageAddress;
        Elements linksToNextPages = null;
        List<String> links = new ArrayList<>();
        Document document = null;

        try {
            document = Jsoup.connect(url.toString()).get();
            pageAddress = document.select("meta[property=og:url]").first().attr("content");
            System.out.println("PageAddress:" + pageAddress);
            linksToNextPages = document.select("a[href]");
            //linksToNextPages.forEach(l -> links.add(l.attr("abs:href")));
            //links.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        linksToNextPages.forEach(l -> {
            URL nextUrl = null;
            String[] parts = null;
            String link = l.attr("abs:href");
            try {
                nextUrl = new URL(link);
                parts = nextUrl.getPath().split("/");
            } catch (MalformedURLException e) {

            }
            if (nextUrl != null && nextUrl.getHost().equals("skillbox.ru") && parts.length == count)
                System.out.println(l.attr("abs:href"));
        });

        List<SiteMapCreator> tasks = new ArrayList<>();

//        for (String link: links) {
//            SiteMapCreator task = null;
//            try {
//                task = new SiteMapCreator(new URL(link));
//                task.fork();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//
//            tasks.add(task);
//        }
//
//        tasks.forEach(ForkJoinTask::join);
    }
}
