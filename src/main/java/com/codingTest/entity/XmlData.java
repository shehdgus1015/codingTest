package com.codingTest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class XmlData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tag;
    private String category;
    private String value;

    public XmlData() {
    }

    public XmlData(String tag, String category, String value) {
        this.tag = tag;
        this.category = category;
        this.value = value;
    }

    public Long getId() {
        return id;
    }
    public String getTag() {
        return tag;
    }
    public String getCategory() {
        return category;
    }
    public String getValue() {
        return value;
    }
}
