package com.rest.webservice.controller.versioning;


import com.rest.webservice.entity.Version;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningController
{

    /**
     * f1,f2: URI based versioning: version1 and version2
     * f3,f4: Parameter based versioning: version1 and version2
     */

//URI based
    @GetMapping("/v1/fetch")
    public Version fetch1()
    {
        return new Version(1,"URI Based Versioning");
    }

    @GetMapping("/v2/fetch")
    public Version fetch2()
    {
        return new Version(2,"URI Based Versioning");
    }

//parameter based
    @GetMapping(value = "/fetch",params ="version=1" )
    public Version fetch3()
    {
        return new Version(1,"Paramter Based Versioning");
    }

    @GetMapping(value = "/fetch",params ="version=2" )
    public Version fetch4()
    {
        return new Version(2,"Paramter Based Versioning");
    }



}
