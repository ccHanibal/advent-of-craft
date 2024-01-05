using FluentAssertions;
using FluentAssertions.LanguageExt;
using LanguageExt;
using Xunit;
using static Day19.Tests.ArticleBuilder;

namespace Day19.Tests
{
    public class ArticleTests
    {
        private Either<Error, Article> _article;
        private readonly Bogus.Randomizer _random = new();

        [Fact]
        public void Should_Add_Comment_In_An_Article()
        {
            Given(AnArticle());
            When(article => article.Right(a => a.AddComment(CommentText, Author)).Left(e => e));
            Then(article =>
            {
                article
                    .Right(a =>
                    {
                        a.Comments.Should().HaveCount(1);
                        AssertComment(a.Comments[0], CommentText, Author);
                    })
                    .Left(e => Assert.Fail($"Expected either to be right, but was left: {e.Message}"));
            });
        }

        [Fact]
        public void Should_Add_Comment_In_An_Article_Containing_Already_A_Comment()
        {
            var newComment = _random.String(10);
            var newAuthor = _random.String(3);

            Given(AnArticle().Commented());
            When(article => article.Right(a => a.AddComment(newComment, newAuthor)).Left(e => e));
            Then(article =>
            {
                article
                    .Right(a =>
                    {
                        a.Comments.Should().HaveCount(2);
                        AssertComment(a.Comments[1], newComment, newAuthor);
                    })
                    .Left(e => Assert.Fail($"Expected either to be right, but was left: {e.Message}"));
            });
        }

        private static void AssertComment(Comment comment, string expectedComment, string expectedAuthor)
        {
            comment.Text.Should().Be(expectedComment);
            comment.Author.Should().Be(expectedAuthor);
            comment.CreationDate.Should().Be(DateOnly.FromDateTime(DateTime.Now));
        }

        public class Fail
        {
            [Fact]
            public void When_Adding_An_Existing_Comment()
            {
                AnArticle().Build()
                    .Right(a =>
                    {
                        var articleWithOneComment = a.AddComment(CommentText, Author);
                        articleWithOneComment
                            .Right(a1 =>
                            {
                                var articleWithTwoComments = a1.AddComment(CommentText, Author);
                                articleWithTwoComments.Should()
                                    .BeLeft(e =>
                                    {
                                        e.Should().BeOfType<CommentAlreadyExists>();
                                    });
                            })
                            .Left(e => Assert.Fail($"Expected either to be right, but was left: {e.Message}"));
                    })
                    .Left(e => Assert.Fail($"Expected either to be right, but was left: {e.Message}"));
                ;
            }
        }

        private void Given(ArticleBuilder articleBuilder) => _article = articleBuilder.Build();
        private void When(Func<Either<Error, Article>, Either<Error, Article>> act) => _article = act(_article);
        private void Then(Action<Either<Error, Article>> act) => act(_article);
    }
}