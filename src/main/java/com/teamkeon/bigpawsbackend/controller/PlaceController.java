package com.teamkeon.bigpawsbackend.controller;

import com.teamkeon.bigpawsbackend.dto.PlaceCreateRequest;
import com.teamkeon.bigpawsbackend.dto.PlaceResponse;
import com.teamkeon.bigpawsbackend.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping
    public List<PlaceResponse> getPlaces() {
        return placeService.getAllPlaces();
    }

    @GetMapping("/{id}")
    public PlaceResponse getPlace(@PathVariable Long id) {
        return placeService.getPlace(id);
    }

    @PostMapping
    public PlaceResponse createPlace(@RequestBody PlaceCreateRequest request) {
        return placeService.createPlace(request);
    }
}