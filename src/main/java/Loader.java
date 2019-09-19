import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ForkJoinPool;

public class Loader {

    public static void main(String[] args) throws MalformedURLException {

        ForkJoinPool pool = new ForkJoinPool();

        pool.invoke(new SiteMapCreator(new URL("https://skillbox.ru/courses/#code")));

        pool.shutdown();

        //System.out.println(s);
    }
}
