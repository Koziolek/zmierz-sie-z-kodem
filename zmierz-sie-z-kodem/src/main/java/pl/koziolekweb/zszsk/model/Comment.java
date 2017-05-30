package pl.koziolekweb.zszsk.model;

import java.util.UUID;

/**
 * Created by koziolek on 30.05.17.
 */
public class Comment {
    private UUID id;
    private Author author;
    private Post post;
    private String content;

    public Comment() {
        this.id = UUID.randomUUID();
    }

    public Author getAuthor() {

        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        return id.equals(comment.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
