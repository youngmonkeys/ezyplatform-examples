package org.youngmonkeys.personal.web.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class TopBlogResponse {
    private long id;
    private String title;
    private String slug;
    private String featuredImageUrl;
    private long views;
    private LocalDateTime publishedAt;

    public TopBlogResponse(
        long id, String title,
        String slug,
        String featuredImageUrl,
        long views,
        LocalDateTime publishedAt) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.featuredImageUrl = featuredImageUrl;
        this.views = views;
        this.publishedAt = publishedAt;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }

    public String getFeaturedImageUrl() {
        return featuredImageUrl;
    }

    public long getViews() {
        return views;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }
}
