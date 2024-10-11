package com.palmstam.roguelite.model.data;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.palmstam.roguelite.model.databaseItems.Enemy;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class EnemyCsvParserService {

    public List<Enemy> parseCsv(String filePath) throws Exception {
        try (Reader reader = new FileReader(filePath)) {
            CsvToBean<Enemy> csvToBean = new CsvToBeanBuilder<Enemy>(reader)
                    .withType(Enemy.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        }
    }
}