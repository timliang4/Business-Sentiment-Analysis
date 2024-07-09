package com.tim_liang.business_sentiment_analysis;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
class BusinessController {
    private final BusinessRepository repo;
    private final BusinessModelAssembler assembler;

    BusinessController(BusinessRepository repository, BusinessModelAssembler assembler) {
        this.repo = repository;
        this.assembler = assembler;
    }

    @PostMapping("/business/{id}")
    EntityModel<Business> newBusiness(@PathVariable String id) {
        Business newBusiness = new Business(id);
        System.out.println("RUNNING THE API");
        if (newBusiness.findSentimentAndName()) {
            repo.save(newBusiness);
            return assembler.toModel(newBusiness);
        }
        throw new BusinessNotFoundException(id);
    }

    @GetMapping("/business/{id}")
    EntityModel<Business> one(@PathVariable String id) {

        Business business = repo.findById(id) //
                .orElseThrow(() -> new BusinessNotFoundException(id));

        return assembler.toModel(business);
    }

    @GetMapping("/business/")
    CollectionModel<EntityModel<Business>> all() {

        List<EntityModel<Business>> businesses = repo.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(businesses, linkTo(methodOn(BusinessController.class).all()).withSelfRel());
    }

    @PutMapping("/business/{id}")
    EntityModel<Business> replaceModel(@RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "averageSentiment", defaultValue = "") String averageSentiment,
            @RequestParam(value = "sentimentVariance", defaultValue = "") String sentimentVariance,
            @PathVariable String id) {
        return repo.findById(id)
                .map(business -> {
                    business.setName(name.equals("") ? business.getName() : name);
                    business.setAverageSentiment(
                            averageSentiment.equals("") ? business.getAverageSentiment() : averageSentiment);
                    business.setSentimentVariance(
                            sentimentVariance.equals("") ? business.getSentimentVariance() : sentimentVariance);
                    repo.save(business);
                    return assembler.toModel(business);
                })
                .orElseThrow(() -> new BusinessNotFoundException(id));
    }

    @PatchMapping("/business/{id}")
    EntityModel<Business> updateModel(@PathVariable String id) {
        return repo.findById(id)
                .map(business -> {
                    System.out.println("RUNNING THE API");
                    business.findSentimentAndName();
                    repo.save(business);
                    return assembler.toModel(business);
                })
                .orElseThrow(() -> new BusinessNotFoundException(id));
    }

    @DeleteMapping("/business/{id}")
    void deleteBusiness(@PathVariable String id) {
        repo.deleteById(id);
    }

}
