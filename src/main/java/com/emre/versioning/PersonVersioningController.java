package com.emre.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

    @GetMapping("/v1/person")
    public PersonV1 personV1() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping("/v2/person")
    public PersonV2 personV2() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    @GetMapping(value = "/person/param", params="version=1") // localhost:8080/person/param?version=1
    public PersonV1 paramV1() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(value = "/person/param", params="version=2") // localhost:8080/person/param?version=2
    public PersonV2 paramV2() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    @GetMapping(value = "/person/header", headers="X_API_VERSION=1") // localhost:8080/person/header   -Postman' de Headers' a X_API_VERSION=1 ekle
    public PersonV1 headerV1() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(value = "/person/header", headers="X_API_VERSION=2") // localhost:8080/person/header   -Postman' de Headers' a X_API_VERSION=2 ekle
    public PersonV2 headerV2() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    @GetMapping(value = "/person/produces", produces = "application/vnd.company.app-v1+json") // localhost:8080/person/produces   -Postman' de Headers' a Accept=application/vnd.company.app-v1+json ekle
    public PersonV1 producesV1() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(value = "/person/produces", produces = "application/vnd.company.app-v2+json") // localhost:8080/person/produces   -Postman' de Headers' a Accept=application/vnd.company.app-v2+json ekle
    public PersonV2 producesV2() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

}