package com.teamkeon.bigpawsbackend.controller;

import com.teamkeon.bigpawsbackend.domain.Place;
import com.teamkeon.bigpawsbackend.dto.PlaceCreateRequest;
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
    public List<Place> getPlaces() {
        return placeService.getAllPlaces();
    }

    @GetMapping("/{id}")
    public Place getPlace(@PathVariable Long id) {
        return placeService.getPlace(id);
    }

    @PostMapping
    public Place createPlace(@RequestBody PlaceCreateRequest request) {
        return placeService.createPlace(request);
    }
}