package com.nighthawk.spring_portfolio.mvc.lightboard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lightboard")
public class LightBoardApiController {

    @GetMapping("/{rows}/{columns}")
    public ResponseEntity<String> generateLightBoard(@PathVariable int rows, @PathVariable int columns) {
        LightBoard newLightBoard = new LightBoard(rows, columns);
        return new ResponseEntity<>(newLightBoard.toString(), HttpStatus.OK);
    }
}
