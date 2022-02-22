package com.dvivasva.card.utils;

import com.dvivasva.card.dto.CardDto;
import com.dvivasva.card.model.Card;
import org.springframework.beans.BeanUtils;

public class CardUtil {
    public static CardDto entityToDto(Card card){
        var cardDto=new CardDto();
        BeanUtils.copyProperties(card,cardDto);
        return cardDto;
    }
    public static Card dtoToEntity(CardDto cardDto){
        var entity=new Card();
        BeanUtils.copyProperties(cardDto,entity);
        return entity;
    }
}
