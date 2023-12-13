package blog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestArticle {

    private String content = "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore";
    private String name = "Lorem Ipsum";
    private final List<Comment> comments = new ArrayList<>();

    public Article build() throws CommentAlreadyExistException {
        var article = new Article(name, content);
        for (var comment : comments)
            article.addComment(comment.text(), comment.author());

        return article;
    }

    public TestArticle withComment(Comment comment) {
        this.comments.add(comment);
        return this;
    }

    public TestArticle withComment(String auther, String text) {
        return withComment(new Comment(text, auther, LocalDate.now()));
    }

    public TestArticle withComment(String auther, String text, LocalDate postingDate) {
        return withComment(new Comment(text, auther, postingDate));
    }

    public TestArticle withContent(String content) {
        this.content = content;
        return this;
    }

    public TestArticle withName(String name) {
        this.name = name;
        return this;
    }
}
