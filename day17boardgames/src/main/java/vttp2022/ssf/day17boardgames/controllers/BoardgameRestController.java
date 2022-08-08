package vttp2022.ssf.day17boardgames.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.ssf.day17boardgames.models.Boardgame;
import vttp2022.ssf.day17boardgames.services.BoardgameService;

@RestController
@RequestMapping("/boardgame")
public class BoardgameRestController {

    @Autowired
    private BoardgameService boardgameSvc;
    
    @GetMapping(path="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBoardgame (@PathVariable String id ) {

        // get the opt from boardgame services after passing in the id to retrive payload from repo
        Optional<Boardgame> opt = boardgameSvc.getBoardgameById(id);

        // payload format is in opt
        if (opt.isEmpty()) {
            JsonObject err = Json.createObjectBuilder()
                .add("error", "Id %s not found".formatted(id))
                .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(err.toString());
        }

        Boardgame boardgame = opt.get();
        return ResponseEntity.ok(boardgame.readModelCreateJsonObj().toString());
        // return ResponseEntity.ok(opt.get().readModelCreateJsonObj().toString());
    }
}
