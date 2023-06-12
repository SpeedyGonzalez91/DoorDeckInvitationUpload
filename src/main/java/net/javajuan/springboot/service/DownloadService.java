package net.javajuan.springboot.service;



import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import net.javajuan.springboot.entities.GraphData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DownloadService {
	
	   

	    public List<GraphData> generateGraphData(MultipartFile file) throws IOException {
	        List<GraphData> graphDataList = new ArrayList<>();

	        try (Reader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
	             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

	            // Read the CSV records
	            for (CSVRecord record : csvParser) {
	                String category = record.get(0);
	                int value = Integer.parseInt(record.get(1));

	                GraphData graphData = new GraphData(category, value);
	                graphDataList.add(graphData);
	            }
	        }

	        return graphDataList;
	    }
}

	   


