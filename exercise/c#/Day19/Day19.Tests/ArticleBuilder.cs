using LanguageExt;

namespace Day19.Tests
{
	using static Either<Error, Article>;

	public class ArticleBuilder
	{
		public const string Author = "Pablo Escobar";
		public const string CommentText = "Amazing article !!!";

		private readonly Dictionary<string, string> _comments = new();
		private readonly Bogus.Randomizer _random = new();

		public static ArticleBuilder AnArticle() => new();

		public ArticleBuilder Commented()
		{
			_comments.Add(CommentText, Author);
			return this;
		}

		public Either<Error, Article> Build()
		{
			return _comments
						.Aggregate(
							Right(new Article(_random.String(), _random.String())),
							(article, comment) => article.Right(a => a.AddComment(comment.Key, comment.Value)).Left(e => e)
						);
		}
	}
}