package org.koitharu.kotatsu.parsers.site.madara.es

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.madara.MadaraParser

@MangaSourceParser("TECNOSCANN", "TecnoScann", "es")
internal class TecnoScann(context: MangaLoaderContext) :
	MadaraParser(context, MangaSource.TECNOSCANN, "tecnoscann.com", 24) {

	override val datePattern = "MMMM d, yyyy"
}
