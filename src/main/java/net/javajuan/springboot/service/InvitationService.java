package net.javajuan.springboot.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.javajuan.springboot.entities.Frequency;
import net.javajuan.springboot.entities.Invitation;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;







@Service
public class InvitationService {
    
    
    public List<Frequency> convertToFrequencyList(Map<Invitation, Integer> frequencyDataMap) {
        List<Frequency> frequencyData = new ArrayList<>();
        for (Map.Entry<Invitation, Integer> entry : frequencyDataMap.entrySet()) {
            Frequency frequency = new Frequency(entry.getKey(), entry.getValue());
            frequencyData.add(frequency);
        }
        return frequencyData;
    }
    
    public List<Frequency> getFrequencyData(String filePath) throws IOException {
        List<String> lines = readCSVFile(filePath);
        Map<String, Integer> frequencyMap = calculateFrequency(lines);

        List<Frequency> frequencyData = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            String category = entry.getKey();
            int frequency = entry.getValue();
            Frequency frequencyObj = new Frequency(category, frequency);
            frequencyData.add(frequencyObj);
        }

        return frequencyData;
    }

    private List<String> readCSVFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }
    private Map<String, Integer> calculateFrequency(List<String> lines) {
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String line : lines) {
            // Process each line and calculate the frequency
            String[] elements = line.split(",");

            for (String element : elements) {
                String category = element.trim();
                frequencyMap.put(category, frequencyMap.getOrDefault(category, 0) + 1);
            }
        }

        return frequencyMap;
    }
    
    public List<Frequency> generateFrequencyTable(MultipartFile file) {
        List<Invitation> invitationList = new ArrayList<>();
        try (Reader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_16);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setDelimiter('\t').build())) {

            for (CSVRecord csvRecord : csvParser) {
                Invitation invitation = new Invitation();
                invitation.setKioskGroupId(csvRecord.get(0));
                invitation.setLocation(csvRecord.get(5));
                invitation.setReason(csvRecord.get(6));
                invitation.setTimeZone(csvRecord.get(7));
                invitationList.add(invitation);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Handle the IOException appropriately
        }

        Map<String, Long> frequencyMap = invitationList.stream()
                .collect(Collectors.groupingBy(Invitation::getKioskGroupId, Collectors.counting()));

        List<Frequency> frequencyTable = new ArrayList<>();
        for (Map.Entry<String, Long> entry : frequencyMap.entrySet()) {
            Frequency frequency = new Frequency(entry.getKey(), entry.getValue().intValue());
            frequencyTable.add(frequency);
        }

        return frequencyTable;
    }




}




