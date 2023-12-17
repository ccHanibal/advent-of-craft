package blog;

import org.assertj.core.api.ThrowingConsumer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.function.Function;

import static blog.ArticleBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.instancio.Instancio.create;

class ArticleTests {

    @Test
    void should_add_comment_in_an_article() throws CommentAlreadyExistException {
        var article = anArticle()
                        .commented()
                        .build();

        assertThat(article.getComments()).hasSize(1);
        assertComment(article.getComments().getFirst(), COMMENT_TEXT, AUTHOR);
    }

    @Test
    void should_add_comment_in_an_article_containing_already_a_comment() throws Throwable {
        final var newComment = create(String.class);
        final var newAuthor = create(String.class);

        var article = anArticle()
                        .commented()
                        .build();

        article = article.addComment(newComment, newAuthor);

        assertThat(article.getComments()).hasSize(2);
        assertComment(article.getComments().getLast(), newComment, newAuthor);
    }

    @Test
    void should_add_comment_in_an_article_leaves_object_unchanged() throws Throwable {
        final var newComment = create(String.class);
        final var newAuthor = create(String.class);

        var article = anArticle()
                .commented()
                .build();

        var a2 = article.addComment(newComment, newAuthor);

        assertThat(article.getComments()).hasSize(1);
        assertComment(article.getComments().getLast(), COMMENT_TEXT, AUTHOR);
    }

    @Test
    void should_have_immutable_comments_list() throws CommentAlreadyExistException {
        var article = anArticle()
                .commented()
                .build();

        assertThatThrownBy(() -> {
            article.getComments().add(new Comment("foo", "bar", LocalDate.now()));
        }).isInstanceOf(UnsupportedOperationException.class);
    }

    @Nested
    class Fail {
        @Test
        void when__adding_an_existing_comment() throws CommentAlreadyExistException {
            var article = anArticle()
                    .commented()
                    .build();

            assertThatThrownBy(() -> {
                article.addComment(article.getComments().getFirst().text(), article.getComments().getFirst().author());
            }).isInstanceOf(CommentAlreadyExistException.class);
        }
    }

    private static void assertComment(Comment comment, String commentText, String author) {
        assertThat(comment.text()).isEqualTo(commentText);
        assertThat(comment.author()).isEqualTo(author);
    }
}