package org.koitharu.kotatsu.parsers.site.mangareader.ar

import org.jsoup.nodes.Document
import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.*
import org.koitharu.kotatsu.parsers.site.mangareader.MangaReaderParser
import org.koitharu.kotatsu.parsers.util.*
import java.text.SimpleDateFormat

@MangaSourceParser("SWATEAM", "SwaTeam", "ar")
internal class SwaTeam(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaSource.SWATEAM, "goldragon.me", pageSize = 42, searchPageSize = 39) {

	override val datePattern = "MMMM dd, yyyy"
	override val selectMangaList = ".listupd .bs .bsx"
	override val selectMangaListImg = "img"
	override val isNetShieldProtected = true

	override suspend fun getListPage(page: Int, filter: MangaListFilter?): List<Manga> {
		val url = buildString {
			append("https://")
			append(domain)

			when (filter) {

				is MangaListFilter.Search -> {
					append("/?s=")
					append(filter.query.urlEncoded())
				}

				is MangaListFilter.Advanced -> {
					append(listUrl)

					append("/?order=")
					append(
						when (filter.sortOrder) {
							SortOrder.ALPHABETICAL -> "a-z"
							SortOrder.NEWEST -> "added"
							SortOrder.POPULARITY -> "popular"
							SortOrder.UPDATED -> "update"
							else -> ""
						},
					)

					val tagKey = "genre[]".urlEncoded()
					val tagQuery =
						if (filter.tags.isEmpty()) ""
						else filter.tags.joinToString(separator = "&", prefix = "&") { "$tagKey=${it.key}" }
					append(tagQuery)

					if (filter.states.isNotEmpty()) {
						filter.states.oneOrThrowIfMany()?.let {
							append("&status=")
							when (it) {
								MangaState.ONGOING -> append("ongoing")
								MangaState.FINISHED -> append("completed")
								MangaState.PAUSED -> append("hiatus")
								else -> append("")
							}
						}
					}
				}

				null -> {
					append(listUrl)
					append("/?order=update")
				}
			}
			append("&page=")
			append(page.toString())
		}

		return parseMangaList(webClient.httpGet(url).parseHtml())
	}


	override suspend fun getDetails(manga: Manga): Manga {
		val docs = webClient.httpGet(manga.url.toAbsoluteUrl(domain)).parseHtml()
		val dateFormat = SimpleDateFormat(datePattern, sourceLocale)
		val chapters = docs.select("div.bixbox li").mapChapters(reversed = true) { index, element ->
			val url = element.selectFirst("a")?.attrAsRelativeUrl("href") ?: return@mapChapters null
			MangaChapter(
				id = generateUid(url),
				name = element.selectFirst("a")?.text() ?: "Chapter ${index + 1}",
				url = url,
				number = index + 1,
				scanlator = null,
				uploadDate = dateFormat.tryParse(element.selectFirst(".chapter-date")?.text()),
				branch = null,
				source = source,
			)
		}
		return parseInfo(docs, manga, chapters)
	}

	override suspend fun parseInfo(docs: Document, manga: Manga, chapters: List<MangaChapter>): Manga {

		/// set if is table

		val states = docs.selectFirst("div.spe span:contains(Ongoing)")?.text()

		val state = if (states.isNullOrEmpty()) {
			"completed"
		} else {
			"ongoing"
		}

		val mangaState = state.let {
			when (it) {
				"ongoing" -> MangaState.ONGOING

				"completed" -> MangaState.FINISHED

				else -> null
			}
		}
		val author = docs.selectFirst("span.author i")?.text()

		val nsfw = docs.selectFirst(".restrictcontainer") != null
			|| docs.selectFirst(".info-right .alr") != null
			|| docs.selectFirst(".postbody .alr") != null

		return manga.copy(
			description = docs.selectFirst("span.desc")?.html(),
			state = mangaState,
			author = author,
			isNsfw = manga.isNsfw || nsfw,
			tags = emptySet(),
			chapters = chapters,
		)
	}
}
