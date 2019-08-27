package com.yardox.training.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnews")
    private Long id;

    private String title;

    private Timestamp date;

    @Column(columnDefinition = "TEXT")
    private String text;

    private String link;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public News() {
    }

    public News(String title, Timestamp date, String text, String link, Tag tag) {
        this.title = title;
        this.date = date;
        this.text = text;
        this.link = link;
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", text='" + text + '\'' +
                ", link='" + link + '\'' +
                ", tag=" + tag +
                '}';
    }
}
