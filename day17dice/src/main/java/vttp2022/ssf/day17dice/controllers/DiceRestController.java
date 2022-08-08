package vttp2022.ssf.day17dice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp2022.ssf.day17dice.services.DiceService;

@RestController
// produces -> Accept:
@RequestMapping(path="/rolls")
public class DiceRestController {

    @Autowired
    private DiceService diceSvc;

    // @GetMapping(produces = "text/csv")
    // public ResponseEntity<String> getRollAsCSV(
    //         @RequestParam(name = "dice", defaultValue = "1") Integer count) {
    //     if ((count < 1) || (count > 10)) {
    //         String error = "Error: Valid dice count is between 1 and 10. Your count is %d".formatted(count);
    //         return ResponseEntity
    //                 //.status(HttpStatus.BAD_REQUEST) 
    //                 .badRequest() //400
    //                 .body(error);
    //     }

    //     // Get the dice roll
    //     List<Integer> rolls = diceSvc.roll(count);

    //     // 3, 1, 5
    //     String csvString = rolls
    //             .stream()
    //             .map(v -> v.toString())
    //             .collect(Collectors.joining(","));

    //     return ResponseEntity.ok(csvString);
    // }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRollAsJson(
            @RequestParam(name = "dice", defaultValue = "1") Integer count) {

    // @RequestParam means extracting from query string in URL /rolls?dice=3
    // otherwise default count = 1

        if ((count < 1) || (count > 10)) {
            JsonObject errResp = Json
                    .createObjectBuilder()
                    .add("error", "Valid dice count is between 1 and 10. Your count is %d".formatted(count))
                    .build();
            String payload = errResp.toString();
            // Return 400
            return ResponseEntity
                    //.status(HttpStatus.BAD_REQUEST) 
                    .badRequest() //400
                    .body(payload);
        }
        
        // else
        // Get the dice roll
        List<Integer> rolls = diceSvc.roll(count);

        // Create the response payload
        JsonObjectBuilder builder = Json
                .createObjectBuilder()
                .add("count", count);

        // Create a JsonArray
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        // itertate through the list and add it into a json array
        for (Integer d: rolls)
            arrBuilder.add(d);

        // Add JsonArray (rolls) to the respose (json object)
        builder = builder.add("rolls", arrBuilder);

        // Get(build) the JsonObject object from JsonBuilder
        JsonObject resp = builder.build();

        return ResponseEntity.ok(resp.toString());
    }
}