import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

    public class Picture {
        static String imageFolder = "C:\\Java\\WebScraping\\images";

        public static void downloadPicture(String request) throws IOException {
            if(request.matches(".+\\s+.+")) request = request.replace(' ','+');
            Document doc = Jsoup.connect("https://www.google.ru/images?q=" + request).get();
            Elements pictures = doc.selectXpath("//img[contains(@class,'rg_i')]");

            for(Element e:pictures){
                if(e.hasAttr("data-src")){
                    String pictureURL = e.attr("data-src");
                    downloadPic(pictureURL);
                }
            }
        }

        public static void downloadPic(String url) throws IOException {
            Random random = new Random();
            String pictureName = random.toString() + ".jpg";

            System.out.println("Saving: " + pictureName + ", from: " + url);

            URL urlImage = new URL(url);
            InputStream inputStream = urlImage.openStream();

            byte[] buffer = new byte[4096];
            int n = -1;

            OutputStream os =
                    new FileOutputStream( imageFolder + "\\" + pictureName );

            while ( (n = inputStream.read(buffer)) != -1 ){
                os.write(buffer, 0, n);
            }

            os.close();

            System.out.println("Saved!");
        }
    }
