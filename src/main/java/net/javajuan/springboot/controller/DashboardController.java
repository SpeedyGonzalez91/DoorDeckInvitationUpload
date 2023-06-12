package net.javajuan.springboot.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import net.javajuan.springboot.entities.Frequency;
import net.javajuan.springboot.entities.GraphData;
import net.javajuan.springboot.service.DownloadService;
import net.javajuan.springboot.service.GraphDownloadService;
import net.javajuan.springboot.service.GraphService;
import net.javajuan.springboot.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class DashboardController {

    @Autowired
    InvitationService invitationService;
    @Autowired
    GraphService graphService;
    @Autowired
    DownloadService downloadService;
    @Autowired
    GraphDownloadService graphdownloadService;

   
    
    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("frequencyData", Collections.emptyList());
        model.addAttribute("chartBytes", new byte[0]);
        return "dashboard";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        if (!file.isEmpty()) {
            try {
                // Process the uploaded file
                List<Frequency> frequencyList = invitationService.generateFrequencyTable(file);
                model.addAttribute("frequencyData", frequencyList);
                
                // Generate the graph data based on the processed file
                List<GraphData> graphData = downloadService.generateGraphData(file);
                model.addAttribute("graphData", graphData);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle any exceptions or errors during file processing
                model.addAttribute("frequencyData", Collections.emptyList());
                model.addAttribute("graphData", Collections.emptyList());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Handle the case where the frequency value is not a valid integer
                model.addAttribute("frequencyData", Collections.emptyList());
                model.addAttribute("graphData", Collections.emptyList());
            }
        } else {
            // Handle the case where no file is uploaded
            model.addAttribute("frequencyData", Collections.emptyList());
            model.addAttribute("graphData", Collections.emptyList());
        }
        return "dashboard";
    }


    
    @PostMapping(path = "/graph", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<byte[]> generateGraph(@RequestPart() MultipartFile file) throws IOException {
        // Generate the graph data using the graphService
		byte[] graphData = graphService.retrieveFrequencyDataFromFile(file);

		// Set the appropriate response headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);

		// Return the graph image as a response
		return new ResponseEntity<>(graphData, headers, HttpStatus.OK);
    }
    
    @PostMapping("/frequency")
    public String showFrequencyTable(Model model, @RequestParam("file") MultipartFile file) {
        List<Frequency> frequencyTable = invitationService.generateFrequencyTable(file);
        model.addAttribute("frequencyTable", frequencyTable);
        
        return "frequency_table";
    }

    
    @GetMapping("/graph/download")
    public ResponseEntity<byte[]> downloadGraph(@RequestParam("file") MultipartFile file) throws IOException {
        // Generate the graph CSV data using the graphService
        String graphCSVData = graphdownloadService.generateGraphFromCSV(file);

        // Set the appropriate response headers for CSV download
        byte[] csvBytes = graphCSVData.getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "graph.csv");

        // Return the CSV file as a response
        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }
    

}







    



