package org.koitharu.kotatsu.parsers.model

import java.util.*

sealed interface MangaListFilter {

	fun isEmpty(): Boolean

	val sortOrder: SortOrder?

	data class Search(
		@JvmField val query: String,
	) : MangaListFilter {

		override val sortOrder: SortOrder? = null

		override fun isEmpty() = query.isBlank()
	}

	data class Advanced(
		override val sortOrder: SortOrder,
		@JvmField val tags: Set<MangaTag>,
		@JvmField val locale: Locale?,
		@JvmField val states: Set<MangaState>,
	) : MangaListFilter {

		override fun isEmpty(): Boolean = tags.isEmpty() && locale == null && states.isEmpty()
	}
}
