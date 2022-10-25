package com.example.webcrawler.services;

import com.example.webcrawler.dto.MakesDto;
import com.example.webcrawler.dto.ModelDto;
import com.example.webcrawler.entities.Car;
import com.example.webcrawler.repositories.CarRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.io.IOException;

import java.util.List;


import static java.lang.Thread.sleep;

@Service
@Component
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public void saveCar() throws InterruptedException, IOException {
        String hasznaltauto="https://www.hasznaltauto.hu/talalatilista/PCOG2VG3R3RDADH5S56EDOONZSDMOXNBIFNKDEKWTLL4UUCTIKJRQJLJGWAPR53V3IBE3RKTVVRTWOJ6HYU4Q6JGV5PE5JSSKQILAJFHMCBY2WBSG2I4EX6QUA7EGHNIQXTFFACOBM3YY72IWGSUMBIMUEBWE34DAE23RXBIDN5TGKOEHABT7Q6MIIFKYXM66RWV4JLC3OTKO4YAP6Y2AQ36ART6V5Q4FETO3525RIJBZWE5AKW7ZC4CNEOBANGZC443444LB32QCA55JD7ZFAXZSFXWPTXP24GGQKKXGBO56QGBATCXEK5TERBSWOAGHDPDWWECHXH4NNPLQKPBF5WYICFCC6EXQHTXJ2DLDV6KINTDRJM5NEX4DDZ7R3O572USSS7XJEDP7ILL2QHH3IXJ27Q6ZSD6VMBHWOSTXZ4VP62A42X7FYOLSGQ2BSFORMXP3VTWPWXTPPKK3FOSLZKXV3VVDNS4FEO5APJ4MPAFLEFL72ECQKIVVD2QHYMSUBHLKP5CDY4QQG26BGCFJGAHHAIKMUWRS25C7YUF57M4UIKUMZWMWJEV7CJHTVP4BTAJ5IL4TQFVVQ4J3DWSAF7SVK5O3NXGKBZNFROTDBH3W6AW4NMRPT3DHTX6FBKY3Y4OBHI52CLOQ2QXY3CNEXH2ML4AJNQRKU45XWGAOPEFVI6HATSSCWCFRXTEEVJEZVK5OVXA7C2A2BAY52GYXSP3JPC75L4G7BNVYJ7N2VH3EZCGKFRRLIRFPQHFWDKJKO6EHV3MLKVUM4CAOOFHOJ5HWVXTEOXSKKIT2N6DDQVYD7RUELNJBJIDHEVIP6D366H6CRZZITM7FFKWEYMTL7RE7A3LIZ7U7GPFYCO2BR4N4OUR3HNH62CXDO2AHMVX74V5VJPU";
        String joAutok="https://joautok.hu/hasznaltauto";

        carRepository.deleteAll();
        saveHasznaltauto(hasznaltauto);
        saveJoAutok(joAutok);
    }


    public List<Car> getCars(String makes, String model, String fuelType, String minAge,
                             String maxAge, String minEngine, String maxEngine, String minPrice, String maxPrice) {
        return carRepository.findAll(new Specification<Car>() {
            @Override
            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate p = cb.conjunction();
                if(!StringUtils.isEmpty(makes)){
                    p = cb.and(p, cb.equal(root.get("makes"), makes));
                }
                if(!StringUtils.isEmpty(model)){
                    p = cb.and(p, cb.equal(root.get("model"), model));
                }
                if(!StringUtils.isEmpty(fuelType)){
                    p = cb.and(p, cb.equal(root.get("fuelType"), fuelType));
                }
                if(!StringUtils.isEmpty(minAge) && !StringUtils.isEmpty(maxAge) && Integer.parseInt(minAge)<Integer.parseInt(maxAge)){
                    p = cb.and(p, cb.between(root.get("age"), Integer.parseInt(minAge),Integer.parseInt(maxAge)));
                }
                if(!StringUtils.isEmpty(minEngine) && !StringUtils.isEmpty(maxEngine) && Integer.parseInt(minEngine)<Integer.parseInt(maxEngine)){
                    p = cb.and(p, cb.between(root.get("engine"), Integer.parseInt(minEngine),Integer.parseInt(maxEngine)));
                }
                if(!StringUtils.isEmpty(minPrice) && !StringUtils.isEmpty(maxPrice) && Integer.parseInt(minPrice)<Integer.parseInt(maxPrice)){
                    p = cb.and(p, cb.between(root.get("price"), Integer.parseInt(minPrice), Integer.parseInt(maxPrice)));
                }
                return p;
            }
        });
    }

    public MakesDto[] getCarMakes() {

        String[] makes =  carRepository.getCarMakes();

        MakesDto[] makesDto = new MakesDto[makes.length];
        for(int i=0; i<makes.length; i++){
            makesDto[i] = new MakesDto();
            makesDto[i].setMake(makes[i]);
            makesDto[i].setValue(makes[i]);
        }
        return makesDto;
    }

    public ModelDto[] getModelByName(String makes) {

        String[] models =  carRepository.getModelByName(makes);

        ModelDto[] modelDtos = new ModelDto[models.length];
        for(int i=0; i<models.length; i++){
            modelDtos[i] = new ModelDto();
            modelDtos[i].setModel(models[i]);
            modelDtos[i].setValue(models[i]);
        }
        return modelDtos;
    }

    public void saveHasznaltauto(String hasznaltauto) throws InterruptedException, IOException {
        int i = 1;

        do {

            Document doc = Jsoup.connect(hasznaltauto + "/page" + i).get();
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

                if (car.getFuelType().equals("Elektromos")) {
                    car.setAge(Integer.parseInt(CarData[1].split("/")[0].split(",")[0]));
                    car.setHp(Integer.parseInt(CarData[7].split("LE")[0]));
                    car.setMileage(CarData[6] + CarData[7]);
                    carRepository.save(car);

                } else if (car.getFuelType().equals("Benzin") ||
                        car.getFuelType().equals("Dízel")) {
                    try {
                        car.setAge(Integer.parseInt(CarData[1].split("/")[0].split(",")[0]));
                        car.setEngine(Integer.parseInt((CarData[2] + CarData[3]).split("cm")[0]));
                        car.setHp(Integer.parseInt(CarData[7].split("LE")[0]));
                        car.setMileage(CarData[9] + CarData[10]);
                        carRepository.save(car);
                    }catch (Exception e){
                        System.out.println("Some Data is Bad");
                    }
                } else {
                }

            }

            i++;
            sleep(700);
        } while (i < 10);
    }

    public void saveJoAutok(String joAutok) throws IOException, InterruptedException {
        int i = 30;

        try {
            Document doc = Jsoup.connect(joAutok +"?page=" +i).get();

            Elements cars = doc.select("a.item");
            for (Element carD : cars) {
                Car car = new Car();

                String price = carD.select("div.price").text().replaceAll(" ", "");
                double priceNumber = Integer.parseInt(price.substring(0, price.indexOf("F")));
                car.setPrice(priceNumber);


                car.setImage(carD.getElementsByTag("img").attr("abs:data-src"));
                car.setLink(carD.select("a").attr("abs:href"));

                String CarName[] = carD.select("div.h2-top").text().split(" ");
                String CarData[] = carD.select("span.dotted").text().split(" ");

                car.setMakes(CarName[0]);
                car.setModel(CarName[1]);
                System.out.println(CarData);
                car.setFuelType(CarData[7]);
                String Age[] =carD.select("b").text().split(" ");

                if (car.getFuelType().equals("Benzin") ||
                        car.getFuelType().equals("Dízel")) {
                    try {
                        car.setAge(Integer.parseInt(Age[2].substring(0,Age[2].indexOf("."))));
                        car.setEngine(Integer.parseInt(CarData[5]));
                        car.setHp(Integer.parseInt(CarData[3]));
                        car.setMileage(CarData[0] + CarData[1]);
                        carRepository.save(car);
                    }catch (Exception e){
                        System.out.println("Some Data is Bad!");
                    }
                } else {
                }
            }
        }catch (Exception e){
            System.out.println("Bad Price");
        }
    }
}


