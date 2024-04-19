package com.codingTest.controller;

import com.codingTest.entity.XmlData;
import com.codingTest.repository.XmlDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class XmlDataController {

    @Autowired
    private XmlDataRepository xmlDataRepository;

    @PostMapping("/save-to-db")
    public ResponseEntity<String> processXmlFile(@RequestBody XmlDataRequest request) {
        try {

            String tag = request.getTag();
            String category = request.getCategory();
            String value = request.getValue();

            if (value == null || value.isEmpty()) {
                return ResponseEntity.ok("일치하는 결과가 없습니다.");
            }

            XmlData xmlData = new XmlData(tag, category, value);
            xmlDataRepository.save(xmlData);

            return ResponseEntity.ok("DB에 저장되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생했습니다.");
        }
    }

    @GetMapping("/load-to-db")
    public ResponseEntity<List<XmlData>> getDbList() {
        try {
            List<XmlData> dbList = xmlDataRepository.findAll();
            return ResponseEntity.ok(dbList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    static class XmlDataRequest {
        private String tag;
        private String category;
        private String value;

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}

