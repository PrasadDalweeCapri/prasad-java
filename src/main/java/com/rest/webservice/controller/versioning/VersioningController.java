package com.rest.webservice.controller.versioning;


import com.rest.webservice.entity.Version;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class VersioningController {

    /**
     * f1,f2: URI based versioning: version1 and version2
     * f3,f4: Parameter based versioning: version1 and version2
     */

//URI based
    @GetMapping("/v1/fetch")
    public Version fetchByURI1() {
        return new Version(1, "URI Based Versioning");
    }

    @GetMapping("/v2/fetch")
    public Version fetchByURI2() {
        return new Version(2, "URI Based Versioning");
    }


    //parameter based
    @GetMapping(value = "/fetch")
    public Version fetchByParam1(@RequestParam Integer version) {
        if (version.equals(1)) {
            return new Version(1, "Paramter Based Versioning");
        }

        if (version.equals(2)) {
            return new Version(2, "Parameter Based Versioning");
        } else
            throw new NoSuchElementException("That version doesn't exist");
    }


    //Header Based
    @GetMapping(value = "/fetch", headers = "X-API-VERSION=1")
    public Version fetchByHeader1() {
        return new Version(1, "Header Based Versioning");
    }

    @GetMapping(value = "/fetch", headers = "X-API-VERSION=2")
    public Version fetchByHeader2() {
        return new Version(2, "Header Based Versioning");
    }


    //Media-Type Based (aka Content Negotiation)
    //standard format: application/vnd.<apiname>.v<version>+json
    @GetMapping(value = "/fetch", produces = "application/vnd.users.v1+json")
    public Version fetchByMediaType1() {
        return new Version(1, "Media Type Based Versioning");
    }

    @GetMapping(value = "/fetch", produces = "application/vnd.users.v2+json")
    public Version fetchByMediaType2() {
        return new Version(2, "Media Type Based Versioning");
    }

//Pros and Cons
    /*
    1. URI based:
        Pros:
            1.Easy to read and understand
            2.Version has to be mentioned in URI
            3.Easy to request through browser
            4.Commonly Used
        Cons:
            1.Clutters URI
            2.Version is visible in URI (might not be desired)
            3.Disrupts RESTful compliance: (URIs should represent resources not versions)
            4.Should be avoided if versioning resources
    2. Param based:
       Pros:
            1. Version is visible as parameter in URI
            2. Easy to request through browser
       Cons:
            1. Request Parameters can be omitted, version might be omitted as well
            2. If resource versions are mixed, the query can get messy
            3. Clutters URI
    3. Header Based
        Pros:
            1. Preserves URI(Neater than above 2)
            2. Version information is provided by headers
        Cons:
            1. Version isn't visible in URI
            2. Cannot run directly on browser
            3. Requires custom headers
    4. Media Type Based
        Pros:
            1. Preserves URI
            2. Version Information is provided in the request header
            3. Useful for media-type versioning beyond
        Cons:
            1. Version isn't visible in URI
            2. Cannot run directly on browser
            3. Requires custom headers
            4. They distort the HTTP headers’ purpose: clients will need to know the media type for
            each resource and request the same one throughout their use of your API to ensure their
            code continues to function normally as you push out new changes.
     */


}
