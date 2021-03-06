package org.demo.service;

import org.demo.dto.Input;
import org.demo.dto.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Future;

//exposes RESTful webservices to
//persist data and
//compute statistics
//also a healthcheck method at the root context
//to ensure the webservice is running

@RestController
@RequestMapping
public class RestService {

    @Autowired
    private RepoService repoService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity healthCheck() {
        return new ResponseEntity("service is up", HttpStatus.OK);
    }

    @PostMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity postTransaction(@RequestBody Input input) throws Exception {
        Future<Boolean> status = repoService.add(input);
        return new ResponseEntity(HttpStatus.valueOf(status.get() ? 201 : 204));
    }

    @GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Output> getStatistics() throws Exception {
        Future<Output> restOutput = repoService.get();
        return new ResponseEntity(restOutput.get(), HttpStatus.OK);
    }
}
