package net.javajuan.springboot.service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.javajuan.springboot.entities.Invitation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
public class GraphService {
	
	public byte[] retrieveFrequencyDataFromFile(MultipartFile file) {
	    List<Invitation> invitationList = new ArrayList<>();
	    try {
	        Reader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_16);
	        CSVParser csvParser = new CSVParserBuilder().withSeparator('\t').build();
	        CSVReader csvReader = new CSVReaderBuilder(reader)
	                .withCSVParser(csvParser)
	                .build();
	        String[] line;
	        while ((line = csvReader.readNext()) != null) {
	            Invitation invitation = new Invitation();
	            invitation.setKioskGroupId(line[0]);
	            invitation.setLocation(line[5]);
	            invitation.setReason(line[6]);
	            invitation.setTimeZone(line[7]);
	            invitationList.add(invitation);
	        }
	    } catch (Exception e) {
	        //TODO: do something about this
	    }
	    Map<String, Long> result = invitationList.stream().collect(Collectors.groupingBy(
	            Invitation::getKioskGroupId,
	            Collectors.counting()
	    ));
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	    for (Map.Entry<String, Long> entry : result.entrySet()) {
	        dataset.addValue(entry.getValue(), "Frequency", entry.getKey());
	    }
	    JFreeChart chart = ChartFactory.createBarChart("Frequency Graph", "Category", "Frequency", dataset);
	    // Convert the chart to a byte array
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    try {
	        ChartUtils.writeChartAsPNG(outputStream, chart, 800, 400);
	    } catch (Exception e) {
	        //TODO: make something with me
	    }
	    return outputStream.toByteArray();
	}
	
	
	    public byte[] generateGraphImage() throws IOException {
	        // Generate the graph using your preferred graph generation logic
	        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	        // Populate the dataset with your graph data

	        JFreeChart chart = ChartFactory.createBarChart("Frequency Graph", "Category", "Frequency", dataset);
	        byte[] chartBytes = ChartUtils.encodeAsPNG(chart.createBufferedImage(800, 400));

	        return chartBytes;
	    }

	    public void saveGraphImage(byte[] graphImageData, String filePath) throws IOException {
	        // Save the graph image file to the specified filePath
	        try (OutputStream outputStream = new FileOutputStream(filePath)) {
	            outputStream.write(graphImageData);
	        }
	    }
	    
}