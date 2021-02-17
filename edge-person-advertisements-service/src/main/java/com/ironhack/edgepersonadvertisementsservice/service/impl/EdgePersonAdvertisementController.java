package com.ironhack.edgepersonadvertisementsservice.service.impl;
import com.ironhack.edgepersonadvertisementsservice.classes.Advertisement;
import com.ironhack.edgepersonadvertisementsservice.classes.People;
import com.ironhack.edgepersonadvertisementsservice.client.AdvertisementClient;
import com.ironhack.edgepersonadvertisementsservice.client.PeopleClient;
import com.ironhack.edgepersonadvertisementsservice.dto.AdvertisementDto;
import com.ironhack.edgepersonadvertisementsservice.dto.PeopleDto;
import com.ironhack.edgepersonadvertisementsservice.dto.PersonWithAdDto;
import com.ironhack.edgepersonadvertisementsservice.service.interfaces.IEdgePersonAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EdgePersonAdvertisementController implements IEdgePersonAdvertisementService {

    @Autowired
    private AdvertisementClient advertisementClient;
    @Autowired
    private PeopleClient peopleClient;

    private CircuitBreakerFactory circuitBreakerFactory = new Resilience4JCircuitBreakerFactory();

    public PersonWithAdDto getByName(String name){

        CircuitBreaker adCircuitBreaker = circuitBreakerFactory.create("advertisement-service");
        CircuitBreaker peopleCircuitBreaker = circuitBreakerFactory.create("people-service");

        Advertisement ad = adCircuitBreaker.run(()-> advertisementClient.getRandom(), throwable -> getRandomFallBack());
        if(ad==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You don't deserve our ads");
        }

        People people = peopleCircuitBreaker.run(()-> peopleClient.findByName(name), throwable -> findByNameFallBack(name));
        if(people==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The person you are looking for is sleeping");
        }

        PersonWithAdDto personWithAdDto = new PersonWithAdDto();
        personWithAdDto.setName(name);
        personWithAdDto.setPhoneNumber(people.getPhoneNumber());
        personWithAdDto.setVendor(ad.getVendor());
        personWithAdDto.setText(ad.getText());

        return personWithAdDto;
    }


    public PersonWithAdDto getByPhoneNumber(String phoneNumber){

        CircuitBreaker adCircuitBreaker = circuitBreakerFactory.create("advertisement-service");
        CircuitBreaker peopleCircuitBreaker = circuitBreakerFactory.create("people-service");

        Advertisement ad = adCircuitBreaker.run(()-> advertisementClient.getRandom(), throwable -> getRandomFallBack());
        if(ad==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You don't deserve our ads");
        }
        People people = peopleCircuitBreaker.run(()-> peopleClient.findByPhoneNumber(phoneNumber), throwable -> findByPhoneNameFallBack(phoneNumber));
        if(people==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The person you are looking for is sleeping");
        }

        PersonWithAdDto personWithAdDto = new PersonWithAdDto();
        personWithAdDto.setName(people.getName());
        personWithAdDto.setPhoneNumber(phoneNumber);
        personWithAdDto.setVendor(ad.getVendor());
        personWithAdDto.setText(ad.getText());

        return personWithAdDto;

    }



    public People createPeople(PeopleDto peopleDto){
        return peopleClient.newPeople(peopleDto);
    }

    public Advertisement createAd(AdvertisementDto advertisementDto){
        return advertisementClient.newAd(advertisementDto);
    }



    private People findByNameFallBack(String name) {
        return null;
    }

    private Advertisement getRandomFallBack() {
        return null;
    }

    private People findByPhoneNameFallBack(String phoneNumber) {
        return null;
    }

}
