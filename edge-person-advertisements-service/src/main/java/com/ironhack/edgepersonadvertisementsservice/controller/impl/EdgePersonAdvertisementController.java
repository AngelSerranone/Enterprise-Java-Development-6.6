package com.ironhack.edgepersonadvertisementsservice.controller.impl;
import com.ironhack.edgepersonadvertisementsservice.classes.Advertisement;
import com.ironhack.edgepersonadvertisementsservice.classes.People;
import com.ironhack.edgepersonadvertisementsservice.controller.interfaces.IEdgePersonAdvertisementController;
import com.ironhack.edgepersonadvertisementsservice.dto.AdvertisementDto;
import com.ironhack.edgepersonadvertisementsservice.dto.PeopleDto;
import com.ironhack.edgepersonadvertisementsservice.dto.PersonWithAdDto;
import com.ironhack.edgepersonadvertisementsservice.service.interfaces.IEdgePersonAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class EdgePersonAdvertisementController implements IEdgePersonAdvertisementController {

    @Autowired
    private IEdgePersonAdvertisementService edgePersonAdvertisementService;

    @GetMapping("/person-by-name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public PersonWithAdDto getByName(@PathVariable("name") String name){
        return edgePersonAdvertisementService.getByName(name);
    }

    @GetMapping("/person-by-phone/{phoneNumber}")
    @ResponseStatus(HttpStatus.OK)
    public PersonWithAdDto getByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber){

        return edgePersonAdvertisementService.getByPhoneNumber(phoneNumber);

    }

    @PostMapping("/new-people-created")
    @ResponseStatus(HttpStatus.CREATED)
    public People createPeople(@RequestBody @Valid PeopleDto peopleDto){
        return edgePersonAdvertisementService.createPeople(peopleDto);
    }

    @PostMapping("/new-ad-created")
    @ResponseStatus(HttpStatus.CREATED)
    public Advertisement createAd(@RequestBody @Valid AdvertisementDto advertisementDto){
        return edgePersonAdvertisementService.createAd(advertisementDto);
    }
}
