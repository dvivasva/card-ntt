package com.dvivasva.card.service;

import com.dvivasva.card.dto.CardDto;
import com.dvivasva.card.model.Card;
import com.dvivasva.card.repository.CardRepository;
import com.dvivasva.card.utils.CardUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final static Logger logger= LoggerFactory.getLogger(CardService.class);

    public Flux<CardDto> read(){
        logger.info("inside methode read");
        return cardRepository.findAll().map(CardUtil::entityToDto);
    }

    public Mono<CardDto> create(Mono<CardDto> entityToDto){

        Mono<CardDto> result=entityToDto.map(
                p -> {
                    p.setCardVerificationValue(bCryptPasswordEncoder.encode(p.getCardVerificationValue()));
                    p.setKeyATM(bCryptPasswordEncoder.encode(p.getKeyATM()));
                    p.setKeyInternet(bCryptPasswordEncoder.encode(p.getKeyInternet()));

                    return p;
                });

        return result.map(CardUtil::dtoToEntity)
                .flatMap(cardRepository::save)
                .map(CardUtil::entityToDto);

    }
    public Mono<CardDto> update(Mono<CardDto> cardDtoMono, String id){
        Mono<CardDto> result=cardDtoMono.map(
                p -> {
                    p.setKeyATM(bCryptPasswordEncoder.encode(p.getKeyATM()));
                    p.setKeyInternet(bCryptPasswordEncoder.encode(p.getKeyInternet()));
                    return p;
                });
        return cardRepository.findById(id)
                .flatMap(p->result.map(CardUtil::dtoToEntity)
                        .doOnNext(e->e.setId(id)))
                .flatMap(cardRepository::save)
                .map(CardUtil::entityToDto);

    }

    public Mono<Void> delete(String id){
        return cardRepository.deleteById(id);
    }

    public Mono<CardDto> findByNumber(String number){
        logger.info("inside methode find by number ");
        Query query = new Query();
        query.addCriteria(Criteria.where("number").is(number));
        return reactiveMongoTemplate.findOne(query, Card.class).map(CardUtil::entityToDto);

    }

}
