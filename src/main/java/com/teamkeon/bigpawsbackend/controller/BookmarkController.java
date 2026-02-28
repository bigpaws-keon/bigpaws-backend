package com.teamkeon.bigpawsbackend.controller;

import com.teamkeon.bigpawsbackend.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/{placeId}")
    public Map<String, Boolean> toggleBookmark(@PathVariable Long placeId) {
        boolean bookmarked = bookmarkService.toggle(placeId);
        return Map.of("bookmarked", bookmarked);
    }

    @GetMapping("/{placeId}")
    public Map<String, Boolean> isBookmarked(@PathVariable Long placeId) {
        boolean bookmarked = bookmarkService.isBookmarked(placeId);
        return Map.of("bookmarked", bookmarked);
    }

    @GetMapping("/my")
    public List<Long> getMyBookmarks() {
        return bookmarkService.getMyBookmarkPlaceIds();
    }
}
