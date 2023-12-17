package blog;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ArticleBuilder {
    public static final String AUTHOR = "Pablo Escobar";
    public static final String COMMENT_TEXT = "Amazing article !!!";
    private final HashMap<String, String> comments;

    public ArticleBuilder() {
        comments = new HashMap<>();
    }

    public static ArticleBuilder anArticle() {
        return new ArticleBuilder();
    }

    public ArticleBuilder commented() {
        this.comments.put(COMMENT_TEXT, AUTHOR);
        return this;
    }
    public ArticleBuilder commented(String text, String author) {
        this.comments.put(text, author);
        return this;
    }

    public Article build() throws CommentAlreadyExistException {
        return new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore",
                comments.entrySet().stream()
                        .map(e -> new Comment(e.getKey(), e.getValue(), LocalDate.now()))
                        .toArray(Comment[]::new)
        );
    }
}
