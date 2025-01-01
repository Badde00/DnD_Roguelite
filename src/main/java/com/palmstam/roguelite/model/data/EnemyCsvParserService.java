package com.palmstam.roguelite.model.data;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Service
public class EnemyCsvParserService {

    public List<EnemyDTO> parseCsv(String filePath) throws Exception {
        try (Reader reader = new FileReader(filePath)) {
            CsvToBean<EnemyDTO> csvToBean = new CsvToBeanBuilder<EnemyDTO>(reader)
                    .withType(EnemyDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        }
    }

    public List<EnemyDTO> parseCsv(MultipartFile file) throws Exception {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            CsvToBean<EnemyDTO> csvToBean = new CsvToBeanBuilder<EnemyDTO>(reader)
                    .withType(EnemyDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        }
    }
}