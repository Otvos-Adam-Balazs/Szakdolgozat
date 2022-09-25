package com.example.webcrawler.controller;

import com.example.webcrawler.entities.Car;
import com.example.webcrawler.repositories.CarRepository;
import com.example.webcrawler.services.CarService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.Console;
import java.io.IOException;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@RestController
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public void getData() throws InterruptedException, IOException {
        String url="https://www.hasznaltauto.hu/talalatilista/PCOG2VG3R3RDADH5S56EDOONZSDMOXNBIFNKDEKWTLL4UUCTIKJRQJLJGWAPR53V3IBE3RKTVVRTWOJ6HYU4Q6JGV5PE5JSSKQILAJFHMCBY2WBSG2I4EX6QUA7EGHNIQXTFFACOBM3YY72IWGSUMBIMUEBWE34DAE23RXBIDN5TGKOEHABT7Q6MIIFKYXM66RWV4JLC3OTKO4YAP6Y2AQ36ART6V5Q4FETO3525RIJBZWE5AKW7ZC4CNEOBANGZC443444LB32QCA55JD7ZFAXZSFXWPTXP24GGQKKXGBO56QGBATCXEK5TERBSWOAGHDPDWWECHXH4NNPLQKPBF5WYICFCC6EXQHTXJ2DLDV6KINTDRJM5NEX4DDZ7R3O572USSS7XJEDP7ILL2QHH3IXJ27Q6ZSD6VMBHWOSTXZ4VP62A42X7FYOLSGQ2BSFORMXP3VTWPWXTPPKK3FOSLZKXV3VVDNS4FEO5APJ4MPAFLEFL72ECQKIVVD2QHYMSUBHLKP5CDY4QQG26BGCFJGAHHAIKMUWRS25C7YUF57M4UIKUMZWMWJEV7CJHTVP4BTAJ5IL4TQFVVQ4J3DWSAF7SVK5O3NXGKBZNFROTDBH3W6AW4NMRPT3DHTX6FBKY3Y4OBHI52CLOQ2QXY3CNEXH2ML4AJNQRKU45XWGAOPEFVI6HATSSCWCFRXTEEVJEZVK5OVXA7C2A2BAY52GYXSP3JPC75L4G7BNVYJ7N2VH3EZCGKFRRLIRFPQHFWDKJKO6EHV3MLKVUM4CAOOFHOJ5HWVXTEOXSKKIT2N6DDQVYD7RUELNJBJIDHEVIP6D366H6CRZZITM7FFKWEYMTL7RE7A3LIZ7U7GPFYCO2BR4N4OUR3HNH62CXDO2AHMVX74V5VJPU";
        int i = 1;

            do {

                Document doc = Jsoup.connect(url + "/page" + i).get();
                System.out.println(url + "/page" + i);
                Elements cars = doc.select("div.row.talalati-sor.kiemelt");

                for (Element carD : cars) {
                    Car car = new Car();

                    String price = carD.select("div.pricefield-primary").text().replaceAll(" ","");
                    if(price.equals("")){
                        car.setPrice(0);
                    }else{
                        double prices = Integer.parseInt(price.split("Ft")[0]);
                        car.setPrice(prices);
                    }

                    car.setImage(carD.getElementsByTag("img").attr("abs:src"));
                    car.setLink(carD.select("a").attr("abs:href"));

                    String CarName[] = carD.select("h3").text().split(" ");
                    String CarData[] = carD.select("span.info").text().split(" ");

                    car.setMakes(CarName[0]);
                    car.setModel(CarName[1]);

                    car.setFuelType(CarData[0].substring(0, CarData[0].length() - 1));
                    System.out.println(car.getFuelType());
                    if (car.getFuelType().equals("Elektromos")) {
                        car.setAge(Integer.parseInt(CarData[1].split("/")[0].split(",")[0]));
                        car.setHp(Integer.parseInt(CarData[7].split("LE")[0]));
                        car.setMileage(CarData[6] + CarData[7]);
                        carService.saveCar(car);

                    } else if (car.getFuelType().equals("Benzin") ||
                            car.getFuelType().equals("Dízel")) {
                        try {
                        car.setAge(Integer.parseInt(CarData[1].split("/")[0].split(",")[0]));
                        car.setEngine(Integer.parseInt((CarData[2] + CarData[3]).split("cm")[0]));
                        car.setHp(Integer.parseInt(CarData[7].split("LE")[0]));
                        car.setMileage(CarData[9]);
                        carService.saveCar(car);
                    }catch (Exception e){
                        System.out.println("Valami adat rossz");
                    }
                    } else {
                    }

                }

                i++;
                sleep(700);
            } while (i < 10);

    }


    @GetMapping("/get")
    public List<Car> listCar(){
        return carRepository.findAll();
    }

//   @GetMapping("/get/{makes}")
//    public List<Car> getCarByName(@PathVariable String makes){
//      return carService.getCarByMake(makes);
//  }
//
//    @GetMapping("/get/a")
//    public List<Car> getCarByNameAndModel(@RequestParam(name="makes") String makes ,@RequestParam(name="model") String model) {
//        return carService.getCarByMakeAndModel(makes, model);
//    }

    @GetMapping("/get/b")
    public List<Car> getCarByNameAndModelAndFuel(@RequestParam(name="makes") String makes ,
                                                 @RequestParam(name="model") String model,
                                                 @RequestParam("fuelType") String fuelType,
                                                 @RequestParam("minAge") String minAge,
                                                 @RequestParam("maxAge") String maxAge,
                                                 @RequestParam("minEngine") String minEngine,
                                                 @RequestParam("maxEngine") String maxEngine,
                                                 @RequestParam("minPrice") String minPrice,
                                                 @RequestParam("maxPrice") String maxPrice){
        return carService.getCarByMakeAndModelAndFuel(makes, model, fuelType, minAge,maxAge,minEngine,maxEngine,minPrice,maxPrice);
    }

//    @GetMapping("/get/{makes}/{model}/{fuelType}/{minAge}/{maxAge}")
//    public List<Car> getCarByNameAndModelAndFuelAndAge(
//            @PathVariable String makes ,@PathVariable String model, @PathVariable String fuelType,
//            @PathVariable String minAge, @PathVariable String maxAge){
//        return  carService.getCarByNameAndModelAndFuelAndAge(makes,model,fuelType,minAge,maxAge);
//    }
//
//    @GetMapping("/got/{minAge}/{maxAge}")
//    public List<Car> getCarByAge(@PathVariable String minAge, @PathVariable String maxAge){
//        return carService.getCarByAge(minAge, maxAge);
//
//    }


}
