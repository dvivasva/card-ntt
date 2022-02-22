package com.dvivasva.card.controller;

import com.dvivasva.card.dto.CardDto;
import com.dvivasva.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CardDto> create(@RequestBody Mono<CardDto> cardDtoMono) {
        return cardService.create(cardDtoMono);
    }

    @PutMapping("/{id}")
    public Mono<CardDto> update( @RequestBody Mono<CardDto> cardDtoMono,@PathVariable String id){
        return cardService.update(cardDtoMono,id);
    }

    @GetMapping("/{number}")
    public Mono<CardDto> findByNumber(@PathVariable String number){
        return cardService.findByNumber(number);
    }

}
