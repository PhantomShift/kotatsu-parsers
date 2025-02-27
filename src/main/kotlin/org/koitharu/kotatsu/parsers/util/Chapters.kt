package org.koitharu.kotatsu.parsers.util

import org.koitharu.kotatsu.parsers.InternalParsersApi
import org.koitharu.kotatsu.parsers.model.MangaChapter

@InternalParsersApi
inline fun <T> List<T>.mapChapters(
	reversed: Boolean = false,
	transform: (index: Int, T) -> MangaChapter?,
): List<MangaChapter> {
	val builder = ChaptersListBuilder(collectionSize())
	var index = 0
	val elements = if (reversed) this.asReversed() else this
	for (item in elements) {
		if (builder.add(transform(index, item))) {
			index++
		}
	}
	return builder.toList()
}

@PublishedApi
internal fun <T> Iterable<T>.collectionSize(): Int {
	return if (this is Collection<*>) this.size else 10
}

@PublishedApi
internal class ChaptersListBuilder(initialSize: Int) {

	private val ids = HashSet<Long>(initialSize)
	private val list = ArrayList<MangaChapter>(initialSize)

	fun add(chapter: MangaChapter?): Boolean {
		return chapter != null && ids.add(chapter.id) && list.add(chapter)
	}

	operator fun plusAssign(chapter: MangaChapter?) {
		add(chapter)
	}

	fun reverse() {
		list.reverse()
	}

	fun toList(): List<MangaChapter> = list
}
