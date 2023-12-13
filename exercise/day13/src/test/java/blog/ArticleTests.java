package blog;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArticleTests {
    public static final String AUTHOR = "Pablo Escobar";
    private static final String COMMENT_TEXT = "Amazing article !!!";

    @Test
    void should_add_comment_in_an_article() throws CommentAlreadyExistException {
        var article = new TestArticle()
                .withComment(AUTHOR, COMMENT_TEXT)
                .build();

        assertThat(article.getComments()).hasSize(1);

        var comment = article.getComments().get(0);
        assertThat(comment.text()).isEqualTo(COMMENT_TEXT);
        assertThat(comment.author()).isEqualTo(AUTHOR);
    }

    @Test
    void should_add_comment_in_an_article_containing_already_a_comment() throws CommentAlreadyExistException {
        var article = new TestArticle()
                .withComment(AUTHOR, COMMENT_TEXT)
                .withComment("Al Capone", "Finibus Bonorum et Malorum", LocalDate.now())
                .build();

        assertThat(article.getComments()).hasSize(2);

        var lastComment = article.getComments().getLast();
        assertThat(lastComment.text()).isEqualTo("Finibus Bonorum et Malorum");
        assertThat(lastComment.author()).isEqualTo("Al Capone");
    }

    @Nested
    class Fail {
        @Test
        void when_adding_an_existing_comment() throws CommentAlreadyExistException {
            var article = new TestArticle()
                    .withComment(AUTHOR, COMMENT_TEXT)
                    .build();

            assertThatThrownBy(() -> {
                article.addComment(COMMENT_TEXT, AUTHOR);
            }).isInstanceOf(CommentAlreadyExistException.class);
        }
    }
}
