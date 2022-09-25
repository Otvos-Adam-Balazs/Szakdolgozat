package com.example.webcrawler;

import com.example.webcrawler.entities.Car;
import com.example.webcrawler.repositories.CarRepository;
import com.example.webcrawler.services.CarService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static java.lang.Thread.sleep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class WebcrawlerApplication {


	
	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(WebcrawlerApplication.class, args);
//		CarService carService = new CarService();
//		String url="https://www.hasznaltauto.hu/talalatilista/PCOG2VG3R3RDADH5S56EDOONZSDMOXNBIFNKDEKWTLL4UUCTIKJRQJLJGWAPR53V3IBE3RKTVVRTWOJ6HYU4Q6JGV5PE5JSSKQILAJFHMCBY2WBSG2I4EX6QUA7EGHNIQXTFFACOBM3YY72IWGSUMBIMUEBWE34DAE23RXBIDN5TGKOEHABT7Q6MIIFKYXM66RWV4JLC3OTKO4YAP6Y2AQ36ART6V5Q4FETO3525RIJBZWE5AKW7ZC4CNEOBANGZC443444LB32QCA55JD7ZFAXZSFXWPTXP24GGQKKXGBO56QGBATCXEK5TERBSWOAGHDPDWWECHXH4NNPLQKPBF5WYICFCC6EXQHTXJ2DLDV6KINTDRJM5NEX4DDZ7R3O572USSS7XJEDP7ILL2QHH3IXJ27Q6ZSD6VMBHWOSTXZ4VP62A42X7FYOLSGQ2BSFORMXP3VTWPWXTPPKK3FOSLZKXV3VVDNS4FEO5APJ4MPAFLEFL72ECQKIVVD2QHYMSUBHLKP5CDY4QQG26BGCFJGAHHAIKMUWRS25C7YUF57M4UIKUMZWMWJEV7CJHTVP4BTAJ5IL4TQFVVQ4J3DWSAF7SVK5O3NXGKBZNFROTDBH3W6AW4NMRPT3DHTX6FBKY3Y4OBHI52CLOQ2QXY3CNEXH2ML4AJNQRKU45XWGAOPEFVI6HATSSCWCFRXTEEVJEZVK5OVXA7C2A2BAY52GYXSP3JPC75L4G7BNVYJ7N2VH3EZCGKFRRLIRFPQHFWDKJKO6EHV3MLKVUM4CAOOFHOJ5HWVXTEOXSKKIT2N6DDQVYD7RUELNJBJIDHEVIP6D366H6CRZZITM7FFKWEYMTL7RE7A3LIZ7U7GPFYCO2BR4N4OUR3HNH62CXDO2AHMVX74V5VJPU";
//		int i = 1;
//		do {
//
//			Document doc = Jsoup.connect(url + "/page" + i).get();
//			System.out.println(url + "/page" + i);
//			Elements cars = doc.select("div.row.talalati-sor.kiemelt");
//
//			for (Element carD : cars) {
//				Car car = new Car();
//				car.setName(carD.select("h3").text());
//				car.setImage(carD.getElementsByTag("img").attr("abs:src"));
//				car.setDatas(carD.select("span.info").text());
//				car.setLink(carD.select("a").attr("abs:href"));
//
//				carService.saveCar(car);
//			}
//			i++;
//			sleep(700);
//		}while(i<4);

	}

}
