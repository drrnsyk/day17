package vttp2022.ssf.day17boardgames.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;
import vttp2022.ssf.day17boardgames.models.Boardgame;
import vttp2022.ssf.day17boardgames.repositories.BoardgameRepository;

@Service
public class BoardgameService {
    
    @Autowired
    private BoardgameRepository boardgameRepo;
    
    // cannot autowired Boardgame class because it is not handled by a bean
    // @Autowired
    // private Boardgame boardgame;

    public Optional<Boardgame> getBoardgameById(String id) {

        Boardgame boardgame = new Boardgame();
        
        String payload = boardgameRepo.getFromRedis(id);
        if (payload == null) {
            return Optional.empty();
        }
        else
        {
            JsonObject jo = boardgame.readStrCreateJsonObject(payload);
            return Optional.of(boardgame.readJsonObjCreateBoardgame(jo));
        }

    }

    public Integer count() {
        return boardgameRepo.count();
    }

    public List<String> keys() {
        return boardgameRepo.keys();
    }


}
