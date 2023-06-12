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
public class GraphDownloadService {
	
	public String generateGraphFromCSV(MultipartFile file) throws IOException {
        // Read the CSV file and generate the graph CSV data
        List<GraphData> graphDataList = readCSV(file);
        return generateCSV(graphDataList);
    }

    private List<GraphData> readCSV(MultipartFile file) throws IOException {
        // Read the CSV file and extract graph data
        // Implement your CSV reading logic here
        
        // Example code to read CSV using Apache Commons CSV
        List<GraphData> graphDataList = new ArrayList<>();
        Reader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        for (CSVRecord record : csvParser) {
            String category = record.get(0);
            int value = Integer.parseInt(record.get(1));
            GraphData graphData = new GraphData(category, value);
            graphDataList.add(graphData);
        }
        csvParser.close();
        reader.close();
        
        return graphDataList;
    }

    private String generateCSV(List<GraphData> graphDataList) {
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Category,Value\n");
        for (GraphData graphData : graphDataList) {
            csvBuilder.append(graphData.getCategory()).append(",").append(graphData.getValue()).append("\n");
        }
        return csvBuilder.toString();
    }
    
}

