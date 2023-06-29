package com.betmanager.betmenager.controllers;

import com.betmanager.betmenager.domain.bet.BetService;
import com.betmanager.betmenager.dto.request.BetRequest;
import com.betmanager.betmenager.dto.response.BetResponse;
import com.betmanager.betmenager.queues.IProducer;
import com.betmanager.betmenager.external.kafka.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BetController {
    private final IProducer producer;
    private final ObjectMapper objectMapper;

    private final BetService betService;

    public BetController(Producer producer, ObjectMapper objectMapper, BetService betService) {
        this.producer = producer;
        this.objectMapper = objectMapper;
        this.betService = betService;
    }

    @PostMapping("/ingest")
    public ResponseEntity<String> ingest(@RequestBody BetRequest betDTO) throws JsonProcessingException {
        String output = objectMapper.writeValueAsString(betDTO);
        System.out.println("output is: " + output);
        producer.produce(output);
        return ResponseEntity.ok("Ingested");
    }


    @GetMapping("/bets")
    public ResponseEntity<List<BetResponse>> getBets(@RequestParam int limit, @RequestParam int page) {
        if (limit == 0) {
            limit = 100;
        }
        if (page == 0) {
            page = 1;
        }
        int offset = (page - 1) * limit;

        List<BetResponse> bets = betService.loadPaginated(offset, limit);
        return ResponseEntity.ok(bets);
    }
}
