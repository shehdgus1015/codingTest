package com.codingTest.repository;

import com.codingTest.entity.XmlData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XmlDataRepository extends JpaRepository<XmlData, Long> {
}

