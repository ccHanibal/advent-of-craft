package blog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Article {
    private final String name;
    private final String content;
    private final List<Comment> comments;

    public Article(String name, String content) {
        this.name = name;
        this.content = content;
        this.comments = new ArrayList<>();
    }
    public Article(String name, String content, Comment... comments) {
        this.name = name;
        this.content = content;
        this.comments = Arrays.asList(comments);
    }

    private Article addComment(
            String text,
            String author,
            LocalDate creationDate) throws CommentAlreadyExistException {
        var comment = new Comment(text, author, creationDate);

        if (comments.contains(comment))
            throw new CommentAlreadyExistException();

        return new Article(name, content, Stream.concat(comments.stream(), Stream.of(comment)).toArray(Comment[]::new));
    }

    public Article addComment(String text, String author) throws CommentAlreadyExistException {
        return addComment(text, author, LocalDate.now());
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }
}

