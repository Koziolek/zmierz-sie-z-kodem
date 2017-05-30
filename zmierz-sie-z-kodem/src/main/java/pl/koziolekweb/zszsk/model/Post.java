package pl.koziolekweb.zszsk.model;

import java.util.List;
import java.util.UUID;

/**
 * Created by koziolek on 30.05.17.
 */
public class Post {

    private UUID id;
    private Author author;
    private String content;
    private List<Comment> comments;

    public Post() {
        this.id = UUID.randomUUID();
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return id.equals(post.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
