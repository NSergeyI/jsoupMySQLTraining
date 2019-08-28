package com.yardox.training.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tag", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtag")
    private Long id;

    private String name;

//    @ManyToMany(mappedBy = "tags")
//    private Set<News> newsSet;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Set<News> getNewsSet() {
//        return newsSet;
//    }
//
//    public void setNewsSet(Set<News> newsSet) {
//        this.newsSet = newsSet;
//    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
