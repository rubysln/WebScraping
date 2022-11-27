import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Scrapping {
    public static void doScrapping() throws IOException, IndexOutOfBoundsException {
        List<Integer> numberOfShop = new ArrayList<>();
        Document docMain = Jsoup.connect("https://nanegative.ru/internet-magaziny").get();
        HashMap<Integer, String> mapShops = new HashMap<>();
        System.out.println(docMain.title());

        Elements pagesCount = docMain.selectXpath("//a[starts-with(@href,'/internet-magaziny?page=')]");
        for(int i = 1; i <= pagesCount.size(); i++){
            Document doc = Jsoup.connect("https://nanegative.ru/internet-magaziny?page=" + i).get();
            Elements postTitleElement = doc.getElementsByAttributeValue("class", "ss");
            Elements postRateElement = doc.getElementsByAttributeValue("class", "sro");
            Elements postRateCountElement = doc.getElementsByAttributeValue("class", "reviewers");

            for(int j = 0; j < postTitleElement.size(); j++){
                Element rateCountElement = postRateCountElement.get(j);
                Element titleElement = postTitleElement.get(j);
                Element rateElement = postRateElement.get(j);
                String str = titleElement.text().replace("Отзывы о", "");
                System.out.println((numberOfShop.size()) + ") " + str + " | " + rateElement.text() + " | " + rateCountElement.text());
                numberOfShop.add(1);
                mapShops.put(j, titleElement.attr("href"));
            }
        }

        System.out.println("Введите номер интересующего магазина: ");
        Scanner scanner = new Scanner(System.in);
        int inputShop = scanner.nextInt();

        docMain = Jsoup.connect("https://nanegative.ru" + mapShops.get(inputShop)).get();
        var pages = docMain.selectXpath("//a[starts-with(@href,'" + mapShops.get(inputShop) + "?page=')]");
        if(pages.size() == 0){
            Elements reviews = docMain.getElementsByAttributeValue("itemprop", "description");
            if(reviews.size() == 0){
                System.out.println("Отзывы о данном магазине отсутствуют!");
            }
            else{
                for(var e: reviews){
                    System.out.println(e.text());
                }
            }
        }
        else{
            for(int i = 0; i < pages.size(); i++){
                docMain = Jsoup.connect("https://nanegative.ru" + mapShops.get(inputShop) + "?page=" + i).get();
                Elements reviews = docMain.getElementsByAttributeValue("itemprop", "description");
                for(var e: reviews){
                    System.out.println(e.text());
                }
            }
        }
    }
}
