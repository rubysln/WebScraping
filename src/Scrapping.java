import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scrapping {
    public static void doScrapping() throws IOException, IndexOutOfBoundsException {
        List<Integer> numberOfShop = new ArrayList<>();
        Document docMain = Jsoup.connect("https://nanegative.ru/internet-magaziny").get();
        System.out.println(docMain.title());

        for(int i = 1; i <= 11; i++){
            Document doc = Jsoup.connect("https://nanegative.ru/internet-magaziny?page=" + i).get();
            Elements postTitleElement = doc.getElementsByAttributeValue("class", "ss");
            Elements postRateElement = doc.getElementsByAttributeValue("class", "sro");
            Elements postRateCountElement = doc.getElementsByAttributeValue("class", "reviewers");

            for(int j = 0; j < postTitleElement.size(); j++){
                Element rateCountElement = postRateCountElement.get(j);
                Element titleElement = postTitleElement.get(j);
                Element rateElement = postRateElement.get(j);
                System.out.println((numberOfShop.size()) + ") " +titleElement.text() + " | " + rateElement.text() + " | " + rateCountElement.text());
                numberOfShop.add(1);
            }
        }
    }
}
