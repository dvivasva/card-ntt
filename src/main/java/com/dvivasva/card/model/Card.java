package com.dvivasva.card.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document
public class Card {
    @Id
    private String id;
    private String type; // debt or credit
    private String number;
    private Date dueDate;
    private String cardVerificationValue;
    private String keyATM;
    private String keyInternet;
    private List<String> connectTo; // account or credit "connectTo" : [ "ACC-001", "ACC-004", "ACC-001" ]

}
