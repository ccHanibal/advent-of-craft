package blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assumptions.assumeThat;

class ArticleTests {

    private Article article;

    @BeforeEach
    public void beforeEach() throws CommentAlreadyExistException {
        this.article =
                new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
                );

        assumeThat(article.getComments())
                .isEmpty();

        article.addComment("Amazing article !!!", "Pablo Escobar");
    }

    @Test
    void it_should_add_valid_comment() {
        assertThat(article.getComments())
                .hasSize(1);
    }

    @Test
    void it_should_add_a_comment_with_the_given_text() {
        assertThat(article.getComments())
                .anyMatch(comment -> comment.text().equals("Amazing article !!!"));
    }

    @Test
    void it_should_add_a_comment_with_the_given_author() {
        assertThat(article.getComments())
                .anyMatch(comment -> comment.author().equals("Pablo Escobar"));
    }

    @Test
    void it_should_add_a_comment_with_the_date_of_the_day() {
        assertThat(article.getComments())
                .anyMatch(comment -> comment.creationDate().equals(LocalDate.now()));
    }

    @Test
    void it_should_fail_when_adding_an_existing_comment() {
        assertThatThrownBy(() -> {
            article.addComment("Amazing article !!!", "Pablo Escobar");
        }).isInstanceOf(CommentAlreadyExistException.class);
    }
}
