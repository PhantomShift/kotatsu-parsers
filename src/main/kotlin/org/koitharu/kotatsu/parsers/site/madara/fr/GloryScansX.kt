package org.koitharu.kotatsu.parsers.site.madara.fr

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.ContentType
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.madara.MadaraParser

@MangaSourceParser("GLORYSCANSX", "GloryScansX", "fr", ContentType.HENTAI)
internal class GloryScansX(context: MangaLoaderContext) :
	MadaraParser(context, MangaSource.GLORYSCANSX, "x.gloryscans.fr") {
	override val datePattern = "dd MMMM yyyy"
}
