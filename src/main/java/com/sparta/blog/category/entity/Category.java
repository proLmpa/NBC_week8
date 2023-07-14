package com.sparta.blog.category.entity;

import com.sparta.blog.category.dto.CategoryRequestDto;
import com.sparta.blog.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "categorized_posts")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Post> posts;

    public Category(CategoryRequestDto requestDto) {
        this.category = requestDto.getCategory();
        posts = new ArrayList<>();
    }
}
